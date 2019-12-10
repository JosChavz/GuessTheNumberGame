package edu.miracosta.cs113;

public class BubbleSort {

    /**
     * Sorts the given array from lowest to largest
     * Worst Case: O(n^2)
     * Best Case: O(n)
     * @param arr The array that will be sorted
     * @return The sorted array
     */
    public static int[] sort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            for(int x = 1; x < (arr.length - i); x++) {
                if(arr[x - 1] > arr[x]) arr = swap(x - 1, x, arr);
            }
        }
        return arr;
    }

    /** HELPING METHOD */
    private static int[] swap(int i, int x, int[] array) {
        int temp = array[i];
        array[i] = array[x];
        array[x] = temp;
        return array;
    }
}
