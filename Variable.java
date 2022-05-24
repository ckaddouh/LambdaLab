
public class Variable implements Expression {
	private String name;
	private Expression expression;
	
	public Variable(String name) {
		this.name = name;
	}

	public Variable(String name, Expression expression){
		this.name = name;
		this.expression = expression;
	}
	@Override
	public String toString() {
		if (expression != null){
			return "Added " + name + " as " + expression.toString();
		}
		return name;
	}

}
