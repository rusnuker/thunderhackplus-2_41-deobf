//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.*;
import java.util.concurrent.atomic.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import java.awt.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;

public class Blink extends Module
{
    public Setting<Float> circleWidth;
    public Setting<ColorSetting> circleColor;
    private final Setting<Boolean> pulse;
    private final Setting<Boolean> strict;
    private final Setting<Float> factor;
    private final Setting<Boolean> render;
    private final Setting<Boolean> fill;
    private final Queue<Packet> storedPackets;
    private Vec3d lastPos;
    private final AtomicBoolean sending;
    
    public Blink() {
        super("Blink", "\u041e\u0442\u043c\u0435\u043d\u044f\u0435\u0442 \u043f\u0430\u043a\u0435\u0442\u044b-\u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f", Module.Category.MISC);
        this.circleWidth = (Setting<Float>)this.register(new Setting("Width", (T)2.5f, (T)5.0f, (T)0.1f));
        this.circleColor = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(869950564, true)));
        this.pulse = (Setting<Boolean>)this.register(new Setting("Pulse", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)1.0f, (T)0.1f, (T)10.0f));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.fill = (Setting<Boolean>)this.register(new Setting("Fill", (T)true));
        this.storedPackets = new LinkedList<Packet>();
        this.lastPos = new Vec3d((Vec3i)BlockPos.ORIGIN);
        this.sending = new AtomicBoolean(false);
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (Blink.mc.player == null || Blink.mc.world == null) {
            return;
        }
        if (this.render.getValue() && this.lastPos != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GL11.glEnable(3008);
            GL11.glBlendFunc(770, 771);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            final IRenderManager renderManager = (IRenderManager)Blink.mc.getRenderManager();
            final float[] hsb = Color.RGBtoHSB(this.circleColor.getValue().getRed(), this.circleColor.getValue().getGreen(), this.circleColor.getValue().getBlue(), null);
            float hue;
            final float initialHue = hue = System.currentTimeMillis() % 7200L / 7200.0f;
            int rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
            final ArrayList<Vec3d> vecs = new ArrayList<Vec3d>();
            final double x = this.lastPos.x - renderManager.getRenderPosX();
            final double y = this.lastPos.y - renderManager.getRenderPosY();
            final double z = this.lastPos.z - renderManager.getRenderPosZ();
            GL11.glShadeModel(7425);
            GlStateManager.disableCull();
            GL11.glLineWidth((float)this.circleWidth.getValue());
            GL11.glBegin(1);
            for (int i = 0; i <= 360; ++i) {
                final Vec3d vec = new Vec3d(x + Math.sin(i * 3.141592653589793 / 180.0) * 0.5, y + 0.01, z + Math.cos(i * 3.141592653589793 / 180.0) * 0.5);
                vecs.add(vec);
            }
            for (int j = 0; j < vecs.size() - 1; ++j) {
                final int red = rgb >> 16 & 0xFF;
                final int green = rgb >> 8 & 0xFF;
                final int blue = rgb & 0xFF;
                if (this.circleColor.getValue().isCycle()) {
                    GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, ((boolean)this.fill.getValue()) ? 1.0f : (this.circleColor.getValue().getAlpha() / 255.0f));
                }
                else {
                    GL11.glColor4f(this.circleColor.getValue().getRed() / 255.0f, this.circleColor.getValue().getGreen() / 255.0f, this.circleColor.getValue().getBlue() / 255.0f, ((boolean)this.fill.getValue()) ? 1.0f : (this.circleColor.getValue().getAlpha() / 255.0f));
                }
                GL11.glVertex3d(vecs.get(j).x, vecs.get(j).y, vecs.get(j).z);
                GL11.glVertex3d(vecs.get(j + 1).x, vecs.get(j + 1).y, vecs.get(j + 1).z);
                hue += 0.0027777778f;
                rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
            }
            GL11.glEnd();
            if (this.fill.getValue()) {
                hue = initialHue;
                GL11.glBegin(9);
                for (int j = 0; j < vecs.size() - 1; ++j) {
                    final int red = rgb >> 16 & 0xFF;
                    final int green = rgb >> 8 & 0xFF;
                    final int blue = rgb & 0xFF;
                    if (this.circleColor.getValue().isCycle()) {
                        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, this.circleColor.getValue().getAlpha() / 255.0f);
                    }
                    else {
                        GL11.glColor4f(this.circleColor.getValue().getRed() / 255.0f, this.circleColor.getValue().getGreen() / 255.0f, this.circleColor.getValue().getBlue() / 255.0f, this.circleColor.getValue().getAlpha() / 255.0f);
                    }
                    GL11.glVertex3d(vecs.get(j).x, vecs.get(j).y, vecs.get(j).z);
                    GL11.glVertex3d(vecs.get(j + 1).x, vecs.get(j + 1).y, vecs.get(j + 1).z);
                    hue += 0.0027777778f;
                    rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3008);
            GlStateManager.enableCull();
            GL11.glShadeModel(7424);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (fullNullCheck()) {
            return;
        }
        final Packet packet = event.getPacket();
        if (this.sending.get()) {
            return;
        }
        if (this.pulse.getValue()) {
            if (event.getPacket() instanceof CPacketPlayer) {
                if (this.strict.getValue() && !((CPacketPlayer)event.getPacket()).isOnGround()) {
                    this.sending.set(true);
                    while (!this.storedPackets.isEmpty()) {
                        final Packet pckt = this.storedPackets.poll();
                        Blink.mc.player.connection.sendPacket(pckt);
                        if (pckt instanceof CPacketPlayer) {
                            this.lastPos = new Vec3d(((CPacketPlayer)pckt).getX(Blink.mc.player.posX), ((CPacketPlayer)pckt).getY(Blink.mc.player.posY), ((CPacketPlayer)pckt).getZ(Blink.mc.player.posZ));
                        }
                    }
                    this.sending.set(false);
                    this.storedPackets.clear();
                }
                else {
                    event.setCanceled(true);
                    this.storedPackets.add(event.getPacket());
                }
            }
        }
        else if (!(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketConfirmTeleport) && !(packet instanceof CPacketKeepAlive) && !(packet instanceof CPacketTabComplete) && !(packet instanceof CPacketClientStatus)) {
            event.setCanceled(true);
            this.storedPackets.add(event.getPacket());
        }
    }
    
    public void onUpdate() {
        if (Blink.mc.player == null || Blink.mc.world == null || Blink.mc.isIntegratedServerRunning()) {
            this.toggle();
            return;
        }
        if (this.pulse.getValue() && Blink.mc.player != null && Blink.mc.world != null && this.storedPackets.size() >= this.factor.getValue() * 10.0f) {
            this.sending.set(true);
            while (!this.storedPackets.isEmpty()) {
                final Packet pckt = this.storedPackets.poll();
                Blink.mc.player.connection.sendPacket(pckt);
                if (pckt instanceof CPacketPlayer) {
                    this.lastPos = new Vec3d(((CPacketPlayer)pckt).getX(Blink.mc.player.posX), ((CPacketPlayer)pckt).getY(Blink.mc.player.posY), ((CPacketPlayer)pckt).getZ(Blink.mc.player.posZ));
                }
            }
            this.sending.set(false);
            this.storedPackets.clear();
        }
    }
    
    public void onDisable() {
        if (Blink.mc.world == null || Blink.mc.player == null) {
            return;
        }
        while (!this.storedPackets.isEmpty()) {
            Blink.mc.player.connection.sendPacket((Packet)this.storedPackets.poll());
        }
    }
    
    public void onEnable() {
        if (Blink.mc.player == null || Blink.mc.world == null || Blink.mc.isIntegratedServerRunning()) {
            this.toggle();
            return;
        }
        this.lastPos = Blink.mc.player.getPositionVector();
        this.sending.set(false);
        this.storedPackets.clear();
    }
}
