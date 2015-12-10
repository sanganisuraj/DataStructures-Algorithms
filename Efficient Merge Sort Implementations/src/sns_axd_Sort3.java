public class sns_axd_Sort3 {
	static int MergeSort(int[] A, int[] B, int p, int r)
	{
		if(p<r)
		{
			int q=(p+r)/2;
			int h1;
			h1=MergeSort(A, B, p, q);
			int h2;
			h2=MergeSort(A, B, q+1, r);
			if(h1!=h2)
			{
				System.out.println("Not a power of 2!");
			}
			if(h1%2==1)
			{
				Merge(B, A, p, q, r);
			}
			else
			{
				Merge(A, B, p, q, r);
			}
			
			return h1+1;
		}
		else return 0;
	}
	static void Merge(int[] src, int[] dest, int p, int q, int r)
	{
		int i=p; 
		int j=q+1;
		for(int k=p; k<=r; k++)
		{
			if((j>r) || (i<=q) && src[(int) i]<=src[(int) j])
			{
				dest[k]=src[i++];
			}
			else
			{
				dest[k]=src[j++];
			}
		}

	}
//  Code to calculate the nearest power of 2 for the input	
	public static int nextPowerOf2(final int a)
    {
        int b = 1;
        while (b < a)
        {
            b = b << 1;
        }
        return b;
    }
	
	public static void main(String[] args) {
	
		int n = Integer.parseInt("33554432");
		int a= nextPowerOf2(n);
        int[] A = new int[a];
        int[] B = new int[a];
        for (int i=0;i<a; i++)
        {
        	A[i]=a-i;
        }
        for(int i=0; i<a; i++ )
        {
        	System.out.println(A[i]);
        }
        long startTime = System.currentTimeMillis();
        MergeSort(A, B, 0, a-1);
        long endTime = System.currentTimeMillis();
        int count=0;
        //Code to check whether the input is an odd or even power of 2
        while (((a % 2) == 0) && a > 1) 
	   {
        	a/= 2;
        	count++;
	   }
//			Code to print sorted array        
//        if(count%2==1)             
//        {
//			for(int i=0; i<n;i++)
//        {
//        	System.out.println(B[i]);
//        } 
//}
//else
//{
//      for(int i=0; i<n; i++)
//        {
//        	System.out.println(A[i]);
//        }
//}
	System.out.println("Success!");
	System.out.print(endTime-startTime);
	}

}
