public class FreeVar extends Variable{
    
    public FreeVar(String name){
        super(name);
    }

    @Override
	public String toString() {
        System.out.println("Free Var");
		// return "Free Var!!: " + name;
        return name;
	}
}
