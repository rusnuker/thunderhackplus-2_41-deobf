//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.*;

public class HoleFiller extends Module
{
    private final Setting<Float> rangeXZ;
    private final Setting<Integer> predictTicks;
    private final List<BlockPos> Holes;
    private final Timer notification_timer;
    EntityPlayer target;
    BlockPos targetPosition;
    
    public HoleFiller() {
        super("HoleFiller", "HoleFiller", "HoleFiller", Category.COMBAT);
        this.rangeXZ = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)1.0f, (T)7.0f));
        this.predictTicks = (Setting<Integer>)this.register(new Setting("PredictTicks", (T)3, (T)0, (T)25));
        this.Holes = new ArrayList<BlockPos>();
        this.notification_timer = new Timer();
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync e) {
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleFiller.mc.player.getPosition().add((double)(-this.rangeXZ.getValue()), (double)(-this.rangeXZ.getValue()), (double)(-this.rangeXZ.getValue())), HoleFiller.mc.player.getPosition().add((double)this.rangeXZ.getValue(), (double)this.rangeXZ.getValue(), (double)this.rangeXZ.getValue()));
        this.Holes.clear();
        for (final BlockPos pos : blocks) {
            if ((!HoleFiller.mc.world.getBlockState(pos).getMaterial().blocksMovement() || !HoleFiller.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement() || !HoleFiller.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial().blocksMovement()) && this.checkHole(pos)) {
                this.Holes.add(pos);
            }
        }
        if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).isEnabled() && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).getTarget() != null) {
            this.target = ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).getTarget();
        }
        else {
            this.target = this.findTarget();
        }
        if (this.target == null) {
            return;
        }
        final double predict_x = (this.target.posX - this.target.prevPosX) * this.predictTicks.getValue();
        final double predict_z = (this.target.posZ - this.target.prevPosZ) * this.predictTicks.getValue();
        final BlockPos predict_pos = new BlockPos(this.target.posX + predict_x, this.target.posY, this.target.posZ + predict_z);
        for (final BlockPos bp : this.Holes) {
            if (this.target.getDistanceSq(bp) < 4.0) {
                this.fixHolePre(bp);
            }
            else {
                if (predict_pos.distanceSq((double)bp.getX(), (double)bp.getY(), (double)bp.getZ()) >= 4.0) {
                    continue;
                }
                this.fixHolePre(bp);
            }
        }
    }
    
    public void fixHolePre(final BlockPos bp) {
        final float[] angle = SilentRotationUtil.calcAngle(HoleFiller.mc.player.getPositionEyes(HoleFiller.mc.getRenderPartialTicks()), new Vec3d((Vec3i)bp.down().add(0.5, 1.0, 0.5)));
        HoleFiller.mc.player.rotationYaw = angle[0];
        HoleFiller.mc.player.rotationPitch = angle[1];
        this.targetPosition = bp;
    }
    
    @SubscribeEvent
    public void postEntitySync(final EventPostSync e) {
        if (this.targetPosition != null) {
            final int obby_slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
            if (obby_slot == -1) {
                Command.sendMessage("no obby");
                this.targetPosition = null;
                this.toggle();
                return;
            }
            HoleFiller.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obby_slot));
            InteractionUtil.placeBlock(this.targetPosition, true);
            if (this.notification_timer.passedMs(200L) && ((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isOn()) {
                NotificationManager.publicity("HoleFiller " + ChatFormatting.GREEN + "hole X" + this.targetPosition.getX() + " Y" + this.targetPosition.getY() + " Z" + this.targetPosition.getZ() + " is successfully blocked", 2, Notification.Type.SUCCESS);
                this.notification_timer.reset();
            }
            this.targetPosition = null;
            HoleFiller.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(HoleFiller.mc.player.inventory.currentItem));
        }
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        double distance = this.rangeXZ.getPow2Value();
        for (final EntityPlayer entity : HoleFiller.mc.world.playerEntities) {
            if (entity == HoleFiller.mc.player) {
                continue;
            }
            if (Thunderhack.friendManager.isFriend(entity)) {
                continue;
            }
            if (HoleFiller.mc.player.getDistanceSq((Entity)entity) > distance) {
                continue;
            }
            target = entity;
            distance = HoleFiller.mc.player.getDistanceSq((Entity)entity);
        }
        return target;
    }
    
    public boolean isOccupied(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        for (final Entity entity : HoleFiller.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
            if (entity instanceof EntityPlayer) {
                return true;
            }
        }
        for (final Entity entity : HoleFiller.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
            if (entity instanceof EntityEnderCrystal) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkHole(final BlockPos pos) {
        return (BlockUtils.validObi(pos) || BlockUtils.validBedrock(pos)) && !this.isOccupied(pos);
    }
}
