package util.applications;


import java.util.ArrayList;

import heaps.element.Pair;
import heaps.BinaryHeap;


public class HeapSort {

	private BinaryHeap binaryHeap = new BinaryHeap();
	private ArrayList<Pair> priorityQueue = new ArrayList<Pair>();
	private Pair[] heapified;

	/**
	 * Heap sort is simply to inheap the elements to priority queue, and outheap
	 * elements
	 */
	public Pair[] sort(Pair[] set) {

		heapified = new Pair[set.length];

		/** Build initial heap */
		heapify(set);
		heapSort();

		return heapified;
	}

	private void heapify(Pair[] set) {

		/** Heapifying */
		for (int i = 0; i < set.length; i++)
			binaryHeap.inHeap(priorityQueue, set[i]);

		heapified = priorityQueue.toArray(heapified);

	}

	/** Outheap one by one */
	private void heapSort() {

		int queueSize = priorityQueue.size();

		for (int j = 0; j < queueSize; j++) {

			heapified[j] = binaryHeap.top(priorityQueue);
			binaryHeap.outHeap(priorityQueue);

		}

	}
}
