package heaps;

import java.util.ArrayList;

import heaps.element.Pair;
import heaps.tree.algorithm.AVLTree;
import heaps.tree.node.TreeNode;

/** Binary Heap */
/** Bigger priority is on the top as default */
/**
 * As upgrade is a very expensive function O (n), then an AVL heaps.tree is used to
 * map the indexes and their positions and the search is reduced from O (n) to O
 * (log n). Consider that there are no repeated indexes.
 */
public class BinaryHeapAvlIndexed {

	private AVLTree avlTree;

	/** For priorization with high priority on top */
	public static final int HIGH = 0;
	/** For priorization with low priority on top */
	public static final int LOW = 1;
	private int priorityType;

	public BinaryHeapAvlIndexed() {
		priorityType = HIGH;
		avlTree = new AVLTree();
	}

	public BinaryHeapAvlIndexed(int type) {
		priorityType = type;
		avlTree = new AVLTree();
	}

	/** Inheap heaps.element x into priority queue */
	public void inHeap(ArrayList<Pair> priorityQueue, Pair x) {

		/** Add the pair x into priority queue */
		priorityQueue.add(x);

		/** Check priority organization and reorganize if necessary */
		swapHeapDownUp(priorityQueue, x);
	}

	/** Outheap heaps.element in the top of priority queue */
	public void outHeap(ArrayList<Pair> priorityQueue) {

		Pair bottom = bottom(priorityQueue);

		/**
		 * Remove the heaps.element in the top and insert the heaps.element in the bottom
		 * into the top
		 */
		priorityQueue.set(0, bottom);
		priorityQueue.remove(priorityQueue.size() - 1);

		if (priorityQueue.size() > 0) {
			/** Reorganize the priorities over the top */
			Pair x = priorityQueue.get(0);
			swapHeapUpDown(priorityQueue, x);
		}
	}

	/** Reorganize the queue priority of heaps.element x, from up down */
	public void swapHeapUpDown(ArrayList<Pair> priorityQueue, Pair x) {
		swapHeapDownUp(priorityQueue, x, 0);
	}

	public void swapHeapUpDown(ArrayList<Pair> priorityQueue, Pair x, int position) {

		int currentPosition = position;
		int currentLeftPositon = 2 * currentPosition + 1;
		int currentRightPositon = 2 * currentPosition + 2;

		/**
		 * Compare priority heaps.element to its parents priority and swap if
		 * necessary if xParentPosition = 0, it has reached the root.
		 */

		while (currentPosition < priorityQueue.size() && priorityQueue.size() > 1) {

			if (priorityType == HIGH) {
				// High priorization

				if (currentLeftPositon < priorityQueue.size()
						&& priorityQueue.get(currentPosition).p < priorityQueue.get(currentLeftPositon).p
						&& (currentRightPositon >= priorityQueue.size() || (currentRightPositon < priorityQueue.size() && priorityQueue
								.get(currentLeftPositon).p >= priorityQueue.get(currentRightPositon).p))) {

					swap(priorityQueue, currentPosition + 1, currentLeftPositon + 1);

					currentPosition = currentLeftPositon;

				} else if (currentRightPositon < priorityQueue.size()
						&& (priorityQueue.get(currentPosition).p < priorityQueue.get(currentRightPositon).p)) {

					swap(priorityQueue, currentPosition + 1, currentRightPositon + 1);

					currentPosition = currentRightPositon;
				} else {
					break;
				}
			} else {
				// Low priorization

				if (currentLeftPositon < priorityQueue.size()
						&& priorityQueue.get(currentPosition).p > priorityQueue.get(currentLeftPositon).p
						&& (currentRightPositon >= priorityQueue.size() || (currentRightPositon < priorityQueue.size() && priorityQueue
								.get(currentLeftPositon).p <= priorityQueue.get(currentRightPositon).p))) {

					swap(priorityQueue, currentPosition + 1, currentLeftPositon + 1);

					currentPosition = currentLeftPositon;

				} else if (currentRightPositon < priorityQueue.size()
						&& (priorityQueue.get(currentPosition).p > priorityQueue.get(currentRightPositon).p)) {

					swap(priorityQueue, currentPosition + 1, currentRightPositon + 1);

					currentPosition = currentRightPositon;
				} else {
					break;
				}
			}

			currentLeftPositon = 2 * currentPosition + 1;
			currentRightPositon = 2 * currentPosition + 2;

		}

	}

	/** Reorganize the queue priority of heaps.element x, from down up */
	public void swapHeapDownUp(ArrayList<Pair> priorityQueue, Pair x) {
		swapHeapDownUp(priorityQueue, x, priorityQueue.size());
	}

	public void swapHeapDownUp(ArrayList<Pair> priorityQueue, Pair x, int position) {

		int xPosition = position;
		int xParentPosition = xPosition / 2;

		/**
		 * Compare priority heaps.element to its parents priority and swap if
		 * necessary if xParentPosition = 0, it reached the root
		 */
		while (xParentPosition != 0
				|| (xParentPosition > 0 && priorityQueue.get(xPosition - 1).p > priorityQueue.get(xParentPosition - 1).p)) {

			if (priorityType == HIGH) {
				if (priorityQueue.get(xPosition - 1).p > priorityQueue.get(xParentPosition - 1).p) {

					swap(priorityQueue, xPosition, xParentPosition);
				}
			} else {
				if (priorityQueue.get(xPosition - 1).p < priorityQueue.get(xParentPosition - 1).p) {

					swap(priorityQueue, xPosition, xParentPosition);
				}
			}

			xPosition = xParentPosition;
			xParentPosition = xParentPosition / 2;
		}

	}

	/**
	 * Increase in delta the priority of heaps.element x. Px = Px + delta. Update the
	 * priority queue.
	 */
	public void upgrade(ArrayList<Pair> priorityQueue, int delta, Pair x) {

		/** Found the heaps.element, its priority will be changed */
		TreeNode mapped = getAvlTree().find(x.v);

		if (mapped != null) {

			int pos = mapped.getValue() - 1;

			priorityQueue.get(pos).p = priorityQueue.get(pos).p + delta;

			/** Then, reorganize the priority queue over x */
			swapHeapDownUp(priorityQueue, priorityQueue.get(pos), pos + 1);

			/** Update position in the AVL heaps.tree */
			for (int i = 0; i < priorityQueue.size(); i++)
				getAvlTree().find(priorityQueue.get(i).v).setValue(i + 1);
		} else {
			System.out.println("\nPair " + x + " not found to upgrade");
		}

	}

	/**
	 * Decrease in delta the priority of heaps.element x. Px = Px - delta. Update the
	 * priority queue.
	 */
	public void dowgrade(ArrayList<Pair> priorityQueue, int delta, Pair x) {

		/** Found the heaps.element, its priority will be changed */
		TreeNode mapped = getAvlTree().find(x.v);

		if (mapped != null) {
			int pos = mapped.getValue() - 1;

			priorityQueue.get(pos).p = priorityQueue.get(pos).p - delta;

			/** Then, reorganize the priority queue over x */
			swapHeapUpDown(priorityQueue, priorityQueue.get(pos), pos);

			/** Update position in the AVL heaps.tree */
			for (int i = 0; i < priorityQueue.size(); i++)
				getAvlTree().find(priorityQueue.get(i).v).setValue(i + 1);
		} else {
			System.out.println("\nPair " + x + " not found to downgrade");
		}

	}

	/**
	 * Return the heaps.element in the top
	 */
	public Pair top(ArrayList<Pair> priorityQueue) {
		return priorityQueue.get(0);
	}

	/**
	 * Return the heaps.element in the bottom
	 */
	public Pair bottom(ArrayList<Pair> priorityQueue) {
		return priorityQueue.get(priorityQueue.size() - 1);
	}

	/** Map the heaps.element index at the AVL heaps.tree */
	public void map(ArrayList<Pair> priorityQueue) {
		for (int i = 0; i < priorityQueue.size(); i++)
			avlTree.insert(priorityQueue.get(i).v, i + 1);
	}

	/** Simple swap */
	private void swap(ArrayList<Pair> priorityQueue, int xPosition, int xParentPosition) {
		Pair temp = priorityQueue.get(xPosition - 1);
		priorityQueue.set(xPosition - 1, priorityQueue.get(xParentPosition - 1));
		priorityQueue.set(xParentPosition - 1, temp);
	}

	/** Get AVLTree */
	public AVLTree getAvlTree() {
		return avlTree;
	}

}
