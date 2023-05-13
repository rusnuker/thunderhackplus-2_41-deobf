//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;

public class Observable<T>
{
    private final List<Observer<? super T>> observers;
    private boolean deactivated;
    
    public Observable() {
        this.observers = new LinkedList<Observer<? super T>>();
    }
    
    public T onChange(final T value) {
        if (!this.deactivated) {
            for (final Observer<? super T> observer : this.observers) {
                observer.onChange((Object)value);
            }
        }
        return value;
    }
    
    public void addObserver(final Observer<? super T> observer) {
        if (observer != null && !this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }
    
    public void removeObserver(final Observer<? super T> observer) {
        this.observers.remove(observer);
    }
    
    public void setObserversDeactivated(final boolean deactivated) {
        this.deactivated = deactivated;
    }
}
