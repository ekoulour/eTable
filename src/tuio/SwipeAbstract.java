package tuio;

import java.util.HashMap;
import java.util.Map;

import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

/**
 * Abstract class to hold the common parts of catching a Swipe event in
 * different directions.
 */
public abstract class SwipeAbstract implements TuioListener {
	protected Map<Integer,Float> start_x = new HashMap<Integer, Float>();
	protected Map<Integer,Float> start_y = new HashMap<Integer, Float>();
	protected Map<Integer,Float> previous_x = new HashMap<Integer, Float>();
	protected Map<Integer,Float> previous_y = new HashMap<Integer, Float>();
	protected Map<Integer,Boolean> still_good = new HashMap<Integer, Boolean>();
	protected Map<Integer,Long> end_time   = new HashMap<Integer, Long>();
	protected static final long TIMEOUT = 400;
	protected static final float MIN_DISTANCE = (float) 0.2;

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
	 * Called when the swipe event is detected.
	 */
	abstract void eventTriggered(TuioCursor arg);
	
	/**
	 * Adds new cursor and checks whether we were already following it.
	 * As mentioned elsewhere, needed due to the flaky nature of add and
	 * remove events.
	 */
	@Override
	public void addTuioCursor(TuioCursor arg) {
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

	/* Unused */
	@Override
	public void updateTuioObject(TuioObject arg) {
		return;
	}
	@Override
	public void addTuioObject(TuioObject arg) {
		return;
	}
	@Override
	public void refresh(TuioTime arg) {
		return;
	}
	@Override
	public void removeTuioObject(TuioObject arg) {
		return;
	}
}
