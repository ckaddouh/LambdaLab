
import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */
	public Expression parse(ArrayList<String> tokens) throws ParseException {

		Variable var = new Variable(tokens.get(0));
		// int open;
		// int close;
		// for (int i = 0; i < tokens.size(); i++){
		// 	if (tokens.get(i).equals("(")){
		// 		open = i;
		// 		int j = i + 1;
		// 		while (!tokens.get(j).equals(")")) {
		// 			j++;
		// 		}
		// 		close = j;
		// 		ArrayList<String> arrp1 = new ArrayList<>(tokens.subList(open, close++));
		// 		ArrayList<String> arrp2 = new ArrayList<>(tokens.subList(close, tokens.size()));
		// 		Application app1 = new Application(parse(arrp1), parse(arrp2));
		// 	}
		// }

		// Expression exp = new Expression(tokens);
		
		// This is nonsense code, just to show you how to thrown an Exception.
		// To throw it, type "error" at the console.

		if (var.toString().equals("error")) {
			throw new ParseException("User typed \"Error\" as the input!", 0);
		}
		
		return var;
	}
}
