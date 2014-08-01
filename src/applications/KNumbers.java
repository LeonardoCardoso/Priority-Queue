package applications;

import java.util.ArrayList;

import element.Pair;
import heaps.BinaryHeap;

/** The Smallest/largest k numbers (consider priorities) of a priority queue */
public class KNumbers {

	public static final int SMALLEST = 0, LARGEST = 1;
	private ArrayList<Pair> kList = new ArrayList<Pair>();

	public ArrayList<Pair> get(Pair[] set, int k, int type) {

		BinaryHeap binaryHeap = new BinaryHeap(type);

		int i = 0;

		/** Inheap the k first elements */
		for (i = 0; i < k; i++)
			binaryHeap.inHeap(kList, set[i]);

		if (type == SMALLEST) {
			/**
			 * If the next element is smaller than the priority queue top,
			 * inheap it
			 */
			for (i = k; i < set.length; i++) {
				if (set[i].p < binaryHeap.top(kList).p) {
					kList.set(0, set[i]);
					binaryHeap.swapHeapUpDown(kList,
							binaryHeap.top(kList));
				}
			}
		} else {
			/**
			 * If the next element is smaller than the priority queue top,
			 * inheap it
			 */
			for (i = k; i < set.length; i++) {
				if (set[i].p > binaryHeap.top(kList).p) {
					kList.set(0, set[i]);
					binaryHeap.swapHeapUpDown(kList,
							binaryHeap.top(kList));
				}
			}
		}

		return kList;
	}

}
