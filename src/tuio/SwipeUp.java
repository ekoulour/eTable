package tuio;

import java.util.HashMap;
import java.util.Map;

import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

/*
 * Keep in mind:
 * - Cursor in/out happens even if you hold finger still, don't rely on it
 * - As such, start time of a cursor is also unreliable if you don't do it
 *   yourself
 * - Instance of this class handles every TuioCursor id (meaning you have to
 *   manually keep track of what different IDs do)
 * - Idea is to have a different class for different gestures that we want to
 *   recognise, similar to what we did in the class Lode taught halfway
 *   November.
 */
public class SwipeUp implements TuioListener {
	private Map<Integer,Float> start_x = new HashMap<Integer, Float>();
	private Map<Integer,Float> start_y = new HashMap<Integer, Float>();
	private Map<Integer,Float> previous_x = new HashMap<Integer, Float>();
	private Map<Integer,Float> previous_y = new HashMap<Integer, Float>();
	private Map<Integer,Boolean> still_good = new HashMap<Integer, Boolean>();
	private Map<Integer,Long> end_time   = new HashMap<Integer, Long>();
	private static final long TIMEOUT = 400;
	private static final float MIN_DISTANCE = (float) 0.2;

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
						// Cursor is really gone
						float distance = arg.getX() - start_x.get(arg.getCursorID());
						if (distance > MIN_DISTANCE && still_good.get(arg.getCursorID())) {
							System.out.println("Swipe up event!");
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
	
	/* Validate that we aren't deviating from going upwards */
	@Override
	public void updateTuioCursor(TuioCursor arg) {
		if (start_x.containsKey(arg.getCursorID())) {
			if (arg.getX() > start_x.get(arg.getCursorID()) - 0.05
					&& arg.getX() > previous_x.get(arg.getCursorID()) - 0.05
					&& arg.getY() < start_y.get(arg.getCursorID()) + 0.1
					&& arg.getY() > start_y.get(arg.getCursorID()) - 0.1) {
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
	public void updateTuioObject(TuioObject arg0) {
		return;
	}
	@Override
	public void addTuioObject(TuioObject arg0) {
		return;
	}
	@Override
	public void refresh(TuioTime arg0) {
		return;
	}
	@Override
	public void removeTuioObject(TuioObject arg0) {
		return;
	}
}
