//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.toasts.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiToast.class })
public class MixinGuiToast
{
    @Inject(method = { "drawToast" }, at = { @At("HEAD") }, cancellable = true)
    public void drawToastHook(final ScaledResolution resolution, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().advancements.getValue()) {
            info.cancel();
        }
    }
}
