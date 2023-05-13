//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.*;
import java.util.function.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.*;

public abstract class AbstractCalculation<T extends CrystalData> extends Finishable
{
    protected final Set<BlockPos> blackList;
    protected final List<Entity> entities;
    protected final AutoCrystal module;
    protected final List<EntityPlayer> raw;
    protected double maxY;
    protected List<EntityPlayer> friends;
    protected List<EntityPlayer> enemies;
    protected List<EntityPlayer> players;
    protected List<EntityPlayer> all;
    protected BreakData<T> breakData;
    protected PlaceData placeData;
    protected boolean scheduling;
    protected boolean attacking;
    protected boolean noPlace;
    protected boolean noBreak;
    protected boolean rotating;
    protected boolean placing;
    protected boolean fallback;
    protected int motionID;
    protected int count;
    protected int shieldCount;
    protected int shieldRange;
    
    public AbstractCalculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        this.maxY = Double.MAX_VALUE;
        this.noPlace = false;
        this.noBreak = false;
        this.motionID = module.motionID.get();
        if (blackList.length == 0) {
            this.blackList = new EmptySet<BlockPos>();
        }
        else {
            this.blackList = new HashSet<BlockPos>();
            for (final BlockPos pos : blackList) {
                if (pos != null) {
                    this.blackList.add(pos);
                }
            }
        }
        this.module = module;
        this.entities = entities;
        this.raw = players;
    }
    
    public AbstractCalculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        this(module, entities, players, blackList);
        this.noPlace = breakOnly;
        this.noBreak = noBreak;
    }
    
    protected abstract IBreakHelper<T> getBreakHelper();
    
    @Override
    protected void execute() {
        try {
            if (this.module.clearPost.getValue()) {
                this.module.post.clear();
            }
            this.runCalc();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void runCalc() {
        if (this.check()) {
            return;
        }
        if ((boolean)this.module.forceAntiTotem.getValue() && (boolean)this.module.antiTotem.getValue() && this.checkForceAntiTotem()) {
            return;
        }
        final float minDamage = this.module.getMinDamage();
        if ((boolean)this.module.focusRotations.getValue() && !this.module.noRotateNigga(AutoCrystal.ACRotate.Break) && this.focusBreak()) {
            return;
        }
        this.module.focus = null;
        if (this.breakData == null && this.breakCheck()) {
            this.setCount(this.breakData = this.getBreakHelper().getData(this.getBreakDataSet(), this.entities, this.all, this.friends), minDamage);
            if (this.evaluate(this.breakData)) {
                return;
            }
        }
        else if (this.module.multiPlaceCalc.getValue()) {
            if (this.breakData != null) {
                this.setCount(this.breakData, minDamage);
                this.breakData = null;
            }
            else {
                final BreakData<T> onlyForCountData = this.getBreakHelper().getData(new ArrayList<T>(5), this.entities, this.all, this.friends);
                this.setCount(onlyForCountData, minDamage);
            }
        }
        if (this.placeCheck()) {
            this.placeData = this.module.placeHelper.getData(this.all, this.players, this.enemies, this.friends, this.entities, minDamage, this.blackList, this.maxY);
            if (this.place(this.placeData)) {
                final boolean passed = this.module.obbyCalcTimer.passedMs((int)this.module.obbyCalc.getValue());
                if ((this.obbyCheck() && passed && this.placeObby(this.placeData, null)) || (boolean)this.module.basePlaceOnly.getValue()) {
                    return;
                }
                if (this.preSpecialCheck() && (!(boolean)this.module.requireOnGround.getValue() || Util.mc.player.onGround) && (boolean)this.module.interruptSpeedmine.getValue() && (!(boolean)this.module.pickaxeOnly.getValue() || Util.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) && this.module.liquidTimer.passedMs((int)this.module.liqDelay.getValue()) && ((boolean)this.module.lava.getValue() || (boolean)this.module.water.getValue())) {
                    final MineSlots mineSlots = HelperLiquids.getSlots((boolean)this.module.requireOnGround.getValue());
                    if (mineSlots.getBlockSlot() == -1 || mineSlots.getDamage() < 1.0f) {
                        return;
                    }
                    final PlaceData liquidData = this.module.liquidHelper.calculate(this.module.placeHelper, this.placeData, this.friends, this.all, (float)this.module.minDamage.getValue());
                    final boolean attackingBefore = this.attacking;
                    if (this.placeNoAntiTotem(liquidData, mineSlots) && attackingBefore == this.attacking && (boolean)this.module.liquidObby.getValue() && this.obbyCheck() && passed) {
                        this.placeObby(this.placeData, mineSlots);
                    }
                }
            }
        }
    }
    
    protected void setCount(final BreakData<T> breakData, final float minDmg) {
        this.shieldCount = breakData.getShieldCount();
        if (this.module.multiPlaceMinDmg.getValue()) {
            this.count = (int)breakData.getData().stream().filter(d -> (boolean)this.module.countDeadCrystals.getValue() || !EntityUtil.isDead(d.getCrystal())).filter(d -> d.getDamage() > minDmg).count();
            return;
        }
        this.count = (int)breakData.getData().stream().filter(d -> (boolean)this.module.countDeadCrystals.getValue() || !EntityUtil.isDead(d.getCrystal())).count();
    }
    
    protected boolean evaluate(final BreakData<T> breakData) {
        final boolean shouldDanger = this.module.shouldDanger();
        boolean slowReset = !shouldDanger;
        AutoCrystal.BreakValidity validity;
        if (this.breakData.getAntiTotem() != null && (validity = HelperUtil.isValid(this.module, this.breakData.getAntiTotem())) != AutoCrystal.BreakValidity.INVALID) {
            this.attack(this.breakData.getAntiTotem(), validity);
            this.module.breakTimer.reset((int)this.module.breakDelay.getValue());
            this.module.antiTotemHelper.setTarget(null);
            this.module.antiTotemHelper.setTargetPos(null);
        }
        else {
            final int packets = (int)(this.module.noRotateNigga(AutoCrystal.ACRotate.Break) ? this.module.packets.getValue() : 1);
            T firstRotation = null;
            final List<T> valids = new ArrayList<T>(packets);
            for (final T data : this.breakData.getData()) {
                if (EntityUtil.isDead(data.getCrystal())) {
                    continue;
                }
                validity = HelperUtil.isValid(this.module, data.getCrystal());
                if (validity == AutoCrystal.BreakValidity.VALID && valids.size() < packets) {
                    valids.add(data);
                }
                else {
                    if (validity != AutoCrystal.BreakValidity.ROTATIONS || firstRotation != null) {
                        continue;
                    }
                    firstRotation = data;
                }
            }
            final int slowDelay = (int)this.module.slowBreakDelay.getValue();
            final float slow = (float)this.module.slowBreakDamage.getValue();
            if (valids.isEmpty()) {
                if (firstRotation != null && (this.module.shouldDanger() || !(slowReset = (firstRotation.getDamage() <= slow)) || this.module.breakTimer.passed(slowDelay))) {
                    this.attack(firstRotation.getCrystal(), AutoCrystal.BreakValidity.ROTATIONS);
                }
            }
            else {
                for (final T valid : valids) {
                    final boolean high = valid.getDamage() > (float)this.module.slowBreakDamage.getValue();
                    if (high || shouldDanger || (this.module.breakTimer.passed((int)this.module.slowBreakDelay.getValue()) && valid.getDamage() >= (float)this.module.minBreakDamage.getValue())) {
                        slowReset = (slowReset && !high);
                        this.attack(valid.getCrystal(), AutoCrystal.BreakValidity.VALID);
                    }
                }
            }
        }
        if (this.attacking) {
            this.module.breakTimer.reset(slowReset ? ((long)(int)this.module.slowBreakDelay.getValue()) : ((long)(int)this.module.breakDelay.getValue()));
        }
        return this.rotating && !this.module.noRotateNigga(AutoCrystal.ACRotate.Place);
    }
    
    protected boolean breakCheck() {
        return (boolean)this.module.attack.getValue() && !this.noBreak && Thunderhack.switchManager.getLastSwitch() >= (int)this.module.cooldown.getValue() && this.module.breakTimer.passed((int)this.module.breakDelay.getValue());
    }
    
    protected boolean placeCheck() {
        if (this.module.sequentialHelper.isBlockingPlacement()) {
            return false;
        }
        if (this.module.damageSync.getValue()) {
            final Confirmer c = this.module.damageSyncHelper.getConfirmer();
            if (c.isValid() && (!c.isPlaceConfirmed((int)this.module.placeConfirm.getValue()) || !c.isBreakConfirmed((int)this.module.breakConfirm.getValue())) && c.isValid() && (boolean)this.module.preSynCheck.getValue()) {
                return false;
            }
        }
        return this.count < (int)this.module.multiPlace.getValue() && Thunderhack.switchManager.getLastSwitch() >= (int)this.module.placeCoolDown.getValue() && (boolean)this.module.place.getValue() && (!this.attacking || (boolean)this.module.multiTask.getValue()) && (!this.rotating || this.module.noRotateNigga(AutoCrystal.ACRotate.Place)) && this.module.placeTimer.passed((int)this.module.placeDelay.getValue()) && !this.noPlace;
    }
    
    protected boolean obbyCheck() {
        return this.preSpecialCheck() && (boolean)this.module.obsidian.getValue() && this.module.obbyTimer.passedMs((int)this.module.obbyDelay.getValue());
    }
    
    protected boolean preSpecialCheck() {
        return !this.placing && this.placeData != null && (this.placeData.getTarget() != null || this.module.targetMode.getValue() == AutoCrystal.Target.Damage) && !this.fallback;
    }
    
    protected boolean check() {
        if ((!(boolean)this.module.spectator.getValue() && Util.mc.player.isSpectator()) || this.raw == null || this.entities == null || ((boolean)this.module.stopWhenEating.getValue() && this.module.isEating()) || ((boolean)this.module.stopWhenMining.getValue() && this.module.isMining())) {
            return true;
        }
        this.setFriendsAndEnemies();
        return this.all.isEmpty() || (!this.module.shouldcalcN() && this.module.autoSwitch.getValue() != AutoCrystal.AutoSwitch.Always && !this.module.weaknessHelper.canSwitch() && !this.module.switching) || (this.module.weaknessHelper.isWeaknessed() && this.module.antiWeakness.getValue() == AutoCrystal.AntiWeakness.None);
    }
    
    protected void setFriendsAndEnemies() {
        if (this.module.isSuicideModule()) {
            this.enemies = new ArrayList<EntityPlayer>(Arrays.asList(RotationUtil.getRotationPlayer()));
            this.players = new ArrayList<EntityPlayer>(0);
            this.friends = new ArrayList<EntityPlayer>(0);
            this.all = this.enemies;
            return;
        }
        final List<List<EntityPlayer>> split = CollectionUtil.split(this.raw, p -> p == null || EntityUtil.isDead(p) || ((EntityPlayer)p).equals((Object)Util.mc.player) || ((EntityPlayer)p).getDistanceSq((Entity)Util.mc.player) > MathUtil.square((double)(float)this.module.targetRange.getValue()), Thunderhack.friendManager::isFriend, Thunderhack.friendManager::isEnemy);
        this.friends = split.get(1);
        this.enemies = split.get(2);
        this.players = split.get(3);
        this.all = new ArrayList<EntityPlayer>(this.enemies.size() + this.players.size());
        this.shieldRange += (int)this.enemies.stream().peek(e -> this.all.add(e)).filter(e -> e.getDistanceSq((Entity)Util.mc.player) <= MathUtil.square((double)(float)this.module.shieldRange.getValue())).count();
        this.shieldRange += (int)this.players.stream().peek(e -> this.all.add(e)).filter(e -> e.getDistanceSq((Entity)Util.mc.player) <= MathUtil.square((double)(float)this.module.shieldRange.getValue())).count();
        if (this.module.yCalc.getValue()) {
            this.maxY = Double.MIN_VALUE;
            for (final EntityPlayer player : this.all) {
                if (player.posY > this.maxY) {
                    this.maxY = player.posY;
                }
            }
        }
    }
    
    protected boolean attack(final Entity entity, final AutoCrystal.BreakValidity validity) {
        if (this.module.basePlaceOnly.getValue()) {
            return validity != AutoCrystal.BreakValidity.INVALID;
        }
        this.module.setCrystal(entity);
        switch (validity) {
            case VALID: {
                if (!this.module.weaknessHelper.isWeaknessed()) {
                    if (this.module.breakSwing.getValue() == AutoCrystal.SwingTime.Pre) {
                        Swing.Packet.swing(EnumHand.MAIN_HAND);
                    }
                    Util.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                    if (this.module.pseudoSetDead.getValue()) {
                        ((IEntity)entity).setPseudoDeadT(true);
                    }
                    else if (this.module.setDead.getValue()) {
                        Thunderhack.setDeadManager.setDead(entity);
                    }
                    if (this.module.breakSwing.getValue() == AutoCrystal.SwingTime.Post) {
                        Swing.Packet.swing(EnumHand.MAIN_HAND);
                    }
                    Swing.Client.swing(EnumHand.MAIN_HAND);
                    this.attacking = true;
                    if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Break)) {
                        this.module.rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<Boolean>(true));
                    }
                    return true;
                }
                if (this.module.antiWeakness.getValue() == AutoCrystal.AntiWeakness.None) {
                    return false;
                }
                final Runnable r = this.module.rotationHelper.post(entity, new MutableWrapper<Boolean>(false));
                r.run();
                this.attacking = true;
                if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Break)) {
                    this.module.rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<Boolean>(true));
                }
                return true;
            }
            case ROTATIONS: {
                this.attacking = true;
                this.rotating = true;
                final MutableWrapper<Boolean> attacked = new MutableWrapper<Boolean>(false);
                final Runnable post = this.module.rotationHelper.post(entity, attacked);
                final RotationFunction function = this.module.rotationHelper.forBreaking(entity, attacked);
                if ((boolean)this.module.multiThread.getValue() && this.module.rotationThread.getValue() == AutoCrystal.RotationThread.Cancel && this.module.rotationCanceller.setRotations(function) && HelperUtil.isValid(this.module, entity) == AutoCrystal.BreakValidity.VALID) {
                    this.rotating = false;
                    post.run();
                }
                else {
                    this.module.rotation = function;
                    this.module.post.add(post);
                }
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    protected boolean checkForceAntiTotem() {
        final BlockPos forcePos = this.module.forceHelper.getPos();
        if (forcePos != null && this.module.forceHelper.isForcing((boolean)this.module.syncForce.getValue())) {
            for (final Entity entity : this.entities) {
                if (entity instanceof EntityEnderCrystal && !EntityUtil.isDead(entity) && entity.getEntityBoundingBox().intersects(new AxisAlignedBB(forcePos.up()))) {
                    this.attack(entity, HelperUtil.isValid(this.module, entity));
                    return true;
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean place(final PlaceData data) {
        AntiTotemData antiTotem = null;
        final boolean god = (boolean)this.module.godAntiTotem.getValue() && this.module.idHelper.isSafe(this.raw, (boolean)this.module.holdingCheck.getValue(), (boolean)this.module.toolCheck.getValue());
        for (final AntiTotemData antiTotemData : data.getAntiTotem()) {
            if (!antiTotemData.getCorresponding().isEmpty() && !antiTotemData.isBlocked()) {
                final BlockPos pos = antiTotemData.getPos();
                final Entity entity = (Entity)new EntityEnderCrystal((World)Util.mc.world, (double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
                if (god) {
                    for (final PositionData positionData : antiTotemData.getCorresponding()) {
                        if (positionData.isBlocked()) {
                            continue;
                        }
                        final BlockPos up = positionData.getPos().up();
                        final double y = this.module.newVerEntities.getValue() ? 1.0 : 2.0;
                        if (entity.getEntityBoundingBox().intersects(new AxisAlignedBB((double)up.getX(), (double)up.getY(), (double)up.getZ(), up.getX() + 1.0, up.getY() + y, up.getZ() + 1.0))) {
                            continue;
                        }
                        if ((boolean)this.module.totemSync.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
                            return false;
                        }
                        this.module.noGod = true;
                        this.module.antiTotemHelper.setTargetPos(antiTotemData.getPos());
                        final EntityPlayer player = antiTotemData.getFirstTarget();
                        Command.sendMessage("Attempting God-AntiTotem: " + ((player == null) ? "null" : player.getName()));
                        this.place(antiTotemData, player, false, false, false);
                        this.module.idHelper.attack((AutoCrystal.SwingTime)this.module.breakSwing.getValue(), (AutoCrystal.PlaceSwing)this.module.godSwing.getValue(), 1, (int)this.module.idPackets.getValue(), 0);
                        this.place(positionData, player, false, false, false);
                        this.module.idHelper.attack((AutoCrystal.SwingTime)this.module.breakSwing.getValue(), (AutoCrystal.PlaceSwing)this.module.godSwing.getValue(), 2, (int)this.module.idPackets.getValue(), 0);
                        this.module.breakTimer.reset((int)this.module.breakDelay.getValue());
                        return this.module.noGod = false;
                    }
                }
                if (antiTotem != null) {
                    continue;
                }
                antiTotem = antiTotemData;
                if (!god) {
                    break;
                }
                continue;
            }
        }
        if (antiTotem == null) {
            if ((boolean)this.module.forceAntiTotem.getValue() && (boolean)this.module.antiTotem.getValue() && this.module.forceTimer.passedMs((int)this.module.attempts.getValue())) {
                for (final Map.Entry<EntityPlayer, ForceData> entry : data.getForceData().entrySet()) {
                    final ForceData forceData = entry.getValue();
                    final PositionData first = forceData.getForceData().stream().findFirst().orElse(null);
                    if (first != null && forceData.hasPossibleAntiTotem()) {
                        if (!forceData.hasPossibleHighDamage()) {
                            continue;
                        }
                        if ((boolean)this.module.syncForce.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
                            return false;
                        }
                        this.module.forceHelper.setSync(first.getPos(), (boolean)this.module.newVerEntities.getValue());
                        this.place(first, entry.getKey(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), this.rotating || this.scheduling, (boolean)this.module.forceSlow.getValue());
                        this.module.forceTimer.reset();
                        return false;
                    }
                }
            }
            return this.placeNoAntiTotem(data, null);
        }
        final EntityPlayer player2 = antiTotem.getFirstTarget();
        this.module.setTarget(player2);
        if ((boolean)this.module.totemSync.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
            return false;
        }
        Command.sendMessage("Attempting AntiTotem: " + ((player2 == null) ? "null" : player2.getName()));
        this.module.antiTotemHelper.setTargetPos(antiTotem.getPos());
        this.place(antiTotem, player2, !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), this.rotating || this.scheduling, false);
        return false;
    }
    
    protected boolean placeNoAntiTotem(final PlaceData data, final MineSlots liquid) {
        float maxBlockedDamage = 0.0f;
        PositionData firstData = null;
        for (final PositionData d : data.getData()) {
            if (!(boolean)this.module.override.getValue() || d.getSelfDamage() <= (float)this.module.maxSelfPlace.getValue() || d.getMaxDamage() >= d.getHealth()) {
                if ((boolean)this.module.efficientPlacements.getValue() && d.getMaxDamage() < d.getSelfDamage()) {
                    if (!(boolean)this.module.override.getValue()) {
                        continue;
                    }
                    if (d.getMaxDamage() < d.getHealth()) {
                        continue;
                    }
                }
                if (!d.isBlocked()) {
                    firstData = d;
                    break;
                }
                if (maxBlockedDamage >= d.getMaxDamage()) {
                    continue;
                }
                maxBlockedDamage = d.getMaxDamage();
            }
        }
        boolean isRayBypass = false;
        if ((boolean)this.module.rayTraceBypass.getValue() && (boolean)this.module.forceBypass.getValue() && firstData == null) {
            for (final PositionData d2 : data.getRaytraceData()) {
                if (!d2.isBlocked()) {
                    firstData = d2;
                    break;
                }
                if (maxBlockedDamage >= d2.getMaxDamage()) {
                    continue;
                }
                maxBlockedDamage = d2.getMaxDamage();
                isRayBypass = true;
            }
        }
        if (this.breakData != null && !this.attacking) {
            final Entity fallback = this.breakData.getFallBack();
            if ((boolean)this.module.fallBack.getValue() && (!isRayBypass || (boolean)this.module.rayBypassFallback.getValue()) && this.breakData.getFallBackDmg() < (float)this.module.fallBackDmg.getValue() && fallback != null && maxBlockedDamage != 0.0f && (firstData == null || maxBlockedDamage - firstData.getMaxDamage() >= (float)this.module.fallBackDiff.getValue())) {
                this.attack(fallback, HelperUtil.isValid(this.module, fallback));
                return false;
            }
        }
        if (firstData != null && firstData.isRaytraceBypass()) {
            this.module.setRenderPos(firstData.getPos(), MathUtil.round(firstData.getMaxDamage(), 1) + " (RB)");
            this.module.setBypassPos(firstData.getPos());
            return false;
        }
        if (firstData != null && !this.module.damageSyncHelper.isSyncing(firstData.getMaxDamage(), (boolean)this.module.damageSync.getValue()) && (liquid == null || (float)this.module.minDamage.getValue() <= firstData.getMaxDamage())) {
            boolean slow = false;
            if (firstData.getMaxDamage() <= (float)this.module.slowPlaceDmg.getValue() && !this.module.shouldDanger()) {
                if (!this.module.placeTimer.passed((int)this.module.slowPlaceDelay.getValue())) {
                    return !this.module.damageSyncHelper.isSyncing(0.0f, (boolean)this.module.damageSync.getValue());
                }
                slow = true;
            }
            MutableWrapper<Boolean> liquidBreak = null;
            if (liquid != null) {
                liquidBreak = this.placeAndBreakLiquid(firstData, liquid, this.rotating);
            }
            this.place(firstData, firstData.getTarget(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), this.rotating || this.scheduling, slow, slow ? firstData.getMaxDamage() : Float.MAX_VALUE, liquidBreak);
            return false;
        }
        final Optional<PositionData> shield;
        if ((boolean)this.module.shield.getValue() && this.module.shieldTimer.passedMs((int)this.module.shieldDelay.getValue()) && (this.shieldCount < (int)this.module.shieldCount.getValue() || !this.attacking) && (shield = data.getShieldData().stream().findFirst()).isPresent() && this.placeData.getHighestSelfDamage() >= (float)this.module.shieldMinDamage.getValue() && this.shieldRange > 0) {
            this.place(shield.get(), shield.get().getTarget(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), this.rotating || this.scheduling, false, Float.MAX_VALUE, null, true);
            this.module.shieldTimer.reset();
            return false;
        }
        return !this.module.damageSyncHelper.isSyncing(0.0f, (boolean)this.module.damageSync.getValue());
    }
    
    protected void place(final PositionData data, final EntityPlayer target, final boolean rotate, final boolean schedule, final boolean resetSlow) {
        this.place(data, target, rotate, schedule, resetSlow, Float.MAX_VALUE, null);
    }
    
    protected void place(final PositionData data, final EntityPlayer target, final boolean rotate, final boolean schedule, final boolean resetSlow, final float damage, final MutableWrapper<Boolean> liquidBreak) {
        this.place(data, target, rotate, schedule, resetSlow, damage, liquidBreak, false);
    }
    
    protected void place(final PositionData data, final EntityPlayer target, final boolean rotate, final boolean schedule, final boolean resetSlow, final float damage, final MutableWrapper<Boolean> liquidBreak, final boolean shield) {
        if (this.module.basePlaceOnly.getValue()) {
            return;
        }
        if (liquidBreak != null) {
            this.module.liquidTimer.reset();
        }
        this.module.placeTimer.reset(resetSlow ? ((long)(int)this.module.slowPlaceDelay.getValue()) : ((long)(int)this.module.placeDelay.getValue()));
        final BlockPos pos = data.getPos();
        final BlockPos crystalPos = new BlockPos((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
        this.module.placed.put(crystalPos, new CrystalTimeStamp(damage, shield));
        this.module.damageSyncHelper.setSync(pos, data.getMaxDamage(), (boolean)this.module.newVerEntities.getValue());
        this.module.setTarget(target);
        final boolean realtime = (boolean)this.module.realtime.getValue();
        if (!realtime) {
            this.module.setRenderPos(pos, data.getMaxDamage());
        }
        final MutableWrapper<Boolean> hasPlaced = new MutableWrapper<Boolean>(false);
        if (InventoryUtil.isHolding(Items.END_CRYSTAL) || this.module.autoSwitch.getValue() == AutoCrystal.AutoSwitch.Always || this.module.autoSwitch.getValue() != AutoCrystal.AutoSwitch.Bind || this.module.switching) {}
        final Runnable post = this.module.rotationHelper.post(this.module, data.getMaxDamage(), realtime, pos, hasPlaced, liquidBreak);
        if (rotate) {
            final RotationFunction function = this.module.rotationHelper.forPlacing(pos, hasPlaced);
            if (this.module.rotationCanceller.setRotations(function)) {
                this.module.runPost();
                post.run();
                if ((boolean)this.module.attack.getValue() && hasPlaced.get()) {
                    this.module.rotation = function;
                }
                return;
            }
            this.module.rotation = function;
        }
        if (schedule || !this.placeCheckPre(pos)) {
            this.module.post.add(post);
        }
        else {
            post.run();
        }
    }
    
    protected boolean placeObby(final PlaceData data, final MineSlots liquid) {
        final PositionData bestData = this.module.obbyHelper.findBestObbyData((liquid != null) ? data.getLiquidObby() : data.getAllObbyData(), this.all, this.friends, this.entities, data.getTarget(), (boolean)this.module.newVer.getValue());
        this.module.obbyCalcTimer.reset();
        if (bestData != null && bestData.getMaxDamage() > (float)this.module.obbyMinDmg.getValue()) {
            this.module.setTarget(bestData.getTarget());
            if (this.module.obbyRotate.getValue() != AutoCrystal.Rotate.None && !this.rotating && bestData.getPath().length > 0) {
                this.module.rotation = this.module.rotationHelper.forObby(bestData);
                this.rotating = true;
            }
            final Runnable r = this.module.rotationHelper.postBlock(bestData);
            if (!this.rotating) {
                r.run();
            }
            else {
                this.module.post.add(r);
            }
            if (liquid != null) {
                this.placeAndBreakLiquid(bestData, liquid, this.rotating);
            }
            this.place(bestData, bestData.getTarget(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), this.rotating || this.scheduling, false);
            this.module.obbyTimer.reset();
            return true;
        }
        return false;
    }
    
    @Override
    public void setFinished(final boolean finished) {
        if ((boolean)this.module.multiThread.getValue() && (boolean)this.module.smartPost.getValue() && this.module.motionID.get() != this.motionID) {
            this.module.runPost();
        }
        super.setFinished(finished);
        if (finished) {
            synchronized (this.module) {
                this.module.notifyAll();
            }
        }
    }
    
    protected boolean placeCheckPre(final BlockPos pos) {
        final double x = Thunderhack.positionManager.getX();
        final double y = Thunderhack.positionManager.getY() + (this.module.placeRangeEyes.getValue() ? RotationUtil.getRotationPlayer().getEyeHeight() : 0.0f);
        final double z = Thunderhack.positionManager.getZ();
        if ((this.module.placeRangeCenter.getValue() ? pos.distanceSqToCenter(x, y, z) : pos.distanceSq(x, y, z)) >= MathUtil.square((double)(float)this.module.placeRange.getValue())) {
            return false;
        }
        if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Place) && !this.module.isNotCheckingRotations()) {
            final RayTraceResult result = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.world);
            if (result == null || !result.getBlockPos().equals((Object)pos)) {
                return false;
            }
        }
        if (pos.distanceSqToCenter(x, y, z) < MathUtil.square((double)(float)this.module.placeTrace.getValue())) {
            return true;
        }
        if ((boolean)this.module.rayTraceBypass.getValue() && !Visible.INSTANCE.check(pos, (int)this.module.bypassTicks.getValue())) {
            return true;
        }
        RayTraceResult result;
        if (this.module.isNotCheckingRotations()) {
            final float[] rotations = com.mrzak34.thunderhack.util.phobos.RotationUtil.getRotationsToTopMiddle(pos);
            result = com.mrzak34.thunderhack.util.phobos.RotationUtil.rayTraceWithYP(pos, (IBlockAccess)Util.mc.world, rotations[0], rotations[1], (b, p) -> true);
        }
        else {
            result = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.world, (b, p) -> true);
        }
        if (result != null && !result.getBlockPos().equals((Object)pos)) {
            return (boolean)this.module.ignoreNonFull.getValue() && !Util.mc.world.getBlockState(result.getBlockPos()).getBlock().isFullBlock(Util.mc.world.getBlockState(result.getBlockPos()));
        }
        return result != null && result.getBlockPos().equals((Object)pos);
    }
    
    protected MutableWrapper<Boolean> placeAndBreakLiquid(final PositionData data, final MineSlots liquid, final boolean rotating) {
        final boolean newVer = (boolean)this.module.newVer.getValue();
        final boolean absorb = (boolean)this.module.absorb.getValue();
        final List<Ray> path = new ArrayList<Ray>((newVer ? 1 : 2) + (absorb ? 1 : 0));
        final BlockStateHelper access = new BlockStateHelper();
        path.add(RayTraceFactory.rayTrace(data.getFrom(), data.getPos(), EnumFacing.UP, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
        final BlockPos up = data.getPos().up();
        access.addBlockState(up, Blocks.NETHERRACK.getDefaultState());
        final IBlockState upState = Util.mc.world.getBlockState(up);
        int[] order;
        if (!newVer && upState.getMaterial().isLiquid()) {
            path.add(RayTraceFactory.rayTrace(data.getFrom(), up, EnumFacing.UP, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
            access.addBlockState(up.up(), Blocks.NETHERRACK.getDefaultState());
            order = new int[] { 0, 1 };
        }
        else {
            order = new int[] { 0 };
        }
        if (absorb) {
            BlockPos absorpPos = up;
            EnumFacing absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, (IBlockAccess)access, (float)this.module.placeRange.getValue());
            if (absorbFacing == null && !newVer) {
                absorpPos = up.up();
                absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, (IBlockAccess)access, (float)this.module.placeRange.getValue());
            }
            if (absorbFacing != null) {
                path.add(RayTraceFactory.rayTrace(data.getFrom(), absorpPos, absorbFacing, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
                order = ((order.length == 2) ? new int[] { 2, 1, 0 } : new int[] { 1, 0 });
            }
        }
        final Ray[] pathArray = path.toArray(new Ray[0]);
        data.setPath(pathArray);
        data.setValid(true);
        final MutableWrapper<Boolean> placed = new MutableWrapper<Boolean>(false);
        final MutableWrapper<Integer> postBlock = new MutableWrapper<Integer>(-1);
        final Runnable r = this.module.rotationHelper.postBlock(data, liquid.getBlockSlot(), (AutoCrystal.Rotate)this.module.liqRotate.getValue(), placed, postBlock);
        final Runnable b = this.module.rotationHelper.breakBlock(liquid.getToolSlot(), placed, postBlock, order, pathArray);
        Runnable a = null;
        if (this.module.setAir.getValue()) {
            final Iterator<Ray> iterator;
            Ray ray;
            a = (() -> {
                path.iterator();
                while (iterator.hasNext()) {
                    ray = iterator.next();
                    Util.mc.world.setBlockState(ray.getPos().offset(ray.getFacing()), Blocks.AIR.getDefaultState());
                }
                return;
            });
        }
        if (rotating) {
            synchronized (this.module.post) {
                this.module.post.add(r);
                this.module.post.add(b);
                if (a != null) {
                    Util.mc.addScheduledTask(a);
                }
            }
        }
        else {
            r.run();
            b.run();
            if (a != null) {
                Util.mc.addScheduledTask(a);
            }
        }
        return placed;
    }
    
    protected boolean focusBreak() {
        final Entity focus = this.module.focus;
        if (focus == null) {
            return false;
        }
        if (EntityUtil.isDead(focus) || (!this.module.rangeHelper.isCrystalInRangeOfLastPosition(focus) && !this.module.rangeHelper.isCrystalInRange(focus))) {
            this.module.focus = null;
            return false;
        }
        final double exponent = (double)this.module.focusExponent.getValue();
        this.breakData = this.getBreakHelper().getData((Collection<T>)(((boolean)this.module.focusAngleCalc.getValue() && exponent != 0.0) ? RotationComparator.asSet(exponent, (double)this.module.focusDiff.getValue()) : this.getBreakDataSet()), this.entities, this.all, this.friends);
        final List<T> focusList = new ArrayList<T>(1);
        final BreakData<T> focusData = this.getBreakHelper().newData(focusList);
        T minData = null;
        double minAngle = Double.MAX_VALUE;
        for (final T data : this.breakData.getData()) {
            if (EntityUtil.isDead(data.getCrystal())) {
                continue;
            }
            if (data.hasCachedRotations() && data.getAngle() < minAngle) {
                minAngle = data.getAngle();
                minData = data;
            }
            if (!data.getCrystal().equals((Object)focus)) {
                continue;
            }
            if (data.getSelfDmg() > (float)this.module.maxSelfBreak.getValue() || data.getDamage() < (float)this.module.minBreakDamage.getValue()) {
                return false;
            }
            focusData.getData().add(data);
        }
        final Optional<T> first = focusData.getData().stream().filter(d -> !EntityUtil.isDead(d.getCrystal())).findFirst();
        if (!first.isPresent()) {
            this.module.focus = null;
            return false;
        }
        if ((boolean)this.module.focusAngleCalc.getValue() && minData != null && !minData.equals(first.get())) {
            focusList.set(0, minData);
        }
        this.evaluate(focusData);
        return this.rotating || this.attacking;
    }
    
    protected Set<T> getBreakDataSet() {
        final double exponent = (double)this.module.rotationExponent.getValue();
        if (Double.compare(exponent, 0.0) == 0 || this.module.noRotateNigga(AutoCrystal.ACRotate.Break)) {
            return new TreeSet<T>();
        }
        return (Set<T>)RotationComparator.asSet(exponent, (double)this.module.minRotDiff.getValue());
    }
}
