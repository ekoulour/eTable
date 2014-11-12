package application;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.lang.model.util.Elements;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.swing.text.Document;
 
public class MailReader
{
	 Folder inbox;
	 
	 //List with from+subject
	 ArrayList<String> fromAndSubject = new ArrayList<String>();
	 ArrayList<String> fromSubject = new ArrayList<String>();
	 ArrayList<String> fromSub = new ArrayList<String>();
	 
	 //Array list with contents
	 //ArrayList<String> content = new ArrayList<String>();
	 ArrayList<String> contentText = new ArrayList<String>();
	 ArrayList<String> messageContent = new ArrayList<String>();
	 
	 //Constructor of the calss.
	 public ArrayList<String> ReadMails()
	 {
		 /*  Set the mail properties  */
		 Properties props = System.getProperties();
		 props.setProperty("mail.store.protocol", "imaps");
		 
		 try {
			 /*  Create the session and get the store for read the mail. */
			 Session session = Session.getDefaultInstance(props, null);
			 Store store = session.getStore("imaps");
			 store.connect("imap.gmail.com","nguiproject@gmail.com", "KRISTAefi");
			 
			 /*  Mention the folder name which you want to read. */
			 inbox = store.getFolder("Inbox");
		
			 /*Open the inbox using store.*/
			 inbox.open(Folder.READ_ONLY);
			 
			 /*  Get all messages from inbox*/
			 Message messages[] = inbox.getMessages(); 
			 			 
			 /* Use a suitable FetchProfile    */
			 FetchProfile fp = new FetchProfile();
			 fp.add(FetchProfile.Item.ENVELOPE);
			 fp.add(FetchProfile.Item.CONTENT_INFO);
			 inbox.fetch(messages, fp);
		 
		 try {
			 fromSub = printAllMessages(messages);
			 inbox.close(true);
			 store.close();
			 }
		 catch (Exception ex) {
			 System.out.println("Exception arise at the time of read mail");
			 ex.printStackTrace();
			 }
		 }
		 
		 catch (NoSuchProviderException e) {
			 e.printStackTrace();
			 System.exit(1);
			 }
		 catch (MessagingException e) {
			 e.printStackTrace();
			 System.exit(2);
		 	}
		 
	 return fromSub;
	 }
	 
	 //Give each message to envelope for getting from and subject
	 public ArrayList<String> printAllMessages(Message[] msgs) throws Exception {
		 
		 for (int i = 0; i < msgs.length; i++) {
			 fromSubject = printEnvel(msgs[i]);
		 }
		 return fromSubject;
	 }
	 
	 /*  Print the envelope(FromAddress,ReceivedDate,Subject)  */
	 public ArrayList<String> printEnvel(Message message) throws Exception {
		 Address[] a;
	
		 // Add to fromAndSubject list From and Subject
		 if ((a = message.getFrom()) != null) {
			 for (int j = 0; j < a.length; j++) {
				 
				String subject = message.getSubject(); 
				 
				String mailFrom = a[j].toString(); 
				Pattern MY_PATTERN = Pattern.compile("\\<(.*?)\\>");
				
				Matcher m = MY_PATTERN.matcher(mailFrom);
				while (m.find()) {
				    String from = m.group(1);
				    fromAndSubject.add(from + " " + subject);
				}
			 }
		 }
		 
		 return fromAndSubject;
	 }
 
	 

	 
/*____________________________________________________________________________________________________*/ 
	 
	 
	 //To get Content of selected email
	 public ArrayList<String> ReadContent(Integer x) { //String content= messages[i].getContent().toString();
		 
		 /*  Set the mail properties  */
		 Properties props = System.getProperties();
		 props.setProperty("mail.store.protocol", "imaps");
		 
		 try {
			 /*  Create the session and get the store for read the mail. */
			 Session session = Session.getDefaultInstance(props, null);
			 Store store = session.getStore("imaps");
			 store.connect("imap.gmail.com","nguiproject@gmail.com ", "KRISTAefi");
			 
			 /*  Mention the folder name which you want to read. */
			 inbox = store.getFolder("Inbox");
		
			 /*Open the inbox using store.*/
			 inbox.open(Folder.READ_ONLY);
			 
			 /*  Get all messages from inbox*/
			 Message messages[] = inbox.getMessages(); 
			 			 
			 /* Use a suitable FetchProfile    */
			 FetchProfile fp = new FetchProfile();
			 fp.add(FetchProfile.Item.ENVELOPE);
			 fp.add(FetchProfile.Item.CONTENT_INFO);
			 inbox.fetch(messages, fp);
		 
		 try {
			 messageContent = printMessagecontent(messages, x);
			 inbox.close(true);
			 store.close();
			 }
		 catch (Exception ex) {
			 System.out.println("Exception arise at the time of read mail");
			 ex.printStackTrace();
			 }
		 }
		 
		 catch (NoSuchProviderException e) {
			 e.printStackTrace();
			 System.exit(1);
			 }
		 catch (MessagingException e) {
			 e.printStackTrace();
			 System.exit(2);
		 	}
		 
	 return messageContent;
	 }
	 
//	 //Give each message to envelope Content
	 public ArrayList<String> printMessagecontent(Message[] msgs, int x) throws Exception {
				 
		 fromSubject = printEnvelope(msgs[x]);
		 System.out.println("From" + fromSubject);
		 return fromSubject;
	 }
	 
	 /*  Print the envelope(FromAddress,ReceivedDate,Subject)  */
	 public ArrayList<String> printEnvelope(Message message) throws Exception {
		 Address[] a;
		 
		 // Add to fromAndSubject list From and Subject
		 if ((a = message.getFrom()) != null) {
			 for (int j = 0; j < a.length; j++) {
				 
//				String subject = message.getSubject(); 
//				 
//				String mailFrom = a[j].toString(); 
//		
//				Pattern MY_PATTERN = Pattern.compile("\\<(.*?)\\>");
//				
//				Matcher m = MY_PATTERN.matcher(mailFrom);
//				while (m.find()) {
//				    String from = m.group(1);
//				    fromAndSubject.add(from + " " + subject);
//				}
				
				Object content = message.getContent();  
	            if (message.isMimeType("text/*")) {  
	                System.out.println((String)content); 
	                
	            }  
	            else if (message.isMimeType("multipart/alternative")) {  
	                Multipart mp = (Multipart)message.getContent();  
	                int partsCount = mp.getCount();  
	                //IN CASE OF MULTIPART MAIL WE CAN TAKE ONLY THE BEST ALTERNATE PART  
	                //WHICH HAS THE HIGHEST SEQUENCE NUMBER  
	                System.out.println("1 " +(String)mp.getBodyPart(partsCount -1).getContent());
	                //System.out.format("2" + (String)mp.getBodyPart(partsCount -1).getContent());
	                //String multipartText = (String)mp.getBodyPart(partsCount -1).getContent();

	                //String formattedXml = new [UnknownClass]().format(multipartText);
//	                
//	                Pattern MY_PATTERN2 = Pattern.compile("\\>(.*?)\\div>");
//					
//					Matcher k = MY_PATTERN2.matcher(multipartText);
//					while (k.find()) {
//					    String fromT = k.group(1);
//					    contentText.add(fromT);
//					    System.out.println("table" + contentText);
//					}
	            }  
				
				
			 }
		 }
		 return contentText; 
	 }
}
 
 
 
 
 
 
	 
//	 // TO
//	 if ((a = message.getRecipients(Message.RecipientType.TO)) != null)
//	 {
//		 for (int j = 0; j < a.length; j++)
//		 {
//		 System.out.println("TO: " + a[j].toString());
//		 }
//	 }
//	 String subject = message.getSubject();
//	 Date receivedDate = message.getReceivedDate();
//	 String content = message.getContent().toString();
//	 System.out.println("Subject : " + subject);
//	 System.out.println("Received Date : " + receivedDate.toString());
//	 //System.out.println("Content : " + content);
//	 //getContent(message);
// }
 
// public void getContent(Message msg)
// {
//	 try
//	 {
//		 String contentType = msg.getContentType();
//		 System.out.println("Content Type : " + contentType);
//		 Multipart mp = (Multipart) msg.getContent();
//		 int count = mp.getCount();
//		 for (int i = 0; i < count; i++)
//		 {
//			 dumpPart(mp.getBodyPart(i));
//		 }
//	 }
//	 catch (Exception ex)
//	 {
//		 System.out.println("Exception arise at get Content");
//		 ex.printStackTrace();
//	 }
// }
 
// public void dumpPart(Part p) throws Exception
// {
//	 // Dump input stream ..
//	 InputStream is = p.getInputStream();
//	 // If "is" is not already buffered, wrap a BufferedInputStream
//	 // around it.
//	 if (!(is instanceof BufferedInputStream))
//	 {
//		is = new BufferedInputStream(is);
//	 }
//	 int c;
//	 System.out.println("Message : ");
//	 while ((c = is.read()) != -1)
//	 {
//		 System.out.write(c);
//	 }
// }
 
//	 public static void main(String args[])
//	 {
//		 new MailReader();
//		 
//	 }