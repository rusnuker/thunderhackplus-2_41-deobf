//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.junit.experimental.runners;

import org.junit.runners.*;
import org.junit.runners.model.*;

public class Enclosed extends Suite
{
    public Enclosed(final Class<?> klass, final RunnerBuilder builder) throws Throwable {
        super(builder, klass, klass.getClasses());
    }
}
