//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockRendererDispatcher.class })
public class MixinBlockRendererDispatcher
{
    @Inject(method = { "renderBlock" }, at = { @At("HEAD") })
    public void blockRenderInject(final IBlockState iBlockState, final BlockPos blockPos, final IBlockAccess iBlockAccess, final BufferBuilder bufferBuilder, final CallbackInfoReturnable<Boolean> cir) {
        final BlockRenderEvent event = new BlockRenderEvent(iBlockState.getBlock(), blockPos);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
