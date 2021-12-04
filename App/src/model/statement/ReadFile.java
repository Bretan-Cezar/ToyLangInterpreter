package model.statement;

import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.type.IType;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ReadFile implements IStatement {

    IExpression exp;
    String var;

    public ReadFile(IExpression e, String v) {

        exp = e;
        var = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue v = exp.evaluate(state.getSymTable(), state.getHeap());

        if (v.getType().equals(new StringType())) {

            StringValue s = (StringValue) v;

            if (state.getSymTable().isKeyDefined(var)) {

                if (state.getSymTable().get(var).getType().equals(new IntType())) {

                    if (state.getFileTable().isKeyDefined(s)) {

                        BufferedReader fd = state.getFileTable().get(s);
                        String val_str;
                        try {

                            val_str = fd.readLine();
                        }
                        catch (IOException ioe) {

                            throw new ToyLangException("An error occurred while trying to read the line in the file.");
                        }

                        if (!Objects.equals(val_str, "")) {

                            IntValue v2;
                            try {

                                v2 = new IntValue(Integer.parseInt(val_str));
                            }
                            catch (NumberFormatException nfe) {

                                throw new ToyLangException("Cannot read value because a non-int value has been read or EOF has been reached.");
                            }

                            state.getSymTable().modify(var, v2);
                        }
                        else state.getSymTable().modify(var, new IntValue(0));
                    }
                    else throw new ToyLangException("The given filename is not opened.");
                }
                else throw new ToyLangException("The given variable is not of type int.");
            }
            else throw new ToyLangException("The given variable is not defined.");
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

        return new ReadFile(exp.deepCopy(), String.valueOf(var));
    }

    @Override
    public String toString() {

        return "ReadFile(" + exp.toString() + ", " + var + ")";
    }
}
