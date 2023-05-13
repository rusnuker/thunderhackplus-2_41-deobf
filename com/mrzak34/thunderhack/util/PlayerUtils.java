//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PlayerUtils
{
    private static final Minecraft mc;
    
    public static boolean isPlayerMoving() {
        return PlayerUtils.mc.gameSettings.keyBindForward.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindBack.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindRight.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindLeft.isKeyDown();
    }
    
    public static double getDistance(final BlockPos pos) {
        return PlayerUtils.mc.player.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }
    
    public static double getDistance(final EntityPlayer pos) {
        return PlayerUtils.mc.player.getDistance((Entity)pos);
    }
    
    public static EntityPlayer getLookingPlayer(final double range) {
        final List<EntityPlayer> players = new ArrayList<EntityPlayer>(PlayerUtils.mc.world.playerEntities);
        for (int i = 0; i < players.size(); ++i) {
            if (getDistance(players.get(i)) > range) {
                players.remove(i);
            }
        }
        players.remove(PlayerUtils.mc.player);
        EntityPlayer target = null;
        final Vec3d positionEyes = PlayerUtils.mc.player.getPositionEyes(PlayerUtils.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = PlayerUtils.mc.player.getLook(PlayerUtils.mc.getRenderPartialTicks());
        final int precision = 2;
        for (int j = 0; j < (int)range; ++j) {
            for (int k = precision; k > 0; --k) {
                for (final EntityPlayer targetTemp : players) {
                    final AxisAlignedBB playerBox = targetTemp.getEntityBoundingBox();
                    final double xArray = positionEyes.x + rotationEyes.x * j + rotationEyes.x / k;
                    final double yArray = positionEyes.y + rotationEyes.y * j + rotationEyes.y / k;
                    final double zArray = positionEyes.z + rotationEyes.z * j + rotationEyes.z / k;
                    if (playerBox.maxY >= yArray && playerBox.minY <= yArray && playerBox.maxX >= xArray && playerBox.minX <= xArray && playerBox.maxZ >= zArray && playerBox.minZ <= zArray) {
                        target = targetTemp;
                    }
                }
            }
        }
        return target;
    }
    
    public static EntityPlayer getNearestPlayer(final double range) {
        return (EntityPlayer)PlayerUtils.mc.world.playerEntities.stream().filter(p -> PlayerUtils.mc.player.getDistance(p) <= range).filter(p -> PlayerUtils.mc.player.getEntityId() != p.getEntityId()).min(Comparator.comparing(p -> PlayerUtils.mc.player.getDistance(p))).orElse(null);
    }
    
    public static double[] directionSpeed(final double speed) {
        float forward = PlayerUtils.mc.player.movementInput.moveForward;
        float side = PlayerUtils.mc.player.movementInput.moveStrafe;
        float yaw = PlayerUtils.mc.player.prevRotationYaw + (PlayerUtils.mc.player.rotationYaw - PlayerUtils.mc.player.prevRotationYaw) * PlayerUtils.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static boolean isPlayerAboveVoid() {
        boolean aboveVoid = false;
        if (PlayerUtils.mc.player.posY <= 0.0) {
            return true;
        }
        for (int i = 1; i < PlayerUtils.mc.player.posY; ++i) {
            final BlockPos pos = new BlockPos(PlayerUtils.mc.player.posX, (double)i, PlayerUtils.mc.player.posZ);
            if (!(PlayerUtils.mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
                aboveVoid = false;
                break;
            }
            aboveVoid = true;
        }
        return aboveVoid;
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static boolean isKeyDown(final int i) {
        return i != 0 && i < 256 && ((i < 0) ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i));
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(PlayerUtils.mc.player.posX), Math.floor(PlayerUtils.mc.player.posY), Math.floor(PlayerUtils.mc.player.posZ));
    }
    
    public static void centerPlayer(final Vec3d centeredBlock) {
        final double xDeviation = Math.abs(centeredBlock.x - PlayerUtils.mc.player.posX);
        final double zDeviation = Math.abs(centeredBlock.z - PlayerUtils.mc.player.posZ);
        if (xDeviation <= 0.1 && zDeviation <= 0.1) {
            double newX = -2.0;
            double newZ = -2.0;
            final int xRel = (PlayerUtils.mc.player.posX < 0.0) ? -1 : 1;
            final int zRel = (PlayerUtils.mc.player.posZ < 0.0) ? -1 : 1;
            if (BlockUtils.getBlock(PlayerUtils.mc.player.posX, PlayerUtils.mc.player.posY - 1.0, PlayerUtils.mc.player.posZ) instanceof BlockAir) {
                if (Math.abs(PlayerUtils.mc.player.posX % 1.0) * 100.0 <= 30.0) {
                    newX = Math.round(PlayerUtils.mc.player.posX - 0.3 * xRel) + 0.5 * -xRel;
                }
                else if (Math.abs(PlayerUtils.mc.player.posX % 1.0) * 100.0 >= 70.0) {
                    newX = Math.round(PlayerUtils.mc.player.posX + 0.3 * xRel) - 0.5 * -xRel;
                }
                if (Math.abs(PlayerUtils.mc.player.posZ % 1.0) * 100.0 <= 30.0) {
                    newZ = Math.round(PlayerUtils.mc.player.posZ - 0.3 * zRel) + 0.5 * -zRel;
                }
                else if (Math.abs(PlayerUtils.mc.player.posZ % 1.0) * 100.0 >= 70.0) {
                    newZ = Math.round(PlayerUtils.mc.player.posZ + 0.3 * zRel) - 0.5 * -zRel;
                }
            }
            if (newX == -2.0) {
                if (PlayerUtils.mc.player.posX > Math.round(PlayerUtils.mc.player.posX)) {
                    newX = Math.round(PlayerUtils.mc.player.posX) + 0.5;
                }
                else if (PlayerUtils.mc.player.posX < Math.round(PlayerUtils.mc.player.posX)) {
                    newX = Math.round(PlayerUtils.mc.player.posX) - 0.5;
                }
                else {
                    newX = PlayerUtils.mc.player.posX;
                }
            }
            if (newZ == -2.0) {
                if (PlayerUtils.mc.player.posZ > Math.round(PlayerUtils.mc.player.posZ)) {
                    newZ = Math.round(PlayerUtils.mc.player.posZ) + 0.5;
                }
                else if (PlayerUtils.mc.player.posZ < Math.round(PlayerUtils.mc.player.posZ)) {
                    newZ = Math.round(PlayerUtils.mc.player.posZ) - 0.5;
                }
                else {
                    newZ = PlayerUtils.mc.player.posZ;
                }
            }
            PlayerUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(newX, PlayerUtils.mc.player.posY, newZ, true));
            PlayerUtils.mc.player.setPosition(newX, PlayerUtils.mc.player.posY, newZ);
        }
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
