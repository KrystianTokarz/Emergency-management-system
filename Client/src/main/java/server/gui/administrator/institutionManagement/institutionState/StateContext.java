package server.gui.administrator.institutionManagement.institutionState;


/**
 * Class which have actually state in context application
 */
public class StateContext {

    private InstitutionState state;

    public StateContext(InstitutionState state){
        setState(state);
    }

    public void setState(InstitutionState state){
        this.state = state;
    }

    public InstitutionState getState(){
        return state;
    }

}
