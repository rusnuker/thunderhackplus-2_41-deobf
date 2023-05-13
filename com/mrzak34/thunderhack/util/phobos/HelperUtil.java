//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class HelperUtil
{
    public static AutoCrystal.BreakValidity isValid(final AutoCrystal module, final Entity crystal) {
        return isValid(module, crystal, false);
    }
    
    public static AutoCrystal.BreakValidity isValid(final AutoCrystal module, final Entity crystal, final boolean lastPos) {
        if ((int)module.existed.getValue() != 0 && System.currentTimeMillis() - ((IEntity)crystal).getTimeStampT() + (module.pingExisted.getValue() ? (Thunderhack.serverManager.getPing() / 2.0) : 0.0) < (int)module.existed.getValue()) {
            return AutoCrystal.BreakValidity.INVALID;
        }
        if ((lastPos && !module.rangeHelper.isCrystalInRangeOfLastPosition(crystal)) || (!lastPos && !module.rangeHelper.isCrystalInRange(crystal))) {
            return AutoCrystal.BreakValidity.INVALID;
        }
        if (((lastPos && Thunderhack.positionManager.getDistanceSq(crystal) >= MathUtil.square((double)(float)module.breakTrace.getValue())) || (!lastPos && Util.mc.player.getDistanceSq(crystal) >= MathUtil.square((double)(float)module.breakTrace.getValue()))) && ((lastPos && !Thunderhack.positionManager.canEntityBeSeen(crystal)) || (!lastPos && !RayTraceUtil.canBeSeen(new Vec3d(crystal.posX, crystal.posY + 1.7, crystal.posZ), (Entity)Util.mc.player)))) {
            return AutoCrystal.BreakValidity.INVALID;
        }
        if (module.noRotateNigga(AutoCrystal.ACRotate.Break) || module.isNotCheckingRotations() || (CalculationMotion.isLegit(crystal, new Entity[] { crystal }) && AutoCrystal.POSITION_HISTORY.arePreviousRotationsLegit(crystal, (int)module.rotationTicks.getValue(), true))) {
            return AutoCrystal.BreakValidity.VALID;
        }
        return AutoCrystal.BreakValidity.ROTATIONS;
    }
    
    public static void simulateExplosion(final AutoCrystal module, final double x, final double y, final double z) {
        final List<Entity> entities = (List<Entity>)Util.mc.world.loadedEntityList;
        if (entities == null) {
            return;
        }
        for (final Entity entity : entities) {
            if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(x, y, z) < 144.0) {
                if (module.pseudoSetDead.getValue()) {
                    ((IEntity)entity).setPseudoDeadT(true);
                }
                else {
                    Thunderhack.setDeadManager.setDead(entity);
                }
            }
        }
    }
    
    public static boolean validChange(final BlockPos pos, final List<EntityPlayer> players) {
        for (final EntityPlayer player : players) {
            if (player != null && !player.equals((Object)Util.mc.player) && !player.isDead) {
                if (Thunderhack.friendManager.isFriend(player)) {
                    continue;
                }
                if (player.getDistanceSqToCenter(pos) <= 4.0 && player.posY >= pos.getY()) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public static boolean valid(final Entity entity, final double range, final double trace) {
        final EntityPlayer player = (EntityPlayer)Util.mc.player;
        final double d = entity.getDistanceSq((Entity)player);
        return d < MathUtil.square(range) && (d < trace || RayTraceUtil.canBeSeen(entity, (EntityLivingBase)player));
    }
}
