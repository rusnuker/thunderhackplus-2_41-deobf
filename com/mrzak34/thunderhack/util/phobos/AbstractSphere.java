//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import java.util.*;

public abstract class AbstractSphere extends AbstractGeoCache
{
    private final double r;
    
    public AbstractSphere(final int expectedSize, final int indicesSize, final double radius) {
        super(expectedSize, indicesSize);
        this.r = radius;
    }
    
    protected abstract Collection<BlockPos> sorter(final BlockPos p0);
    
    protected void fill(final Vec3i[] cache, final int[] indices) {
        final BlockPos pos = BlockPos.ORIGIN;
        final Collection<BlockPos> positions = this.sorter(pos);
        final double rSquare = this.r * this.r;
        for (int x = pos.getX() - (int)this.r; x <= pos.getX() + this.r; ++x) {
            for (int z = pos.getZ() - (int)this.r; z <= pos.getZ() + this.r; ++z) {
                for (int y = pos.getY() - (int)this.r; y < pos.getY() + this.r; ++y) {
                    final double dist = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    if (dist < rSquare) {
                        positions.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        if (positions.size() != cache.length) {
            throw new IllegalStateException("Unexpected Size for Sphere: " + positions.size() + ", expected " + cache.length + "!");
        }
        int i = 0;
        int currentDistance = 0;
        for (final BlockPos off : positions) {
            if (Math.sqrt(pos.distanceSq((Vec3i)off)) > currentDistance) {
                indices[currentDistance++] = i;
            }
            cache[i++] = (Vec3i)off;
        }
        if (currentDistance != indices.length - 1) {
            throw new IllegalStateException("Sphere Indices not initialized!");
        }
        indices[indices.length - 1] = cache.length;
    }
}
