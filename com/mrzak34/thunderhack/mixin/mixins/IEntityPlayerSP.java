//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityPlayerSP.class })
public interface IEntityPlayerSP
{
    @Accessor("serverSneakState")
    boolean getServerSneakState();
    
    @Accessor("serverSneakState")
    void setServerSneakState(final boolean p0);
    
    @Accessor("serverSprintState")
    boolean getServerSprintState();
    
    @Accessor("serverSprintState")
    void setServerSprintState(final boolean p0);
    
    @Accessor("wasFallFlying")
    boolean wasFallFlying();
    
    @Accessor("prevOnGround")
    boolean getPrevOnGround();
    
    @Accessor("prevOnGround")
    void setPrevOnGround(final boolean p0);
    
    @Accessor("autoJumpEnabled")
    void setAutoJumpEnabled(final boolean p0);
    
    @Accessor("lastReportedPosX")
    double getLastReportedPosX();
    
    @Accessor("lastReportedPosX")
    void setLastReportedPosX(final double p0);
    
    @Accessor("lastReportedPosY")
    double getLastReportedPosY();
    
    @Accessor("lastReportedPosY")
    void setLastReportedPosY(final double p0);
    
    @Accessor("lastReportedPosZ")
    double getLastReportedPosZ();
    
    @Accessor("lastReportedPosZ")
    void setLastReportedPosZ(final double p0);
    
    @Accessor("lastReportedYaw")
    float getLastReportedYaw();
    
    @Accessor("lastReportedYaw")
    void setLastReportedYaw(final float p0);
    
    @Accessor("lastReportedPitch")
    float getLastReportedPitch();
    
    @Accessor("lastReportedPitch")
    void setLastReportedPitch(final float p0);
    
    @Accessor("positionUpdateTicks")
    int getPositionUpdateTicks();
    
    @Accessor("positionUpdateTicks")
    void setPositionUpdateTicks(final int p0);
    
    @Invoker("onUpdateWalkingPlayer")
    void invokeOnUpdateWalkingPlayer();
}
