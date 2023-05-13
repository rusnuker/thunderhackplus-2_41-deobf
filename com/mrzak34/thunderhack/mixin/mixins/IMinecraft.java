//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Minecraft.class })
public interface IMinecraft
{
    @Accessor("rightClickDelayTimer")
    void setRightClickDelayTimer(final int p0);
    
    @Invoker("rightClickMouse")
    void invokeRightClick();
    
    @Accessor("rightClickDelayTimer")
    int getRightClickDelayTimer();
}
