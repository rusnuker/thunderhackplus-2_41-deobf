//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;

@Mixin({ Render.class })
abstract class MixinRenderer
{
    @Shadow
    protected boolean renderOutlines;
    @Shadow
    @Final
    protected RenderManager renderManager;
    
    @Shadow
    protected abstract boolean bindEntityTexture(final Entity p0);
    
    @Shadow
    protected abstract int getTeamColor(final Entity p0);
}
