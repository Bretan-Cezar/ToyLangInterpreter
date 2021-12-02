package model.expression;

import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.heap.IHeap;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticExpression implements IExpression {

    IExpression exp1;
    IExpression exp2;
    char operator;

    public ArithmeticExpression(IExpression e1, IExpression e2, char op) {

        exp1 = e1;
        exp2 = e2;
        operator = op;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {

        IValue v1,v2;
        v1 = exp1.evaluate(table, heap);

        if (v1.getType().equals(new IntType())) {

            v2 = exp2.evaluate(table, heap);

            if (v2.getType().equals(new IntType())) {

                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;

                n1 = i1.getValue();
                n2 = i2.getValue();

                switch (operator) {

                    case '+':
                        return new IntValue(n1+n2);

                    case '-':
                        return new IntValue(n1-n2);

                    case '*':
                        return new IntValue(n1*n2);

                    case '/':
                        if (n2 == 0)
                            throw new ToyLangException("Division by zero");
                        else return new IntValue(n1/n2);

                    default:
                        throw new ToyLangException("Invalid operand");
                }

            }
            else throw new ToyLangException("Second operand is not an integer");
        }
        else throw new ToyLangException("First operand is not an integer");

    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        IType t1, t2;

        t1 = exp1.typecheck(typeEnv);
        t2 = exp2.typecheck(typeEnv);

        if (t1.equals(new IntType())) {

            if (t2.equals(new IntType())) {

                return new IntType();
            }
            else throw new ToyLangException("Second operand is not an integer, got " + t2);
        }
        else throw new ToyLangException("First operand is not an integer, got " + t1);
    }

    @Override
    public IExpression deepCopy() {

        return new ArithmeticExpression(exp1.deepCopy(), exp2.deepCopy(), Character.valueOf(operator));
    }

    @Override
    public String toString() {

        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
