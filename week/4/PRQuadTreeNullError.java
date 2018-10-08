package cmsc420.meeshquest.part1;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class PRQuadTree {

	private PRNode root;
	Point2D.Float dimensions;

	public PRQuadTree(Point2D.Float dimensions) {
		this.dimensions = dimensions;
		root = PRWhiteNode.getInstance();
	}

	public int insert(City city) {
		// check to see if already mapped
		// check if city is in dictionary
		Point2D.Float center = new Point2D.Float(dimensions.x / 2, dimensions.y / 2);
		if (root.getClass() == PRWhiteNode.class) {
			root = new PRBlackNode(city);

			return Status.SUCCESS;
		} else if (root.getClass() == PRBlackNode.class) { // Insert first
															// initial gray node
			City oldCity = ((PRBlackNode) root).getCity();

			int quadrant = getCityQuadrant(oldCity, center);
			PRGrayNode newRoot = new PRGrayNode(center, dimensions.x / 2);
			newRoot.setQuadrant(oldCity, quadrant);

			root = newRoot;
			insert_aux(city, null, root, center, dimensions.x / 2);

			return Status.SUCCESS;
		}

		else {
			insert_aux(city, null, root, center, dimensions.x  / 2);


		}
		return Status.SUCCESS;

	}

	public PRNode insert_aux(City newCity, PRNode prev, PRNode curr, Point2D.Float center, float dim) {
		int newCityQuadrant = getCityQuadrant(newCity, center);

		PRGrayNode grayNode = (PRGrayNode) curr;
		PRNode targetNode = grayNode.getQuadrant(newCityQuadrant);

		if (targetNode instanceof PRWhiteNode) { // inserting newCity on white
													// node

			grayNode.setQuadrant(newCity, newCityQuadrant);
			return grayNode;


		} else {

			Point2D.Float nextCenter = getNextCenter(center, dim, newCityQuadrant);
			dim = dim / 2;

			if (targetNode instanceof PRBlackNode) { // inserting newCity on
														// black node
				City oldCity = ((PRBlackNode) targetNode).getCity();

				int oldCityNextQuadrant = getCityQuadrant(oldCity, nextCenter);
				PRGrayNode newGrayNode = new PRGrayNode(nextCenter, dim);
				newGrayNode.setQuadrant(oldCity, oldCityNextQuadrant);

				((PRGrayNode) curr).insertGray(newGrayNode, newCityQuadrant);
//				newGrayNode =  (PRGrayNode) insert_aux(newCity, null, newGrayNode, nextCenter, dim);

//				return newGrayNode;
				curr = newGrayNode;
				return insert_aux(newCity, null, curr, nextCenter, dim / 2);


			} else { // inserting newCity on gray node
				PRGrayNode nextNode = (PRGrayNode) targetNode;

				return insert_aux(newCity, curr, nextNode, nextCenter, dim / 2);
			}


		}
	}

	public int setDimensions(Point2D.Float spatialWidth, Point2D.Float spatialHeight) {

		return 0;
	}

	public int getCityQuadrant(City city, Point2D.Float center) {
		float xCoordinate = city.x;
		float yCoordinate = city.y;
		int quadrant = -1;

		if (yCoordinate < center.x) { // In Southern Quadrant
			if (xCoordinate < center.x) { // in SW quadrant
				quadrant = 2;
			} else { // in SE quadrant
				quadrant = 3;
			}
		} else {
			if (xCoordinate < center.x) { // in NW quadrant
				quadrant = 0;
			} else { // in NE quadrant
				quadrant = 1;
			}
		}
		return quadrant;
	}

	public Point2D.Float getNextCenter(Point2D.Float center, float dimension, int quadrant) {
		Point2D.Float newCenter = null;
		float nextDim = dimension / 2;
		if (dimension == 1f) { // previous quadrant is the minimum size
			return null;
		}

		if (quadrant == 0) { // NW quadrant
			newCenter = new Point2D.Float(center.x - nextDim, center.y + nextDim);
		} else if (quadrant == 1) { // NE quadrant
			newCenter = new Point2D.Float(center.x + nextDim, center.y + nextDim);
//			center.setLocation(center.x + nextDim, center.y + nextDim);
		} else if (quadrant == 2) { // SW quadrant
			newCenter = new Point2D.Float(center.x - nextDim, center.y - nextDim);
//			center.setLocation(center.x - nextDim, center.y - nextDim);
		} else if (quadrant == 3) { // SE quadrant
			newCenter = new Point2D.Float(center.x + nextDim, center.y - nextDim);
//			center.setLocation(center.x + nextDim, center.y - nextDim);
		} else {
			System.out.println("Invalid quadrant for next center");
		}
		return newCenter;
	}

	public PRNode getRoot(){
		return root;
	}
}
