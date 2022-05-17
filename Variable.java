
public class Variable implements Expression {
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
