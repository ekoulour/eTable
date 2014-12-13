package tuio;

import TUIO.TuioCursor;

/**
 * Catches a swiping down of a user and handles accordingly.
 */
public class SwipeRight extends SwipeAbstract {
	@Override
	boolean goingGood(TuioCursor arg) {
		return arg.getX() < start_x.get(arg.getCursorID()) + 0.1
				&& arg.getX() > start_x.get(arg.getCursorID()) - 0.1
				&& arg.getY() > start_y.get(arg.getCursorID()) - 0.05
				&& arg.getY() > previous_y.get(arg.getCursorID()) - 0.05;
	}

	@Override
	boolean farEnough(TuioCursor arg) {
		return (arg.getY() - start_y.get(arg.getCursorID())) > MIN_DISTANCE;
	}

	@Override
	void eventTriggered(TuioCursor arg) {
		System.out.println("Swipe right event!");
	}
}
