
import java.text.ParseException;
import java.util.ArrayList;

// notes:
// applications with functions are stacking incorrectly
// inputs that have wrong outputs:
// 9, last one in 15, first two in 16
// i think that it's not determining the top level items correctly
public class Parser {
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */
	public Expression parse(ArrayList<String> tokens) throws Exception {
		System.out.println("new parse");
		// MIGHT BE UNNECESSARY: create a list of indexes of open and and close parens, check if balanced, 
		// if there is a (, ), ., \, it should split into an application
		// if there's a part of the split that is one token, that token is a variable
		// if there's a . it's a \ at the start and . it's a function WOAH HI KAZ I AM IN CONTROL
		// use linkedlist? or at least make a copy of the arraylist as we go

		// ArrayList<Integer> lambdaInds = new ArrayList<Integer>();
		ArrayList<Integer> parensTokens = new ArrayList<>(tokens.size());
		int parenCount = 0;
		for (int i = 0; i < tokens.size(); i++){
			// System.out.println("loop");
			if (tokens.get(i).equals("(")){
				// System.out.println("is u working");

				parenCount++;
			}
			else if (tokens.get(i).equals(")")){
				// System.out.println("IS U WORKING");

				parenCount--;
			}
			parensTokens.add(parenCount);
			// System.out.println(parensTokens);
		}
		//how to recurse parse without the error????

		if (parensTokens.get(0)-1 == parensTokens.get(tokens.size()-1) && tokens.get(0).equals("(") && tokens.get(tokens.size()-1).equals(")")){
			ArrayList<String> newTokens = new ArrayList<String>(tokens.subList(1, tokens.size()-1));
			return parse(newTokens);
		}
		System.out.println(parensTokens);
		
		// ArrayList<Integer> openParens = new ArrayList<Integer>();
		// ArrayList<Integer> closedParens = new ArrayList<Integer>();


		// for (int i = 0; i < tokens.size(); i++){
		// 	if (tokens.get(i).equals("(")){
		// 		openParens.add(i);
		// 	}
		// 	else if (tokens.get(i).equals(")")){
		// 		closedParens.add(i);
		// 	}
		// }
		// if (openParens.size() != closedParens.size()){
		// 	throw new Exception();
		// }
		// // System.out.println(openParens);
		// // System.out.println(closedParens);
		// for (int j = 0; j < openParens.size()-1; j++){
		// 	if (openParens.get(j)+1 == openParens.get(j+1) && closedParens.get(closedParens.size()-j-1)-1 == closedParens.get(closedParens.size()-j-2)){
		// 		tokens.set(openParens.get(j), null);
		// 		tokens.set(closedParens.get(closedParens.size()-1-j), null);
		// 		// System.out.println(j + " : " + tokens);
		// 	}
		// }

		// while(tokens.contains(null)){
		// 	tokens.remove(null);
		// }


		// for (int i = 0; i < tokens.size()-2; i++){
		// 	// System.out.println("loop");
		// 	if (tokens.get(i).equals("(") && tokens.get(i+2).equals(")")){
		// 		tokens.set(i, "null");
		// 		tokens.set(i+2, "null");
		// 	}
		// }

		// while(tokens.contains("null")){
		// 	tokens.remove("null");
		// }
		// // System.out.println(tokens);

		if (tokens.get(0).equals("(") && tokens.get(tokens.size()-1).equals(")") && tokens.lastIndexOf("(") == 0){
			System.out.println("removed extra parens");
			tokens.remove(tokens.size()-1);
			tokens.remove(0);
		}
		System.out.println(tokens);

		
//		ArrayList<Integer> openInds = new ArrayList<>();
//		ArrayList<Integer> closeInds = new ArrayList<>();
//
//		for (int i = 0; i < tokens.size(); i++){
//			if (tokens.get(i).equals("(")){
//				openParens.add(i);
//			}
//			else if (tokens.get(i).equals(")")){
//				closedParens.add(i);
//			}
//		}
		if (tokens.contains("=")){
			ArrayList<String> exp = new ArrayList<String>(tokens.subList(tokens.indexOf("=")+1, tokens.size()));
			return new Variable(tokens.get(0), parse(exp));
		} 
		
		if (tokens.get(0).equals("run")) {
			if (parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))) instanceof Variable) 
				return (Variable)(parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))));
			 if (!tokens.contains("λ")) {
				return (Expression) (parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))));
			}
			if (parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))) instanceof Variable) {
				// if parsing first part gives function and parsing second part gives variable, run it
			}
		}
		// System.out.println(tokens.get(0));
		
		if (tokens.size() == 1){
			System.out.println("variable");
			return new Variable(tokens.get(0));
		}
		
 // PAREN COUNT; ADD A PAREN EVERY TIME YOU SEE A LAMBDA AND THEN WHEN THE COUNT BECOMES 0
	// (IE IT'S UNBALANCED)
		// ONE LAYER OF PAREN, VAR, EXTRA PAREN, FUNCTION (IF LAMBDA IS FIRST)
		// APPLICATION - COUNT HOW MANY TOP LEVEL ITEMS

		
		if (tokens.get(0).equals( "λ")){
			System.out.println("function");
			Variable var = new Variable(tokens.get(tokens.indexOf("λ")+ 1));
			// System.out.println("var " + var);
			ArrayList<String> app = new ArrayList<String>(tokens.subList(tokens.indexOf(".")+1, tokens.size()));
			// System.out.println("app " + app);

			return new Function(var, parse(app));
		}
		else{
			System.out.println("application");
		
			ArrayList<String> app1 = new ArrayList<>(tokens.subList(0, parensTokens.indexOf(0)+1));
			System.out.println("app1= " + app1);
			ArrayList<String> app2 = new ArrayList<>(tokens.subList(parensTokens.indexOf(0)+1, tokens.size()));
			System.out.println("app2= " + app2);
			return new Application(parse(app1), parse(app2));
		}
// 		else if (indexOpenParen != -1){
// 			// System.out.println("application with paren");

// 			// get rid of extra parens
// 			// if (tokens.get(indexOpenParen+1).equals("(") && tokens.get(tokens.indexOf(")")+1).equals(")")){
// 			// 	return new Application
// 			// }
// //			ArrayList<String> app12 = new ArrayList<>(tokens.subList(0,  tokens.indexOf(")")+1));
// //			ArrayList<String> app22 = new ArrayList<>(tokens.subList(tokens.indexOf(")")+1, tokens.size()));
// //			System.out.println("app12:" + app12);
// //			System.out.println("app22:" + app22);
// //			
// //			return new Application(parse(app12), parse(app22));

// //			ArrayList<String> a1 = new ArrayList<>(tokens.subList(0,  tokens.indexOf("(")));
// //			ArrayList<String> a2 = new ArrayList<>(tokens.subList(tokens.indexOf("("), tokens.indexOf(")")+1));
// //			ArrayList<String> a3 = new ArrayList<>(tokens.subList(tokens.indexOf(")")+1,  tokens.size()));
// //
// //			System.out.println("a1:" + a1);
// //			System.out.println("a2:" + a2);
// //			System.out.println("a3:" + a3);
			
// //			return new Application(parse(a1), new Application(parse(a2), parse(a3)));
// 			if (tokens.indexOf("(") != 0 && tokens.indexOf(")") != tokens.size()-1){
// 				ArrayList<String> app1 = new ArrayList<String>(tokens.subList(0, tokens.indexOf("(")));
// 				// System.out.print("app1: " + app1);
// 				ArrayList<String> app2 = new ArrayList<String>(tokens.subList(tokens.indexOf("(") , tokens.indexOf(")")+1)); //+1 breaks iit for some reason???
// 				ArrayList<String> app3 = new ArrayList<String>(tokens.subList(tokens.indexOf(")")+1 , tokens.size())); //+1 breaks iit for some reason???

				
// 				return new Application(parse(app1), new Application(parse(app2), parse(app3)));
// 			}
// 			else if (tokens.indexOf("(") == 0 && tokens.indexOf(")") != tokens.size()-1) {
// 				ArrayList<String> app1 = new ArrayList<String>(tokens.subList(0, tokens.indexOf(")")+1));
// 				// System.out.print("app1= " + app1);
// 				ArrayList<String> app2 = new ArrayList<String>(tokens.subList(tokens.indexOf(")")+1 , tokens.size())); //+1 breaks iit for some reason???

				
// 				return new Application(parse(app1), parse(app2));
// 			}
// 			else if (tokens.indexOf(")") == tokens.size()-1 && tokens.indexOf("(") != 0 ) {
// 				ArrayList<String> app1 = new ArrayList<String>(tokens.subList(0, tokens.indexOf("(")));
// 				// System.out.print("app1:= " + app1);
// 				ArrayList<String> app2 = new ArrayList<String>(tokens.subList(tokens.indexOf("(") , tokens.indexOf(")")+1)); //+1 breaks iit for some reason???
				
// 				return new Application(parse(app1), parse(app2));
// 			}
// 			else {
// 				// open paren is at front and close paren is at end
// 				ArrayList<String> app1 = new ArrayList<String>(tokens.subList(1, tokens.size()-1));
// 				ArrayList<String> app2 = new ArrayList<>();

// 				return new Application(parse(app1), parse(app2)) ;
// 			}
			
//			else if (tokens.lastIndexOf(")") != tokens.size()-1) {
//				ArrayList<String> part1 = new ArrayList<String>(tokens.subList(indexOpenParen+1, tokens.lastIndexOf(")")));
//				ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.lastIndexOf(")")+ 1, tokens.size()));
//				// System.out.println("Part 1: " + part1);
//				// ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.indexOf(" ") + 1, tokens.indexOf(")")));
//				// System.out.println("Part 2: " + part2);
//				return new Application(parse(part1), parse(part2));
//			}
//			else{
//				System.out.println("u should be here");
//				ArrayList<String> p1 = new ArrayList<String>(tokens.subList(indexOpenParen+1, indexOpenParen+2));
//				// System.out.println("p1: " + p1);
//				ArrayList<String> p2 = new ArrayList<String>(tokens.subList(tokens.size()-2, tokens.size()-1));
//				// System.out.println("p2: " + p2);
//				return new Application(parse(p1), parse(p2));
//			}

		// }

		
		// else{
		// 	int ind = tokens.indexOf("λ");
		// 	if (ind != -1){
		// 		ArrayList<String> p1 = new ArrayList<String>(tokens.subList(0, ind));
		// 		ArrayList<String> p2 = new ArrayList<String>(tokens.subList(ind,  tokens.size()));
		// 		return new Application(parse(p1), parse(p2));
		// 	}
		// 	else {
		// 		// System.out.println("application without paren");
		// 		ArrayList<String> p1 = new ArrayList<String>(tokens.subList(0, tokens.size() -1));
		// 		ArrayList<String> p2 = new ArrayList<String>(tokens.subList(tokens.size()-1, tokens.size()));
		// 		// System.out.println("p1: " + p1);
		// 		// ArrayList<String> part2 = new ArrayList<String>(tokens.subList(tokens.indexOf(" ") + 1, tokens.indexOf(")")));
		// 		// System.out.println("p2: " + p2);
		// 		return new Application(parse(p1), parse(p2));
		// 	}
			
			
		// }


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
