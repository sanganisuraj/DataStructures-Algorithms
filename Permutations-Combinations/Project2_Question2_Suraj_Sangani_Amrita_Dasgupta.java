import java.util.*;
import java.io.*;

/**
 * 
 * @author Suraj Sangani & Amrita Dasgupta
 * This is Question 2
 *
 */
public class Project2_Question2_Suraj_Sangani_Amrita_Dasgupta {

	// This is to keep count of number of permutations done.
	static int count = 0;

	/**
	 * Function to calculate lexicographic permutations of the input number as
	 * it can have duplicates
	 * 
	 * @param L
	 *            Array of integers sent from the main method which was input by
	 *            user.
	 * @param v
	 *            The verbose value that was input by user.
	 */
	public static void lexicoPerm(int[] L, int v) {

		// set the sentinel value for checking that no negative numbers are
		// allowed.
		int a0 = 0;
		if (L[0] < a0)
			return;

		// visit the first permutation
		visit(L, L.length, v);

		int j, l;
		while (true) {

			j = L.length - 2;
			l = L.length - 1;

			// find the index which has smaller value compared to the next index
			// while reading
			// from right to left
			for (; j >= 0; j--) {
				if (L[j] < L[j + 1])
					break;

			}

			// break out of the while loop after the last permutation denoted
			// when j = 0.
			if (j < 0) {

				break;
			}

			// find the index greater than jth index value reading from right to
			// left.
			for (; l >= 0; l--) {

				if (L[j] < L[l]) {

					break;

				}
			}

			// swap the two values
			int tmp = L[j];
			L[j] = L[l];
			L[l] = tmp;

			// reverse the values from j+1st position till the end
			reverse(L, j + 1, L.length);
			// visit this new permutation created.
			visit(L, L.length, v);

		}

	}

	/**
	 * function to reverse the array form start position to end-1.
	 * 
	 * @param L
	 * @param start
	 * @param end
	 */
	public static void reverse(int[] L, int start, int end) {

		int k = start;
		int l = end - 1;

		while (k < l) {

			if (k < l) {
				int tmp = L[k];
				L[k] = L[l];
				L[l] = tmp;

			}
			k++;
			l--;

		}
	}

	/**
	 * function to either print each element in the array after each permutation
	 * (if verbose = 1) else to return the count value after increasing it
	 * (verbose =0).
	 * 
	 * @param A
	 *            array with integers
	 * @param n
	 *            length of the array
	 * @param v
	 *            verbose value
	 * @return
	 */
	static int visit(int[] A, int n, int v) {
		int verbose = v;
		if (verbose > 0) {
			for (int i = 0; i < n; i++) {
				if (A[i] > 0)
					System.out.print(A[i] + " ");
			}
			System.out.println();
		}
		return count++;
	}

	/**
	 * Main function
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// n is the size of the number to be input from user;
		// v is the verbose value (2 cases 0 and 1)
		int n, v;
		// take user input
		Scanner in = new Scanner(System.in);
		System.out.println("Enter value of n");
		n = in.nextInt();
		// create the array of the size n
		int[] Lex = new int[n];

		// get the verbose value from user
		// for v = 0 output will have only count of permutations.
		// for v = 1 output will have all the values of permutations.
		System.out.println("Enter value of v");
		v = in.nextInt();

		System.out.println("Enter the numbers for each index and press enter");

		for (int i = 0; i < n; i++) {
			System.out.println("Enter for index " + i);
			Lex[i] = in.nextInt();
		}

		// sort the integer array to get the minimum value for the input
		// integers
		Arrays.sort(Lex);
		long startTime = System.currentTimeMillis();
		// do lexicographic permutation (may or may not have duplicates)
		lexicoPerm(Lex, v);
		long endTime = System.currentTimeMillis();
		System.out.println(count + " " + (endTime - startTime));

	}

}
