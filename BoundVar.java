public class BoundVar extends Variable{

    public BoundVar(String name){
		super(name);
	}

    public void setName(String newName){
        name = newName;
    }
    
    @Override
	public String toString() {
        System.out.println("Bound Var: " + name);

		// return "Bound Var!!: " + name;
        return name;
	}

    
}
