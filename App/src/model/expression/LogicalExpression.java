package model.expression;

import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.heap.IHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicalExpression implements IExpression {

    IExpression exp1;
    IExpression exp2;
    String operator;

    public LogicalExpression(IExpression e1, IExpression e2, String op) {

        exp1 = e1;
        exp2 = e2;
        operator = op;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {

        IValue v1, v2;

        v1 = exp1.evaluate(table, heap);

        if (v1.getType().equals(new BoolType())) {

            v2 = exp2.evaluate(table, heap);

            if (v2.getType().equals(new BoolType())) {

                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;

                switch (operator) {

                    case "&&":
                        return new BoolValue(b1.getValue() && b2.getValue());

                    case "||":
                        return new BoolValue(b1.getValue() || b2.getValue());

                    case "^":
                        return new BoolValue(b1.getValue() ^ b2.getValue());

                    default:
                        throw new ToyLangException("Invalid logical operator");
                }

            }
            else throw new ToyLangException("Second operand is not a boolean");
        }
        else throw new ToyLangException("First operand is not a boolean");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType t1, t2;

        t1 = exp1.typecheck(typeEnv);
        t2 = exp2.typecheck(typeEnv);

        if (t1.equals(new BoolType())) {

            if (t2.equals(new BoolType())) {

                return new BoolType();
            }
            else throw new ToyLangException("Second operand is not a boolean, got " + t2);
        }
        else throw new ToyLangException("First operand is not a boolean, got " + t1);
    }

    @Override
    public IExpression deepCopy() {

        return new LogicalExpression(exp1.deepCopy(), exp2.deepCopy(), String.valueOf(operator));
    }

    @Override
    public String toString() {

        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
