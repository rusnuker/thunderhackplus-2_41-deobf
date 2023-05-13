//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import java.util.concurrent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.util.math.*;
import java.util.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.projectile.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;

public class PearlESP extends Module
{
    public static final Vec3d ORIGIN;
    private final Setting<ColorSetting> color;
    private final Setting<ColorSetting> color2;
    private final Setting<ColorSetting> TriangleColor;
    public Setting<Float> width2;
    public Setting<Boolean> arrows;
    public Setting<Boolean> pearls;
    public Setting<Boolean> snowballs;
    public Setting<Integer> time;
    public Map<Entity, List<PredictedPosition>> entAndTrail;
    protected Map<Integer, TimeAnimation> ids;
    protected Map<Integer, List<Trace>> traceLists;
    protected Map<Integer, Trace> traces;
    private final Setting<Boolean> triangleESP;
    private final Setting<Boolean> glow;
    private final Setting<Float> width;
    private final Setting<Float> radius;
    private final Setting<Float> rad22ius;
    private final Setting<Float> tracerA;
    private final Setting<Integer> glowe;
    private final Setting<Integer> glowa;
    private final Setting<Mode> mode;
    
    public PearlESP() {
        super("Predictions", "Predictions", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color1", (T)new ColorSetting(-2013200640)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-2013200640)));
        this.TriangleColor = (Setting<ColorSetting>)this.register(new Setting("TriangleColor", (T)new ColorSetting(-2013200640)));
        this.width2 = (Setting<Float>)this.register(new Setting("Width", (T)1.6f, (T)0.1f, (T)10.0f));
        this.arrows = (Setting<Boolean>)this.register(new Setting("Arrows", (T)false));
        this.pearls = (Setting<Boolean>)this.register(new Setting("Pearls", (T)false));
        this.snowballs = (Setting<Boolean>)this.register(new Setting("Snowballs", (T)false));
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)1, (T)1, (T)10));
        this.entAndTrail = new HashMap<Entity, List<PredictedPosition>>();
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
        this.triangleESP = (Setting<Boolean>)this.register(new Setting("TriangleESP", (T)true));
        this.glow = (Setting<Boolean>)this.register(new Setting("Glow", (T)true));
        this.width = (Setting<Float>)this.register(new Setting("TracerHeight", (T)2.5f, (T)0.1f, (T)5.0f));
        this.radius = (Setting<Float>)this.register(new Setting("Radius", (T)50.0f, (T)(-50.0f), (T)50.0f));
        this.rad22ius = (Setting<Float>)this.register(new Setting("TracerDown", (T)3.0f, (T)0.1f, (T)20.0f));
        this.tracerA = (Setting<Float>)this.register(new Setting("TracerWidth", (T)0.5f, (T)0.0f, (T)8.0f));
        this.glowe = (Setting<Integer>)this.register(new Setting("GlowRadius", (T)10, (T)1, (T)20));
        this.glowa = (Setting<Integer>)this.register(new Setting("GlowAlpha", (T)150, (T)0, (T)255));
        this.mode = (Setting<Mode>)this.register(new Setting("LineMode", (T)Mode.Mode1));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.mode.getValue() == Mode.Mode2) {
            this.PlayerToPearl(event);
        }
        else if (this.mode.getValue() == Mode.Mode1) {
            this.PearlToDest(event);
        }
        else if (this.mode.getValue() == Mode.Both) {
            this.PlayerToPearl(event);
            this.PearlToDest(event);
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        if (!this.triangleESP.getValue()) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(PearlESP.mc);
        for (final Entity entity : PearlESP.mc.world.loadedEntityList) {
            if (entity != null) {
                if (!(entity instanceof EntityEnderPearl)) {
                    continue;
                }
                final float xOffset = sr.getScaledWidth() / 2.0f;
                final float yOffset = sr.getScaledHeight() / 2.0f;
                GlStateManager.pushMatrix();
                final float yaw = RadarRewrite.getRotations(entity) - PearlESP.mc.player.rotationYaw;
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                this.drawTriangle(xOffset, yOffset - this.radius.getValue(), this.width.getValue() * 5.0f, this.TriangleColor.getValue().getColor());
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(-yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.popMatrix();
            }
        }
    }
    
    public void onEnable() {
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            for (final int id : ((SPacketDestroyEntities)event.getPacket()).getEntityIDs()) {
                if (this.ids.containsKey(id)) {
                    this.ids.get(id).play();
                }
            }
        }
        if (event.getPacket() instanceof SPacketSpawnObject && ((this.pearls.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 65) || (this.arrows.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 60) || (this.snowballs.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 61))) {
            final TimeAnimation animation = new TimeAnimation(this.time.getValue() * 1000, 0.0, this.color.getValue().getAlpha(), false, AnimationMode.LINEAR);
            animation.stop();
            this.ids.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), animation);
            this.traceLists.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), new ArrayList<Trace>());
            try {
                this.traces.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), new Trace(0, null, PearlESP.mc.world.provider.getDimensionType(), new Vec3d(((SPacketSpawnObject)event.getPacket()).getX(), ((SPacketSpawnObject)event.getPacket()).getY(), ((SPacketSpawnObject)event.getPacket()).getZ()), new ArrayList<Trace.TracePos>()));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void PlayerToPearl(final Render3DEvent event) {
        if (PearlESP.mc.world == null || PearlESP.mc.player == null) {
            return;
        }
        for (final Map.Entry<Integer, List<Trace>> entry : this.traceLists.entrySet()) {
            GL11.glPushAttrib(1048575);
            GL11.glPushMatrix();
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(2884);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4353);
            GL11.glDisable(2896);
            GL11.glLineWidth((float)this.width2.getValue());
            final TimeAnimation animation = this.ids.get(entry.getKey());
            animation.add(event.getPartialTicks());
            GL11.glColor4f((float)this.color.getValue().getRed(), (float)this.color.getValue().getGreen(), (float)this.color.getValue().getBlue(), MathHelper.clamp((float)(this.color.getValue().getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            entry.getValue().forEach(trace -> {
                GL11.glBegin(3);
                trace.getTrace().forEach(this::renderVec);
                GL11.glEnd();
                return;
            });
            GL11.glColor4f((float)this.color.getValue().getRed(), (float)this.color.getValue().getGreen(), (float)this.color.getValue().getBlue(), MathHelper.clamp((float)(this.color.getValue().getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            GL11.glBegin(3);
            final Trace trace2 = this.traces.get(entry.getKey());
            if (trace2 != null) {
                trace2.getTrace().forEach(this::renderVec);
            }
            GL11.glEnd();
            GL11.glEnable(2896);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glDepthMask(true);
            GL11.glCullFace(1029);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }
    
    private void renderVec(final Trace.TracePos tracePos) {
        final double x = tracePos.getPos().x - ((IRenderManager)PearlESP.mc.getRenderManager()).getRenderPosX();
        final double y = tracePos.getPos().y - ((IRenderManager)PearlESP.mc.getRenderManager()).getRenderPosY();
        final double z = tracePos.getPos().z - ((IRenderManager)PearlESP.mc.getRenderManager()).getRenderPosZ();
        GL11.glVertex3d(x, y, z);
    }
    
    public void onUpdate() {
        if (PearlESP.mc.world == null) {
            return;
        }
        if (this.ids.keySet().isEmpty()) {
            return;
        }
        for (final Integer id : this.ids.keySet()) {
            if (id == null) {
                continue;
            }
            if (PearlESP.mc.world.loadedEntityList == null) {
                return;
            }
            if (PearlESP.mc.world.loadedEntityList.isEmpty()) {
                return;
            }
            Trace idTrace = this.traces.get(id);
            final Entity entity = PearlESP.mc.world.getEntityByID((int)id);
            if (entity != null) {
                final Vec3d vec = entity.getPositionVector();
                if (vec == null) {
                    continue;
                }
                if (vec.equals((Object)PearlESP.ORIGIN)) {
                    continue;
                }
                if (!this.traces.containsKey(id) || idTrace == null) {
                    this.traces.put(id, new Trace(0, null, PearlESP.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                    idTrace = this.traces.get(id);
                }
                List<Trace.TracePos> trace = idTrace.getTrace();
                final Vec3d vec3d = trace.isEmpty() ? vec : trace.get(trace.size() - 1).getPos();
                if (!trace.isEmpty() && (vec.distanceTo(vec3d) > 100.0 || idTrace.getType() != PearlESP.mc.world.provider.getDimensionType())) {
                    this.traceLists.get(id).add(idTrace);
                    trace = new ArrayList<Trace.TracePos>();
                    this.traces.put(id, new Trace(this.traceLists.get(id).size() + 1, null, PearlESP.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                }
                if (trace.isEmpty() || !vec.equals((Object)vec3d)) {
                    trace.add(new Trace.TracePos(vec));
                }
            }
            final TimeAnimation animation = this.ids.get(id);
            if (entity instanceof EntityArrow && (entity.onGround || entity.collided || !entity.isAirBorne)) {
                animation.play();
            }
            if (animation == null || this.color.getValue().getAlpha() - animation.getCurrent() > 0.0) {
                continue;
            }
            animation.stop();
            this.ids.remove(id);
            this.traceLists.remove(id);
            this.traces.remove(id);
        }
    }
    
    public void drawTriangle(final float x, final float y, final float size, final int color) {
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        final boolean depth = GL11.glIsEnabled(2929);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        RadarRewrite.hexColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        RadarRewrite.hexColor(ColorUtil.darker(new Color(color), 0.8f).getRGB());
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)(x + size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        RadarRewrite.hexColor(ColorUtil.darker(new Color(color), 0.6f).getRGB());
        GL11.glBegin(7);
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)(x + size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
        if (this.glow.getValue()) {
            Drawable.drawBlurredShadow(x - size * this.tracerA.getValue(), y, x + size * this.tracerA.getValue() - (x - size * this.tracerA.getValue()), size, this.glowe.getValue(), DrawHelper.injectAlpha(new Color(color), this.glowa.getValue()));
        }
        if (depth) {
            GL11.glEnable(2929);
        }
    }
    
    public void draw(final List<PredictedPosition> list, final Entity entity) {
        boolean first = true;
        final boolean depth = GL11.glIsEnabled(2929);
        final boolean texture = GL11.glIsEnabled(3553);
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glColor4f(this.color2.getValue().getRed() / 255.0f, this.color2.getValue().getGreen() / 255.0f, this.color2.getValue().getBlue() / 255.0f, this.color2.getValue().getAlpha() / 255.0f);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glLineWidth(0.5f);
        GL11.glBegin(3);
        for (int i = 0; i < list.size(); ++i) {
            final PredictedPosition pp = list.get(i);
            Vec3d v = new Vec3d(pp.pos.x, pp.pos.y, pp.pos.z);
            if (list.size() > 2 && first) {
                final PredictedPosition next = list.get(i + 1);
                v = v.add((next.pos.x - v.x) * PearlESP.mc.getRenderPartialTicks(), (next.pos.y - v.y) * PearlESP.mc.getRenderPartialTicks(), (next.pos.z - v.z) * PearlESP.mc.getRenderPartialTicks());
            }
            GL11.glVertex3d(v.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), v.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), v.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ());
            first = false;
        }
        list.removeIf(w -> w.tick < entity.ticksExisted);
        GL11.glEnd();
        if (depth) {
            GL11.glEnable(2929);
        }
        if (texture) {
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
    
    public void PearlToDest(final Render3DEvent event) {
        for (final Entity entity : PearlESP.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderPearl && this.entAndTrail.get(entity) != null) {
                this.draw(this.entAndTrail.get(entity), entity);
            }
            if (entity instanceof EntityArrow && this.entAndTrail.get(entity) != null) {
                this.draw(this.entAndTrail.get(entity), entity);
            }
        }
    }
    
    static {
        ORIGIN = new Vec3d(8.0, 64.0, 8.0);
    }
    
    public enum Mode
    {
        NONE, 
        Mode1, 
        Mode2, 
        Both;
    }
    
    public static class PredictedPosition
    {
        public Color color;
        public Vec3d pos;
        public int tick;
    }
}
