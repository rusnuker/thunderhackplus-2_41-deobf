//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;

public class SmartRangeUtil
{
    public static boolean isInSmartRange(final BlockPos pos, final Entity entity, final double rangeSq, final int smartTicks) {
        return isInSmartRange(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, entity, rangeSq, smartTicks);
    }
    
    public static boolean isInSmartRange(final double crystalX, final double crystalY, final double crystalZ, final Entity entity, final double rangeSq, final int smartTicks) {
        final double x = entity.posX + entity.motionX * smartTicks;
        final double y = entity.posY + entity.motionY * smartTicks;
        final double z = entity.posZ + entity.motionZ * smartTicks;
        return distanceSq(crystalX, crystalY, crystalZ, x, y, z) < rangeSq;
    }
    
    public static boolean isInStrictBreakRange(final double crystalX, double crystalY, final double crystalZ, final double rangeSq, final double entityX, final double entityY, final double entityZ) {
        final double height = 2.0;
        final double pY = entityY + Util.mc.player.getEyeHeight();
        final double dY = crystalY;
        if (pY > dY) {
            if (pY >= dY + 2.0) {
                crystalY = dY + 2.0;
            }
            else {
                crystalY = pY;
            }
        }
        final double x = crystalX - entityX;
        final double y = crystalY - pY;
        final double z = crystalZ - entityZ;
        return x * x + y * y + z * z <= rangeSq;
    }
    
    public static double distanceSq(final double x, final double y, final double z, final Entity entity) {
        return distanceSq(x, y, z, entity.posX, entity.posY, entity.posZ);
    }
    
    public static double distanceSq(final double x, final double y, final double z, final double x1, final double y1, final double z1) {
        final double xDist = x - x1;
        final double yDist = y - y1;
        final double zDist = z - z1;
        return xDist * xDist + yDist * yDist + zDist * zDist;
    }
}
