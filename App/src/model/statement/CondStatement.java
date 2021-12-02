package model.statement;

import model.dictionary.Dictionary;
import model.dictionary.IDictionary;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;
import model.ProgramState;
import model.exceptions.ToyLangException;
import model.expression.IExpression;

public class CondStatement implements IStatement {

    private final IExpression exp;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public CondStatement(IExpression exp, IStatement thenStatement, IStatement elseStatement) {

        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {

        return "(IF (" + exp.toString() + ") THEN (" + thenStatement.toString() + ") ELSE ("+ elseStatement.toString() +"))";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue eval = exp.evaluate(state.getSymTable(), state.getHeap());

        if (eval.getType().equals(new BoolType())) {

            BoolValue v1 = (BoolValue) eval;

            if (v1.getValue()) {

                state.getExeStack().push(thenStatement);
            }
            else {

                state.getExeStack().push(elseStatement);
            }

        }
        else throw new ToyLangException("Expression does not evaluate to a boolean value.");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType condType = exp.typecheck(typeEnv);

        if (condType.equals(new BoolType())) {

            thenStatement.typecheck(clone(typeEnv));
            elseStatement.typecheck(clone(typeEnv));

            return typeEnv;
        }
        else throw new ToyLangException("Expression does not evaluate to a boolean value.");
    }

    private IDictionary<String, IType> clone(IDictionary<String, IType> typeEnv) {

        IDictionary<String, IType> newDictionary = new Dictionary<>();

        typeEnv.getContent()
                .forEach((id, type) -> newDictionary.modify(String.valueOf(id), type.deepCopy()));

        return newDictionary;
    }

    @Override
    public IStatement deepCopy() {

        return new CondStatement(exp.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}
