package tuio;

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
public class Tracking implements TuioListener {

	@Override
	public void addTuioCursor(TuioCursor arg) {
		System.out.println(">>> Cursor in:   " + arg.getCursorID() + "    " + arg.getX());
	}

	@Override
	public void removeTuioCursor(TuioCursor arg) {
		System.out.println("<<< Cursor out:  " + arg.getCursorID() + "    " + arg.getX());
	}
	@Override
	public void updateTuioCursor(TuioCursor arg) {
		System.out.println("> Cursor update: " + arg.getCursorID() + "    " + arg.getX());
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
