
public class Function implements Expression {
    private Expression expression;
    private Variable variable;

    public Function(Expression expression, Variable variable) {
		this.expression = expression;
        this.variable = variable;
	}
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
