//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class EventEntityMove extends Event
{
    private final Entity ctx;
    private final Vec3d from;
    
    public EventEntityMove(final Entity ctx, final Vec3d from) {
        this.ctx = ctx;
        this.from = from;
    }
    
    public Vec3d from() {
        return this.from;
    }
    
    public Entity ctx() {
        return this.ctx;
    }
}
