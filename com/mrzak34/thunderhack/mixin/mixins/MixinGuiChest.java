//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiChest.class })
public abstract class MixinGuiChest
{
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") }, cancellable = true)
    private void drawScreenHook(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        if (KDShop.cancelRender) {
            ci.cancel();
        }
    }
}
