package tuio;

import TUIO.TuioClient;
import TUIO.TuioListener;


public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final TuioListener listener = new Tracking();

		TuioClient client = new TuioClient(3333);

		client.addTuioListener(listener);

		client.connect();
		System.out.println(client.isConnected());
		System.out.println(client.getTuioCursors().size());
	}

}
