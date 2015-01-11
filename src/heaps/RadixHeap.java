package heaps;

import java.util.ArrayList;

import heaps.element.Pair;

/**
 * Radix Heap is the process to organize a PQ reducing its medium complexity of
 * heap operation at the maximum. In this case, the PQ is divided in ranked
 * pieces.
 *
 * Insertion and deletions are made according to the ranks, from the smallest to
 * the largest or vice versa while the priority is being treated
 *
 * To create the ranks, first the largest priority must be know and the ranks
 * can be as follows:
 *
 * [2^1][2^2][2^3][...][2^k]
 *
 * K is the power when 2^k is the first number greater than the largest priority
 *
 * Then priorities lower than or equals 2^1 will be allocated in the first
 * priority queue.
 *
 * Priorities greater than 2^1 and lower than 2^2 will be allocated in the second
 * priority queue.
 *
 * And so on...
 *
 * */
@SuppressWarnings("unchecked")
public class RadixHeap {

	private ArrayList<Pair>[] radixSet;

	private final int[] POWERS_OF_TWO = { 2, 4, 8, 16, 32, 64, 128, 256, 512,
			1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144,
			524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432,
			67108864, 134217728, 268435456, 536870912, 1073741824 };

	public void organize(Pair[] set) {
		BinaryHeap binaryHeap = new BinaryHeap(BinaryHeap.LOW);

		int largestPriority = getLargestPriority(set);
		int kPower = getKPower(largestPriority);

		/** Initiate the radixSet */
		radixSet = new ArrayList[kPower];
		for (int i = 0; i < radixSet.length; i++)
			radixSet[i] = new ArrayList<Pair>();

		/** Distribute elements to the correct priority queue */
		for (int i = 0; i < set.length; i++) {
			int k = getKPower(set[i].p);
			binaryHeap.inHeap(radixSet[k - 1], set[i]);
		}

	}

	public ArrayList<Pair>[] getRadixSet() {
		return radixSet;
	}

	/** Calculate the k power */
	private int getKPower(int priority) {
		int k = 0;

		if (priority <= POWERS_OF_TWO[0])
			k = 0;
		else if (priority > POWERS_OF_TWO[POWERS_OF_TWO.length - 1]) {
			k = POWERS_OF_TWO.length + 1;
		} else {
			for (int i = 1; i < POWERS_OF_TWO.length - 1; i++) {
				if (priority > POWERS_OF_TWO[i - 1]
						&& priority <= POWERS_OF_TWO[i]) {
					k = i;
					break;
				}
			}
		}

		return k + 1;
	}

	/** Get the largest priority */
	private int getLargestPriority(Pair[] set) {
		int largestPriority = 0;

		for (int i = 0; i < set.length; i++) {
			if (set[i].p > largestPriority)
				largestPriority = set[i].p;
		}

		return largestPriority;
	}
}
