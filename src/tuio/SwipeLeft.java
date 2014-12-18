package tuio;

import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;
import TUIO.TuioCursor;

/**
 * Catches a swiping left of a user and handles accordingly.
 */
@SuppressWarnings("restriction")
public class SwipeLeft extends SwipeAbstract {
	public SwipeLeft(Node p) {
		super(p);
	}

	@Override
	boolean goingGood(TuioCursor arg) {
		return arg.getX() < start_x.get(arg.getCursorID()) + 0.1
				&& arg.getX() > start_x.get(arg.getCursorID()) - 0.1
				&& arg.getY() < start_y.get(arg.getCursorID()) + 0.05
				&& arg.getY() < previous_y.get(arg.getCursorID()) + 0.05;
	}

	@Override
	boolean farEnough(TuioCursor arg) {
		return (arg.getY() - start_y.get(arg.getCursorID())) < -MIN_DISTANCE;
	}

	@Override
	EventType<SwipeEvent> getSwipeType() {
		return SwipeEvent.SWIPE_LEFT;
	}
}
