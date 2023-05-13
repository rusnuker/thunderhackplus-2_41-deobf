//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketExplosion.class })
public interface ISPacketExplosion
{
    @Accessor("motionX")
    float getMotionX();
    
    @Accessor("motionY")
    float getMotionY();
    
    @Accessor("motionZ")
    float getMotionZ();
    
    @Accessor("motionX")
    void setMotionX(final float p0);
    
    @Accessor("motionY")
    void setMotionY(final float p0);
    
    @Accessor("motionZ")
    void setMotionZ(final float p0);
}
