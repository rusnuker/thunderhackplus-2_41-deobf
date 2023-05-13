//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.math.*;

public abstract class AbstractGeoCache implements GeoCache
{
    private final Vec3i[] cache;
    private final int[] indices;
    
    public AbstractGeoCache(final int expectedSize, final int indicesSize) {
        this.cache = new Vec3i[expectedSize];
        this.indices = new int[indicesSize];
    }
    
    protected abstract void fill(final Vec3i[] p0, final int[] p1);
    
    @Override
    public void cache() {
        final Vec3i dummy = new Vec3i(Integer.MAX_VALUE, 0, 0);
        this.cache[this.cache.length - 1] = dummy;
        this.fill(this.cache, this.indices);
        if (this.cache[this.cache.length - 1] == dummy) {
            throw new IllegalStateException("Cache was not filled!");
        }
    }
    
    @Override
    public int getRadius(final double radius) {
        return this.indices[MathUtil.clamp((int)Math.ceil(radius), 0, this.indices.length)];
    }
    
    @Override
    public Vec3i get(final int index) {
        return this.cache[index];
    }
    
    @Override
    public Vec3i[] array() {
        return this.cache;
    }
}
