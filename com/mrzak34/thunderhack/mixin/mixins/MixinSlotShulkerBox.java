//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ SlotShulkerBox.class })
public class MixinSlotShulkerBox
{
    @Inject(method = { "isItemValid" }, at = { @At("HEAD") }, cancellable = true)
    public void isItemValid(final ItemStack stack, final CallbackInfoReturnable<Boolean> ci) {
        if (((Shulkerception)Thunderhack.moduleManager.getModuleByClass((Class)Shulkerception.class)).isEnabled() && Block.getBlockFromItem(stack.getItem()) instanceof BlockShulkerBox) {
            ci.setReturnValue((Object)true);
        }
    }
}
