import java.util.*;
import java.io.*;

/**
 * 
 * @author Suraj Sangani & Amrita Dasgupta
 * This is Question 1.
 *
 */
public class Project2_Question1_Suraj_Sangani_Amrita_Dasgupta {

	static int count = 0; // This is for counting the number of permutations or
							// combinations done.
	static int[] B;
	static int[] C;

	/**
	 * This function recursively permutes the array of integers in Array B.
	 * 
	 * @param B
	 *            Array of integers which are to be permuted.
	 * @param i
	 *            Length of the array.
	 * @param v
	 *            Version of the program, the user is interested in.
	 */
	public static void Permute(int[] B, int i, int v) {
		if (i == 0) {
			visit(C, C.length, v);
		} else {
			int k;
			int n = i - 1;
			for (k = 0; k < C.length; k++) {
				if (C[k] == 0) {
					C[k] = B[n];
					Permute(B, i - 1, v);
					C[k] = 0;
				}

			}
		}
	}

	/**
	 * This function is used specifically for selecting one arrangement at a
	 * time and then calls the permute method for permutations. This is used for
	 * verbose value 0 and 2
	 * 
	 * @param A
	 *            Array of integers which are to be arranged.
	 * @param i
	 *            Length of the array.
	 * @param k
	 *            Number of selections out of n numbers.
	 * @param v
	 *            Version of the program, the user is interested in.
	 */
	public static void Combine(int[] A, int i, int k, int v) {
		int j = i - 1;
		if (k == 0) {
			int s = 0;
			for (int m = 0; m < A.length; m++) {
				if (A[m] > 0)
					B[s++] = A[m];
			}
			Permute(B, B.length, v);
		} else if (i < k)
			return;
		else {
			A[j] = i;
			Combine(A, i - 1, k - 1, v);
			A[j] = 0;
			Combine(A, i - 1, k, v);
		}
	}

	/**
	 * This function is used for finding various arrangements of the numbers.
	 * This is used for verbose values 1 and 3.
	 * 
	 * @param A
	 *            Array of integers which are to be arranged.
	 * @param i
	 *            Length of the array.
	 * @param k
	 *            Number of selections out of n numbers.
	 * @param v
	 *            Version of the program, the user is interested in.
	 */
	public static void Combines(int[] A, int i, int k, int v) {
		int j = i - 1;
		if (k == 0) {
			visit(A, A.length, v);
		} else if (i < k)
			return;
		else {

			A[j] = i;
			Combines(A, i - 1, k - 1, v);
			A[j] = 0;
			Combines(A, i - 1, k, v);

		}
	}

	/**
	 * This function is called to count the number of permutations/combinations
	 * and to print them.
	 * 
	 * @param A
	 *            Array of integers which are to be printed.
	 * @param n
	 *            The length of the array to be printed.
	 * @param verbose
	 *            Depending on the version of the program, either count will be
	 *            returned or the array will be printed.
	 * @return
	 */
	static int visit(int[] A, int n, int verbose) {
		if (verbose > 1) {
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
		int n, k, v;
		while (true) {
			Scanner in = new Scanner(System.in);
			System.out.println("Enter value of n"); // The set of distinct
													// objects.
			n = in.nextInt();
			int A[] = new int[n];
			for (int i = 0; i < n; i++) {
				A[i] = 0;
			}
			System.out.println("Enter value of k");// The number of objects out
													// of n which are to
													// permuted/combined.
			k = in.nextInt();

			B = new int[k];
			C = new int[k];
			System.out.println("Enter value of v");// Version of the program the
													// user is interested in.
			v = in.nextInt();
			long startTime;
			long endTime;
			switch (v) {
			/**
			 * Printing the number of permutations of k objects out of n
			 * distinct objects
			 */
			case 0:
				startTime = System.currentTimeMillis();
				Combine(A, n, k, v);
				endTime = System.currentTimeMillis();
				System.out.print(count + " " + (endTime - startTime) + "\n");
				count = 0;
				break;

			/**
			 * Printing the number of combinations of k objects out of n
			 * distinct objects
			 */
			case 1:
				startTime = System.currentTimeMillis();
				Combines(A, n, k, v);
				endTime = System.currentTimeMillis();
				System.out.print(count + " " + (endTime - startTime) + "\n");
				count = 0;
				break;

			/**
			 * Printing the permutations of k objects out of n distinct objects
			 */
			case 2:
				startTime = System.currentTimeMillis();
				Combine(A, n, k, v);
				endTime = System.currentTimeMillis();
				System.out.print(count + " " + (endTime - startTime) + "\n");
				count = 0;
				break;

			/**
			 * Printing the combinations of k objects out of n distinct objects
			 */
			case 3:
				startTime = System.currentTimeMillis();
				Combines(A, n, k, v);
				endTime = System.currentTimeMillis();
				System.out.print(count + " " + (endTime - startTime) + "\n");
				count = 0;
				break;

			/**
			 * Invalid Input
			 */
			default:
				if (v >= 4) {
					System.exit(0);
				}

			}

		}
	}
}
