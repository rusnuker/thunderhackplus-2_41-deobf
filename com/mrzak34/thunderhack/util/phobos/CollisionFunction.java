//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

@FunctionalInterface
public interface CollisionFunction
{
    public static final CollisionFunction DEFAULT = IBlockProperties::collisionRayTrace;
    
    RayTraceResult collisionRayTrace(final IBlockState p0, final World p1, final BlockPos p2, final Vec3d p3, final Vec3d p4);
}
