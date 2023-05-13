//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketPlayer.class })
public interface ICPacketPlayer
{
    @Accessor("x")
    void setX(final double p0);
    
    @Accessor("y")
    void setY(final double p0);
    
    @Accessor("z")
    void setZ(final double p0);
    
    @Accessor("yaw")
    void setYaw(final float p0);
    
    @Accessor("pitch")
    void setPitch(final float p0);
    
    @Accessor("onGround")
    void setOnGround(final boolean p0);
    
    @Accessor("moving")
    boolean isMoving();
    
    @Accessor("moving")
    void setMoving(final boolean p0);
    
    @Accessor("rotating")
    boolean isRotating();
}
