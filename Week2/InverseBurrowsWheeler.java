import java.util.*;
import java.io.*;
public class InverseBurrowsWheeler
{
	
	
	/************************ SOLUTION STARTS HERE ***********************/
	
	static class Pair {
		char ch;
		int pos;
		Pair(char ch, int pos){
			this.ch = ch;
			this.pos = pos;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void solve(FastScanner s1, PrintWriter out){
		
		ArrayList<Integer>[] adj = new ArrayList[26];
		for(int i=0;i<26;i++)
			adj[i] = new ArrayList<>();
		
		char bwText[] = s1.nextLine().toCharArray();
		char firstCol[] = Arrays.copyOf(bwText, bwText.length);
		
		Arrays.sort(firstCol);
		ArrayDeque<Character> stack = new ArrayDeque<>();
		stack.push('$');
		int N = bwText.length;
		for(int i=0;i<N;i++)
			if(firstCol[i] != '$')
				adj[firstCol[i] - 'A'].add(i);
		
		int cnt[] = new int[26];
		Pair arr[] = new Pair[N];
		for(int i=0;i<N;i++){
			if(bwText[i] != '$')
				arr[i] = new Pair(bwText[i], cnt[bwText[i] - 'A']++);
			else
				arr[i] = new Pair(bwText[i], -1);
		}
		Pair curr = arr[0];
		while(curr.ch != '$'){
			stack.push(curr.ch);
			curr = arr[adj[curr.ch - 'A'].get(curr.pos)];
		}
		
		for(char ch:stack)
			out.print(ch);
	}
	
	
	
	/************************ SOLUTION ENDS HERE ************************/
	
	
	
	
	
	/************************ TEMPLATE STARTS HERE *********************/
	
	public static void main(String []args) throws IOException {
		FastScanner in  = new FastScanner(System.in);
		PrintWriter out = 
				new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false); 
		solve(in, out);
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