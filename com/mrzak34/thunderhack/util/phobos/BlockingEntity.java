//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class BlockingEntity
{
    private final Entity entity;
    private final BlockPos pos;
    
    public BlockingEntity(final Entity entity, final BlockPos pos) {
        this.entity = entity;
        this.pos = pos;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public BlockPos getBlockedPos() {
        return this.pos;
    }
}
