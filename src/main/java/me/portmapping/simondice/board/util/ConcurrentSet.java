package me.portmapping.simondice.board.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Deprecated
public final class ConcurrentSet<E> extends AbstractSet<E> implements Serializable {
    private static final long serialVersionUID = -6761513279741915432L;
    private final ConcurrentMap<E, Boolean> map = new ConcurrentHashMap<>();

    public ConcurrentSet() {
    }

    public int size() {
        return this.map.size();
    }

    public boolean contains(Object o) {
        return this.map.containsKey(o);
    }

    public boolean add(E o) {
        return this.map.putIfAbsent(o, Boolean.TRUE) == null;
    }

    public boolean remove(Object o) {
        return this.map.remove(o) != null;
    }

    public void clear() {
        this.map.clear();
    }

    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
}
