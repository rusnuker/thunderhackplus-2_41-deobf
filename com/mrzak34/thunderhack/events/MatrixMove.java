//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

@Cancelable
public class MatrixMove extends Event
{
    private final boolean toGround;
    private final AxisAlignedBB aabbFrom;
    private final double fromX;
    private final double fromY;
    private final double fromZ;
    private double motionX;
    private double motionY;
    private double motionZ;
    
    public MatrixMove(final double fromX, final double fromY, final double fromZ, final double motionX, final double motionY, final double motionZ, final boolean toGround, final AxisAlignedBB aabbFrom) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.fromZ = fromZ;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.toGround = toGround;
        this.aabbFrom = aabbFrom;
    }
    
    public double getFromX() {
        return this.fromX;
    }
    
    public double getFromZ() {
        return this.fromZ;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
    
    public AxisAlignedBB getAABBFrom() {
        return this.aabbFrom;
    }
    
    public boolean toGround() {
        return this.toGround;
    }
}
