// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

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
		String[] vals = {"(", ")", ".", "λ", "="};
		ArrayList<String> arr = new ArrayList<>();

		int i = 0;
		
		while (i < input.length()) {
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
	
		for (int y = 1; y < arr.size(); y++) {
			if (arr.get(y).equals("λ")) {
				if (!arr.get(y-1).equals("(")) {
					arr.add(y, "(");
					int x = y+1;
					int parenCount = 1;
					while (x < arr.size() && parenCount > 0) {
						if (arr.get(x).equals(")")) {
							parenCount--;
						}
						if (arr.get(x).equals("(")) {
							parenCount++;
						}
						x++;
					}
					arr.add(x, ")");
				}
				y++;
			}
		}

		return arr;
	}
}
