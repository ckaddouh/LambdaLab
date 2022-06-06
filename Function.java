
public class Function implements Expression {
    public Expression expression;
    public BoundVar variable;

    public Function(BoundVar variable, Expression expression) {
		this.expression = expression;
        this.variable = variable;
	}
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
