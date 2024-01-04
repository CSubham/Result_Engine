package model.Condition_blocks;

import java.io.Serializable;
import java.util.ArrayList;

public class Exception implements ConditionBlock,Serializable{

    ArrayList<Integer> exception;

    

    public Exception(ArrayList<Integer> exception) {
        this.exception = exception;
    }



    public ArrayList<Integer> getException() {
        return exception;
    }





    
    
}
