package edu.ser222.m02_01;
import java.util.Random;

/**
 * 3 methods generate test data into an Integer array and then a benchmark is completed
 * by sorting the data using 2 different sorting algorithms, insertion sort and 
 * shell sort.
 * 
 * Completion time: 4 hours
 *
 * @author Borys Banaszkiewicz, Acuna, Sedgewick
 * @version 1.0
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.
    
    public CompletedBenchmarkTool() {

    }
    /*
    * Generates an array of integers where half the data is 0s, half 1s.
    
     * @param size number of elements in the array.
     * @return generated test set.
    */
    @Override
    public Integer[] generateTestDataBinary(int size) {
        int zeros = size / 2;
        
        Integer[] arr = new Integer[size];
        
        int n = size;
        
        for (int i = 0; i < n; i++) {
            if (zeros > 0) {
                arr[i] = 0;
                zeros--;
            } else {
                arr[i] = 1;
            }
        }
        
        return arr;
    }
    
    /**
     * Generates an array of integers where half the data is 0s, half the
     * remainder is 1s, half the reminder is 2s, half the reminder is 3s, and so
     * forth. 
     * 
     * @param size number of elements in the array.
     * @return generated test set.
     */
    @Override
    public Integer[] generateTestDataHalves(int size) {
        Integer[] arr = new Integer[size];
        int num = 0;
        int count = 1;
        int remainder = size / 2;
        
        for (int i = 0; i < size; i++) {
            arr[i] = num;
            
            if (i == remainder - 1 && remainder > 0) {
                num++;
                remainder += (size - remainder) / 2;  // Calculate the next threshold
            }
        }
        return arr;
    }
    
    /**
     * Generates an array of integers where half the data is 0s, and half random
     * int values. All values will be positive.
     * @param size
     * @return 
     */
    @Override
    public Integer[] generateTestDataHalfRandom(int size) {
        int n = size / 2;
        Integer[] arr = new Integer[size];
        Random rand = new Random();
        
        for (int i = 0; i < size; i++) {
            if (i >= n) {
                arr[i] = rand.nextInt(Integer.MAX_VALUE);
            }
            else {
                arr[i] = 0;
            }
        }
        
        return arr;
    }
    
    /**
     * Computes the double formula value for two run times.
     * 
     * @param t1 first time
     * @param t2 second time
     * @return b value
     */
    @Override
    public double computeDoublingFormula(double t1, double t2) {
        double result = Math.log(t2/t1) / Math.log(2);
        return result;
    }
    
    /**
     * Computes an empirical b value for insertion sort by running it on a pair
     * of inputs and using the doubling formula.
     * 
     * @param small small test data array
     * @param large large test data array. twice the same of small array.
     * @return b value
     */
    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
        Stopwatch stopwatch_small = new Stopwatch();
        insertionSort(small);
        double time_small = stopwatch_small.elapsedTime();
        
        Stopwatch stopwatch_large = new Stopwatch();
        insertionSort(large);
        double time_large = stopwatch_large.elapsedTime();
        
        return computeDoublingFormula(time_small, time_large);
    }
    
    /**
     * Computes an empirical b value for shellsort sort by running it on a pair
     * of inputs and using the doubling formula.
     * @param small small test data array
     * @param large large test data array. twice the same of small array.
     * 
     * @return b value
     */
    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large) {
        Stopwatch stopwatch_small = new Stopwatch();
        shellsort(small);
        double time_small = stopwatch_small.elapsedTime();
        
        Stopwatch stopwatch_large = new Stopwatch();
        shellsort(large);
        double time_large = stopwatch_large.elapsedTime();
        
        return computeDoublingFormula(time_small, time_large);
    }
    
    /**
     * Runs the two sorting algorithms on the three types of test data to
     * produce six different b values. B values are displayed to the user.
     * 
     * @param size size of benchmark array. to be doubled later.
     */
    @Override
    public void runBenchmarks(int size) {
        double[] results = new double[6];
        
        BenchmarkTool bm = new CompletedBenchmarkTool();
        
        Integer[] arr_binary_insertion = bm.generateTestDataBinary(size);
        Integer[] arr_halves_insertion = bm.generateTestDataHalves(size);
        Integer[] arr_random_insertion = bm.generateTestDataHalfRandom(size);
        
        Integer[] arr_binary_insertion_2 = bm.generateTestDataBinary(size*2);
        Integer[] arr_halves_insertion_2 = bm.generateTestDataHalves(size*2);
        Integer[] arr_random_insertion_2 = bm.generateTestDataHalfRandom(size*2);

        Integer[] arr_binary_shell = copyArray(arr_binary_insertion);
        Integer[] arr_halves_shell = copyArray(arr_halves_insertion);
        Integer[] arr_random_shell = copyArray(arr_random_insertion);
        
        Integer[] arr_binary_shell_2 = copyArray(arr_binary_insertion_2);
        Integer[] arr_halves_shell_2 = copyArray(arr_halves_insertion_2);
        Integer[] arr_random_shell_2 = copyArray(arr_random_insertion_2);
        
        for (int i = 0; i < 5; i++) { 
            results[0] += bm.benchmarkInsertionSort(arr_binary_insertion, arr_binary_insertion_2);
            results[1] += bm.benchmarkInsertionSort(arr_halves_insertion, arr_halves_insertion_2);
            results[2] += bm.benchmarkInsertionSort(arr_random_insertion, arr_random_insertion_2);
            arr_random_insertion = bm.generateTestDataHalfRandom(size);
            arr_random_insertion_2 = bm.generateTestDataHalfRandom(size*2);
            
            results[3] += bm.benchmarkShellsort(arr_binary_shell, arr_binary_shell_2);
            results[4] += bm.benchmarkShellsort(arr_halves_shell, arr_halves_shell_2);
            results[5] += bm.benchmarkShellsort(arr_random_shell, arr_random_shell_2);
            arr_random_shell = copyArray(arr_random_insertion);
            arr_random_shell_2 = copyArray(arr_random_insertion_2);
        }
        
        results[0] /= 5;
        results[1] /= 5;
        results[2] /= 5;
        results[3] /= 5;
        results[4] /= 5;
        results[5] /= 5;
        
        System.out.println("\t\tInsertion\tShellsort");
        System.out.printf("Bin\t\t%.3f\t\t%.3f\n", results[0],results[3]);
        System.out.printf("Half\t\t%.3f\t\t%.3f\n", results[1],results[4]);
        System.out.printf("RanInt\t\t%.3f\t\t%.3f\n", results[2],results[5]);
        System.out.println();
        
    }
    private Integer[] copyArray(Integer[] original) {
        if (original == null) {
            return null;
        }

        Integer[] copy = new Integer[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }

        return copy;
    }
    
    
    public static void main(String args[]) {
        
        int size = (int)Math.pow(2,17);
        BenchmarkTool me = new CompletedBenchmarkTool();
        me.runBenchmarks(size);
        
    }
}