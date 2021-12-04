package model.statement;

import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStatement {

    IExpression exp;

    public OpenRFile(IExpression e) {

        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue v = exp.evaluate(state.getSymTable(), state.getHeap());

        if (v.getType().equals(new StringType())) {

            StringValue s = (StringValue) v;

            if (!state.getFileTable().isKeyDefined(s)) {

                BufferedReader fd;
                try {

                    fd = new BufferedReader(new FileReader(s.getValue()));
                }
                catch (IOException ioe) {

                    throw new ToyLangException(ioe.getMessage());
                }

                state.getFileTable().modify(s, fd);
            }
            else throw new ToyLangException("The file with the given name is already opened.");
        }
        else throw new ToyLangException("The expression is not of StringType.");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType t = exp.typecheck(typeEnv);

        if (t.equals(new StringType())) {

            return typeEnv;
        }
        else throw new ToyLangException("The expression is not of StringType.");
    }

    @Override
    public IStatement deepCopy() {

        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {

        return "OpenRFile(" + exp.toString() + ")";
    }
}
