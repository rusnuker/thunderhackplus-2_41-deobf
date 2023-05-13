//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.block.state.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Spider extends Module
{
    public final Setting<Integer> delay;
    public Setting<Boolean> dropBlocks;
    private final Setting<mode> a;
    
    public Spider() {
        super("Spider", "Spider", Module.Category.MOVEMENT);
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)2, (T)1, (T)15));
        this.dropBlocks = (Setting<Boolean>)this.register(new Setting("DropBlocks", (T)false));
        this.a = (Setting<mode>)this.register(new Setting("Mode", (T)mode.Matrix));
    }
    
    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (!Spider.mc.world.isAirBlock(neighbour)) {
                final IBlockState blockState = Spider.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    public void onTick() {
        if (!Spider.mc.player.collidedHorizontally) {
            return;
        }
        if (this.a.getValue() == mode.Default) {
            Spider.mc.player.motionY = 0.2;
            Spider.mc.player.isAirBorne = false;
        }
        else if (this.a.getValue() == mode.Matrix) {
            if (Spider.mc.player.ticksExisted % this.delay.getValue() == 0) {
                Spider.mc.player.onGround = true;
                Spider.mc.player.isAirBorne = false;
            }
            else {
                Spider.mc.player.onGround = false;
            }
            final EntityPlayerSP player = Spider.mc.player;
            player.prevPosY -= 2.0E-232;
            if (Spider.mc.player.onGround) {
                Spider.mc.player.motionY = 0.41999998688697815;
            }
        }
    }
    
    @SubscribeEvent
    public void onMotion(final EventSync event) {
        if (Spider.mc.gameSettings.keyBindJump.isKeyDown() && Spider.mc.player.motionY <= -0.3739040364667221 && this.a.getValue() == mode.MatrixNew) {
            Spider.mc.player.onGround = true;
            Spider.mc.player.motionY = 0.48114514191918;
        }
        if (Spider.mc.player.ticksExisted % this.delay.getValue() == 0 && Spider.mc.player.collidedHorizontally && MovementUtil.isMoving() && this.a.getValue() == mode.Blocks) {
            int find = -2;
            for (int i = 0; i <= 8; ++i) {
                if (Spider.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                    find = i;
                }
            }
            if (find == -2) {
                return;
            }
            final BlockPos pos = new BlockPos(Spider.mc.player.posX, Spider.mc.player.posY + 2.0, Spider.mc.player.posZ);
            final EnumFacing side = getPlaceableSide(pos);
            if (side != null) {
                Spider.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(find));
                final BlockPos neighbour = new BlockPos(Spider.mc.player.posX, Spider.mc.player.posY + 2.0, Spider.mc.player.posZ).offset(side);
                final EnumFacing opposite = side.getOpposite();
                final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                final float x = (float)(hitVec.x - neighbour.getX());
                final float y = (float)(hitVec.y - neighbour.getY());
                final float z = (float)(hitVec.z - neighbour.getZ());
                Spider.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(neighbour, opposite, EnumHand.MAIN_HAND, x, y, z));
                Spider.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Spider.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                if (Spider.mc.world.getBlockState(new BlockPos((Entity)Spider.mc.player).add(0, 2, 0)).getBlock() != Blocks.AIR) {
                    Spider.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
                    Spider.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, neighbour, opposite));
                }
                Spider.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Spider.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            Spider.mc.player.onGround = true;
            Spider.mc.player.isAirBorne = true;
            Spider.mc.player.jump();
            Spider.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Spider.mc.player.inventory.currentItem));
            if (this.dropBlocks.getValue()) {
                for (int j = 9; j < 45; ++j) {
                    if (Spider.mc.player.inventoryContainer.getSlot(j).getHasStack() && Spider.mc.player.inventoryContainer.getSlot(j).getStack().getItem() instanceof ItemBlock) {
                        Spider.mc.playerController.windowClick(Spider.mc.player.inventoryContainer.windowId, j, 0, ClickType.THROW, (EntityPlayer)Spider.mc.player);
                        Spider.mc.player.jump();
                        break;
                    }
                }
            }
        }
    }
    
    public enum mode
    {
        Default, 
        Matrix, 
        MatrixNew, 
        Blocks;
    }
}
