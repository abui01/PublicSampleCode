package com.learning.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailManager {

	final static Logger logger = Logger.getLogger(EmailManager.class);

	private String toAddress = "";
	private String ccAddress = "";
	private String bccAddress = "";
	public List<String> attachmentFiles = new ArrayList<String>();

	public void setToAddress(String toEmailsList) {
		toAddress = toEmailsList;		
	}
	
	public void sendEmail(String host, String port, final String emailUserID, final String emailUserPassword,
			String subject, String message, List<String> attachments) {
		try {
			// sets SMTP server properties
			Properties prop = new Properties();
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.user", emailUserID);
			prop.put("mail.password", emailUserPassword);
			logger.info("step1> preparing email configuration...");

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailUserID, emailUserPassword);
				}
			};
			Session session = Session.getInstance(prop, auth);
			// creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(emailUserID));
			msg.addRecipients(Message.RecipientType.TO, setMultipleEmails(toAddress));
			if (!ccAddress.equals(null) && !ccAddress.isEmpty()) {
				msg.addRecipients(Message.RecipientType.CC, setMultipleEmails(ccAddress));
			}
			if (!bccAddress.equals(null) && !bccAddress.isEmpty()) {
				msg.addRecipients(Message.RecipientType.BCC, setMultipleEmails(bccAddress));
			}
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			if (attachmentFiles != null && attachmentFiles.size() > 0) {
				for (String temp : attachmentFiles) {
					MimeBodyPart attachPart = new MimeBodyPart();
					try {
						attachPart.attachFile(temp);
					} catch (IOException ex) {
						logger.error("Oops, Attaching files to email error !!!", ex);
					}
					multipart.addBodyPart(attachPart);
				}
			}

			logger.info("Step2> attaching report files & error screenshots...");
			// sets the multi-part as email's content
			msg.setContent(multipart);

			// sends the e-mail
			logger.info("Step3> Sending e-Mail in progress...");
			Transport.send(msg);
			logger.info("Step4> Sending e-mail complete...");
			
		} catch (AddressException ex) {
			logger.error("Oops, Sending email error !!!", ex);
		} catch (MessagingException ex) {
			logger.error("Oops, Sending email error !!!", ex);
		} 
	}

	
	private InternetAddress[] setMultipleEmails(String emailAddress) {
		String multipleEmails[] = emailAddress.split(",");
		InternetAddress[] addresses = new InternetAddress[multipleEmails.length];
		try {
			for (int i = 0; i < multipleEmails.length; i++) {
				addresses[i] = new InternetAddress(multipleEmails[i]);
			}
		} catch (AddressException ex) {
			logger.error("Oops, Adding multiple email addresses error!", ex);
		}
		return addresses;
	}

	public void sendEmail(List<String> attachments)
	{		
		String emailMsgBody = "Test Report Results. " 
		+ "<br><br> Regards, <br>PSD Automation<br>";		
		
		setToAddress("abui83@yahoo.com");		
		sendEmail("smtp.gmail.com", "587", "anhkibui@gmail.com", 
				"123452bg", "Automated Java Email for Selenium", emailMsgBody,
				attachmentFiles);
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
