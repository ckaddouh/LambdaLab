public class FreeVar extends Variable{
    
    public FreeVar(String name){
        super(name);
    }

    @Override
	public String toString() {

		// return "Free Var!!: " + name;
        return name;
	}
}
