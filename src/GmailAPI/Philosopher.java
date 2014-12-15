package GmailAPI;

import java.util.List;

/**
 * Holds one email.
 */
public class Philosopher {
    public Integer No;
	public String id;
    public String Snippet;
    public String Subject;
    public String From;
    public String To;
    public String Received;
    public String EMcontent;
    public List<String> Flags;

    public Philosopher(Integer No, String id, String Snippet, String Subject, String From, String To, String Received, String EMcontent, List<String> Flags) {
    	this.No = No;
    	this.id = id;
		this.Snippet = Snippet;
		this.Subject = Subject;
		this.From = From;
		this.To = To;
		this.Received = Received;
		this.EMcontent = EMcontent;
		this.Flags = Flags;
    }

    public String toString() {
	return "No = " + this.No + ", id = " + this.id + ", Snippet = " + this.Snippet + ", Subject = " + this.Subject + ", From = " + this.From
			+ ", To = " + this.To + ", Received = " + this.Received +", EMcontent" + EMcontent + ", Flags" + this.Flags;
    }
}