
import java.util.Random;
import java.util.ArrayList;

/**
 * A class that implements various sorting algorithms.
 */
public class Sorting {

    public ArrayList<Integer> bubbleSort(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            /* Detects if a swap was made on a given pass. */
            boolean swapMade = false;
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    this.swap(list, j, j + 1);
                    swapMade = true;
                }
            }
            /* If no elements were swapped in a given pass, then the array is 
            sorted. */

            if (!swapMade) {
                break;
            }
        }
        return list;
    }

    public ArrayList<Integer> insertionSort(ArrayList<Integer> list) {
        /* Insertion sort is Shell sort with a starting gap of 1. */
        return this.shellSort(list, 1);
    }

    /**
     * Selection sort. In each pass find the minimum of the elements that remain
     * to be sorted and swap it with the element at index i.
     *
     * @param list The list to be sorted.
     * @return The list sorted in increasing order.
     */
    public ArrayList<Integer> selectionSort(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            /* Assume that this is the minimum value among the unsorted elements.*/
            int minValue = list.get(i);
            int minIndex = i;
            /* Compare minValue to all the elements that succeed it in the list.*/
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < minValue) {
                    minIndex = j;
                    minValue = list.get(j);
                }
            }
            this.swap(list, i, minIndex);
        }
        return list;
    }

    /**
     * Merge sort. Given a list, break it in half, recursively sort each half,
     * and then merge the halves back together.
     *
     * @param list The list to be sorted.
     * @return The sorted list in increasing order.
     */
    public ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        /* A list of size 1 is sorted. */
        if (list.size() == 1) {
            return list;
        }

        /* Break the list into two pieces. */
        ArrayList<Integer> list1 = this.splitList(list, 1);
        ArrayList<Integer> list2 = this.splitList(list, 2);

        /* Recursively merge sort each half. */
        list1 = this.mergeSort(list1);
        list2 = this.mergeSort(list2);

        /* Merge the two halves. */
        ArrayList<Integer> merged = this.merge(list1, list2);
        return merged;
    }

    /**
     * Splits a list into two halves, and returns one of the halves.
     *
     * @param list The list to be split.
     * @param half The half of the original list to return.
     * @return The specified half of the original list.
     */
    private ArrayList<Integer> splitList(ArrayList<Integer> list, int half) {
        ArrayList<Integer> split = new ArrayList<>();
        int start;
        int end;

        /* Set start and end to the beginning and midpoint of the list, respectively. */
        if (half == 1) {
            start = 0;
            end = list.size() / 2;
        }
        /* Set start and end to the midpoint and end of the list, respectively. */
        else {
            start = list.size() / 2;
            end = list.size();
        }
        /* Copy the appropriate half of the list. */
        for (int i = start; i < end; i++) {
            split.add(list.get(i));
        }
        return split;
    }

    /**
     * Merges two lists together. If the two lists are themselves already
     * sorted, then the resulting merged list will also be sorted.
     *
     * @param list1 The first list.
     * @param list2 The second list.
     * @return A single list is the result of merging the two input lists.
     */
    private ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> merged = new ArrayList<>();
        int m = list1.size();
        int n = list2.size();

        /* A variable to range over the first list. */
        int i = 0;

        /* A variable to range over the second list. */
        int j = 0;

        /* Continue until all the elements of the shorter list have been processed. */
        while (i < m && j < n) {
            /* Compare the first list at index i with the second list at index j,
            add the smaller one to the merged list, and increment the 
            appropriate variable. */
            if (list1.get(i) < list2.get(j)) {
                merged.add(list1.get(i));
                i++;
            }
            else if (list1.get(i) > list2.get(j)) {
                merged.add(list2.get(j));
                j++;
            }
            else {
                merged.add(list1.get(i));
                merged.add(list2.get(j));
                i++;
                j++;
            }
        }

        /* Add all of the remaining elements of the list that still has 
        elements remaining to be processed. In the first case the first list
        has been exhausted, so process the rest of the second list. In the second 
        case process the rest of the first list. */
        if (i == list1.size()) {
            while (j < list2.size()) {
                merged.add(list2.get(j));
                j++;
            }
        }
        else {
            while (i < list1.size()) {
                merged.add(list1.get(i));
                i++;
            }
        }
        return merged;
    }

    /**
     * A helper method that swaps the contents of two indices in a list.
     *
     * @param list The list containing the elements to be swapped.
     * @param i The first index.
     * @param j The second index.
     */
    private void swap(ArrayList<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * A public Shell Sort that can be used by the client. The actual sorting
     * and auxiliary operations are handled in the overloaded private method.
     *
     * @param list The list to be sorted.
     * @return The sorted list in increasing order.
     */
    public ArrayList<Integer> shellSort(ArrayList<Integer> list) {
        /* While perhaps not optimal, a starting gap of list.size()/2
        is recommended (by the book) as an acceptable starting gap, especially
        compared to other starting gaps (such as 1, which is equivalent to
        an insertion sort. */
        return this.shellSort(list, list.size() / 2);
    }

    /**
     * Implements a Shell sort with a given starting gap. A starting gap of 1 is
     * equivalent to an insertion sort.
     *
     * @param list The list to be sorted.
     * @param gap The starting gap as given in the Shell sort algorithm.
     * @return The list sorted in increasing order.
     */
    private ArrayList<Integer> shellSort(ArrayList<Integer> list, int gap) {

        /* The rest of the method closely follows the algorithm described in
        the book and is therefore presented without further comment. */
        while (gap >= 1) {
            int i = gap;
            while (i < list.size()) {
                int index = i;
                int n = 1;
                while (i - n * gap >= 0 && list.get(i) < list.get(i - n * gap)) {
                    index = i - n * gap;
                    n++;
                }
                if (index != i) {
                    this.shellInsert(list, index, i, gap);
                }
                i++;
            }
            gap /= 2;
        }
        return list;
    }

    /**
     * A helper method to the shellSort method. An insertion sort is performed
     * on a selected sublist of the provided list.
     *
     * @param list The list that contains a sublist on which an insertion sort
     * is to be performed.
     * @param start The starting index of the sublist.
     * @param end The ending index of the sublist.
     * @param gap The gap size, which determines which elements of the list are
     * in the sublist.
     */
    private void shellInsert(ArrayList<Integer> list, int start, int end, int gap) {
        int temp = list.get(end);
        for (int i = end; i > start; i -= gap) {
            list.set(i, list.get(i - gap));
        }
        list.set(start, temp);
    }

    /**
     * The public quicksort method. All the logic is implemented in the
     * overloaded private method.
     *
     * @param list The list to be sorted.
     * @return The sorted list in increasing order.s
     */
    public ArrayList<Integer> quickSort(ArrayList<Integer> list) {
        return this.quickSort(list, 0, list.size() - 1);
    }

    /**
     * Implements quicksort, as described in the book.
     *
     * @param list The list to be sorted.
     * @param start The start of the range to be partitioned.
     * @param end The end of the range.
     * @return The sorted list in increasing order.
     */
    private ArrayList<Integer> quickSort(ArrayList<Integer> list, int start, int end) {
        if (start >= end) {
            return list;
        }

        /* The following statement is an optimization suggested by the book. 
        This takes the median value of the start, end, and middle values of the 
        range and swaps the median value with the start value. */
        list = this.medianToFront(list, start, end);

        /* Partition the range and establish the pivot. */
        int pivotIndex = this.partition(list, start, end);

        /* Recursively sort the part of the array before and after the pivot. */
        this.quickSort(list, start, pivotIndex - 1);
        this.quickSort(list, pivotIndex + 1, end);

        return list;
    }

    /**
     * Partitions the range (the sublist in the list) from start to end by
     * inserting the pivot in its proper position.
     *
     * @param list The list that contains the range to be partitioned.
     * @param start The index of the start of the range.
     * @param end The index of the end of the range.
     * @return The index of the resulting pivot. After the pivot operation, all
     * values to the left of the pivot are smaller than the pivot and all values
     * to the right are larger.
     */
    private int partition(ArrayList<Integer> list, int start, int end) {
        int nextBiggestIndex = start + 1;
        int nextSmallestIndex = end;
        while (nextBiggestIndex < nextSmallestIndex) {

            while (list.get(start) >= list.get(nextBiggestIndex)) {
                nextBiggestIndex++;
                if (nextBiggestIndex == list.size()) {
                    break;
                }
            }
            while (list.get(start) < list.get(nextSmallestIndex)) {
                nextSmallestIndex--;
            }
            if (nextBiggestIndex < nextSmallestIndex) {
                this.swap(list, nextBiggestIndex, nextSmallestIndex);
            }

        }
        if (nextSmallestIndex < nextBiggestIndex) {
            this.swap(list, start, nextSmallestIndex);
        }
        return nextSmallestIndex;

    }

    /**
     * Places the median value Of start, end, and the middle at the beginning of
     * the sublist given by start and end.
     *
     * @param list The list that contains the sublist.
     * @param start The start of the sublist.
     * @param end The end of the sublist.
     * @return The list with the start of the sublist containing the median of
     * the start, middle, and end.
     */
    private ArrayList<Integer> medianToFront(ArrayList<Integer> list, int start, int end) {
        int middle = (end + start) / 2;
        if (list.get(start) > list.get(middle)) {
            this.swap(list, start, middle);
        }
        if (list.get(middle) > list.get(end)) {
            this.swap(list, middle, end);
        }
        if (list.get(start) < list.get(middle)) {
            this.swap(list, start, middle);
        }
        return list;
    }

    /**
     * Implements heap sort. Uses the Heap project classes as given in the
     * Libraries folder of this project.
     *
     * @param list The list to be sorted.
     * @return The listin increasing order.
     */
    public ArrayList<Integer> heapSort(ArrayList<Integer> list) {
        /* The algorithm consists of constructing a min heap from the given
        list, and then pulling elements from the top of the min heap one at a 
        time and putting them back into the list. Since a min heap stores the 
        minimum element at the top of the heap, this results in a sorted list. */

        int n = list.size();
        Heap<Integer> heap = new MinHeap<>();
        for (int i = 0; i < n; i++) {
            heap.add(list.get(i));
        }
        list.clear();
        for (int i = 0; i < n; i++) {
            list.add(heap.remove());
        }
        return list;
    }

    /**
     * Fills a list with random nonnegative integers.
     *
     * @param n The quantity of random numbers desired.
     * @param bound The upper bound on the range of random integers generated.
     * @return A list of random nonnegative integers in the range [0, bound -
     * 1].
     */
    public ArrayList<Integer> fillRandom(int n, int bound) {
        Random rng = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(rng.nextInt(bound));
        }
        return list;
    }

    /**
     * A method to demonstrate and test the various sorts.
     * Constructs seven ArrayLists, each of size 15 and initially containing
     * random integers in the range [0, 999]. Each list is printed both before 
     * and after sorting.
     */
    public void runSortingDemo() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Integer> temp = this.fillRandom(10, 1000);
            list.add(temp);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ": Before sorting: " + list.get(i) + "\n");
            System.out.print("After sorting with ");

            if (i == 0) {
                System.out.println("bubble sort: " + this.bubbleSort(list.get(0)));
            }
            else if (i == 1) {
                System.out.println("insertion sort: " + this.insertionSort(list.get(1)));
            }
            else if (i == 2) {
                System.out.println("selection sort: " + this.selectionSort(list.get(2)));
            }
            else if (i == 3) {
                System.out.println("merge sort: " + this.mergeSort(list.get(3)));
            }
            else if(i == 4){
                System.out.println("shell sort: " + this.shellSort(list.get(4)));
            }
            else if(i == 5){
                System.out.println("quick sort: " + this.quickSort(list.get(5)));
            }
            else{
                System.out.println("heap sort: " + this.heapSort(list.get(6)));
            }
            System.out.println();
        }
    }

}
