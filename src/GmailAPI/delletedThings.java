package GmailAPI;






public class delletedThings {


//	@FXML public void ListViewOnMouseClickedd(MouseEvent mouseEvent) throws IOException, InterruptedException, MessagingException {
//	Integer xMail = ListView.getSelectionModel().getSelectedIndex();
//	String EMcontent = null;
//	String EmailFrom = null;
//       if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
//           //open email on browser
//    	   if(mouseEvent.getClickCount() == 3){
//    			String EmailNo = null;
//
//    			for(ArrayList<Philosopher> MessageList : MessagesList){
//    		    	for(Philosopher message : MessageList){
//    		    		if(message.No.equals(xMail)){
//    		    			EmailNo = (message.id) ;
//    		    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
//    		    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
//    		    		}
//    		    	}
//    			}
//
//    			EmailFrom = ListView.getSelectionModel().getSelectedItem();
//    			System.out.println(EmailFrom);
//    			if(EmailFrom.contains("!")){ //If email is unread then after opening it it becomes read -> list is refreshed!
////	    			wait 3 seconds to do next step!
//	    			TimeUnit.SECONDS.sleep(5);
//	    			btnRefreshOnClicked();
//    			}
//            }
//            //Show content -> new functions
//            else if(mouseEvent.getClickCount() == 2){
//
//            	//set content box visible
//            	EmileContent.setVisible(true);
//
//            	for(ArrayList<Philosopher> MessageList : MessagesList){
//            		for(Philosopher Cont : MessageList){
//            			if(Cont.No.equals(xMail)){
//            				EMcontent = (Cont.EMcontent);
//            			}
//            		}
//            	}
//            }
//        }
//	EmileContent.setText(EMcontent);
//}



//	//Get INBOX list of email (from subject)
//	public ObservableList<String> FillFromSubject(){
//		// arrayList to print into ListView
//		ArrayList<String> FromSubject = new ArrayList<String>();
//		for(ArrayList<Philosopher> MessageList : MessagesList){
//
//			for(Philosopher ff : MessageList){
//	    		String frSu = null;
//	    		String unread = null;
//	    		for(String flag : ff.Flags){
//	    			if(flag.contains("UNREAD")){
//	    				unread = "* ";
//	    			}
//	    			else unread = "";
//	    		}
//	    		frSu = (unread + ff.From + " \n" + ff.Subject) ;
//	    		FromSubject.add(frSu);
//	    	}
//		}
//
//		ObservableList<String> fromAndSubject = FXCollections.observableArrayList(FromSubject);
//		return fromAndSubject;
//
//	}



//	//Open current email on gmail.com browser
//	Integer xMail = ListView.getSelectionModel().getSelectedIndex();
//		String EmailNo = null;
//		for(ArrayList<Philosopher> MessageList : MessagesList){
//	    	for(Philosopher message : MessageList){
//	    		if(message.No.equals(xMail)){
//	    			EmailNo = (message.id) ;
//	    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
//	    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
//	    		}
//	    	}
//		}
//		//If opened email is not read then refresh the
//		String EmailFrom = null;
//		EmailFrom = ListView.getSelectionModel().getSelectedItem();
//		System.out.println(EmailFrom);
////		if(EmailFrom.contains("!")){ //If email is unread then after opening it it becomes read -> list is refreshed!
//////			wait 5 seconds to do next step!
////			TimeUnit.SECONDS.sleep(5);
////			btnRefreshOnClicked();
////		}



	//ListView.setItems(FillFromSubject()); //old one





//	@FXML
//	public void EmileContentPicOnSwipeUP() throws IOException { //how to get where is clicked
//		//Open current email on gmail.com browser
//
//		java.awt.Desktop.getDesktop().browse(java.net.URI.create(EmailLink));
//
////		Integer xMail = ListView.getSelectionModel().getSelectedIndex();
////		String EmailNo = null;
////		for(ArrayList<Philosopher> MessageList : MessagesList){
////	    	for(Philosopher message : MessageList){
////	    		if(message.No.equals(xMail)){
////	    			EmailNo = (message.id) ;
////	    			String urlGmail = "https://mail.google.com/mail/u/0/#inbox/"+EmailNo;
////	    			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlGmail));
////	    		}
////	    	}
////		}
//
//	}


//	@FXML
//	public void ListEmailOnMouseClickedaaaaaaaaaaaaaaaaaaaaaaaaaa(MouseEvent mouseEvent){
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
//		EmailContPic.setVisible(true);
//
//		mouseEvent.consume();
//	}


//	/**
//	 *	On mouse moving around it catches the mouse gesture and print out gesture name and coordinates
//	**/
//
//	@FXML
//        public void mouseHandler(MouseEvent mouseEvent) {
////            System.out.println(mouseEvent.getEventType() + "\n"
////                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
////                    + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
////                    + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());
//
//        }


//	@FXML public void lockDesctop(MouseEvent e) throws Exception{
//		System.out.print("Lock");
//		String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
////		Runtime.getRuntime().exec(lockDesctop);
//
//		e.consume();
//	}


//	@FXML public void openDown(MouseEvent e) throws Exception{
//		System.out.print("CMD");
//		String lockDesctop = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";
////		Runtime.getRuntime().exec(lockDesctop);
//
//		e.consume();
//	}
}
