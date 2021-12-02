package model.statement;

import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.stack.IStack;
import model.type.IType;

public class CompStatement implements IStatement {

    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement s1, IStatement s2) {

        first = s1;
        second = s2;
    }

    @Override
    public String toString() {

        return "( " + first.toString() + " ; " + second.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IStack<IStatement> stack = state.getExeStack();

        stack.push(second);
        stack.push(first);

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}
