import java.util.*;
import java.io.*;
public class PatternMatching
{
	
	
	/************************ SOLUTION STARTS HERE ***********************/
	
static class SuffixArray {  // Implemented for Upper case alphabets automatically appends ' $ ' 
		
		final char $ = '@';  // use ' ` ' for small case alphabets
		int equiClass[];
		int order[];		
		int N;
		
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
		
		SuffixArray(String str) {
			
			str = str.concat(String.valueOf($));
			N = str.length();
			equiClass = new int[N];
			order = new int[N];
			
			
			int cnt[] = new int[27];
			for(int i=0;i<N;i++)
				cnt[str.charAt(i) - $]++;
			for(int i=1;i<27;i++)
				cnt[i] += cnt[i - 1];
			for(int i=N-1;i>=0;i--)
				order[--cnt[str.charAt(i) - $]] = i;
			equiClass[order[0]] = 0;
			for(int i=1;i<N;i++)
				equiClass[order[i]] = equiClass[order[i - 1]] + (str.charAt(order[i]) == str.charAt(order[i - 1]) 
									  							? 0 : 1); 
		}
		
	}
	static String str;
	static int length;
	static int[] suffix;
	
	static char charAt(int i){ return i < length ? str.charAt(i) : '$';}
	
	static int[] findRange(int left , int right , char ch , int offset){
		
		boolean flag = false;
		int mid = -1;
		int range[] = new int[2];
		int l  = left , r = right;
		int ub = -1 , lb = -1;
		while(l <= r){
			mid = (l + r) / 2;
			if(charAt(suffix[mid] + offset) >= ch){
				lb = mid;
				r = mid - 1;
				flag = !flag ? charAt(suffix[mid] + offset) == ch : flag;
			}
			else
				l = mid + 1;
		}
		range[0] = lb;
		l = left;
		r = right;
		while(l <= r){
			mid = (l + r) / 2;
			if(charAt(suffix[mid] + offset) <= ch){
				ub = mid;
				l = mid + 1;
			}
			else
				r = mid - 1;
		}
		range[1] = ub;
		// System.out.println("range " + Arrays.toString(range));
		return flag ? range : null;
	}
	
	static int[] findOccurence(String pattern){
		
		int[] range = {1 , length};
		for(int i=0 , len = pattern.length(); i < len && range != null;i++)
			range = findRange(range[0], range[1], pattern.charAt(i), i);
		
		return range;
	}
	
	private static void solve(FastScanner s1, PrintWriter out){
		
		str = s1.nextLine();
		length = str.length();
		suffix = new SuffixArray(str).getSuffixArray();

		int cnt[] = new int[length + 5];
		
		int Q = s1.nextInt();

		while(Q-->0){
			int[] range = findOccurence(s1.next());
			if(range != null){
				cnt[range[0]]++;
				cnt[range[1] + 1]--;
			}
		}
		for(int i=1;i<=length;i++)
			cnt[i] += cnt[i - 1];
		
		for(int i=1;i<=length;i++)
			if(cnt[i] != 0)
				out.print(suffix[i] + " ");
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