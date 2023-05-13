//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;

public class EmptySet<T> implements Set<T>
{
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean contains(final Object o) {
        return false;
    }
    
    @Override
    public Iterator<T> iterator() {
        return Collections.emptyIterator();
    }
    
    @Override
    public Object[] toArray() {
        return new Object[0];
    }
    
    @Override
    public <T1> T1[] toArray(final T1[] a) {
        return (T1[])new Object[0];
    }
    
    @Override
    public boolean add(final T t) {
        return false;
    }
    
    @Override
    public boolean remove(final Object o) {
        return false;
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        return false;
    }
    
    @Override
    public boolean addAll(final Collection<? extends T> c) {
        return false;
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        return false;
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        return false;
    }
    
    @Override
    public void clear() {
    }
}
