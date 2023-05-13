//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class AutoMend extends Module
{
    public Setting<Integer> waterMarkZ1;
    public Setting<Integer> waterMarkZ2;
    public Setting<SubBind> subBind;
    private final Setting<Integer> threshold;
    private final Setting<Integer> dlay;
    private final Setting<Integer> armdlay;
    private final Timer timer;
    private final Timer timer2;
    int arm1;
    int arm2;
    int arm3;
    int arm4;
    int totalarmor;
    int prev_item;
    public static boolean isMending;
    
    public AutoMend() {
        super("AutoMend", "\u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c-AutoArmor!", "turn on AutoArmor!", Module.Category.PLAYER);
        this.waterMarkZ1 = (Setting<Integer>)this.register(new Setting("Y", (T)10, (T)0, (T)524));
        this.waterMarkZ2 = (Setting<Integer>)this.register(new Setting("X", (T)20, (T)0, (T)862));
        this.subBind = (Setting<SubBind>)this.register(new Setting("subbind", (T)new SubBind(56)));
        this.threshold = (Setting<Integer>)this.register(new Setting("Percent", (T)100, (T)0, (T)100));
        this.dlay = (Setting<Integer>)this.register(new Setting("ThrowDelay", (T)100, (T)0, (T)100));
        this.armdlay = (Setting<Integer>)this.register(new Setting("ArmorDelay", (T)100, (T)0, (T)1000));
        this.timer = new Timer();
        this.timer2 = new Timer();
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync event) {
        if (PlayerUtils.isKeyDown(this.subBind.getValue().getKey())) {
            AutoMend.mc.player.rotationPitch = 90.0f;
            AutoMend.isMending = true;
        }
        else {
            AutoMend.isMending = false;
        }
    }
    
    @SubscribeEvent
    public void postEntitySync(final EventPostSync e) {
        if (fullNullCheck()) {
            return;
        }
        if (PlayerUtils.isKeyDown(this.subBind.getValue().getKey()) && (calculatePercentage(AutoMend.mc.player.inventory.getStackInSlot(39)) < this.threshold.getValue() || calculatePercentage(AutoMend.mc.player.inventory.getStackInSlot(38)) < this.threshold.getValue() || calculatePercentage(AutoMend.mc.player.inventory.getStackInSlot(37)) < this.threshold.getValue() || calculatePercentage(AutoMend.mc.player.inventory.getStackInSlot(36)) < this.threshold.getValue()) && this.getXpSlot() != -1) {
            this.prev_item = AutoMend.mc.player.inventory.currentItem;
            AutoMend.mc.player.inventory.currentItem = this.getXpSlot();
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.getXpSlot()));
            final ItemStack[] armorStacks = { AutoMend.mc.player.inventory.getStackInSlot(39), AutoMend.mc.player.inventory.getStackInSlot(38), AutoMend.mc.player.inventory.getStackInSlot(37), AutoMend.mc.player.inventory.getStackInSlot(36) };
            for (int i = 0; i < 4; ++i) {
                final ItemStack stack = armorStacks[i];
                if (stack.getItem() instanceof ItemArmor) {
                    if (calculatePercentage(stack) >= this.threshold.getValue()) {
                        for (int s = 0; s < 36; ++s) {
                            final ItemStack emptyStack = AutoMend.mc.player.inventory.getStackInSlot(s);
                            if (emptyStack.isEmpty()) {
                                if (emptyStack.getItem() == Items.AIR) {
                                    if (this.timer2.passedMs(this.armdlay.getValue())) {
                                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, (s < 9) ? (s + 36) : s, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                                        AutoMend.mc.playerController.updateController();
                                        this.timer2.reset();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (this.timer.passedMs(this.dlay.getValue())) {
                AutoMend.mc.playerController.processRightClick((EntityPlayer)AutoMend.mc.player, (World)AutoMend.mc.world, EnumHand.MAIN_HAND);
                this.timer.reset();
            }
        }
        else if (this.prev_item != -1) {
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.prev_item));
            this.prev_item = -1;
            this.arm1 = 0;
            this.arm2 = 0;
            this.arm3 = 0;
            this.arm4 = 0;
            this.totalarmor = 0;
        }
    }
    
    private int getXpSlot() {
        ItemStack stack = AutoMend.mc.player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
            return AutoMend.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            stack = AutoMend.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
                return i;
            }
        }
        return -1;
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        if (PlayerUtils.isKeyDown(this.subBind.getValue().getKey())) {
            RenderUtil.drawSmoothRect(this.waterMarkZ2.getValue(), this.waterMarkZ1.getValue(), (float)(106 + this.waterMarkZ2.getValue()), (float)(35 + this.waterMarkZ1.getValue()), new Color(35, 35, 40, 230).getRGB());
            RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(103 + this.waterMarkZ2.getValue()), (float)(15 + this.waterMarkZ1.getValue()), new Color(51, 51, 58, 230).getRGB());
            final ItemStack[] armorStacks = { AutoMend.mc.player.inventory.getStackInSlot(39), AutoMend.mc.player.inventory.getStackInSlot(38), AutoMend.mc.player.inventory.getStackInSlot(37), AutoMend.mc.player.inventory.getStackInSlot(36) };
            final ItemStack stack = armorStacks[0];
            final ItemStack stack2 = armorStacks[1];
            final ItemStack stack3 = armorStacks[2];
            final ItemStack stack4 = armorStacks[3];
            if ((int)calculatePercentage(stack) >= this.arm1) {
                this.arm1 = (int)calculatePercentage(stack);
            }
            if ((int)calculatePercentage(stack2) >= this.arm2) {
                this.arm2 = (int)calculatePercentage(stack2);
            }
            if ((int)calculatePercentage(stack3) >= this.arm3) {
                this.arm3 = (int)calculatePercentage(stack3);
            }
            if ((int)calculatePercentage(stack4) >= this.arm4) {
                this.arm4 = (int)calculatePercentage(stack4);
            }
            this.totalarmor = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 4;
            final float progress = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 400.0f;
            final int expCount = this.getExpCount();
            AutoMend.mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.EXPERIENCE_BOTTLE), this.waterMarkZ2.getValue() + 70 + 11, this.waterMarkZ1.getValue() + 17);
            final String s3 = String.valueOf(expCount);
            Util.fr.drawStringWithShadow(s3, (float)(this.waterMarkZ2.getValue() + 85 + 11), (float)(this.waterMarkZ1.getValue() + 9 + 17), 16777215);
            RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(this.totalarmor + this.waterMarkZ2.getValue() + 5), (float)(15 + this.waterMarkZ1.getValue()), PaletteHelper.fade(new Color(255, 0, 0, 255).getRGB(), new Color(0, 255, 0, 255).getRGB(), progress));
            Util.fr.drawStringWithShadow("Mending...", (float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
            final int width = this.waterMarkZ2.getValue() - 12;
            final int height = this.waterMarkZ1.getValue() + 17;
            GlStateManager.enableTexture2D();
            int iteration = 0;
            for (final ItemStack is : AutoMend.mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) {
                    continue;
                }
                final int x = width - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                RenderUtil.itemRender.zLevel = 200.0f;
                RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, height);
                RenderUtil.itemRender.renderItemOverlayIntoGUI(AutoMend.mc.fontRenderer, is, x, height, "");
                RenderUtil.itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final String s4 = (is.getCount() > 1) ? (is.getCount() + "") : "";
                AutoMend.mc.fontRenderer.drawStringWithShadow(s4, (float)(x + 19 - 2 - AutoMend.mc.fontRenderer.getStringWidth(s4)), (float)(height + 9), 16777215);
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    
    private int getExpCount() {
        int expCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (AutoMend.mc.player.inventory.getStackInSlot(i).getItem().equals(Items.EXPERIENCE_BOTTLE)) {
                expCount += AutoMend.mc.player.inventory.getStackInSlot(i).getCount();
            }
        }
        if (AutoMend.mc.player.getHeldItemOffhand().getItem().equals(Items.EXPERIENCE_BOTTLE)) {
            ++expCount;
        }
        return expCount;
    }
    
    public static float calculatePercentage(final ItemStack stack) {
        final float durability = (float)(stack.getMaxDamage() - stack.getItemDamage());
        return durability / stack.getMaxDamage() * 100.0f;
    }
    
    static {
        AutoMend.isMending = false;
    }
}
