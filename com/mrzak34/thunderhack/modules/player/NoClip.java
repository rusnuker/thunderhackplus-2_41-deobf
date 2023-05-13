//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.*;

public class NoClip extends Module
{
    public Setting<Mode> mode;
    private final Setting<Integer> timeout;
    public Setting<Boolean> silent;
    public int itemIndex;
    public Setting<Boolean> waitBreak;
    private Setting<Integer> afterBreak;
    public int clipTimer;
    
    public NoClip() {
        super("NoClip", "NoClip", Module.Category.PLAYER);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Default));
        this.timeout = (Setting<Integer>)this.register(new Setting("Timeout", (T)5, (T)1, (T)10, v -> this.mode.getValue() == Mode.CC));
        this.silent = (Setting<Boolean>)this.register(new Setting("Silent", (T)false, v -> this.mode.getValue() == Mode.SunriseBypass));
        this.waitBreak = (Setting<Boolean>)this.register(new Setting("WaitBreak", (T)true, v -> this.mode.getValue() == Mode.SunriseBypass));
        this.afterBreak = (Setting<Integer>)this.register(new Setting("BreakTimeout", (T)4, (T)1, (T)20, v -> this.mode.getValue() == Mode.SunriseBypass && this.waitBreak.getValue()));
    }
    
    public boolean playerInsideBlock() {
        return NoClip.mc.world.getBlockState(NoClip.mc.player.getPosition()).getBlock() != Blocks.AIR;
    }
    
    @SubscribeEvent
    public void onPreSync(final EventSync e) {
        if (this.mode.getValue() == Mode.SunriseBypass && (NoClip.mc.player.collidedHorizontally || this.playerInsideBlock()) && !NoClip.mc.player.isInWater() && !NoClip.mc.player.isInLava()) {
            final double[] dir = MovementUtil.forward(0.5);
            BlockPos blockToBreak = null;
            if (NoClip.mc.gameSettings.keyBindSneak.isKeyDown()) {
                blockToBreak = new BlockPos(NoClip.mc.player.posX + dir[0], NoClip.mc.player.posY - 1.0, NoClip.mc.player.posZ + dir[1]);
            }
            else if (MovementUtil.isMoving()) {
                blockToBreak = new BlockPos(NoClip.mc.player.posX + dir[0], NoClip.mc.player.posY, NoClip.mc.player.posZ + dir[1]);
            }
            if (blockToBreak == null) {
                return;
            }
            final int best_tool = this.getTool(blockToBreak);
            if (best_tool == -1) {
                return;
            }
            this.itemIndex = best_tool;
            if (this.silent.getValue()) {
                NoClip.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(best_tool));
            }
            else {
                NoClip.mc.player.inventory.currentItem = best_tool;
                InventoryUtil.syncItem();
            }
            if (blockToBreak != null) {
                NoClip.mc.playerController.onPlayerDamageBlock(blockToBreak, NoClip.mc.player.getHorizontalFacing());
                NoClip.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (this.silent.getValue()) {
                NoClip.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(NoClip.mc.player.inventory.currentItem));
            }
        }
    }
    
    public void onUpdate() {
        if (this.clipTimer > 0) {
            --this.clipTimer;
        }
        if (this.mode.getValue() == Mode.CC) {
            if (MovementUtil.isMoving()) {
                this.disable();
                return;
            }
            if (NoClip.mc.world.getCollisionBoxes((Entity)NoClip.mc.player, NoClip.mc.player.getEntityBoundingBox().grow(0.01, 0.0, 0.01)).size() < 2) {
                NoClip.mc.player.setPosition(MathUtil.roundToClosest(NoClip.mc.player.posX, Math.floor(NoClip.mc.player.posX) + 0.301, Math.floor(NoClip.mc.player.posX) + 0.699), NoClip.mc.player.posY, MathUtil.roundToClosest(NoClip.mc.player.posZ, Math.floor(NoClip.mc.player.posZ) + 0.301, Math.floor(NoClip.mc.player.posZ) + 0.699));
            }
            else if (NoClip.mc.player.ticksExisted % this.timeout.getValue() == 0) {
                NoClip.mc.player.setPosition(NoClip.mc.player.posX + MathHelper.clamp(MathUtil.roundToClosest(NoClip.mc.player.posX, Math.floor(NoClip.mc.player.posX) + 0.241, Math.floor(NoClip.mc.player.posX) + 0.759) - NoClip.mc.player.posX, -0.03, 0.03), NoClip.mc.player.posY, NoClip.mc.player.posZ + MathHelper.clamp(MathUtil.roundToClosest(NoClip.mc.player.posZ, Math.floor(NoClip.mc.player.posZ) + 0.241, Math.floor(NoClip.mc.player.posZ) + 0.759) - NoClip.mc.player.posZ, -0.03, 0.03));
                NoClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(NoClip.mc.player.posX, NoClip.mc.player.posY, NoClip.mc.player.posZ, true));
                NoClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(MathUtil.roundToClosest(NoClip.mc.player.posX, Math.floor(NoClip.mc.player.posX) + 0.23, Math.floor(NoClip.mc.player.posX) + 0.77), NoClip.mc.player.posY, MathUtil.roundToClosest(NoClip.mc.player.posZ, Math.floor(NoClip.mc.player.posZ) + 0.23, Math.floor(NoClip.mc.player.posZ) + 0.77), true));
            }
        }
    }
    
    public boolean canNoClip() {
        return this.mode.getValue() == Mode.Default || !this.waitBreak.getValue() || this.clipTimer != 0;
    }
    
    @SubscribeEvent
    public void onDestroyBlock(final DestroyBlockEvent e) {
        this.clipTimer = this.afterBreak.getValue();
    }
    
    private int getTool(final BlockPos pos) {
        int index = -1;
        float CurrentFastest = 1.0f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = NoClip.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                NoClip.mc.player.inventory.getStackInSlot(i).getMaxDamage();
                NoClip.mc.player.inventory.getStackInSlot(i).getItemDamage();
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(NoClip.mc.world.getBlockState(pos));
                if (NoClip.mc.world.getBlockState(pos).getBlock() instanceof BlockAir) {
                    return -1;
                }
                NoClip.mc.world.getBlockState(pos).getBlock();
                if (digSpeed + destroySpeed > CurrentFastest) {
                    CurrentFastest = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
    
    public enum Mode
    {
        Default, 
        SunriseBypass, 
        CC;
    }
}
