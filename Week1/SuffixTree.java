import java.util.*;
import java.io.*;
public class SuffixTree
{


	/************************ SOLUTION STARTS HERE ***********************/

	static String text;
	static int len;

	static class Pair {
		int index , start , length;
		Pair(int index , int start,int length){
			this.index = index;
			this.start = start;
			this.length = length;
		}
		@Override
		public String toString() {
			return "start " + start + " len " + length + " s = " + text.substring(start, start + length) + " i " + index;
		}
	}
	static ArrayList<HashMap<Character,Pair>> tree = new ArrayList<>();
	static{
		tree.add(new HashMap<>());
	}


	
	static void add(int idx_text,int idx_tree){

		Pair p = tree.get(idx_tree).get(text.charAt(idx_text));
		if(p == null){
			p = new Pair(tree.size(), idx_text, len - idx_text);
			tree.add(new HashMap<>());
			tree.get(idx_tree).put(text.charAt(idx_text), p);
		}
		else{
			for(int i = idx_text , j = p.start;i < len && j < p.start + p.length;i++,j++){
				if(text.charAt(i) != text.charAt(j)){
					Pair p1 = new Pair(tree.size(), j, p.start + p.length - j);
					tree.add(null);
					tree.set(p1.index, tree.get(p.index));
					tree.set(p.index, new HashMap<>());
					Pair p2 = new Pair(tree.size(), i, len - i);
					p.length = j - p.start;
					tree.add(new HashMap<>());
					tree.get(p.index).put(text.charAt(j), p1);
					tree.get(p.index).put(text.charAt(i), p2);
					return;
				}
			}
			add(idx_text + p.length, p.index);
		}
	}

	static void print(int idx_tree,PrintWriter out){
		for(Map.Entry<Character, Pair> e : tree.get(idx_tree).entrySet()){
			Pair p = e.getValue();
			out.println(text.substring(p.start, p.start + p.length));
			print(p.index,out);
		}
	}

	private static void solve(FastScanner s1, PrintWriter out){

		text = s1.nextLine();
		len = text.length();

		//long start = System.nanoTime();
		for(int i = len - 1;i >= 0;i--)
			add(i, 0);	
		
		print(0,out);
		
		//long stop = System.nanoTime();
		//System.err.println("Time elapsed : " + ((stop - start) / 1e9) + "s");
	}

	/************************ SOLUTION ENDS HERE ************************/





	/************************ TEMPLATE STARTS HERE *********************/

	public static void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
			public void run() {
				new SuffixTree().run();
			}
		}, "Increase Stack", 1L << 25).start();
	}

	void run(){	
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

