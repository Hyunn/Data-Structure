/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p4
// FILE:             IntervalTree.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * This interface defines the structure of an <code>IntervalTree</code> type. It
 * supports modifying and inserting intervals as data items into nodes of this
 * tree.
 * </p>
 * 
 * <p>
 * You must create a class named <code>IntervalTree</code> that implements this
 * interface. Your class <code>IntervalTree</code> must have following:
 * </p>
 * 
 * <p>
 * You may (and are encouraged to) define private helper methods as needed. An
 * example: <code>deleteHelper</code> is included to help give you an idea.
 * </p>
 * 
 * <p>
 * A default no-arg constructor that properly initialized the IntervalTree.
 * </p>
 * 
 * <pre>
 * public IntervalTree()
 * </pre>
 *
 * <p>
 * A constructor that constructs a tree the given node as its root node.
 * </p>
 * 
 * <pre>
 * public IntervalTree(IntervalNode&lt;T&gt; root)
 * </pre>
 *
 * @param <T>
 *            A Comparable type that can be used to indicate the start and end
 *            times of an interval.
 */
public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	//declare varibles
	IntervalNode<T> root;
	T maxEnd;
	int size;
	int height;
	List<IntervalADT<T>> list;

	/**
	 * A default no-arg constructor that properly initialized the IntervalTree.
	 */

	public IntervalTree() {
		root = null;
	}

	/** Returns the root node of this IntervalTree. */
	@Override
	public IntervalNode<T> getRoot() {
		return this.root;
	}

	/**
	 * Inserts an <code>Interval</code> in the tree.
	 * 
	 * <p>
	 * Each <code>Interval</code> is stored as the data item of an
	 * <code>IntervalNode</code>. The position of the new IntervalNode will be
	 * the position found using the binary search algorithm. This is the same
	 * algorithm presented in BST readings and lecture examples.
	 * 
	 * @param interval
	 *            the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException
	 *             if interval is null or is found to be a duplicate of an
	 *             existing interval in this tree.
	 */
	@Override
	public void insert(IntervalADT<T> interval) throws IllegalArgumentException {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		root = insertHelper(root, interval);
	}

	/**
	 * Recursive helper method for the insert operation.
	 * 
	 * @throws IllegalArgumentException
	 *             if interval is null
	 * @param interval
	 *            the interval (item) to insert in the tree.
	 * @param node
	 *            the interval node that is currently being checked.
	 */
	public IntervalNode<T> insertHelper(IntervalNode<T> node, IntervalADT<T> interval) throws IllegalArgumentException {
		if (node == null) {
			return new IntervalNode<T>(interval);
		}
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		if (interval.compareTo(node.getInterval()) < 0) {
			node.setMaxEnd(
					((node.getMaxEnd().compareTo(interval.getEnd()) >= 0) ? node.getMaxEnd() : interval.getEnd()));
			node.setLeftNode(insertHelper(node.getLeftNode(), interval));
		}
		if (interval.compareTo(node.getInterval()) > 0) {
			node.setMaxEnd(
					((node.getMaxEnd().compareTo(interval.getEnd()) >= 0) ? node.getMaxEnd() : interval.getEnd()));
			node.setRightNode(insertHelper(node.getRightNode(), interval));
		} else if (interval.compareTo(node.getInterval()) == 0) {
			throw new IllegalArgumentException();
		}
		return node;
	}

	/**
	 * Delete the node containing the specified interval in the tree. Delete
	 * operations must also update the maxEnd of interval nodes that change as a
	 * result of deletion.
	 * 
	 * @throws IllegalArgumentException
	 *             if interval is null
	 * @throws IntervalNotFoundException
	 *             if the interval does not exist.
	 * @param interval
	 *            the interval (item) to insert in the tree.
	 */
	@Override
	public void delete(IntervalADT<T> interval) throws IntervalNotFoundException, IllegalArgumentException {
		boolean ThrowException = true;

		if (interval == null) {
			throw new IllegalArgumentException();
		}

		if (root.getInterval().compareTo(interval) == 0) {
			ThrowException = false;
		}

		root = deleteHelper(root, interval);

		if (root == null && ThrowException) {
			throw new IntervalNotFoundException(interval.toString());
		}
	}

	/**
	 * Recursive helper method for the delete operation.
	 * 
	 *
	 * @param node
	 *            the interval node that is currently being checked.
	 * 
	 * @param interval
	 *            the interval to delete.
	 * 
	 * @throws IllegalArgumentException
	 *             if the interval is null.
	 * 
	 * @throws IntervalNotFoundException
	 *             if the interval is not null, but is not found in the tree.
	 * 
	 * @return Root of the tree after deleting the specified interval.
	 */
	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node, IntervalADT<T> interval)
			throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		if (node == null) {
			throw new IntervalNotFoundException(interval.toString());
		}

		if (node.getInterval().compareTo(interval) == 0) {
			if (node.getRightNode() == null) {
				return node.getLeftNode();
			} else {
				IntervalADT<T> newInt = node.getSuccessor().getInterval();
				node.setInterval(newInt);
				node.setMaxEnd(recalculateMaxEnd(node));
				node.setRightNode(deleteHelper(node.getRightNode(), newInt));
				return node;
			}
		}

		else {
			if (interval.compareTo(node.getInterval()) < 0) {
				node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
				node.setMaxEnd(recalculateMaxEnd(node));
				return node;
			} else
				node.setRightNode(deleteHelper(node.getRightNode(), interval));
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
	}

	/**
	 * a non-recursive helper method that recalculates maxEnd for any node based
	 * on the maxEnd of its child nodes
	 */
	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate) {

		if (nodeToRecalculate != null) {
			if (maxEnd == null) {
				maxEnd = nodeToRecalculate.getInterval().getEnd();
			}
			if (nodeToRecalculate.getInterval().getEnd().compareTo(maxEnd) > 0) {
				maxEnd = nodeToRecalculate.getInterval().getEnd();
			}

			recalculateMaxEnd(nodeToRecalculate.getLeftNode());
			recalculateMaxEnd(nodeToRecalculate.getRightNode());

		}
		return maxEnd;
	}

	/**
	 * Find and return a list of all intervals that overlap with the given
	 * interval.
	 * 
	 * @param interval
	 *            the interval to search for overlapping
	 *
	 * @return list of intervals that overlap with the input interval.
	 */
	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		list = new ArrayList<>();
		findOverlappingHelper(root, interval, list);
		return list;
	}

	/**
	 * Recursive helper method for the findOverlapping operation.
	 * 
	 *
	 * @param node
	 *            the interval node that is currently being checked.
	 * 
	 * @param interval
	 *            the interval to be checked.
	 */
	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval, List<IntervalADT<T>> result) {
		if (node == null)
			return;
		if (node.getInterval().overlaps(interval))
			list.add(node.getInterval());
		if (node.getLeftNode() != null) {
			if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
				findOverlappingHelper(node.getLeftNode(), interval, list);
		}
		if (node.getRightNode() != null) {
			if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
				findOverlappingHelper(node.getRightNode(), interval, list);
		}
		return;
	}

	/**
	 * Search and return a list of all intervals containing a given point. This
	 * method may return an empty list.
	 * 
	 * @throws IllegalArgumentException
	 *             if point is null
	 * 
	 * @param point
	 *            input point to search for.
	 * @return List of intervals containing the point.
	 */
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		if (point == null)
			throw new IllegalArgumentException();
		// make the point as an interval with [point point], and call the
		// findOverlapping method with interval
		return findOverlapping(new Interval<T>(point, point, ""));
	}

	/**
	 * Get the size of the interval tree. The size is the total number of nodes
	 * present in the tree.
	 * 
	 * @return int number of nodes in the tree.
	 */
	@Override
	public int getSize() {
		return getSizeHelper(root);
	}

	/**
	 * Recursive helper method for the getSize operation.
	 * 
	 * @param interval
	 *            the interval to get size.
	 * 
	 * @return int number of nodes in the tree.
	 */
	public int getSizeHelper(IntervalNode<T> intervalNode) {
		if (intervalNode == null)
			return 0;
		else
			return (getSizeHelper(intervalNode.getLeftNode()) + getSizeHelper(intervalNode.getRightNode()) + 1);
	}

	/**
	 * Return the height of the interval tree at the root of the tree.
	 * 
	 * @return the height of the interval tree
	 */
	@Override
	public int getHeight() {
		if (root == null)
			return 0;
		else {

			int leftHeight = getHeightHelper(root.getLeftNode());
			int rightHeight = getHeightHelper(root.getRightNode());

			if (leftHeight > rightHeight)
				return (leftHeight + 1);
			else
				return (rightHeight + 1);
		}
	}

	/**
	 * Recursive helper method for the getHeight operation.
	 * 
	 * @param interval
	 *            the interval to get height.
	 * 
	 * @return the height of the interval tree.
	 */
	public int getHeightHelper(IntervalNode<T> intervalNode) {
		if (intervalNode == null)
			return 0;
		else {

			int leftHeight = getHeightHelper(intervalNode.getLeftNode());
			int rightHeight = getHeightHelper(intervalNode.getRightNode());

			if (leftHeight > rightHeight)
				return (leftHeight + 1);
			else
				return (rightHeight + 1);
		}
	}

	/**
	 * Returns true if the tree contains an exact match for the start and end of
	 * the given interval. The label is not considered for this operation.
	 * 
	 * @param interval
	 *            target interval for which to search the tree for.
	 * @return boolean representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             if interval is null.
	 * 
	 */
	@Override
	public boolean contains(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		// base case
		if (root.getLeftNode() == null && root.getRightNode() == null) {
			if (interval.compareTo(root.getInterval()) == 0)
				return true;
			else
				return false;
		}
		// go left node
		if (interval.compareTo(root.getInterval()) < 0) {
			if (root.getLeftNode() == null) {
				return false;
			} else {
				root = root.getLeftNode();
				return contains(interval);
			}
		}
		// go right node
		if (interval.compareTo(root.getInterval()) < 0) {
			if (root.getRightNode() == null) {
				return false;
			} else {
				root = root.getRightNode();
				return contains(interval);
			}
		}
		return true;
	}

	/**
	 * Print the statistics of the tree in the below format
	 * 
	 * <pre>
	 *	-----------------------------------------
	 *	Height: 2
	 *	Size: 3
	 *	-----------------------------------------
	 * </pre>
	 */
	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + this.getHeight());
		System.out.println("Size: " + this.getSize());
		System.out.println("-----------------------------------------");
	}

}
