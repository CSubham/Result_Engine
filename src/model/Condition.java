package model;

import java.util.ArrayList;

import model.Condition_blocks.ConditionBlock;

public class Condition {
    // a collection of condition blocks

    private ArrayList<ConditionBlock> condition;

    public Condition() {
    }

    public void addConditionBlock(ConditionBlock conditionBlock){
        this.condition.add(conditionBlock);
    }

    public ArrayList<ConditionBlock> getCondition() {
        return condition;
    }

    
}
