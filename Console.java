// had some help from Galadriel with some of the redex/replace structures

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Console {
	private static Scanner in;
	
	public static void main(String[] args) {
		in = new Scanner (System.in);
		
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		
		String input = cleanConsoleInput();

		
		while (! input.equalsIgnoreCase("exit")) {
			


			ArrayList<String> tokens = lexer.tokenize(input);

			String output = "";
			
			try {
				Expression exp = parser.parse(tokens);

				if (parser.isRun) {

					Expression newExp = nextRedex(exp, parser);
					while (!(exp.equals(newExp))) {
						exp = newExp;
						newExp = nextRedex(exp, parser);
					}
					output = exp.toString();


					for (int i = 0; i<parser.vars.size(); i++){
						if (parser.vars.get(i).getExpression().toString().equals(exp.toString())){
							output = parser.vars.get(i).getName();
						}
					}
					
				}

				if (!parser.isRun){
					output = exp.toString();
				}



				if (parser.isVar) {
					Variable v = new Variable(tokens.get(0), exp);
					parser.vars.add(v);
					output = "Added " + v.getName() + " as " + v.getExpression().toString();
					parser.isVar = false;
				}


				
				
			} catch (Exception e) {
				if (parser.variableAlreadyDefined) {
					System.out.println(e.getMessage());
				}
				else if (!input.equals("")){
					System.out.println("Unparsable expression, input was: \"" + input + "\"");
				}
				input = cleanConsoleInput();
				continue;
			}

			System.out.println(output);
			
			input = cleanConsoleInput();
		}
		System.out.println("Goodbye!");
	}

	
	
	/*
	 * Collects user input, and ...
	 * ... does a bit of raw string processing to (1) strip away comments,  
	 * (2) remove the BOM character that appears in unicode strings in Windows,
	 * (3) turn all weird whitespace characters into spaces,
	 * and (4) replace all backslashes with λ.
	 */
	
	private static String cleanConsoleInput() {
		System.out.print("> ");
		String raw = in.nextLine();
		String deBOMified = raw.replaceAll("\uFEFF", ""); // remove Byte Order Marker from UTF

		String clean = removeWeirdWhitespace(deBOMified);
		
		if (deBOMified.contains(";")) {
			clean = deBOMified.substring(0, deBOMified.indexOf(";"));
		}
		
		return clean.replaceAll("\\\\", "λ");
	}
	
	// CHANGE CHANGE CHANGE
	public static Expression nextRedex(Expression e, Parser p) {

		if (e instanceof Application) {
			Application a = ((Application) e);
			if (a.getLeft() instanceof Function) {

				Function f = (Function)(a.getLeft());
				for (int i = 0; i < p.freeVars.size(); i++){

					e = reduce(e, p.freeVars.get(i));
				}
				e = replace(f.expression,  a.getRight(), f.variable);	
			}
			else {
				Expression left = nextRedex(a.getLeft(), p);
				if (!((a.getLeft()).toString()).equals(left.toString())) {
					return new Application(left, a.getRight());
				} else {
					Expression right = nextRedex(a.getRight(),p);
					if (!((a.getRight()).toString()).equals(right.toString())) {
						return new Application(a.getLeft(), right);
					}
				}
			}
		}
		else if (e instanceof Function) {
			Function f = (Function)e;
			Expression exp = nextRedex(f.expression, p);
			if (!((((Function)e).expression).toString()).equals(exp.toString())) {
				return new Function(f.variable, exp);
			}
		}
		return e;
	}

	
	public static Expression reduce(Expression e, Variable v) {
		if (e instanceof Variable) {
			if (((Variable) e).name.equals(v.getName()) && ((Variable) e) instanceof BoundVar) {
				return new BoundVar(v.getName() + "1");
			}
			return e;
		}

		else if (e instanceof Function) {
			if (((Function) e).variable.name.equals(v.getName())) {
				return new Function(new Parameter(v.getName() + "1"),
						replace(((Function) e).expression, new BoundVar(v.getName() + "1"), v));
			}
			return new Function(((Function) e).variable, reduce(((Function) e).expression, v));
		} 

		else if (e instanceof Application) {
			return new Application(reduce(((Application) e).left, v), reduce(((Application) e).right, v));
		} 
		
		

		return e;

	}

	public static Expression replace(Expression e, Expression in, Variable var) {

		if (e instanceof Application) {
			Application a = (Application) e;
			return new Application(replace(a.getLeft(), in, var), replace(a.getRight(), in, var));
		} 

		else if (e instanceof Function) {

			Function f = (Function) e;

			if ((f.getVariable().getName().equals(var.getName()))) {
				return e;
			} 

			else if (in instanceof Variable && ((f.getVariable().getName().equals(((Variable) in).getName())))){
				return new Function(new Parameter(f.getVariable().getName()+ "1"), replace(f.getExpression(), in, var));
			} 
			
			else {
				return new Function(f.getVariable(), replace(f.getExpression(), in, var));
			}
		} 
		
		else if (e instanceof Variable) {
			Variable v = (Variable) e;
			if (var.getName().equals(v.getName())) {
				return in;
			} 

			else {
				return e;
			}
		} 
		
		else {
			return null;
		}

	}
	
	
	
	public static String removeWeirdWhitespace(String input) {
		String whitespace_chars =  ""       /* dummy empty string for homogeneity */
				+ "\\u0009" // CHARACTER TABULATION
				+ "\\u000A" // LINE FEED (LF)
				+ "\\u000B" // LINE TABULATION
				+ "\\u000C" // FORM FEED (FF)
				+ "\\u000D" // CARRIAGE RETURN (CR)
				+ "\\u0020" // SPACE
				+ "\\u0085" // NEXT LINE (NEL) 
				+ "\\u00A0" // NO-BREAK SPACE
				+ "\\u1680" // OGHAM SPACE MARK
				+ "\\u180E" // MONGOLIAN VOWEL SEPARATOR
				+ "\\u2000" // EN QUAD 
				+ "\\u2001" // EM QUAD 
				+ "\\u2002" // EN SPACE
				+ "\\u2003" // EM SPACE
				+ "\\u2004" // THREE-PER-EM SPACE
				+ "\\u2005" // FOUR-PER-EM SPACE
				+ "\\u2006" // SIX-PER-EM SPACE
				+ "\\u2007" // FIGURE SPACE
				+ "\\u2008" // PUNCTUATION SPACE
				+ "\\u2009" // THIN SPACE
				+ "\\u200A" // HAIR SPACE
				+ "\\u2028" // LINE SEPARATOR
				+ "\\u2029" // PARAGRAPH SEPARATOR
				+ "\\u202F" // NARROW NO-BREAK SPACE
				+ "\\u205F" // MEDIUM MATHEMATICAL SPACE
				+ "\\u3000"; // IDEOGRAPHIC SPACE 
		Pattern whitespace = Pattern.compile(whitespace_chars);
		Matcher matcher = whitespace.matcher(input);
		String result = input;
		if (matcher.find()) {
			result = matcher.replaceAll(" ");
		}

		return result;
	}

}
