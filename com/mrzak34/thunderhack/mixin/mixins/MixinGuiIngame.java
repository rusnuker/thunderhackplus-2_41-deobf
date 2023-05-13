//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame extends Gui
{
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (((Potions)Thunderhack.moduleManager.getModuleByClass((Class)Potions.class)).isOn()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderScoreboardHook(final ScoreObjective objective, final ScaledResolution scaledRes, final CallbackInfo ci) {
        if (((AntiTittle)Thunderhack.moduleManager.getModuleByClass((Class)AntiTittle.class)).scoreBoard.getValue() && ((AntiTittle)Thunderhack.moduleManager.getModuleByClass((Class)AntiTittle.class)).isOn()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderPortal" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPortal(final float n, final ScaledResolution scaledResolution, final CallbackInfo callbackInfo) {
        if (((NoRender)Thunderhack.moduleManager.getModuleByClass((Class)NoRender.class)).portal.getValue() && ((NoRender)Thunderhack.moduleManager.getModuleByClass((Class)NoRender.class)).isOn()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderAttackIndicator" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderAttackIndicator(final float partialTicks, final ScaledResolution p_184045_2_, final CallbackInfo ci) {
        final RenderAttackIndicatorEvent event = new RenderAttackIndicatorEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
