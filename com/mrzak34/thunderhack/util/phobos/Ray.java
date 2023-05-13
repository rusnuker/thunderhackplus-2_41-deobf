//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class Ray
{
    private final RayTraceResult result;
    private final EnumFacing facing;
    private final BlockPos pos;
    private final Vec3d vector;
    private float[] rotations;
    private boolean legit;
    
    public Ray(final RayTraceResult result, final float[] rotations, final BlockPos pos, final EnumFacing facing, final Vec3d vector) {
        this.result = result;
        this.rotations = rotations;
        this.pos = pos;
        this.facing = facing;
        this.vector = vector;
    }
    
    public RayTraceResult getResult() {
        return this.result;
    }
    
    public void updateRotations(final Entity entity) {
        if (this.vector != null) {
            this.rotations = RayTraceFactory.rots(entity, this.vector);
        }
    }
    
    public float[] getRotations() {
        return this.rotations;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public boolean isLegit() {
        return this.legit;
    }
    
    public Ray setLegit(final boolean legit) {
        this.legit = legit;
        return this;
    }
    
    public Vec3d getVector() {
        return this.vector;
    }
}
