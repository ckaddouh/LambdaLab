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
			System.out.println(tokens);

			String output = "";
			
			try {
				Expression exp = parser.parse(tokens);
				if (parser.isRun) {
					Expression newExp = nextRedex(exp);
					while (!(exp.equals(newExp))) {
						exp = newExp;
						newExp = nextRedex(exp);
					}
				}
				output = exp.toString();
			} catch (Exception e) {
				if (!input.equals("")){
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
	public static Expression nextRedex(Expression e) {
		System.out.println("nextRedex");
		if (e instanceof Application) {
			Application a = ((Application) e);
			if (a.left instanceof Function) {
				System.out.println("REDEX CONFIRMED");
				Function f = (Function)(a.left);
			
				e = replace(f.expression, a.right, f.variable);	
			}
			else {
				Expression left = nextRedex(a.left);
				if (!((a.left).toString()).equals(left.toString())) {
					return new Application(left, a.right);
				} else {
					Expression right = nextRedex(a.right);
					if (!((a.right).toString()).equals(right.toString())) {
						return new Application(a.left, right);
					}
				}
			}
		}
		else if (e instanceof Function) {
			Function f = (Function)e;
			Expression exp = nextRedex(f.expression);
			if (!((((Function)e).expression).toString()).equals(exp.toString())) {
				return new Function(f.variable, exp);
			}
		}
		return e;
	}
	
	public static Expression reduce(Expression e, Variable v) {
		if (e instanceof Variable) {
			Variable newE = ((Variable) e);
			if (newE.name.equals(v.name)&& (newE instanceof BoundVar)) {
				BoundVar bv = ((BoundVar) newE);
				bv.setName(newE.name+"1");
				return bv;
			}
			return e;
		}
		else if (e instanceof Application) {
			Application a = ((Application) e);
			return new Application (reduce(a.left, v), reduce(a.right,v));

		}
		else if (e instanceof Function) {
			Function f = ((Function) e);
			if (f.variable.name.equals(v.name)) {
				f.variable.setName(f.variable.name+"1");
				BoundVar bv = new BoundVar(v.name+"1");
				return new Function(f.variable, replace(f, bv, v));
			}
			return e;
			
		}
		return e;
	}
	
	public static Expression replace(Expression exp, Expression right, Variable param) {
		System.out.println("REPLACE IS RUNNING");
		if (exp instanceof Function) {
			System.out.println("function");
			Function funcLeft = ((Function) exp);
			System.out.println(param.name);
			System.out.println(funcLeft.expression);
			if (param.name.equals(funcLeft.expression)) {
				System.out.println(right);
				return funcLeft.expression;
			}
			System.out.println("replace func");
			return new Function(funcLeft.variable, replace(funcLeft.expression, right, param));
			
		}
		else if (exp instanceof Application) {
			System.out.println("application");
			Application app = ((Application) exp);
			return new Application(replace(app.left, right, param), replace(app.right, right, param));
		}
		else if (exp instanceof Variable) {
			System.out.println("variable");
			Variable var = ((Variable) exp);
			if (param.name.equals(var.name)) {
				System.out.println(right);
				return right;
			}
			else {
				System.out.println("HIHIHIHIHIHI");
				return exp;
			}
		}
		return null;
	}


//		String newExp = "";
//		
//		for (int j = 0; j < funcLeft.expression.toString().length(); j++) {
//			for (int i = 0; i < funcLeft.variable.getList().size(); i++) {
//				if (funcLeft.expression.toString().substring(j, j+1).equals(funcLeft.variable.getList().get(i).name)) {
//					newExp += freeVar; 
//				}
//				newExp += funcLeft.expression.toString().substring(j, j+1);
//			}
//			
//		}
		
	
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
