package com.toylanggui.interpreter.model.dictionary;

import java.util.HashMap;

public interface IDictionary<K, V> {

    boolean isKeyDefined(K key);

    V get(K key);

    void modify(K key, V value);

    void delete(K key);

    String toLogString();

    HashMap<K, V> getContent();
}
