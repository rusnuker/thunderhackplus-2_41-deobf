//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;

public class Fail extends Statement
{
    private final Throwable fError;
    
    public Fail(final Throwable e) {
        this.fError = e;
    }
    
    @Override
    public void evaluate() throws Throwable {
        throw this.fError;
    }
}
