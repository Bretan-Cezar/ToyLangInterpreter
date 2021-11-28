package model.heap;

import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public interface IHeap {

    Integer createEntry(IValue val);

    boolean entryDefined(Integer addr);

    IValue readEntry(Integer addr);

    void writeEntry(Integer addr, IValue val);

    HashMap<Integer, IValue> getContent();

    void setContent(Map<Integer, IValue> newMap);

    String toLogString();
}
