//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;

@FunctionalInterface
public interface TriPredicate<T, U, V>
{
    boolean test(final T p0, final U p1, final V p2);
    
    default TriPredicate<T, U, V> and(final TriPredicate<? super T, ? super U, ? super V> other) {
        Objects.requireNonNull(other);
        return (t, u, v) -> this.test((T)t, (U)u, (V)v) && other.test((Object)t, (Object)u, (Object)v);
    }
    
    default TriPredicate<T, U, V> negate() {
        return (t, u, v) -> !this.test(t, u, v);
    }
    
    default TriPredicate<T, U, V> or(final TriPredicate<? super T, ? super U, ? super V> other) {
        Objects.requireNonNull(other);
        return (t, u, v) -> this.test((T)t, (U)u, (V)v) || other.test((Object)t, (Object)u, (Object)v);
    }
}
