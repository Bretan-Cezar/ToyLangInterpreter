package com.toylanggui.interpreter.model.statement;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.type.*;

public class VarDeclStatement implements IStatement {

    private final String name;
    private final IType type;

    public VarDeclStatement(String name, IType type) {

        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        if (!state.getSymTableStack().getContent().getFirst().isKeyDefined(name)) {

            if (type.equals(new BoolType())) {

                state.getSymTableStack().getContent().getFirst().modify(name, (new BoolType()).defaultValue());
            }
            else if (type.equals(new IntType())) {

                state.getSymTableStack().getContent().getFirst().modify(name, (new IntType()).defaultValue());
            }
            else if (type.equals(new StringType())) {

                state.getSymTableStack().getContent().getFirst().modify(name, (new StringType()).defaultValue());
            }
            else if (type instanceof RefType refType) {

                state.getSymTableStack().getContent().getFirst().modify(name, (new RefType(refType.getInner()).defaultValue()));
            }

        }
        else throw new ToyLangException("A variable with the same name is already declared.");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        //if (!typeEnv.isKeyDefined(name)) {

            typeEnv.modify(name, type);
            return typeEnv;
        //}
        //else throw new ToyLangException("A variable with the same name is already declared.");
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
