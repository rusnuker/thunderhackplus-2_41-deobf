//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.*;

public class PyroSpeed
{
    public static AxisAlignedBB Method5403(final double d) {
        final double[] arrd = Method732(0.20000000298023224);
        double d2 = arrd[0];
        double d3 = arrd[1];
        final double d4 = d2;
        final double d5 = d3;
        final List<AxisAlignedBB> list = (List<AxisAlignedBB>)Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, Util.mc.player.getEntityBoundingBox().expand(d2, 0.0, d3));
        AxisAlignedBB axisAlignedBB = Util.mc.player.getEntityBoundingBox();
        if (d2 != 0.0) {
            for (int n2 = list.size(), n3 = 0; n3 < n2; ++n3) {
                d2 = list.get(n3).calculateXOffset(axisAlignedBB, d2);
            }
            if (d2 != 0.0) {
                axisAlignedBB = axisAlignedBB.offset(d2, 0.0, 0.0);
            }
        }
        if (d3 != 0.0) {
            for (int n2 = list.size(), n3 = 0; n3 < n2; ++n3) {
                d3 = list.get(n3).calculateZOffset(axisAlignedBB, d3);
            }
            if (d3 != 0.0) {
                axisAlignedBB = axisAlignedBB.offset(0.0, 0.0, d3);
            }
        }
        final double d6 = d2;
        final double d7 = d3;
        final AxisAlignedBB axisAlignedBB2 = Util.mc.player.getEntityBoundingBox();
        double d8 = d;
        final List<AxisAlignedBB> list2 = (List<AxisAlignedBB>)Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, axisAlignedBB.expand(d4, d8, d5));
        AxisAlignedBB axisAlignedBB3 = axisAlignedBB;
        final AxisAlignedBB axisAlignedBB4 = axisAlignedBB3.expand(d4, 0.0, d5);
        double d9 = d8;
        for (final AxisAlignedBB axisAlignedBB5 : list2) {
            d9 = axisAlignedBB5.calculateYOffset(axisAlignedBB4, d9);
        }
        axisAlignedBB3 = axisAlignedBB3.offset(0.0, d9, 0.0);
        double d10 = d4;
        for (final AxisAlignedBB axisAlignedBB6 : list2) {
            d10 = axisAlignedBB6.calculateXOffset(axisAlignedBB3, d10);
        }
        axisAlignedBB3 = axisAlignedBB3.offset(d10, 0.0, 0.0);
        double d11 = d5;
        for (final AxisAlignedBB element : list2) {
            d11 = element.calculateZOffset(axisAlignedBB3, d11);
        }
        axisAlignedBB3 = axisAlignedBB3.offset(0.0, 0.0, d11);
        AxisAlignedBB axisAlignedBB7 = axisAlignedBB;
        double d12 = d8;
        for (final AxisAlignedBB item : list2) {
            d12 = item.calculateYOffset(axisAlignedBB7, d12);
        }
        axisAlignedBB7 = axisAlignedBB7.offset(0.0, d12, 0.0);
        double d13 = d4;
        for (final AxisAlignedBB value : list2) {
            d13 = value.calculateXOffset(axisAlignedBB7, d13);
        }
        axisAlignedBB7 = axisAlignedBB7.offset(d13, 0.0, 0.0);
        double d14 = d5;
        for (final AxisAlignedBB bb : list2) {
            d14 = bb.calculateZOffset(axisAlignedBB7, d14);
        }
        axisAlignedBB7 = axisAlignedBB7.offset(0.0, 0.0, d14);
        final double d15 = d10 * d10 + d11 * d11;
        final double d16 = d13 * d13 + d14 * d14;
        AxisAlignedBB axisAlignedBB8;
        if (d15 > d16) {
            d2 = d10;
            d3 = d11;
            d8 = -d9;
            axisAlignedBB8 = axisAlignedBB3;
        }
        else {
            d2 = d13;
            d3 = d14;
            d8 = -d12;
            axisAlignedBB8 = axisAlignedBB7;
        }
        for (final AxisAlignedBB alignedBB : list2) {
            d8 = alignedBB.calculateYOffset(axisAlignedBB8, d8);
        }
        axisAlignedBB8 = axisAlignedBB8.offset(0.0, d8, 0.0);
        if (d6 * d6 + d7 * d7 >= d2 * d2 + d3 * d3) {
            axisAlignedBB8 = axisAlignedBB2;
        }
        return axisAlignedBB8;
    }
    
    public static boolean isMovingClient() {
        return Util.mc.player != null && (Util.mc.player.movementInput.moveForward != 0.0f || Util.mc.player.movementInput.moveStrafe != 0.0f);
    }
    
    public static void Method744(final EventMove event, final double d) {
        final MovementInput movementInput = Util.mc.player.movementInput;
        double d2 = movementInput.moveForward;
        double d3 = movementInput.moveStrafe;
        d3 = MathUtil.clamp(d3, -1.0, 1.0);
        float f = Util.mc.player.rotationYaw;
        if (d2 == 0.0 && d3 == 0.0) {
            event.set_x(0.0);
            event.set_z(0.0);
        }
        else {
            if (d2 != 0.0) {
                if (d3 > 0.0) {
                    f += ((d2 > 0.0) ? -45 : 45);
                }
                else if (d3 < 0.0) {
                    f += ((d2 > 0.0) ? 45 : -45);
                }
                d3 = 0.0;
                if (d2 > 0.0) {
                    d2 = 1.0;
                }
                else if (d2 < 0.0) {
                    d2 = -1.0;
                }
            }
            event.set_x(d2 * d * Math.cos(Math.toRadians(f + 90.0f)) + d3 * d * Math.sin(Math.toRadians(f + 90.0f)));
            event.set_z(d2 * d * Math.sin(Math.toRadians(f + 90.0f)) - d3 * d * Math.cos(Math.toRadians(f + 90.0f)));
        }
    }
    
    public static double Method5402(final double d) {
        if (!Util.mc.player.onGround) {
            return 0.0;
        }
        if (!Util.mc.player.collidedHorizontally) {
            return 0.0;
        }
        if (Util.mc.player.fallDistance != 0.0f) {
            return 0.0;
        }
        if (Util.mc.player.isInWater()) {
            return 0.0;
        }
        if (Util.mc.player.isInLava()) {
            return 0.0;
        }
        if (Util.mc.player.isOnLadder()) {
            return 0.0;
        }
        if (Util.mc.player.movementInput.jump) {
            return 0.0;
        }
        if (Util.mc.player.movementInput.sneak) {
            return 0.0;
        }
        return Method5403(d).minY - Util.mc.player.getEntityBoundingBox().minY;
    }
    
    public static double Method718() {
        float f = Util.mc.player.rotationYaw;
        if (Util.mc.player.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (Util.mc.player.moveForward < 0.0f) {
            f2 = -0.5f;
        }
        else if (Util.mc.player.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (Util.mc.player.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (Util.mc.player.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }
    
    public static double[] Method732(final double d) {
        if (!isMovingClient()) {
            return null;
        }
        final double d2 = Method718();
        final double d3 = -Math.sin(d2) * d;
        final double d4 = Math.cos(d2) * d;
        return new double[] { d3, d4 };
    }
}
