package model;

import java.util.ArrayList;
import model.Condition_blocks.Compulsory;
import model.Condition_blocks.Exception;
import model.Condition_blocks.MixedValue;
import model.Condition_blocks.Value;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class ConditionComposer {

    private Condition condition = new Condition();

    public  void addCompulsoryBlock(int subjectCode, Operator unaryOperator, int value){

        condition.addConditionBlock(new Compulsory( subjectCode,  unaryOperator, value));
        
    } 


    public  void addCompulsoryBlock(ArrayList<Integer> subjects, Operator unaryOperator, int value){

        condition.addConditionBlock(new Compulsory( subjects,  unaryOperator,  value));
        
    } 

    public void addExceptionBlock(ArrayList<Integer> exception){
        condition.addConditionBlock(new Exception(exception));

    }

    public void addMixedValueBlock(Value firstValueCondition, Value secondValueCondition){
        condition.addConditionBlock(new MixedValue( firstValueCondition,  secondValueCondition));

    }

    public void addMixedValue(Value firstValueCondition, Value secondValueCondition, Exception exception){
        condition.addConditionBlock( new MixedValue(firstValueCondition, secondValueCondition));
    }

    public void addValueBlock(SubjectSignificance significance, Operator valueOperator, Operator comparisonOperator, int value,
            int comparisonValue, Exception exception){
                condition.addConditionBlock(new Value(significance, valueOperator, comparisonOperator, value, comparisonValue));

    }

    public void addValueBlock(SubjectSignificance significance, Operator valueOperator, Operator comparisonOperator, int value,
    int comparisonValue){
        condition.addConditionBlock(new Value(significance, valueOperator, comparisonOperator, value, comparisonValue));
    }

    public void resetConditionHolder(){
        condition = new Condition();
    }

    public Condition getCurrentConditon(){
        return condition;
    }



    
}
