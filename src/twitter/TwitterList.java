package twitter;

import java.util.Date;

public class TwitterList {
    public Integer No;
	public String id;
    public String text;
    public String userName;
    public String screenName;
    public Date date;


    public TwitterList(Integer No, String id, String text, String userName, String screenName, Date date) {
    	this.No = No;
    	this.id = id;
		this.text = text;
		this.userName = userName;
		this.screenName = screenName;
		this.date = date;
    }

    public String toString() {
	return "No = " + this.No + ", id = " + this.id + ", text = " + this.text + ", userName = " + 
			this.userName + ", screenName = " + this.screenName + ", date = " + this.date;
    }
}