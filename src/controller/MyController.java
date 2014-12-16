package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.mail.MessagingException;

import twitter.TwitterList;
import twitter.TwitterTimeline;
import winAPI.Window;
import GmailAPI.Philosopher;
import GmailAPI.connectGmail;


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

		//Set UP panel as unvisible
		UPPan.setVisible(false);

	//Fill ListViewTwitter with Tweets
		addTwittStreem();

	}

/*
 * For Twitter
 */
	public static int SizeTweettsList;
	@FXML private ScrollPane scrolPan;
	@FXML private GridPane gridTwitt;


	public void addTwittStreem() {

			ArrayList<ArrayList<TwitterList>> TweettsList = new ArrayList<ArrayList<TwitterList>>();
			ArrayList<String> TweetStreemList = new ArrayList<String>();
			TweettsList = new TwitterTimeline().TwittMain();

			//How many tweets
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

		    	        	//To not work when Windows appears on right side
		    	    		if(TWPan.isVisible() || !TWPan.isMouseTransparent())
		    	    		{
				    			try {
									java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlTweet));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    	    		}

		    	            event.consume();
		    	        }
		    	});

		    		gridTwitt.addRow(x, tt);
		    		x+=1;
		    		}
		    	}
		}


	private static double valueScrollPan = 0.0;

	@FXML
	public void scrolPanOnSwipeUp(SwipeEvent e){
		if(TWPan.isVisible() || !TWPan.isMouseTransparent())
		{
			if(valueScrollPan>0.0){
				valueScrollPan -= 0.1;
				scrolPan.setVvalue(valueScrollPan);
			}
		}

		 e.consume();
	}

	@FXML
	public void scrolPanOnSwipeDown(SwipeEvent e){
		if(TWPan.isVisible() || !TWPan.isMouseTransparent())
		{
			valueScrollPan += 0.1;
			scrolPan.setVvalue(valueScrollPan);
		}
		e.consume();
	}


/*
 * Section for Gmail
 */
	@FXML private Pane GMPan;
	@FXML private Pane TWPan;
	@FXML private Pane UPPan;
	@FXML private Pane panShort;
	@FXML private GridPane mailGrid;
	@FXML private ScrollPane EmailListGrid;

	@FXML private ProgressIndicator progresInd;
	@FXML private Label CountMail;

	@FXML private ImageView picRefresh;
	@FXML private ImageView EmailContPic;

	@FXML private Button btnRefresh;
	@FXML private Button btnStartAuthToGmail;
	@FXML private Button btnAuth; //To autorize when code is in

	@FXML private TextField authField;//To give an authorization code and start authorization
	@FXML private TextArea EmileContent;

	//To start authorization
	@FXML public void btnStartAuthOnMouseClick(MouseEvent e) throws Exception {

		if(GMPan.isVisible() || !GMPan.isMouseTransparent()){
			//connect to gmail.com start
			new connectGmail().authorize();
			//Hide start Auth button
			btnStartAuthToGmail.setVisible(false);

			//Set to visible Auth field and Auth button
			btnAuth.setVisible(true);
			authField.setVisible(true);

			progresInd.setVisible(true);
		}
		e.consume();
	}


	public int mailSize = 0;

	@FXML private void btnAuthOnMouseClick(MouseEvent e) throws MessagingException{
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
				//Hide Auth field and button
				authField.setVisible(false);
				btnAuth.setVisible(false);

				//get inserted code
				String code = null;
				code = authField.getText();

				System.out.println("code: "+code);

				if(code.equals(null) || code.equals("") || code.isEmpty() || code.length() < 30)
				{
					btnStartAuthToGmail.setVisible(false);
					authField.setVisible(false);
					progresInd.setVisible(false);
					btnStartAuthToGmail.setVisible(true);
				}
				else
				{
				//send code to authorization function
					new connectGmail().PreMain(code);

					try {
						MessagesList = new connectGmail().showMessages();
					} catch (IOException eo) {
						// TODO Auto-generated catch block
						eo.printStackTrace();
					}

					if(!MessagesList.isEmpty())
					{
						//Fill email Email List Grid with from and subject
						//ListView.setItems(FillFromSubject()); //old one
						fillEmails();

						//Print Inbox messages count
						Integer CountMails = MessagesList.size();
						String ss = "Inbox: " + Integer.toString(CountMails);

						Label MailCount = new Label();
						MailCount.setText(ss);
						mailGrid.addRow(0, MailCount);

					}
					else
					{
						Label NoMessages = new Label();
						NoMessages.setText("No messages!");
						mailGrid.addRow(0, NoMessages);

						//Show start Auth button
						btnStartAuthToGmail.setVisible(false);
						btnRefresh.setVisible(false);
					}

					//Stop progress
					progresInd.setVisible(false);

					//Set refresh visible
					btnRefresh.setVisible(true);
					picRefresh.setVisible(true);
				}
		}
		e.consume();
	}


	private static double valueScrolEmailsGrid = 0.0;

	@FXML
	public void ListEmailOnSwipeDown(SwipeEvent e){
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			valueScrolEmailsGrid += 0.1;
			EmailListGrid.setVvalue(valueScrolEmailsGrid);
		}
		e.consume();
	}

	@FXML public void ListEmailOnSwipeUp(SwipeEvent e) throws IOException, InterruptedException, MessagingException {
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			if(valueScrolEmailsGrid>0.0){
				valueScrolEmailsGrid -= 0.1;
				EmailListGrid.setVvalue(valueScrolEmailsGrid);
			}
		}
		e.consume();
	}

	//Refresh Emails
	@FXML public void btnRefreshOnClicked(MouseEvent e) throws MessagingException {
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			//progress starts
			progresInd.setVisible(true);

			try {
				MessagesList = new connectGmail().showMessages();
			} catch (IOException eo) {
				// TODO Auto-generated catch block
				eo.printStackTrace();
			}

			//Fill email List grid with from and subject
			fillEmails();

			//stop progress
			progresInd.setVisible(false);
		}
		e.consume();
	}


	public String EmailLink = null; //to store emails link to what is onclicked

	//EmailContent
	@FXML
	public void EmileContentPicOnSwipeUP() throws IOException { //how to get where is clicked
		//Open current email on gmail.com browser
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			if (EmailLink != null)
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(EmailLink));
		}
	}

	@FXML public void EmileContentPicOnSwipeRight(SwipeEvent e) {
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			//Clear content
			EmileContent.setText(null);
			EmileContent.setVisible(false);
			EmailContPic.setVisible(false);

			EmailLink = null; //when close email content then set link to null
		}
		e.consume();
	}

	//Test
	@FXML void EmileContentPicOnClick(MouseEvent e){
		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		{
			EmileContent.setText(null);
			EmileContent.setVisible(false);
			EmailContPic.setVisible(false);

			EmailLink = null; //when close email content then set link to null
		}
		e.consume();
	}



	//Print Email on list
	public void fillEmails(){

		Integer x = 1;
		String FromSubj = null;

		for(ArrayList<Philosopher> MessageList : MessagesList){

			for(Philosopher ff : MessageList){

		    		String unread = null;
		    		for(String flag : ff.Flags){
		    			if(flag.contains("UNREAD")){
		    				unread = "* ";
		    			}
		    			else unread = "";
		    		}
		    		FromSubj = (unread + ff.From + " \n" + ff.Subject) ;

		    		String EmContent = ff.EMcontent;

		    		//create an text area and add it on grid new row
		    		TextArea tt = new TextArea(FromSubj);
		    		tt.setPrefHeight(50);
		    		tt.setWrapText(true);
		    		tt.setEditable(false);
		    		tt.setMouseTransparent(false);
		    		tt.setId(Integer.toString(x));

		    		//Tap and open Email content
		    		tt.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    	        @Override public void handle(MouseEvent e) {
		    	    		if(GMPan.isVisible() || !GMPan.isMouseTransparent())
		    	    		{
			    	        	//set content box visible
			    	        	EmileContent.setVisible(true);
			    	        	EmailContPic.setVisible(true);
			    	        	//Set email text
			    	        	EmileContent.setText(EmContent);

			    	        	//Store globally the emails link
			    	        	EmailLink = "https://mail.google.com/mail/u/0/#inbox/"+ff.id;
		    	    		}
		    	        	e.consume();
		    	        }
		    		});

		    		mailGrid.addRow(x, tt);
		    		x+=1;
			}
		}
	}


/*
* End of Gmail section
*
*/

/*
 * Extra buttons
 */

	//Lock
		@FXML private ImageView btnpicLock;
		@FXML private Button btnLock;
		@FXML public void lockDesctop(MouseEvent e) throws Exception{
			System.out.print("Lock");
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
//			Runtime.getRuntime().exec(lockDesctop);

			e.consume();
		}


		//ShDown
		@FXML private ImageView btnpicDown;
		@FXML private Button btnDown;
		@FXML public void openDown(MouseEvent e) throws Exception{
			System.out.print("CMD");
			String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
//			Runtime.getRuntime().exec(lockDesctop);

			e.consume();
		}


/*
 * End of Extra buttons
*/

/*
 * Functions to move Right side(Twitter and Gmail) UP or to Right side again
 */
		@FXML private Button RightUP;
		@FXML private Button RightToRight;

public void RightUPFunct(){
	//Set Right Gmail and Twitter unvisible
	GMPan.setVisible(false);
	GMPan.setMouseTransparent(true);
	GMPan.setDisable(true);

	TWPan.setVisible(false);
	TWPan.setMouseTransparent(true);
	TWPan.setDisable(true);

	//Set Up panel visible
	UPPan.setVisible(true);
}

public void RightToRightFunct(){
	//Set Right panel Gmail and Twitter visible
	GMPan.setVisible(true);
	GMPan.setMouseTransparent(false);
	GMPan.setDisable(false);

	TWPan.setVisible(true);
	TWPan.setMouseTransparent(false);
	TWPan.setDisable(false);
	//Hide Up panel
	UPPan.setVisible(false);
}


//test Button functions on click

@FXML public void RightUPClick(){
	RightUPFunct();
}
@FXML public void RightToRightClick(){
	RightToRightFunct();
}

	/*
	 * Windows handling in gesture events
	 * For each gesture event the corresponding function
	 * is called
	 * @paramSwipeEvent  type of swipe event
	 */
		Window window = new Window();
		@FXML private Pane swipeWindow;

		@FXML public void windowHanding(SwipeEvent e){

			String side;
			EventType<SwipeEvent> swipeType = e.getEventType();
			String title = "NGUI";


			if(swipeType == SwipeEvent.SWIPE_DOWN){
				System.out.println("SWIPE DOWN");
				side = "LEFT";
				window.moveWindowtoTable(title,side);

			}else if(swipeType == SwipeEvent.SWIPE_UP){

				if(e.getX() >= 0 && e.getX() < 600){
					side = "LEFT";
				}else
					side = "RIGHT";

				if(side == "RIGHT"){
					RightToRightFunct();
				}

				window.moveWindowtoDesktop(side);

			}else if(swipeType == SwipeEvent.SWIPE_RIGHT){

				System.out.println(swipeType);
				side = "RIGHT";
				RightUPFunct();
				window.moveWindowtoTable(title,side);

			}

		}

		@FXML private Pane swipeScrollWindow;

		@FXML public void scrollHanding(SwipeEvent e){

			/*EventType<SwipeEvent> swipeType = e.getEventType();

			if(swipeType == SwipeEvent.SWIPE_DOWN){
				System.out.println("scroll down ");
				window.scrollWindowDown();
			}else
				System.out.println("scroll up ");
				window.scrollWindowUp();*/

		}







}


