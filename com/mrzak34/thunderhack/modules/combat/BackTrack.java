//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.events.*;
import java.util.function.*;
import net.minecraft.client.model.*;
import net.minecraft.util.math.*;

public class BackTrack extends Module
{
    private final Setting<RenderMode> renderMode;
    private final Setting<ColorSetting> color1;
    private final Setting<ColorSetting> color2;
    private final Setting<Integer> btticks;
    private final Setting<Boolean> hlaura;
    private final Setting<Boolean> holdPackets;
    long skip_packet_ka;
    long skip_packet_ct;
    long skip_packet_cwt;
    
    public BackTrack() {
        super("BackTrack", "\u043e\u0442\u043a\u0430\u0442\u044b\u0432\u0430\u0435\u0442 \u043f\u043e\u0437\u0438\u0446\u0438\u044e-\u0432\u0440\u0430\u0433\u043e\u0432", "rolls back the-position of enemies", Category.COMBAT);
        this.renderMode = (Setting<RenderMode>)this.register(new Setting("RenderMode", (T)RenderMode.Chams));
        this.color1 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2009289807)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("HighLightColor", (T)new ColorSetting(-2009289807)));
        this.btticks = (Setting<Integer>)this.register(new Setting("TrackTicks", (T)5, (T)1, (T)15));
        this.hlaura = (Setting<Boolean>)this.register(new Setting("HighLightAura", (T)true));
        this.holdPackets = (Setting<Boolean>)this.register(new Setting("ServerSync", (T)true));
    }
    
    @SubscribeEvent
    public void onPreRenderEvent(final PreRenderEvent event) {
        synchronized (this) {
            for (final EntityPlayer entity : BackTrack.mc.world.playerEntities) {
                if (entity == BackTrack.mc.player) {
                    continue;
                }
                if (((IEntity)entity).getPosition_history().size() <= 0) {
                    continue;
                }
                for (int i = 0; i < ((IEntity)entity).getPosition_history().size(); ++i) {
                    GlStateManager.pushMatrix();
                    if (Aura.bestBtBox != ((IEntity)entity).getPosition_history().get(i) && this.hlaura.getValue()) {
                        if (this.renderMode.getValue() == RenderMode.Box) {
                            RenderUtil.drawBoundingBox(((IEntity)entity).getPosition_history().get(i), 1.0, this.color1.getValue().getColorObject());
                        }
                        else if (this.renderMode.getValue() == RenderMode.Chams) {
                            RenderUtil.renderEntity(((IEntity)entity).getPosition_history().get(i), (ModelBase)((IEntity)entity).getPosition_history().get(i).modelPlayer, ((IEntity)entity).getPosition_history().get(i).limbSwing, ((IEntity)entity).getPosition_history().get(i).limbSwingAmount, ((IEntity)entity).getPosition_history().get(i).Yaw, ((IEntity)entity).getPosition_history().get(i).Pitch, (EntityLivingBase)((IEntity)entity).getPosition_history().get(i).ent, this.color1.getValue().getColorObject());
                        }
                        else if (this.renderMode.getValue() == RenderMode.Ghost) {
                            GlStateManager.pushMatrix();
                            final boolean lighting = GL11.glIsEnabled(2896);
                            final boolean blend = GL11.glIsEnabled(3042);
                            final boolean depthtest = GL11.glIsEnabled(2929);
                            GlStateManager.enableLighting();
                            GlStateManager.enableBlend();
                            GlStateManager.enableDepth();
                            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                            try {
                                BackTrack.mc.getRenderManager().renderEntity((Entity)entity, ((IEntity)entity).getPosition_history().get(i).position.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), ((IEntity)entity).getPosition_history().get(i).position.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), ((IEntity)entity).getPosition_history().get(i).position.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ(), ((IEntity)entity).getPosition_history().get(i).Yaw, BackTrack.mc.getRenderPartialTicks(), false);
                            }
                            catch (Exception ex) {}
                            if (!depthtest) {
                                GlStateManager.disableDepth();
                            }
                            if (!lighting) {
                                GlStateManager.disableLighting();
                            }
                            if (!blend) {
                                GlStateManager.disableBlend();
                            }
                            GlStateManager.popMatrix();
                        }
                    }
                    else if (this.renderMode.getValue() == RenderMode.Box) {
                        RenderUtil.drawBoundingBox(((IEntity)entity).getPosition_history().get(i), 1.0, this.color2.getValue().getColorObject());
                    }
                    else if (this.renderMode.getValue() == RenderMode.Chams) {
                        RenderUtil.renderEntity(((IEntity)entity).getPosition_history().get(i), (ModelBase)((IEntity)entity).getPosition_history().get(i).modelPlayer, ((IEntity)entity).getPosition_history().get(i).limbSwing, ((IEntity)entity).getPosition_history().get(i).limbSwingAmount, ((IEntity)entity).getPosition_history().get(i).Yaw, ((IEntity)entity).getPosition_history().get(i).Pitch, (EntityLivingBase)((IEntity)entity).getPosition_history().get(i).ent, this.color2.getValue().getColorObject());
                    }
                    else if (this.renderMode.getValue() == RenderMode.Ghost) {
                        GlStateManager.pushMatrix();
                        final boolean lighting = GL11.glIsEnabled(2896);
                        final boolean blend = GL11.glIsEnabled(3042);
                        final boolean depthtest = GL11.glIsEnabled(2929);
                        GlStateManager.enableLighting();
                        GlStateManager.enableBlend();
                        GlStateManager.enableDepth();
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.1f);
                        try {
                            BackTrack.mc.getRenderManager().renderEntity((Entity)entity, ((IEntity)entity).getPosition_history().get(i).position.x, ((IEntity)entity).getPosition_history().get(i).position.y, ((IEntity)entity).getPosition_history().get(i).position.z, ((IEntity)entity).getPosition_history().get(i).Yaw, BackTrack.mc.getRenderPartialTicks(), false);
                        }
                        catch (Exception ex2) {}
                        if (!depthtest) {
                            GlStateManager.disableDepth();
                        }
                        if (!lighting) {
                            GlStateManager.disableLighting();
                        }
                        if (!blend) {
                            GlStateManager.disableBlend();
                        }
                        GlStateManager.popMatrix();
                    }
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (!this.holdPackets.getValue() || Module.fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof CPacketKeepAlive) {
            if (((CPacketKeepAlive)event.getPacket()).getKey() == this.skip_packet_ka) {
                return;
            }
            event.setCanceled(true);
            ThreadUtil.run(() -> {
                this.skip_packet_ka = ((CPacketKeepAlive)event.getPacket()).getKey();
                BackTrack.mc.player.connection.sendPacket(event.getPacket());
                return;
            }, this.btticks.getValue() * 50L);
        }
        if (event.getPacket() instanceof CPacketConfirmTransaction) {
            if (((CPacketConfirmTransaction)event.getPacket()).getUid() == this.skip_packet_ct) {
                return;
            }
            if (((CPacketConfirmTransaction)event.getPacket()).getWindowId() == this.skip_packet_cwt) {
                return;
            }
            event.setCanceled(true);
            ThreadUtil.run(() -> {
                this.skip_packet_ct = ((CPacketConfirmTransaction)event.getPacket()).getUid();
                this.skip_packet_cwt = ((CPacketConfirmTransaction)event.getPacket()).getWindowId();
                BackTrack.mc.player.connection.sendPacket(event.getPacket());
            }, this.btticks.getValue() * 50L);
        }
    }
    
    @SubscribeEvent
    public void onEntityMove(final EventEntityMove e) {
        if (e.ctx() == BackTrack.mc.player) {
            return;
        }
        if (e.ctx() instanceof EntityPlayer && e.ctx() != null) {
            final EntityPlayer a = (EntityPlayer)e.ctx();
            ((IEntity)a).getPosition_history().add(new Box(e.ctx().getPositionVector(), this.btticks.getValue(), a.limbSwing, a.limbSwingAmount, a.rotationYaw, a.rotationPitch, (EntityPlayer)e.ctx()));
        }
    }
    
    @Override
    public void onUpdate() {
        for (final EntityPlayer player : BackTrack.mc.world.playerEntities) {
            ((IEntity)player).getPosition_history().removeIf(Box::update);
        }
    }
    
    public enum RenderMode
    {
        Box, 
        Chams, 
        Ghost, 
        None;
    }
    
    public static class Box
    {
        private final ModelPlayer modelPlayer;
        private final Vec3d position;
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float Yaw;
        private final float Pitch;
        private final EntityPlayer ent;
        private int ticks;
        
        public Box(final Vec3d position, final int ticks, final float limbswing, final float limbSwingAmount, final float Yaw, final float Pitch, final EntityPlayer ent) {
            this.position = position;
            this.ticks = ticks;
            this.modelPlayer = new ModelPlayer(0.0f, false);
            this.limbSwing = limbswing;
            this.limbSwingAmount = limbSwingAmount;
            this.Pitch = Pitch;
            this.Yaw = Yaw;
            this.ent = ent;
        }
        
        public ModelPlayer getModelPlayer() {
            return this.modelPlayer;
        }
        
        public EntityPlayer getEnt() {
            return this.ent;
        }
        
        public int getTicks() {
            return this.ticks;
        }
        
        public boolean update() {
            return this.ticks-- <= 0;
        }
        
        public Vec3d getPosition() {
            return this.position;
        }
        
        public float getLimbSwing() {
            return this.limbSwing;
        }
        
        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }
        
        public float getYaw() {
            return this.Yaw;
        }
        
        public float getPitch() {
            return this.Pitch;
        }
    }
}
