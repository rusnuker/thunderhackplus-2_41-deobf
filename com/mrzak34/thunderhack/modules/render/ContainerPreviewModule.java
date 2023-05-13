//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;

public class ContainerPreviewModule extends Module
{
    public Setting<Integer> av;
    public Setting<Integer> bv;
    public Setting<Integer> colorr;
    public Setting<Integer> colorg;
    public Setting<Integer> colorb;
    public Setting<Integer> colora;
    public ScaledResolution scaledResolution;
    private final HashMap<BlockPos, ArrayList<ItemStack>> PosItems;
    private int TotalSlots;
    
    public ContainerPreviewModule() {
        super("ContainerPrev", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0441\u043e\u0434\u0435\u0440\u0436\u0438\u043c\u043e\u0435-\u043a\u043e\u043d\u0442\u0435\u0439\u043d\u0435\u0440\u0430", Module.Category.RENDER);
        this.av = (Setting<Integer>)this.register(new Setting("x", (T)256, (T)0, (T)1500));
        this.bv = (Setting<Integer>)this.register(new Setting("y", (T)256, (T)0, (T)1500));
        this.colorr = (Setting<Integer>)this.register(new Setting("Red", (T)100, (T)0, (T)255));
        this.colorg = (Setting<Integer>)this.register(new Setting("Green", (T)100, (T)0, (T)255));
        this.colorb = (Setting<Integer>)this.register(new Setting("Blue", (T)100, (T)0, (T)255));
        this.colora = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)0, (T)255));
        this.PosItems = new HashMap<BlockPos, ArrayList<ItemStack>>();
        this.TotalSlots = 0;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketWindowItems) {
            final RayTraceResult ray = ContainerPreviewModule.mc.objectMouseOver;
            if (ray == null) {
                return;
            }
            if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            }
            final IBlockState l_State = ContainerPreviewModule.mc.world.getBlockState(ray.getBlockPos());
            if (l_State == null) {
                return;
            }
            if (l_State.getBlock() != Blocks.CHEST && !(l_State.getBlock() instanceof BlockShulkerBox)) {
                return;
            }
            final SPacketWindowItems l_Packet = (SPacketWindowItems)event.getPacket();
            final BlockPos blockpos = ray.getBlockPos();
            this.PosItems.remove(blockpos);
            final ArrayList<ItemStack> l_List = new ArrayList<ItemStack>();
            for (int i = 0; i < l_Packet.getItemStacks().size(); ++i) {
                final ItemStack itemStack = l_Packet.getItemStacks().get(i);
                if (itemStack != null) {
                    if (i >= this.TotalSlots) {
                        break;
                    }
                    l_List.add(itemStack);
                }
            }
            this.PosItems.put(blockpos, l_List);
        }
        else if (event.getPacket() instanceof SPacketOpenWindow) {
            final SPacketOpenWindow l_Packet2 = (SPacketOpenWindow)event.getPacket();
            this.TotalSlots = l_Packet2.getSlotCount();
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent p_Event) {
        final RayTraceResult ray = ContainerPreviewModule.mc.objectMouseOver;
        if (ray == null) {
            return;
        }
        if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        if (!this.PosItems.containsKey(ray.getBlockPos())) {
            return;
        }
        final BlockPos l_Pos = ray.getBlockPos();
        final ArrayList<ItemStack> l_Items = this.PosItems.get(l_Pos);
        if (l_Items == null) {
            return;
        }
        final IBlockState pan4ur = ContainerPreviewModule.mc.world.getBlockState(ray.getBlockPos());
        int l_I = 0;
        int l_Y = -20;
        int x = 0;
        for (final ItemStack stack : l_Items) {
            if (stack != null) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderHelper.enableGUIStandardItemLighting();
                RenderUtil.drawSmoothRect((float)(this.av.getValue() - 3), (float)(this.bv.getValue() - 50), (float)(this.av.getValue() + 150), (pan4ur.getBlock() != Blocks.CHEST) ? ((float)this.bv.getValue()) : ((float)(this.bv.getValue() + 48)), new Color(this.colorr.getValue(), this.colorg.getValue(), this.colorb.getValue(), this.colora.getValue()).getRGB());
                GlStateManager.translate((float)(x + this.av.getValue()), (float)(l_Y + ContainerPreviewModule.mc.fontRenderer.FONT_HEIGHT - 19 + this.bv.getValue()), 0.0f);
                ContainerPreviewModule.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
                ContainerPreviewModule.mc.getRenderItem().renderItemOverlays(ContainerPreviewModule.mc.fontRenderer, stack, 0, 0);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.popMatrix();
                x += 16;
            }
            if (++l_I % 9 == 0) {
                x = 0;
                l_Y += 15;
            }
        }
    }
}
