// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

public class Function implements Expression {
    public Expression expression;
    public Parameter variable;

    public Function(Parameter variable, Expression expression) {
		this.expression = expression;
        this.variable = variable;
	}

    public Parameter getVariable(){
        return variable;
    }

    public Expression getExpression(){
        return expression;
    }
    
    public String toString() {
        return "(λ" + variable + "." + expression + ")";
    }
}
