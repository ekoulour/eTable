package application;

public class Philosopher {
    public Integer No;
	public String id;
    public String Snippet;
    public String Subject;
    public String From;
    public String To;
    public String Received;

    public Philosopher(Integer No, String id, String Snippet, String Subject, String From, String To, String Received) {
    	this.No = No;
    	this.id = id;
		this.Snippet = Snippet;
		this.Subject = Subject;
		this.From = From;
		this.To = To;
		this.Received = Received;
    }

    public String toString() {
	return "No = " + this.No + ", id = " + this.id + ", Snippet = " + this.Snippet + ", Subject = " + this.Subject + ", From = " + this.From
			+ ", To = " + this.To + ", Received = " + this.Received;
    }
}