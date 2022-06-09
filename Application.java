
public class Application implements Expression {
    private Expression left;
    private Expression right;

    public Application(Expression right, Expression left) {
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