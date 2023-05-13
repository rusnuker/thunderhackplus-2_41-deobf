//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import net.minecraft.client.renderer.culling.*;
import io.netty.util.internal.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;

public class NewChunks extends Module
{
    public Setting<ColorSetting> color;
    private final ICamera frustum;
    private final Set<ChunkPos> chunks;
    
    public NewChunks() {
        super("NewChunks", "NewChunks", "NewChunks", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(new Color(0.8392157f, 0.3372549f, 0.5764706f, 0.39215687f).hashCode(), false)));
        this.frustum = (ICamera)new Frustum();
        this.chunks = (Set<ChunkPos>)new ConcurrentSet();
    }
    
    public static void drawBox(final AxisAlignedBB box, final int mode, final int color) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(mode, DefaultVertexFormats.POSITION_COLOR);
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final float a = (color >> 24 & 0xFF) / 255.0f;
        buffer.pos(box.minX, box.minY, box.minZ).color(r, g, b, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, 0.0f).endVertex();
        tessellator.draw();
    }
    
    @SubscribeEvent
    public void onReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChunkData) {
            final SPacketChunkData packet = (SPacketChunkData)event.getPacket();
            if (packet.isFullChunk()) {
                return;
            }
            final ChunkPos newChunk = new ChunkPos(packet.getChunkX(), packet.getChunkZ());
            this.chunks.add(newChunk);
        }
    }
    
    @SubscribeEvent
    public void onRender(final Render3DEvent event) {
        if (NewChunks.mc.getRenderViewEntity() == null) {
            return;
        }
        this.frustum.setPosition(NewChunks.mc.getRenderViewEntity().posX, NewChunks.mc.getRenderViewEntity().posY, NewChunks.mc.getRenderViewEntity().posZ);
        GlStateManager.pushMatrix();
        RenderUtil.beginRender();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.glLineWidth(2.0f);
        for (final ChunkPos chunk : this.chunks) {
            final AxisAlignedBB chunkBox = new AxisAlignedBB((double)chunk.getXStart(), 0.0, (double)chunk.getZStart(), (double)chunk.getXEnd(), 0.0, (double)chunk.getZEnd());
            GlStateManager.pushMatrix();
            if (this.frustum.isBoundingBoxInFrustum(chunkBox)) {
                final double x = NewChunks.mc.player.lastTickPosX + (NewChunks.mc.player.posX - NewChunks.mc.player.lastTickPosX) * event.getPartialTicks();
                final double y = NewChunks.mc.player.lastTickPosY + (NewChunks.mc.player.posY - NewChunks.mc.player.lastTickPosY) * event.getPartialTicks();
                final double z = NewChunks.mc.player.lastTickPosZ + (NewChunks.mc.player.posZ - NewChunks.mc.player.lastTickPosZ) * event.getPartialTicks();
                drawBox(chunkBox.offset(-x, -y, -z), 3, this.color.getValue().getColor());
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        RenderUtil.endRender();
        GlStateManager.popMatrix();
    }
    
    public void onEnable() {
        this.chunks.clear();
    }
}
