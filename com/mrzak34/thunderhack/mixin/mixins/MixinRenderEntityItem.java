//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEntityItem.class })
public abstract class MixinRenderEntityItem extends MixinRenderer
{
    @Inject(method = { "doRender" }, at = { @At("HEAD") }, cancellable = true)
    private void doRender(final EntityItem entityItem, final double d, final double d2, final double d3, final float f, final float f2, final CallbackInfo callbackInfo) {
        if (((ItemPhysics)Thunderhack.moduleManager.getModuleByClass((Class)ItemPhysics.class)).isEnabled()) {
            ((ItemPhysics)Thunderhack.moduleManager.getModuleByClass((Class)ItemPhysics.class)).Method2279((Entity)entityItem, d, d2, d3);
            callbackInfo.cancel();
        }
    }
}
