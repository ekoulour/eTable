package tuio;

import TUIO.TuioClient;
import javafx.scene.Node;

/**
 * Used to bind different gesture recognisers to the JavaFX scene.
 */
@SuppressWarnings("restriction")
public class ConnectTuioToFX {
	public ConnectTuioToFX(Node n) {
		TuioClient client = new TuioClient(3333);

		client.addTuioListener(new Tap(n));
		client.addTuioListener(new SwipeUp(n));
		client.addTuioListener(new SwipeDown(n));
		client.addTuioListener(new SwipeLeft(n));
		client.addTuioListener(new SwipeRight(n));

		client.connect();

		if (client.isConnected())
			System.out.println("TUIO connected.");
	}
}
