//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ GuiScreen.class })
public interface IGuiScreen
{
    @Accessor("buttonList")
    List<GuiButton> getButtonList();
    
    @Accessor("buttonList")
    void setButtonList(final List<GuiButton> p0);
}
