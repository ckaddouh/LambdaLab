import java.util.ArrayList;

public class Parameter extends Variable{
    ArrayList<BoundVar> list = new ArrayList<BoundVar>();

    public Parameter(String name, ArrayList<BoundVar> list){
        super(name);
        this.list = list;
    }
}
