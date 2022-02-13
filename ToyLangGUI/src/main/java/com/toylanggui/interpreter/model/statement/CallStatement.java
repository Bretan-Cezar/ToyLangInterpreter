package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.Dictionary;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.IExpression;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

import java.util.List;

public class CallStatement implements IStatement {

    String func_name;
    List<IExpression> params;

    public CallStatement(String func_name, List<IExpression> params) {

        this.func_name = func_name;
        this.params = params;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        if (state.getProcTable().isKeyDefined(func_name)) {

            IDictionary<String, IValue> newSymTable = clone_dict(state.getSymTableStack().getContent().getFirst());

            List<String> params = state.getProcTable().get(func_name).getKey();

            for (int i = 0; i < this.params.size(); i++) {

                String var_name = params.get(i);
                IValue eval = this.params.get(i).evaluate(state.getSymTableStack().getContent().getFirst(), state.getHeap());

                newSymTable.modify(var_name, eval);
            }

            state.getSymTableStack().push(newSymTable);

            state.getExeStack().push(new ReturnStatement());

            state.getExeStack().push(state.getProcTable().get(func_name).getValue());

        }
        else throw new ToyLangException("Function not defined.");

        return null;
    }

    private IDictionary<String, IValue> clone_dict(IDictionary<String, IValue> symTable) {

        IDictionary<String, IValue> newDictionary = new Dictionary<>();

        symTable.getContent()
                .forEach((key, value) -> newDictionary.modify(String.valueOf(key), value.deepCopy()));

        return newDictionary;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public String toString() {

        return "call " + func_name + params.toString();
    }
}
