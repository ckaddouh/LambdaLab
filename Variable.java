// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

public class Variable implements Expression {
	public String name;
	public Expression expression;
	
	public Variable(String name) {
		this.name = name;
	}

	public Variable(String name, Expression expression){
		this.name = name;
		this.expression = expression;
	}

	public String getName(){
		return name;
	}

	public Expression getExpression(){
		return expression;
	}
	
	@Override
	public String toString() {
		if (expression != null){
			return "Added " + name + " as " + expression.toString();
		}

		return name;
	}
}
