package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.Dictionary;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.IExpression;
import com.toylanggui.interpreter.model.type.BoolType;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.BoolValue;
import com.toylanggui.interpreter.model.value.IValue;

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
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        if (exp.typecheck(typeEnv).equals(new BoolType())) {

            stmt.typecheck(clone(typeEnv));
            return typeEnv;
        }
        else throw new ToyLangException("Expression is not of BoolType.");

    }

    private IDictionary<String, IType> clone(IDictionary<String, IType> typeEnv) {

        IDictionary<String, IType> newDictionary = new Dictionary<>();

        typeEnv.getContent()
                .forEach((id, type) -> newDictionary.modify(String.valueOf(id), type.deepCopy()));

        return newDictionary;
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
