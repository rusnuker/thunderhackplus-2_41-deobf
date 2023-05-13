//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;

@Mixin({ RenderGlobal.class })
public abstract class MixinRenderGlobal
{
    private static void VZWQ(final float width) {
        MqiP();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(width);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    private static void JLYv() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    private static void feKn() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    private static void mptE() {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    private static void VdOT() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    private static void MqiP() {
        final Framebuffer framebuffer = Util.mc.getFramebuffer();
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            Cvvp(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }
    
    private static void Cvvp(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    
    @Inject(method = { "renderEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;release()V", shift = At.Shift.BEFORE) })
    private void renderEntitiesHook(final Entity renderViewEntity, final ICamera camera, final float partialTicks, final CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new PostRenderEntitiesEvent(partialTicks, 0));
    }
    
    @Inject(method = { "renderEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;preRenderDamagedBlocks()V", shift = At.Shift.BEFORE) })
    public void renderEntities(final Entity entity, final ICamera camera, final float n, final CallbackInfo callbackInfo) {
        final StorageEsp esp = (StorageEsp)Thunderhack.moduleManager.getModuleByClass((Class)StorageEsp.class);
        if ((esp.isEnabled() && esp.mode.getValue() == StorageEsp.Mode.ShaderBox) || esp.mode.getValue() == StorageEsp.Mode.ShaderOutline) {
            esp.renderNormal(n);
            VZWQ(esp.lineWidth.getValue());
            esp.renderNormal(n);
            JLYv();
            esp.renderColor(n);
            feKn();
            mptE();
            esp.renderColor(n);
            VdOT();
        }
    }
}
