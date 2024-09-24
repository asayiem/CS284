/**
 * Abu Sayiem
 * CS 284 E
 * I pledge that I abide by the Stevens Honor System
 */


     /**
     * Sorts an array of integers using the Counting Sort algorithm.
     * 
     * This method modifies the input array in-place to a sorted order.
     * If the array is null or has less than two elements, the method returns immediately with no modifications.
     * 
     * 
     * @param A the array of integers to be sorted
     */
public class CountingSort {
    public static void sort(int[] A) {
        if (A == null || A.length <= 1) {
            return;  // No need to sort
        }

        // Find the maximum value to determine the range of the auxiliary array C
        int k = 0;
        for (int num : A) {
            if (num > k) {
                k = num;
            }
        }

        // Initialize auxiliary array C
        int[] C = new int[k + 1];
        
        // Count each element
        for (int j = 0; j < A.length; j++) {
            C[A[j]]++;
        }
        
        // Modify C to store the number of elements <= i
        for (int i = 1; i <= k; i++) {
            C[i] += C[i - 1];
        }
        
        // Build the output array B
        int[] B = new int[A.length];
        for (int j = A.length - 1; j >= 0; j--) {
            B[C[A[j]] - 1] = A[j];
            C[A[j]]--;
        }
        
        // Copy sorted elements back to original array A
        System.arraycopy(B, 0, A, 0, A.length);
    }

    public static void main(String[] args) {
        int[] A = {2, 5, 3, 0, 2, 3, 0, 3};
        System.out.println("Original array:");
        for (int i : A) {
            System.out.print(i + " ");
        }
        System.out.println();

        sort(A);

        System.out.println("Sorted array:");
        for (int i : A) {
            System.out.print(i + " ");
        }
    }
}
