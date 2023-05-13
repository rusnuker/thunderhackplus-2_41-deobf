//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import java.math.*;

public class Speed extends Module
{
    public double distance;
    public int Field2015;
    public int FunnyGameStage;
    public boolean flip;
    int velocity;
    int boostticks;
    boolean isBoosting;
    private final Setting<mode> Mode;
    public Setting<Integer> bticks;
    public Setting<Boolean> strafeBoost;
    public Setting<Float> reduction;
    public Setting<Boolean> usver;
    public double defaultBaseSpeed;
    private final Setting<Boolean> autoWalk;
    private final Setting<Boolean> uav;
    private boolean nexus_flip;
    private double strictBaseSpeed;
    private int strictCounter;
    private int strictStage;
    private int ticksPassed;
    private int strictTicks;
    private double maxVelocity;
    private final Timer velocityTimer;
    
    public Speed() {
        super("Speed", "\u0441\u043f\u0438\u0434\u044b", Module.Category.MOVEMENT);
        this.Field2015 = 4;
        this.velocity = 0;
        this.boostticks = 0;
        this.isBoosting = false;
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.Default));
        this.bticks = (Setting<Integer>)this.register(new Setting("boostTicks", (T)10, (T)1, (T)40, v -> this.Mode.getValue() == mode.Default));
        this.strafeBoost = (Setting<Boolean>)this.register(new Setting("StrafeBoost", (T)false, v -> this.Mode.getValue() == mode.Default));
        this.reduction = (Setting<Float>)this.register(new Setting("reduction ", (T)2.0f, (T)1.0f, (T)10.0f, v -> this.Mode.getValue() == mode.Default));
        this.usver = (Setting<Boolean>)this.register(new Setting("calcJumpBoost", (T)false, v -> this.Mode.getValue() == mode.Default));
        this.defaultBaseSpeed = this.getBaseMoveSpeed();
        this.autoWalk = (Setting<Boolean>)this.register(new Setting("AutoWalk", (T)false));
        this.uav = (Setting<Boolean>)this.register(new Setting("UseAllVelocity", (T)false, v -> this.Mode.getValue() == mode.Default));
        this.nexus_flip = false;
        this.strictBaseSpeed = 0.2873;
        this.strictStage = 4;
        this.ticksPassed = 0;
        this.maxVelocity = 0.0;
        this.velocityTimer = new Timer();
    }
    
    public void onDisable() {
        this.defaultBaseSpeed = this.getBaseMoveSpeed();
        this.Field2015 = 4;
        this.distance = 0.0;
        this.FunnyGameStage = 0;
        Speed.mc.player.jumpMovementFactor = 0.02f;
        Thunderhack.TICK_TIMER = 1.0f;
        this.velocity = 0;
        this.strictTicks = 0;
    }
    
    public void onEnable() {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            this.toggle();
            return;
        }
        this.strictTicks = 0;
        this.maxVelocity = 0.0;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync event) {
        if (this.Mode.getValue() == mode.MatrixJumpBoost && MovementUtil.isMoving() && !Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().expand(0.5, 0.0, 0.5).offset(0.0, -1.0, 0.0)).isEmpty() && !this.nexus_flip) {
            Speed.mc.player.onGround = true;
            Speed.mc.player.jump();
            Speed.mc.player.jumpMovementFactor = 0.026523f;
        }
        if (Speed.mc.player.fallDistance > 0.0f) {
            this.nexus_flip = true;
        }
        if (!Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, -0.2, 0.0)).isEmpty() && this.nexus_flip) {
            this.nexus_flip = false;
        }
        if (this.strafeBoost.getValue() && this.isBoosting) {
            return;
        }
        if (this.Mode.getValue() == mode.Grief) {
            return;
        }
        final double d2 = Speed.mc.player.posX - Speed.mc.player.prevPosX;
        final double d3 = Speed.mc.player.posZ - Speed.mc.player.prevPosZ;
        final double d4 = d2 * d2 + d3 * d3;
        this.distance = Math.sqrt(d4);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == Speed.mc.player.getEntityId()) {
            final SPacketEntityVelocity pack = (SPacketEntityVelocity)event.getPacket();
            int vX = pack.getMotionX();
            int vZ = pack.getMotionZ();
            if (vX < 0) {
                vX *= -1;
            }
            if (vZ < 0) {
                vZ *= -1;
            }
            if (vX + vZ < 3000 && !this.uav.getValue()) {
                return;
            }
            this.velocity = vX + vZ;
            this.boostticks = this.bticks.getValue();
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            Thunderhack.TICK_TIMER = 1.0f;
            this.strictBaseSpeed = 0.2873;
            this.strictStage = 4;
            this.strictCounter = 0;
            this.strictTicks = 0;
            this.maxVelocity = 0.0;
            this.toggle();
        }
        else if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion velocity = (SPacketExplosion)event.getPacket();
            this.maxVelocity = Math.sqrt(velocity.getMotionX() * velocity.getMotionX() + velocity.getMotionZ() * velocity.getMotionZ());
            this.velocityTimer.reset();
        }
    }
    
    public void onUpdate() {
        if (this.autoWalk.getValue()) {
            ((IKeyBinding)Speed.mc.gameSettings.keyBindForward).setPressed(true);
        }
        if (this.Mode.getValue() == mode.Grief) {
            if (!MovementUtil.isMoving()) {
                return;
            }
            if (Speed.mc.player.onGround) {
                Speed.mc.player.jump();
                Thunderhack.TICK_TIMER = 0.94f;
            }
            if (Speed.mc.player.fallDistance > 0.7 && Speed.mc.player.fallDistance < 1.3) {
                Thunderhack.TICK_TIMER = 1.8f;
            }
        }
        if (this.Mode.getValue() == mode.ReallyWorld) {
            if (!MovementUtil.isMoving()) {
                return;
            }
            if (Speed.mc.player.onGround) {
                Speed.mc.player.jump();
            }
            if (Speed.mc.player.fallDistance <= 0.22) {
                Thunderhack.TICK_TIMER = 3.5f;
                Speed.mc.player.jumpMovementFactor = 0.026523f;
            }
            else if (Speed.mc.player.fallDistance < 1.25) {
                Thunderhack.TICK_TIMER = 0.47f;
            }
        }
    }
    
    public double getBaseMoveSpeed() {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return 0.2873;
        }
        double d = 0.2873;
        if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int n = Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST) && this.usver.getValue()) {
            final int n = Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST)).getAmplifier();
            d /= 1.0 + 0.2 * (n + 1);
        }
        if (this.strafeBoost.getValue() && this.velocity > 0 && this.boostticks > 0) {
            d += this.velocity / 8000.0f / this.reduction.getValue();
            --this.boostticks;
        }
        if (this.boostticks == 1) {
            this.velocity = 0;
        }
        return d;
    }
    
    public double isJumpBoost() {
        if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            return 0.2;
        }
        return 0.0;
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            return;
        }
        switch (this.Mode.getValue()) {
            case StrafeStrict: {
                ++this.strictCounter;
                this.strictCounter %= 5;
                if (this.strictCounter != 0) {
                    Thunderhack.TICK_TIMER = 1.0f;
                }
                else if (PlayerUtils.isPlayerMoving()) {
                    Thunderhack.TICK_TIMER = 1.3f;
                    final EntityPlayerSP player = Speed.mc.player;
                    player.motionX *= 1.0199999809265137;
                    final EntityPlayerSP player2 = Speed.mc.player;
                    player2.motionZ *= 1.0199999809265137;
                }
                if (Speed.mc.player.onGround && PlayerUtils.isPlayerMoving()) {
                    this.strictStage = 2;
                }
                if (this.round(Speed.mc.player.posY - (int)Speed.mc.player.posY) == this.round(0.138)) {
                    final EntityPlayerSP player3 = Speed.mc.player;
                    player3.motionY -= 0.08;
                    event.setY(event.get_y() - 0.09316090325960147);
                    final EntityPlayerSP player4 = Speed.mc.player;
                    player4.posY -= 0.09316090325960147;
                }
                if (this.strictStage == 1 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                    this.strictStage = 2;
                    this.strictBaseSpeed = 1.38 * this.getBaseMotionSpeed() - 0.01;
                }
                else if (this.strictStage == 2) {
                    this.strictStage = 3;
                    event.setY(Speed.mc.player.motionY = 0.399399995803833);
                    this.strictBaseSpeed *= 2.149;
                }
                else if (this.strictStage == 3) {
                    this.strictStage = 4;
                    final double adjustedMotion = 0.66 * (this.distance - this.getBaseMotionSpeed());
                    this.strictBaseSpeed = this.distance - adjustedMotion;
                }
                else {
                    if (Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() > 0 || Speed.mc.player.collidedVertically) {
                        this.strictStage = 1;
                    }
                    this.strictBaseSpeed = this.distance - this.distance / 159.0;
                }
                this.strictBaseSpeed = Math.max(this.strictBaseSpeed, this.getBaseMotionSpeed());
                if (this.maxVelocity > 0.0 && this.strafeBoost.getValue() && !this.velocityTimer.passedMs(75L) && !Speed.mc.player.collidedHorizontally) {
                    this.strictBaseSpeed = Math.max(this.strictBaseSpeed, this.maxVelocity);
                }
                else {
                    this.strictBaseSpeed = Math.min(this.strictBaseSpeed, (this.ticksPassed > 25) ? 0.449 : 0.433);
                }
                float forward = Speed.mc.player.movementInput.moveForward;
                float strafe = Speed.mc.player.movementInput.moveStrafe;
                float yaw = Speed.mc.player.rotationYaw;
                ++this.ticksPassed;
                if (this.ticksPassed > 50) {
                    this.ticksPassed = 0;
                }
                if (forward == 0.0f && strafe == 0.0f) {
                    event.setX(0.0);
                    event.setZ(0.0);
                }
                else if (forward != 0.0f) {
                    if (strafe >= 1.0f) {
                        yaw += ((forward > 0.0f) ? -45 : 45);
                        strafe = 0.0f;
                    }
                    else if (strafe <= -1.0f) {
                        yaw += ((forward > 0.0f) ? 45 : -45);
                        strafe = 0.0f;
                    }
                    if (forward > 0.0f) {
                        forward = 1.0f;
                    }
                    else if (forward < 0.0f) {
                        forward = -1.0f;
                    }
                }
                strafe = MathUtil.clamp(strafe, -1.0f, 1.0f);
                final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
                event.setX(forward * this.strictBaseSpeed * cos + strafe * this.strictBaseSpeed * sin);
                event.setZ(forward * this.strictBaseSpeed * sin - strafe * this.strictBaseSpeed * cos);
                if (forward == 0.0f && strafe == 0.0f) {
                    event.setX(0.0);
                    event.setZ(0.0);
                    break;
                }
                break;
            }
            case FunnyGameNew: {
                if (MovementUtil.isMoving()) {
                    Thunderhack.TICK_TIMER = 1.088f;
                    if (this.strictStage == 1) {
                        this.strictBaseSpeed = 1.35 * this.getBaseMoveSpeed() - 0.01;
                    }
                    else if (this.strictStage == 2) {
                        double jumpSpeed = 0.3999999463558197;
                        if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                            final double amplifier = Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
                            jumpSpeed += (amplifier + 1.0) * 0.1;
                        }
                        event.setY(Speed.mc.player.motionY = jumpSpeed);
                        final double acceleration = 2.149;
                        this.strictBaseSpeed *= acceleration;
                    }
                    else if (this.strictStage == 3) {
                        final double scaledstrictBaseSpeed = 0.66 * (this.distance - this.getBaseMoveSpeed());
                        this.strictBaseSpeed = this.distance - scaledstrictBaseSpeed;
                    }
                    else {
                        if ((Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() > 0 || Speed.mc.player.collidedVertically) && this.strictStage > 0) {
                            this.strictStage = (MovementUtil.isMoving() ? 1 : 0);
                        }
                        this.strictBaseSpeed = this.distance - this.distance / 159.0;
                    }
                    this.strictBaseSpeed = Math.max(this.strictBaseSpeed, this.getBaseMoveSpeed());
                    double baseStrictSpeed = 0.465;
                    double baseRestrictedSpeed = 0.44;
                    if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
                        final double amplifier2 = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                        baseStrictSpeed *= 1.0 + 0.2 * (amplifier2 + 1.0);
                        baseRestrictedSpeed *= 1.0 + 0.2 * (amplifier2 + 1.0);
                    }
                    if (Speed.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
                        final double amplifier2 = Speed.mc.player.getActivePotionEffect(MobEffects.SLOWNESS).getAmplifier();
                        baseStrictSpeed /= 1.0 + 0.2 * (amplifier2 + 1.0);
                        baseRestrictedSpeed /= 1.0 + 0.2 * (amplifier2 + 1.0);
                    }
                    this.strictBaseSpeed = Math.min(this.strictBaseSpeed, (this.strictTicks > 25) ? baseStrictSpeed : baseRestrictedSpeed);
                    ++this.strictTicks;
                    if (this.strictTicks > 50) {
                        this.strictTicks = 0;
                    }
                    float forward2 = Speed.mc.player.movementInput.moveForward;
                    float strafe2 = Speed.mc.player.movementInput.moveStrafe;
                    float yaw2 = Speed.mc.player.prevRotationYaw + (Speed.mc.player.rotationYaw - Speed.mc.player.prevRotationYaw) * Speed.mc.getRenderPartialTicks();
                    if (!MovementUtil.isMoving()) {
                        event.setX(0.0);
                        event.setZ(0.0);
                    }
                    else if (forward2 != 0.0f) {
                        if (strafe2 >= 1.0f) {
                            yaw2 += ((forward2 > 0.0f) ? -45 : 45);
                            strafe2 = 0.0f;
                        }
                        else if (strafe2 <= -1.0f) {
                            yaw2 += ((forward2 > 0.0f) ? 45 : -45);
                            strafe2 = 0.0f;
                        }
                        if (forward2 > 0.0f) {
                            forward2 = 1.0f;
                        }
                        else if (forward2 < 0.0f) {
                            forward2 = -1.0f;
                        }
                    }
                    final double cos2 = Math.cos(Math.toRadians(yaw2));
                    final double sin2 = -Math.sin(Math.toRadians(yaw2));
                    event.setX(forward2 * this.strictBaseSpeed * sin2 + strafe2 * this.strictBaseSpeed * cos2);
                    event.setZ(forward2 * this.strictBaseSpeed * cos2 - strafe2 * this.strictBaseSpeed * sin2);
                    ++this.strictStage;
                    break;
                }
                break;
            }
            case Default: {
                if (event.isCanceled()) {
                    return;
                }
                if (!PyroSpeed.isMovingClient() || Speed.mc.player.fallDistance > 5.0f) {
                    return;
                }
                if (Speed.mc.player.collidedHorizontally) {
                    final double d;
                    if (Speed.mc.player.onGround && (d = PyroSpeed.Method5402(1.0)) == 1.0) {
                        ++this.FunnyGameStage;
                    }
                    if (this.FunnyGameStage > 0) {
                        switch (this.FunnyGameStage) {
                            case 1: {
                                event.setCanceled(true);
                                event.set_y(0.41999998688698);
                                final int n2 = this.FunnyGameStage++;
                                return;
                            }
                            case 2: {
                                event.setCanceled(true);
                                event.set_y(0.33319999363422);
                                final int n3 = this.FunnyGameStage++;
                                return;
                            }
                            case 3: {
                                final float f = (float)PyroSpeed.Method718();
                                event.set_y(0.24813599859094704);
                                event.set_x(-MathHelper.sin(f) * 0.2);
                                event.set_z(MathHelper.cos(f) * 0.2);
                                this.FunnyGameStage = 0;
                                Speed.mc.player.motionY = 0.0;
                                event.setCanceled(true);
                                return;
                            }
                            default: {
                                return;
                            }
                        }
                    }
                }
                this.FunnyGameStage = 0;
                if (this.Field2015 == 1 && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f)) {
                    this.defaultBaseSpeed = 1.35 * this.getBaseMoveSpeed() - 0.01;
                }
                else if (this.Field2015 == 2 && Speed.mc.player.collidedVertically) {
                    final double d2;
                    final double d = d2 = 0.4;
                    Speed.mc.player.motionY = d2 + this.isJumpBoost();
                    final double d3 = d;
                    event.set_y(d3 + this.isJumpBoost());
                    this.flip = !this.flip;
                    this.defaultBaseSpeed *= (this.flip ? 1.6835 : 1.395);
                }
                else if (this.Field2015 == 3) {
                    final double d = 0.66 * (this.distance - this.getBaseMoveSpeed());
                    this.defaultBaseSpeed = this.distance - d;
                }
                else {
                    final List<AxisAlignedBB> list = (List<AxisAlignedBB>)Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0));
                    if ((list.size() > 0 || Speed.mc.player.collidedVertically) && this.Field2015 > 0) {
                        this.Field2015 = 1;
                    }
                    this.defaultBaseSpeed = this.distance - this.distance / 159.0;
                }
                event.setCanceled(true);
                PyroSpeed.Method744(event, this.defaultBaseSpeed = Math.max(this.defaultBaseSpeed, this.getBaseMoveSpeed()));
                ++this.Field2015;
                break;
            }
        }
        if (this.Mode.getValue() == mode.StrafeStrict || this.Mode.getValue() == mode.FunnyGameNew) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onMove(final MatrixMove move) {
        if (this.Mode.getValue() != mode.Matrix) {
            return;
        }
        if (!Speed.mc.player.onGround && move.toGround()) {
            move.setMotionX(move.getMotionX() * 2.0);
            move.setMotionZ(move.getMotionZ() * 2.0);
            final EntityPlayerSP player = Speed.mc.player;
            player.motionX *= 2.0;
            final EntityPlayerSP player2 = Speed.mc.player;
            player2.motionZ *= 2.0;
        }
    }
    
    public double getBaseMotionSpeed() {
        double baseSpeed = 0.2873;
        if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1.0);
        }
        return baseSpeed;
    }
    
    private double round(final double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public enum mode
    {
        Default, 
        Grief, 
        StrafeStrict, 
        ReallyWorld, 
        Matrix, 
        MatrixJumpBoost, 
        FunnyGameNew;
    }
}
