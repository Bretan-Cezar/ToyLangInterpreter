package model.statement;

import model.ProgramState;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement {

    IExpression exp;

    public CloseRFile(IExpression e) {

        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue v = exp.evaluate(state.getSymTable(), state.getHeap());

        if (v.getType().equals(new StringType())) {

            StringValue s = (StringValue) v;

            if (state.getFileTable().isKeyDefined(s)) {

                BufferedReader fd = state.getFileTable().get(s);

                try {

                    fd.close();
                }
                catch (IOException ioe) {

                    throw new ToyLangException("An error occurred when trying to close the file.");
                }

                state.getFileTable().delete(s);
            }
            else throw new ToyLangException("The file with the given name is not opened.");
        }
        else throw new ToyLangException("The expression is not of StringType.");

        return state;
    }

    @Override
    public IStatement deepCopy() {

        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {

        return "CloseRFile(" + exp.toString() + ")";
    }
}
