//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;

public class NcpInteractTrace extends NcpTrace
{
    protected final boolean strict = false;
    protected int lastBx;
    protected int lastBy;
    protected int lastBz;
    protected int targetX;
    protected int targetY;
    protected int targetZ;
    
    public NcpInteractTrace() {
        this.forceStepEndPos = false;
    }
    
    @Override
    public void set(final double x0, final double y0, final double z0, final double x1, final double y1, final double z1) {
        this.set(x0, y0, z0, x1, y1, z1, Visible.floor(x1), Visible.floor(y1), Visible.floor(z1));
    }
    
    public void set(final double x0, final double y0, final double z0, final double x1, final double y1, final double z1, final int targetX, final int targetY, final int targetZ) {
        super.set(x0, y0, z0, x1, y1, z1);
        this.collides = false;
        this.lastBx = this.blockX;
        this.lastBy = this.blockY;
        this.lastBz = this.blockZ;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }
    
    private boolean doesCollide(final int blockX, final int blockY, final int blockZ) {
        final BlockPos pos = new BlockPos(blockX, blockY, blockZ);
        final IBlockState state = Util.mc.world.getBlockState(pos);
        return state.getMaterial().isSolid() && !state.getMaterial().isLiquid() && state.getBlock().canCollideCheck(state, false) && state.getBoundingBox((IBlockAccess)Util.mc.world, pos).getAverageEdgeLength() == 1.0;
    }
    
    public boolean isTargetBlock() {
        return this.targetX != Integer.MAX_VALUE && this.blockX == this.targetX && this.blockY == this.targetY && this.blockZ == this.targetZ;
    }
    
    @Override
    protected boolean step(final int blockX, final int blockY, final int blockZ, final double oX, final double oY, final double oZ, final double dT, final boolean isPrimary) {
        if (this.isTargetBlock() || !this.doesCollide(blockX, blockY, blockZ)) {
            if (isPrimary) {
                this.lastBx = blockX;
                this.lastBy = blockY;
                this.lastBz = blockZ;
            }
            return true;
        }
        this.collides = true;
        return false;
    }
}
