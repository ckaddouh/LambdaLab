import java.util.ArrayList;

public class Parameter extends Variable{
    public ArrayList<BoundVar> list = new ArrayList<BoundVar>();

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
    
    public void setName(String in){
		name = in;    
    }
    
    public ArrayList<BoundVar> getList(){
    	return list;
    }

    @Override
	public String toString() {

		// return "Param Var!!: " + name;
        return name;
	}
}
