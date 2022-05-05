
import java.util.ArrayList;
import java.util.Arrays;
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
		ArrayList<String> tokens = new ArrayList<String>();

		StringTokenizer st = new StringTokenizer(input);
		
		// This next line is definitely incorrect!
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			if (t.contains('.') || t.contains('\\') || t.contains('(') || t.contains(')'))
			tokens.add(st.nextToken());
		}

		char[] vals = {'(', ')', '.', '\\'};
		for (int i = 0; i < tokens.size(); i++) {
			for (int c = 0; c < tokens.get(i).length(); c++) {
				int index = Arrays.binarySearch(vals, tokens.get(i).charAt(c));
				while (index != -1) {
					if (index == 0) {
						tokens.add(i, Character.toString(tokens.get(i).charAt(c)));
						tokens.add(i+1, tokens.get(i).substring(1));
					}
					else if (index == tokens.get(i).)

					index = Arrays.binarySearch(vals, tokens.get(i).charAt(c));
				}
			}
		}

		for (int i = 0;)
	}



}
