
public class Function implements Expression {
    public Expression expression;
    public Variable variable;

    public Function(Variable variable, Expression expression) {
		this.expression = expression;
        this.variable = variable;
	}
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
