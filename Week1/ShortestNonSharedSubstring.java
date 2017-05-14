import java.util.*;
import java.io.*;
public class ShortestNonSharedSubstring
{


	/************************ SOLUTION STARTS HERE ***********************/

	static void generateTestCase(){

		Random r = new Random();
		int runs = 3000;
		int MAX_LEN = 5;
		while(runs-->0){
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			for(int i=0;i<MAX_LEN;i++){
				sb1.append((char)('A' + r.nextInt(2)));
				sb2.append((char)('A' + r.nextInt(2)));
			}
			//System.out.println(sb1);
			//System.out.println(sb2);
			if(sb1.toString().equals(sb2.toString()))
				continue;
			String ans = getAnswer(sb1.toString(), sb2.toString());
			if(!testAnswer(sb1.toString(), sb2.toString(), ans)){
				System.out.println("Wrong ANswer");
				System.out.println(sb1 + "\n" + sb2);
				System.out.println("WA : " + ans);
			}
			else
				System.out.println("PASS");
		}
	}
	static boolean testAnswer(String t1 , String t2 , String ans){
		HashSet<String> set1 = new HashSet<>();
		HashSet<String> set2 = new HashSet<>();
		int l1 = t1.length();
		int l2 = t2.length();
		for(int i=0;i<l1;i++)
			for(int j=i + 1;j<=l1;j++)
				set1.add(t1.substring(i, j));

		for(int i=0;i<l2;i++)
			for(int j=i+1;j<=l2;j++)
				set2.add(t2.substring(i, j));

		int min = Integer.MAX_VALUE;
		for(String s:set1)
			min = !set2.contains(s) ? Math.min(min,s.length()) : min;

			return ans.length() == min && set1.contains(ans);
	}
	static String text;
	static int len;
	static int l1 , l2;
	static class Pair {
		int index , start , length;
		Pair(int index , int start,int length){
			this.index = index;
			this.start = start;
			this.length = length;
		}
		@Override
		public String toString() {
			return "start " + start + " len " + length + " s = " + text.substring(start, start + length) + " i " ;
		}
	}
	static ArrayList<HashMap<Character,Pair>> tree;
	static ArrayDeque<String> stack;
	static boolean flag[];

	static int idx_end;
	static int min_l = Integer.MAX_VALUE;
	static boolean mark(int idx_tree, Pair curr){

		if(tree.get(idx_tree).size() == 0){
			return curr.start > l1;
		}
		else{
			for(Map.Entry<Character, Pair> e : tree.get(idx_tree).entrySet())
				flag[curr.index] |= mark(e.getValue().index, e.getValue());

			return flag[curr.index];
		}
	}

	static int temp = 0;
	static void printNonSharedSubStrings(int idx_tree,Pair curr,int curr_len,boolean choosed , int start){
		
		if(curr.start >= 0 && curr.start < l1 && !flag[curr.index]){
			int sub_l = curr_len + 1 - curr.length;
			if(sub_l < min_l){
				min_l = sub_l;
				idx_end = curr.index;
			}
		}

		for(Map.Entry<Character, Pair> e : tree.get(idx_tree).entrySet()){
			Pair p = e.getValue();
			printNonSharedSubStrings(p.index, p , curr_len + p.length , true, !choosed ? p.start : start);
		}
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
					p.start = idx_text;
					tree.add(new HashMap<>());
					tree.get(p.index).put(text.charAt(j), p1);
					tree.get(p.index).put(text.charAt(i), p2);
					return;
				}
			}
			p.start = idx_text;
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
	
	static boolean findAnswer(Pair curr){
		
		if(curr.index == idx_end){
			stack.push(String.valueOf(text.charAt(curr.start)));
			return true;
		}
		else{
			boolean flag = false;
			for(Map.Entry<Character, Pair> e:tree.get(curr.index).entrySet()){
				flag |= findAnswer(e.getValue());
			}
			
			if(flag)
				stack.push(text.substring(curr.start, curr.start + curr.length));
			
			return flag;
		}
	}
	
	static String getAnswer(String t1, String t2){

		tree = new ArrayList<>();
		tree.add(new HashMap<>());
		l1 = t1.length();
		l2 = t2.length();
		stack = new ArrayDeque<>();
		text = t1.concat("$").concat(t2).concat("#");
		len = text.length();
		min_l = Integer.MAX_VALUE;
		for(int i = len - 1;i >= 0;i--)
			add(i, 0);	
		
		flag = new boolean[tree.size() + 10];
		mark(0, new Pair(0, 0, 0));
		printNonSharedSubStrings(0, new Pair(0, -1, 0),0,false,0);

		StringBuilder sb = new StringBuilder();
		findAnswer(new Pair(0, 0, 0));
		for(String s:stack)
			sb.append(s);
		
		return sb.toString();
	}
	private static void solve(FastScanner s1, PrintWriter out){
		
		tree = new ArrayList<>();
		tree.add(new HashMap<>());
		stack = new ArrayDeque<>();
		
		String t1 = s1.nextLine();
		String t2 = s1.nextLine();

		l1 = t1.length();
		l2 = t2.length();
		text = t1.concat("$").concat(t2).concat("#");
		len = text.length();

		//long start = System.nanoTime();
		for(int i = len - 1;i >= 0;i--)
			add(i, 0);	
		
		flag = new boolean[tree.size() + 10];
		mark(0, new Pair(0, 0, 0));
		printNonSharedSubStrings(0, new Pair(0, -1, 0),0,false,0);
		findAnswer(new Pair(0, 0, 0));
		
		for(String s:stack)
			out.print(s);
		
		out.println();

		//long stop = System.nanoTime();
		//System.err.println("Time elapsed : " + ((stop - start) / 1e9) + "s");
	}



	/************************ SOLUTION ENDS HERE ************************/





	/************************ TEMPLATE STARTS HERE *********************/

	public static void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
			public void run() {
				new ShortestNonSharedSubstring().run();
			}
		}, "Increase Stack", 1L << 25).start();
	}

	void run(){	
		FastScanner in  = new FastScanner(System.in);
		PrintWriter out = 
				new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false); 
		solve(in, out);
		//generateTestCase();
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

