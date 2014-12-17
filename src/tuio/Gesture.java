package tuio;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

@SuppressWarnings("restriction")
public abstract class Gesture implements TuioListener {
	protected Node root;

	public Gesture(Node p) {
		super();
		root = p;
	}

	/**
	 * Checks whether the tuio cursor is in the "dead zone".
	 * Basically, this is a zone where we want to ignore any events, so that
	 * the keyboard has a place to stand. Likely only want to check for this
	 * when adding a cursor as it the origin that we want to ignore.
	 * @param arg Tuio Cursor
	 * @return    True if the tuio cursor is in a zone we want to ignore.
	 */
	protected boolean isDeadZone(TuioCursor arg) {
		if (arg.getY() > 0.35 && arg.getY() < 0.65
				&& arg.getX() < 0.6) {
			return true;
		}
		return false;
	}
	/**
	 * Recursively looks through the children of a node to find the one at
	 * certain coordinates. Not the most efficient, but JavaFX does not
	 * provide something simple for this for whatever reason.
	 * @param node The node whose children to check.
	 * @param x    x coordinate
	 * @param y    y coordinate
	 * @return     Node at that spot or null
	 */
	protected Node pickNodeBySceneXY(final Node node, double x, double y) {
		Point2D localXY = node.sceneToLocal(x, y);

		// If the parent does not contain it, then discount its children too.
		// Changed to this since we otherwise run into troubles with scrolling
		// views. The view itself is only a small box, but the list within it
		// is the complete size of its elements. The scrollbox more works like
		// a view. Since that big list is also visible, technically, it "eats"
		// up all the other events.
		if (!node.contains(localXY) || node.isMouseTransparent() || !node.isVisible()) return null;

		// Check if this node has children and if the point falls in one of
		// them. (that is, a child is a more precise result)
		Node result = null;
		if (node instanceof Parent) {
			// Have to loop in reversed order for JavaFX z-index
			List<Node> children = ((Parent) node).getChildrenUnmodifiable();
			for (int i = children.size() - 1; i >= 0; i--) {
				if (result == null) {
					result = pickNodeBySceneXY(children.get(i), x, y);
				}
			}
		}

		if (result != null) {
			return result;
		}

		if (node.contains(localXY) && !node.isMouseTransparent() && node.isVisible()) {
			return node;
		}

		return null;
	}

	/**
	 * Turns coordinates from the tuio library into matching spot in the
	 * JavaFX scene.
	 * @param x The x coordinate according to tuio.
	 * @param y The y coordinate according to tuio.
	 * @return  The same point in JavaFX coordinates.
	 */
	protected Point2D tuioXYtoJavaFXXY(float x, float y) {
		double total_height = root.getBoundsInParent().getHeight();
		double total_width = root.getBoundsInParent().getWidth();
		double fx_y = total_height * ((double) 1 - (double) x);
		double fx_x = total_width * y;
		return new Point2D(fx_x, fx_y);
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
