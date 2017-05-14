import java.util.*;
import java.io.*;
public class KMP
{
	
	
	/************************ SOLUTION STARTS HERE ***********************/
	
	
	static int[] prefix(String str){
		int N = str.length();
		int border = 0;
		int prefix[] = new int[N];
		
		for(int i=1;i<N;i++){
			
			while(border != 0 && str.charAt(border) != str.charAt(i))
				border = prefix[border - 1];
			
			if(str.charAt(border) == str.charAt(i))
				prefix [i] = ++border;
		
		}
		
		return prefix;
	}
	
	private static void solve(FastScanner s1, PrintWriter out){ // Time : 0.55 s
		
		String pattern = s1.nextLine();
		String text = s1.nextLine();
		int patLen = pattern.length();
		int textLen = text.length();
		int prefix[] = prefix(pattern.concat("$").concat(text));
		
		for(int i=0;i<textLen;i++){
			int j = i + patLen + 1;
			if(prefix[j] == patLen)
				out.print((j - (2 * patLen)) + " "); 
			/*
			 * 
			 * bhis$bhishma
			 *         j
			 */
			
		}
		
	}
	
	static void slowSolve(){  // Time : 3.14 s
		Scanner s1 = new Scanner(System.in);
		String pattern = s1.nextLine();
		String text = s1.nextLine();
		int patLen = pattern.length();
		int textLen = text.length();
		int prefix[] = prefix(pattern.concat("$").concat(text));
		
		for(int i=0;i<textLen;i++){
			int j = i + patLen + 1;
			if(prefix[j] == patLen)
				System.out.print((j - (2 * patLen)) + " "); 
			
		}
		s1.close();
	}
	
	
	/************************ SOLUTION ENDS HERE ************************/
	
	
	
	
	
	/************************ TEMPLATE STARTS HERE *********************/
	
	public static void main(String []args) throws IOException {
		FastScanner in  = new FastScanner(System.in);
		PrintWriter out = 
				new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false); 
		solve(in, out);
		//slowSolve();
		in.close();
		out.close();
	}    
	
	static class FastScanner{
		BufferedReader reader;
		StringTokenizer st;
		FastScanner(InputStream stream){reader=new BufferedReader(new InputStreamReader(stream));st=null;}	
		String next()
		{while(st == null || !st.hasMoreTokens()){try{String line = reader.readLine();if(line == null){return null;}		    
		st = new StringTokenizer(line);}catch (Exception e){throw new RuntimeException();}}return st.nextToken();}
		String nextLine()  {String s=null;try{s=reader.readLine();}catch(IOException e){e.printStackTrace();}return s;}	    	  	
		int    nextInt()   {return Integer.parseInt(next());}
		long   nextLong()  {return Long.parseLong(next());}		
		double nextDouble(){return Double.parseDouble(next());}
		char   nextChar()  {return next().charAt(0);}
		int[]  nextIntArray(int n)         {int[] arr= new int[n];   int i=0;while(i<n){arr[i++]=nextInt();}  return arr;}
		long[] nextLongArray(int n)        {long[]arr= new long[n];  int i=0;while(i<n){arr[i++]=nextLong();} return arr;}	
		int[]  nextIntArrayOneBased(int n) {int[] arr= new int[n+1]; int i=1;while(i<=n){arr[i++]=nextInt();} return arr;}	    	
		long[] nextLongArrayOneBased(int n){long[]arr= new long[n+1];int i=1;while(i<=n){arr[i++]=nextLong();}return arr;}	    	
		void   close(){try{reader.close();}catch(IOException e){e.printStackTrace();}}				
	}
	
	/************************ TEMPLATE ENDS HERE ************************/
}