package twitter;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterTimeline {
	/**
	 * Gets the home timeline of the user whose credentials are in the
	 * twitter4j.properties file.
	 * @return ArrayList of ArrayList of tweets that can be used in JavaFX
	 */
	public ArrayList<ArrayList<TwitterList>> TwittMain() {
		Twitter tweetter = TwitterFactory.getSingleton();
		ArrayList<ArrayList<TwitterList>> TweetsList = new ArrayList<ArrayList<TwitterList>>();
		Integer No = 0;
		try {
			// Timeline of the user whose keys are in twitter4j.properties
			ResponseList<Status> result = tweetter.getHomeTimeline();
			for (Status status : result) {
				ArrayList<TwitterList> TweetList = new ArrayList<>();
				
				String link = ( "https://twitter.com/" + status.getUser().getScreenName() + "/status/"+ status.getId());
				
				TweetList.add(new TwitterList(No, link, status.getText(), 
						status.getUser().getName(), status.getUser().getScreenName(), status.getCreatedAt()));
				No ++;
				
				TweetsList.add(TweetList);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return TweetsList;
	}
}
