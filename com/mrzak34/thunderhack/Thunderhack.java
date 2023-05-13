//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack;

import net.minecraftforge.fml.common.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.ffp.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.manager.*;
import java.io.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.dism.*;
import net.minecraftforge.fml.client.registry.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import java.util.*;

@Mod(modid = "thunderhack", name = "ThunderHack", version = "2.41", acceptableRemoteVersions = "*")
public class Thunderhack
{
    @Mod.Instance
    public static Thunderhack INSTANCE;
    public static float TICK_TIMER;
    public static List<String> alts;
    public static long initTime;
    public static BlockPos gps_position;
    public static Color copy_color;
    public static NoMotionUpdateService noMotionUpdateService;
    public static ServerTickManager servtickManager;
    public static PositionManager positionManager;
    public static RotationManager rotationManager;
    public static EntityProvider entityProvider;
    public static CommandManager commandManager;
    public static SetDeadManager setDeadManager;
    public static NetworkHandler networkHandler;
    public static ThreadManager threadManager;
    public static SwitchManager switchManager;
    public static ReloadManager reloadManager;
    public static CombatManager combatManager;
    public static ServerManager serverManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static EventManager eventManager;
    public static MacroManager macromanager;
    public static Scheduler yahz;
    public static CFontRenderer fontRenderer;
    public static CFontRenderer fontRenderer2;
    public static CFontRenderer fontRenderer3;
    public static CFontRenderer fontRenderer4;
    public static CFontRenderer fontRenderer5;
    public static CFontRenderer fontRenderer6;
    public static CFontRenderer fontRenderer7;
    public static CFontRenderer fontRenderer8;
    public static CFontRenderer icons;
    public static CFontRenderer middleicons;
    public static CFontRenderer BIGicons;
    private static boolean unloaded;
    
    public static void load() {
        ConfigManager.loadAlts();
        ConfigManager.loadSearch();
        Thunderhack.unloaded = false;
        if (Thunderhack.reloadManager != null) {
            Thunderhack.reloadManager.unload();
            Thunderhack.reloadManager = null;
        }
        ConfigManager.init();
        try {
            Thunderhack.fontRenderer = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(24.0f), true, true);
            Thunderhack.fontRenderer2 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont3.ttf"))).deriveFont(28.0f), true, true);
            Thunderhack.fontRenderer3 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(18.0f), true, true);
            Thunderhack.fontRenderer4 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(50.0f), true, true);
            Thunderhack.fontRenderer5 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(12.0f), true, true);
            Thunderhack.fontRenderer6 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(14.0f), true, true);
            Thunderhack.fontRenderer7 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(10.0f), true, true);
            Thunderhack.fontRenderer8 = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont3.ttf"))).deriveFont(62.0f), true, true);
            Thunderhack.icons = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(20.0f), true, true);
            Thunderhack.middleicons = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(46.0f), true, true);
            Thunderhack.BIGicons = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(72.0f), true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Thunderhack.noMotionUpdateService = new NoMotionUpdateService();
        Thunderhack.servtickManager = new ServerTickManager();
        Thunderhack.positionManager = new PositionManager();
        Thunderhack.rotationManager = new RotationManager();
        Thunderhack.commandManager = new CommandManager();
        Thunderhack.entityProvider = new EntityProvider();
        Thunderhack.networkHandler = new NetworkHandler();
        Thunderhack.setDeadManager = new SetDeadManager();
        Thunderhack.serverManager = new ServerManager();
        Thunderhack.threadManager = new ThreadManager();
        Thunderhack.switchManager = new SwitchManager();
        Thunderhack.combatManager = new CombatManager();
        Thunderhack.friendManager = new FriendManager();
        Thunderhack.moduleManager = new ModuleManager();
        Thunderhack.eventManager = new EventManager();
        Thunderhack.macromanager = new MacroManager();
        Thunderhack.yahz = new Scheduler();
        Thunderhack.noMotionUpdateService.init();
        Thunderhack.positionManager.init();
        Thunderhack.rotationManager.init();
        Thunderhack.servtickManager.init();
        Thunderhack.moduleManager.init();
        Thunderhack.entityProvider.init();
        Thunderhack.setDeadManager.init();
        Thunderhack.combatManager.init();
        Thunderhack.switchManager.init();
        Thunderhack.eventManager.init();
        Thunderhack.serverManager.init();
        FriendManager.loadFriends();
        Thunderhack.yahz.init();
        ConfigManager.load(ConfigManager.getCurrentConfig());
        Thunderhack.moduleManager.onLoad();
        ThunderUtils.syncCapes();
        MacroManager.onLoad();
        if (Util.mc.getSession() != null && !Thunderhack.alts.contains(Util.mc.getSession().getUsername())) {
            Thunderhack.alts.add(Util.mc.getSession().getUsername());
        }
    }
    
    public static void unload(final boolean initReloadManager) {
        Display.setTitle("Minecraft 1.12.2");
        if (initReloadManager) {
            (Thunderhack.reloadManager = new ReloadManager()).init((Thunderhack.commandManager != null) ? Thunderhack.commandManager.getPrefix() : ".");
        }
        ConfigManager.saveAlts();
        ConfigManager.saveSearch();
        FriendManager.saveFriends();
        if (!Thunderhack.unloaded) {
            Thunderhack.eventManager.onUnload();
            Thunderhack.noMotionUpdateService.unload();
            Thunderhack.positionManager.unload();
            Thunderhack.rotationManager.unload();
            Thunderhack.servtickManager.unload();
            Thunderhack.entityProvider.unload();
            Thunderhack.setDeadManager.unload();
            Thunderhack.combatManager.unload();
            Thunderhack.switchManager.unload();
            Thunderhack.serverManager.unload();
            Thunderhack.yahz.unload();
            Thunderhack.moduleManager.onUnload();
            ConfigManager.save(ConfigManager.getCurrentConfig());
            MacroManager.saveMacro();
            Thunderhack.moduleManager.onUnloadPost();
            Thunderhack.unloaded = true;
        }
        Thunderhack.eventManager = null;
        Thunderhack.friendManager = null;
        Thunderhack.fontRenderer = null;
        Thunderhack.macromanager = null;
        Thunderhack.networkHandler = null;
        Thunderhack.commandManager = null;
        Thunderhack.serverManager = null;
        Thunderhack.servtickManager = null;
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGib.class, RenderGib::new);
        GlobalExecutor.EXECUTOR.submit(() -> Sphere.cacheSphere());
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("ThunderHack+");
        Thunderhack.initTime = System.currentTimeMillis();
        load();
        MinecraftForge.EVENT_BUS.register((Object)Thunderhack.networkHandler);
    }
    
    static {
        Thunderhack.TICK_TIMER = 1.0f;
        Thunderhack.alts = new ArrayList<String>();
        Thunderhack.unloaded = false;
    }
}
