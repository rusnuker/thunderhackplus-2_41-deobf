//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketEntityVelocity.class })
public interface ISPacketEntityVelocity
{
    @Accessor("motionX")
    int getMotionX();
    
    @Accessor("motionY")
    int getMotionY();
    
    @Accessor("motionZ")
    int getMotionZ();
    
    @Accessor("motionX")
    void setMotionX(final int p0);
    
    @Accessor("motionY")
    void setMotionY(final int p0);
    
    @Accessor("motionZ")
    void setMotionZ(final int p0);
}
