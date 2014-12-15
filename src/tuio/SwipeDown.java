package tuio;

import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;
import TUIO.TuioCursor;

/**
 * Catches a swiping down of a user and handles accordingly.
 */
@SuppressWarnings("restriction")
public class SwipeDown extends SwipeAbstract {
	public SwipeDown(Node p) {
		super(p);
	}

	@Override
	boolean goingGood(TuioCursor arg) {
		return arg.getX() < start_x.get(arg.getCursorID()) + 0.05
				&& arg.getX() < previous_x.get(arg.getCursorID()) + 0.05
				&& arg.getY() < start_y.get(arg.getCursorID()) + 0.1
				&& arg.getY() > start_y.get(arg.getCursorID()) - 0.1;
	}

	@Override
	boolean farEnough(TuioCursor arg) {
		return (arg.getX() - start_x.get(arg.getCursorID())) < -MIN_DISTANCE;
	}

	@Override
	EventType<SwipeEvent> getSwipeType() {
		return SwipeEvent.SWIPE_DOWN;
	}
	
}
