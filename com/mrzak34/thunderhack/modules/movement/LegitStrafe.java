//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.init.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;

public class LegitStrafe extends Module
{
    public Setting<Float> motion2;
    public Setting<Float> speed;
    public Setting<Float> speedM;
    public Setting<Integer> acceleration;
    public Setting<Boolean> onlyDown;
    public Setting<Float> jitterY;
    int prevElytraSlot;
    int acceleration_ticks;
    private final Timer timer;
    private final Timer fixTimer;
    
    public LegitStrafe() {
        super("GlideFly", "\u0444\u043b\u0430\u0439 \u043d\u0430 \u0441\u0430\u043d\u0438\u043a-\u0445\u0443\u0439 \u043f\u043e\u0441\u043e\u0441\u0430\u043d\u0438\u043a", Module.Category.MOVEMENT);
        this.motion2 = (Setting<Float>)this.register(new Setting("motionY", (T)0.42f, (T)0.0f, (T)0.84f));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.8f, (T)0.1f, (T)3.0f));
        this.speedM = (Setting<Float>)this.register(new Setting("MaxSpeed", (T)0.8f, (T)0.1f, (T)3.0f));
        this.acceleration = (Setting<Integer>)this.register(new Setting("Acceleration", (T)60, (T)0, (T)100));
        this.onlyDown = (Setting<Boolean>)this.register(new Setting("Silent", (T)true));
        this.jitterY = (Setting<Float>)this.register(new Setting("JitterY", (T)0.2f, (T)0.0f, (T)0.42));
        this.prevElytraSlot = -1;
        this.acceleration_ticks = 0;
        this.timer = new Timer();
        this.fixTimer = new Timer();
    }
    
    public static int getElly() {
        for (final ItemStack stack : LegitStrafe.mc.player.getArmorInventoryList()) {
            if (stack.getItem() == Items.ELYTRA) {
                return -2;
            }
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            final ItemStack s = LegitStrafe.mc.player.inventory.getStackInSlot(i);
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
    
    public static void setSpeed(final float speed) {
        float yaw = LegitStrafe.mc.player.rotationYaw;
        float forward = LegitStrafe.mc.player.movementInput.moveForward;
        float strafe = LegitStrafe.mc.player.movementInput.moveStrafe;
        if (forward != 0.0f) {
            if (strafe > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (strafe < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            strafe = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        LegitStrafe.mc.player.motionX = forward * speed * cos + strafe * speed * sin;
        LegitStrafe.mc.player.motionZ = forward * speed * sin - strafe * speed * cos;
    }
    
    public static void setSpeed2(final float speed) {
        final float yaw = LegitStrafe.mc.player.rotationYaw;
        final float forward = 1.0f;
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        LegitStrafe.mc.player.motionX = forward * speed * cos;
        LegitStrafe.mc.player.motionZ = forward * speed * sin;
    }
    
    @SubscribeEvent
    public void onEvent222(final PlayerUpdateEvent event) {
        if (!this.onlyDown.getValue()) {
            if (LegitStrafe.mc.player.ticksExisted % 2 != 0) {
                return;
            }
            final ItemStack itemStack = ElytraSwap.getItemStack(38);
            if (itemStack == null) {
                return;
            }
            if (LegitStrafe.mc.player.onGround) {
                return;
            }
            if (itemStack.getItem() == Items.ELYTRA) {
                if (this.prevElytraSlot != -1) {
                    ElytraSwap.clickSlot(this.prevElytraSlot);
                    ElytraSwap.clickSlot(38);
                    ElytraSwap.clickSlot(this.prevElytraSlot);
                }
            }
            else if (ElytraSwap.hasItem(Items.ELYTRA)) {
                ElytraSwap.clickSlot(this.prevElytraSlot = ElytraSwap.getSlot(Items.ELYTRA));
                ElytraSwap.clickSlot(38);
                ElytraSwap.clickSlot(this.prevElytraSlot);
                LegitStrafe.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)LegitStrafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                LegitStrafe.mc.player.motionY = this.jitterY.getValue();
                if (((IKeyBinding)LegitStrafe.mc.gameSettings.keyBindJump).isPressed()) {
                    LegitStrafe.mc.player.motionY = this.motion2.getValue();
                }
                else if (((IKeyBinding)LegitStrafe.mc.gameSettings.keyBindSneak).isPressed()) {
                    LegitStrafe.mc.player.motionY = -this.motion2.getValue();
                }
                if (MovementUtil.isMoving()) {
                    setSpeed(this.speed.getValue());
                }
                else {
                    setSpeed2(0.1f);
                }
            }
        }
        else {
            final int elytra = getElly();
            if (elytra == -1) {
                Command.sendMessage("\u041d\u0435\u0442 \u044d\u043b\u0438\u0442\u0440!");
                this.toggle();
                return;
            }
            if (LegitStrafe.mc.player.onGround) {
                LegitStrafe.mc.player.jump();
                this.timer.reset();
                this.acceleration_ticks = 0;
            }
            else if (this.timer.passedMs(350L)) {
                if (LegitStrafe.mc.player.ticksExisted % 2 == 0) {
                    this.disabler2(elytra);
                }
                LegitStrafe.mc.player.motionY = ((LegitStrafe.mc.player.ticksExisted % 2 != 0) ? -0.25 : 0.25);
                if (!LegitStrafe.mc.player.isSneaking() && ((IKeyBinding)LegitStrafe.mc.gameSettings.keyBindJump).isPressed()) {
                    LegitStrafe.mc.player.motionY = this.motion2.getValue();
                }
                if (LegitStrafe.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    LegitStrafe.mc.player.motionY = -this.motion2.getValue();
                }
                if (MovementUtil.isMoving()) {
                    setSpeed((float)RenderUtil.interpolate(this.speedM.getValue(), this.speed.getValue(), Math.min(this.acceleration_ticks, this.acceleration.getValue()) / (float)this.acceleration.getValue()));
                }
                else {
                    this.acceleration_ticks = 0;
                    setSpeed2(0.1f);
                }
            }
        }
        ++this.acceleration_ticks;
        this.fixElytra();
    }
    
    public void fixElytra() {
        final ItemStack stack = LegitStrafe.mc.player.inventory.getItemStack();
        if (stack != null && stack.getItem() instanceof ItemArmor && this.fixTimer.passed(300L)) {
            final ItemArmor ia = (ItemArmor)stack.getItem();
            if (ia.armorType == EntityEquipmentSlot.CHEST && LegitStrafe.mc.player.inventory.armorItemInSlot(2).getItem() == Items.ELYTRA) {
                LegitStrafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
                int nullSlot = Strafe.findNullSlot();
                final boolean needDrop = nullSlot == 999;
                if (needDrop) {
                    nullSlot = 9;
                }
                LegitStrafe.mc.playerController.windowClick(0, nullSlot, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
                if (needDrop) {
                    LegitStrafe.mc.playerController.windowClick(0, -999, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
                }
                this.fixTimer.reset();
            }
        }
    }
    
    public void disabler2(final int elytra) {
        if (elytra != -2) {
            LegitStrafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
            LegitStrafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
        }
        if (!this.onlyDown.getValue()) {
            LegitStrafe.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)LegitStrafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
        LegitStrafe.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)LegitStrafe.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        if (elytra != -2) {
            LegitStrafe.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
            LegitStrafe.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)LegitStrafe.mc.player);
        }
    }
    
    public void onEnable() {
        this.acceleration_ticks = 0;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.acceleration_ticks = 0;
        }
    }
}
