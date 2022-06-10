
public class Application implements Expression {
    public Expression left;
    public Expression right;

    public Application(Expression left, Expression right) {
		this.left = left;
        this.right = right;
        
	}

    // public Application deepCopy(Application a) {
    //     Application newApp = new Application();
        
    //     while (a.left != null) {
    //         Application n = new Application(deepCopy(a.left), deepCopy(a.right));
    //     }
    // }

    public String toString() {
        return "(" + left + " " + right + ")";
    }
}