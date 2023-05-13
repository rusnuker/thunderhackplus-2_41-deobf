//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.item.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.block.model.*;

public class AutoPot extends Module
{
    public static int neededCap;
    private final Setting<Mode> mainMode;
    public Setting<Integer> triggerhealth;
    public Setting<Integer> delay;
    public Setting<Boolean> animation;
    public Timer timer;
    public Timer alerttimer;
    private int itemActivationTicks;
    private float itemActivationOffX;
    private float itemActivationOffY;
    private ItemStack itemActivationItem;
    private final Random random;
    
    public AutoPot() {
        super("AutoCappuccino", "\u0430\u0432\u0442\u043e\u043a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e \u0434\u043b\u044f-\u0444\u0430\u043d\u0433\u0435\u0439\u043c\u0430", Category.FUNNYGAME);
        this.mainMode = (Setting<Mode>)this.register(new Setting("Version", (T)Mode.New));
        this.triggerhealth = (Setting<Integer>)this.register(new Setting("TriggerHealth", (T)10, (T)1, (T)36));
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)200, (T)1, (T)2000));
        this.animation = (Setting<Boolean>)this.register(new Setting("Animation", (T)true));
        this.timer = new Timer();
        this.alerttimer = new Timer();
        this.random = new Random();
    }
    
    @Override
    public void onUpdate() {
        if (AutoPot.mc.player.getHealth() < this.triggerhealth.getValue() && this.timer.passedMs(this.delay.getValue()) && InventoryUtil.getCappuchinoAtHotbar(this.mainMode.getValue() == Mode.Old) != -1) {
            this.itemActivationItem = InventoryUtil.getPotionItemStack(this.mainMode.getValue() == Mode.Old);
            final int hotbarslot = AutoPot.mc.player.inventory.currentItem;
            AutoPot.mc.world.playSound(PlayerUtils.getPlayerPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.AMBIENT, 150.0f, 1.0f, true);
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getCappuchinoAtHotbar(this.mainMode.getValue() == Mode.Old)));
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            ++AutoPot.neededCap;
            this.aboba();
            this.timer.reset();
        }
        if (InventoryUtil.getCappuchinoAtHotbar(this.mainMode.getValue() == Mode.Old) == -1 && this.alerttimer.passedMs(1000L)) {
            Command.sendMessage("\u041d\u0435\u043c\u0430 \u0437\u0435\u043b\u0435\u043a!!!!");
            AutoPot.mc.world.playSound(PlayerUtils.getPlayerPos(), SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.AMBIENT, 150.0f, 10.0f, true);
            this.alerttimer.reset();
        }
        if (this.itemActivationTicks > 0) {
            --this.itemActivationTicks;
            if (this.itemActivationTicks == 0) {
                this.itemActivationItem = null;
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (this.animation.getValue()) {
            this.renderItemActivation(e.scaledResolution.getScaledWidth(), e.scaledResolution.getScaledHeight(), AutoPot.mc.getRenderPartialTicks());
        }
    }
    
    public void aboba() {
        this.itemActivationTicks = 40;
        this.itemActivationOffX = this.random.nextFloat() * 2.0f - 1.0f;
        this.itemActivationOffY = this.random.nextFloat() * 2.0f - 1.0f;
    }
    
    public void renderItemActivation(final int p_190563_1_, final int p_190563_2_, final float p_190563_3_) {
        if (this.itemActivationItem != null && this.itemActivationTicks > 0) {
            final int i = 40 - this.itemActivationTicks;
            final float f = (i + p_190563_3_) / 40.0f;
            final float f2 = f * f;
            final float f3 = f * f2;
            final float f4 = 10.25f * f3 * f2 + -24.95f * f2 * f2 + 25.5f * f3 + -13.8f * f2 + 4.0f * f;
            final float f5 = f4 * 3.1415927f;
            final float f6 = this.itemActivationOffX * (p_190563_1_ / 4);
            final float f7 = this.itemActivationOffY * (p_190563_2_ / 4);
            GlStateManager.enableAlpha();
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableDepth();
            GlStateManager.disableCull();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.translate(p_190563_1_ / 2 + f6 * MathHelper.abs(MathHelper.sin(f5 * 2.0f)), p_190563_2_ / 2 + f7 * MathHelper.abs(MathHelper.sin(f5 * 2.0f)), -50.0f);
            final float f8 = 50.0f + 175.0f * MathHelper.sin(f5);
            GlStateManager.scale(f8, -f8, f8);
            GlStateManager.rotate(900.0f * MathHelper.abs(MathHelper.sin(f5)), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(6.0f * MathHelper.cos(f * 8.0f), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(6.0f * MathHelper.cos(f * 8.0f), 0.0f, 0.0f, 1.0f);
            AutoPot.mc.getRenderItem().renderItem(this.itemActivationItem, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableCull();
            GlStateManager.disableDepth();
        }
    }
    
    static {
        AutoPot.neededCap = 0;
    }
    
    public enum Mode
    {
        Old, 
        New;
    }
}
