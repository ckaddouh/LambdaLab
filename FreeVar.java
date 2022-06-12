// Kaz Nam and Christina Kaddouh
// Lambda Lab 2022

public class FreeVar extends Variable{
    
    public FreeVar(String name){
        super(name);
    }

    @Override
	public String toString() {
        return name;
	}
}
