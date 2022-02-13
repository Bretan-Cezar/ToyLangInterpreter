package com.toylanggui.interpreter.model.statement;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.IExpression;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.type.RefType;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.RefValue;

import java.util.Objects;

public class Alloc implements IStatement {

    String var_name;
    IExpression exp;

    public Alloc(String name, IExpression e) {

        var_name = name;
        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        if (state.getSymTableStack().getContent().getFirst().isKeyDefined(var_name)) {

            IType var_type = state.getSymTableStack().getContent().getFirst().get(var_name).getType();
            IValue val = exp.evaluate(state.getSymTableStack().getContent().getFirst(), state.getHeap());

            System.out.println(val);

            if (var_type instanceof RefType) {

                IType inner = ((RefType) var_type).getInner();

                if (Objects.equals(inner, val.getType())) {

                    Integer addr = state.getHeap().createEntry(val);

                    state.getSymTableStack().getContent().getFirst().modify(var_name, new RefValue(addr, inner));
                }
                else throw new ToyLangException("Mismatch in inner variable type (" + var_type + ") and expression (" + val.getType().toString() + ") types.");
            }
            else throw new ToyLangException("Variable is not of RefType.");
        }
        else throw new ToyLangException("No variable with the given name is defined.");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        if (typeEnv.get(var_name) instanceof RefType varType) {

            IType expType = exp.typecheck(typeEnv);

            if (varType.getInner().equals(expType)) {

                return typeEnv;
            }
            else throw new ToyLangException("Mismatch in inner variable type (" + varType.getInner() + ") and expression (" + expType + ") types.");
        }
        else throw new ToyLangException("Variable is not of RefType.");
    }

    @Override
    public IStatement deepCopy() {

        return new Alloc(String.valueOf(var_name), exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Alloc(" + var_name + ", " + exp.toString() + ")";
    }
}
