// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

public class Application implements Expression {
    public Expression left;
    public Expression right;

    public Application(Expression left, Expression right) {
		this.left = left;
        this.right = right;
	}

    public Expression getLeft(){
        return left;
    }

    public Expression getRight(){
        return right;
    }

    public String toString() {
        return "(" + left + " " + right + ")";
    }
}