package model.statement;

import model.ProgramState;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

import java.util.Objects;

public class WhileStatement implements IStatement {

    IExpression exp;
    IStatement stmt;

    public WhileStatement(IExpression e, IStatement s) {

        exp = e;
        stmt = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue v = exp.evaluate(state.getSymTable(), state.getHeap());

        if (Objects.equals(v.getType(), new BoolType())) {

            BoolValue eval = (BoolValue) v;
            if (eval.getValue()) {

                state.getExeStack().push(this);
                state.getExeStack().push(stmt);
            }
        }
        else throw new ToyLangException("Expression does not evaluate to a BoolValue.");

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "( while (" + exp.toString() + ") " + stmt.toString() + " )";
    }
}
