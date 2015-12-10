import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Amrita Dasgupta and Vaibhav Shah and Suraj Sangani
 *
 */
public class StringMatching {

	static String pattern = "";
	static String text = "";
	static long patHash;
	private final static long base = Character.MAX_VALUE + 1;

	private final static long q = 88469;
	static int[] next;
	static List<Integer> list = new ArrayList<Integer>();

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {

		FileReader f1 = new FileReader(args[1]);

		BufferedReader br = new BufferedReader(f1);
		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		text = sb.toString();
		pattern = args[0];
		naive(text, pattern);
		RabinKarp(text.toCharArray(), pattern.toCharArray(), base, q);
		KnuthMorrisPratt(text, pattern);
		BoyerMoore(text, pattern);
		br.close();
		

	}
	/**
	 * Function to find out the number of valid matches of the pattern within the text using the Naive Method
	 * @param text
	 * @param pattern
	 */
	public static void naive(String text, String pattern) {
		long startT = System.currentTimeMillis();
		int p = pattern.length();
		int t = text.length();
		int count = 0;
		//Iterate from start to the text.length - pattern.length
		for(int i=0; i<=t-p; i++) {
			int j;
			for(j=0; j<p; j++) {
				if(text.charAt(i+j)!=pattern.charAt(j)) //check match for each character
					break;
			}
			//found match
			if(j==p) {
				count++;
			}
		}

		long endT = System.currentTimeMillis();
		System.out.print(count + " <" + (endT - startT) + " msec> ");
	}
	
	/**
	 * Function to find out the number of valid matches of the pattern within the text using the Rabin Karp Algorithm
	 * @param text
	 * @param pattern
	 * @param base = Character.MAXVALUE + 1
	 * @param q = large prime number chosen 
	 */
	public static void RabinKarp(char[] text, char[] pattern, long base, long q) {
		long startT = System.currentTimeMillis();
		
		int m = pattern.length;
		if (m>text.length) System.out.println(); //if pattern is greater than text
		LinkedList<Integer> validshift = new LinkedList<>();
		long p = 0;
		long t = 0;
		for(int i = 0; i < m; i++) {
			p = (p*base + pattern[i])%q;
			t = (t*base + text[i])%q;
		}
		long mult = powMod(base, m-1, q);
		int textminuspattern = text.length - m;
		for(int i=0; i<=textminuspattern; i++) {
			if(p==t)
				if(text.length-i<pattern.length)
					validshift.add(i);
			for(int j = 0; j<pattern.length; j++)
				if (text[i+j]!=pattern[j])
					validshift.add(i);
			if (i<textminuspattern) {
				t = t-mult*text[i];
				while (t<0)
					t=t+q;
				t=(base*t + text[i+m])%q;
			}
		}
		long endT = System.currentTimeMillis();
		System.out.print("<" + (endT - startT) + " msec> ");
	}

	/**
	 * Computes the power of base to m modulo q.
	 * 
	 * @param base
	 * @param m
	 * @param q
	 * @return base^n mod q.
	 */
	private static long powMod(long base, long m, long q) {
		if(m==0)
			return 1;
		if(m==1)
			return base % q;
		long j = powMod(base, m/2, q);
		j=(j*j)%q;
		if(m%2==0)
			return j;
		return ((j*base)%q);
	}

	private static int[] failure;

	public static void KnuthMorrisPratt(String text, String pat) {

		long startT = System.currentTimeMillis();
		failure = new int[pat.length()];
		fail(pat);

		int pos = posMatch(text, pat);
		long endT = System.currentTimeMillis();
		System.out.print("<" + (endT - startT) + " msec> ");
	}

	/**
	 * 
	 * @param pat
	 */
	private static void fail(String pat) {
		int n = pat.length();
		failure[0] = -1;
		for (int j = 1; j < n; j++) {
			int i = failure[j - 1];
			while ((pat.charAt(j) != pat.charAt(i + 1)) && i >= 0)
				i = failure[i];
			if (pat.charAt(j) == pat.charAt(i + 1))
				failure[j] = i + 1;
			else
				failure[j] = -1;
		}
	}

	/**
	 * 
	 * @param text
	 * @param pat
	 * @return
	 */
	private static int posMatch(String text, String pat) {
		int i = 0, j = 0;
		int lens = text.length();
		int lenp = pat.length();
		int count = 0;
		while (i < lens && j < lenp) {
			if (text.charAt(i) == pat.charAt(j)) {
				i++;
				j++;
			}
			if (j == lenp) {
				j = failure[j - 1] + 1;
				count++;
			}

			else if (i < lens && pat.charAt(j) != text.charAt(i)) {
				if (j != 0)
					j = failure[j - 1] + 1;
				else
					i = i + 1;
			}
		}

		return count;
	}

	/**
	 * 
	 * @param t
	 * @param p
	 */
	public static void BoyerMoore(String t, String p) {
		long startT = System.currentTimeMillis();
		char[] text = t.toCharArray();
		char[] pattern = p.toCharArray();
		int pos = indexOf(text, pattern);
		long endT = System.currentTimeMillis();
		System.out.println("<" + (endT - startT) + " msec>");
		for (int m = 0; m < list.size(); m++) {
			System.out.print(list.get(m) + " ");
		}

	}

	/**
	 * 
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static int indexOf(char[] text, char[] pattern) {
		int count = 0;
		if (pattern.length == 0)
			return 0;
		int charTable[] = makeCharTable(pattern);
		int offsetTable[] = makeOffsetTable(pattern);
		for (int i = pattern.length - 1, j; i < text.length;) {
			for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j) {

				if (j == 0) {
					count++;
					list.add(i);
					break;
				}
			}

			i += Math.max(offsetTable[pattern.length - 1 - j],
					charTable[text[i]]);

		}
		return count;
	}

	/**
	 * 
	 * @param pattern
	 * @return
	 */
	private static int[] makeCharTable(char[] pattern) {
		final int ALPHABET_SIZE = 256;
		int[] table = new int[ALPHABET_SIZE];
		for (int i = 0; i < table.length; ++i)
			table[i] = pattern.length;
		for (int i = 0; i < pattern.length - 1; ++i)
			table[pattern[i]] = pattern.length - 1 - i;
		return table;
	}

	/**
	 * 
	 * @param pattern
	 * @return
	 */
	private static int[] makeOffsetTable(char[] pattern) {
		int[] table = new int[pattern.length];
		int lastPrefixPosition = pattern.length;
		for (int i = pattern.length - 1; i >= 0; --i) {
			if (isPrefix(pattern, i + 1))
				lastPrefixPosition = i + 1;
			table[pattern.length - 1 - i] = lastPrefixPosition - i
					+ pattern.length - 1;
		}
		for (int i = 0; i < pattern.length - 1; ++i) {
			int slen = suffixLength(pattern, i);
			table[slen] = pattern.length - 1 - i + slen;
		}
		return table;
	}

	/**
	 * 
	 * @param pattern
	 * @param p
	 * @return
	 */
	private static boolean isPrefix(char[] pattern, int p) {
		for (int i = p, j = 0; i < pattern.length; ++i, ++j)
			if (pattern[i] != pattern[j])
				return false;
		return true;
	}

	/**
	 * function to returns the maximum length of the substring ends at p and is
	 * a suffix
	 **/
	private static int suffixLength(char[] pattern, int p) {
		int len = 0;
		for (int i = p, j = pattern.length - 1; i >= 0
				&& pattern[i] == pattern[j]; --i, --j)
			len += 1;
		return len;
	}
}
