//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class AutoBuy extends Module
{
    public static List<AutoBuyItem> items;
    boolean clickGreenPannel;
    boolean clicked;
    boolean direction;
    int pages;
    private final Timer timer;
    private final Timer timer2;
    private final Timer roamDelay;
    private int windowId;
    
    public AutoBuy() {
        super("AutoBuy", "\u0430\u0432\u0442\u043e \u0437\u0430\u043b\u0443\u043f\u043a\u0430", Category.MISC);
        this.clickGreenPannel = false;
        this.clicked = false;
        this.direction = false;
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.roamDelay = new Timer();
    }
    
    public static NBTTagList getLoreTagList(final ItemStack stack) {
        final NBTTagCompound displayTag = getDisplayTag(stack);
        if (!hasLore(stack)) {
            displayTag.setTag("Lore", (NBTBase)new NBTTagList());
        }
        return displayTag.getTagList("Lore", 8);
    }
    
    public static boolean hasLore(final ItemStack stack) {
        return hasDisplayTag(stack) && getDisplayTag(stack).hasKey("Lore", 9);
    }
    
    public static boolean hasDisplayTag(final ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("display", 10);
    }
    
    public static NBTTagCompound getDisplayTag(final ItemStack stack) {
        return stack.getOrCreateSubCompound("display");
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketWindowItems) {
            final SPacketWindowItems pac = (SPacketWindowItems)e.getPacket();
            this.windowId = pac.getWindowId();
            int slot = 0;
            if (this.clickGreenPannel) {
                return;
            }
            for (final ItemStack itemStack : pac.getItemStacks()) {
                for (final AutoBuyItem abitem : AutoBuy.items) {
                    if (Objects.equals(abitem.getName(), (itemStack.getItem().getRegistryName() + "").replace("minecraft:", "")) && this.getPrice(getLoreTagList(itemStack).toString()) <= abitem.price1) {
                        if (abitem.EnchantsIsEmpty()) {
                            this.roamDelay.reset();
                            this.Buy(slot);
                        }
                        else {
                            final String[] ench = new String[20];
                            int i = 0;
                            for (final NBTBase tag : itemStack.getEnchantmentTagList()) {
                                ench[i] = this.rewriteEnchant(tag.toString());
                                ++i;
                            }
                            if (!this.isContain(abitem.enchantments, ench)) {
                                continue;
                            }
                            this.roamDelay.reset();
                            this.Buy(slot);
                        }
                    }
                }
                ++slot;
            }
        }
        if (e.getPacket() instanceof SPacketChat) {
            final SPacketChat packetChat = (SPacketChat)e.getPacket();
            if (packetChat.getChatComponent().getUnformattedText().contains("\u0423\u0441\u043f\u0435\u0448\u043d\u0430\u044f \u043f\u043e\u043a\u0443\u043f\u043a\u0430")) {
                this.clicked = false;
                this.clickGreenPannel = false;
                this.direction = false;
                this.pages = 0;
                this.timer.reset();
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.timer2.passedMs(100L)) {
            if (AutoBuy.mc.currentScreen instanceof GuiChest && this.roamDelay.passedMs(2000L)) {
                if (this.pages < 15 && !this.direction) {
                    AutoBuy.mc.playerController.windowClick(this.windowId, 50, 0, ClickType.PICKUP, (EntityPlayer)AutoBuy.mc.player);
                    AutoBuy.mc.playerController.updateController();
                    ++this.pages;
                }
                else if (this.pages == 15 && !this.direction) {
                    this.direction = true;
                }
                else if (this.pages == 0) {
                    this.direction = false;
                }
                else if (this.direction) {
                    AutoBuy.mc.playerController.windowClick(this.windowId, 48, 0, ClickType.PICKUP, (EntityPlayer)AutoBuy.mc.player);
                    AutoBuy.mc.playerController.updateController();
                    --this.pages;
                }
            }
            this.timer2.reset();
        }
        if (this.clickGreenPannel && !this.clicked) {
            this.Buy(0);
        }
        else if (this.clicked) {
            this.clickGreenPannel = false;
        }
    }
    
    public void Buy(final int slot) {
        if (this.timer.passedMs(600L)) {
            AutoBuy.mc.playerController.windowClick(this.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoBuy.mc.player);
            AutoBuy.mc.playerController.updateController();
            if (slot == 0 && this.clickGreenPannel) {
                this.clicked = true;
            }
            else {
                this.clickGreenPannel = true;
            }
            this.timer.reset();
        }
    }
    
    public boolean isContain(final String[] m1, final String[] m2) {
        int count = 0;
        for (final String a : m1) {
            for (final String b : m2) {
                if (Objects.equals(a, b)) {
                    ++count;
                    break;
                }
            }
        }
        return count == m1.length;
    }
    
    public String rewriteEnchant(final String string) {
        final String id = StringUtils.substringBetween(string, "id:", "s");
        final String lvl = StringUtils.substringBetween(string, "lvl:", "s,");
        return id + "(" + lvl + ")";
    }
    
    public int getPrice(final String string) {
        if (string == null) {
            return 9999999;
        }
        String string2 = StringUtils.substringBetween(string, "\u0437\u0430 \u0432\u0441\u0435: ", "$");
        if (string2 == null) {
            return 9999999;
        }
        string2 = string2.replace(",", "");
        final String[] string3 = string2.split("l");
        return Integer.parseInt(string3[1]);
    }
    
    static {
        AutoBuy.items = new ArrayList<AutoBuyItem>();
    }
    
    public static class AutoBuyItem
    {
        String name;
        String[] enchantments;
        int price1;
        int price2;
        boolean noench;
        
        public AutoBuyItem(final String name, final String[] enchantments, final int price1, final int price2, final boolean noEnch) {
            this.noench = true;
            this.name = name;
            this.enchantments = enchantments;
            this.price1 = price1;
            this.price2 = price2;
            this.noench = noEnch;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String[] getEnchantments() {
            return this.enchantments;
        }
        
        public int getPrice1() {
            return this.price1;
        }
        
        public int getPrice2() {
            return this.price2;
        }
        
        public boolean EnchantsIsEmpty() {
            return this.noench;
        }
    }
}
