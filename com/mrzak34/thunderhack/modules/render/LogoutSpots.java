//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.util.math.*;
import java.net.*;
import org.apache.commons.io.*;
import org.json.simple.*;
import java.io.*;
import org.json.simple.parser.*;
import com.mojang.authlib.*;

public class LogoutSpots extends Module
{
    private final Setting<Integer> removeDistance;
    private final Map<String, EntityPlayer> playerCache;
    private final Map<String, PlayerData> logoutCache;
    private final Setting<Float> scaling;
    private final Setting<Boolean> scaleing;
    private final Setting<Float> factor;
    private final Setting<Boolean> smartScale;
    private final Map<String, String> uuidNameCache;
    
    public LogoutSpots() {
        super("LogoutSpots", "Puts Armor on for you.", Module.Category.RENDER);
        this.removeDistance = (Setting<Integer>)this.register(new Setting("RemoveDistance", (T)255, (T)1, (T)2000));
        this.playerCache = (Map<String, EntityPlayer>)Maps.newConcurrentMap();
        this.logoutCache = (Map<String, PlayerData>)Maps.newConcurrentMap();
        this.scaling = (Setting<Float>)this.register(new Setting("Size", (T)0.3f, (T)0.1f, (T)20.0f));
        this.scaleing = (Setting<Boolean>)this.register(new Setting("Scale", (T)false));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)0.3f, (T)0.1f, (T)1.0f));
        this.smartScale = (Setting<Boolean>)this.register(new Setting("SmartScale", (T)false));
        this.uuidNameCache = (Map<String, String>)Maps.newConcurrentMap();
    }
    
    public void onEnable() {
        this.playerCache.clear();
        this.logoutCache.clear();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        try {
            final SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
            if (packet.getEntries().size() <= 1) {
                if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                    packet.getEntries().forEach(data -> {
                        if (!data.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) && data.getProfile().getName() == null && data.getProfile().getId().toString() == "b9523a25-2b04-4a75-bee0-b84027824fe0") {
                            if (data.getProfile().getId().toString() == "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
                                return;
                            }
                        }
                        try {
                            this.onPlayerJoin(data.getProfile().getId().toString());
                        }
                        catch (Exception ex) {}
                    });
                }
                else if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    packet.getEntries().forEach(data2 -> {
                        if (!data2.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) || data2.getProfile().getId() == null || data2.getProfile().getId().toString() != "b9523a25-2b04-4a75-bee0-b84027824fe0" || data2.getProfile().getId().toString() != "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
                            this.onPlayerLeave(data2.getProfile().getId().toString());
                        }
                    });
                }
            }
        }
        catch (Exception ex2) {}
    }
    
    public void onUpdate() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) {
            return;
        }
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player != null) {
                if (player.equals((Object)mc.player)) {
                    continue;
                }
                this.updatePlayerCache(player.getGameProfile().getId().toString(), player);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final String uuid : this.logoutCache.keySet()) {
            final PlayerData data = this.logoutCache.get(uuid);
            if (this.isOutOfRange(data)) {
                this.logoutCache.remove(uuid);
            }
            else {
                data.ghost.prevLimbSwingAmount = 0.0f;
                data.ghost.limbSwing = 0.0f;
                data.ghost.limbSwingAmount = 0.0f;
                data.ghost.hurtTime = 0;
                GlStateManager.pushMatrix();
                final boolean lighting = GL11.glIsEnabled(2896);
                final boolean blend = GL11.glIsEnabled(3042);
                final boolean depthtest = GL11.glIsEnabled(2929);
                GlStateManager.enableLighting();
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                try {
                    mc.getRenderManager().renderEntity((Entity)data.ghost, data.position.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), data.position.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), data.position.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ(), data.ghost.rotationYaw, mc.getRenderPartialTicks(), false);
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
    }
    
    @SubscribeEvent
    public void onRenderPost(final PreRenderEvent event) {
        for (final String uuid : this.logoutCache.keySet()) {
            final PlayerData data = this.logoutCache.get(uuid);
            if (this.isOutOfRange(data)) {
                this.logoutCache.remove(uuid);
            }
            else {
                this.renderNameTag(data.position.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), data.position.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), data.position.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ(), event.getPartialTicks(), data.profile.getName() + " just logout at " + (int)data.position.x + " " + (int)data.position.y + " " + (int)data.position.z);
            }
        }
    }
    
    private void renderNameTag(final double x, final double y, final double z, final float delta, final String displayTag) {
        double tempY = y;
        tempY += 0.7;
        final Entity camera = NameTags.mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = RenderUtil.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = RenderUtil.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = RenderUtil.interpolate(camera.prevPosZ, camera.posZ, delta);
        final double distance = camera.getDistance(x + NameTags.mc.getRenderManager().viewerPosX, y + NameTags.mc.getRenderManager().viewerPosY, z + NameTags.mc.getRenderManager().viewerPosZ);
        final int width = LogoutSpots.mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.scaling.getValue() * (distance * this.factor.getValue())) / 1000.0;
        if (distance <= 8.0 && this.smartScale.getValue()) {
            scale = 0.0245;
        }
        if (!this.scaleing.getValue()) {
            scale = this.scaling.getValue() / 100.0;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(NameTags.mc.getRenderManager().playerViewX, (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        RenderUtil.drawRect((float)(-width - 2), -4.0f, width + 2.0f, 1.5f, 1426063360);
        GlStateManager.disableBlend();
        LogoutSpots.mc.fontRenderer.drawStringWithShadow(displayTag, (float)(-width), -2.0f, -1);
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public void onPlayerLeave(final String uuid2) {
        for (final String uuid3 : this.playerCache.keySet()) {
            if (!uuid3.equals(uuid2)) {
                continue;
            }
            final EntityPlayer player = this.playerCache.get(uuid3);
            final PlayerData data = new PlayerData(player.getPositionVector(), player.getGameProfile(), player);
            if (this.hasPlayerLogged(uuid3)) {
                continue;
            }
            this.logoutCache.put(uuid3, data);
        }
        this.playerCache.clear();
    }
    
    public void onPlayerJoin(final String uuid3) {
        for (final String uuid4 : this.logoutCache.keySet()) {
            if (!uuid4.equals(uuid3)) {
                continue;
            }
            Command.sendMessage(this.playerCache.get(uuid4) + " logged back at  X: " + (int)this.logoutCache.get(uuid4).position.x + " Y: " + (int)this.logoutCache.get(uuid4).position.y + " Z: " + (int)this.logoutCache.get(uuid4).position.z);
            this.logoutCache.remove(uuid4);
        }
        this.playerCache.clear();
    }
    
    private void cleanLogoutCache(final String uuid) {
        this.logoutCache.remove(uuid);
    }
    
    private void updatePlayerCache(final String uuid, final EntityPlayer player) {
        this.playerCache.put(uuid, player);
    }
    
    private boolean hasPlayerLogged(final String uuid) {
        return this.logoutCache.containsKey(uuid);
    }
    
    private boolean isOutOfRange(final PlayerData data) {
        try {
            final Vec3d position = data.position;
            return Minecraft.getMinecraft().player.getDistance(position.x, position.y, position.z) > this.removeDistance.getValue();
        }
        catch (Exception ex) {
            return true;
        }
    }
    
    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject)jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get((Object)"name").toString();
                    }
                }
            }
        }
        catch (IOException ex) {}
        catch (ParseException ex2) {}
        return null;
    }
    
    private class PlayerData
    {
        Vec3d position;
        GameProfile profile;
        EntityPlayer ghost;
        
        public PlayerData(final Vec3d position, final GameProfile profile, final EntityPlayer ghost) {
            this.position = position;
            this.profile = profile;
            this.ghost = ghost;
        }
    }
}
