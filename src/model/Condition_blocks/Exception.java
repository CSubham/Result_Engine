package model.Condition_blocks;

import java.util.ArrayList;

public class Exception implements ConditionBlock{

    ArrayList<Integer> exception;

    

    public Exception(ArrayList<Integer> exception) {
        this.exception = exception;
    }



    public ArrayList<Integer> getException() {
        return exception;
    }





    
    
}
