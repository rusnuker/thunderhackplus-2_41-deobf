//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class BreakHighLight extends Module
{
    private final Setting<BreakRenderMode> bRenderMode;
    private final Setting<Float> bRange;
    private final Setting<Boolean> bOutline;
    private final Setting<Boolean> bWireframe;
    private final Setting<Float> bWidth;
    private final Setting<ColorSetting> bOutlineColor;
    private final Setting<ColorSetting> bCrossOutlineColor;
    private final Setting<Boolean> naame;
    private final Setting<Boolean> bFill;
    private final Setting<ColorSetting> bFillColor;
    private final Setting<ColorSetting> bCrossFillColor;
    private final Setting<Boolean> bTracer;
    private final Setting<ColorSetting> bTracerColor;
    private final Setting<Boolean> pOutline;
    private final Setting<Boolean> pWireframe;
    private final Setting<Float> pWidth;
    private final Setting<ColorSetting> pOutlineColor;
    private final Setting<Boolean> pFill;
    private final Setting<ColorSetting> pFillColor;
    
    public BreakHighLight() {
        super("BreakHighLight", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u043b\u043e\u043c\u0430\u043d\u0438\u044f-\u0431\u043b\u043e\u043a\u043e\u0432", Module.Category.RENDER);
        this.bRenderMode = (Setting<BreakRenderMode>)this.register(new Setting("BRenderMove", (T)BreakRenderMode.GROW));
        this.bRange = (Setting<Float>)this.register(new Setting("BRange", (T)15.0f, (T)5.0f, (T)255.0f));
        this.bOutline = (Setting<Boolean>)this.register(new Setting("BOutline", (T)true));
        this.bWireframe = (Setting<Boolean>)this.register(new Setting("BWireframe", (T)false));
        this.bWidth = (Setting<Float>)this.register(new Setting("BWidth", (T)1.5f, (T)1.0f, (T)10.0f));
        this.bOutlineColor = (Setting<ColorSetting>)this.register(new Setting("BOutlineColor", (T)new ColorSetting(-65536)));
        this.bCrossOutlineColor = (Setting<ColorSetting>)this.register(new Setting("BCrossOutlineColor", (T)new ColorSetting(-65536)));
        this.naame = (Setting<Boolean>)this.register(new Setting("Name", (T)true));
        this.bFill = (Setting<Boolean>)this.register(new Setting("BFill", (T)true));
        this.bFillColor = (Setting<ColorSetting>)this.register(new Setting("BFillColor", (T)new ColorSetting(1727987712)));
        this.bCrossFillColor = (Setting<ColorSetting>)this.register(new Setting("BCrossFillColor", (T)new ColorSetting(1727987712)));
        this.bTracer = (Setting<Boolean>)this.register(new Setting("BTracer", (T)false));
        this.bTracerColor = (Setting<ColorSetting>)this.register(new Setting("BTracerColor", (T)new ColorSetting(-65536)));
        this.pOutline = (Setting<Boolean>)this.register(new Setting("POutline", (T)true));
        this.pWireframe = (Setting<Boolean>)this.register(new Setting("PWireframe", (T)false));
        this.pWidth = (Setting<Float>)this.register(new Setting("PWidth", (T)1.5f, (T)1.0f, (T)10.0f));
        this.pOutlineColor = (Setting<ColorSetting>)this.register(new Setting("POutlineColor", (T)new ColorSetting(-16776961)));
        this.pFill = (Setting<Boolean>)this.register(new Setting("PFill", (T)true));
        this.pFillColor = (Setting<ColorSetting>)this.register(new Setting("PFillColor", (T)new ColorSetting(1711276287)));
    }
    
    public static void renderBreakingBB2(final AxisAlignedBB bb, final Color fill, final Color outline) {
        BlockRenderUtil.prepareGL();
        TessellatorUtil.drawBox(bb, fill);
        BlockRenderUtil.releaseGL();
        BlockRenderUtil.prepareGL();
        TessellatorUtil.drawBoundingBox(bb, 1.0, outline);
        BlockRenderUtil.releaseGL();
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (BreakHighLight.mc.player == null || BreakHighLight.mc.world == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        if (BreakHighLight.mc.playerController.getIsHittingBlock()) {
            final float progress = ((IPlayerControllerMP)BreakHighLight.mc.playerController).getCurBlockDamageMP();
            final BlockPos pos = ((IPlayerControllerMP)BreakHighLight.mc.playerController).getCurrentBlock();
            final AxisAlignedBB bb = BreakHighLight.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)BreakHighLight.mc.world, pos).offset(pos);
            switch (this.bRenderMode.getValue()) {
                case GROW: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case SHRINK: {
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case CROSS: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bCrossFillColor.getValue(), this.bCrossOutlineColor.getValue());
                    break;
                }
                default: {
                    this.renderBreakingBB(bb, this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
            }
            if (this.bTracer.getValue()) {
                final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(BreakHighLight.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(BreakHighLight.mc.player.rotationYaw));
                this.renderTracer(eyes.x, eyes.y + BreakHighLight.mc.player.getEyeHeight(), eyes.z, pos.getX() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosX() + 0.5, pos.getY() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosY() + 0.5, pos.getZ() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosZ() + 0.5, this.bTracerColor.getValue().getColor());
            }
        }
        final Entity object;
        BlockPos pos2;
        String name;
        ((IRenderGlobal)BreakHighLight.mc.renderGlobal).getDamagedBlocks().forEach((integer, destroyBlockProgress) -> {
            this.renderGlobalBreakage(destroyBlockProgress);
            object = BreakHighLight.mc.world.getEntityByID((int)integer);
            if (object != null && this.naame.getValue() && !object.getName().equals(BreakHighLight.mc.player.getName())) {
                GlStateManager.pushMatrix();
                pos2 = destroyBlockProgress.getPosition();
                try {
                    RenderUtil.glBillboardDistanceScaled(pos2.getX() + 0.5f, pos2.getY() + 0.5f, pos2.getZ() + 0.5f, (EntityPlayer)BreakHighLight.mc.player, 1.0f);
                }
                catch (Exception ex) {}
                name = object.getName();
                FontRender.drawString3(name, (float)(int)(-(FontRender.getStringWidth(name) / 2.0)), -4.0f, -1);
                GlStateManager.popMatrix();
            }
            return;
        });
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    private void renderGlobalBreakage(final DestroyBlockProgress destroyBlockProgress) {
        if (destroyBlockProgress != null) {
            final BlockPos pos = destroyBlockProgress.getPosition();
            if (BreakHighLight.mc.playerController.getIsHittingBlock() && ((IPlayerControllerMP)BreakHighLight.mc.playerController).getCurrentBlock().equals((Object)pos)) {
                return;
            }
            if (BreakHighLight.mc.player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > this.bRange.getValue()) {
                return;
            }
            final float progress = Math.min(1.0f, destroyBlockProgress.getPartialBlockDamage() / 8.0f);
            final AxisAlignedBB bb = BreakHighLight.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)BreakHighLight.mc.world, pos).offset(pos);
            switch (this.bRenderMode.getValue()) {
                case GROW: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case SHRINK: {
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case CROSS: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bCrossFillColor.getValue(), this.bCrossOutlineColor.getValue());
                    break;
                }
                default: {
                    this.renderBreakingBB(bb, this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
            }
            if (this.bTracer.getValue()) {
                final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(BreakHighLight.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(BreakHighLight.mc.player.rotationYaw));
                this.renderTracer(eyes.x, eyes.y + BreakHighLight.mc.player.getEyeHeight(), eyes.z, pos.getX() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosX() + 0.5, pos.getY() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosY() + 0.5, pos.getZ() - ((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosZ() + 0.5, this.bTracerColor.getValue().getColor());
            }
        }
    }
    
    public void renderPlacingBB(final AxisAlignedBB bb) {
        if (this.pFill.getValue()) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBox(bb, this.pFillColor.getValue().getColorObject());
            BlockRenderUtil.releaseGL();
        }
        if (this.pOutline.getValue()) {
            BlockRenderUtil.prepareGL();
            if (this.pWireframe.getValue()) {
                BlockRenderUtil.drawWireframe(bb.offset(-((IRenderManager)Module.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosZ()), this.pOutlineColor.getValue().getColor(), this.pWidth.getValue());
            }
            else {
                TessellatorUtil.drawBoundingBox(bb, this.pWidth.getValue(), this.pOutlineColor.getValue().getColorObject());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    private void renderBreakingBB(final AxisAlignedBB bb, final ColorSetting fill, final ColorSetting outline) {
        if (this.bFill.getValue()) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBox(bb, fill.getColorObject());
            BlockRenderUtil.releaseGL();
        }
        if (this.bOutline.getValue()) {
            BlockRenderUtil.prepareGL();
            if (this.bWireframe.getValue()) {
                BlockRenderUtil.drawWireframe(bb.offset(-((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)BreakHighLight.mc.getRenderManager()).getRenderPosZ()), outline.getColor(), this.bWidth.getValue());
            }
            else {
                TessellatorUtil.drawBoundingBox(bb, this.bWidth.getValue(), outline.getColorObject());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    private void renderTracer(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer)BreakHighLight.mc.entityRenderer).orientCam(BreakHighLight.mc.getRenderPartialTicks());
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GlStateManager.enableLighting();
    }
    
    private enum BreakRenderMode
    {
        GROW, 
        SHRINK, 
        CROSS, 
        STATIC;
    }
}
