package main;

import heaps.BinaryHeap;
import heaps.BinaryHeapAvlIndexed;
import heaps.MinMaxHeap;
import heaps.RadixHeap;
import heaps.element.Pair;
import util.PrintHeap;
import util.applications.HeapSort;
import util.applications.KNumbers;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    public static final Pair[] SET = {new Pair(2, 3), new Pair(21, 4), new Pair(3, 9), new Pair(16, 16), new Pair(4, 4),
            new Pair(9, 2), new Pair(21, 7), new Pair(31, 9), new Pair(3, 3)};

    public static void main(String[] args) {
//        callPriorityQueue(SET);
//        callAvlIndex(SET);
//        callMinMaxHeap(SET);
//        callHeapSort(SET);
//        callKNumbers(SET);
//        callRadixHeap(SET);
    }

    public static void callPriorityQueue(Pair[] set) {
        BinaryHeap binaryHeap = new BinaryHeap();

        /** Initiate priority queue */
        ArrayList<Pair> priorityQueue = new ArrayList<Pair>();

        /** Inheap Elements */
        for (int i = 0; i < set.length; i++)
            binaryHeap.inHeap(priorityQueue, set[i]);

        /** Outheap */
        // binaryHeap.outHeap(priorityQueue);

        /** Upgrade */
        // binaryHeap.upgrade(priorityQueue, 50, new Pair(2, 3));

        /** Downgrade */
        // binaryHeap.dowgrade(priorityQueue, 10, new Pair(52, 3));

        /** Top */
        // System.out.println("Top " + binaryHeap.top(priorityQueue));

        /** Bottom */
        // System.out.println("Bottom "
        // + binaryHeap.bottom(priorityQueue));

        /** Print priority queue */
        System.out.println("\nFinal priority queue\n");
        print("Priority Queue", priorityQueue);
    }

    public static void callMinMaxHeap(Pair[] set) {
        MinMaxHeap minMaxHeap = new MinMaxHeap();
        /** Initiate priority queue */
        ArrayList<Pair> priorityQueue = new ArrayList<Pair>();

        /** Inheap Elements */
        for (int i = 0; i < set.length; i++)
            minMaxHeap.inHeap(priorityQueue, set[i]);

        /** Outheap Min */
        // minMaxHeap.outHeapMin(priorityQueue);

        /** Outheap Max */
        // minMaxHeap.outHeapMax(priorityQueue);

        /** Upgrade */
        // minMaxHeap.upgrade(priorityQueue, 8, new Pair(15, 8));

        /** Downgrade */
        // minMaxHeap.dowgrade(priorityQueue, 8, new Pair(8, 2));

        /** Top Min */
        // System.out.println("Top Min " + minMaxHeap.topMin(priorityQueue));

        /** Top Max */
        // System.out.println("Top Max " + minMaxHeap.topMax(priorityQueue));

        /** Levels */
        // System.out.println("Levels "
        // + minMaxHeap.levels(minMaxHeap.size(priorityQueue)));

        /** Size */
        // System.out.println("Size " + minMaxHeap.size(priorityQueue));

        /** Bottom */
        // System.out.println("Bottom " + minMaxHeap.bottom(priorityQueue));

        /** Print priority queue */
        System.out.println("\nFinal priority queue [Min-Max Heap] \n");
        print("MinMax Heap", priorityQueue);
    }

    public static void callAvlIndex(Pair[] set) {
        BinaryHeapAvlIndexed binaryHeapAvlIndexed = new BinaryHeapAvlIndexed();

        /** Initiate priority queue */
        ArrayList<Pair> priorityQueue = new ArrayList<Pair>();

        /** Inheap Elements */
        for (int i = 0; i < set.length; i++)
            binaryHeapAvlIndexed.inHeap(priorityQueue, set[i]);

        binaryHeapAvlIndexed.map(priorityQueue);

        /** Upgrade */
//        binaryHeapAvlIndexed.upgrade(priorityQueue, 50, new Pair(2, 3));

        /** Downgrade */
//        binaryHeapAvlIndexed.dowgrade(priorityQueue, 37, new Pair(52, 3));

        /** Print priority queue */
        System.out.println("\nFinal priority queue\n");
        print("Binary Heap AVL - Indexed", priorityQueue);


        System.out.println("\n\nAVL Tree (priority, value)\n");
        binaryHeapAvlIndexed.getAvlTree().printAll();
    }

    /**
     * Sort the elements using Heap Sort
     */
    public static void callHeapSort(Pair[] set) {
        System.out.print("HeapSort\n\nBefore: ");
        print(set, true);
        System.out.println("\n");
        ArrayList<Pair> toPrint = new ArrayList<Pair>(Arrays.asList(set));
        print("HeapSort\n\nBefore: ", toPrint);
        System.out.println("\n");


        HeapSort heapSort = new HeapSort();
        set = heapSort.sort(set);


        System.out.print("\nAfter: ");
        print(set, true);
        System.out.println("\n");
        toPrint = new ArrayList<Pair>(Arrays.asList(set));
        print("HeapSort\n\nAfter: ", toPrint);
    }

    /**
     * Get the smallest/largest k numbers (consider priorities) of a priority
     * queue
     */
    public static void callKNumbers(Pair[] set) {

        KNumbers kNumbers = new KNumbers();

        System.out.print("SET = ");
        print(set, true);

        int k = 3;
        ArrayList<Pair> smallest = kNumbers.get(set, k, KNumbers.SMALLEST);
        System.out.println("\n\nSmallest " + k + " numbers (priorities)");
        print("K Numbers - Smallest", smallest);

        kNumbers = new KNumbers();
        ArrayList<Pair> largest = kNumbers.get(set, k, KNumbers.LARGEST);
        System.out.println("\n\nLargest " + k + " numbers (priorities)");
        print("K Numbers - Largest", largest);

    }

    /**
     * Organize PQ as a radix set
     */
    public static void callRadixHeap(Pair[] set) {

        RadixHeap radixHeap = new RadixHeap();
        radixHeap.organize(set);
        ArrayList<Pair>[] radixSet = radixHeap.getRadixSet();

        for (int i = 0; i < radixSet.length; i++) {
            System.out.println("Radix: 2^" + (i + 1));
            print(radixSet[i]);
            System.out.println("\n");
        }
        PrintHeap.drawAllRadix("Radix Heap", radixSet);

    }

    /**
     * Print priority queue
     */
    public static void print(Pair[] stock) {
        print(stock, false);
    }

    public static void print(Pair[] stock, boolean printIndex) {
        System.out.print("{");

        for (int i = 0; i < stock.length; i++) {
            System.out.print((printIndex ? stock[i] : stock[i].p) + (i == stock.length - 1 ? "" : ","));
        }

        System.out.print("}");
    }

    /**
     * Print priority queue
     */
    public static Pair[] print(ArrayList<Pair> priorityQueue) {
        Pair[] stock = new Pair[priorityQueue.size()];
        stock = priorityQueue.toArray(stock);
        PrintHeap.printPairs(stock);
        return stock;
    }

    public static void print(String title, ArrayList<Pair> priorityQueue) {
        PrintHeap.drawAll(title, print(priorityQueue));
    }

}
