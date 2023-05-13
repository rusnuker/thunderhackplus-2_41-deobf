//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.command.commands.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer
{
    private final ResourceLocation amogus;
    private final ResourceLocation rabbit;
    private final ResourceLocation fred;
    private float renderPitch;
    private float renderYaw;
    private float renderHeadYaw;
    private float prevRenderHeadYaw;
    private float prevRenderPitch;
    
    public MixinRenderPlayer() {
        this.amogus = new ResourceLocation("textures/amogus.png");
        this.rabbit = new ResourceLocation("textures/rabbit.png");
        this.fred = new ResourceLocation("textures/freddy.png");
    }
    
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    public void renderEntityNameHook(final AbstractClientPlayer entityIn, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo info) {
        if (((NameTags)Thunderhack.moduleManager.getModuleByClass((Class)NameTags.class)).isEnabled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
    private boolean isUserRedirect(final AbstractClientPlayer abstractClientPlayer) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return abstractClientPlayer.isUser() && abstractClientPlayer == mc.getRenderViewEntity();
        }
        return abstractClientPlayer.isUser();
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void rotateBegin(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).renderRotations.getValue() && entity == Minecraft.getMinecraft().player) {
            if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
                return;
            }
            this.prevRenderHeadYaw = entity.prevRotationYawHead;
            this.prevRenderPitch = entity.prevRotationPitch;
            this.renderPitch = entity.rotationPitch;
            this.renderYaw = entity.rotationYaw;
            this.renderHeadYaw = entity.rotationYawHead;
            final float interpYaw = (float)RadarRewrite.interp((double)Thunderhack.rotationManager.visualYaw, (double)Thunderhack.rotationManager.prevVisualYaw);
            final float interpPitch = (float)RadarRewrite.interp((double)Thunderhack.rotationManager.visualPitch, (double)Thunderhack.rotationManager.prevVisualPitch);
            entity.rotationPitch = interpPitch;
            entity.prevRotationPitch = interpPitch;
            entity.rotationYaw = interpYaw;
            entity.rotationYawHead = interpYaw;
            entity.prevRotationYawHead = interpYaw;
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    private void rotateEnd(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).renderRotations.getValue() && entity == Minecraft.getMinecraft().player) {
            if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
                return;
            }
            entity.rotationPitch = this.renderPitch;
            entity.rotationYaw = this.renderYaw;
            entity.rotationYawHead = this.renderHeadYaw;
            entity.prevRotationYawHead = this.prevRenderHeadYaw;
            entity.prevRotationPitch = this.prevRenderPitch;
        }
    }
    
    @Inject(method = { "getEntityTexture" }, at = { @At("HEAD") }, cancellable = true)
    public void getEntityTexture(final AbstractClientPlayer entity, final CallbackInfoReturnable<ResourceLocation> ci) {
        if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).isEnabled() && (!((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).onlySelf.getValue() || entity == Minecraft.getMinecraft().player || (Thunderhack.friendManager.isFriend(entity.getName()) && ((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).friends.getValue()))) {
            if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).Mode.getValue() == Models.mode.Amogus) {
                ci.setReturnValue((Object)this.amogus);
            }
            if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).Mode.getValue() == Models.mode.Rabbit) {
                ci.setReturnValue((Object)this.rabbit);
            }
            if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).Mode.getValue() == Models.mode.Freddy) {
                ci.setReturnValue((Object)this.fred);
            }
        }
        else if (ChangeSkinCommand.getInstance().changedplayers.contains(entity.getName())) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ci.setReturnValue((Object)PNGtoResourceLocation.getTexture3(entity.getName(), "png"));
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ci.setReturnValue((Object)entity.getLocationSkin());
        }
    }
}
