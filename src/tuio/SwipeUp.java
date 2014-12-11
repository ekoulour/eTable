package tuio;

import TUIO.TuioCursor;

/**
 * Catches a swiping up of a user and handles accordingly.
 */
public class SwipeUp extends SwipeAbstract {
	@Override
	boolean goingGood(TuioCursor arg) {
		return arg.getX() > start_x.get(arg.getCursorID()) - 0.05
				&& arg.getX() > previous_x.get(arg.getCursorID()) - 0.05
				&& arg.getY() < start_y.get(arg.getCursorID()) + 0.1
				&& arg.getY() > start_y.get(arg.getCursorID()) - 0.1;
	}

	@Override
	boolean farEnough(TuioCursor arg) {
		return (arg.getX() - start_x.get(arg.getCursorID())) > MIN_DISTANCE;
	}

	@Override
	void eventTriggered(TuioCursor arg) {
		System.out.println("Swipe up event!");
	}
}
