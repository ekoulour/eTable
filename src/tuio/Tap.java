package tuio;

import java.util.HashMap;
import java.util.Map;

import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

/*
 * Question is: can we wait x milliseconds on remove to see if it is readded again?
 * Won't this cause very weird racing conditions under the assumption
 * "atomic" variable
 */
public class Tap implements TuioListener {
	private Map<Integer,Float> start_x = new HashMap<Integer, Float>();
	private Map<Integer,Float> start_y = new HashMap<Integer, Float>();
	private Map<Integer,Long> start_time = new HashMap<Integer, Long>();
	private Map<Integer,Long> end_time   = new HashMap<Integer, Long>();
	/** Timeout before checking if the pressing really ended */
	private static final long TIMEOUT = 400;
	/** Maximum hold length to consider a tap */
	private static final long MAX_TAP_TIME = 500;

	@Override
	public void addTuioCursor(TuioCursor arg) {
		if (end_time.containsKey(arg.getCursorID())) {
			end_time.remove(arg.getCursorID());
		}
		else {
			start_x.put(arg.getCursorID(), arg.getX());
			start_y.put(arg.getCursorID(), arg.getY());
			start_time.put(arg.getCursorID(), System.currentTimeMillis());
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
						// The cursor is really gone
						long hold_length = local_end - start_time.get(arg.getCursorID());
						if (hold_length < MAX_TAP_TIME && closeEnough(arg)) {
							System.out.println("Tap event!");
						}
						start_x.remove(arg.getCursorID());
						start_y.remove(arg.getCursorID());
						start_time.remove(arg.getCursorID());
						end_time.remove(arg.getCursorID());
					}
				}
			}
		}
		LocalThread l = new LocalThread();
		l.start();
	}

	@Override
	public void updateTuioCursor(TuioCursor arg0) {
		//TODO Should invalidate if moving too far
	}

	/* Makes sure current position is close enough to the start */
	private boolean closeEnough(TuioCursor arg) {
		return start_x.containsKey(arg.getCursorID())
			&& arg.getX() < start_x.get(arg.getCursorID()) + 0.1
			&& arg.getX() > start_x.get(arg.getCursorID()) - 0.1
			&& arg.getY() < start_y.get(arg.getCursorID()) + 0.1
			&& arg.getY() > start_y.get(arg.getCursorID()) - 0.1;
	}

	
	/* Unused */
	@Override
	public void removeTuioObject(TuioObject arg0) {
		return;
	}
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

}
