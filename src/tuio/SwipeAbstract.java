package tuio;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import TUIO.TuioCursor;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.SwipeEvent;

/**
 * Abstract class to hold the common parts of catching a Swipe event in
 * different directions.
 */
@SuppressWarnings("restriction")
public abstract class SwipeAbstract extends Gesture {
	protected Map<Integer,Float> start_x = new HashMap<Integer, Float>();
	protected Map<Integer,Float> start_y = new HashMap<Integer, Float>();
	protected Map<Integer,Float> previous_x = new HashMap<Integer, Float>();
	protected Map<Integer,Float> previous_y = new HashMap<Integer, Float>();
	protected Map<Integer,Boolean> still_good = new HashMap<Integer, Boolean>();
	protected Map<Integer,Long> end_time   = new HashMap<Integer, Long>();
	/**  Timeout before checking if the pressing really ended */
	protected static final long TIMEOUT = 400;
	/** Minimum distance before a swipe is considered triggered */
	protected static final float MIN_DISTANCE = (float) 0.2;

	public SwipeAbstract(Node p) {
		super(p);
	}

	/**
	 * Used during update events to see if the movement is heading the right
	 * direction.
	 */
	abstract boolean goingGood(TuioCursor arg);
	
	/**
	 * Used during remove event to see if the end point is far enough from
	 * the source.
	 */
	abstract boolean farEnough(TuioCursor arg);
	
	/**
	 * @return The type of swipe event that the subclass should trigger
	 */
	abstract EventType<SwipeEvent> getSwipeType();
	
	/**
	 * Adds new cursor and checks whether we were already following it.
	 * As mentioned elsewhere, needed due to the flaky nature of add and
	 * remove events.
	 */
	@Override
	public void addTuioCursor(TuioCursor arg) {
		if (isDeadZone(arg)) return;
		if (start_x.containsKey(arg.getCursorID())) {
			end_time.remove(arg.getCursorID());
		} else {
			start_x.put(arg.getCursorID(), arg.getX());
			start_y.put(arg.getCursorID(), arg.getY());
			previous_x.put(arg.getCursorID(), arg.getX());
			previous_y.put(arg.getCursorID(), arg.getY());
			still_good.put(arg.getCursorID(), true);
		}
	}

	/**
	 * Waits a bit, then checks if the cursor did not reappear in the meantime.
	 * This waiting has to be done due to the flaky nature of add/remove
	 * events.
	 */
	@Override
	public void removeTuioCursor(TuioCursor arg) {
		class LocalThread extends Thread {
			public void run() {
				if (start_x.containsKey(arg.getCursorID())) {
					long local_end = System.currentTimeMillis();
					end_time.put(arg.getCursorID(), local_end);
					
					try {
						Thread.sleep(TIMEOUT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (end_time.containsKey(arg.getCursorID())
							&& end_time.get(arg.getCursorID()) == local_end) {
						// Cursor is really gone now
						if (farEnough(arg) && still_good.get(arg.getCursorID())) {
							eventTriggered(arg);
						}
						start_x.remove(arg.getCursorID());
						start_y.remove(arg.getCursorID());
						previous_x.remove(arg.getCursorID());
						previous_y.remove(arg.getCursorID());
						still_good.remove(arg.getCursorID());
						end_time.remove(arg.getCursorID());
					}
				}
			}
		}
		LocalThread l = new LocalThread();
		l.start();
	}
	
	/**
	 * Validate that we aren't deviating from our intended direction
	 */
	@Override
	public void updateTuioCursor(TuioCursor arg) {
		if (start_x.containsKey(arg.getCursorID())) {
			if (goingGood(arg)) {
				// Still going the right way
			} else {
				still_good.put(arg.getCursorID(), false);
			}
			previous_x.put(arg.getCursorID(), arg.getX());
			previous_y.put(arg.getCursorID(), arg.getY());
		}
	}
	
	/**
	 * Called when the swipe event is detected.
	 */
	private void eventTriggered(TuioCursor arg) {
		Point2D p = tuioXYtoJavaFXXY(arg.getX(), arg.getY());
		Node currenttarget = pickNodeBySceneXY(root, p.getX(), p.getY());
		Point2D p_local = currenttarget.sceneToLocal(p);
		SwipeEvent swipe = new SwipeEvent(
				getSwipeType(),
				p_local.getX(), p_local.getY(),
				p.getX(), p.getY(),
				false, false, false, false, true, 1, null);
		// Needed since events have to run in JavaFX application thread
		Platform.runLater(new Runnable() {
			public void run() {
				currenttarget.fireEvent(swipe);
			}
		});
	}
}
