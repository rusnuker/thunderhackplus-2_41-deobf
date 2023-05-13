//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.misc.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.world.chunk.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.notification.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import java.awt.*;

public class NoCom extends Module
{
    public static int scannedChunks;
    public static List<Dot> dots;
    private static NoCom INSTANCE;
    private static BlockPos playerPos;
    private static long time;
    private static int count;
    private static int masynax;
    private static int masynay;
    private final Setting<SubBind> self;
    public Setting<Integer> delay;
    public Setting<Integer> loop;
    public Setting<Integer> startX;
    public Setting<Integer> startZ;
    public Setting<Integer> scale;
    public Setting<Boolean> you;
    public Setting<Boolean> loadgui;
    public int couti;
    private int renderDistanceDiameter;
    private int x;
    private int z;
    
    public NoCom() {
        super("NoCom", "\u044d\u043a\u0441\u043f\u043b\u043e\u0438\u0442 \u0434\u043b\u044f \u043f\u043e\u0438\u0441\u043a\u0430-\u0438\u0433\u0440\u043e\u043a\u043e\u0432", Category.MISC);
        this.self = (Setting<SubBind>)this.register(new Setting("openGui", (T)new SubBind(0)));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)200, (T)0, (T)1000));
        this.loop = (Setting<Integer>)this.register(new Setting("LoopPerTick", (T)1, (T)1, (T)100));
        this.startX = (Setting<Integer>)this.register(new Setting("StartX", (T)0, (T)0, (T)1000000));
        this.startZ = (Setting<Integer>)this.register(new Setting("StartZ", (T)0, (T)0, (T)1000000));
        this.scale = (Setting<Integer>)this.register(new Setting("PointerScale", (T)4, (T)1, (T)4));
        this.you = (Setting<Boolean>)this.register(new Setting("you", (T)true));
        this.loadgui = (Setting<Boolean>)this.register(new Setting("LoadGui", (T)true));
        this.couti = 1;
        this.renderDistanceDiameter = 0;
        this.setInstance();
    }
    
    public static NoCom getInstance() {
        if (NoCom.INSTANCE == null) {
            NoCom.INSTANCE = new NoCom();
        }
        return NoCom.INSTANCE;
    }
    
    public static void getgui() {
        Util.mc.displayGuiScreen((GuiScreen)GuiScanner.getGuiScanner());
    }
    
    public static void rerun(final int x, final int y) {
        NoCom.dots.clear();
        NoCom.playerPos = null;
        NoCom.count = 0;
        NoCom.time = 0L;
        NoCom.masynax = x;
        NoCom.masynay = y;
    }
    
    private void setInstance() {
        NoCom.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (PlayerUtils.isKeyDown(this.self.getValue().getKey())) {
            getgui();
        }
        if (GuiScanner.neartrack && NoCom.scannedChunks > 25) {
            NoCom.scannedChunks = 0;
        }
        if (GuiScanner.neartrack && NoCom.scannedChunks == 0) {
            this.donocom((int)NoCom.mc.player.posX, (int)NoCom.mc.player.posZ);
        }
        if (GuiScanner.neartrack) {
            return;
        }
        if (this.loadgui.getValue()) {
            getgui();
            this.loadgui.setValue(false);
        }
        if (!GuiScanner.busy) {
            if (!this.you.getValue()) {
                this.donocom(this.startX.getValue(), this.startZ.getValue());
            }
            else {
                this.donocom((int)NoCom.mc.player.posX, (int)NoCom.mc.player.posZ);
            }
        }
        else if (NoCom.masynax != 0 && NoCom.masynay != 0) {
            this.donocom(NoCom.masynax, NoCom.masynay);
        }
    }
    
    public void donocom(final int x3, final int y3) {
        NoCom.playerPos = new BlockPos(NoCom.mc.player.posX, NoCom.mc.player.posY - 1.0, NoCom.mc.player.posZ);
        if (this.renderDistanceDiameter == 0) {
            this.renderDistanceDiameter = 8;
        }
        if (NoCom.time == 0L) {
            NoCom.time = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - NoCom.time > this.delay.getValue()) {
            for (int i = 0; i < this.loop.getValue(); ++i) {
                int x4 = 0;
                int z1 = 0;
                if (!this.you.getValue()) {
                    x4 = this.getSpiralCoords(NoCom.count)[0] * this.renderDistanceDiameter * 16 + x3;
                    z1 = this.getSpiralCoords(NoCom.count)[1] * this.renderDistanceDiameter * 16 + y3;
                }
                else {
                    x4 = this.getSpiralCoords(NoCom.count)[0] * this.renderDistanceDiameter * 16 + x3;
                    z1 = this.getSpiralCoords(NoCom.count)[1] * this.renderDistanceDiameter * 16 + y3;
                }
                final BlockPos position = new BlockPos(x4, 0, z1);
                this.x = x4;
                this.z = z1;
                NoCom.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, NoCom.playerPos, EnumFacing.EAST));
                NoCom.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, position, EnumFacing.EAST));
                NoCom.dots.add(new Dot(x4 / 16, z1 / 16, DotType.Searched));
                NoCom.playerPos = new BlockPos(NoCom.mc.player.posX, NoCom.mc.player.posY - 1.0, NoCom.mc.player.posZ);
                NoCom.time = System.currentTimeMillis();
                ++NoCom.count;
                ++NoCom.scannedChunks;
            }
        }
    }
    
    @SubscribeEvent
    public final void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange) {
            final int x = ((SPacketBlockChange)event.getPacket()).getBlockPosition().getX();
            final int z = ((SPacketBlockChange)event.getPacket()).getBlockPosition().getZ();
            final IChunkProviderClient chunkProviderClient = (IChunkProviderClient)NoCom.mc.world.getChunkProvider();
            for (final Chunk chunk : chunkProviderClient.getLoadedChunks().values()) {
                if (chunk.x == x / 16 || chunk.z == z / 16) {
                    return;
                }
            }
            final String shittytext = "Player spotted at X: " + ChatFormatting.GREEN + x + ChatFormatting.RESET + " Z: " + ChatFormatting.GREEN + z;
            NoCom.dots.add(new Dot(x / 16, z / 16, DotType.Spotted));
            Command.sendMessage(shittytext);
            GuiScanner.getInstance().consoleout.add(new cout(this.couti, shittytext));
            ++this.couti;
            if (GuiScanner.track) {
                GuiScanner.getInstance().consoleout.add(new cout(this.couti, "tracking x " + x + " z " + z));
                rerun(x, z);
            }
            if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isEnabled()) {
                NotificationManager.publicity(shittytext, 3, Notification.Type.INFO);
            }
        }
    }
    
    private int[] getSpiralCoords(int n) {
        int x = 0;
        int z = 0;
        int d = 1;
        int lineNumber = 1;
        int[] coords = { 0, 0 };
        for (int i = 0; i < n; ++i) {
            if (2 * x * d < lineNumber) {
                x += d;
                coords = new int[] { x, z };
            }
            else if (2 * z * d < lineNumber) {
                z += d;
                coords = new int[] { x, z };
            }
            else {
                d *= -1;
                ++lineNumber;
                ++n;
            }
        }
        return coords;
    }
    
    @Override
    public void onEnable() {
        NoCom.playerPos = null;
        NoCom.count = 0;
        NoCom.time = 0L;
    }
    
    @Override
    public void onDisable() {
        NoCom.dots.clear();
        NoCom.playerPos = null;
        NoCom.count = 0;
        NoCom.time = 0L;
    }
    
    @Override
    public String getDisplayInfo() {
        return this.x + " , " + this.z;
    }
    
    static {
        NoCom.scannedChunks = 0;
        NoCom.dots = new ArrayList<Dot>();
        NoCom.playerPos = null;
        NoCom.time = 0L;
        NoCom.count = 0;
        NoCom.masynax = 0;
        NoCom.masynay = 0;
        NoCom.INSTANCE = new NoCom();
    }
    
    public enum DotType
    {
        Spotted, 
        Searched;
    }
    
    public static class cout
    {
        public String string;
        public int posY;
        
        public cout(final int posY, final String out) {
            this.posY = posY;
            this.string = out;
        }
    }
    
    public class Dot
    {
        public DotType type;
        public int posX;
        public int posY;
        public Color color;
        public int ticks;
        
        public Dot(final int posX, final int posY, final DotType type) {
            this.posX = posX;
            this.posY = posY;
            this.type = type;
            this.ticks = 0;
        }
    }
}
