/**
 * Reference to link (Retrieved on 01.11.2014)
 * http://metoojava.wordpress.com/2010/03/21/java-code-to-receive-mail-using-javamailapi/
 * 
 * This class give a count of mails and unread mails
 */

package application;

import java.util.*;

import javax.mail.*;
 
public class CountMails
{
 Folder inbox;
 
 //Constructor of the calss.
 public String Counts() {
	 
	 /*  Set the mail properties  */
	 Properties props = System.getProperties();
	 props.setProperty("mail.store.protocol", "imaps");
	 String countOfMessages = "empty";
	 
	 try {
		 /*  Create the session and get the store for read the mail. */
		 Session session = Session.getDefaultInstance(props, null);
		 Store store = session.getStore("imaps");
		 store.connect("imap.gmail.com","nguiproject@gmail.com", "KRISTAefi");
		 
		 /*  Mention the folder name which you want to read. */
		 inbox = store.getFolder("Inbox");
		 
		 /* Total message count and unread message count */
		 countOfMessages = "Total Messages : " + inbox.getMessageCount() + "\nUnread Messages : " + inbox.getUnreadMessageCount();
				
		 /*Open the inbox using store.*/
		 inbox.open(Folder.READ_ONLY);	 

	 }
	 catch (NoSuchProviderException e) {
		 e.printStackTrace();
		 System.exit(1);
	 }
	 catch (MessagingException e) {
		 e.printStackTrace();
		 System.exit(2);
	 }
	 
	 return countOfMessages;
 }
 
}


//package application;
//
//import java.util.Properties;
//
//import javax.mail.Folder;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Store;
//import javax.mail.URLName;
//
//import com.sun.mail.pop3.POP3SSLStore;
//
//public class test {
//	
//	 public static void prot() {
//         
//	        System.out.println("YEs dit it!");
//	    }
//	 
//	 private Session session = null;
//	 private Store store = null;
//	 private String username, password;
//	 private Folder folder;
//	
//	 public void setUserPass(String username, String password) {
//		 this.username = username;
//	     this.password = password;
//	 }
//	 
//	 public void connect() throws Exception {
//	        
//	        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//	         
//	        Properties pop3Props = new Properties();
//	         
//	        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
//	        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
//	        pop3Props.setProperty("mail.pop3.port",  "995");
//	        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
//	         
//	        URLName url = new URLName("pop3", "pop.gmail.com", 995, "",
//	        		"kristakrkr@gmail.com", "bul4erinka");
//	         
//	        session = Session.getInstance(pop3Props, null);
//	        store = new POP3SSLStore(session, url);
//	        store.connect();
//	         
//	    }
//	 
//	 public void openFolder(String folderName) throws Exception {
//	        
//	        // Open the Folder
//	        folder = store.getDefaultFolder();
//	         
//	        folder = folder.getFolder(folderName);
//	         
//	        if (folder == null) {
//	            throw new Exception("Invalid folder");
//	        }
//	         
//	        // try to open read/write and if that fails try read-only
//	        try {
//	             
//	            folder.open(Folder.READ_WRITE);
//	             
//	        } catch (MessagingException ex) {
//	             
//	            folder.open(Folder.READ_ONLY);
//	             
//	        }
//	    }
//	 
//	 public void closeFolder() throws Exception {
//	        folder.close(false);
//	    }
//	     
//	    public int getMessageCount() throws MessagingException {
//	        return folder.getMessageCount();
//	    }
//	     
//	    public int getNewMessageCount() throws MessagingException {
//	        return folder.getNewMessageCount(); //getNewMessageCount();
//	    }
//	     
//	    public void disconnect() throws Exception {
//	        store.close();
//	    }
//	    
//	    
//	    
//	    public static void callMe() { //callMe
//	         
//	    	inbox = store.getFolder("Inbox");
//	    	System.out.println("No of Unread Messages : " + inbox.getUnreadMessageCount());
//	    	
//	        try {
//	             
//	        	EMails gmail = new EMails();
//	            gmail.setUserPass("kristakrkr@gmail.com","bul4erinka");
//	            gmail.connect();
//	            gmail.openFolder("INBOX");
//	             
//	            int totalMessages = gmail.getMessageCount();
//	            int newMessages = gmail.getNewMessageCount();
//	            //int unreadMessages = folder.getNewMessageCount();
//	            
//	            System.out.println("Total messages = " + totalMessages);
//	            System.out.println("New messages = " + newMessages);
//	            //System.out.println("New messages = " + unreadMessages);
//	            	             
//	            //gmail.printAllMessageEnvelopes();
//	            //gmail.printAllMessages();
//	            gmail.closeFolder();
//	             
//	        } catch(Exception e) {
//	            e.printStackTrace();
//	           // System.exit(-1);
//	        }
//	         
//	    }
//	 
//	 
//}
