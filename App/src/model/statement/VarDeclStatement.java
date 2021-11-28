package model.statement;

import model.*;
import model.exceptions.ToyLangException;
import model.type.*;
import model.value.StringValue;

public class VarDeclStatement implements IStatement {

    private final String name;
    private final IType type;

    public VarDeclStatement(String name, IType type) {

        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        if (!state.getSymTable().isKeyDefined(name)) {

            if (type.equals(new BoolType())) {

                state.getSymTable().modify(name, (new BoolType()).defaultValue());
            }
            else if (type.equals(new IntType())) {

                state.getSymTable().modify(name, (new IntType()).defaultValue());
            }
            else if (type.equals(new StringType())) {

                state.getSymTable().modify(name, (new StringType()).defaultValue());
            }
            else if (type instanceof RefType refType) {

                state.getSymTable().modify(name, (new RefType(refType.getInner()).defaultValue()));
            }

        }
        else throw new ToyLangException("A variable with the same name is already declared.");

        return state;
    }

    @Override
    public IStatement deepCopy() {

        return new VarDeclStatement(String.valueOf(name), type.deepCopy());
    }

    @Override
    public String toString() {

        return type.toString() + " " + name;
    }
}
