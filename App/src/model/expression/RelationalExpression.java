package model.expression;

import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.heap.IHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression {

    IExpression exp1;
    IExpression exp2;
    String operator;

    public RelationalExpression(IExpression e1, IExpression e2, String op) {

        exp1 = e1;
        exp2 = e2;
        operator = op;
    }
    
    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {

        IValue v1, v2;

        v1 = exp1.evaluate(table, heap);

        if (v1.getType().equals(new IntType())) {

            v2 = exp2.evaluate(table, heap);

            if (v2.getType().equals(new IntType())) {

                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                switch (operator) {

                    case "<":
                        return new BoolValue(i1.getValue() < i2.getValue());

                    case "<=":
                        return new BoolValue(i1.getValue() <= i2.getValue());

                    case "==":
                        return new BoolValue(i1.getValue() == i2.getValue());

                    case ">":
                        return new BoolValue(i1.getValue() > i2.getValue());

                    case ">=":
                        return new BoolValue(i1.getValue() >= i2.getValue());

                    case "!=":
                        return new BoolValue(i1.getValue() != i2.getValue());

                    default:
                        throw new ToyLangException("Invalid relational operator.");
                }
            }
            else throw new ToyLangException("Second operand must be an (expression that evaluates to) int.");
        }
        else throw new ToyLangException("First operand must be an (expression that evaluates to) int.");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType t1 = exp1.typecheck(typeEnv);
        IType t2 = exp2.typecheck(typeEnv);

        if (t1.equals(new IntType())) {

            if (t2.equals(new IntType())) {

                return new BoolType();
            }
            else throw new ToyLangException("Second operand is not an int, got " + t2);
        }
        else throw new ToyLangException("First operand is not an int, got " + t1);
    }

    @Override
    public String toString() {

        return exp1.toString() + " " + operator + " " + exp2.toString();
    }

    @Override
    public IExpression deepCopy() {

        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), String.valueOf(operator));
    }
}
