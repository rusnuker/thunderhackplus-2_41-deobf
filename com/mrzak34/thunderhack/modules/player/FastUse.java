//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class FastUse extends Module
{
    private final Setting<Integer> delay;
    public Setting<Boolean> blocks;
    public Setting<Boolean> crystals;
    public Setting<Boolean> xp;
    public Setting<Boolean> all;
    
    public FastUse() {
        super("FastUse", "\u0443\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0430\u0434\u0435\u0440\u0436\u043a\u0443-\u0438\u0441\u043f\u043e\u043b\u044c\u043e\u0432\u0430\u043d\u0438\u044f \u043f\u043a\u043c", "FastUse", Module.Category.PLAYER);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)1, (T)0, (T)4));
        this.blocks = (Setting<Boolean>)this.register(new Setting("Blocks", (T)false));
        this.crystals = (Setting<Boolean>)this.register(new Setting("Crystals", (T)false));
        this.xp = (Setting<Boolean>)this.register(new Setting("XP", (T)false));
        this.all = (Setting<Boolean>)this.register(new Setting("All", (T)true));
    }
    
    public void onUpdate() {
        if (this.check(FastUse.mc.player.getHeldItemMainhand().getItem()) && ((IMinecraft)FastUse.mc).getRightClickDelayTimer() > this.delay.getValue()) {
            ((IMinecraft)FastUse.mc).setRightClickDelayTimer((int)this.delay.getValue());
        }
    }
    
    public boolean check(final Item item) {
        return (item instanceof ItemBlock && this.blocks.getValue()) || (item == Items.END_CRYSTAL && this.crystals.getValue()) || (item == Items.EXPERIENCE_BOTTLE && this.xp.getValue()) || this.all.getValue();
    }
}
