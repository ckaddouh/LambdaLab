
import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */
	public Expression parse(ArrayList<String> tokens) throws ParseException {

		// MIGHT BE UNNECESSARY: create a list of indexes of open and and close parens, check if balanced, 
		// if there is a (, ), ., \, it should split into an application
		// if there's a part of the split that is one token, that token is a variable
		// if there's a . it's a \ at the start and . it's a function
		// use linkedlist? or at least make a copy of the arraylist as we go

		//Variable var = new Variable(tokens.get(0));
		int open = tokens.indexOf("(");
		int close = tokens.indexOf(")");
		Variable var1 = new Variable(tokens.get(++open));
		Variable var2 = new Variable(tokens.get(--close));
		System.out.println("Variable 1 " + var1);
		System.out.println("Variable 2 " + var2);
		Application app1 = new Application(var1, var2);

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

		if (app1.toString().equals("error")) {
			throw new ParseException("User typed \"Error\" as the input!", 0);
		}
		
		return app1;
	}
}
