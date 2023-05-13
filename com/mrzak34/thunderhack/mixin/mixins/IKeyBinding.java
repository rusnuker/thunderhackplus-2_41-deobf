//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ KeyBinding.class })
public interface IKeyBinding
{
    @Accessor("pressed")
    boolean isPressed();
    
    @Accessor("pressed")
    void setPressed(final boolean p0);
}
