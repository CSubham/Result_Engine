package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.Condition_blocks.ConditionBlock;

public class Condition implements Serializable {
    // a collection of condition blocks

    private ArrayList<ConditionBlock> condition = new ArrayList<>();

    public Condition() {
    }

    public void addConditionBlock(ConditionBlock conditionBlock){
        this.condition.add(conditionBlock);
    }

    public ArrayList<ConditionBlock> getCondition() {
        return condition;
    }

   
    
}
