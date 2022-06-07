import java.text.ParseException;
import java.util.ArrayList;

// notes:
// parse works backwards now
// inputs that have wrong outputs:
// first one in 16
public class Parser {

	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */
	public ArrayList<Variable> vars = new ArrayList<>();
	ArrayList<String> paramIndex = new ArrayList<>();


	public Expression parse(ArrayList<String> tokens) throws Exception {
		System.out.println("new parse");
		// MIGHT BE UNNECESSARY: create a list of indexes of open and and close parens, check if balanced, 
		// if there is a (, ), ., \, it should split into an application
		// if there's a part of the split that is one token, that token is a variable
		// if there's a . it's a \ at the start and . it's a function WOAH HI KAZ I AM IN CONTROL
		// use linkedlist? or at least make a copy of the arraylist as we go
		// ArrayList<Integer> lambdaInds = new ArrayList<Integer>();
		ArrayList<Integer> parensTokens = parenTokenList(tokens);
		// ArrayList<Integer> parensTokens = new ArrayList<>(tokens.size());
		// int parenCount = 0;
		// for (int i = 0; i < tokens.size(); i++){
		// 	// System.out.println("loop");
		// 	if (tokens.get(i).equals("(")){
		// 		// System.out.println("is u working");
		// 		parenCount++;
		// 	}
		// 	else if (tokens.get(i).equals(")")){
		// 		// System.out.println("IS U WORKING");
		// 		parenCount--;
		// 	}
		// 	parensTokens.add(parenCount);
		// 	// System.out.println("Parens Tokens: " + parensTokens);
		// }
		// System.out.println(parensTokens);
		System.out.println(tokens);
		
		if (parensTokens.get(0)== 1 && parensTokens.get(tokens.size()-1) == 0 && parensTokens.indexOf(0) == parensTokens.lastIndexOf(0)){
			System.out.println("get rid of extra parens");
			ArrayList<String> newTokens = new ArrayList<String>(tokens.subList(1, tokens.size()-1));
			return parse(newTokens);
		}
		System.out.println(parensTokens);

		
		if (tokens.contains("=")){
			ArrayList<String> exp = new ArrayList<String>(tokens.subList(tokens.indexOf("=")+1, tokens.size()));
			for (int i = 0; i < vars.size(); i++) {
				if (vars.get(i).name.equals(tokens.get(0))) {
					System.out.println(tokens.get(0) + " is already defined.");
					return null;
					// NEED TO RETURN NULL OR SOMETHING HERE INSTEAD, SOMETHING THAT WON'T BREAK IT
					// return new Variable(tokens.get(0), parse(exp));
				}
			}
			Variable v = new Variable(tokens.get(0), parse(exp));
			vars.add(v);
			return v;
		} 
		
		if (tokens.get(0).equals("run")) {			
			if (parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))) instanceof Variable) 
				return (Variable)(parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))));
			 if (!tokens.contains("λ")) {
				return (Expression) (parse(new ArrayList<String>(tokens.subList(1,  tokens.size()))));
			 }
			 else if (parse(new ArrayList<String>(tokens.subList(1, tokens.size()))) instanceof Function){
				 return parse(new ArrayList<String>(tokens.subList(1, tokens.size())));
			 }
			 // if the last grouping is a var and the first grouping is a function, make a new app?			
			// NOT WORKING, how to actually make y go into \x.x
			 else{
				Expression e = parse(new ArrayList<String>(tokens.subList(1,  tokens.size())));
				Lexer l = new Lexer();
				ArrayList<String> t = l.tokenize(e.toString());
				ArrayList<Integer> finalTokens = parenTokenList(t);
				for (int i = 0; i < finalTokens.size()-1; i++){
					if (tokens.get(i).equals("λ")){
						Parameter one = new Parameter(tokens.get(i+1));
						paramIndex.add(one.name);
						// for (int j = i+2; j < tokens.size() ; j++){
						// 	if (tokens.get(j).equals(one.name) && finalTokens.get(j) >= finalTokens.get(i)){
						// 		boundIndex.add(j);
						// 	}
						// }
					}
				}
				e = parse(t);

			 }
		}
		// System.out.println(tokens.get(0));
		
		if (tokens.size() == 1){
			System.out.println("variable");
			// System.out.println(vars);
			for (int i = 0; i < vars.size(); i++) {
				if (vars.get(i).name.equals(tokens.get(0))) {
					return vars.get(i).expression;
				}
			}
			if (paramIndex.contains(tokens.get(0).toString())){
				return new BoundVar(tokens.get(0));
			}
			return new FreeVar(tokens.get(0));
			
		}
		

 // PAREN COUNT; ADD A PAREN EVERY TIME YOU SEE A LAMBDA AND THEN WHEN THE COUNT BECOMES 0
	// (IE IT'S UNBALANCED)
		// ONE LAYER OF PAREN, VAR, FUNCTION (IF LAMBDA IS FIRST)
		// APPLICATION - COUNT HOW MANY TOP LEVEL ITEMS  ((\a.a) (\b.b))
		
		if (tokens.get(0).equals( "λ")){
			System.out.println("function");
			Parameter var = new Parameter(tokens.get(tokens.indexOf("λ")+ 1));
			// System.out.println("var " + var);
			ArrayList<String> app = new ArrayList<String>(tokens.subList(tokens.indexOf(".")+1, tokens.size()));
			Expression exp = parse(app);
			System.out.println("exp: "+ exp);
			// System.out.println("app " + app);
			return new Function(var, parse(app));
		}
		else{
			System.out.println("application");
 // go from open paren all to close
		// go backwards, from 0 to 0 before, parse what's inside (treat that as an expression)
			// then parse everything to the left of it
			// if no parens, treat that thing as an expression
			

			if (tokens.get(tokens.size()-1).equals(")")) {
				ArrayList<String> app1 = new ArrayList<>(tokens.subList(0, parensTokens.subList(0, parensTokens.size()-1).lastIndexOf(0)+1));
				System.out.println("app1= " + app1);
				ArrayList<String> app2 = new ArrayList<>(tokens.subList(parensTokens.subList(0, parensTokens.size()-1).lastIndexOf(0)+1, tokens.size()));
				System.out.println("app2= " + app2);
				return new Application(parse(app1), parse(app2));

			}
			else {
				ArrayList<String> app1 = new ArrayList<>(tokens.subList(0, tokens.size()-1));
				System.out.println("app1= " + app1);
				ArrayList<String> app2 = new ArrayList<>(tokens.subList(tokens.size()-1, tokens.size()));
				System.out.println("app2= " + app2);	
				return new Application(parse(app1), parse(app2));

			}
		}
	}

	public ArrayList<Integer> parenTokenList(ArrayList<String> tokens) throws Exception{
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
			// System.out.println("Parens Tokens: " + parensTokens);
		}
		if (parensTokens.get(parensTokens.size()-1)!=0){
			throw new Exception();
		}
		return parensTokens;
	}
}