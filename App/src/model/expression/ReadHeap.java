package model.expression;

import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.heap.IHeap;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeap implements IExpression {

    IExpression exp;

    public ReadHeap(IExpression e) {

        exp = e;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {

        IValue v1 = exp.evaluate(table, heap);

        if (v1 instanceof RefValue) {

            RefValue ref = (RefValue) v1;
            Integer addr = ref.getAddr();

            if (heap.entryDefined(addr)) {

                return heap.readEntry(addr);
            }
            else throw new ToyLangException("Address " + ref.getAddr() + " from variable " + ref + " of type RefValue is not allocated in the heap.");
        }
        else throw new ToyLangException("Given expression does not evaluate to RefValue.");
    }

    @Override
    public IExpression deepCopy() {

        return new ReadHeap(exp.deepCopy());
    }

    @Override
    public String toString() {

        return "ReadHeap(" + exp.toString() + ")";
    }
}
