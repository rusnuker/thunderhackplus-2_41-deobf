//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import java.util.concurrent.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;

public class BreadCrumbs extends Module
{
    private final Setting<Integer> limit;
    private final Setting<ColorSetting> color;
    private final List<Vec3d> positions;
    
    public BreadCrumbs() {
        super("BreadCrumbs", "\u043e\u0441\u0442\u0430\u0432\u043b\u044f\u0435\u0442 \u043b\u0438\u043d\u0438\u044e-\u043f\u0440\u0438 \u0445\u043e\u0434\u044c\u0431\u0435", "BreadCrumbs", Module.Category.RENDER);
        this.limit = (Setting<Integer>)this.register(new Setting("ListLimit", (T)1000, (T)10, (T)99999));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(3649978)));
        this.positions = new CopyOnWriteArrayList<Vec3d>();
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        BreadCrumbs.mc.entityRenderer.disableLightmap();
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        for (final Vec3d pos : this.positions) {
            RenderUtil.glColor(this.color.getValue().getColor());
            GL11.glVertex3d(pos.x - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosX(), pos.y - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosY(), pos.z - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosZ());
        }
        GL11.glEnd();
        GL11.glLineWidth(5.0f);
        GL11.glBegin(3);
        for (final Vec3d pos : this.positions) {
            RenderUtil.glColor(DrawHelper.injectAlpha(this.color.getValue().getColorObject(), 80));
            GL11.glVertex3d(pos.x - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosX(), pos.y - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosY(), pos.z - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosZ());
        }
        GL11.glEnd();
        GL11.glLineWidth(10.0f);
        GL11.glBegin(3);
        for (final Vec3d pos : this.positions) {
            RenderUtil.glColor(DrawHelper.injectAlpha(this.color.getValue().getColorObject(), 50));
            GL11.glVertex3d(pos.x - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosX(), pos.y - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosY(), pos.z - ((IRenderManager)BreadCrumbs.mc.getRenderManager()).getRenderPosZ());
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    @SubscribeEvent
    public void postSync(final EventPostSync event) {
        if (this.positions.size() > this.limit.getValue()) {
            this.positions.remove(0);
        }
        this.positions.add(new Vec3d(BreadCrumbs.mc.player.posX, BreadCrumbs.mc.player.getEntityBoundingBox().minY, BreadCrumbs.mc.player.posZ));
    }
    
    public void onDisable() {
        this.positions.clear();
    }
}
