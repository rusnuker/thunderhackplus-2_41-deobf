//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runners.model.*;
import org.junit.runner.*;

public class NullBuilder extends RunnerBuilder
{
    @Override
    public Runner runnerForClass(final Class<?> each) throws Throwable {
        return null;
    }
}
