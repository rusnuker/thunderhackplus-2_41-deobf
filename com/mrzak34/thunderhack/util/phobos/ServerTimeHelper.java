//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class ServerTimeHelper
{
    private static final ScheduledExecutorService THREAD;
    private final AutoCrystal module;
    private final Setting<AutoCrystal.ACRotate> rotate;
    private final Setting<AutoCrystal.SwingTime> placeSwing;
    private final Setting<Boolean> antiFeetPlace;
    private final Setting<Boolean> newVersion;
    private final Setting<Integer> buffer;
    
    public ServerTimeHelper(final AutoCrystal module, final Setting<AutoCrystal.ACRotate> rotate, final Setting<AutoCrystal.SwingTime> placeSwing, final Setting<Boolean> antiFeetPlace, final Setting<Boolean> newVersion, final Setting<Integer> buffer) {
        this.module = module;
        this.rotate = rotate;
        this.placeSwing = placeSwing;
        this.antiFeetPlace = antiFeetPlace;
        this.newVersion = newVersion;
        this.buffer = buffer;
    }
    
    public static boolean isAtFeet(final List<EntityPlayer> players, final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        for (final EntityPlayer player : players) {
            if (!Thunderhack.friendManager.isFriend(player)) {
                if (player == Util.mc.player) {
                    continue;
                }
                if (isAtFeet(player, pos, ignoreCrystals, noBoost2)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public static boolean isAtFeet(final EntityPlayer player, final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos up = pos.up();
        if (!canPlaceCrystal(pos, ignoreCrystals, noBoost2)) {
            return false;
        }
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = up.offset(face);
            if (Util.mc.world.getEntitiesWithinAABB((Class)EntityPlayer.class, new AxisAlignedBB(off)).contains(player)) {
                return true;
            }
            final BlockPos off2 = off.offset(face);
            if (Util.mc.world.getEntitiesWithinAABB((Class)EntityPlayer.class, new AxisAlignedBB(off2)).contains(player)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        return canPlaceCrystal(pos, ignoreCrystals, noBoost2, null);
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities) {
        return canPlaceCrystal(pos, ignoreCrystals, noBoost2, entities, noBoost2, 0L);
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final IBlockState state = Util.mc.world.getBlockState(pos);
        return (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) && checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
    }
    
    public static boolean canPlaceCrystalReplaceable(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final IBlockState state = Util.mc.world.getBlockState(pos);
        return (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK || state.getMaterial().isReplaceable()) && checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
    }
    
    public static boolean checkBoost(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final BlockPos boost = pos.up();
        if (Util.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || !checkEntityList(boost, ignoreCrystals, entities, deathTime)) {
            return false;
        }
        if (!noBoost2) {
            final BlockPos boost2 = boost.up();
            return Util.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && (ignoreBoost2Entities || checkEntityList(boost2, ignoreCrystals, entities, deathTime));
        }
        return true;
    }
    
    public static boolean checkEntityList(final BlockPos pos, final boolean ignoreCrystals, final List<Entity> entities) {
        return checkEntityList(pos, ignoreCrystals, entities, 0L);
    }
    
    public static boolean checkEntityList(final BlockPos pos, final boolean ignoreCrystals, final List<Entity> entities, final long deathTime) {
        if (entities == null) {
            return checkEntities(pos, ignoreCrystals, deathTime);
        }
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (checkEntity(entity, ignoreCrystals, deathTime) && entity.getEntityBoundingBox().intersects(bb)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean checkEntities(final BlockPos pos, final boolean ignoreCrystals, final long deathTime) {
        for (final Entity entity : Util.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (checkEntity(entity, ignoreCrystals, deathTime)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkEntity(final Entity entity, final boolean ignoreCrystals, final long deathTime) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof EntityEnderCrystal) {
            return !ignoreCrystals && (!entity.isDead || !Thunderhack.setDeadManager.passedDeathTime(entity, deathTime));
        }
        return !EntityUtil.isDead(entity);
    }
    
    public static boolean isSemiSafe(final EntityPlayer player, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos origin = player.getPosition();
        int i = 0;
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = origin.offset(face);
            if (Util.mc.world.getBlockState(off).getBlock() != Blocks.AIR) {
                ++i;
            }
        }
        return i >= 3;
    }
    
    public static EntityPlayer getClosestEnemy() {
        return getClosestEnemy(Util.mc.world.playerEntities);
    }
    
    public static EntityPlayer getClosestEnemy(final List<EntityPlayer> list) {
        return getClosestEnemy(Util.mc.player.getPositionVector(), list);
    }
    
    public static EntityPlayer getClosestEnemy(final BlockPos pos, final List<EntityPlayer> list) {
        return getClosestEnemy(pos.getX(), pos.getY(), pos.getZ(), list);
    }
    
    public static EntityPlayer getClosestEnemy(final Vec3d vec3d, final List<EntityPlayer> list) {
        return getClosestEnemy(vec3d.x, vec3d.y, vec3d.z, list);
    }
    
    public static EntityPlayer getClosestEnemy(final double x, final double y, final double z, final double maxRange, final List<EntityPlayer> enemies, final List<EntityPlayer> players) {
        final EntityPlayer closestEnemied = getClosestEnemy(x, y, z, enemies);
        if (closestEnemied != null && closestEnemied.getDistanceSq(x, y, z) < MathUtil.square(maxRange)) {
            return closestEnemied;
        }
        return getClosestEnemy(x, y, z, players);
    }
    
    public static EntityPlayer getClosestEnemy(final double x, final double y, final double z, final List<EntityPlayer> players) {
        EntityPlayer closest = null;
        double distance = 3.4028234663852886E38;
        for (final EntityPlayer player : players) {
            if (player != null && !player.isDead && !player.equals((Object)Util.mc.player) && !Thunderhack.friendManager.isFriend(player)) {
                final double dist = player.getDistanceSq(x, y, z);
                if (dist >= distance) {
                    continue;
                }
                closest = player;
                distance = dist;
            }
        }
        return closest;
    }
    
    public static boolean isValid(final Entity player, final double range) {
        return player != null && !player.isDead && Util.mc.player.getDistanceSq(player) <= MathUtil.square(range) && !Thunderhack.friendManager.isFriend((EntityPlayer)player);
    }
    
    public void onUseEntity(final CPacketUseEntity packet, final Entity crystal) {
        final EntityPlayer closest;
        if (packet.getAction() == CPacketUseEntity.Action.ATTACK && (boolean)this.antiFeetPlace.getValue() && (this.rotate.getValue() == AutoCrystal.ACRotate.None || this.rotate.getValue() == AutoCrystal.ACRotate.Break) && crystal instanceof EntityEnderCrystal && (closest = getClosestEnemy()) != null && isSemiSafe(closest, true, (boolean)this.newVersion.getValue()) && isAtFeet(Util.mc.world.playerEntities, crystal.getPosition().down(), true, (boolean)this.newVersion.getValue())) {
            final int intoTick = Thunderhack.servtickManager.getTickTimeAdjusted();
            final int sleep = Thunderhack.servtickManager.getServerTickLengthMS() + Thunderhack.servtickManager.getSpawnTime() + (int)this.buffer.getValue() - intoTick;
            this.place(crystal.getPosition().down(), sleep);
        }
    }
    
    private void place(final BlockPos pos, final int sleep) {
        final AutoCrystal.SwingTime time = (AutoCrystal.SwingTime)this.placeSwing.getValue();
        EnumHand hand;
        RayTraceResult ray;
        float[] f;
        final AutoCrystal.SwingTime swingTime;
        ServerTimeHelper.THREAD.schedule(() -> {
            if (InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                hand = InventoryUtil.getHand(Items.END_CRYSTAL);
                ray = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.world);
                f = RayTraceUtil.hitVecToPlaceVec(pos, ray.hitVec);
                if (swingTime == AutoCrystal.SwingTime.Pre) {
                    Swing.Packet.swing(hand);
                    Swing.Client.swing(hand);
                }
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, ray.sideHit, hand, f[0], f[1], f[2]));
                this.module.sequentialHelper.setExpecting(pos);
                if (swingTime == AutoCrystal.SwingTime.Post) {
                    Swing.Packet.swing(hand);
                    Swing.Client.swing(hand);
                }
            }
        }, sleep, TimeUnit.MILLISECONDS);
    }
    
    static {
        THREAD = ThreadUtil.newDaemonScheduledExecutor("Server-Helper");
    }
}
