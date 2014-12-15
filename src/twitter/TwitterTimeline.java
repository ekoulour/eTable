package twitter;

import java.util.ArrayList;
import java.util.List;

import GmailAPI.Philosopher;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterTimeline {
	/**
	 * Example that outputs the statuses. Useful when testing.
	 * @param args
	 * @return 
	 */
//	public static void main(String[] args) {
	public ArrayList<ArrayList<TwitterList>> TwittMain() {
		Twitter tweetter = TwitterFactory.getSingleton();
		ArrayList<ArrayList<TwitterList>> TweetsList = new ArrayList<ArrayList<TwitterList>>();
		Integer No = 0;
		try {
			// Timeline of the user whose keys are in twitter4j.properties
			ResponseList<Status> result = tweetter.getHomeTimeline();
			for (Status status : result) {
				ArrayList<TwitterList> TweetList = new ArrayList<>();
				
//				System.out.println("[" + status.getCreatedAt() + "] @" + status.getUser().getScreenName() + ":" + status.getText());
//				System.out.println("https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId());
			
				TweetList.add(new TwitterList(No, status.getUser().getScreenName(), status.getText(), 
						status.getUser().getName(), status.getUser().getScreenName(), status.getCreatedAt()));
				No ++;
				
				TweetsList.add(TweetList);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return TweetsList;
	}
	
	/**
	 * Uses the credentials in twitter4j.properties to get the time line of
	 * the user in question.
	 * @return List of most recent statuses appearing in the user's timeline.
	 */
	public List<TwitterStatus> getStatuses() {
		List<TwitterStatus> statuses = new ArrayList<TwitterStatus>();
		Twitter twitter = TwitterFactory.getSingleton();
		try {
			ResponseList<Status> result = twitter.getHomeTimeline();
			for (Status status : result) {
				statuses.add(new TwitterStatus(
						status.getUser().getScreenName(),
						status.getText(),
						status.getCreatedAt(),
						"https://twitter.com/"
								+ status.getUser().getScreenName()
								+ "/status/"
								+ status.getId()));
			}
			return statuses;
		} catch (TwitterException e) {
			System.out.println("Failed to get Timeline.");
			return new ArrayList<TwitterStatus>();
		}
	}
}
