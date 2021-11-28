package model.statement;

import model.ProgramState;
import model.exceptions.ToyLangException;
import model.expression.IExpression;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

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

        if (state.getSymTable().isKeyDefined(var_name)) {

            IType var_type = state.getSymTable().get(var_name).getType();
            IValue val = exp.evaluate(state.getSymTable(), state.getHeap());

            System.out.println(val);

            if (var_type instanceof RefType) {

                IType inner = ((RefType) var_type).getInner();

                if (Objects.equals(inner, val.getType())) {

                    Integer addr = state.getHeap().createEntry(val);

                    state.getSymTable().modify(var_name, new RefValue(addr, inner));
                }
                else throw new ToyLangException("Mismatch in inner variable type (" + var_type + ") and expression (" + val.getType().toString() + ") types.");
            }
            else throw new ToyLangException("Variable is not of RefType.");
        }
        else throw new ToyLangException("No variable with the given name is defined.");

        return state;
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
