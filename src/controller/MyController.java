package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.mail.MessagingException;

import twitter.TwitterList;
import twitter.TwitterTimeline;
import winAPI.Window;
import GmailAPI.Philosopher;
import GmailAPI.connectGmail;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


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
	public static int SizeTweettsList;
	
	@FXML private GridPane gridTwitt;
	
	public void addTwittStreem() {
		ArrayList<ArrayList<TwitterList>> TweettsList = new ArrayList<ArrayList<TwitterList>>();
		ArrayList<String> TweetStreemList = new ArrayList<String>();
		TweettsList = new TwitterTimeline().TwittMain();
		
		//How meny tweets
		SizeTweettsList = TweettsList.size();
		
		Integer x = 1;
		
		for(ArrayList<TwitterList> TweettList : TweettsList){
	    	for(TwitterList tweet : TweettList){
	    		
				@SuppressWarnings("deprecation")
				String aa = tweet.date.getDay() + "/" +  tweet.date.getMonth() + "/" + tweet.date.getYear() + " " +
						tweet.date.getHours() + ":" + tweet.date.getMinutes() +
						"  @" + tweet.screenName + "\n" + tweet.text;
				
				TweetStreemList.add(aa);
	    		TextArea tt = new TextArea(aa);
	    		tt.setPrefHeight(80);
	    		tt.setWrapText(true);
	    		tt.setEditable(false);
	    		tt.setMouseTransparent(false);
	    		tt.setId(Integer.toString(x));
	    		
	    		//Tweet link
	    		String urlTweet = tweet.id;	    		
	    		
	    		//Tap on Tweet to open it on web
	    		tt.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	        @Override public void handle(MouseEvent event) {
	    	        	
		    			try {
							java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlTweet));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
	    	            event.consume();
	    	        }
	    	}); 
	    		
	    		gridTwitt.addRow(x, tt);
	    		x+=1;
	    		}
	    	}
		}
	
	@FXML private ScrollPane scrolPan;
	
	//test
	@FXML private Button up;
	@FXML private Button down;
	
	private static double valueScrollPan = 0.0;
	//test
	@FXML 
	public void swipeup(MouseEvent e){
		valueScrollPan -= 0.1;
		scrolPan.setVvalue(valueScrollPan);

		 e.consume();
	}
	//test
	@FXML 
	public void swipedown(MouseEvent e){
		valueScrollPan += 0.1;
		scrolPan.setVvalue(valueScrollPan);
		e.consume();
	}
	
	
	@FXML
	public void scrolPanOnSwipeUp(SwipeEvent e){
		valueScrollPan -= 0.1;
		scrolPan.setVvalue(valueScrollPan);

		 e.consume();
	}
	
	
	@FXML 
	public void scrolPanOnSwipeDown(SwipeEvent e){
		valueScrollPan += 0.1;
		scrolPan.setVvalue(valueScrollPan);
		e.consume();
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
	@FXML private ImageView EmailContPic;
	
	//To start authorization
	@FXML private Button btnStartAuthToGmail;
	
	@FXML public void btnStartAuthOnMouseClick(MouseEvent e) throws Exception {		
		//connect to gmail.com start
		new connectGmail().authorize();
		//Hide start Auth button
		btnStartAuthToGmail.setVisible(false);
		
		//Set to visible Auth field and Auth button
		btnAuth.setVisible(true);
		authField.setVisible(true);
		
		e.consume();
	}
	
	
	//To give an authorization code and start authorization
	@FXML private TextField authField;
	
	@FXML private Button btnAuth;
	
	public int mailSize;
	
	@FXML private void btnAuthOnMouseClick(MouseEvent e) throws MessagingException{
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
			} catch (IOException eo) {
				// TODO Auto-generated catch block
				eo.printStackTrace();
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

		e.consume();
	}
	
	
//	@FXML public void ListViewOnMouseClickedd(MouseEvent mouseEvent) throws IOException, InterruptedException, MessagingException {
//		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
//		String EMcontent = null;
//		String EmailFrom = null;
//	       if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
//	           //open email on browser 
//	    	   if(mouseEvent.getClickCount() == 3){
//	    			String EmailNo = null;
//
//	    			for(ArrayList<Philosopher> MessageList : MessagesList){
//	    		    	for(Philosopher message : MessageList){
//	    		    		if(message.No.equals(xMail)){
//	    		    			EmailNo = (message.id) ;
//	    		    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
//	    		    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
//	    		    		}
//	    		    	}
//	    			}
//	    			
//	    			EmailFrom = ListView.getSelectionModel().getSelectedItem();
//	    			System.out.println(EmailFrom);
//	    			if(EmailFrom.contains("!")){ //If email is unread then after opening it it becomes read -> list is refreshed!
////		    			wait 3 seconds to do next step!
//		    			TimeUnit.SECONDS.sleep(5);
//		    			btnRefreshOnClicked();
//	    			}
//	            }
//	            //Show content -> new functions 
//	            else if(mouseEvent.getClickCount() == 2){
//	            	
//	            	//set content box visible
//	            	EmileContent.setVisible(true);
//	            	
//	            	for(ArrayList<Philosopher> MessageList : MessagesList){
//	            		for(Philosopher Cont : MessageList){
//	            			if(Cont.No.equals(xMail)){
//	            				EMcontent = (Cont.EMcontent);
//	            			}
//	            		}
//	            	}
//	            }
//	        }	
//		EmileContent.setText(EMcontent);
//	}
	
	
	
	@FXML
	public void ListViewOnMouseClicked(MouseEvent mouseEvent){
		mouseEvent.getTarget();
		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
		String EMcontent = null;
    	for(ArrayList<Philosopher> MessageList : MessagesList){
    		for(Philosopher Cont : MessageList){
    			if(Cont.No.equals(xMail)){
    				EMcontent = (Cont.EMcontent);
    			}
    		}
    	}
    	//set content box visible
    	EmileContent.setVisible(true);
    	//Set email text
    	EmileContent.setText(EMcontent);
		EmailContPic.setVisible(true);
    	
		mouseEvent.consume();
	}
	
	private static int scrolValListView = 0;
	
	//for test
	@FXML Button listup;
	@FXML Button listdown;
	@FXML
	public void modeup(MouseEvent e){
		if(scrolValListView>=1){
			scrolValListView -=1;
			ListView.scrollTo(scrolValListView);
		}
		e.consume();
	}
	@FXML
	public void movedown(MouseEvent e){
		scrolValListView+=1;
		ListView.scrollTo(scrolValListView);
		e.consume();
	}
	
	
	@FXML
	public void ListViewOnSwipeDown(SwipeEvent e){
		scrolValListView+=1;
		ListView.scrollTo(scrolValListView);
		e.consume();
	}
	
	@FXML public void ListViewOnSwipeUp(SwipeEvent e) throws IOException, InterruptedException, MessagingException {
		if(scrolValListView >= 1){
			scrolValListView -=1;
			ListView.scrollTo(scrolValListView);
		}
		e.consume();
		
//		//Open current email on gmail.com browser
//		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
//			String EmailNo = null;
//			for(ArrayList<Philosopher> MessageList : MessagesList){
//		    	for(Philosopher message : MessageList){
//		    		if(message.No.equals(xMail)){
//		    			EmailNo = (message.id) ;
//		    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
//		    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
//		    		}
//		    	}
//			}
//			//If opened email is not read then refresh the
//			String EmailFrom = null;
//			EmailFrom = ListView.getSelectionModel().getSelectedItem();
//			System.out.println(EmailFrom);
////			if(EmailFrom.contains("!")){ //If email is unread then after opening it it becomes read -> list is refreshed!
//////    			wait 5 seconds to do next step!
////    			TimeUnit.SECONDS.sleep(5);
////    			btnRefreshOnClicked();
////			}
	}
	
	//Refresh Emails
	@FXML public void btnRefreshOnClicked(MouseEvent e) throws MessagingException {
		System.out.println("RR");
		//progress starts
		progresInd.setVisible(true);
		
		try {
			MessagesList = new connectGmail().showMessages();
		} catch (IOException eo) {
			// TODO Auto-generated catch block
			eo.printStackTrace();
		}

		//Fill email listView with from and subject	
		ListView.setItems(FillFromSubject());
		
		//stop progress
		progresInd.setVisible(false);
		
		e.consume();
	}
	
	
	
	//EmailContent
	@FXML 
	public void EmileContentPicOnSwipeUP() throws IOException {
		//Open current email on gmail.com browser
		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
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
//		e.consume();
	}
	
	@FXML
	public void EmileContentPicOnSwipeRight(SwipeEvent e) {
		//Clear content
		EmileContent.setText(null);
		EmileContent.setVisible(false);
		EmailContPic.setVisible(false);
		e.consume();
	}
	//Test
	@FXML void EmileContentPicOnClick(MouseEvent e){
		EmileContent.setText(null);
		EmileContent.setVisible(false);
		EmailContPic.setVisible(false);
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
		@FXML public void lockDesctop(MouseEvent e) throws Exception{
			System.out.print("Lock");	
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
			Runtime.getRuntime().exec(lockDesctop);
			
			e.consume();
		}
		
		
		//ShDown
		@FXML private ImageView btnpicDown;
		@FXML private Button btnDown;
		@FXML public void openDown(MouseEvent e) throws Exception{
			System.out.print("CMD");	
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
			Runtime.getRuntime().exec(lockDesctop);
			
			e.consume();
		}
		
	
/*
 * End of Gmail section
*/	 
    
	/*
	 * Windows handling in gesture events
	 * For each gesture event the corresponding function 
	 * is called
	 * @paramSwipeEvent  type of swipe event
	 */
		@FXML public void windowHanding(SwipeEvent e){
			
			Window window = new Window();
			EventType<SwipeEvent> swipeType = e.getEventType();
			
			String title = "NGUI";
			
			String side;
						
			if(swipeType == SwipeEvent.SWIPE_DOWN){
				
				side= "LEFT";
				System.out.println("SWIPE DOWN");
				
				/*if (e.getX() >= 0 && e.getX() < 0.5){
					side = "LEFT";
				}else
					side="RIGHT";*/
				
				window.moveWindowtoTable(title, side);
				
			}else if(swipeType == SwipeEvent.SWIPE_UP){
				window.moveWindowtoDesktop();
			}else{
				
				System.out.println(swipeType);
				//window.deleteWindow();
			}
				
			
			
			
		}
		

}


