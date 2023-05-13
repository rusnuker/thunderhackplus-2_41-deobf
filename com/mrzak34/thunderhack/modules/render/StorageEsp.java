//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.tileentity.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;

public class StorageEsp extends Module
{
    public final Setting<Float> range;
    public final Setting<Boolean> chest;
    public final Setting<Boolean> dispenser;
    public final Setting<Boolean> shulker;
    public final Setting<Boolean> echest;
    public final Setting<Boolean> furnace;
    public final Setting<Boolean> hopper;
    public final Setting<Boolean> cart;
    public final Setting<Boolean> frame;
    private final Setting<ColorSetting> chestColor;
    private final Setting<ColorSetting> shulkColor;
    private final Setting<ColorSetting> echestColor;
    private final Setting<ColorSetting> frameColor;
    private final Setting<ColorSetting> shulkerframeColor;
    private final Setting<ColorSetting> furnaceColor;
    private final Setting<ColorSetting> hopperColor;
    private final Setting<ColorSetting> dispenserColor;
    private final Setting<ColorSetting> minecartColor;
    public Setting<Mode> mode;
    public final Setting<Float> lineWidth;
    public final Setting<Integer> boxAlpha;
    private final ArrayList<Storage> storages;
    
    public StorageEsp() {
        super("StorageESP", "\u043f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u043a\u043e\u043d\u0442\u0435\u0439\u043d\u0435\u0440\u044b", Module.Category.RENDER);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)50.0f, (T)1.0f, (T)300.0f));
        this.chest = (Setting<Boolean>)this.register(new Setting("Chest", (T)true));
        this.dispenser = (Setting<Boolean>)this.register(new Setting("Dispenser", (T)false));
        this.shulker = (Setting<Boolean>)this.register(new Setting("Shulker", (T)true));
        this.echest = (Setting<Boolean>)this.register(new Setting("Ender Chest", (T)true));
        this.furnace = (Setting<Boolean>)this.register(new Setting("Furnace", (T)false));
        this.hopper = (Setting<Boolean>)this.register(new Setting("Hopper", (T)false));
        this.cart = (Setting<Boolean>)this.register(new Setting("Minecart", (T)false));
        this.frame = (Setting<Boolean>)this.register(new Setting("ItemFrame", (T)false));
        this.chestColor = (Setting<ColorSetting>)this.register(new Setting("ChestColor", (T)new ColorSetting(-2013200640)));
        this.shulkColor = (Setting<ColorSetting>)this.register(new Setting("ShulkerColor", (T)new ColorSetting(-2013200640)));
        this.echestColor = (Setting<ColorSetting>)this.register(new Setting("EChestColor", (T)new ColorSetting(-2013200640)));
        this.frameColor = (Setting<ColorSetting>)this.register(new Setting("FrameColor", (T)new ColorSetting(-2013200640)));
        this.shulkerframeColor = (Setting<ColorSetting>)this.register(new Setting("ShulkFrameColor", (T)new ColorSetting(-2013200640)));
        this.furnaceColor = (Setting<ColorSetting>)this.register(new Setting("FurnaceColor", (T)new ColorSetting(-2013200640)));
        this.hopperColor = (Setting<ColorSetting>)this.register(new Setting("HopperColor", (T)new ColorSetting(-2013200640)));
        this.dispenserColor = (Setting<ColorSetting>)this.register(new Setting("DispenserColor", (T)new ColorSetting(-2013200640)));
        this.minecartColor = (Setting<ColorSetting>)this.register(new Setting("MinecartColor", (T)new ColorSetting(-2013200640)));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.ShaderBox));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() != Mode.Box));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)170, (T)0, (T)255, v -> this.mode.getValue() != Mode.Outline));
        this.storages = new ArrayList<Storage>();
    }
    
    @SubscribeEvent
    public void onRenderingShit(final PreRenderEvent event) {
        final boolean depth = GL11.glIsEnabled(2929);
        GlStateManager.disableDepth();
        if (this.mode.getValue() == Mode.ShaderBox || this.mode.getValue() == Mode.ShaderOutline) {
            this.checkSetupFBO();
        }
        for (final TileEntity tileEntity : StorageEsp.mc.world.loadedTileEntityList) {
            final BlockPos pos;
            if (((tileEntity instanceof TileEntityChest && this.chest.getValue()) || (tileEntity instanceof TileEntityDispenser && this.dispenser.getValue()) || (tileEntity instanceof TileEntityShulkerBox && this.shulker.getValue()) || (tileEntity instanceof TileEntityEnderChest && this.echest.getValue()) || (tileEntity instanceof TileEntityFurnace && this.furnace.getValue()) || (tileEntity instanceof TileEntityHopper && this.hopper.getValue())) && StorageEsp.mc.player.getDistanceSq(pos = tileEntity.getPos()) <= MathUtil.square(this.range.getValue())) {
                int mode = 0;
                if (tileEntity instanceof TileEntityChest) {
                    final TileEntityChest chest = (TileEntityChest)tileEntity;
                    if (chest.adjacentChestZPos != null) {
                        mode = 3;
                    }
                    else if (chest.adjacentChestXPos != null) {
                        mode = 1;
                    }
                    else if (chest.adjacentChestXNeg != null) {
                        mode = 2;
                    }
                    else if (chest.adjacentChestZNeg != null) {
                        mode = 4;
                    }
                }
                this.storages.add(new Storage(pos, this.getTileEntityColor(tileEntity), mode));
            }
        }
        for (final Entity entity : StorageEsp.mc.world.loadedEntityList) {
            final BlockPos pos;
            if (((entity instanceof EntityItemFrame && this.frame.getValue()) || (entity instanceof EntityMinecartChest && this.cart.getValue())) && StorageEsp.mc.player.getDistanceSq(pos = entity.getPosition()) <= MathUtil.square(this.range.getValue())) {
                this.storages.add(new Storage(pos, this.getEntityColor(entity), 0));
            }
        }
        for (final Storage storage : this.storages) {
            if (this.mode.getValue() != Mode.ShaderOutline) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                RenderUtil.drawBoxESP(storage.position, new Color(storage.color), false, new Color(storage.color), this.lineWidth.getValue(), this.mode.getValue() == Mode.Outline || this.mode.getValue() == Mode.BoxOutline, this.mode.getValue() == Mode.ShaderBox || this.mode.getValue() == Mode.Box || this.mode.getValue() == Mode.BoxOutline, this.boxAlpha.getValue(), false, storage.getChest());
                GlStateManager.resetColor();
                GlStateManager.popMatrix();
            }
        }
        if (depth) {
            GlStateManager.enableDepth();
        }
        this.storages.clear();
    }
    
    public int getTileEntityColor(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return this.chestColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return this.echestColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityShulkerBox) {
            return this.shulkColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityFurnace) {
            return this.furnaceColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityHopper) {
            return this.hopperColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityDispenser) {
            return this.dispenserColor.getValue().getColor();
        }
        return -1;
    }
    
    private int getEntityColor(final Entity entity) {
        if (entity instanceof EntityMinecartChest) {
            return this.minecartColor.getValue().getColor();
        }
        if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof ItemShulkerBox) {
            return this.shulkerframeColor.getValue().getColor();
        }
        if (entity instanceof EntityItemFrame && !(((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof ItemShulkerBox)) {
            return this.frameColor.getValue().getColor();
        }
        return -1;
    }
    
    public void checkSetupFBO() {
        final Framebuffer fbo = StorageEsp.mc.getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            this.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    public void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, StorageEsp.mc.displayWidth, StorageEsp.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
    
    public void renderNormal(final float n) {
        RenderHelper.enableStandardItemLighting();
        for (final TileEntity tileEntity : StorageEsp.mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest) && !(tileEntity instanceof TileEntityShulkerBox)) {
                continue;
            }
            GL11.glPushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            TileEntityRendererDispatcher.instance.render(tileEntity, tileEntity.getPos().getX() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), tileEntity.getPos().getY() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), tileEntity.getPos().getZ() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ(), n);
            GL11.glPopMatrix();
        }
    }
    
    public void renderColor(final float n) {
        RenderHelper.enableStandardItemLighting();
        for (final TileEntity tileEntity : StorageEsp.mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest) && !(tileEntity instanceof TileEntityShulkerBox) && !(tileEntity instanceof TileEntityFurnace) && !(tileEntity instanceof TileEntityHopper)) {
                continue;
            }
            this.setColor(new Color(this.getTileEntityColor(tileEntity)));
            TileEntityRendererDispatcher.instance.render(tileEntity, tileEntity.getPos().getX() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX(), tileEntity.getPos().getY() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY(), tileEntity.getPos().getZ() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ(), n);
        }
    }
    
    public void setColor(final Color c) {
        GL11.glColor3f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
    }
    
    public enum Mode
    {
        Outline, 
        Box, 
        BoxOutline, 
        ShaderOutline, 
        ShaderBox;
    }
    
    private class Storage
    {
        BlockPos position;
        int color;
        int chest;
        
        private Storage(final BlockPos pos, final int color, final int chest) {
            this.position = pos;
            this.color = color;
            this.chest = chest;
        }
        
        public int getChest() {
            return this.chest;
        }
        
        public BlockPos getPosition() {
            return this.position;
        }
        
        public int getColor() {
            return this.color;
        }
    }
}
