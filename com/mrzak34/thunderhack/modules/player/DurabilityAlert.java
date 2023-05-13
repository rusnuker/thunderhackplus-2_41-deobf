//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class DurabilityAlert extends Module
{
    public Setting<Boolean> friends;
    public Setting<Integer> percent;
    private final ResourceLocation ICON;
    private boolean need_alert;
    private Timer timer;
    
    public DurabilityAlert() {
        super("DurabilityAlert", "\u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0430\u0435\u0442 \u043e-\u043f\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u0438 \u0431\u0440\u043e\u043d\u0438", "durability alert", Module.Category.PLAYER);
        this.friends = (Setting<Boolean>)this.register(new Setting("Friend message", (T)true));
        this.percent = (Setting<Integer>)this.register(new Setting("Percent", (T)20, (T)1, (T)100));
        this.ICON = new ResourceLocation("textures/broken_shield.png");
        this.need_alert = false;
        this.timer = new Timer();
    }
    
    public void onUpdate() {
        if (this.friends.getValue()) {
            for (final EntityPlayer player : DurabilityAlert.mc.world.playerEntities) {
                if (!Thunderhack.friendManager.isFriend(player)) {
                    continue;
                }
                if (player == DurabilityAlert.mc.player) {
                    continue;
                }
                for (final ItemStack stack : player.inventory.armorInventory) {
                    if (stack.isEmpty()) {
                        continue;
                    }
                    if (getDurability(stack) >= this.percent.getValue() || !this.timer.passedMs(30000L)) {
                        continue;
                    }
                    if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                        DurabilityAlert.mc.player.sendChatMessage("/msg " + player.getName() + " \u0421\u0440\u043e\u0447\u043d\u043e \u0447\u0438\u043d\u0438 \u0431\u0440\u043e\u043d\u044e!");
                    }
                    else {
                        DurabilityAlert.mc.player.sendChatMessage("/msg " + player.getName() + " Repair your armor immediately!");
                    }
                    this.timer.reset();
                }
            }
        }
        boolean flag = false;
        for (final ItemStack stack2 : DurabilityAlert.mc.player.inventory.armorInventory) {
            if (stack2.isEmpty()) {
                continue;
            }
            if (getDurability(stack2) >= this.percent.getValue()) {
                continue;
            }
            this.need_alert = true;
            flag = true;
        }
        if (!flag && this.need_alert) {
            this.need_alert = false;
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        if (this.need_alert) {
            if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                FontRender.drawCentString6("\u0421\u0440\u043e\u0447\u043d\u043e \u0447\u0438\u043d\u0438 \u0431\u0440\u043e\u043d\u044e!", (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
                Drawable.drawTexture(this.ICON, (float)e.getScreenWidth() / 2.0f - 40.0f, (float)e.getScreenHeight() / 3.0f - 120.0f, 80.0, 80.0, new Color(16768768));
            }
            else {
                FontRender.drawCentString6("Repair your armor immediately!", (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
                Drawable.drawTexture(this.ICON, (float)e.getScreenWidth() / 2.0f - 40.0f, (float)e.getScreenHeight() / 3.0f - 120.0f, 80.0, 80.0, new Color(16768768));
            }
        }
    }
    
    public static int getDurability(final ItemStack stack) {
        return (int)((stack.getMaxDamage() - stack.getItemDamage()) / Math.max(0.1, stack.getMaxDamage()) * 100.0);
    }
}
