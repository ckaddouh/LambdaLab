import java.util.ArrayList;

public class Parameter extends Variable{
    ArrayList<BoundVar> list = new ArrayList<BoundVar>();

    public Parameter(String name){
        super(name);

    }
    public Parameter(String name, ArrayList<BoundVar> list){
        super(name);
        this.list = list;
    }

    public void addVar(BoundVar var){
        list.add(var);
    }

    @Override
	public String toString() {
        System.out.println("Parameter");
		// return "Param Var!!: " + name;
        return name;
	}
}
