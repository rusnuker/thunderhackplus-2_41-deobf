//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Strafe extends Module
{
    public static double oldSpeed;
    public static double contextFriction;
    public static boolean needSwap;
    public static boolean prevSprint;
    public static boolean needSprintState;
    public static int counter;
    public static int noSlowTicks;
    public static float jumpTicks;
    public Setting<Float> setSpeed;
    boolean skip;
    private final Setting<Mode> mode;
    public Setting<Boolean> elytra;
    public Setting<Boolean> onlyDown;
    private final Setting<Float> maxSpeed;
    private float waterTicks;
    private final Timer fixTimer;
    private final Timer elytraDelay;
    private final Timer startDelay;
    
    public Strafe() {
        super("Strafe", "testMove", Module.Category.MOVEMENT);
        this.setSpeed = (Setting<Float>)this.register(new Setting("speed", (T)1.3f, (T)0.0f, (T)2.0f));
        this.skip = false;
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Matrix));
        this.elytra = (Setting<Boolean>)this.register(new Setting("ElytraBoost", (T)false, v -> this.mode.getValue() == Mode.Matrix));
        this.onlyDown = (Setting<Boolean>)this.register(new Setting("OnlyDown", (T)false, v -> this.mode.getValue() == Mode.SunriseFast));
        this.maxSpeed = (Setting<Float>)this.register(new Setting("MaxSpeed", (T)0.9f, (T)0.0f, (T)2.0f, v -> this.mode.getValue() == Mode.SunriseFast));
        this.waterTicks = 0.0f;
        this.fixTimer = new Timer();
        this.elytraDelay = new Timer();
        this.startDelay = new Timer();
    }
    
    public static float getDirection() {
        float rotationYaw = Strafe.mc.player.rotationYaw;
        float strafeFactor = 0.0f;
        if (Strafe.mc.player.movementInput.moveForward > 0.0f) {
            strafeFactor = 1.0f;
        }
        if (Strafe.mc.player.movementInput.moveForward < 0.0f) {
            strafeFactor = -1.0f;
        }
        if (strafeFactor == 0.0f) {
            if (Strafe.mc.player.movementInput.moveStrafe > 0.0f) {
                rotationYaw -= 90.0f;
            }
            if (Strafe.mc.player.movementInput.moveStrafe < 0.0f) {
                rotationYaw += 90.0f;
            }
        }
        else {
            if (Strafe.mc.player.movementInput.moveStrafe > 0.0f) {
                rotationYaw -= 45.0f * strafeFactor;
            }
            if (Strafe.mc.player.movementInput.moveStrafe < 0.0f) {
                rotationYaw += 45.0f * strafeFactor;
            }
        }
        if (strafeFactor < 0.0f) {
            rotationYaw -= 180.0f;
        }
        return (float)Math.toRadians(rotationYaw);
    }
    
    public static void setStrafe(final double motion) {
        if (!MovementUtil.isMoving()) {
            return;
        }
        final double radians = getDirection();
        Strafe.mc.player.motionX = -Math.sin(radians) * motion;
        Strafe.mc.player.motionZ = Math.cos(radians) * motion;
    }
    
    public static float getMotion() {
        return (float)Math.sqrt(Strafe.mc.player.motionX * Strafe.mc.player.motionX + Strafe.mc.player.motionZ * Strafe.mc.player.motionZ);
    }
    
    public static void setMotion(final double motion) {
        double forward = Strafe.mc.player.movementInput.moveForward;
        double strafe = Strafe.mc.player.movementInput.moveStrafe;
        float yaw = Strafe.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Strafe.mc.player.motionX = 0.0;
            Strafe.mc.player.motionZ = 0.0;
            Strafe.oldSpeed = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            final double cosinus = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sinus = Math.sin(Math.toRadians(yaw + 90.0f));
            Strafe.mc.player.motionX = forward * motion * cosinus + strafe * motion * sinus;
            Strafe.mc.player.motionZ = forward * motion * sinus - strafe * motion * cosinus;
        }
    }
    
    public static double calculateSpeed(final MatrixMove move) {
        final Minecraft mc = Minecraft.getMinecraft();
        final boolean fromGround = mc.player.onGround;
        final boolean toGround = move.toGround();
        final boolean jump = move.getMotionY() > 0.0;
        final float speedAttributes = getAIMoveSpeed((EntityPlayer)mc.player);
        final float frictionFactor = getFrictionFactor((EntityPlayer)mc.player, move);
        float n6 = (mc.player.isPotionActive(MobEffects.JUMP_BOOST) && mc.player.isHandActive()) ? 0.88f : ((float)((Strafe.oldSpeed > 0.32 && mc.player.isHandActive()) ? 0.88 : 0.9100000262260437));
        if (fromGround) {
            n6 = frictionFactor;
        }
        final float n7 = (float)(0.16277135908603668 / Math.pow(n6, 3.01));
        float n8;
        if (fromGround) {
            n8 = speedAttributes * n7;
            if (jump) {
                n8 += 0.2f;
            }
        }
        else {
            n8 = 0.0255f;
        }
        boolean noslow = false;
        double max2 = Strafe.oldSpeed + n8;
        double max3 = 0.0;
        if (mc.player.isHandActive() && !jump) {
            double n9 = Strafe.oldSpeed + n8 * 0.25;
            final double motionY2 = move.getMotionY();
            if (motionY2 != 0.0 && Math.abs(motionY2) < 0.08) {
                n9 += 0.055;
            }
            if (max2 > (max3 = Math.max(0.043, n9))) {
                noslow = true;
                ++Strafe.noSlowTicks;
            }
            else {
                Strafe.noSlowTicks = Math.max(Strafe.noSlowTicks - 1, 0);
            }
        }
        else {
            Strafe.noSlowTicks = 0;
        }
        if (Strafe.noSlowTicks > 3) {
            max2 = max3 - 0.019;
        }
        else {
            max2 = Math.max(noslow ? 0.0 : 0.25, max2) - ((Strafe.counter++ % 2 == 0) ? 0.001 : 0.002);
        }
        Strafe.contextFriction = n6;
        if (!toGround && !fromGround) {
            Strafe.needSwap = true;
        }
        else {
            Strafe.prevSprint = false;
        }
        if (!fromGround && !toGround) {
            Strafe.needSprintState = !((IEntityPlayerSP)mc.player).getServerSprintState();
        }
        if (toGround && fromGround) {
            Strafe.needSprintState = false;
        }
        return max2;
    }
    
    public static float getAIMoveSpeed(final EntityPlayer contextPlayer) {
        final boolean prevSprinting = contextPlayer.isSprinting();
        contextPlayer.setSprinting(false);
        final float speed = contextPlayer.getAIMoveSpeed() * 1.3f;
        contextPlayer.setSprinting(prevSprinting);
        return speed;
    }
    
    private static float getFrictionFactor(final EntityPlayer contextPlayer, final MatrixMove move) {
        final BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(move.getFromX(), move.getAABBFrom().minY - 1.0, move.getFromZ());
        return contextPlayer.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos).getBlock().slipperiness * 0.91f;
    }
    
    public static int findNullSlot() {
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = Strafe.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemAir) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return 999;
    }
    
    public static void strafe(final float speed) {
        if (!MovementUtil.isMoving()) {
            return;
        }
        final double yaw = getDirection();
        Strafe.mc.player.motionX = -Math.sin(yaw) * speed;
        Strafe.mc.player.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void disabler(final int elytra) {
        if (elytra != -2) {
            Strafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
            Strafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
        }
        Strafe.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Strafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        Strafe.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Strafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        if (elytra != -2) {
            Strafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
            Strafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
        }
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(final PlayerUpdateEvent e) {
        if (this.mode.getValue() == Mode.Matrix) {
            if (!this.elytra.getValue()) {
                return;
            }
            final int elytra = this.getHotbarSlotOfItem();
            if (Strafe.mc.player.isInWater() || Strafe.mc.player.isInLava() || this.waterTicks > 0.0f || elytra == -1 || ((IEntity)Strafe.mc.player).isInWeb()) {
                return;
            }
            if (Strafe.mc.player.fallDistance != 0.0f && Strafe.mc.player.fallDistance < 0.1 && Strafe.mc.player.motionY < -0.1) {
                if (elytra != -2) {
                    Strafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                    Strafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                }
                Strafe.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)Strafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Strafe.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)Strafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                if (elytra != -2) {
                    Strafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                    Strafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                }
            }
        }
        if (Strafe.jumpTicks > 0.0f) {
            --Strafe.jumpTicks;
        }
    }
    
    @SubscribeEvent
    public void onMove(final MatrixMove event) {
        if (this.mode.getValue() == Mode.Matrix) {
            final int elytraSlot = this.getHotbarSlotOfItem();
            if (this.elytra.getValue() && elytraSlot != -1 && MovementUtil.isMoving() && !Strafe.mc.player.onGround && Strafe.mc.player.fallDistance >= 0.15 && event.toGround()) {
                setMotion(this.setSpeed.getValue());
                Strafe.oldSpeed = this.setSpeed.getValue() / 1.06;
            }
            if (Strafe.mc.player.isInWater()) {
                this.waterTicks = 10.0f;
            }
            else {
                --this.waterTicks;
            }
            if (this.strafes()) {
                double forward = Strafe.mc.player.movementInput.moveForward;
                double strafe = Strafe.mc.player.movementInput.moveStrafe;
                float yaw = Strafe.mc.player.rotationYaw;
                if (forward == 0.0 && strafe == 0.0) {
                    event.setMotionX(Strafe.oldSpeed = 0.0);
                    event.setMotionZ(0.0);
                }
                else {
                    if (forward != 0.0) {
                        if (strafe > 0.0) {
                            yaw += ((forward > 0.0) ? -45 : 45);
                        }
                        else if (strafe < 0.0) {
                            yaw += ((forward > 0.0) ? 45 : -45);
                        }
                        strafe = 0.0;
                        if (forward > 0.0) {
                            forward = 1.0;
                        }
                        else if (forward < 0.0) {
                            forward = -1.0;
                        }
                    }
                    final double speed = calculateSpeed(event);
                    final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                    final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
                    event.setMotionX(forward * speed * cos + strafe * speed * sin);
                    event.setMotionZ(forward * speed * sin - strafe * speed * cos);
                    event.setCanceled(true);
                }
            }
            else {
                Strafe.oldSpeed = 0.0;
            }
        }
    }
    
    @SubscribeEvent
    public void updateValues(final EventSync e) {
        final double distTraveledLastTickX = Strafe.mc.player.posX - Strafe.mc.player.prevPosX;
        final double distTraveledLastTickZ = Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ;
        Strafe.oldSpeed = Math.sqrt(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ) * Strafe.contextFriction;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            Strafe.oldSpeed = 0.0;
        }
    }
    
    public void onEnable() {
        Strafe.oldSpeed = 0.0;
        this.startDelay.reset();
        this.skip = true;
    }
    
    public boolean strafes() {
        return Strafe.mc.player != null && !Strafe.mc.player.isSneaking() && !Strafe.mc.player.isInLava() && !((RusherScaffold)Thunderhack.moduleManager.getModuleByClass((Class)RusherScaffold.class)).isEnabled() && !Strafe.mc.player.isInWater() && this.waterTicks <= 0.0f && !Strafe.mc.player.capabilities.isFlying;
    }
    
    @SubscribeEvent
    public void actionEvent(final EventSprint eventAction) {
        if (this.mode.getValue() == Mode.SunriseFast) {
            return;
        }
        if (this.strafes() && EventManager.serversprint != Strafe.needSprintState) {
            eventAction.setSprintState(!EventManager.serversprint);
        }
        if (Strafe.needSwap) {
            eventAction.setSprintState(!((IEntityPlayerSP)Strafe.mc.player).getServerSprintState());
            Strafe.needSwap = false;
        }
    }
    
    private int getHotbarSlotOfItem() {
        for (final ItemStack stack : Strafe.mc.player.getArmorInventoryList()) {
            if (stack.getItem() == Items.ELYTRA) {
                return -2;
            }
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            final ItemStack s = Strafe.mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == Items.ELYTRA) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }
    
    public void fixElytra() {
        final ItemStack stack = Strafe.mc.player.inventory.getItemStack();
        if (stack != null && stack.getItem() instanceof ItemArmor && this.fixTimer.passed(300L)) {
            final ItemArmor ia = (ItemArmor)stack.getItem();
            if (ia.armorType == EntityEquipmentSlot.CHEST && Strafe.mc.player.inventory.armorItemInSlot(2).getItem() == Items.ELYTRA) {
                Strafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                int nullSlot = findNullSlot();
                final boolean needDrop = nullSlot == 999;
                if (needDrop) {
                    nullSlot = 9;
                }
                Strafe.mc.playerController.windowClick(0, nullSlot, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                if (needDrop) {
                    Strafe.mc.playerController.windowClick(0, -999, 1, ClickType.PICKUP, (EntityPlayer)Strafe.mc.player);
                }
                this.fixTimer.reset();
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent event) {
        if (this.mode.getValue() == Mode.ElytraMiniJump) {
            if (Strafe.mc.player.onGround) {
                Strafe.mc.player.jump();
                return;
            }
            if (!Strafe.mc.world.getCollisionBoxes((Entity)Strafe.mc.player, Strafe.mc.player.getEntityBoundingBox().offset(0.0, -0.9, 0.0)).isEmpty() && this.elytraDelay.passedMs(250L) && this.startDelay.passedMs(500L)) {
                final int elytra = InventoryUtil.getElytra();
                if (elytra == -1) {
                    this.toggle();
                }
                else {
                    Strafe.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Strafe.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    disabler(elytra);
                }
                Strafe.mc.player.motionY = 0.0;
                if (MovementUtil.isMoving()) {
                    LegitStrafe.setSpeed((float)this.setSpeed.getValue());
                }
                this.elytraDelay.reset();
            }
        }
        if (this.mode.getValue() == Mode.SunriseFast) {
            if (Strafe.mc.player.ticksExisted % 6 == 0) {
                final int elytra = InventoryUtil.getElytra();
                if (elytra == -1) {
                    this.toggle();
                }
                else {
                    disabler(elytra);
                }
            }
            if (!this.skip) {
                if (Strafe.mc.player.onGround && !((IKeyBinding)Strafe.mc.gameSettings.keyBindJump).isPressed()) {
                    Strafe.mc.player.jump();
                    if (Strafe.jumpTicks != 0.0f) {
                        strafe(0.2f);
                        return;
                    }
                    Strafe.jumpTicks = 11.0f;
                    strafe((float)(MovementUtil.getSpeed() * this.setSpeed.getValue()));
                }
                if (!Strafe.mc.world.getCollisionBoxes((Entity)Strafe.mc.player, Strafe.mc.player.getEntityBoundingBox().offset(0.0, -0.84, 0.0)).isEmpty() && (!this.onlyDown.getValue() || Strafe.mc.player.fallDistance > 0.05)) {
                    setMotion(Math.min(MovementUtil.getSpeed() * this.setSpeed.getValue(), this.maxSpeed.getValue()));
                }
            }
            else {
                if (Strafe.mc.player.onGround) {
                    Strafe.mc.player.jump();
                }
                if (Strafe.mc.player.fallDistance > 0.05) {
                    this.skip = false;
                }
            }
        }
        this.fixElytra();
    }
    
    static {
        Strafe.jumpTicks = 0.0f;
    }
    
    private enum Mode
    {
        Matrix, 
        ElytraMiniJump, 
        SunriseFast;
    }
}
