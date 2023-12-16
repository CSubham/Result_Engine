package model.Condition_blocks;

public class MixedValue implements ConditionBlock {
    

    private Value firstValueCondition;
    private Value secondValueCondition;
    
    private Exception exception;

    public MixedValue(Value firstValueCondition, Value secondValueCondition) {
        this.firstValueCondition = firstValueCondition;
        this.secondValueCondition = secondValueCondition;
        this.exception = null;
    }
    public MixedValue(Value firstValueCondition, Value secondValueCondition, Exception exception) {
        this.firstValueCondition = firstValueCondition;
        this.secondValueCondition = secondValueCondition;
        this.exception = exception;
    }

    public Value getFirstValueCondition() {
        return firstValueCondition;
    }

    public Value getSecondValueCondition() {
        return secondValueCondition;
    }
    public Exception getException() {
        return exception;
    }

}
