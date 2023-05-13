//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;

public interface Pathable
{
    BlockPos getPos();
    
    Entity getFrom();
    
    Ray[] getPath();
    
    void setPath(final Ray... p0);
    
    int getMaxLength();
    
    boolean isValid();
    
    void setValid(final boolean p0);
    
    List<BlockingEntity> getBlockingEntities();
}
