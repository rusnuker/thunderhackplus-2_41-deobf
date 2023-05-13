//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.*;
import java.util.function.*;
import java.util.*;
import com.mrzak34.thunderhack.util.surround.*;
import com.mrzak34.thunderhack.util.phobos.*;

public class Surround extends Module
{
    private String really;
    public final Setting<Boolean> multiThread;
    private final Setting<Integer> delay;
    private final Setting<EventMode> eventMode;
    private final Setting<Mode> mainMode;
    private final Setting<BlockMode> blockMode;
    private final Setting<Switch> switchMode;
    private final Setting<SwitchWhen> switchWhen;
    private final Setting<CBTimings> cBTimingsMode;
    private final Setting<CbMode> cbMode;
    private final Setting<CBRotateMode> cBRotateMode;
    private final Setting<Rotate> rotateMode;
    private final Setting<detectEntityMode> detectEntity;
    private final Setting<ToggleMode> toggleMode;
    public Setting<Boolean> syncronized;
    public Setting<Boolean> allEntities;
    public Setting<Boolean> extension;
    public Setting<Boolean> safeDynamic;
    public Setting<Boolean> rangeCheck;
    public Setting<Boolean> smartBlock;
    public Setting<Boolean> safeEChest;
    public Setting<Boolean> center;
    public Setting<Boolean> smartCenter;
    public Setting<Boolean> smartHelping;
    public Setting<Boolean> fightCA;
    public Setting<Boolean> detectSound;
    public Setting<Boolean> onEntityDestruction;
    public Setting<Boolean> antiCity;
    public Setting<Boolean> manipulateWorld;
    public Setting<Boolean> postReceive;
    public Setting<Boolean> packet;
    public Setting<Boolean> feetBlocks;
    public Setting<Boolean> down;
    public Setting<Boolean> inAir;
    public Setting<Boolean> airMotion;
    public Setting<Boolean> crystalBreaker;
    public Setting<Boolean> cBRotate;
    public Setting<Boolean> cBPacket;
    public Setting<Boolean> cientSide;
    public final Setting<Boolean> cbTerrain;
    public final Setting<Boolean> cbNoSuicide;
    private final Setting<Float> heightLimit;
    private final Setting<Float> cBSequentialDelay;
    private final Setting<Float> cBRange;
    private final Setting<Integer> cBDelay;
    private final Setting<Float> placeRange;
    private final Setting<Float> toggleHeight;
    private ModeUtil modeUtil;
    private Timer breakTimer;
    private boolean haveBlock;
    private Function blockState;
    private double pos_Y;
    private static final ScheduledExecutorService THREAD;
    
    public Surround() {
        super("Surround", "\u043e\u043a\u0440\u0443\u0436\u0430\u0435\u0442 \u0442\u0435\u0431\u044f \u043e\u0431\u0441\u043e\u0439", "surrounds you", Category.COMBAT);
        this.really = " TheKisDevs & LavaHack Development owns you, and I am sorry, because it is uncrackable <3";
        this.multiThread = (Setting<Boolean>)this.register(new Setting("Multi Thread", (T)false));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)15, (T)0, (T)100));
        this.eventMode = (Setting<EventMode>)this.register(new Setting("Event Mode", (T)EventMode.SyncEvent));
        this.mainMode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Normal));
        this.blockMode = (Setting<BlockMode>)this.register(new Setting("Block", (T)BlockMode.Obsidian));
        this.switchMode = (Setting<Switch>)this.register(new Setting("Switch", (T)Switch.Silent));
        this.switchWhen = (Setting<SwitchWhen>)this.register(new Setting("SwitchWhen", (T)SwitchWhen.Place));
        this.cBTimingsMode = (Setting<CBTimings>)this.register(new Setting("CB Timings", (T)CBTimings.Adaptive));
        this.cbMode = (Setting<CbMode>)this.register(new Setting("CbMode", (T)CbMode.SurroundBlocks));
        this.cBRotateMode = (Setting<CBRotateMode>)this.register(new Setting("CBRotateMode", (T)CBRotateMode.Packet));
        this.rotateMode = (Setting<Rotate>)this.register(new Setting("Rotate", (T)Rotate.Packet));
        this.detectEntity = (Setting<detectEntityMode>)this.register(new Setting("DetectEntity", (T)detectEntityMode.RemoveEntity));
        this.toggleMode = (Setting<ToggleMode>)this.register(new Setting("Toggle", (T)ToggleMode.OnComplete));
        this.syncronized = (Setting<Boolean>)this.register(new Setting("Syncronized", (T)false));
        this.allEntities = (Setting<Boolean>)this.register(new Setting("AllEntities", (T)false));
        this.extension = (Setting<Boolean>)this.register(new Setting("Extension", (T)false));
        this.safeDynamic = (Setting<Boolean>)this.register(new Setting("Safe Dynamic", (T)false));
        this.rangeCheck = (Setting<Boolean>)this.register(new Setting("RangeCheck", (T)false));
        this.smartBlock = (Setting<Boolean>)this.register(new Setting("Smart Block", (T)false));
        this.safeEChest = (Setting<Boolean>)this.register(new Setting("Safe E Chest", (T)false));
        this.center = (Setting<Boolean>)this.register(new Setting("Center", (T)true));
        this.smartCenter = (Setting<Boolean>)this.register(new Setting("SmartCenter", (T)false));
        this.smartHelping = (Setting<Boolean>)this.register(new Setting("SmartHelping", (T)false));
        this.fightCA = (Setting<Boolean>)this.register(new Setting("FightCA", (T)true));
        this.detectSound = (Setting<Boolean>)this.register(new Setting("DetectSound", (T)true));
        this.onEntityDestruction = (Setting<Boolean>)this.register(new Setting("OnEntityDestruction", (T)false));
        this.antiCity = (Setting<Boolean>)this.register(new Setting("AntiCity", (T)false));
        this.manipulateWorld = (Setting<Boolean>)this.register(new Setting("ManipulateWorld", (T)false));
        this.postReceive = (Setting<Boolean>)this.register(new Setting("PostReceive", (T)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.feetBlocks = (Setting<Boolean>)this.register(new Setting("FeetBlocks", (T)false));
        this.down = (Setting<Boolean>)this.register(new Setting("Down", (T)false));
        this.inAir = (Setting<Boolean>)this.register(new Setting("InAir", (T)false));
        this.airMotion = (Setting<Boolean>)this.register(new Setting("InAirMotionStop", (T)false));
        this.crystalBreaker = (Setting<Boolean>)this.register(new Setting("CrystalBreaker", (T)true));
        this.cBRotate = (Setting<Boolean>)this.register(new Setting("CBRotate", (T)false));
        this.cBPacket = (Setting<Boolean>)this.register(new Setting("CBPacket", (T)false));
        this.cientSide = (Setting<Boolean>)this.register(new Setting("ClientSide", (T)false));
        this.cbTerrain = (Setting<Boolean>)this.register(new Setting("CbTerrain", (T)true));
        this.cbNoSuicide = (Setting<Boolean>)this.register(new Setting("CbNoSuicide", (T)true));
        this.heightLimit = (Setting<Float>)this.register(new Setting("HeightLimit", (T)256.0f, (T)0.0f, (T)256.0f));
        this.cBSequentialDelay = (Setting<Float>)this.register(new Setting("CBSequentialDelay", (T)1.0f, (T)0.0f, (T)10.0f));
        this.cBRange = (Setting<Float>)this.register(new Setting("CBRange", (T)3.0f, (T)0.0f, (T)10.0f));
        this.cBDelay = (Setting<Integer>)this.register(new Setting("CBDelay", (T)0, (T)0, (T)500));
        this.placeRange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)5.0f, (T)2.0f, (T)10.0f));
        this.toggleHeight = (Setting<Float>)this.register(new Setting("ToggleHeight", (T)0.4f, (T)0.1f, (T)1.0f));
        this.modeUtil = new ModeUtil();
        this.breakTimer = new Timer();
        this.haveBlock = false;
        this.blockState = Surround::getBlockState;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTick(final TickEvent tickEvent) {
        if (this.eventMode.getValue() != EventMode.Tick) {
            return;
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
            return;
        }
        this.doNonSynchronized();
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (this.eventMode.getValue() != EventMode.Update) {
            return;
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
        }
        else {
            this.doNonSynchronized();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onSync(final EventSync e) {
        if (this.eventMode.getValue() != EventMode.SyncEvent) {
            return;
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
            return;
        }
        this.doNonSynchronized();
    }
    
    @Override
    public void onEnable() {
        this.breakTimer.reset();
        if (fullNullCheck()) {
            return;
        }
        this.pos_Y = Surround.mc.player.posY;
        if (this.center.getValue() && !this.setCenter() && this.toggleMode.getValue() != ToggleMode.Never) {
            this.disable();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive2(final PacketEvent.Receive event) {
        if (!this.onEntityDestruction.getValue()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketDestroyEntities)) {
            return;
        }
        final SPacketDestroyEntities sPacketDestroyEntities = (SPacketDestroyEntities)event.getPacket();
        final int[] getEntityIDs;
        final int[] nArray = getEntityIDs = sPacketDestroyEntities.getEntityIDs();
        for (final int n2 : getEntityIDs) {
            Surround.mc.world.removeEntityFromWorld(n2);
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
            return;
        }
        this.doNonSynchronized();
    }
    
    @SubscribeEvent
    public void onPacketReceivePost(final PacketEvent.ReceivePost e) {
        if (!this.postReceive.getValue()) {
            return;
        }
        this.doAntiCity(new PacketEvent.Receive(e.getPacket()));
    }
    
    @SubscribeEvent
    public void onPacketReceivePre(final PacketEvent.Receive e) {
        if (this.postReceive.getValue()) {
            return;
        }
        this.doAntiCity(e);
    }
    
    @SubscribeEvent
    public void onSpawnCrystal(final EventEntitySpawn event) {
        if (!this.fightCA.getValue()) {
            return;
        }
        if (this.detectEntity.getValue() == detectEntityMode.Off) {
            return;
        }
        final Entity entity = event.getEntity();
        final List<BlockPos> list = this.modeUtil.getBlockPositions(this.mainMode.getValue());
        if (!this.checkIntersections(entity.getEntityBoundingBox(), list)) {
            return;
        }
        if (this.detectEntity.getValue() == detectEntityMode.SetDead || this.detectEntity.getValue() == detectEntityMode.Off) {
            entity.setDead();
        }
        if (this.detectEntity.getValue() == detectEntityMode.RemoveEntity || this.detectEntity.getValue() == detectEntityMode.Both) {
            Surround.mc.world.removeEntity(entity);
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
            return;
        }
        this.doNonSynchronized();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)event.getPacket();
            if (sPacketSoundEffect.getSound() == SoundEvents.BLOCK_STONE_PLACE) {
                event.setCanceled(true);
            }
        }
        if (!this.fightCA.getValue()) {
            return;
        }
        if (!this.detectSound.getValue()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketSoundEffect)) {
            return;
        }
        final SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)event.getPacket();
        if (sPacketSoundEffect.getSound() != SoundEvents.ENTITY_GENERIC_EXPLODE) {
            return;
        }
        final Vec3d vec3d = new Vec3d(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ());
        if (!this.doesCrystalWantToFuckUs(vec3d, this.modeUtil.getBlockPositions(this.mainMode.getValue()))) {
            return;
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
            return;
        }
        this.doNonSynchronized();
    }
    
    private void doNonSynchronized() {
        if (this.multiThread.getValue()) {
            Surround.THREAD.schedule(this::handleSurround, this.delay.getValue(), TimeUnit.MILLISECONDS);
        }
        else {
            this.handleSurround();
        }
    }
    
    private synchronized void doSynchronized() {
        if (this.multiThread.getValue()) {
            Surround.THREAD.schedule(this::handleSurround, this.delay.getValue(), TimeUnit.MILLISECONDS);
        }
        else {
            this.handleSurround();
        }
    }
    
    private void handleSurround() {
        if (fullNullCheck()) {
            return;
        }
        if (Surround.mc.player.ticksExisted < 60) {
            return;
        }
        if (!this.inAir.getValue() || Surround.mc.player.onGround) {
            if (this.inAir.getValue() && this.airMotion.getValue() && !Surround.mc.player.onGround) {
                Surround.mc.player.motionX = 0.0;
                Surround.mc.player.motionZ = 0.0;
            }
            final int n2 = Surround.mc.player.inventory.currentItem;
            final int n3 = this.getSlotWithBestBlock();
            if (n3 == -1) {
                return;
            }
            if (this.switchWhen.getValue() == SwitchWhen.RunSurround) {
                this.SwitchMethod(this.switchMode.getValue(), n3, false);
            }
            final List<BlockPos> positions = this.modeUtil.getBlockPositions(this.mainMode.getValue());
            this.doPlace(positions);
            if (this.crystalBreaker.getValue()) {
                this.doCrystalBreaker(positions);
            }
            if (this.switchWhen.getValue() == SwitchWhen.RunSurround) {
                this.SwitchMethod(this.switchMode.getValue(), n2, true);
            }
        }
        if (Surround.mc.player.posY > this.pos_Y + this.toggleHeight.getValue() && this.toggleMode.getValue() == ToggleMode.PositiveYChange) {
            this.disable();
        }
        if (Surround.mc.player.posY != this.pos_Y && this.toggleMode.getValue() == ToggleMode.YChange) {
            this.disable();
        }
        if ((Surround.mc.player.posY != this.pos_Y || !Surround.mc.player.onGround) && this.toggleMode.getValue() == ToggleMode.Combo) {
            this.disable();
        }
        if (this.toggleMode.getValue() == ToggleMode.OnComplete) {
            this.disable();
        }
        if (!Surround.mc.player.onGround && this.toggleMode.getValue() == ToggleMode.OffGround) {
            this.disable();
        }
    }
    
    @Override
    public void onDisable() {
        this.blockState = Surround::getBlockState;
        this.breakTimer.reset();
    }
    
    private void doCrystalBreaker(final List<BlockPos> list) {
        if (!this.breakTimer.passedMs(this.cBDelay.getValue())) {
            return;
        }
        final float[] fArray = { Surround.mc.player.rotationYaw, Surround.mc.player.rotationPitch };
        final HashSet<EntityEnderCrystal> hashSet = new HashSet<EntityEnderCrystal>(64);
        if (this.cbMode.getValue() == CbMode.Area) {
            final double d = this.cBRange.getValue();
            final double d2 = Surround.mc.player.posX - d;
            final double d3 = Surround.mc.player.posY - d;
            final double d4 = Surround.mc.player.posZ - d;
            final double d5 = Surround.mc.player.posX + d;
            final double d6 = Surround.mc.player.posY + d;
            final double d7 = Surround.mc.player.posZ + d;
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d2, d3, d4, d5, d6, d7);
            for (final EntityEnderCrystal entityEnderCrystal : Surround.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, axisAlignedBB)) {
                if (!this.canBreakCrystal(entityEnderCrystal)) {
                    return;
                }
                this.breakCrystal(entityEnderCrystal, fArray);
            }
            return;
        }
        for (final BlockPos blockPos : list) {
            for (final EntityEnderCrystal entityEnderCrystal2 : Surround.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(blockPos))) {
                if (!hashSet.contains(entityEnderCrystal2)) {
                    if (!this.canBreakCrystal(entityEnderCrystal2)) {
                        continue;
                    }
                    this.breakCrystal(entityEnderCrystal2, fArray);
                    hashSet.add(entityEnderCrystal2);
                }
            }
        }
    }
    
    private boolean canBreakCrystal(final EntityEnderCrystal entityEnderCrystal) {
        if (this.cBTimingsMode.getValue() == CBTimings.Sequential && entityEnderCrystal.ticksExisted < this.cBSequentialDelay.getValue()) {
            return false;
        }
        if (!this.cbNoSuicide.getValue()) {
            return true;
        }
        final float f = DamageUtil.calculateDamage(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, (Entity)Surround.mc.player, this.cbTerrain.getValue());
        return f < Surround.mc.player.getHealth() + Surround.mc.player.getAbsorptionAmount();
    }
    
    private void breakCrystal(final EntityEnderCrystal entityEnderCrystal, final float[] fArray) {
        if (this.cBRotate.getValue()) {
            final float[] fArray2 = SilentRotationUtil.calcAngle(entityEnderCrystal.getPositionVector());
            this.rotateToCrystal(fArray2);
        }
        if (this.cBPacket.getValue()) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)entityEnderCrystal));
        }
        else {
            Surround.mc.playerController.attackEntity((EntityPlayer)Surround.mc.player, (Entity)entityEnderCrystal);
        }
        Surround.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (this.cientSide.getValue()) {
            Surround.mc.world.removeEntityFromWorld(entityEnderCrystal.getEntityId());
        }
        if (!this.cBRotate.getValue()) {
            return;
        }
        this.rotateToCrystal(fArray);
    }
    
    private void rotateToCrystal(final float[] fArray) {
        if (this.cBRotateMode.getValue() != CBRotateMode.Client) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(fArray[0], fArray[1], Surround.mc.player.onGround));
        }
        if (this.cBRotateMode.getValue() != CBRotateMode.Client && this.cBRotateMode.getValue() != CBRotateMode.Both) {
            return;
        }
        Surround.mc.player.rotationYaw = fArray[0];
        Surround.mc.player.rotationPitch = fArray[1];
    }
    
    private boolean setCenter() {
        if (!this.smartCenter.getValue()) {
            this.setCenterPos(new BlockPos(Surround.mc.player.posX, Surround.mc.player.posY, Surround.mc.player.posZ));
            return true;
        }
        BlockPos blockPos = new BlockPos(Surround.mc.player.posX, Surround.mc.player.posY, Surround.mc.player.posZ);
        if (this.checkBlockPos(blockPos)) {
            blockPos = this.getCenterBlockPos(blockPos);
        }
        if (blockPos == null) {
            return false;
        }
        this.setCenterPos(blockPos);
        return true;
    }
    
    private void setCenterPos(final BlockPos blockPos) {
        final Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, Surround.mc.player.posY, blockPos.getZ() + 0.5);
        Surround.mc.player.motionX = 0.0;
        Surround.mc.player.motionZ = 0.0;
        Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(vec3d.x, vec3d.y, vec3d.z, true));
        Surround.mc.player.setPosition(vec3d.x, vec3d.y, vec3d.z);
    }
    
    private void doPlace(final List<BlockPos> list) {
        final int n = this.getSlotWithBestBlock();
        if (n == -1) {
            return;
        }
        final int n2 = Surround.mc.player.inventory.currentItem;
        if (this.switchMode.getValue() == Switch.None) {
            final ItemStack itemStack = Surround.mc.player.inventory.getStackInSlot(n2);
            final Item item = itemStack.getItem();
            if (!(item instanceof ItemBlock)) {
                return;
            }
            final Block block2 = ((ItemBlock)item).getBlock();
            if (block2 != this.getBlockByMode()) {
                return;
            }
        }
        for (final BlockPos o : list) {
            if (o.getY() <= this.heightLimit.getValue() && this.checkBlockPos(o) && !this.getInterferingEntities(o)) {
                if (this.rangeCheck.getValue() && Surround.mc.player.getDistanceSq(o) > this.placeRange.getValue()) {
                    continue;
                }
                this.SwitchMethod(this.switchMode.getValue(), n, false);
                PlaceMethod(o, EnumHand.MAIN_HAND, this.rotateMode.getValue(), this.packet.getValue());
                this.SwitchMethod(this.switchMode.getValue(), n2, true);
            }
        }
    }
    
    public static void PlaceMethod(final BlockPos blockPos, final EnumHand enumHand, final Rotate rotate, final boolean bl) {
        final EnumFacing enumFacing = getFacing(blockPos);
        if (enumFacing == null) {
            return;
        }
        final BlockPos blockPos2 = blockPos.offset(enumFacing);
        final EnumFacing enumFacing2 = enumFacing.getOpposite();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos2).add(new Vec3d(0.5, 0.5, 0.5).add(new Vec3d(enumFacing2.getDirectionVec()).scale(0.5)));
        final boolean sneak = Surround.mc.world.getBlockState(blockPos2).getBlock().onBlockActivated((World)Surround.mc.world, blockPos2, Surround.mc.world.getBlockState(blockPos2), (EntityPlayer)Surround.mc.player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
        if (sneak) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Surround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        final float[] angle = getNeededRotations2(blockPos2);
        if (rotate == Rotate.Packet) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], Surround.mc.player.onGround));
        }
        if (rotate == Rotate.Silent) {
            Surround.mc.player.rotationYaw = angle[0];
            Surround.mc.player.rotationPitch = angle[1];
        }
        placeMethod(blockPos2, vec3d, enumHand, enumFacing2, bl);
        Surround.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (sneak) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Surround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (rotate == Rotate.Packet) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], Surround.mc.player.onGround));
        }
    }
    
    public static float[] getNeededRotations2(final BlockPos bp) {
        final Vec3d eyesPos = new Vec3d(Surround.mc.player.posX, Surround.mc.player.posY + Surround.mc.player.getEyeHeight(), Surround.mc.player.posZ);
        final double diffX = bp.getX() - eyesPos.x;
        final double diffY = bp.getY() - eyesPos.y;
        final double diffZ = bp.getZ() - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Surround.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - Surround.mc.player.rotationYaw), Surround.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - Surround.mc.player.rotationPitch) };
    }
    
    public static void placeMethod(final BlockPos blockPos, final Vec3d vec3d, final EnumHand enumHand, final EnumFacing enumFacing, final boolean bl) {
        if (bl) {
            final float f = (float)(vec3d.x - blockPos.getX());
            final float f2 = (float)(vec3d.y - blockPos.getY());
            final float f3 = (float)(vec3d.z - blockPos.getZ());
            Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, f, f2, f3));
        }
        else {
            Surround.mc.playerController.processRightClickBlock(Surround.mc.player, Surround.mc.world, blockPos, enumFacing, vec3d, enumHand);
        }
        Surround.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraft)Surround.mc).setRightClickDelayTimer(4);
    }
    
    public static EnumFacing getFacing(final BlockPos blockPos) {
        final Iterator<EnumFacing> iterator = getFacings(blockPos).iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }
    
    private void SwitchMethod(final Switch mode, final int slot, final boolean update_controller) {
        if (Surround.mc.player.inventory.currentItem == slot) {
            return;
        }
        if (mode == Switch.Packet) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else if (mode == Switch.Silent) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            Surround.mc.player.inventory.currentItem = slot;
        }
        else if (mode == Switch.Vanilla && !update_controller) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            Surround.mc.player.inventory.currentItem = slot;
        }
        if (update_controller) {
            Surround.mc.playerController.updateController();
        }
    }
    
    private void doAntiCity(final PacketEvent.Receive event) {
        if (!this.antiCity.getValue()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketBlockChange)) {
            return;
        }
        final SPacketBlockChange sPacketBlockChange = (SPacketBlockChange)event.getPacket();
        final BlockPos blockPos = sPacketBlockChange.getBlockPosition();
        if (!sPacketBlockChange.getBlockState().getBlock().isReplaceable((IBlockAccess)Surround.mc.world, blockPos)) {
            return;
        }
        final List<BlockPos> list = this.modeUtil.getBlockPositions(this.mainMode.getValue());
        if (!list.contains(blockPos)) {
            return;
        }
        if (this.manipulateWorld.getValue()) {
            this.blockState = (arg_0 -> getBlockStateAS(list, arg_0));
        }
        if (this.syncronized.getValue()) {
            this.doSynchronized();
        }
        else {
            this.doNonSynchronized();
        }
        this.blockState = Surround::getBlockState;
    }
    
    private static IBlockState getBlockStateAS(final List<BlockPos> list, final BlockPos blockPos) {
        if (list.contains(blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        return Surround.mc.world.getBlockState(blockPos);
    }
    
    private boolean checkIntersections(final AxisAlignedBB axisAlignedBB, final List<BlockPos> list) {
        final Iterator<BlockPos> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (new AxisAlignedBB((BlockPos)iterator.next()).intersects(axisAlignedBB)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean getInterferingEntities(final BlockPos blockPos) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos);
        for (final Entity entity : Surround.mc.world.getEntitiesWithinAABB((Class)Entity.class, axisAlignedBB)) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (!(entity instanceof EntityXPOrb)) {
                return true;
            }
        }
        return false;
    }
    
    private int getSlotWithBestBlock() {
        final int obby_slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        final int echest_slot = InventoryUtil.findHotbarBlock(Blocks.ENDER_CHEST);
        if (this.blockMode.getValue() == BlockMode.Obsidian) {
            this.haveBlock = (this.smartBlock.getValue() && obby_slot == -1);
            return obby_slot;
        }
        this.haveBlock = (!this.smartBlock.getValue() && echest_slot != -1);
        return echest_slot;
    }
    
    private Block getBlockByMode() {
        if (this.blockMode.getValue() != BlockMode.Obsidian) {
            return Blocks.ENDER_CHEST;
        }
        return Blocks.OBSIDIAN;
    }
    
    private BlockPos getCenterBlockPos(final BlockPos blockPos) {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (this.blockState.apply(blockPos.north().down()).getMaterial().isSolid()) {
            arrayList.add(blockPos.north());
        }
        if (this.blockState.apply(blockPos.east().down()).getMaterial().isSolid()) {
            arrayList.add(blockPos.east());
        }
        if (this.blockState.apply(blockPos.south().down()).getMaterial().isSolid()) {
            arrayList.add(blockPos.south());
        }
        if (!this.blockState.apply(blockPos.west().down()).getMaterial().isSolid()) {
            return arrayList.stream().min(Comparator.comparingDouble((ToDoubleFunction<? super BlockPos>)Surround::getDistanceToBlock)).orElse(null);
        }
        arrayList.add(blockPos.west());
        return arrayList.stream().min(Comparator.comparingDouble((ToDoubleFunction<? super BlockPos>)Surround::getDistanceToBlock)).orElse(null);
    }
    
    public List<BlockPos> getDynamicPositions() {
        if (!this.extension.getValue()) {
            return this.getDynamicPositionWOE();
        }
        return this.getDynamicPositionWE();
    }
    
    private List<BlockPos> getDynamicPositionWOE() {
        final List<BlockPos> list = this.checkEntities((Entity)Surround.mc.player, Surround.mc.player.posY);
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(16);
        if (this.feetBlocks.getValue()) {
            arrayList.addAll(this.checkHitBoxes((Entity)Surround.mc.player, Surround.mc.player.posY, -1));
        }
        for (final BlockPos o : list) {
            final List<BlockPos> list2 = this.getSmartHelpingPositions(o);
            arrayList.addAll(list2);
            arrayList.add(o);
        }
        return arrayList;
    }
    
    private List<BlockPos> getDynamicPositionWE() {
        final List<BlockPos> list2 = this.getDynamicPositionWOE();
        List<Entity> list3 = new ArrayList<Entity>();
        for (final BlockPos bp : list2) {
            final List<Entity> list4 = (List<Entity>)Surround.mc.world.getEntitiesWithinAABB((Class)(((boolean)this.allEntities.getValue()) ? Entity.class : EntityPlayer.class), new AxisAlignedBB(bp));
            if (list3.isEmpty()) {
                list3 = (List<Entity>)Surround.mc.world.getEntitiesWithinAABB((Class)EntityPlayer.class, new AxisAlignedBB(bp.down()));
            }
            list3.addAll(list4);
        }
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(list2);
        for (final Entity value : list3) {
            if (value.equals((Object)Surround.mc.player)) {
                continue;
            }
            final List<BlockPos> list5 = this.checkEntities(value, Surround.mc.player.posY);
            final ArrayList<BlockPos> arrayList2 = new ArrayList<BlockPos>(16);
            if (this.feetBlocks.getValue()) {
                arrayList2.addAll(this.checkHitBoxes(value, Surround.mc.player.posY, -1));
            }
            for (final BlockPos bp2 : list5) {
                final List<BlockPos> object3 = this.getSmartHelpingPositions(bp2);
                arrayList2.addAll(object3);
                arrayList2.add(bp2);
            }
            final ArrayList<Entity> arrayList3 = new ArrayList<Entity>(list3);
            arrayList3.add((Entity)Surround.mc.player);
            for (final Entity entity : arrayList3) {
                final List<BlockPos> list6 = this.checkHitBoxes(entity, Surround.mc.player.posY, 0);
                for (final BlockPos blockPos : arrayList2) {
                    if (!list6.contains(blockPos)) {
                        continue;
                    }
                    list6.add(blockPos);
                }
            }
            arrayList2.removeAll(list5);
            arrayList.addAll(arrayList2);
        }
        return arrayList;
    }
    
    private List<BlockPos> getSmartHelpingPositions(final BlockPos blockPos) {
        if (!this.smartHelping.getValue()) {
            return Collections.singletonList(blockPos.down());
        }
        if (getFacings(blockPos).isEmpty()) {
            return Collections.singletonList(blockPos.down());
        }
        return Collections.emptyList();
    }
    
    private List<BlockPos> checkEntities(final Entity entity, final double d) {
        final List<BlockPos> list = this.checkHitBoxes(entity, d, 0);
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(16);
        for (final BlockPos blockPos : list) {
            final BlockPos blockPos2 = blockPos.north();
            final BlockPos blockPos3 = blockPos.east();
            final BlockPos blockPos4 = blockPos.south();
            final BlockPos blockPos5 = blockPos.west();
            if (!list.contains(blockPos2)) {
                arrayList.add(blockPos2);
            }
            if (!list.contains(blockPos3)) {
                arrayList.add(blockPos3);
            }
            if (!list.contains(blockPos4)) {
                arrayList.add(blockPos4);
            }
            if (!list.contains(blockPos5)) {
                arrayList.add(blockPos5);
            }
            if (!this.safeDynamic.getValue()) {
                if (!this.safeEChest.getValue()) {
                    continue;
                }
                if (!this.haveBlock) {
                    continue;
                }
            }
            final BlockPos blockPos6 = blockPos.north().west();
            final BlockPos blockPos7 = blockPos.north().east();
            final BlockPos blockPos8 = blockPos.south().east();
            final BlockPos blockPos9 = blockPos.south().west();
            if (!list.contains(blockPos6)) {
                arrayList.add(blockPos6);
            }
            if (!list.contains(blockPos7)) {
                arrayList.add(blockPos7);
            }
            if (!list.contains(blockPos8)) {
                arrayList.add(blockPos8);
            }
            if (list.contains(blockPos9)) {
                continue;
            }
            arrayList.add(blockPos9);
        }
        return arrayList;
    }
    
    public List<BlockPos> checkHitBoxes(final Entity entity, final double d, final int n) {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(16);
        final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        final double d2 = (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double d3 = (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final Vec3d vec3d = new Vec3d(entity.posX + d2, d + n, entity.posZ + d3);
        final Vec3d vec3d2 = new Vec3d(entity.posX + d2, d + n, entity.posZ - d3);
        final Vec3d vec3d3 = new Vec3d(entity.posX - d2, d + n, entity.posZ + d3);
        final Vec3d vec3d4 = new Vec3d(entity.posX - d2, d + n, entity.posZ - d3);
        this.addBlockToList(vec3d, arrayList);
        this.addBlockToList(vec3d2, arrayList);
        this.addBlockToList(vec3d3, arrayList);
        this.addBlockToList(vec3d4, arrayList);
        return arrayList;
    }
    
    public List<BlockPos> getAntiFacePlacePositions() {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(16);
        arrayList.addAll(this.modeUtil.getBlockPositions(Mode.Normal));
        final BlockPos blockPos = new BlockPos(Surround.mc.player.posX, Surround.mc.player.posY, Surround.mc.player.posZ);
        final List<BlockPosWithFacing> list = this.getNeighbours(blockPos.up());
        for (final BlockPosWithFacing pos : list) {
            if (this.getBlock(pos.getPosition().up()) == Blocks.AIR) {
                arrayList.add(pos.getPosition());
            }
            else {
                if (this.getBlock(pos.getPosition().offset(pos.getFacing())) != Blocks.AIR) {
                    continue;
                }
                arrayList.add(pos.getPosition());
            }
        }
        return arrayList;
    }
    
    private List<BlockPosWithFacing> getNeighbours(final BlockPos blockPos) {
        final ArrayList<BlockPosWithFacing> arrayList = new ArrayList<BlockPosWithFacing>(16);
        arrayList.add(new BlockPosWithFacing(blockPos.north(), EnumFacing.NORTH));
        arrayList.add(new BlockPosWithFacing(blockPos.east(), EnumFacing.EAST));
        arrayList.add(new BlockPosWithFacing(blockPos.south(), EnumFacing.SOUTH));
        arrayList.add(new BlockPosWithFacing(blockPos.west(), EnumFacing.WEST));
        return arrayList;
    }
    
    public static List<EnumFacing> getFacings(final BlockPos blockPos) {
        final ArrayList<EnumFacing> arrayList = new ArrayList<EnumFacing>();
        if (Surround.mc.world == null) {
            return arrayList;
        }
        if (blockPos == null) {
            return arrayList;
        }
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos blockPos2 = blockPos.offset(enumFacing);
            final IBlockState iBlockState = Surround.mc.world.getBlockState(blockPos2);
            if (iBlockState != null && iBlockState.getBlock().canCollideCheck(iBlockState, false) && !iBlockState.getMaterial().isReplaceable()) {
                arrayList.add(enumFacing);
            }
        }
        return arrayList;
    }
    
    private void addBlockToList(final Vec3d vec3d, final List<BlockPos> list) {
        final BlockPos blockPos = new BlockPos(vec3d);
        if (!this.checkBlockPos(blockPos)) {
            return;
        }
        if (list.contains(blockPos)) {
            return;
        }
        list.add(blockPos);
    }
    
    private boolean checkBlockPos(final BlockPos blockPos) {
        return blockPos != null && Surround.mc.world != null && this.blockState.apply(blockPos).getMaterial().isReplaceable();
    }
    
    private Block getBlock(final BlockPos blockPos) {
        return this.blockState.apply(blockPos).getBlock();
    }
    
    private boolean doesCrystalWantToFuckUs(final Vec3d vec3d, final List<BlockPos> list) {
        final Iterator<BlockPos> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (new AxisAlignedBB((BlockPos)iterator.next()).contains(vec3d)) {
                return true;
            }
        }
        return false;
    }
    
    private static double getDistanceToBlock(final BlockPos blockPos) {
        return Surround.mc.player.getDistance(blockPos.getX() + 0.5, (double)blockPos.getY(), blockPos.getZ() + 0.5);
    }
    
    private static IBlockState getBlockState(final Object blockPos) {
        return Surround.mc.world.getBlockState((BlockPos)blockPos);
    }
    
    static {
        THREAD = ThreadUtil.newDaemonScheduledExecutor("SURROUND");
    }
    
    public enum EventMode
    {
        Tick, 
        Update, 
        SyncEvent;
    }
    
    public enum Mode
    {
        High, 
        AntiFacePlace, 
        Dynamic, 
        Cubic, 
        Safe, 
        SemiSafe, 
        Strict, 
        Normal;
    }
    
    public enum BlockMode
    {
        Obsidian, 
        EnderChest;
    }
    
    public enum Switch
    {
        None, 
        Vanilla, 
        Packet, 
        Silent;
    }
    
    public enum SwitchWhen
    {
        Place, 
        RunSurround;
    }
    
    public enum CBTimings
    {
        Sequential, 
        Adaptive;
    }
    
    public enum CbMode
    {
        SurroundBlocks, 
        Area;
    }
    
    public enum CBRotateMode
    {
        Client, 
        Packet, 
        Both;
    }
    
    public enum Rotate
    {
        None, 
        Packet, 
        Silent;
    }
    
    public enum detectEntityMode
    {
        Off, 
        RemoveEntity, 
        SetDead, 
        Both;
    }
    
    public enum ToggleMode
    {
        Never, 
        OffGround, 
        OnComplete, 
        Combo, 
        PositiveYChange, 
        YChange;
    }
}
