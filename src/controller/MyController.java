package controller;

import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import twitter.TwitterTimeline;
import twitter.TwitterList;
import GmailAPI.Philosopher;
import GmailAPI.connectGmail;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


@SuppressWarnings("restriction")
public class MyController implements Initializable {
	//Initialize ArrayList<ArrayList<Philosopher>>
	ArrayList<ArrayList<Philosopher>> MessagesList = new ArrayList<ArrayList<Philosopher>>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Gmail hide elements
		EmileContent.setVisible(false);
		progresInd.setVisible(false);
		authField.setVisible(false);
		btnAuth.setVisible(false);
		btnRefresh.setVisible(false);
		picRefresh.setVisible(false);
	
	//Fill ListViewTwitter with Tweets
		//ListViewTwitter.setItems(addTwittStreem());
		addTwittStreem();

		
	}
	/**
	 *	On mouse moving around it catches the mouse gesture and print out gesture name and coordinates 
	**/
	
	@FXML
        public void mouseHandler(MouseEvent mouseEvent) {
//            System.out.println(mouseEvent.getEventType() + "\n"
//                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
//                    + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
//                    + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());

        }    
	
	/*
	 * For Twitter
	 */
		
	@FXML private ListView<String> ListViewTwitter;
	@FXML private GridPane TWGrid;
	@FXML private ScrollPane TwittPan;
	
	public void addTwittStreem() {
		ArrayList<ArrayList<TwitterList>> TweettsList = new ArrayList<ArrayList<TwitterList>>();
		ArrayList<String> TweetStreemList = new ArrayList<String>();
		TweettsList = new TwitterTimeline().TwittMain();
		
		
		Integer x = 0;
		Integer TwittArraySize = TweettsList.size();
		
		for(ArrayList<TwitterList> TweettList : TweettsList){
	    	for(TwitterList tweet : TweettList){
	    		
				String aa = tweet.date + "<b>" + tweet.userName +"</b> @" + tweet.screenName + "\n" + tweet.text;
	    
	    		
				TweetStreemList.add(aa);
	    		System.out.println(aa);
	    		TextArea tt = new TextArea("avbhi");
	    		tt.setPrefHeight(80);
	    		tt.setPrefWidth(411);
	    		tt.setWrapText(true);
//	    		TwitterGrid.getRowConstraints().add(tt);
	    		TWGrid.addRow(x, tt);
	    		
	    		x+=1;
	    		
	    		}
	    	}
		}
	
	@FXML
	public void TwitterSwipeUP(){
		
	}
	
	
	
	
	/*
	 * Section for Gmail
	 */
	@FXML private Pane panNotifications;
	@FXML private Pane panShort;
	
	@FXML private ProgressIndicator progresInd;
	
	@FXML private Label CountMail;
	@FXML private ListView<String> ListView;
	
	@FXML private TextArea EmileContent;
	
	@FXML private ImageView picRefresh;
	@FXML private Button btnRefresh;
	
	
	//To start authorization
	@FXML private Button btnStartAuthToGmail;
	
	@FXML public void btnStartAuthOnMouseClick(MouseEvent event) throws Exception {		
		//connect to gmail.com start
		new connectGmail().authorize();
		//Hide start Auth button
		btnStartAuthToGmail.setVisible(false);
		
		//Set to visible Auth field and Auth button
		btnAuth.setVisible(true);
		authField.setVisible(true);
	}
	
	
	//To give an authorization code and start authorization
	@FXML private TextField authField;
	
	@FXML private Button btnAuth;
	
	@FXML private void btnAuthOnMouseClick() throws MessagingException{
		progresInd.setVisible(true);
			
			//Hide Auth field and button
			authField.setVisible(false);
			btnAuth.setVisible(false);
			
			//get inserted code
			String code = authField.getText();
			
			//send code to authorization function
			new connectGmail().PreMain(code);
			
			try {
				MessagesList = new connectGmail().showMessages();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!MessagesList.isEmpty()){
				//Fill email listView with from and subject	
				ListView.setItems(FillFromSubject());
				
				//Print Inbox messages count
				Integer CountMails = MessagesList.size();
				String ss = "Inbox: " + Integer.toString(CountMails);
				CountMail.setText(ss);

			}
			else {
				ListView.setId("No messages!");

				//Show start Auth button
				btnStartAuthToGmail.setVisible(false);	
			}
		
		//Stop progress
		progresInd.setVisible(false);
		
		//Set refresh visible
		btnRefresh.setVisible(true);
		picRefresh.setVisible(true);
	}
	
	
	@FXML public void ListViewOnMouseClicked(MouseEvent mouseEvent) throws IOException, InterruptedException, MessagingException {
		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
		String EMcontent = null;
		String EmailFrom = null;
	       if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	           //open email on browser 
	    	   if(mouseEvent.getClickCount() == 3){
	    			String EmailNo = null;

	    			for(ArrayList<Philosopher> MessageList : MessagesList){
	    		    	for(Philosopher message : MessageList){
	    		    		if(message.No.equals(xMail)){
	    		    			EmailNo = (message.id) ;
	    		    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
	    		    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
	    		    		}
	    		    	}
	    			}
	    			
	    			EmailFrom = ListView.getSelectionModel().getSelectedItem();
	    			System.out.println(EmailFrom);
	    			if(EmailFrom.contains("!")){ //If email is unread then after opening it it becomes read -> list is refreshed!
//		    			wait 3 seconds to do next step!
		    			TimeUnit.SECONDS.sleep(5);
		    			btnRefreshOnClicked();
	    			}
	            }
	            //Show content -> new functions 
	            else if(mouseEvent.getClickCount() == 2){
	            	
	            	//set content box visible
	            	EmileContent.setVisible(true);
	            	
	            	for(ArrayList<Philosopher> MessageList : MessagesList){
	            		for(Philosopher Cont : MessageList){
	            			if(Cont.No.equals(xMail)){
	            				EMcontent = (Cont.EMcontent);
	            			}
	            		}
	            	}
	            }
	        }	
		EmileContent.setText(EMcontent);
	}
	
	
	@FXML
	public void EmileContentMouseClicked(MouseEvent mouseEvent){
//    	//set content box visible
//    	EmileContent.setVisible(false);
//    	//Set email text
//    	EmileContent.setText(null);
	}
	
	@FXML
	public void ListViewMousePressed(MouseEvent mouseEvent){
//		mouseEvent.getTarget();
//		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
//		String EMcontent = null;
//    	for(ArrayList<Philosopher> MessageList : MessagesList){
//    		for(Philosopher Cont : MessageList){
//    			if(Cont.No.equals(xMail)){
//    				EMcontent = (Cont.EMcontent);
//    			}
//    		}
//    	}
//    	//set content box visible
//    	EmileContent.setVisible(true);
//    	//Set email text
//    	EmileContent.setText(EMcontent);
	}
	
	@FXML
	public void ListViewMouseReleased(MouseEvent mouseEvent){
//    	//set content box visible
//    	EmileContent.setVisible(false);
//    	//Set email text
//    	EmileContent.setText(null);
	}
	
	
	@FXML public void btnRefreshOnClicked() throws MessagingException {
		System.out.println("RR");
		//progress starts
		progresInd.setVisible(true);
		
		try {
			MessagesList = new connectGmail().showMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Fill email listView with from and subject	
		ListView.setItems(FillFromSubject());
		
		//stop progress
		progresInd.setVisible(false);
	}
	
	
	@FXML public void ListViewOnSwipeUp(KeyEvent e) throws IOException {
		//Open current email on gmail.com browser
		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
		if(KeyEvent.KEY_PRESSED != null){
			String EmailNo = null;
			for(ArrayList<Philosopher> MessageList : MessagesList){
		    	for(Philosopher message : MessageList){
		    		if(message.No.equals(xMail)){
		    			EmailNo = (message.id) ;
		    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
		    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
		    		}
		    	}
			}
		}
		 e.consume();
	}
	
	//EmailContent
	@FXML 
	public ImageView EmailContImg;
	
	@FXML 
	public void EmileContentOnSwipeUP(SwipeEvent e) {
		//Open current email on gmail.com browser
		System.out.println("SWIPE_UP tipe: " + e.getEventType());
		e.consume();
	}
	
	@FXML
	public void EmileContentOnSwipeRight(SwipeEvent e) {
		//Clear content
		EmileContent.setText(null);
		EmileContent.setVisible(false);
		
		e.consume();
	}
	
	
	//Get INBOX list of email (from subject)
	public ObservableList<String> FillFromSubject(){
		// arrayList to print into ListView
		ArrayList<String> FromSubject = new ArrayList<String>();
		for(ArrayList<Philosopher> MessageList : MessagesList){
	
			for(Philosopher ff : MessageList){
	    		String frSu = null;
	    		String unread = null;
	    		for(String flag : ff.Flags){
	    			if(flag.contains("UNREAD")){
	    				unread = "! ";
	    			}
	    			else unread = "";
	    		}
	    		frSu = (unread + ff.From + "   " + ff.Subject) ;
	    		FromSubject.add(frSu);
	    	}
		}
		
		ObservableList<String> fromAndSubject = FXCollections.observableArrayList(FromSubject);
		return fromAndSubject;
		
	}
	
	//Lock
		@FXML private ImageView btnpicLock;
		@FXML private Button btnLock;
		@FXML public void lockDesctop(MouseEvent event) throws Exception{
			System.out.print("Lock");	
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
			Runtime.getRuntime().exec(lockDesctop);
		}
		
		
		//ShDown
		@FXML private ImageView btnpicDown;
		@FXML private Button btnDown;
		@FXML public void openDown(MouseEvent event) throws Exception{
			System.out.print("CMD");	
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
			Runtime.getRuntime().exec(lockDesctop);
		}
		
	
	/*
	 * End of Gmail section
	 */	 


}


