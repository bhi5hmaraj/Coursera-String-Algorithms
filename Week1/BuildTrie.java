import java.util.*;
import java.io.*;
public class BuildTrie
{


	/************************ SOLUTION STARTS HERE ***********************/

	static class Trie
	{
		static class Node
		{
			char ch;
			int index;
			Node adj[];
			public Node(char ch , int index){
				this.ch = ch;
				this.index = index;
				if(ch != '$')
					adj = new Node[27];
			}
			@Override
			public String toString() {
				return String.valueOf(ch);
			}
			@Override
			public int hashCode() {
				return Character.hashCode(ch);
			}
			@Override
			public boolean equals(Object obj) {
				Node that = (Node) obj;
				return this.ch == that.ch;
			}
		}

		Node root;
		int nodes;
		Trie(String arr[]){
			root = new Node('*', 0);
			nodes = 1;
			for(String str:arr)
				add(root, str, 0);

			//printAllStrings("", root);
			//print(root, 0);
		}

		void printAllStrings(String curr, Node root){
			if(root.ch == '$'){
				System.out.println(curr);
			}
			else{
				for(Node n:root.adj)
					if(n != null)
						printAllStrings(curr + n.ch, n);
			}

		}
		void print(Node root , int level){
			System.out.println(root + " level " + level);
			for(Node n:root.adj)
				if(n != null)
					print(n, level + 1);
		}


		void add(Node curr , String str , int idx){

			if(idx >= str.length()){
				curr.adj[26] = new Node('$', nodes);
			}
			else{
				Node n = curr.adj[str.charAt(idx) - 'A'];
				if(n != null){
					add(n, str, idx + 1);
					return;
				}
				Node newNode = new Node(str.charAt(idx) , nodes++);
				curr.adj[str.charAt(idx) - 'A'] = newNode;
				add(newNode, str, idx + 1);
			}
		}

	}

	static void prettyPrint(Trie trie , PrintWriter out , Trie.Node curr){


		for(Trie.Node n:curr.adj){
			if(n != null && n.ch != '$'){
				out.println(curr.index + "->" + n.index + ":" + n.ch);
				prettyPrint(trie, out, n);
			}
		}


	}

	private static void solve(FastScanner s1, PrintWriter out){

		int N = s1.nextInt();
		String arr[] = new String[N];
		for(int i=0;i<N;i++)
			arr[i] = s1.nextLine();
		Trie trie = new Trie(arr);
		prettyPrint(trie, out, trie.root);

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