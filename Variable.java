
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
	@Override
	public String toString() {
		if (expression != null){
			return "Added " + name + " as " + expression.toString();
		}
		System.out.println("normal... ");
		return name;
	}

}
