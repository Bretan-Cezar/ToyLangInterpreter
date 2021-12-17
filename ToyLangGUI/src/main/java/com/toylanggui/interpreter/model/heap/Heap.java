package com.toylanggui.interpreter.model.heap;


import com.toylanggui.interpreter.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap {

    HashMap<Integer, IValue> map;
    Integer freeAddr;

    public Heap() {

        map = new HashMap<Integer, IValue>();
        freeAddr = 1;
    }

    @Override
    public Integer createEntry(IValue val) {

        Integer addr = freeAddr;

        map.put(freeAddr, val);
        while (map.containsKey(freeAddr))
            freeAddr++;

        return addr;
    }

    @Override
    public boolean entryDefined(Integer addr) {
        return map.containsKey(addr);
    }

    @Override
    public IValue readEntry(Integer addr) {
        return map.get(addr);
    }

    @Override
    public void writeEntry(Integer addr, IValue val) {
        map.put(addr, val);
    }

    @Override
    public HashMap<Integer, IValue> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, IValue> newMap) {

        map.clear();
        map.putAll(newMap);
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append("[");

        for (HashMap.Entry<Integer, IValue> entry : map.entrySet()) {

            str.append(entry.getKey().toString());
            str.append(" = ");
            str.append(entry.getValue().toString());
            str.append(" ; ");
        }

        if (!map.isEmpty())
            str.delete(str.length()-3, str.length());

        str.append("]");

        return str.toString();
    }

    @Override
    public String toLogString() {

        StringBuilder str = new StringBuilder();

        for (Map.Entry<Integer, IValue> entry : map.entrySet()) {

            str.append("    ");
            str.append(entry.getKey().toString());
            str.append(" -> ");
            str.append(entry.getValue().toString());
            str.append("\n");
        }

        return str.toString();
    }
}
