//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.util.render.*;
import java.awt.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class CevBreaker extends Module
{
    public static ConcurrentHashMap<BlockPos, Long> shiftedBlocks;
    public final Setting<ColorSetting> Color;
    public final Setting<ColorSetting> Color2;
    private final Setting<Integer> pickTickSwitch;
    private final Setting<Float> placeRange;
    public boolean startBreak;
    boolean broke;
    Timer renderTimer;
    private final Setting<Mode> mode;
    private final Setting<Integer> crysDelay;
    private final Setting<Integer> atttt;
    private final Setting<Integer> pausedelay;
    private final Setting<Integer> actionShift;
    private final Setting<Integer> actionInterval;
    private final Setting<Boolean> strict;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> p1;
    private final Setting<Boolean> MStrict;
    private final Setting<Boolean> strictdirection;
    private int tick;
    private int oldslot;
    private int wait;
    private BlockPos lastBlock;
    private BlockPos continueBlock;
    private boolean pickStillBol;
    private EnumFacing direction;
    private final Timer attackTimer;
    private final Timer cryTimer;
    private int itemSlot;
    private BlockPos renderPos;
    private int tickCounter;
    private BlockPos playerPos;
    private BlockPos toppos;
    private InteractionUtil.Placement placement;
    private InteractionUtil.Placement lastPlacement;
    private final Timer lastPlacementTimer;
    private final Timer pausetimer;
    
    public CevBreaker() {
        super("CevBreaker", "CevBreaker", Category.COMBAT);
        this.Color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.Color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.pickTickSwitch = (Setting<Integer>)this.register(new Setting("Pick Switch Destroy", (T)0, (T)0, (T)20));
        this.placeRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)4.5f, (T)1.0f, (T)16.0f));
        this.startBreak = false;
        this.broke = false;
        this.renderTimer = new Timer();
        this.mode = (Setting<Mode>)this.register(new Setting("BreakMode", (T)Mode.TripleP));
        this.crysDelay = (Setting<Integer>)this.register(new Setting("CrysDelay", (T)200, (T)1, (T)1000));
        this.atttt = (Setting<Integer>)this.register(new Setting("AttackDelay", (T)200, (T)1, (T)1000));
        this.pausedelay = (Setting<Integer>)this.register(new Setting("PauseDelay", (T)300, (T)1, (T)1000));
        this.actionShift = (Setting<Integer>)this.register(new Setting("ActionShift", (T)3, (T)1, (T)8));
        this.actionInterval = (Setting<Integer>)this.register(new Setting("ActionInterval", (T)0, (T)0, (T)10));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.p1 = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)true));
        this.MStrict = (Setting<Boolean>)this.register(new Setting("ModeStrict", (T)true));
        this.strictdirection = (Setting<Boolean>)this.register(new Setting("StrictDirection", (T)true));
        this.tick = 99;
        this.wait = 50;
        this.lastBlock = null;
        this.continueBlock = null;
        this.pickStillBol = false;
        this.attackTimer = new Timer();
        this.cryTimer = new Timer();
        this.tickCounter = 0;
        this.playerPos = null;
        this.toppos = null;
        this.lastPlacementTimer = new Timer();
        this.pausetimer = new Timer();
    }
    
    public static EntityEnderCrystal searchCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        for (final Entity entity : CevBreaker.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
            if (entity instanceof EntityEnderCrystal) {
                return (EntityEnderCrystal)entity;
            }
        }
        for (final Entity entity : CevBreaker.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
            if (entity instanceof EntityEnderCrystal) {
                return (EntityEnderCrystal)entity;
            }
        }
        return null;
    }
    
    public static int getPicSlot() {
        int pic = -1;
        if (Util.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
            pic = Util.mc.player.inventory.currentItem;
        }
        if (pic == -1) {
            for (int l = 0; l < 9; ++l) {
                if (Util.mc.player.inventory.getStackInSlot(l).getItem() == Items.DIAMOND_PICKAXE) {
                    pic = l;
                    break;
                }
            }
        }
        return pic;
    }
    
    @Override
    public void onEnable() {
        if (CevBreaker.mc.player == null || CevBreaker.mc.world == null) {
            this.toggle();
            return;
        }
        this.startBreak = false;
        this.renderPos = null;
        this.playerPos = null;
        this.placement = null;
        this.lastPlacement = null;
        this.tickCounter = this.actionInterval.getValue();
        this.lastBlock = null;
        this.tick = 99;
        this.wait = 50;
        this.continueBlock = null;
        this.pickStillBol = false;
        this.direction = null;
        this.broke = false;
    }
    
    @Override
    public void onDisable() {
        this.continueBlock = null;
        this.lastBlock = null;
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.DoubleP && this.continueBlock != null) {
            if (BlockUtils.getBlockgs(this.continueBlock) instanceof BlockAir) {
                this.broke = true;
            }
            if (!(BlockUtils.getBlockgs(this.continueBlock) instanceof BlockAir) && this.broke) {
                CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                CevBreaker.mc.playerController.onPlayerDamageBlock(this.continueBlock, EnumFacing.UP);
                this.broke = false;
            }
        }
        if (this.tick != 99 && this.tick++ >= this.wait) {
            if (this.oldslot != -1) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
                CevBreaker.mc.playerController.updateController();
                if (this.lastBlock != null && this.direction != null) {
                    CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, this.direction));
                }
                this.oldslot = -1;
            }
            if (!this.pickStillBol) {
                this.wait = 12;
                this.tick = 0;
                this.oldslot = InventoryUtil.getPicatHotbar();
                this.pickStillBol = true;
            }
            else {
                this.tick = 99;
            }
        }
    }
    
    public void onBreakPacket() {
        if (this.mode.getValue() == Mode.Vanilla) {
            return;
        }
        if (CevBreaker.mc.world == null || CevBreaker.mc.player == null) {
            return;
        }
        if (this.toppos == null) {
            return;
        }
        if (this.mode.getValue() == Mode.DoubleP) {
            this.continueBlock = this.toppos;
        }
        CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.toppos, this.handlePlaceRotation(this.toppos)));
        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.toppos, this.handlePlaceRotation(this.toppos)));
        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.toppos, this.handlePlaceRotation(this.toppos)));
        this.lastBlock = this.toppos;
        this.direction = this.handlePlaceRotation(this.toppos);
        this.oldslot = InventoryUtil.getPicatHotbar();
        this.tick = 0;
        this.wait = this.pickTickSwitch.getValue() + 50;
        this.pickStillBol = false;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.renderPos != null && !this.renderTimer.passedMs(500L)) {
            RenderUtil.drawBlockOutline(this.renderPos, this.Color2.getValue().getColorObject(), 0.3f, true, 0);
        }
        if (this.lastBlock != null) {
            RenderUtil.drawBlockOutline(this.lastBlock, new Color(175, 175, 255), 2.0f, false, 0);
            final float prognum = this.tick / (float)this.pickTickSwitch.getValue() * 100.0f / 50.0f * CevBreaker.mc.world.getBlockState(this.lastBlock).getBlock().getBlockHardness(CevBreaker.mc.world.getBlockState(this.lastBlock), (World)CevBreaker.mc.world, this.lastBlock);
            GlStateManager.pushMatrix();
            try {
                RenderUtil.glBillboardDistanceScaled(this.lastBlock.getX() + 0.5f, this.lastBlock.getY() + 0.5f, this.lastBlock.getZ() + 0.5f, (EntityPlayer)CevBreaker.mc.player, 1.0f);
            }
            catch (Exception ex) {}
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            CevBreaker.mc.fontRenderer.drawStringWithShadow(String.valueOf(prognum), (float)(int)(-(CevBreaker.mc.fontRenderer.getStringWidth(String.valueOf(prognum)) / 2.0)), -4.0f, -1);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        if (this.toppos != null) {
            final EntityEnderCrystal ent = searchCrystal(this.toppos);
            if (ent != null && this.attackTimer.passedMs(this.atttt.getValue())) {
                RenderUtil.drawBoxESP(this.toppos, new Color(2472706), false, new Color(3145472), 0.5f, true, true, 170, false, 0);
            }
            else {
                RenderUtil.drawBoxESP(this.toppos, new Color(12255746), false, new Color(16711680), 0.5f, true, true, 170, false, 0);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync event) {
        if (this.playerPos != null) {
            if (this.canPlaceCrystal(this.playerPos.up().up()) && this.cryTimer.passedMs(this.crysDelay.getValue()) && searchCrystal(this.playerPos.up().up()) == null) {
                this.placeCrystal(this.playerPos.up().up(), this.handlePlaceRotation(this.playerPos.up().up()));
                this.setPickSlot();
                this.cryTimer.reset();
            }
            else if (this.canBreakCrystal(this.playerPos.up().up())) {
                final EntityEnderCrystal ent = searchCrystal(this.playerPos.up().up());
                if (ent != null && this.attackTimer.passedMs(this.atttt.getValue())) {
                    CevBreaker.mc.playerController.attackEntity((EntityPlayer)CevBreaker.mc.player, (Entity)ent);
                    CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.startBreak = true;
                    this.attackTimer.reset();
                    this.pausetimer.reset();
                }
            }
        }
        if (this.toppos != null) {
            if (this.mode.getValue() == Mode.Vanilla) {
                final EntityEnderCrystal ent = searchCrystal(this.toppos);
                if (ent == null) {
                    this.placeCrystal(this.toppos, this.handlePlaceRotation(this.toppos));
                }
                else {
                    this.setPickSlot();
                    if (CevBreaker.mc.world.getBlockState(this.toppos).getBlock() != Blocks.AIR) {
                        CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                        CevBreaker.mc.playerController.onPlayerDamageBlock(this.toppos, this.handlePlaceRotation(this.toppos));
                    }
                }
            }
            else if (!this.startBreak) {
                this.onBreakPacket();
                this.startBreak = true;
            }
        }
        if (this.placement != null) {
            this.lastPlacement = this.placement;
            this.lastPlacementTimer.reset();
        }
        this.placement = null;
        this.playerPos = null;
        final int ping = CrystalUtils.ping();
        CevBreaker.shiftedBlocks.forEach((pos, time) -> {
            if (System.currentTimeMillis() - time > ping + 100) {
                CevBreaker.shiftedBlocks.remove(pos);
            }
            return;
        });
        if (event.isCanceled()) {
            return;
        }
        if (this.strict.getValue() && (!CevBreaker.mc.player.onGround || !CevBreaker.mc.player.collidedVertically)) {
            return;
        }
        if (((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled()) {
            return;
        }
        if (this.tickCounter < this.actionInterval.getValue()) {
            ++this.tickCounter;
        }
        final int slot = this.getBlockSlot();
        if (slot == -1) {
            Command.sendMessage("No Obby Found!");
            this.toggle();
            return;
        }
        this.itemSlot = slot;
        final EntityPlayer nearestPlayer = this.getNearestTarget();
        if (nearestPlayer == null) {
            return;
        }
        if (this.tickCounter < this.actionInterval.getValue()) {
            if (this.lastPlacement != null && !this.lastPlacementTimer.passedMs(650L)) {
                CevBreaker.mc.player.rotationPitch = this.lastPlacement.getPitch();
                CevBreaker.mc.player.rotationYaw = this.lastPlacement.getYaw();
            }
            return;
        }
        this.playerPos = new BlockPos(nearestPlayer.posX, nearestPlayer.posY, nearestPlayer.posZ);
        final BlockPos firstPos = this.getNextPos(this.playerPos);
        if (firstPos != null) {
            this.placement = InteractionUtil.preparePlacement(firstPos, this.rotate.getValue(), event);
            if (this.placement != null) {
                CevBreaker.shiftedBlocks.put(firstPos, System.currentTimeMillis());
                this.tickCounter = 0;
                this.renderPos = firstPos;
                this.renderTimer.reset();
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final EventPostSync event) {
        if (!this.pausetimer.passedMs(this.pausedelay.getValue())) {
            return;
        }
        if (this.placement != null && this.playerPos != null && this.itemSlot != -1) {
            final boolean changeItem = CevBreaker.mc.player.inventory.currentItem != this.itemSlot;
            final int startingItem = CevBreaker.mc.player.inventory.currentItem;
            if (changeItem) {
                CevBreaker.mc.player.inventory.currentItem = this.itemSlot;
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.itemSlot));
            }
            final boolean isSprinting = CevBreaker.mc.player.isSprinting();
            final boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(this.placement.getNeighbour());
            if (isSprinting) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (shouldSneak) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
            for (int extraBlocks = 0; extraBlocks < this.actionShift.getValue() - 1; ++extraBlocks) {
                final BlockPos nextPos = this.getNextPos(this.playerPos);
                if (nextPos == null) {
                    break;
                }
                final InteractionUtil.Placement nextPlacement = InteractionUtil.preparePlacement(nextPos, this.rotate.getValue(), true);
                if (nextPlacement == null) {
                    break;
                }
                this.placement = nextPlacement;
                CevBreaker.shiftedBlocks.put(nextPos, System.currentTimeMillis());
                InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
                this.renderPos = nextPos;
                this.renderTimer.reset();
            }
            this.cryTimer.reset();
            if (shouldSneak) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (isSprinting) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
        }
    }
    
    private boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection) {
        return InteractionUtil.canPlaceBlock(pos, strictDirection) && !CevBreaker.shiftedBlocks.containsKey(pos);
    }
    
    private BlockPos getNextPos(final BlockPos playerPos) {
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.offset(enumFacing).down(), true)) {
                final BlockPos tempBlock = playerPos.offset(enumFacing).down();
                final double tempDistance = CevBreaker.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.offset(enumFacing), false)) {
                final BlockPos tempBlock = playerPos.offset(enumFacing);
                final double tempDistance = CevBreaker.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.up().offset(enumFacing), false)) {
                final BlockPos tempBlock = playerPos.up().offset(enumFacing);
                final double tempDistance = CevBreaker.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        final Block baseBlock = CevBreaker.mc.world.getBlockState(playerPos.up().up()).getBlock();
        if (baseBlock instanceof BlockAir || baseBlock instanceof BlockLiquid) {
            if (this.canPlaceBlock(playerPos.up().up(), false)) {
                this.toppos = playerPos.up().up();
                return playerPos.up().up();
            }
            final BlockPos offsetPos = playerPos.up().up().offset(EnumFacing.byHorizontalIndex(MathHelper.floor(CevBreaker.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3));
            if (this.canPlaceBlock(offsetPos, false)) {
                return offsetPos;
            }
        }
        return null;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (CevBreaker.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CevBreaker.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (CevBreaker.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || CevBreaker.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                return false;
            }
            for (final Entity entity : CevBreaker.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
            for (final Entity entity : CevBreaker.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    private boolean canBreakCrystal(final BlockPos blockPos) {
        try {
            if (CevBreaker.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CevBreaker.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return true;
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return false;
    }
    
    public boolean setCrystalSlot() {
        final int crystalSlot = CrystalUtils.getCrystalSlot();
        if (crystalSlot == -1) {
            return false;
        }
        if (CevBreaker.mc.player.inventory.currentItem != crystalSlot) {
            CevBreaker.mc.player.inventory.currentItem = crystalSlot;
            CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(crystalSlot));
        }
        return true;
    }
    
    public boolean setPickSlot() {
        final int pickslot = getPicSlot();
        if (pickslot == -1) {
            return false;
        }
        if (CevBreaker.mc.player.inventory.currentItem != pickslot) {
            CevBreaker.mc.player.inventory.currentItem = pickslot;
            CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(pickslot));
        }
        return true;
    }
    
    public boolean placeCrystal(final BlockPos pos, final EnumFacing facing) {
        if (pos == null) {
            return false;
        }
        if (!this.setCrystalSlot()) {
            return false;
        }
        if (CevBreaker.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
            return false;
        }
        BlockUtils.rightClickBlock(pos, CevBreaker.mc.player.getPositionVector().add(0.0, (double)CevBreaker.mc.player.getEyeHeight(), 0.0), EnumHand.MAIN_HAND, facing, true);
        return true;
    }
    
    private int getBlockSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = CevBreaker.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private EntityPlayer getNearestTarget() {
        final Stream<EntityPlayer> stream = (Stream<EntityPlayer>)CevBreaker.mc.world.playerEntities.stream();
        return stream.filter(e -> e != CevBreaker.mc.player && e != CevBreaker.mc.getRenderViewEntity()).filter(e -> !Thunderhack.friendManager.isFriend(e.getName())).filter(e -> CevBreaker.mc.player.getDistance(e) < Math.max(this.placeRange.getValue() - 1.0f, 1.0f)).filter(this::isValidBase).min(Comparator.comparing(e -> CevBreaker.mc.player.getDistance(e))).orElse(null);
    }
    
    private boolean isValidBase(final EntityPlayer player) {
        final BlockPos basePos = new BlockPos(player.posX, player.posY, player.posZ).down();
        final Block baseBlock = CevBreaker.mc.world.getBlockState(basePos).getBlock();
        return !(baseBlock instanceof BlockAir) && !(baseBlock instanceof BlockLiquid);
    }
    
    public EnumFacing handlePlaceRotation(final BlockPos pos) {
        if (pos == null || CevBreaker.mc.player == null) {
            return null;
        }
        EnumFacing facing = null;
        Vec3d placeVec = null;
        double[] placeRotation = null;
        final double increment = 0.45;
        final double start = 0.05;
        final double end = 0.95;
        final Vec3d eyesPos = new Vec3d(CevBreaker.mc.player.posX, CevBreaker.mc.player.getEntityBoundingBox().minY + CevBreaker.mc.player.getEyeHeight(), CevBreaker.mc.player.posZ);
        for (double xS = start; xS <= end; xS += increment) {
            for (double yS = start; yS <= end; yS += increment) {
                for (double zS = start; zS <= end; zS += increment) {
                    final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                    final double distToPosVec = eyesPos.distanceTo(posVec);
                    final double diffX = posVec.x - eyesPos.x;
                    final double diffY = posVec.y - eyesPos.y;
                    final double diffZ = posVec.z - eyesPos.z;
                    final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                    final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                    final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                    final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                    final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                    final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                    final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                    final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                    final RayTraceResult rayTraceResult = CevBreaker.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, true, false);
                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos)) {
                        final Vec3d currVec = posVec;
                        final double[] currRotation = tempPlaceRotation;
                        if (this.strictdirection.getValue()) {
                            if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                if (CevBreaker.mc.player.getPositionVector().add(0.0, (double)CevBreaker.mc.player.getEyeHeight(), 0.0).distanceTo(currVec) < CevBreaker.mc.player.getPositionVector().add(0.0, (double)CevBreaker.mc.player.getEyeHeight(), 0.0).distanceTo(placeVec)) {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                    }
                                }
                            }
                            else {
                                placeVec = currVec;
                                placeRotation = currRotation;
                                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                    facing = rayTraceResult.sideHit;
                                }
                            }
                        }
                        else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                            if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, currRotation[1] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedPitch()) < Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, placeRotation[1] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedPitch())) {
                                placeVec = currVec;
                                placeRotation = currRotation;
                                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                    facing = rayTraceResult.sideHit;
                                }
                            }
                        }
                        else {
                            placeVec = currVec;
                            placeRotation = currRotation;
                            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                facing = rayTraceResult.sideHit;
                            }
                        }
                    }
                }
            }
        }
        if (this.MStrict.getValue()) {
            if (placeRotation != null && facing != null) {
                return facing;
            }
            for (double xS = start; xS <= end; xS += increment) {
                for (double yS = start; yS <= end; yS += increment) {
                    for (double zS = start; zS <= end; zS += increment) {
                        final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                        final double distToPosVec = eyesPos.distanceTo(posVec);
                        final double diffX = posVec.x - eyesPos.x;
                        final double diffY = posVec.y - eyesPos.y;
                        final double diffZ = posVec.z - eyesPos.z;
                        final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                        final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                        final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                        final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                        final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                        final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                        final RayTraceResult rayTraceResult = CevBreaker.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, true, true);
                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                            final Vec3d currVec = posVec;
                            final double[] currRotation = tempPlaceRotation;
                            if (this.strictdirection.getValue()) {
                                if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                    if (CevBreaker.mc.player.getPositionVector().add(0.0, (double)CevBreaker.mc.player.getEyeHeight(), 0.0).distanceTo(currVec) < CevBreaker.mc.player.getPositionVector().add(0.0, (double)CevBreaker.mc.player.getEyeHeight(), 0.0).distanceTo(placeVec)) {
                                        placeVec = currVec;
                                        placeRotation = currRotation;
                                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                            facing = rayTraceResult.sideHit;
                                        }
                                    }
                                }
                                else {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                    }
                                }
                            }
                            else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, currRotation[1] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedPitch()) < Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, placeRotation[1] - ((IEntityPlayerSP)CevBreaker.mc.player).getLastReportedPitch())) {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                    }
                                }
                            }
                            else {
                                placeVec = currVec;
                                placeRotation = currRotation;
                                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                    facing = rayTraceResult.sideHit;
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (facing != null) {
            return facing;
        }
        if (pos.getY() > CevBreaker.mc.player.posY + CevBreaker.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }
    
    static {
        CevBreaker.shiftedBlocks = new ConcurrentHashMap<BlockPos, Long>();
    }
    
    public enum Mode
    {
        Packet, 
        DoubleP, 
        TripleP, 
        Vanilla, 
        StrictFast;
    }
}
