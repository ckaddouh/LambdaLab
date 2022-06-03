
public class Function implements Expression {
    private Expression expression;
    private Variable variable;

    public Function(Variable variable, Expression expression) {
		this.expression = expression;
        this.variable = variable;
	}
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
