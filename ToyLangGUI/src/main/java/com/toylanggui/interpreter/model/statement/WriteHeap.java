package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.IExpression;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.type.RefType;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.RefValue;

import java.util.Objects;

public class WriteHeap implements IStatement {

    String var_name;
    IExpression exp;

    public WriteHeap(String var, IExpression e) {

        var_name = var;
        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IDictionary<String, IValue> table = state.getSymTable();

        if (table.isKeyDefined(var_name)) {

            if (table.get(var_name) instanceof RefValue) {

                RefValue ref = (RefValue) table.get(var_name);
                RefType type = (RefType) ref.getType();

                IHeap heap = state.getHeap();

                if (heap.entryDefined(ref.getAddr())) {

                    IValue v = exp.evaluate(table, state.getHeap());

                    if (Objects.equals(v.getType(), type.getInner())) {

                        heap.writeEntry(ref.getAddr(), v);
                    }
                    else throw new ToyLangException("The expression does not evaluate to the inner type of " + var_name + " (expected " + type.getInner() + ", got " + v.getType() + ")");
                }
                else throw new ToyLangException("Address " + ref.getAddr() + " from variable " + var_name + " of type RefValue is not allocated in the heap.");
            }
            else throw new ToyLangException("Variable is not of type RefType.");
        }
        else throw new ToyLangException("No variable with the given name has been defined.");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType var_type = typeEnv.get(var_name);

        if (var_type != null) {

            if (var_type instanceof RefType ref) {

                IType exp_type = exp.typecheck(typeEnv);

                if (ref.getInner().equals(exp_type)) {

                    return typeEnv;
                }
                else throw new ToyLangException("The expression does not evaluate to the inner type of " + var_name + " (expected " + ref.getInner() + ", got " + exp_type + ")");
            }
            else throw new ToyLangException("Variable is not of type RefType.");
        }
        else throw new ToyLangException("No variable with the given name has been defined.");
    }

    @Override
    public IStatement deepCopy() {

        return new WriteHeap(String.valueOf(var_name), exp.deepCopy());
    }

    @Override
    public String toString() {

        return "WriteHeap(" + var_name + ", " + exp.toString() + ")";
    }
}
