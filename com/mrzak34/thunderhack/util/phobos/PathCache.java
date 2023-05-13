//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import java.util.*;

public class PathCache extends AbstractSphere
{
    public PathCache(final int expectedSize, final int indicesSize, final double radius) {
        super(expectedSize, indicesSize, radius);
    }
    
    protected Collection<BlockPos> sorter(final BlockPos middle) {
        int xpDiff;
        int ypDiff;
        int zpDiff;
        int xoDiff;
        int yoDiff;
        int zoDiff;
        int compare;
        int compare2;
        return new TreeSet<BlockPos>((o, p) -> {
            if (((BlockPos)o).equals((Object)p)) {
                return 0;
            }
            else {
                xpDiff = middle.getX() - p.getX();
                ypDiff = middle.getY() - p.getY();
                zpDiff = middle.getZ() - p.getZ();
                xoDiff = middle.getX() - ((BlockPos)o).getX();
                yoDiff = middle.getY() - ((BlockPos)o).getY();
                zoDiff = middle.getZ() - ((BlockPos)o).getZ();
                compare = Integer.compare(PathFinder.produceOffsets(false, false, xoDiff, yoDiff, zoDiff).length, PathFinder.produceOffsets(false, false, xpDiff, ypDiff, zpDiff).length);
                if (compare != 0) {
                    return compare;
                }
                else {
                    compare2 = Double.compare(middle.distanceSq(o), middle.distanceSq((Vec3i)p));
                    if (compare2 == 0) {
                        compare2 = Integer.compare(Math.abs(((BlockPos)o).getX()) + Math.abs(((BlockPos)o).getY()) + Math.abs(((BlockPos)o).getZ()), Math.abs(p.getX()) + Math.abs(p.getY()) + Math.abs(p.getZ()));
                    }
                    return (compare2 == 0) ? 1 : compare2;
                }
            }
        });
    }
}
