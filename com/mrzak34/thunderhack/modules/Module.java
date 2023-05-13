//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.notification.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import java.util.*;

public class Module
{
    private List<Setting> settings;
    private final String description;
    private final String eng_description;
    public static Minecraft mc;
    private final Category category;
    public Setting<Boolean> enabled;
    public Setting<String> displayName;
    public Setting<Bind> bind;
    public Setting<Boolean> drawn;
    
    public Module(final String name, final String description, final Category category) {
        this.settings = new ArrayList<Setting>();
        this.enabled = (Setting<Boolean>)this.register(new Setting("Enabled", (T)false));
        this.bind = (Setting<Bind>)this.register(new Setting("Keybind", (T)new Bind(-1)));
        this.drawn = (Setting<Boolean>)this.register(new Setting("Drawn", (T)true));
        this.displayName = (Setting<String>)this.register(new Setting("DisplayName", (T)name));
        this.description = description;
        this.category = category;
        this.eng_description = "no english_description";
    }
    
    public Module(final String name, final String description, final String eng_description, final Category category) {
        this.settings = new ArrayList<Setting>();
        this.enabled = (Setting<Boolean>)this.register(new Setting("Enabled", (T)false));
        this.bind = (Setting<Bind>)this.register(new Setting("Keybind", (T)new Bind(-1)));
        this.drawn = (Setting<Boolean>)this.register(new Setting("Drawn", (T)true));
        this.displayName = (Setting<String>)this.register(new Setting("DisplayName", (T)name));
        this.description = description;
        this.eng_description = eng_description;
        this.category = category;
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onLoad() {
    }
    
    public void onTick() {
    }
    
    public void onLogin() {
    }
    
    public void onLogout() {
    }
    
    public void onUpdate() {
    }
    
    public void onRender2D(final Render2DEvent event) {
    }
    
    public void onRender3D(final Render3DEvent event) {
    }
    
    public void onUnload() {
    }
    
    public String getDisplayInfo() {
        return null;
    }
    
    public boolean isOn() {
        return this.enabled.getValue();
    }
    
    public boolean isOff() {
        return !this.enabled.getValue();
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            this.enable();
        }
        else {
            this.disable();
        }
    }
    
    public void enable() {
        this.enabled.setValue(true);
        this.onEnable();
        if (!Objects.equals(this.getDisplayName(), "ThunderGui")) {
            if (!Objects.equals(this.getDisplayName(), "ClickGUI")) {
                SoundUtil.playSound(SoundUtil.ThunderSound.ON);
            }
        }
        if (!Objects.equals(this.getDisplayName(), "ElytraSwap") && !Objects.equals(this.getDisplayName(), "ClickGui") && !Objects.equals(this.getDisplayName(), "ThunderGui") && !Objects.equals(this.getDisplayName(), "Windows")) {
            NotificationManager.publicity(this.getDisplayName() + " was enabled!", 2, Notification.Type.ENABLED);
        }
        if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).notifyToggles.getValue()) {
            final TextComponentString text = new TextComponentString(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.GREEN + this.getDisplayName() + " toggled on.");
            Util.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)text, 1);
        }
        if (this.isOn()) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    public void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (Module.mc.player == null) {
            return;
        }
        if (!Objects.equals(this.getDisplayName(), "ThunderGui")) {
            if (!Objects.equals(this.getDisplayName(), "ClickGUI")) {
                SoundUtil.playSound(SoundUtil.ThunderSound.OFF);
            }
        }
        this.enabled.setValue(false);
        if (!Objects.equals(this.getDisplayName(), "ElytraSwap") && !Objects.equals(this.getDisplayName(), "ThunderGui") && !Objects.equals(this.getDisplayName(), "ClickGui") && !Objects.equals(this.getDisplayName(), "Windows")) {
            NotificationManager.publicity(this.getDisplayName() + " was disabled!", 2, Notification.Type.DISABLED);
        }
        if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).notifyToggles.getValue()) {
            final TextComponentString text = new TextComponentString(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.RED + this.getDisplayName() + " toggled off.");
            if (text != null) {
                Util.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)text, 1);
            }
        }
        this.onDisable();
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
    
    public String getDisplayName() {
        return this.displayName.getValue();
    }
    
    public String getDescription() {
        if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
            return this.description;
        }
        if (!Objects.equals(this.eng_description, "english_description")) {
            return this.eng_description;
        }
        return this.description;
    }
    
    public boolean isDrawn() {
        return this.drawn.getValue();
    }
    
    public void setDrawn(final boolean drawn) {
        this.drawn.setValue(drawn);
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public String getInfo() {
        return null;
    }
    
    public Bind getBind() {
        return this.bind.getValue();
    }
    
    public void setBind(final int key) {
        this.bind.setValue(new Bind(key));
    }
    
    public boolean listening() {
        return this.isOn();
    }
    
    public String getFullArrayString() {
        return this.getDisplayName() + ChatFormatting.GRAY + ((this.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + this.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
    }
    
    public static boolean fullNullCheck() {
        return Util.mc.player == null || Util.mc.world == null;
    }
    
    public String getName() {
        return this.getDisplayName();
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public boolean isEnabled() {
        return this.isOn();
    }
    
    public boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public Setting register(final Setting setting) {
        setting.setModule(this);
        this.settings.add(setting);
        return setting;
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting setting : this.settings) {
            if (!setting.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return setting;
        }
        return null;
    }
    
    static {
        Module.mc = Util.mc;
    }
    
    public enum Category
    {
        COMBAT("Combat"), 
        MISC("Misc"), 
        RENDER("Render"), 
        MOVEMENT("Movement"), 
        PLAYER("Player"), 
        FUNNYGAME("FunnyGame"), 
        CLIENT("Client"), 
        HUD("HUD");
        
        private final String name;
        
        private Category(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
