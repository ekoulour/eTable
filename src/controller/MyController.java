package controller;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.CountMails;
import application.MailReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MyController implements Initializable {

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//To show mail from and sube=ject in viewList1
		ArrayList<String> fromSubject = new ArrayList<String>();
		fromSubject = new MailReader().ReadMails();
		ObservableList<String> fromAndSubject = FXCollections.observableArrayList(fromSubject);
		listView1.setItems(fromAndSubject);
		
		//Print total messages in text label countMailText
		String cc = new CountMails().Counts();
		countMailText.setText(cc);
	}
	
	@FXML public void openMailContent() {
		emailContent.setText("Test of email");
		
//		//listview riws id of click
		Integer xMail = listView1.getSelectionModel().getSelectedIndex();
		System.out.println(listView1.getSelectionModel().getSelectedIndex());
		
		ArrayList<String> aa = new MailReader().ReadContent(xMail);
		//emailContent.setText(aa);
//		String bb = toString(aa);
//		emailContent.setText(bb);
		
	}
	@FXML private TextArea emailContent;
	
	
	
	@FXML private Button btntest;
	@FXML public void clicktest(){

	}
	
	
	@FXML private Button btnFoo;
	
	@FXML private Label txtBar;
	
	public void changeText(ActionEvent event){
	System.out.print("click!");	
	txtBar.setText("You did it!");
	}

//______________________________________________________________________________________
	//ListView : Show From and subject in listview
	@FXML ListView<String> listView1;
	
	
	//Get Total message count and Unread message Count
	@FXML private Label countMailText; 
	
	//FireFox
	@FXML private ImageView btnpicFF;
	
	@FXML private Button btnFF;
	
	@FXML public void openFireff(MouseEvent event) throws Exception{
		System.out.print("Fox");	
//		String openFirefox = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
//		Runtime.getRuntime().exec(openFirefox);
		Runtime.getRuntime().exec(new String[]{"cmd", "/c","start firefox "});
	}
	
	//Chrome
	@FXML private ImageView btnpicChrme;
	
	@FXML private Button btnChrome;
	
	@FXML public void openChrome(MouseEvent event) throws Exception{
		System.out.print("Chrome");	
		//String openFirefox = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
		//Runtime.getRuntime().exec(openFirefox);
		Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "}); //chrome
	}
	
	//Lock
	@FXML private ImageView btnpicLock;
	
	@FXML private Button btnLock;
	
	@FXML public void lockDesctop(MouseEvent event) throws Exception{
		System.out.print("Lock");	
		String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
		Runtime.getRuntime().exec(lockDesctop);
	}


	//CMD
	@FXML private ImageView btnpicCMD;
	
	@FXML private Button btnCMD;
	
	@FXML public void openCMD(MouseEvent event) throws Exception{
		String cmd = "cmd.exe /c start cd C:\\windows";
		Runtime.getRuntime().exec(cmd); 
//		Runtime.getRuntime().exec("cmd.exe /c start"); 
	    System.out.print("CMD");
	}
	
	//Calculator
	@FXML private ImageView btnpicCalc;
	
	@FXML private Button btnCalc;
	
	@FXML public void openCalc(MouseEvent event) throws Exception{
		System.out.print("Calculator");	
		String openCalc = "C:\\Windows\\system32\\calc.exe";
		Runtime.getRuntime().exec(openCalc);
	}
	
	//Paint
	@FXML private ImageView btnpicPaint;
	
	@FXML private Button btnPaint;	

	@FXML public void openPaint(MouseEvent event) throws Exception{
		System.out.print("CMD");	
		String lockDesctop = "C:\\Windows\\system32\\mspaint.exe";
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
	
		
	//Show Desktop
	@FXML private ImageView btnpicMin;
	
	@FXML private Button btnMin;
	
	@FXML public void openMin(MouseEvent event){
	    try  
	    {  
	      Robot robot = new Robot();  
	      robot.keyPress(KeyEvent.VK_WINDOWS);  
	      robot.keyPress(KeyEvent.VK_D);  
	      robot.keyRelease(KeyEvent.VK_D);  
	      robot.keyRelease(KeyEvent.VK_WINDOWS);  
	    }  
	    catch(Exception e){e.printStackTrace();}
	
	}
	
	//openFacebook
	@FXML private ImageView btnpicFacebook;
	
	@FXML private Button btnFacebook;
	
	@FXML public void openFacebook(MouseEvent event) throws IOException{
		String facebook = "http://facebook.com";
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(facebook));
	}
	
	
	//openTwitter
	@FXML private ImageView btnpicTwitter;
	
	@FXML private Button btnTwitter;
	
	@FXML public void openTwitter(MouseEvent event) throws IOException{
		String twitter = "http://twitter.com";
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitter));
	}
	
}
