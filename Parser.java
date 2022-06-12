// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
	public ArrayList<Variable> vars = new ArrayList<>();
	public ArrayList<FreeVar> freeVars = new ArrayList<>();
	public boolean isRun = false;
	public boolean variableAlreadyDefined = false;
	public boolean isVar = false;

	public Expression parse(ArrayList<String> tokens) throws Exception {
		ArrayList<Parameter> paramVars = new ArrayList<>();
		return parseRunner(tokens, paramVars);
	}
	
	public Expression parseRunner(ArrayList<String> tokens, ArrayList<Parameter> params) throws Exception {
		ArrayList<Integer> parensTokens = parenTokenList(tokens);

		if (parensTokens.get(0)== 1 && parensTokens.get(tokens.size()-1) == 0 && parensTokens.indexOf(0) == parensTokens.lastIndexOf(0)){
			ArrayList<String> newTokens = new ArrayList<String>(tokens.subList(1, tokens.size()-1));
			return parseRunner(newTokens, params);
		}

		if (tokens.contains("=")){
			isVar = true;
			
			for (int i = 0; i < vars.size(); i++) {
				if (vars.get(i).getName().equals(tokens.get(0))) {
					variableAlreadyDefined = true;
				}
			}
			
			ArrayList<String> exp = new ArrayList<String>(tokens.subList(tokens.indexOf("=")+1, tokens.size()));
			return parse(exp);
		} 
		
		if (tokens.size() == 1){

			for (int i = 0; i < vars.size(); i++) {
				if (vars.get(i).name.equals(tokens.get(0))) {
					return vars.get(i).expression;
				}
			}
			
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i).name.equals(tokens.get(0))) {
					params.get(i).addVar(new BoundVar(tokens.get(0)));
					return new BoundVar(tokens.get(0));
				}
			}
			
			FreeVar fv = new FreeVar(tokens.get(0));
			freeVars.add(fv);
			
			return fv;		
		}
		
		if (tokens.get(0).equals("run")) {	
			isRun = true;
			Expression p = parseRunner(new ArrayList<String>(tokens.subList(1,  tokens.size())), params);
			
			if (p instanceof Variable) 
				return (Variable)(p);
			return p;
		}
		
		if (tokens.get(0).equals( "λ")){
			Parameter var = new Parameter(tokens.get(tokens.indexOf("λ")+ 1).toString());
			params.add(var);
			ArrayList<String> app = new ArrayList<String>(tokens.subList(tokens.indexOf(".")+1, tokens.size()));
			
			return new Function(var, parseRunner(app, params));
		}
		
		else{	
			if (tokens.get(tokens.size()-1).equals(")")) {
				ArrayList<String> app1 = new ArrayList<>(tokens.subList(0, parensTokens.subList(0, parensTokens.size()-1).lastIndexOf(0)+1));

				ArrayList<String> app2 = new ArrayList<>(tokens.subList(parensTokens.subList(0, parensTokens.size()-1).lastIndexOf(0)+1, tokens.size()));

				Expression right = parseRunner(app2, params);
				Expression left = parseRunner(app1, params);
				
				return new Application(left, right);
			}
			else {
				ArrayList<String> app1 = new ArrayList<>(tokens.subList(0, tokens.size()-1));
				ArrayList<String> app2 = new ArrayList<>(tokens.subList(tokens.size()-1, tokens.size()));
				Expression right = parseRunner(app2, params);
				Expression left = parseRunner(app1, params);
				
				return new Application(left, right);
			}
		}
	}

	public ArrayList<Integer> parenTokenList(ArrayList<String> tokens) throws Exception{
		ArrayList<Integer> parensTokens = new ArrayList<>(tokens.size());
		int parenCount = 0;
		
		for (int i = 0; i < tokens.size(); i++){
			if (tokens.get(i).equals("(")){
				parenCount++;
			}
			else if (tokens.get(i).equals(")")){
				parenCount--;
			}
			parensTokens.add(parenCount);
		}
		
		if (parensTokens.get(parensTokens.size()-1)!=0){
			throw new Exception();
		}
		
		return parensTokens;
	}
}