//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.surround.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class RusherScaffold extends Module
{
    public final Setting<ColorSetting> Color2;
    private final Setting<Float> lineWidth;
    public Setting<Boolean> rotate;
    public Setting<Boolean> allowShift;
    public Setting<Boolean> autoswap;
    public Setting<Boolean> tower;
    public Setting<Boolean> safewalk;
    public Setting<Boolean> echestholding;
    public Setting<Boolean> render;
    private final Timer timer;
    private BlockPosWithFacing currentblock;
    
    public RusherScaffold() {
        super("Scaffold", "\u043b\u0443\u0447\u0448\u0438\u0439 \u0441\u043a\u0430\u0444\u0444", Module.Category.PLAYER);
        this.Color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.allowShift = (Setting<Boolean>)this.register(new Setting("AllowShift", (T)false));
        this.autoswap = (Setting<Boolean>)this.register(new Setting("AutoSwap", (T)true));
        this.tower = (Setting<Boolean>)this.register(new Setting("Tower", (T)true));
        this.safewalk = (Setting<Boolean>)this.register(new Setting("SafeWalk", (T)true));
        this.echestholding = (Setting<Boolean>)this.register(new Setting("EchestHolding", (T)false));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.timer = new Timer();
    }
    
    private boolean isBlockValid(final Block block) {
        return block.getDefaultState().getMaterial().isSolid();
    }
    
    private BlockPosWithFacing checkNearBlocks(final BlockPos blockPos) {
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private int findBlockToPlace() {
        if (RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
            return RusherScaffold.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            if (RusherScaffold.mc.player.inventory.getStackInSlot(i).getCount() != 0 && RusherScaffold.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && (!this.echestholding.getValue() || (this.echestholding.getValue() && !RusherScaffold.mc.player.inventory.getStackInSlot(i).getItem().equals(Item.getItemFromBlock(Blocks.ENDER_CHEST)))) && this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.inventory.getStackInSlot(i).getItem()).getBlock())) {
                return i;
            }
        }
        return -1;
    }
    
    private BlockPosWithFacing checkNearBlocksExtended(final BlockPos blockPos) {
        BlockPosWithFacing ret = null;
        ret = this.checkNearBlocks(blockPos);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, -1, 0));
        final BlockPos blockPos2 = blockPos.add(0, -1, 0);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        return this.checkNearBlocks(blockPos2.add(0, 0, -1));
    }
    
    private int countValidBlocks() {
        int n = 36;
        int n2 = 0;
        while (n < 45) {
            if (RusherScaffold.mc.player.inventoryContainer.getSlot(n).getHasStack()) {
                final ItemStack itemStack = RusherScaffold.mc.player.inventoryContainer.getSlot(n).getStack();
                if (itemStack.getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)itemStack.getItem()).getBlock())) {
                    n2 += itemStack.getCount();
                }
            }
            ++n;
        }
        return n2;
    }
    
    private Vec3d getEyePosition() {
        return new Vec3d(RusherScaffold.mc.player.posX, RusherScaffold.mc.player.posY + RusherScaffold.mc.player.getEyeHeight(), RusherScaffold.mc.player.posZ);
    }
    
    private float[] getRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, RusherScaffold.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)RusherScaffold.mc.world, blockPos).maxY - 0.01, blockPos.getZ() + 0.5);
        vec3d = vec3d.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
        final Vec3d vec3d2 = this.getEyePosition();
        final double d = vec3d.x - vec3d2.x;
        final double d2 = vec3d.y - vec3d2.y;
        final double d3 = vec3d.z - vec3d2.z;
        final double d4 = Math.sqrt(d * d + d3 * d3);
        final float f = (float)(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        final float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        final float[] ret = { RusherScaffold.mc.player.rotationYaw + MathHelper.wrapDegrees(f - RusherScaffold.mc.player.rotationYaw), RusherScaffold.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - RusherScaffold.mc.player.rotationPitch) };
        return ret;
    }
    
    private void doSafeWalk(final EventMove event) {
        double x = event.get_x();
        final double y = event.get_y();
        double z = event.get_z();
        if (RusherScaffold.mc.player.onGround && !RusherScaffold.mc.player.noClip) {
            final double increment = 0.05;
            while (x != 0.0 && this.isOffsetBBEmpty(x, 0.0)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
            }
            while (z != 0.0 && this.isOffsetBBEmpty(0.0, z)) {
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
            while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, z)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
        }
        event.set_x(x);
        event.set_y(y);
        event.set_z(z);
        event.setCanceled(true);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onMove(final EventMove event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.safewalk.getValue()) {
            this.doSafeWalk(event);
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.currentblock != null) {
            GlStateManager.pushMatrix();
            RenderUtil.drawBlockOutline(this.currentblock.getPosition(), this.Color2.getValue().getColorObject(), this.lineWidth.getValue(), false, 0);
            GlStateManager.popMatrix();
        }
    }
    
    private boolean isOffsetBBEmpty(final double x, final double z) {
        return RusherScaffold.mc.world.getCollisionBoxes((Entity)RusherScaffold.mc.player, RusherScaffold.mc.player.getEntityBoundingBox().offset(x, -2.0, z)).isEmpty();
    }
    
    @SubscribeEvent
    public void onPre(final EventSync event) {
        if (this.countValidBlocks() <= 0) {
            this.currentblock = null;
            return;
        }
        if (RusherScaffold.mc.player.posY < 257.0) {
            this.currentblock = null;
            if (RusherScaffold.mc.player.isSneaking() && !this.allowShift.getValue()) {
                return;
            }
            final int n2 = this.findBlockToPlace();
            if (n2 == -1) {
                return;
            }
            final Item item = RusherScaffold.mc.player.inventory.getStackInSlot(n2).getItem();
            if (!(item instanceof ItemBlock)) {
                return;
            }
            final Block block = ((ItemBlock)item).getBlock();
            final boolean fullBlock = block.getDefaultState().isFullBlock();
            final BlockPos blockPos2 = new BlockPos(RusherScaffold.mc.player.posX, RusherScaffold.mc.player.posY - (fullBlock ? 1.0 : 0.01), RusherScaffold.mc.player.posZ);
            if (!RusherScaffold.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                return;
            }
            this.currentblock = this.checkNearBlocksExtended(blockPos2);
            if (this.currentblock != null && this.rotate.getValue()) {
                final float[] rotations = this.getRotations(this.currentblock.getPosition(), this.currentblock.getFacing());
                RusherScaffold.mc.player.rotationYaw = rotations[0];
                RusherScaffold.mc.player.renderYawOffset = rotations[0];
                RusherScaffold.mc.player.rotationPitch = rotations[1];
            }
        }
    }
    
    @SubscribeEvent
    public void onPost(final EventPostSync e) {
        if (this.currentblock == null) {
            return;
        }
        final int prev_item = RusherScaffold.mc.player.inventory.currentItem;
        if (!(RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) && this.autoswap.getValue()) {
            final int blockSlot = this.findBlockToPlace();
            if (blockSlot != -1) {
                RusherScaffold.mc.player.inventory.currentItem = blockSlot;
                RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(blockSlot));
            }
        }
        if (RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
            if (!RusherScaffold.mc.player.movementInput.jump || RusherScaffold.mc.player.moveForward != 0.0f || RusherScaffold.mc.player.moveStrafing != 0.0f || !this.tower.getValue()) {
                this.timer.reset();
            }
            else {
                RusherScaffold.mc.player.setVelocity(0.0, 0.42, 0.0);
                if (this.timer.passedMs(1500L)) {
                    RusherScaffold.mc.player.motionY = -0.28;
                    this.timer.reset();
                }
            }
            final boolean sneak = RusherScaffold.mc.world.getBlockState(this.currentblock.getPosition()).getBlock().onBlockActivated((World)RusherScaffold.mc.world, this.currentblock.getPosition(), RusherScaffold.mc.world.getBlockState(this.currentblock.getPosition()), (EntityPlayer)RusherScaffold.mc.player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
            if (sneak) {
                RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RusherScaffold.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            RusherScaffold.mc.playerController.processRightClickBlock(RusherScaffold.mc.player, RusherScaffold.mc.world, this.currentblock.getPosition(), this.currentblock.getFacing(), new Vec3d(this.currentblock.getPosition().getX() + Math.random(), RusherScaffold.mc.world.getBlockState(this.currentblock.getPosition()).getSelectedBoundingBox((World)RusherScaffold.mc.world, this.currentblock.getPosition()).maxY - 0.01, this.currentblock.getPosition().getZ() + Math.random()), EnumHand.MAIN_HAND);
            RusherScaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (sneak) {
                RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RusherScaffold.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            RusherScaffold.mc.player.inventory.currentItem = prev_item;
            RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(RusherScaffold.mc.player.inventory.currentItem));
        }
    }
}
