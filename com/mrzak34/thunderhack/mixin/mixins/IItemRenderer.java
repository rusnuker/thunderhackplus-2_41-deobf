//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.item.*;

@Mixin({ ItemRenderer.class })
public interface IItemRenderer
{
    @Accessor("equippedProgressMainHand")
    void setEquippedProgressMainHand(final float p0);
    
    @Accessor("equippedProgressMainHand")
    float getEquippedProgressMainHand();
    
    @Accessor("itemStackMainHand")
    void setItemStackMainHand(final ItemStack p0);
}
