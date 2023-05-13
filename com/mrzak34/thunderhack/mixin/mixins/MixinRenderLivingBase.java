//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase mainModel;
    
    protected MixinRenderLivingBase(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends EntityLivingBase> void injectChamsPre(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo callbackInfo) {
        if (entity instanceof EntityArmorStand && ((NoRender.getInstance().isOn() && NoRender.getInstance().noarmorstands.getValue()) || (((AntiTittle)Thunderhack.moduleManager.getModuleByClass((Class)AntiTittle.class)).isOn() && ((AntiTittle)Thunderhack.moduleManager.getModuleByClass((Class)AntiTittle.class)).armorstands.getValue()))) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "interpolateRotation" }, at = { @At("HEAD") })
    protected void interpolateRotation(final float prevYawOffset, final float yawOffset, final float partialTicks, final CallbackInfoReturnable<Float> cir) {
        if (((EzingKids)Thunderhack.moduleManager.getModuleByClass((Class)EzingKids.class)).isOn()) {
            this.mainModel.isChild = true;
        }
    }
}
