//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.math.*;
import java.util.*;

public class Sphere
{
    private static final Vec3i[] SPHERE;
    private static final int[] INDICES;
    
    private Sphere() {
        throw new AssertionError();
    }
    
    public static int getRadius(final double radius) {
        return Sphere.INDICES[MathUtil.clamp((int)Math.ceil(radius), 0, Sphere.INDICES.length)];
    }
    
    public static Vec3i get(final int index) {
        return Sphere.SPHERE[index];
    }
    
    public static int getLength() {
        return Sphere.SPHERE.length;
    }
    
    public static void cacheSphere() {
        long time = System.currentTimeMillis();
        final BlockPos pos = BlockPos.ORIGIN;
        final BlockPos blockPos;
        int compare;
        final Set<BlockPos> positions = new TreeSet<BlockPos>((o, p) -> {
            if (((BlockPos)o).equals((Object)p)) {
                return 0;
            }
            else {
                compare = Double.compare(blockPos.distanceSq(o), blockPos.distanceSq((Vec3i)p));
                if (compare == 0) {
                    compare = Integer.compare(Math.abs(((BlockPos)o).getX()) + Math.abs(((BlockPos)o).getY()) + Math.abs(((BlockPos)o).getZ()), Math.abs(p.getX()) + Math.abs(p.getY()) + Math.abs(p.getZ()));
                }
                return (compare == 0) ? 1 : compare;
            }
        });
        final double r = 100.0;
        final double rSquare = r * r;
        for (int x = pos.getX() - (int)r; x <= pos.getX() + r; ++x) {
            for (int z = pos.getZ() - (int)r; z <= pos.getZ() + r; ++z) {
                for (int y = pos.getY() - (int)r; y < pos.getY() + r; ++y) {
                    final double dist = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    if (dist < rSquare) {
                        positions.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        if (positions.size() != Sphere.SPHERE.length) {
            throw new IllegalStateException("Unexpected Size for Sphere: " + positions.size() + ", expected " + Sphere.SPHERE.length + "!");
        }
        int i = 0;
        int currentDistance = 0;
        for (final BlockPos off : positions) {
            if (Math.sqrt(pos.distanceSq((Vec3i)off)) > currentDistance) {
                Sphere.INDICES[currentDistance++] = i;
            }
            Sphere.SPHERE[i++] = (Vec3i)off;
        }
        if (currentDistance != Sphere.INDICES.length - 1) {
            throw new IllegalStateException("Sphere Indices not initialized!");
        }
        Sphere.INDICES[Sphere.INDICES.length - 1] = Sphere.SPHERE.length;
        if (Sphere.SPHERE[Sphere.SPHERE.length - 1].getX() == Integer.MAX_VALUE) {
            throw new IllegalStateException("Sphere wasn't filled!");
        }
        time = System.currentTimeMillis() - time;
    }
    
    static {
        SPHERE = new Vec3i[4187707];
        INDICES = new int[101];
        Sphere.SPHERE[Sphere.SPHERE.length - 1] = new Vec3i(Integer.MAX_VALUE, 0, 0);
    }
}
