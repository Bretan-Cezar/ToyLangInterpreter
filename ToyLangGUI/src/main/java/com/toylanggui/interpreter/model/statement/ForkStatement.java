package com.toylanggui.interpreter.model.statement;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.Dictionary;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.stack.IStack;
import com.toylanggui.interpreter.model.stack.Stack;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

public class ForkStatement implements IStatement {

    IStatement statement;

    public ForkStatement(IStatement s) {

        statement = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IStack<IDictionary<String, IValue>> newSymTableStack = clone_stack(state.getSymTableStack());

        return new ProgramState(new Stack<>(), newSymTableStack, state.getOut(), state.getFileTable(), state.getHeap(), state.getProcTable(), statement);
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IDictionary<String, IType> newEnv = clone_env(typeEnv);

        statement.typecheck(newEnv);

        return typeEnv;
    }

    private IStack<IDictionary<String, IValue>> clone_stack(IStack<IDictionary<String, IValue>> symTableStack) {

        IStack<IDictionary<String, IValue>> newStack = new Stack<>();

        symTableStack.getContent().forEach((dict) -> newStack.push(clone_dict(dict)));

        return newStack;
    }

    private IDictionary<String, IValue> clone_dict(IDictionary<String, IValue> symTable) {

        IDictionary<String, IValue> newDictionary = new Dictionary<>();

        symTable.getContent()
                .forEach((key, value) -> newDictionary.modify(String.valueOf(key), value.deepCopy()));

        return newDictionary;
    }

    private IDictionary<String, IType> clone_env(IDictionary<String, IType> typeEnv) {

        IDictionary<String, IType> newDictionary = new Dictionary<>();

        typeEnv.getContent()
                .forEach((id, type) -> newDictionary.modify(String.valueOf(id), type.deepCopy()));

        return newDictionary;
    }

    @Override
    public IStatement deepCopy() {

        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {

        return "fork( " + statement.toString() + " )";
    }
}
