//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GlStateManager.class })
public class MixinGlStateManager
{
    @Inject(method = { "enableFog" }, at = { @At("HEAD") }, cancellable = true)
    private static void onEnableFog(final CallbackInfo info) {
        if (((NoRender)Thunderhack.moduleManager.getModuleByClass((Class)NoRender.class)).fog.getValue() && ((NoRender)Thunderhack.moduleManager.getModuleByClass((Class)NoRender.class)).isOn()) {
            info.cancel();
        }
    }
}
