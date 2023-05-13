//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.init.*;

public class AntiBadEffects extends Module
{
    public AntiBadEffects() {
        super("AntiBadEffects", "AntiBadEffects", Module.Category.PLAYER);
    }
    
    public void onUpdate() {
        if (AntiBadEffects.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            AntiBadEffects.mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
        }
        if (AntiBadEffects.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            AntiBadEffects.mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
        }
        if (AntiBadEffects.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            AntiBadEffects.mc.player.removeActivePotionEffect(MobEffects.MINING_FATIGUE);
        }
        if (AntiBadEffects.mc.player.isPotionActive(MobEffects.LEVITATION)) {
            AntiBadEffects.mc.player.removeActivePotionEffect(MobEffects.LEVITATION);
        }
        if (AntiBadEffects.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            AntiBadEffects.mc.player.removeActivePotionEffect(MobEffects.SLOWNESS);
        }
    }
}
