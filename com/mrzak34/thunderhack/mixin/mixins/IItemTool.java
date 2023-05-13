//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.item.*;

@Mixin({ ItemTool.class })
public interface IItemTool
{
    @Accessor("attackDamage")
    float getAttackDamage();
    
    @Accessor("toolMaterial")
    Item.ToolMaterial getToolMaterial();
}
