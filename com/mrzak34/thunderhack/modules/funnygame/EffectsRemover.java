//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;

public class EffectsRemover extends Module
{
    public static int nig;
    public static boolean jboost;
    public Setting<Boolean> jumpBoost;
    public Setting<Boolean> oldr;
    public Timer timer;
    
    public EffectsRemover() {
        super("PowderTweaks", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0434\u0436\u0430\u043c\u043f\u0431\u0443\u0441\u0442 \u043e\u0442-\u043f\u043e\u0440\u043e\u0445\u0430 \u0438 \u044e\u0437\u0430\u0435\u0442 \u0435\u0433\u043e \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c", Category.FUNNYGAME);
        this.jumpBoost = (Setting<Boolean>)this.register(new Setting("JumpBoostRemove", (T)false));
        this.oldr = (Setting<Boolean>)this.register(new Setting("OldRemove", (T)false));
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        --EffectsRemover.nig;
        if (fullNullCheck()) {
            return;
        }
        if (this.timer.passedMs(500L) && !EffectsRemover.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("strength"))) && EffectsRemover.mc.objectMouseOver != null && EffectsRemover.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
            final int hotbarslot = EffectsRemover.mc.player.inventory.currentItem;
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(InventoryUtil.getPowderAtHotbar());
            if (!itemStack.getItem().getItemStackDisplayName(itemStack).equals("\u041f\u043e\u0440\u043e\u0445")) {
                return;
            }
            EffectsRemover.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getPowderAtHotbar()));
            EffectsRemover.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            EffectsRemover.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            this.timer.reset();
        }
        if (this.jumpBoost.getValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled() && EffectsRemover.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            EffectsRemover.nig = Objects.requireNonNull(EffectsRemover.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST)).getDuration();
            EffectsRemover.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("jump_boost"));
            EffectsRemover.jboost = true;
        }
        if (this.oldr.getValue() && EffectsRemover.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            EffectsRemover.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("jump_boost"));
        }
    }
    
    static {
        EffectsRemover.nig = 0;
        EffectsRemover.jboost = false;
    }
}
