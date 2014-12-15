package tuio;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import TUIO.TuioCursor;

/**
 * Recognises a tap event.
 */
@SuppressWarnings("restriction")
public class Tap extends Gesture {
	private Map<Integer,Float> start_x = new HashMap<Integer, Float>();
	private Map<Integer,Float> start_y = new HashMap<Integer, Float>();
	private Map<Integer,Long> start_time = new HashMap<Integer, Long>();
	private Map<Integer,Long> end_time   = new HashMap<Integer, Long>();
	/** Timeout before checking if the pressing really ended */
	private static final long TIMEOUT = 400;
	/** Maximum hold length to consider a tap */
	private static final long MAX_TAP_TIME = 500;

	public Tap(Node p) {
		super(p);
	}

	@Override
	public void addTuioCursor(TuioCursor arg) {
		if (isDeadZone(arg)) return;
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
							eventTriggered(arg);
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
	
	private void eventTriggered(TuioCursor arg) {
		Point2D p = tuioXYtoJavaFXXY(arg.getX(), arg.getY());
		Node currenttarget = pickNodeBySceneXY(root, p.getX(), p.getY());
		Point2D p_local = currenttarget.sceneToLocal(p);
		MouseEvent mouseclick = new MouseEvent(
				MouseEvent.MOUSE_CLICKED,
				p_local.getX(), p_local.getY(),
				p.getX(), p.getY(),
				MouseButton.PRIMARY, 1,
				false, false, false, false, true, false, true, true, false, true, null);
		currenttarget.fireEvent(mouseclick);
	}

	/**
	 * Makes sure current position is close enough to the start
	 * @param arg The Tuiocursor to compare to the starting value
	 */
	private boolean closeEnough(TuioCursor arg) {
		return start_x.containsKey(arg.getCursorID())
			&& arg.getX() < start_x.get(arg.getCursorID()) + 0.1
			&& arg.getX() > start_x.get(arg.getCursorID()) - 0.1
			&& arg.getY() < start_y.get(arg.getCursorID()) + 0.1
			&& arg.getY() > start_y.get(arg.getCursorID()) - 0.1;
	}
}
