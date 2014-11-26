package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Philosopher;
import application.connectGmail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MyController implements Initializable {
	//Initialize ArrayList<ArrayList<Philosopher>>
	ArrayList<ArrayList<Philosopher>> MessagesList = new ArrayList<ArrayList<Philosopher>>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		EmileContent.setVisible(false);
		progresInd.setVisible(false);
	}
	
	@FXML private Pane panNotifications;
	@FXML private Pane panShort;

	@FXML private Label textCount;
	
	@FXML private ProgressIndicator progresInd;
	
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
	
	
	@FXML private ListView<String> ListView;
	
	@FXML private TextArea EmileContent;
	
	@FXML private ImageView picRefresh;
	@FXML private Button btnRefresh;
	
//	@FXML private TextField authField;
//	@FXML private Button btnAuth;
	@FXML private Button btnStartAuthToGmail;
	
	@FXML private void btnAuthOnMouseClick(){
//		try {
//			MessagesList = new connectGmail().showMessages();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		//Fill email listview with from and subject	
//		ListView.setItems(FillFromSubject());
	}
	
	@FXML public void btnStartAuthOnMouseClick(MouseEvent event) {
		//connect to gmail.com
		new connectGmail().PreMain();

		
		
		try {
			MessagesList = new connectGmail().showMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//Need to put progress bar here
		
		if(!MessagesList.isEmpty()){
			//Fill email listView with from and subject	
			ListView.setItems(FillFromSubject());
			
			//Hide button
			btnStartAuthToGmail.setVisible(false);
			
			//Print Inbox messages count
			Integer CountMail = MessagesList.size();
			String ss = "Inbox: " + Integer.toString(CountMail);
			textCount.setText(ss);
		}
		else {
			ListView.setId("No messages!");
		}
		
	}
	
	
	@FXML public void ListViewOnMouseClicked(MouseEvent mouseEvent) throws IOException {
		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
		String Snippet = null;
	       if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	           //open email on browser 
	    	   if(mouseEvent.getClickCount() == 2){
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
	            //Show content
	            else if(mouseEvent.getClickCount() == 1){
	            	
	            	//set content box visible
	            	EmileContent.setVisible(true);
	            	
	        		for(ArrayList<Philosopher> MessageList : MessagesList){
	        	    	for(Philosopher Snip : MessageList){
	        	    		if(Snip.No.equals(xMail)){
	        	    			Snippet = (Snip.Snippet) ;
	        	    		}
	        	    	}
	        		}
	            }
	        }	
		EmileContent.setText(Snippet);
	}
	
	
	@FXML public void btnRefreshOnClicked(MouseEvent event) {
		
		try {
			MessagesList = new connectGmail().showMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Fill email listView with from and subject	
		ListView.setItems(FillFromSubject());
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
	
	
	@FXML 
	public void EmileContentOnSwipeUP() {
		//Open current email on gmail.com browser
	}
	
	@FXML
	public void EmileContentOnSwipeRight(KeyEvent e) {
		//Clear content
		if(e.isShiftDown()){
			EmileContent.setText(null);
			EmileContent.setVisible(false);
		}
		e.consume();
	}
	
	
	public ObservableList<String> FillFromSubject(){
		// arrayList to print into ListView
		ArrayList<String> FromSubject = new ArrayList<String>();
		for(ArrayList<Philosopher> MessageList : MessagesList){
	    	for(Philosopher ff : MessageList){
	    		String frSu = (ff.From + "   " + ff.Subject) ;
	    		FromSubject.add(frSu);
	    	}
		}
		
		ObservableList<String> fromAndSubject = FXCollections.observableArrayList(FromSubject);
		return fromAndSubject;
		
	}
	
}
