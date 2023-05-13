//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.atomic.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class HelperRotation
{
    private static final AtomicInteger ID;
    private final RotationSmoother smoother;
    private final AutoCrystal module;
    
    public HelperRotation(final AutoCrystal module) {
        this.smoother = new RotationSmoother(Thunderhack.rotationManager);
        this.module = module;
    }
    
    public static Runnable wrap(final Runnable runnable) {
        return () -> acquire(runnable);
    }
    
    public static void acquire(final Runnable runnable) {
        try {
            runnable.run();
        }
        catch (Exception ex) {}
    }
    
    public static void startDigging(final BlockPos pos, final EnumFacing facing) {
        Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, facing));
    }
    
    public static void stopDigging(final BlockPos pos, final EnumFacing facing) {
        Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing));
    }
    
    public static WeaknessSwitch antiWeakness(final AutoCrystal module) {
        if (!module.weaknessHelper.isWeaknessed()) {
            return WeaknessSwitch.NONE;
        }
        if (module.antiWeakness.getValue() == AutoCrystal.AntiWeakness.None || (int)module.cooldown.getValue() != 0) {
            return WeaknessSwitch.INVALID;
        }
        return new WeaknessSwitch(DamageUtil.findAntiWeakness(), true);
    }
    
    public RotationFunction forPlacing(final BlockPos pos, final MutableWrapper<Boolean> hasPlaced) {
        final int id = HelperRotation.ID.incrementAndGet();
        final Timer timer = new Timer();
        final MutableWrapper<Boolean> ended = new MutableWrapper<Boolean>(false);
        boolean breaking;
        float[] rotations;
        final MutableWrapper<Boolean> mutableWrapper;
        final Timer timer2;
        final int n;
        double height;
        double height2;
        final EnumFacing[] array;
        int length;
        int i = 0;
        EnumFacing facing;
        Ray ray;
        return (x, y, z, yaw, pitch) -> {
            breaking = false;
            rotations = null;
            if (hasPlaced.get() || (Util.mc.player.getDistanceSq(pos) > 64.0 && pos.distanceSq(x, y, z) > 64.0) || (this.module.autoSwitch.getValue() != AutoCrystal.AutoSwitch.Always && !this.module.switching && !this.module.weaknessHelper.canSwitch() && !InventoryUtil.isHolding(Items.END_CRYSTAL))) {
                if (!mutableWrapper.get()) {
                    mutableWrapper.set(true);
                    timer2.reset();
                }
                if (!(boolean)this.module.attack.getValue() || timer2.passedMs((int)this.module.endRotations.getValue())) {
                    if (n == HelperRotation.ID.get()) {
                        this.module.rotation = null;
                    }
                    return new float[] { yaw, pitch };
                }
                else {
                    breaking = true;
                    height = 1.7 * (float)this.module.height.getValue();
                    rotations = RotationSmoother.getRotations(pos.getX() + 0.5f, pos.getY() + 1 + height, pos.getZ() + 0.5f, x, y, z, Util.mc.player.getEyeHeight());
                }
            }
            else {
                height2 = (double)this.module.placeHeight.getValue();
                if (this.module.smartTrace.getValue()) {
                    EnumFacing.values();
                    length = array.length;
                    while (i < length) {
                        facing = array[i];
                        ray = RayTraceFactory.rayTrace((Entity)Util.mc.player, pos, facing, (IBlockAccess)Util.mc.world, Blocks.OBSIDIAN.getDefaultState(), (double)this.module.traceWidth.getValue());
                        if (ray.isLegit()) {
                            rotations = ray.getRotations();
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                if (rotations == null) {
                    if (this.module.fallbackTrace.getValue()) {
                        rotations = RotationSmoother.getRotations(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, x, y, z, Util.mc.player.getEyeHeight());
                    }
                    else {
                        rotations = RotationSmoother.getRotations(pos.getX() + 0.5, pos.getY() + height2, pos.getZ() + 0.5, x, y, z, Util.mc.player.getEyeHeight());
                    }
                }
            }
            return this.smoother.smoothen(rotations, breaking ? ((double)(float)this.module.angle.getValue()) : ((double)(float)this.module.placeAngle.getValue()));
        };
    }
    
    public RotationFunction forBreaking(final Entity entity, final MutableWrapper<Boolean> attacked) {
        final int id = HelperRotation.ID.incrementAndGet();
        final Timer timer = new Timer();
        final MutableWrapper<Boolean> ended = new MutableWrapper<Boolean>(false);
        final MutableWrapper<Boolean> mutableWrapper;
        final Timer timer2;
        final int n;
        return (x, y, z, yaw, pitch) -> {
            if (Util.mc.player.getDistanceSq(entity) > 64.0) {
                attacked.set(true);
            }
            if (this.module.getTarget() == null) {
                attacked.set(true);
            }
            if (attacked.get()) {
                if (!mutableWrapper.get()) {
                    mutableWrapper.set(true);
                    timer2.reset();
                }
                if (mutableWrapper.get() && timer2.passedMs((int)this.module.endRotations.getValue())) {
                    if (n == HelperRotation.ID.get()) {
                        this.module.rotation = null;
                    }
                    return new float[] { yaw, pitch };
                }
            }
            return this.smoother.getRotations(entity, x, y, z, Util.mc.player.getEyeHeight(), (float)this.module.height.getValue(), (float)this.module.angle.getValue());
        };
    }
    
    public RotationFunction forObby(final PositionData data) {
        Ray ray;
        return (x, y, z, yaw, pitch) -> {
            if (data.getPath().length <= 0) {
                return new float[] { yaw, pitch };
            }
            else {
                ray = data.getPath()[0];
                ray.updateRotations((Entity)Util.mc.player);
                return ray.getRotations();
            }
        };
    }
    
    public Runnable post(final AutoCrystal module, final float damage, final boolean realtime, final BlockPos pos, final MutableWrapper<Boolean> placed, final MutableWrapper<Boolean> liquidBreak) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload           liquidBreak
        //     3: aload_1         /* module */
        //     4: aload           pos
        //     6: aload           placed
        //     8: iload_3         /* realtime */
        //     9: fload_2         /* damage */
        //    10: invokedynamic   BootstrapMethod #4, run:(Lcom/mrzak34/thunderhack/util/phobos/HelperRotation;Lcom/mrzak34/thunderhack/util/phobos/MutableWrapper;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;Lnet/minecraft/util/math/BlockPos;Lcom/mrzak34/thunderhack/util/phobos/MutableWrapper;ZF)Ljava/lang/Runnable;
        //    15: areturn        
        //    Signature:
        //  (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;FZLnet/minecraft/util/math/BlockPos;Lcom/mrzak34/thunderhack/util/phobos/MutableWrapper<Ljava/lang/Boolean;>;Lcom/mrzak34/thunderhack/util/phobos/MutableWrapper<Ljava/lang/Boolean;>;)Ljava/lang/Runnable;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Runnable post(final Entity entity, final MutableWrapper<Boolean> attacked) {
        final WeaknessSwitch w;
        CPacketUseEntity packet;
        AutoCrystal.SwingTime swingTime;
        int lastSlot;
        final WeaknessSwitch weaknessSwitch;
        final AutoCrystal.SwingTime swingTime2;
        final Packet packet2;
        Runnable runnable;
        return () -> {
            w = antiWeakness(this.module);
            if ((!w.needsSwitch() || w.getSlot() != -1) && !EntityUtil.isDead(entity) && (this.module.noRotateNigga(AutoCrystal.ACRotate.Break) || this.module.isNotCheckingRotations() || CalculationMotion.isLegit(entity, new Entity[0]))) {
                packet = new CPacketUseEntity(entity);
                swingTime = (AutoCrystal.SwingTime)this.module.breakSwing.getValue();
                runnable = (() -> {
                    lastSlot = Util.mc.player.inventory.currentItem;
                    if (weaknessSwitch.getSlot() != -1) {
                        switch ((AutoCrystal.CooldownBypass2)this.module.antiWeaknessBypass.getValue()) {
                            case None: {
                                CooldownBypass.None.switchTo(weaknessSwitch.getSlot());
                                break;
                            }
                            case Pick: {
                                CooldownBypass.Pick.switchTo(weaknessSwitch.getSlot());
                                break;
                            }
                            case Slot: {
                                CooldownBypass.Slot.switchTo(weaknessSwitch.getSlot());
                                break;
                            }
                            case Swap: {
                                CooldownBypass.Swap.switchTo(weaknessSwitch.getSlot());
                                break;
                            }
                        }
                    }
                    if (swingTime2 == AutoCrystal.SwingTime.Pre) {
                        this.swing(EnumHand.MAIN_HAND, true);
                    }
                    Util.mc.player.connection.sendPacket(packet2);
                    attacked.set(true);
                    if (swingTime2 == AutoCrystal.SwingTime.Post) {
                        this.swing(EnumHand.MAIN_HAND, true);
                    }
                    if (weaknessSwitch.getSlot() != -1) {
                        switch ((AutoCrystal.CooldownBypass2)this.module.antiWeaknessBypass.getValue()) {
                            case None: {
                                CooldownBypass.None.switchBack(lastSlot, weaknessSwitch.getSlot());
                                break;
                            }
                            case Pick: {
                                CooldownBypass.Pick.switchBack(lastSlot, weaknessSwitch.getSlot());
                                break;
                            }
                            case Slot: {
                                CooldownBypass.Slot.switchBack(lastSlot, weaknessSwitch.getSlot());
                                break;
                            }
                            case Swap: {
                                CooldownBypass.Swap.switchBack(lastSlot, weaknessSwitch.getSlot());
                                break;
                            }
                        }
                    }
                    return;
                });
                if (w.getSlot() != -1) {
                    acquire(runnable);
                }
                else {
                    runnable.run();
                }
                if (this.module.pseudoSetDead.getValue()) {
                    ((IEntity)entity).setPseudoDeadT(true);
                }
                if (this.module.setDead.getValue()) {
                    Thunderhack.setDeadManager.setDead(entity);
                }
            }
        };
    }
    
    public Runnable postBlock(final PositionData data) {
        return this.postBlock(data, -1, (AutoCrystal.Rotate)this.module.obbyRotate.getValue(), null, null);
    }
    
    public Runnable postBlock(final PositionData data, final int preSlot, final AutoCrystal.Rotate rotate, final MutableWrapper<Boolean> placed, final MutableWrapper<Integer> switchBack) {
        int slot;
        EnumHand hand;
        AutoCrystal.PlaceSwing placeSwing;
        int lastSlot;
        final int n;
        final Ray[] array;
        int length;
        int i = 0;
        Ray ray;
        float[] r;
        float[] f;
        final EnumHand enumHand;
        final Ray ray2;
        final AutoCrystal.PlaceSwing placeSwing2;
        EnumHand swingHand;
        return () -> {
            if (!(!data.isValid())) {
                slot = ((preSlot == -1) ? InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) : preSlot);
                if (slot != -1) {
                    hand = ((slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    placeSwing = (AutoCrystal.PlaceSwing)this.module.obbySwing.getValue();
                    acquire(() -> {
                        lastSlot = Util.mc.player.inventory.currentItem;
                        if (switchBack != null) {
                            switchBack.set(lastSlot);
                        }
                        switch ((AutoCrystal.CooldownBypass2)this.module.obsidianBypass.getValue()) {
                            case None: {
                                CooldownBypass.None.switchTo(n);
                                break;
                            }
                            case Pick: {
                                CooldownBypass.Pick.switchTo(n);
                                break;
                            }
                            case Slot: {
                                CooldownBypass.Slot.switchTo(n);
                                break;
                            }
                            case Swap: {
                                CooldownBypass.Swap.switchTo(n);
                                break;
                            }
                        }
                        data.getPath();
                        for (length = array.length; i < length; ++i) {
                            ray = array[i];
                            if (rotate == AutoCrystal.Rotate.Packet && !CalculationMotion.isLegit(ray.getPos(), ray.getFacing())) {
                                Thunderhack.rotationManager.setBlocking(true);
                                r = ray.getRotations();
                                Util.mc.player.connection.sendPacket((Packet)Burrow.rotation(r[0], r[1], Util.mc.player.onGround));
                                Thunderhack.rotationManager.setBlocking(false);
                            }
                            f = RayTraceUtil.hitVecToPlaceVec(ray.getPos(), ray.getResult().hitVec);
                            Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(ray.getPos(), ray.getFacing(), enumHand, f[0], f[1], f[2]));
                            if ((boolean)this.module.setState.getValue() && preSlot == -1) {
                                Util.mc.addScheduledTask(() -> {
                                    if (Util.mc.world != null) {
                                        Util.mc.world.setBlockState(ray2.getPos().offset(ray2.getFacing()), Blocks.OBSIDIAN.getDefaultState());
                                    }
                                    return;
                                });
                            }
                            if (placeSwing2 == AutoCrystal.PlaceSwing.Always) {
                                Swing.Packet.swing(enumHand);
                            }
                        }
                        if (placeSwing2 == AutoCrystal.PlaceSwing.Once) {
                            Swing.Packet.swing(enumHand);
                        }
                        switch ((AutoCrystal.CooldownBypass2)this.module.obsidianBypass.getValue()) {
                            case None: {
                                CooldownBypass.None.switchBack(lastSlot, n);
                                break;
                            }
                            case Pick: {
                                CooldownBypass.Pick.switchBack(lastSlot, n);
                                break;
                            }
                            case Slot: {
                                CooldownBypass.Slot.switchBack(lastSlot, n);
                                break;
                            }
                            case Swap: {
                                CooldownBypass.Swap.switchBack(lastSlot, n);
                                break;
                            }
                        }
                        if (placed != null) {
                            placed.set(true);
                        }
                        return;
                    });
                    swingHand = this.resolvehand3();
                    if (swingHand != null) {
                        Swing.Client.swing(swingHand);
                    }
                }
            }
        };
    }
    
    public Runnable breakBlock(final int toolSlot, final MutableWrapper<Boolean> placed, final MutableWrapper<Integer> lastSlot, final int[] order, final Ray... positions) {
        final IndexOutOfBoundsException ex;
        int length;
        int j = 0;
        int i;
        Ray ray;
        BlockPos pos;
        return wrap(() -> {
            if (order.length != positions.length) {
                new IndexOutOfBoundsException("Order length: " + order.length + ", Positions length: " + positions.length);
                throw ex;
            }
            else if (!(!placed.get())) {
                switch ((AutoCrystal.CooldownBypass2)this.module.mineBypass.getValue()) {
                    case None: {
                        CooldownBypass.None.switchTo(toolSlot);
                        break;
                    }
                    case Pick: {
                        CooldownBypass.Pick.switchTo(toolSlot);
                        break;
                    }
                    case Slot: {
                        CooldownBypass.Slot.switchTo(toolSlot);
                        break;
                    }
                    case Swap: {
                        CooldownBypass.Swap.switchTo(toolSlot);
                        break;
                    }
                }
                for (length = order.length; j < length; ++j) {
                    i = order[j];
                    ray = positions[i];
                    pos = ray.getPos().offset(ray.getFacing());
                    startDigging(pos, ray.getFacing().getOpposite());
                    stopDigging(pos, ray.getFacing().getOpposite());
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
                switch ((AutoCrystal.CooldownBypass2)this.module.mineBypass.getValue()) {
                    case None: {
                        CooldownBypass.None.switchBack((int)lastSlot.get(), toolSlot);
                        break;
                    }
                    case Pick: {
                        CooldownBypass.Pick.switchBack((int)lastSlot.get(), toolSlot);
                        break;
                    }
                    case Slot: {
                        CooldownBypass.Slot.switchBack((int)lastSlot.get(), toolSlot);
                        break;
                    }
                    case Swap: {
                        CooldownBypass.Swap.switchBack((int)lastSlot.get(), toolSlot);
                        break;
                    }
                }
            }
        });
    }
    
    public void swing(final EnumHand hand, final boolean breaking) {
        Swing.Packet.swing(hand);
        final EnumHand swingHand = breaking ? this.resolvehand() : this.resolvehand2();
        if (swingHand != null) {
            Swing.Client.swing(swingHand);
        }
    }
    
    EnumHand resolvehand() {
        switch ((AutoCrystal.SwingType)this.module.swing.getValue()) {
            case None: {
                return null;
            }
            case OffHand: {
                return EnumHand.OFF_HAND;
            }
            case MainHand: {
                return EnumHand.MAIN_HAND;
            }
            default: {
                return EnumHand.MAIN_HAND;
            }
        }
    }
    
    EnumHand resolvehand2() {
        switch ((AutoCrystal.SwingType)this.module.placeHand.getValue()) {
            case None: {
                return null;
            }
            case OffHand: {
                return EnumHand.OFF_HAND;
            }
            case MainHand: {
                return EnumHand.MAIN_HAND;
            }
            default: {
                return EnumHand.MAIN_HAND;
            }
        }
    }
    
    EnumHand resolvehand3() {
        switch ((AutoCrystal.SwingType)this.module.obbyHand.getValue()) {
            case None: {
                return null;
            }
            case OffHand: {
                return EnumHand.OFF_HAND;
            }
            case MainHand: {
                return EnumHand.MAIN_HAND;
            }
            default: {
                return EnumHand.MAIN_HAND;
            }
        }
    }
    
    static {
        ID = new AtomicInteger();
    }
}
