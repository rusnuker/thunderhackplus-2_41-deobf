//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.math.*;

public class AutoExplosion extends Module
{
    public static EntityPlayer trgt;
    public Setting<Boolean> packetplace;
    public Setting<Integer> stophp;
    public Setting<Integer> delay;
    public Setting<Integer> placedelay;
    public Setting<Integer> maxself;
    public Setting<SubBind> bindButton;
    int ticksNoOnGround;
    BlockPos CoolPosition;
    Timer placeDelay;
    Timer breakDelay;
    int extraTicks;
    private final Setting<Mode> mode;
    private final Setting<TargetMode> targetMode;
    public Setting<Boolean> offAura;
    private BlockPos crysToExplosion;
    
    public AutoExplosion() {
        super("AutoExplosion", "\u0431\u043e\u043b\u0435\u0435 \u0442\u0443\u043f\u0430\u044f \u043a\u0440\u0438\u0441\u0442\u0430\u043b\u043a\u0430-\u0434\u043b\u044f \u043a\u0440\u0438\u043d\u0436 \u0441\u0435\u0440\u0432\u0435\u0440\u043e\u0432", "don't use-this shit", Category.COMBAT);
        this.packetplace = (Setting<Boolean>)this.register(new Setting("packetplace", (T)true));
        this.stophp = (Setting<Integer>)this.register(new Setting("stophp", (T)8, (T)1, (T)20));
        this.delay = (Setting<Integer>)this.register(new Setting("TicksExisted", (T)8, (T)1, (T)20));
        this.placedelay = (Setting<Integer>)this.register(new Setting("PlaceDelay", (T)8, (T)1, (T)1000));
        this.maxself = (Setting<Integer>)this.register(new Setting("maxself", (T)10, (T)1, (T)20));
        this.bindButton = (Setting<SubBind>)this.register(new Setting("BindButton", (T)new SubBind(42)));
        this.ticksNoOnGround = 0;
        this.placeDelay = new Timer();
        this.breakDelay = new Timer();
        this.extraTicks = 5;
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FullAuto));
        this.targetMode = (Setting<TargetMode>)this.register(new Setting("Target", (T)TargetMode.Aura));
        this.offAura = (Setting<Boolean>)this.register(new Setting("offAura", (T)true, v -> this.targetMode.getValue() == TargetMode.AutoExplosion));
    }
    
    @SubscribeEvent
    public void onPlayerPre(final EventSync e) {
        if (this.targetMode.getValue() == TargetMode.Aura) {
            this.offAura.setValue(false);
        }
        if (AutoExplosion.mc.player.getHealth() < this.stophp.getValue()) {
            return;
        }
        if (this.mode.getValue() == Mode.FullAuto) {
            this.FullAuto(e);
        }
        else if (this.mode.getValue() == Mode.Semi) {
            this.Semi(e);
        }
        else if (this.mode.getValue() == Mode.Bind && PlayerUtils.isKeyDown(this.bindButton.getValue().getKey())) {
            this.onBind(e);
        }
    }
    
    public void Semi(final EventSync e) {
        if (Mouse.isButtonDown(1)) {
            if (this.offAura.getValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled()) {
                ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
            }
            final RayTraceResult ray = AutoExplosion.mc.player.rayTrace(4.5, AutoExplosion.mc.getRenderPartialTicks());
            BlockPos pos = null;
            if (ray != null) {
                pos = ray.getBlockPos();
            }
            if (pos != null && AutoExplosion.mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN) {
                final int crysslot = InventoryUtil.getCrysathotbar();
                if (crysslot == -1) {
                    return;
                }
                final int oldSlot = AutoExplosion.mc.player.inventory.currentItem;
                AutoExplosion.mc.player.inventory.currentItem = InventoryUtil.getCrysathotbar();
                AutoExplosion.mc.playerController.processRightClickBlock(AutoExplosion.mc.player, AutoExplosion.mc.world, pos, EnumFacing.UP, new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), EnumHand.MAIN_HAND);
                AutoExplosion.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoExplosion.mc.player.inventory.currentItem = oldSlot;
                this.crysToExplosion = pos;
                this.extraTicks = 5;
            }
        }
        else if (this.crysToExplosion != null) {
            final EntityEnderCrystal ourCrys = this.getCrystal(this.crysToExplosion);
            if (ourCrys != null) {
                if (ourCrys.ticksExisted > this.delay.getValue() && this.breakDelay.passedMs(156L) && CrystalUtils.calculateDamage(ourCrys, (Entity)AutoExplosion.mc.player) < this.maxself.getValue()) {
                    AutoExplosion.mc.player.setSprinting(false);
                    final float[] angle = RotationUtil.calcAngle(AutoExplosion.mc.player.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()), ourCrys.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()));
                    AutoExplosion.mc.player.rotationYaw = angle[0];
                    AutoExplosion.mc.player.rotationPitch = angle[1];
                    AutoExplosion.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)ourCrys));
                    AutoExplosion.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.breakDelay.reset();
                }
            }
            else {
                --this.extraTicks;
                if (this.extraTicks <= 0) {
                    this.crysToExplosion = null;
                    this.extraTicks = 10;
                }
            }
        }
    }
    
    public void FullAuto(final EventSync e) {
        for (final Entity ent : AutoExplosion.mc.world.loadedEntityList) {
            if (ent instanceof EntityEnderCrystal && AutoExplosion.mc.player.getDistance(ent) < 5.0f && ent.ticksExisted >= this.delay.getValue() && this.breakDelay.passedMs(156L) && CrystalUtils.calculateDamage((EntityEnderCrystal)ent, (Entity)AutoExplosion.mc.player) < this.maxself.getValue()) {
                if (this.offAura.getValue()) {
                    ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
                }
                AutoExplosion.mc.player.setSprinting(false);
                final float[] angle = RotationUtil.calcAngle(AutoExplosion.mc.player.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()), ent.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()));
                AutoExplosion.mc.player.rotationYaw = angle[0];
                AutoExplosion.mc.player.rotationPitch = angle[1];
                AutoExplosion.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(ent));
                AutoExplosion.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.breakDelay.reset();
            }
        }
        if (this.targetMode.getValue() == TargetMode.Aura) {
            if (Aura.target == null) {
                AutoExplosion.trgt = null;
                return;
            }
            if (Aura.target instanceof EntityPlayer) {
                AutoExplosion.trgt = (EntityPlayer)Aura.target;
                if (!AutoExplosion.trgt.onGround) {
                    ++this.ticksNoOnGround;
                }
                else {
                    this.ticksNoOnGround = 0;
                }
            }
        }
        else {
            for (final EntityPlayer ent2 : AutoExplosion.mc.world.playerEntities) {
                if (AutoExplosion.mc.player.getDistanceSq((Entity)ent2) < 36.0 && !Thunderhack.friendManager.isFriend(ent2)) {
                    AutoExplosion.trgt = ent2;
                }
            }
            if (AutoExplosion.trgt == null) {
                return;
            }
        }
        if (this.getPosition((EntityPlayer)AutoExplosion.mc.player) != null && AutoExplosion.mc.player.posY + 0.2280000001192093 < AutoExplosion.trgt.posY) {
            this.CoolPosition = this.getPosition((EntityPlayer)AutoExplosion.mc.player);
            if (AutoExplosion.mc.world.getBlockState(this.CoolPosition).getBlock() == Blocks.OBSIDIAN) {
                if (this.getCrystal(this.CoolPosition) != null) {
                    return;
                }
                if (!this.placeDelay.passedMs(this.placedelay.getValue())) {
                    return;
                }
                final int crysslot = InventoryUtil.getCrysathotbar();
                if (crysslot == -1) {
                    return;
                }
                if (this.offAura.getValue()) {
                    ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
                }
                InventoryUtil.switchToHotbarSlot(InventoryUtil.getCrysathotbar(), false);
                BlockUtils.placeBlockSmartRotate(this.CoolPosition.add(0, 1, 0), EnumHand.MAIN_HAND, true, this.packetplace.getValue(), AutoExplosion.mc.player.isSneaking(), e);
                this.placeDelay.reset();
            }
            else {
                if (this.offAura.getValue()) {
                    ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
                }
                final int obbyslot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
                if (obbyslot == -1) {
                    return;
                }
                InventoryUtil.switchToHotbarSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class), false);
                BlockUtils.placeBlockSmartRotate(this.CoolPosition, EnumHand.MAIN_HAND, true, this.packetplace.getValue(), AutoExplosion.mc.player.isSneaking(), e);
            }
        }
    }
    
    public void onBind(final EventSync e) {
        for (final Entity ent : AutoExplosion.mc.world.loadedEntityList) {
            if (ent instanceof EntityEnderCrystal && AutoExplosion.mc.player.getDistance(ent) < 5.0f && ent.ticksExisted >= this.delay.getValue() && this.breakDelay.passedMs(156L) && CrystalUtils.calculateDamage((EntityEnderCrystal)ent, (Entity)AutoExplosion.mc.player) < this.maxself.getValue()) {
                AutoExplosion.mc.player.setSprinting(false);
                final float[] angle = RotationUtil.calcAngle(AutoExplosion.mc.player.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()), ent.getPositionEyes(AutoExplosion.mc.getRenderPartialTicks()));
                AutoExplosion.mc.player.rotationYaw = angle[0];
                AutoExplosion.mc.player.rotationPitch = angle[1];
                AutoExplosion.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(ent));
                AutoExplosion.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.breakDelay.reset();
            }
        }
        if (this.targetMode.getValue() == TargetMode.Aura) {
            if (Aura.target == null) {
                AutoExplosion.trgt = null;
                return;
            }
            if (Aura.target instanceof EntityPlayer) {
                AutoExplosion.trgt = (EntityPlayer)Aura.target;
            }
        }
        else {
            for (final EntityPlayer ent2 : AutoExplosion.mc.world.playerEntities) {
                if (AutoExplosion.mc.player.getDistanceSq((Entity)ent2) < 36.0 && !Thunderhack.friendManager.isFriend(ent2)) {
                    AutoExplosion.trgt = ent2;
                }
            }
            if (AutoExplosion.trgt == null) {
                return;
            }
        }
        if (this.getPosition((EntityPlayer)AutoExplosion.mc.player) != null) {
            this.CoolPosition = this.getPosition((EntityPlayer)AutoExplosion.mc.player);
            if (AutoExplosion.mc.world.getBlockState(this.CoolPosition).getBlock() == Blocks.OBSIDIAN) {
                if (this.getCrystal(this.CoolPosition) != null) {
                    return;
                }
                if (!this.placeDelay.passedMs(this.placedelay.getValue())) {
                    return;
                }
                final int crysslot = InventoryUtil.getCrysathotbar();
                if (crysslot == -1) {
                    return;
                }
                if (this.offAura.getValue()) {
                    ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
                }
                InventoryUtil.switchToHotbarSlot(InventoryUtil.getCrysathotbar(), false);
                BlockUtils.placeBlockSmartRotate(this.CoolPosition.add(0, 1, 0), EnumHand.MAIN_HAND, true, this.packetplace.getValue(), AutoExplosion.mc.player.isSneaking(), e);
                this.placeDelay.reset();
            }
            else {
                if (this.offAura.getValue()) {
                    ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).disable();
                }
                final int obbyslot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
                if (obbyslot == -1) {
                    return;
                }
                InventoryUtil.switchToHotbarSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class), false);
                BlockUtils.placeBlockSmartRotate(this.CoolPosition, EnumHand.MAIN_HAND, true, this.packetplace.getValue(), AutoExplosion.mc.player.isSneaking(), e);
            }
        }
    }
    
    public boolean canPlace(final BlockPos bp) {
        return AutoExplosion.mc.world.getBlockState(bp.add(0, 1, 0)).getBlock() == Blocks.AIR && AutoExplosion.mc.world.getBlockState(bp.add(0, 2, 0)).getBlock() == Blocks.AIR && (AutoExplosion.mc.world.getBlockState(bp).getBlock() == Blocks.AIR || AutoExplosion.mc.world.getBlockState(bp).getBlock() == Blocks.OBSIDIAN);
    }
    
    private BlockPos getPosition(final EntityPlayer entity2) {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        final int playerX = (int)entity2.posX;
        final int playerZ = (int)entity2.posZ;
        final int n4 = 4;
        final double playerX2 = entity2.posX - 0.5;
        final double playerY1 = entity2.posY + entity2.getEyeHeight() - 1.0;
        final double playerZ2 = entity2.posZ - 0.5;
        for (int n5 = playerX - n4; n5 <= playerX + n4; ++n5) {
            for (int n6 = playerZ - n4; n6 <= playerZ + n4; ++n6) {
                if ((n5 - playerX2) * (n5 - playerX2) + (AutoExplosion.mc.player.posY - playerY1) * (AutoExplosion.mc.player.posY - playerY1) + (n6 - playerZ2) * (n6 - playerZ2) <= 25.0 && this.canPlace(new BlockPos((double)n5, AutoExplosion.mc.player.posY, (double)n6))) {
                    if (AutoExplosion.mc.world.getBlockState(new BlockPos((double)n5, AutoExplosion.mc.player.posY, (double)n6)).getBlock() == Blocks.OBSIDIAN && AutoExplosion.trgt.getDistanceSqToCenter(new BlockPos((double)n5, AutoExplosion.mc.player.posY, (double)n6)) < 16.0) {
                        return new BlockPos((double)n5, AutoExplosion.mc.player.posY, (double)n6);
                    }
                    arrayList.add(new BlockPos((double)n5, AutoExplosion.mc.player.posY, (double)n6));
                }
            }
        }
        return this.AI(arrayList);
    }
    
    public EntityEnderCrystal getCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        for (final Entity ent : AutoExplosion.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost, boost2.add(1, 1, 1)))) {
            if (ent instanceof EntityEnderCrystal) {
                return (EntityEnderCrystal)ent;
            }
        }
        return null;
    }
    
    public BlockPos AI(final ArrayList<BlockPos> blocks) {
        BlockPos pos = null;
        double bestdist = 5.0;
        if (AutoExplosion.trgt == null) {
            return null;
        }
        for (final BlockPos pos2 : blocks) {
            if (pos2.getDistance((int)AutoExplosion.trgt.posX, (int)AutoExplosion.trgt.posY, (int)AutoExplosion.trgt.posZ) > 2.0 && AutoExplosion.trgt.getDistanceSqToCenter(pos2) < bestdist) {
                bestdist = AutoExplosion.trgt.getDistanceSqToCenter(pos2);
                pos = pos2;
            }
        }
        return pos;
    }
    
    public enum Mode
    {
        FullAuto, 
        Semi, 
        Bind;
    }
    
    public enum TargetMode
    {
        Aura, 
        AutoExplosion;
    }
}
