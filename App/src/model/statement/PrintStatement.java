package model.statement;

import model.value.IValue;
import model.ProgramState;
import model.exceptions.ToyLangException;
import model.expression.IExpression;

public class PrintStatement implements IStatement {

    private final IExpression exp;

    public PrintStatement(IExpression e) {
        exp = e;
    }

    @Override
    public String toString() {

        return "print(" + exp.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue value = exp.evaluate(state.getSymTable(), state.getHeap());

        System.out.println(value);
        state.getOut().append(value);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(exp.deepCopy());
    }
}
