
public class Function implements Expression {
    public Expression expression;
    public Parameter variable;

    public Function(Parameter variable, Expression expression) {
		this.expression = expression;
        this.variable = variable;
	}
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
