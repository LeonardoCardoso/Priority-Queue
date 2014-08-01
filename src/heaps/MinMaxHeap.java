package heaps;

import java.util.ArrayList;

import element.Pair;

public class MinMaxHeap {

	/** Inheap element x into priority queue */
	public void inHeap(ArrayList<Pair> priorityQueue, Pair x) {

		/** Add the pair x into priority queue */
		priorityQueue.add(x);

		/** Check priority organization and reorganize if necessary */
		swapHeapDownUp(priorityQueue, x);
	}

	/**
	 * Outheap the highest element with the minimum priority of priority queue,
	 * then put its grandchild with the largest priority in its place and the
	 * bottom in the grandchilds place
	 */
	public void outHeapMin(ArrayList<Pair> priorityQueue) {

		if (size(priorityQueue) > 3) {
			int childIndex = 3;

			Pair bottom = bottom(priorityQueue);
			Pair grandChild2 = null;
			Pair grandChild3 = null;
			Pair grandChild4 = null;

			if (size(priorityQueue) > 4) {
				grandChild2 = priorityQueue.get(4);
				if (grandChild2.p < priorityQueue.get(childIndex).p)
					childIndex = 4;
			}
			if (size(priorityQueue) > 5) {
				grandChild3 = priorityQueue.get(5);
				if (grandChild3.p < priorityQueue.get(childIndex).p)
					childIndex = 5;
			}
			if (size(priorityQueue) > 6) {
				grandChild4 = priorityQueue.get(6);
				if (grandChild4.p < priorityQueue.get(childIndex).p)
					childIndex = 6;
			}

			priorityQueue.set(0, priorityQueue.get(childIndex));
			priorityQueue.set(childIndex, bottom);
			priorityQueue.remove(priorityQueue.size() - 1);

			findChildren(priorityQueue, childIndex, false);

		}

	}

	/** Outheap the highest element with the maximum priority of priority queue */
	public void outHeapMax(ArrayList<Pair> priorityQueue) {

		if (size(priorityQueue) > 7) {

			int maxIndex1 = 1;
			int maxIndex2 = 2;
			int changeIndex = maxIndex1;

			Pair max1 = priorityQueue.get(maxIndex1);
			Pair max2 = priorityQueue.get(maxIndex2);

			int childIndex = 7;
			int childIndexOffset = childIndex;

			if (max1.p > max2.p) {
				childIndex = (2 * maxIndex1 + 1) + (2 * maxIndex1 + 1) + 1;
			} else {
				childIndex = (2 * maxIndex2 + 1) + (2 * maxIndex2 + 1) + 1;
				changeIndex = maxIndex2;
			}

			Pair bottom = bottom(priorityQueue);
			Pair grandChild2 = null;
			Pair grandChild3 = null;
			Pair grandChild4 = null;

			if (size(priorityQueue) > childIndexOffset + 1) {
				grandChild2 = priorityQueue.get(childIndexOffset + 1);
				if (grandChild2.p > priorityQueue.get(childIndex).p)
					childIndex = childIndexOffset + 1;
			}
			if (size(priorityQueue) > childIndexOffset + 2) {
				grandChild3 = priorityQueue.get(childIndexOffset + 2);
				if (grandChild3.p > priorityQueue.get(childIndex).p)
					childIndex = childIndexOffset + 2;
			}
			if (size(priorityQueue) > childIndexOffset + 3) {
				grandChild4 = priorityQueue.get(childIndexOffset + 3);
				if (grandChild4.p > priorityQueue.get(childIndex).p)
					childIndex = childIndexOffset + 3;
			}

			priorityQueue.set(changeIndex, priorityQueue.get(childIndex));
			priorityQueue.set(childIndex, bottom);
			priorityQueue.remove(priorityQueue.size() - 1);

			findChildren(priorityQueue, childIndex, true);
		}

	}

	/** Reorganize the queue priority of element x, from down up */
	public void swapHeapDownUp(ArrayList<Pair> priorityQueue, Pair x) {
		swapHeapDownUp(priorityQueue, x, priorityQueue.size());
	}

	public void swapHeapDownUp(ArrayList<Pair> priorityQueue, Pair x,
			int position) {

		int xPosition = position;
		int xParentPosition = xPosition / 2;

		int level = levels(xPosition);

		/**
		 * Compare priority element to its parents priority and swap if
		 * necessary if xParentPosition = 0, it reached the root
		 * 
		 * Even levels keep the min priorities
		 * 
		 * Odd levels keep the max priorities
		 */
		while (xParentPosition != 0) {

			int xGrandParentPosition = xParentPosition / 2;

			if (level % 2 == 0) {

				if (xGrandParentPosition > 0
						&& priorityQueue.get(xPosition - 1).p < priorityQueue
								.get(xGrandParentPosition - 1).p) {
					swap(priorityQueue, xPosition, xGrandParentPosition);
				} else if (priorityQueue.get(xPosition - 1).p > priorityQueue
						.get(xParentPosition - 1).p) {
					swap(priorityQueue, xPosition, xParentPosition);
				}

			} else {

				if (xGrandParentPosition > 0
						&& priorityQueue.get(xPosition - 1).p > priorityQueue
								.get(xGrandParentPosition - 1).p) {
					swap(priorityQueue, xPosition, xGrandParentPosition);
				} else if (priorityQueue.get(xPosition - 1).p < priorityQueue
						.get(xParentPosition - 1).p) {
					swap(priorityQueue, xPosition, xParentPosition);
				}

			}

			xPosition = xParentPosition;
			xParentPosition = xParentPosition / 2;
			level = levels(xPosition);

		}

	}

	/**
	 * Increase in delta the priority of element x. Px = Px + delta. Update the
	 * priority queue.
	 */
	public void upgrade(ArrayList<Pair> priorityQueue, int delta, Pair x) {

		int i = 0;

		/** Search the element to change its priority */
		for (i = 0; i < priorityQueue.size(); i++) {

			/** Found the element, its priority will be changed */
			if (priorityQueue.get(i).p == x.p && priorityQueue.get(i).i == x.i) {
				priorityQueue.get(i).p = priorityQueue.get(i).p + delta;

				x = priorityQueue.get(i);

				break;
			}

		}

		/** Then, reorganize the priority queue over x */
		findChildren(priorityQueue, i / 2, true);

	}

	/**
	 * Decrease in delta the priority of element x. Px = Px - delta. Update the
	 * priority queue.
	 */
	public void dowgrade(ArrayList<Pair> priorityQueue, int delta, Pair x) {

		int i = 0;

		/** Search the element to change its priority */
		for (i = 0; i < priorityQueue.size(); i++) {

			/** Found the element, its priority will be changed */
			if (priorityQueue.get(i).p == x.p && priorityQueue.get(i).i == x.i) {
				priorityQueue.get(i).p = priorityQueue.get(i).p - delta;

				x = priorityQueue.get(i);

				break;
			}

		}

		/** Then, reorganize the priority queue over the top */
		findChildren(priorityQueue, i / 2, true);

	}

	/**
	 * Return the highest element with the minimum priority of priority queue
	 */
	public Pair topMin(ArrayList<Pair> priorityQueue) {
		return priorityQueue.get(0);
	}

	/**
	 * Return the highest element with the maximum priority of priority queue
	 */
	public Pair topMax(ArrayList<Pair> priorityQueue) {
		Pair x = priorityQueue.get(0);
		Pair left = x;
		Pair right = x;

		if (priorityQueue.size() > 1)
			left = priorityQueue.get(1);
		if (priorityQueue.size() > 2)
			right = priorityQueue.get(2);

		return (left.p > right.p ? left : right);
	}

	/**
	 * Return the number of levels of priority queue
	 */
	public int levels(int position) {
		return (int) (Math.log(position) / Math.log(2));
	}

	/**
	 * Return the number of elements of priority queue
	 */
	public int size(ArrayList<Pair> priorityQueue) {
		return priorityQueue.size();
	}

	/**
	 * Return the element in the bottom
	 */
	public Pair bottom(ArrayList<Pair> priorityQueue) {
		return priorityQueue.get(priorityQueue.size() - 1);
	}

	/** Reorganize children of an element */
	private void findChildren(ArrayList<Pair> priorityQueue,
			int elementPosition, boolean greaterThan) {
		int childIndex = elementPosition;

		int childLeftIndex = 2 * childIndex + 1;
		int childRightIndex = 2 * childIndex + 2;
		Pair grandChildLeft = null;
		Pair grandChildRight = null;

		if (childLeftIndex < priorityQueue.size())
			grandChildLeft = priorityQueue.get(childLeftIndex);
		if (childRightIndex < priorityQueue.size())
			grandChildRight = priorityQueue.get(childRightIndex);

		if (greaterThan) {
			if (grandChildLeft != null) {
				if (grandChildRight != null
						&& grandChildRight.p > grandChildLeft.p)
					swapHeapDownUp(priorityQueue, grandChildRight,
							childRightIndex);
				else
					swapHeapDownUp(priorityQueue, grandChildLeft,
							childLeftIndex);
			}
		} else {
			if (grandChildLeft != null) {
				if (grandChildRight != null
						&& grandChildRight.p < grandChildLeft.p)
					swapHeapDownUp(priorityQueue, grandChildRight,
							childRightIndex);
				else
					swapHeapDownUp(priorityQueue, grandChildLeft,
							childLeftIndex);
			}
		}
	}

	/** Simple swap */
	private void swap(ArrayList<Pair> priorityQueue, int xPosition,
			int xParentPosition) {
		Pair temp = priorityQueue.get(xPosition - 1);
		priorityQueue
				.set(xPosition - 1, priorityQueue.get(xParentPosition - 1));
		priorityQueue.set(xParentPosition - 1, temp);
	}
}
