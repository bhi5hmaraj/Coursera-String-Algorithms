import java.util.*;
import java.io.*;
public class SuffixArrayImplementation
{
	
	
	/************************ SOLUTION STARTS HERE ***********************/
	
	
	static class SuffixArray {  // Implemented for Upper case alphabets automatically appends ' $ ' 
		
		final char $ = '@';  // use ' ` ' for small case alphabets
		int equiClass[];	 // Stores the equivalent classes for each of the partial suffices (Indexed by the suffix itself and not their position is the order array)
		int order[];		 // Stores the suffix at each index 
		int N;				 // Length of the String
		
		int add(int a, int b){ return (a + b) % N; }
		int sub(int a ,int b){ return (N + a - b) % N; }
		
		int[] sortDoubled(int L){
			
			int newOrder[] = new int[N];
			int cnt[] = new int[N];
			for(int i=0;i<N;i++){
				int idx = sub(order[i], L);
				cnt[equiClass[idx]]++;
			}
			for(int i=1;i<N;i++)
				cnt[i] += cnt[i - 1];
			for(int i=N-1;i>=0;i--){
				int idx = sub(order[i], L);
				newOrder[--cnt[equiClass[idx]]] = idx;
			}
			
			return newOrder;
			
		}
		int[] updateEquiClass(int L , int newOrder[]){
			int newClass[] = new int[N];
			newClass[newOrder[0]] = 0;
			for(int i=1;i<N;i++){
				int f1 = equiClass[newOrder[i - 1]];
				int s1 = equiClass[add(newOrder[i - 1], L)];
				int f2 = equiClass[newOrder[i]];
				int s2 = equiClass[add(newOrder[i], L)];
				newClass[newOrder[i]] = newClass[newOrder[i - 1]] + (f1 == f2 && s1 == s2 ? 0 : 1); 
			}
			return newClass;
		}
		
		int[] getSuffixArray(){
			
			for(int L = 1;L < N;L <<= 1){
				int[] newOrder = sortDoubled(L);
				int[] newClass = updateEquiClass(L, newOrder);
				order = newOrder;
				equiClass = newClass;
			}
			return order;
		}
		
		SuffixArray(String str){
			
			str = str.concat(String.valueOf($));
			N = str.length();
			equiClass = new int[N];
			order = new int[N];
			
			
			int cnt[] = new int[27];
			for(int i=0;i<N;i++)   		// Radix Sort
				cnt[str.charAt(i) - $]++;
			for(int i=1;i<27;i++)
				cnt[i] += cnt[i - 1];
			for(int i=N-1;i>=0;i--)
				order[--cnt[str.charAt(i) - $]] = i;
			equiClass[order[0]] = 0;
			for(int i=1;i<N;i++)
				equiClass[order[i]] = equiClass[order[i - 1]] + (str.charAt(order[i]) == str.charAt(order[i - 1]) 
									  							? 0 : 1 ); 
		}
		
	}
	
	
	
	private static void solve(FastScanner s1, PrintWriter out){
		
		String str = s1.nextLine();
		int[] suffixArray = new SuffixArray(str.substring(0, str.length() - 1)).getSuffixArray();
		for(int a : suffixArray)
			out.print(a + " ");
		
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