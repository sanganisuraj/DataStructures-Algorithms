import java.io.*;
public class sns_axd_Sort2 {
	static void MergeSort(int[] A, int[] B, int p, int r)
	{
		if(p<r)
		{
			int q=(p+r)/2;
			MergeSort(A, B, p, q);
            MergeSort(A, B, q+1, r);
            Merge(A, B, p, q, r);
		}
		else {  // Insertion sort
			for(int i=p, j=i; i<r; j=++i) {
			    int ai = A[i+1];
			    while(ai < A[j]) {
				A[j+1] = A[j];
				if (j-- == p) {
				    break;
				}
			    }
			    A[j+1] = ai;
		 	}
		    }
	
	}
	static void Merge(int[] A, int[] B, int p, int q, int r)
	{
		for(int i=p; i<=r; i++)
		{
			B[i-p]=A[i];
		}
	
		int i=p;
		int j=q+1;
		for(int k=p; k<=r; k++)
		{
			if(j>r || (i<=q) && (B[i]<=B[j]))
			{
				A[k]=B[i++];
			}
			else
			{
				A[k]=B[j++];
			}
			
		}
	}
	public static void main(String[] args) {
		int n = Integer.parseInt("33554432");
        int[] A = new int[n];
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = n-i;
        }
        long startTime = System.currentTimeMillis();
        MergeSort(A, B, 0, n-1);
        long endTime = System.currentTimeMillis();
        for (int j = 0; j < A.length-1; j++) {
            if(A[j] > A[j+1]) {
		System.out.println("Sorting failed :-(");
		return;
	    }
        }
	System.out.println("Success!");
	System.out.print(endTime-startTime);	
	}

}
