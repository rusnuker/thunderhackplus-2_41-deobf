//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import java.util.function.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;

public class PlayerTrails extends Module
{
    public static AstolfoAnimation astolfo;
    private final Setting<ColorSetting> color;
    private final Setting<Boolean> shfix;
    public Setting<Float> down;
    public Setting<Float> width;
    public Setting<modeEn> mode;
    
    public PlayerTrails() {
        super("PlayerTrails", "\u0442\u0440\u0435\u0439\u043b\u044b \u043f\u043e\u0437\u0430\u0434\u0438-\u0438\u0433\u0440\u043e\u043a\u043e\u0432", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.shfix = (Setting<Boolean>)this.register(new Setting("ShaderFix", (T)false));
        this.down = (Setting<Float>)this.register(new Setting("Down", (T)0.5f, (T)0.0f, (T)2.0f));
        this.width = (Setting<Float>)this.register(new Setting("Height", (T)1.3f, (T)0.1f, (T)2.0f));
        this.mode = (Setting<modeEn>)this.register(new Setting("ColorMode", (T)modeEn.Ukraine));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.shfix.getValue()) {
            return;
        }
        for (final EntityPlayer entity : PlayerTrails.mc.world.playerEntities) {
            if (entity instanceof EntityPlayerSP && PlayerTrails.mc.gameSettings.thirdPersonView == 0) {
                continue;
            }
            final float alpha = this.color.getValue().getAlpha() / 255.0f;
            ((IEntityRenderer)PlayerTrails.mc.entityRenderer).invokeSetupCameraTransform(PlayerTrails.mc.getRenderPartialTicks(), 2);
            if (((IEntity)entity).getTrails().size() > 0) {
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                GL11.glDisable(2884);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glShadeModel(7425);
                GL11.glEnable(2848);
                GL11.glBegin(8);
                if (this.mode.getValue() == modeEn.Default) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, alpha * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                    }
                }
                else if (this.mode.getValue() == modeEn.Ukraine) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.9607843160629272, 0.8901960849761963, 0.25882354378700256, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 2.0f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.25882354378700256, 0.4000000059604645, 0.9607843160629272, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 2.0f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.25882354378700256, 0.4000000059604645, 0.9607843160629272, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.9607843160629272, 0.8901960849761963, 0.25882354378700256, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                    }
                }
                else if (this.mode.getValue() == modeEn.RUSSIA) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 0.0, 0.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 3.0f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.0, 0.0, 1.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 3.0f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() * 0.6666667f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 1.0, 1.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() * 0.6666667f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 1.0, 1.0, (alpha + 0.15f) * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.width.getValue() + this.down.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 0.0, 0.0, (alpha + 0.15f) * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                    }
                }
                GL11.glEnd();
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glShadeModel(7424);
                GL11.glEnable(2884);
                GL11.glPopMatrix();
            }
            GlStateManager.resetColor();
        }
    }
    
    @SubscribeEvent
    public void onRenderPost(final PreRenderEvent event) {
        if (!this.shfix.getValue()) {
            return;
        }
        for (final EntityPlayer entity : PlayerTrails.mc.world.playerEntities) {
            if (entity instanceof EntityPlayerSP && PlayerTrails.mc.gameSettings.thirdPersonView == 0) {
                continue;
            }
            final float alpha = this.color.getValue().getAlpha() / 255.0f;
            ((IEntityRenderer)PlayerTrails.mc.entityRenderer).invokeSetupCameraTransform(PlayerTrails.mc.getRenderPartialTicks(), 2);
            if (((IEntity)entity).getTrails().size() > 0) {
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                GL11.glDisable(2884);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glShadeModel(7425);
                GL11.glEnable(2848);
                GL11.glBegin(8);
                if (this.mode.getValue() == modeEn.Default) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, alpha * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(c.x, c.y, c.z, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                    }
                }
                else if (this.mode.getValue() == modeEn.Ukraine) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.9607843160629272, 0.8901960849761963, 0.25882354378700256, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 2.0f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.25882354378700256, 0.4000000059604645, 0.9607843160629272, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 2.0f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.25882354378700256, 0.4000000059604645, 0.9607843160629272, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.width.getValue() + this.down.getValue(), pos.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Vec3d c = ((IEntity)entity).getTrails().get(i).color();
                        final Trail ctx = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos = ctx.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.9607843160629272, 0.8901960849761963, 0.25882354378700256, (alpha + 0.15f) * ctx.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos.x, pos.y + this.down.getValue(), pos.z);
                    }
                }
                else if (this.mode.getValue() == modeEn.RUSSIA) {
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 0.0, 0.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 3.0f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(0.0, 0.0, 1.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() / 3.0f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() * 0.6666667f, pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(8);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 1.0, 1.0, alpha * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue() * 0.6666667f, pos2.z);
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue() + this.width.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glLineWidth(1.0f);
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 1.0, 1.0, (alpha + 0.15f) * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.width.getValue() + this.down.getValue(), pos2.z);
                    }
                    GL11.glEnd();
                    GL11.glBegin(3);
                    for (int i = 0; i < ((IEntity)entity).getTrails().size(); ++i) {
                        final Trail ctx2 = ((IEntity)entity).getTrails().get(i);
                        final Vec3d pos2 = ctx2.interpolate(PlayerTrails.mc.getRenderPartialTicks());
                        GL11.glColor4d(1.0, 0.0, 0.0, (alpha + 0.15f) * ctx2.animation(PlayerTrails.mc.getRenderPartialTicks()));
                        GL11.glVertex3d(pos2.x, pos2.y + this.down.getValue(), pos2.z);
                    }
                }
                GL11.glEnd();
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glShadeModel(7424);
                GL11.glEnable(2884);
                GL11.glPopMatrix();
            }
            GlStateManager.resetColor();
        }
    }
    
    @SubscribeEvent
    public void onEntityMove(final EventEntityMove e) {
        if (e.ctx() instanceof EntityPlayer) {
            final float red = this.color.getValue().getRed() / 255.0f;
            final float green = this.color.getValue().getGreen() / 255.0f;
            final float blue = this.color.getValue().getBlue() / 255.0f;
            final EntityPlayer a = (EntityPlayer)e.ctx();
            ((IEntity)a).getTrails().add(new Trail(e.from(), e.ctx().getPositionVector(), new Vec3d((double)red, (double)green, (double)blue)));
        }
    }
    
    public void onUpdate() {
        PlayerTrails.astolfo.update();
        for (final EntityPlayer player : PlayerTrails.mc.world.playerEntities) {
            ((IEntity)player).getTrails().removeIf(Trail::update);
        }
    }
    
    static {
        PlayerTrails.astolfo = new AstolfoAnimation();
    }
    
    public enum modeEn
    {
        Default, 
        Ukraine, 
        RUSSIA;
    }
    
    public static class Trail
    {
        private final Vec3d from;
        private final Vec3d to;
        private final Vec3d color;
        private int ticks;
        private int prevTicks;
        
        public Trail(final Vec3d from, final Vec3d to, final Vec3d color) {
            this.from = from;
            this.to = to;
            this.ticks = 10;
            this.color = color;
        }
        
        public Vec3d interpolate(final float pt) {
            final double x = this.from.x + (this.to.x - this.from.x) * pt - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
            final double y = this.from.y + (this.to.y - this.from.y) * pt - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
            final double z = this.from.z + (this.to.z - this.from.z) * pt - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
            return new Vec3d(x, y, z);
        }
        
        public double animation(final float pt) {
            return (this.prevTicks + (this.ticks - this.prevTicks) * pt) / 10.0;
        }
        
        public boolean update() {
            this.prevTicks = this.ticks;
            return this.ticks-- <= 0;
        }
        
        public Vec3d color() {
            return this.color;
        }
    }
}
