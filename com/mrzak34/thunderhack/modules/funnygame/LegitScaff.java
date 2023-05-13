//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class LegitScaff extends Module
{
    private final Setting<Integer> blue;
    public Setting<Boolean> shift;
    public Setting<Boolean> only;
    public Setting<Boolean> fast;
    public Setting<Boolean> lt;
    public Timer timr;
    
    public LegitScaff() {
        super("LegitScaff", "\u043c\u043e\u0436\u043d\u043e \u0438 \u043b\u0435\u0433\u0438\u0442\u043d\u0435\u0435", Category.FUNNYGAME);
        this.blue = (Setting<Integer>)this.register(new Setting("CPS timer", (T)2, (T)0, (T)6));
        this.shift = (Setting<Boolean>)this.register(new Setting("shift", (T)true));
        this.only = (Setting<Boolean>)this.register(new Setting("OnlyBlocks", (T)true));
        this.fast = (Setting<Boolean>)this.register(new Setting("RealyDamnFast", (T)true));
        this.lt = (Setting<Boolean>)this.register(new Setting("LegitTower", (T)true));
        this.timr = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (Util.mc.player != null && Util.mc.world != null) {
            if (this.fast.getValue()) {
                ((IMinecraft)LegitScaff.mc).setRightClickDelayTimer((int)this.blue.getValue());
            }
            if (this.lt.getValue() && LegitScaff.mc.player.movementInput.jump) {
                for (int i = (int)LegitScaff.mc.player.rotationPitch; i < 83; ++i) {
                    LegitScaff.mc.player.rotationPitch = (float)i;
                }
                for (int i = (int)LegitScaff.mc.player.rotationPitch; i > 83; --i) {
                    LegitScaff.mc.player.rotationPitch = (float)i;
                }
                KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            }
            if (this.shift.getValue()) {
                final ItemStack j = Util.mc.player.getHeldItemMainhand();
                final BlockPos bP = new BlockPos(Util.mc.player.posX, Util.mc.player.posY - 1.0, Util.mc.player.posZ);
                if (j != null && (!this.only.getValue() || j.getItem() instanceof ItemBlock)) {
                    KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindSneak.getKeyCode(), false);
                    if (LegitScaff.mc.world.getBlockState(bP).getBlock() == Blocks.AIR) {
                        KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                        if (this.timr.passedMs(50L)) {
                            KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                            this.timr.reset();
                        }
                        else {
                            KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        KeyBinding.setKeyBindState(LegitScaff.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        super.onDisable();
    }
}
