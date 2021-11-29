package model.statement;

import model.heap.IHeap;
import model.type.IType;
import model.value.IValue;
import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.stack.IStack;

public class AssignStatement implements IStatement {

    private final String id;
    private final IExpression exp;

    public AssignStatement(String id, IExpression exp) {

        this.id = id;
        this.exp = exp;

    }

    public String toString() {

        return id + " = " + exp.toString();
    }


    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IDictionary<String, IValue> table = state.getSymTable();
        IHeap heap = state.getHeap();

        if (table.isKeyDefined(id)) {

            IValue val = exp.evaluate(table, heap);
            IType typeId = (table.get(id)).getType();

            if (val.getType().equals(typeId))
                table.modify(id, val);

            else throw new ToyLangException("declared type of variable " + id + " and type of the assigned expression do not match");

        }
        else throw new ToyLangException("the used variable " + id + " was not declared before");

        return null;
    }

    @Override
    public IStatement deepCopy() {

        return new AssignStatement(String.valueOf(id), exp.deepCopy());
    }

}
