//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.combat.*;

public class WeaknessHelper
{
    private final Setting<AutoCrystal.AntiWeakness> antiWeakness;
    private final Setting<Integer> cooldown;
    private boolean weaknessed;
    
    public WeaknessHelper(final Setting<AutoCrystal.AntiWeakness> antiWeakness, final Setting<Integer> cooldown) {
        this.antiWeakness = antiWeakness;
        this.cooldown = cooldown;
    }
    
    public void updateWeakness() {
        this.weaknessed = !DamageUtil.canBreakWeakness(true);
    }
    
    public boolean isWeaknessed() {
        return this.weaknessed;
    }
    
    public boolean canSwitch() {
        return this.antiWeakness.getValue() == AutoCrystal.AntiWeakness.Switch && (int)this.cooldown.getValue() == 0 && this.weaknessed;
    }
}
