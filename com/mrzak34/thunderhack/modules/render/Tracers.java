//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import java.util.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Tracers extends Module
{
    private final Setting<Boolean> showFriends;
    private final Setting<ColorSetting> colorSetting;
    private final Setting<ColorSetting> fcolorSetting;
    private final Setting<Float> width;
    private final Setting<Float> tracerRange;
    
    public Tracers() {
        super("Tracers", "\u0435\u0431\u0443\u0447\u0430\u044f \u043f\u0430\u0443\u0442\u0438\u043d\u0430-\u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435", "tracers", Module.Category.RENDER);
        this.showFriends = (Setting<Boolean>)this.register(new Setting("ShowFriends", (T)true));
        this.colorSetting = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-1)));
        this.fcolorSetting = (Setting<ColorSetting>)this.register(new Setting("FriendColor", (T)new ColorSetting(-1)));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)2.0f, (T)0.1f, (T)5.0f));
        this.tracerRange = (Setting<Float>)this.register(new Setting("Range", (T)128.0f, (T)32.0f, (T)256.0f));
    }
    
    public static void renderTracer(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer)Tracers.mc.entityRenderer).orientCam(Tracers.mc.getRenderPartialTicks());
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        GL11.glPushAttrib(1048575);
        for (final Entity e : Tracers.mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer && e != Tracers.mc.player && Tracers.mc.player.getDistance(e) <= this.tracerRange.getValue()) {
                final Vec3d pos = EntityUtil.interpolateEntity(e, event.getPartialTicks());
                GL11.glBlendFunc(770, 771);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.glLineWidth((float)this.width.getValue());
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.enableAlpha();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                final boolean bobbing = Tracers.mc.gameSettings.viewBobbing;
                Tracers.mc.gameSettings.viewBobbing = false;
                final Color color = (Thunderhack.friendManager.isFriend(e.getName()) && this.showFriends.getValue()) ? this.fcolorSetting.getValue().getColorObject() : this.colorSetting.getValue().getColorObject();
                final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Tracers.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Tracers.mc.player.rotationYaw));
                renderTracer(eyes.x, eyes.y + Tracers.mc.player.getEyeHeight(), eyes.z, pos.x - ((IRenderManager)Tracers.mc.getRenderManager()).getRenderPosX(), pos.y - ((IRenderManager)Tracers.mc.getRenderManager()).getRenderPosY(), pos.z - ((IRenderManager)Tracers.mc.getRenderManager()).getRenderPosZ(), color.getRGB());
                Tracers.mc.gameSettings.viewBobbing = bobbing;
                GlStateManager.disableBlend();
            }
        }
        GL11.glPopAttrib();
    }
}
