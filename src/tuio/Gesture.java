package tuio;

import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

@SuppressWarnings("restriction")
public abstract class Gesture implements TuioListener {
	protected Node root;
	
	public Gesture(Node p) {
		super();
		root = p;
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

		Node result = null;
		if (node instanceof Parent) {
			for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
				if (result == null) {
					result = pickNodeBySceneXY(child, x, y);
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
		double fx_x = total_width * (double) y;
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
