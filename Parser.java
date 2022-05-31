
import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */
	public Expression parse(ArrayList<String> tokens) throws Exception {

		// MIGHT BE UNNECESSARY: create a list of indexes of open and and close parens, check if balanced, 
		// if there is a (, ), ., \, it should split into an application
		// if there's a part of the split that is one token, that token is a variable
		// if there's a . it's a \ at the start and . it's a function WOAH HI KAZ I AM IN CONTROL
		// use linkedlist? or at least make a copy of the arraylist as we go
		ArrayList<Integer> openParens = new ArrayList<Integer>();
		ArrayList<Integer> closedParens = new ArrayList<Integer>();

		for (int i = 0; i < tokens.size(); i++){
			if (tokens.get(i).equals("(")){
				openParens.add(i);
			}
			else if (tokens.get(i).equals(")")){
				closedParens.add(i);
			}
		}

		if (openParens.size() != closedParens.size()){
			throw new Exception();
		}
		// System.out.println(openParens);
		// System.out.println(closedParens);
		for (int j = 0; j < openParens.size()-1; j++){
			if (openParens.get(j)+1 == openParens.get(j+1) && closedParens.get(closedParens.size()-j-1)-1 == closedParens.get(closedParens.size()-j-2)){
				tokens.set(openParens.get(j), null);
				tokens.set(closedParens.get(closedParens.size()-1-j), null);
			}
		}
		while(tokens.contains(null)){
			tokens.remove(null);
		}
		// System.out.println(tokens);
		if (tokens.get(0).equals("(") && tokens.get(tokens.size()-1).equals(")")){
			tokens.remove(tokens.size()-1);
			tokens.remove(0);
		}

		int indexOpenParen = tokens.indexOf("(");
		// System.out.println(tokens.get(0));
		
		if (tokens.size() == 1){
			// System.out.println("variable");
			return new Variable(tokens.get(0));
		}
		if (tokens.contains("=")){
			ArrayList<String> exp = new ArrayList<String>(tokens.subList(tokens.indexOf("=")+1, tokens.size()));
			return new Variable(tokens.get(0), parse(exp));
		}

		if (tokens.get(0).equals( "λ")){
			System.out.println("function");
			Variable var = new Variable(tokens.get(tokens.indexOf("λ")+ 1));
			System.out.println("var " + var);
			ArrayList<String> app = new ArrayList<String>(tokens.subList(tokens.indexOf(".")+1, tokens.size()));
			System.out.println("app " + app);

			return new Function(parse(app), var);
		}
		else if (indexOpenParen != -1){
			System.out.println("application with paren");

			// get rid of extra parens
			// if (tokens.get(indexOpenParen+1).equals("(") && tokens.get(tokens.indexOf(")")+1).equals(")")){
			// 	return new Application
			// }
			if (indexOpenParen != 0){
				ArrayList<String> app1 = new ArrayList<String>(tokens.subList(0, indexOpenParen));
				System.out.print("app1");
				ArrayList<String> app2 = new ArrayList<String>(tokens.subList(indexOpenParen+1 , tokens.size()-1)); //+1 breaks iit for some reason???
				return new Application(parse(app1), parse(app2));
			}
			else if (tokens.lastIndexOf(")") != tokens.size()-1) {
				ArrayList<String> part1 = new ArrayList<String>(tokens.subList(indexOpenParen+1, tokens.lastIndexOf(")")));
				ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.lastIndexOf(")")+ 1, tokens.size()));
				// System.out.println("Part 1: " + part1);
				// ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.indexOf(" ") + 1, tokens.indexOf(")")));
				// System.out.println("Part 2: " + part2);
				return new Application(parse(part1), parse(part2));
			}
			else{
				System.out.println("u should be here");
				ArrayList<String> p1 = new ArrayList<String>(tokens.subList(indexOpenParen+1, indexOpenParen+2));
				// System.out.println("p1: " + p1);
				ArrayList<String> p2 = new ArrayList<String>(tokens.subList(tokens.size()-2, tokens.size()-1));
				// System.out.println("p2: " + p2);
				return new Application(parse(p1), parse(p2));
			}

		}

		else{
			System.out.println("application without paren");
			ArrayList<String> p1 = new ArrayList<String>(tokens.subList(0, tokens.size() -1));
			ArrayList<String> p2 = new ArrayList<String>(tokens.subList(tokens.size()-1, tokens.size()));
			System.out.println("p1: " + p1);
			// ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.indexOf(" ") + 1, tokens.indexOf(")")));
			System.out.println("p2: " + p2);
			return new Application(parse(p1), parse(p2));
		}


		//Variable var = new Variable(tokens.get(0));
		// int open = tokens.indexOf("(");
		// int close = tokens.indexOf(")");
		// Variable var1 = new Variable(tokens.get(++open));
		// Variable var2 = new Variable(tokens.get(--close));
		// System.out.println("Variable 1 " + var1);
		// System.out.println("Variable 2 " + var2);
		// Application app1 = new Application(var1, var2);

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

		// if (app1.toString().equals("error")) {
		// 	throw new ParseException("User typed \"Error\" as the input!", 0);
		// }
		
		// return new Variable ("error");
	}
}
