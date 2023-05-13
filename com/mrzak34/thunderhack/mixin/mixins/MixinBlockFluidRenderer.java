//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockFluidRenderer.class })
public class MixinBlockFluidRenderer
{
    @Inject(method = { "renderFluid" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFluidHook(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BufferBuilder bufferBuilder, final CallbackInfoReturnable<Boolean> info) {
        if (((XRay)Thunderhack.moduleManager.getModuleByClass((Class)XRay.class)).isOn() && !((XRay)Thunderhack.moduleManager.getModuleByClass((Class)XRay.class)).shouldRender(blockState.getBlock())) {
            info.setReturnValue((Object)false);
            info.cancel();
        }
    }
}
