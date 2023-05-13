//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class IsSame<T> extends BaseMatcher<T>
{
    private final T object;
    
    public IsSame(final T object) {
        this.object = object;
    }
    
    public boolean matches(final Object arg) {
        return arg == this.object;
    }
    
    public void describeTo(final Description description) {
        description.appendText("same(").appendValue(this.object).appendText(")");
    }
    
    @Factory
    public static <T> Matcher<T> sameInstance(final T object) {
        return new IsSame<T>(object);
    }
}
