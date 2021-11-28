package model.dictionary;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<K, V> implements IDictionary<K, V> {

    private HashMap<K, V> dictionary;

    public Dictionary() {

        dictionary = new HashMap<>();
    }


    @Override
    public boolean isKeyDefined(K key) {
        return (dictionary.containsKey(key));
    }

    @Override
    public V get(K key) {

        return dictionary.get(key);
    }

    @Override
    public void modify(K key, V value) {

        dictionary.put(key, value);
    }

    @Override
    public void delete(K key) {

        dictionary.remove(key);
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append("[");

        for (HashMap.Entry<K, V> entry : dictionary.entrySet()) {

            str.append(entry.getKey().toString());
            str.append(" = ");
            str.append(entry.getValue().toString());
            str.append(" ; ");
        }

        if (!dictionary.isEmpty())
            str.delete(str.length()-3, str.length());

        str.append("]");

        return str.toString();
    }

    @Override
    public String toLogString() {

        StringBuilder str = new StringBuilder();

        for (Map.Entry<K, V> entry : dictionary.entrySet()) {

            str.append("    ");
            str.append(entry.getKey().toString());
            str.append(" -> ");
            str.append(entry.getValue().toString());
            str.append("\n");
        }

        return str.toString();
    }

    @Override
    public HashMap<K, V> getContent() {
        return dictionary;
    }


}
