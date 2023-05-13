//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.tileentity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.util.glu.*;

public class BeakonESP extends Module
{
    public final Setting<ColorSetting> color;
    public final Setting<ColorSetting> color2;
    private final Setting<Integer> slices;
    private final Setting<Integer> stacks;
    
    public BeakonESP() {
        super("BeakonESP", "\u0440\u0430\u0434\u0438\u0443\u0441 \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u043c\u0430\u044f\u043a\u0430", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("ESPColor", (T)new ColorSetting(-2013200640)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("CircleColor", (T)new ColorSetting(-2013200640)));
        this.slices = (Setting<Integer>)this.register(new Setting("slices", (T)60, (T)10, (T)240));
        this.stacks = (Setting<Integer>)this.register(new Setting("stacks", (T)60, (T)10, (T)240));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        for (final TileEntity tileent : BeakonESP.mc.world.loadedTileEntityList) {
            if (tileent instanceof TileEntityBeacon) {
                final TileEntityBeacon beacon = (TileEntityBeacon)tileent;
                final double n = beacon.getPos().getX();
                BeakonESP.mc.getRenderManager();
                final double x = n - ((IRenderManager)BeakonESP.mc.getRenderManager()).getRenderPosX();
                final double n2 = beacon.getPos().getY();
                BeakonESP.mc.getRenderManager();
                final double y = n2 - ((IRenderManager)BeakonESP.mc.getRenderManager()).getRenderPosY();
                final double n3 = beacon.getPos().getZ();
                BeakonESP.mc.getRenderManager();
                final double z = n3 - ((IRenderManager)BeakonESP.mc.getRenderManager()).getRenderPosZ();
                GL11.glPushMatrix();
                RenderUtil.drawBlockOutline(beacon.getPos(), this.color.getValue().getColorObject(), 3.0f, true, 0);
                RenderHelper.disableStandardItemLighting();
                final float var12 = (float)beacon.getLevels();
                final float var13 = (var12 == 1.0f) ? 19.0f : ((var12 == 2.0f) ? 29.0f : ((var12 == 3.0f) ? 39.0f : ((var12 == 4.0f) ? 49.0f : 0.0f)));
                this.draw(x, y, z, (int)var13);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void draw(final double x, final double y, final double z, final int power) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(x, y, z);
        GL11.glColor4f(this.color2.getValue().getRed() / 255.0f, this.color2.getValue().getBlue() / 255.0f, this.color2.getValue().getBlue() / 255.0f, this.color2.getValue().getAlpha() / 255.0f);
        final Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw((float)power, (int)this.slices.getValue(), (int)this.stacks.getValue());
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}
