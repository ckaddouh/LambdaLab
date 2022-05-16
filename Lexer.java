
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	/*
	 * A lexer (or "tokenizer") converts an input into tokens that
	 * eventually need to be interpreted.
	 * 
	 * Given the input 
	 *    (\bat  .bat flies)cat  λg.joy! )
	 * you should output the ArrayList of strings
	 *    [(, \, bat, ., bat, flies, ), cat, \, g, ., joy!, )]
	 *
	 */
	public ArrayList<String> tokenize(String input) {
		String[] vals = {"(", ")", ".", "\\"};
		ArrayList<String> arr = new ArrayList<>();
		

		int i = 0;
		while (i < input.length()) {
			//System.out.println(input);
		    if (Arrays.asList(vals).contains(input.substring(i,i+1))) {
				arr.add(input.substring(0,i));
				arr.add(input.substring(i,i+1));
				input = input.substring(i+1);
				i = 0;
			}
			else if (input.substring(i,i+1).equals(" ")) {
				arr.add(input.substring(0,i+1));
				input = input.substring(i+1);
				i = 0;
			}
			else {
				i++;
			}
				
		}
		arr.add(input);

		for (int in = 0; in < arr.size(); in++) {
			arr.set(in, arr.get(in).strip());
			if (arr.get(in).equals("")){
				arr.remove(in--);
			}
		}
	
	
		return arr;

		// String[] words = input.split(" ");
		// // String[] a = "hello.there".split(".");
		// // System.out.println(a[0]);
		// while (st.hasMoreTokens()) {
		// 	String t = st.nextToken();

		// 	boolean containsVal = false;

		
		// 	//if (t.contains(".") || t.contains("\\") || t.contains("(") || t.contains(")")) {
		// 	for (int c = 0; c < vals.length; c++) {
		// 		if (t.contains(vals[c])) {
		// 			containsVal = true;
		// 			System.out.println(vals[c]);
		// 			String[] arr = t.split(vals[c]);
		// 			System.out.println(arr.toString());
		// 			for (int word = 0; word < arr.length; word++) {
		// 				System.out.println(arr[word]);
		// 			}
		// 			tokens.add(arr[0]);
		// 			tokens.add(vals[c]);
		// 			if (arr.length > 1)
		// 				tokens.add(arr[1]);
		// 		}
		// 	}
		// 	if (!containsVal)
		// 		tokens.add(t);
		// }

		// return tokens;
		

	}
	

}
