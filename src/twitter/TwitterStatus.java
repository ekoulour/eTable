package twitter;

import java.util.Date;

/**
 * Data class to actually pass around twitter status.
 */
public class TwitterStatus {
	private String url;
	private String user;
	private String text;
	private Date date;
	public TwitterStatus(String user, String text, Date date, String url) {
		super();
		this.url = url;
		this.user = user;
		this.text = text;
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "[" + user + "] " + text;
	}
}
