package edu.miracosta.cs113;

import java.util.ArrayList;

public class BubbleSort {

    /**
     * Sorts the given array from lowest to largest
     * Worst Case: O(n^2)
     * Best Case: O(n)
     * @param arr The array that will be sorted
     * @return The sorted array
     */
    public static ArrayList<Player> sort(ArrayList<Player> arr) {
        for(int i = 0; i < arr.size(); i++) {
            for(int x = 1; x < (arr.size() - i); x++) {
                if(arr.get(x - 1).getScore() > arr.get(x).getScore()) arr = swap(x - 1, x, arr);
            }
        }
        return arr;
    }

    /** HELPING METHOD */
    private static ArrayList<Player> swap(int i, int x, ArrayList<Player> array) {
        Player temp = array.get(i);
        array.set(i, array.get(x));
        array.set(x, temp);
        return array;
    }
}
