//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class AutoBuff extends Module
{
    public Setting<Boolean> strenght;
    public Setting<Boolean> speed;
    public Setting<Boolean> fire;
    public Setting<Boolean> heal;
    public Setting<Integer> health;
    public Timer timer;
    
    public AutoBuff() {
        super("AutoBuff", "\u041a\u0438\u0434\u0430\u0435\u0442 \u0431\u0430\u0444\u044b", "uses explosive potions", Category.COMBAT);
        this.strenght = (Setting<Boolean>)this.register(new Setting("Strenght", (T)true));
        this.speed = (Setting<Boolean>)this.register(new Setting("Speed", (T)true));
        this.fire = (Setting<Boolean>)this.register(new Setting("FireRes", (T)true));
        this.heal = (Setting<Boolean>)this.register(new Setting("Heal", (T)true));
        this.health = (Setting<Integer>)this.register(new Setting("Health", (T)8, (T)0, (T)20));
        this.timer = new Timer();
    }
    
    public static int getPotionSlot(final Potions potion) {
        for (int i = 0; i < 9; ++i) {
            if (isStackPotion(AutoBuff.mc.player.inventory.getStackInSlot(i), potion)) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isPotionOnHotBar(final Potions potions) {
        return getPotionSlot(potions) != -1;
    }
    
    public static boolean isStackPotion(final ItemStack stack, final Potions potion) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() == Items.SPLASH_POTION) {
            int id = 0;
            switch (potion) {
                case STRENGTH: {
                    id = 5;
                    break;
                }
                case SPEED: {
                    id = 1;
                    break;
                }
                case FIRERES: {
                    id = 12;
                    break;
                }
                case HEAL: {
                    id = 6;
                    break;
                }
            }
            for (final PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Potion.getPotionById(id)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEvent(final EventSync event) {
        if (Aura.target != null && AutoBuff.mc.player.getCooledAttackStrength(1.0f) > 0.5f) {
            return;
        }
        final boolean shouldThrow = (!AutoBuff.mc.player.isPotionActive(MobEffects.SPEED) && isPotionOnHotBar(Potions.SPEED) && this.speed.getValue()) || (!AutoBuff.mc.player.isPotionActive(MobEffects.STRENGTH) && isPotionOnHotBar(Potions.STRENGTH) && this.strenght.getValue()) || (!AutoBuff.mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE) && isPotionOnHotBar(Potions.FIRERES) && this.fire.getValue()) || (EntityUtil.getHealth((Entity)AutoBuff.mc.player) < this.health.getValue() && isPotionOnHotBar(Potions.HEAL) && this.heal.getValue());
        if (AutoBuff.mc.player.ticksExisted > 80 && shouldThrow) {
            AutoBuff.mc.player.rotationPitch = 90.0f;
        }
    }
    
    @SubscribeEvent
    public void onPostMotion(final EventPostSync e) {
        if (Aura.target != null && AutoBuff.mc.player.getCooledAttackStrength(1.0f) > 0.5f) {
            return;
        }
        final boolean shouldThrow;
        e.addPostEvent(() -> {
            shouldThrow = ((!AutoBuff.mc.player.isPotionActive(MobEffects.SPEED) && isPotionOnHotBar(Potions.SPEED) && this.speed.getValue()) || (!AutoBuff.mc.player.isPotionActive(MobEffects.STRENGTH) && isPotionOnHotBar(Potions.STRENGTH) && this.strenght.getValue()) || (!AutoBuff.mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE) && isPotionOnHotBar(Potions.FIRERES) && this.fire.getValue()) || (EntityUtil.getHealth((Entity)AutoBuff.mc.player) < this.health.getValue() && isPotionOnHotBar(Potions.HEAL) && this.heal.getValue()));
            if (AutoBuff.mc.player.ticksExisted > 80 && shouldThrow && this.timer.passedMs(1000L)) {
                if (!AutoBuff.mc.player.isPotionActive(MobEffects.SPEED) && isPotionOnHotBar(Potions.SPEED) && this.speed.getValue()) {
                    this.throwPotion(Potions.SPEED);
                }
                if (!AutoBuff.mc.player.isPotionActive(MobEffects.STRENGTH) && isPotionOnHotBar(Potions.STRENGTH) && this.strenght.getValue()) {
                    this.throwPotion(Potions.STRENGTH);
                }
                if (!AutoBuff.mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE) && isPotionOnHotBar(Potions.FIRERES) && this.fire.getValue()) {
                    this.throwPotion(Potions.FIRERES);
                }
                if (EntityUtil.getHealth((Entity)AutoBuff.mc.player) < this.health.getValue() && this.heal.getValue() && isPotionOnHotBar(Potions.HEAL)) {
                    this.throwPotion(Potions.HEAL);
                }
                AutoBuff.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoBuff.mc.player.inventory.currentItem));
                this.timer.reset();
            }
        });
    }
    
    public void throwPotion(final Potions potion) {
        final int slot = getPotionSlot(potion);
        AutoBuff.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        AutoBuff.mc.playerController.updateController();
        AutoBuff.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        AutoBuff.mc.playerController.updateController();
    }
    
    public enum Potions
    {
        STRENGTH, 
        SPEED, 
        FIRERES, 
        HEAL;
    }
}
