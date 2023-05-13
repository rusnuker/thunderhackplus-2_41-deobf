//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.rotations;

import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;

public class ResolverUtil
{
    public static double backUpX;
    public static double backUpY;
    public static double backUpZ;
    public static double serverX;
    public static double serverY;
    public static double serverZ;
    public static double prevServerX;
    public static double prevServerY;
    public static double prevServerZ;
    
    public static void resolve(final EntityOtherPlayerMP player) {
        ResolverUtil.backUpX = player.posX;
        ResolverUtil.backUpY = player.posY;
        ResolverUtil.backUpZ = player.posZ;
        final Vec3d position = Util.mc.player.getPositionVector();
        final Vec3d from = new Vec3d(ResolverUtil.prevServerX, ResolverUtil.prevServerY, ResolverUtil.prevServerZ);
        final Vec3d to = new Vec3d(ResolverUtil.serverX, ResolverUtil.serverY, ResolverUtil.serverZ);
        Vec3d target;
        if (position.distanceTo(from) > position.distanceTo(to)) {
            target = to;
        }
        else {
            target = from;
        }
        if (ResolverUtil.prevServerX != 0.0 && ResolverUtil.prevServerZ != 0.0 && ResolverUtil.prevServerY != 0.0 && ResolverUtil.serverY != 0.0 && ResolverUtil.serverX != 0.0 && ResolverUtil.serverZ != 0.0) {
            player.setPosition(target.x, target.y, target.z);
        }
    }
    
    public static void releaseResolver(final EntityOtherPlayerMP player) {
        if (ResolverUtil.backUpY != -999.0) {
            player.setPosition(ResolverUtil.backUpX, ResolverUtil.backUpY, ResolverUtil.backUpZ);
            ResolverUtil.backUpY = -999.0;
        }
    }
    
    public static void reset() {
        ResolverUtil.backUpX = 0.0;
        ResolverUtil.backUpY = -999.0;
        ResolverUtil.backUpZ = 0.0;
        ResolverUtil.serverX = 0.0;
        ResolverUtil.serverY = 0.0;
        ResolverUtil.serverZ = 0.0;
        ResolverUtil.prevServerX = 0.0;
        ResolverUtil.prevServerY = 0.0;
        ResolverUtil.prevServerZ = 0.0;
    }
}
