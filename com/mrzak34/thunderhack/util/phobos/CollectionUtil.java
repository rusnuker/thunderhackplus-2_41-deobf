//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.function.*;
import java.util.*;

public class CollectionUtil
{
    public static void emptyQueue(final Queue<Runnable> runnables) {
        emptyQueue(runnables, Runnable::run);
    }
    
    public static <T> void emptyQueue(final Queue<T> queue, final Consumer<T> onPoll) {
        while (!queue.isEmpty()) {
            final T polled = queue.poll();
            if (polled != null) {
                onPoll.accept(polled);
            }
        }
    }
    
    @SafeVarargs
    public static <T> List<List<T>> split(final List<T> list, final Predicate<T>... predicates) {
        final List<List<T>> result = new ArrayList<List<T>>(predicates.length + 1);
        final List<T> current = new ArrayList<T>((Collection<? extends T>)list);
        List<T> next = new ArrayList<T>();
        for (final Predicate<T> p : predicates) {
            final Iterator<T> it = current.iterator();
            while (it.hasNext()) {
                final T t = it.next();
                if (p.test(t)) {
                    next.add(t);
                    it.remove();
                }
            }
            result.add(next);
            next = new ArrayList<T>();
        }
        result.add(current);
        return result;
    }
    
    public static <T, C extends T> C getByClass(final Class<C> clazz, final Collection<T> collection) {
        for (final T t : collection) {
            if (clazz.isInstance(t)) {
                return (C)t;
            }
        }
        return null;
    }
    
    public static <T, R> List<T> convert(final R[] array, final Function<R, T> function) {
        final List<T> result = new ArrayList<T>(array.length);
        for (int i = 0; i < array.length; ++i) {
            result.add(i, function.apply(array[i]));
        }
        return result;
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        list.sort((Comparator<? super Map.Entry<K, V>>)Map.Entry.comparingByValue());
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    public static <T> T getLast(final Collection<T> iterable) {
        T last = null;
        final Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final T t = last = iterator.next();
        }
        return last;
    }
}
