// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

public class BoundVar extends Variable{

    public BoundVar(String name){
		super(name);
	}

    public void setName(String newName){
        name = newName;
    }
    
    @Override
	public String toString() {
        return name;
	}

    
}
