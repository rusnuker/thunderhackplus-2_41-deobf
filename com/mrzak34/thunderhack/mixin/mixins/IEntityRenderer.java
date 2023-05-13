//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityRenderer.class })
public interface IEntityRenderer
{
    @Invoker("orientCamera")
    void orientCam(final float p0);
    
    @Invoker("setupCameraTransform")
    void invokeSetupCameraTransform(final float p0, final int p1);
    
    @Accessor("rendererUpdateCount")
    int getRendererUpdateCount();
    
    @Accessor("rainXCoords")
    float[] getRainXCoords();
    
    @Accessor("rainYCoords")
    float[] getRainYCoords();
}
