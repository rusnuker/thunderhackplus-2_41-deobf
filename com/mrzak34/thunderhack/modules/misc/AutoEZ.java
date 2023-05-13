//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.nio.charset.*;
import java.io.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.command.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;

public class AutoEZ extends Module
{
    public static ArrayList<String> EZWORDS;
    public Setting<Boolean> global;
    String a;
    String b;
    String c;
    String[] EZ;
    private final Setting<ModeEn> Mode;
    private final Setting<ServerMode> server;
    
    public AutoEZ() {
        super("AutoEZ", "\u041f\u0438\u0448\u0435\u0442 \u0438\u0437\u0438 \u0443\u0431\u0438\u043b \u0443\u0431\u0438\u043b - \u043f\u043e\u0441\u043b\u0435 \u043a\u0438\u043b\u043b\u0430", "only for-mcfunny.su", Category.MISC);
        this.global = (Setting<Boolean>)this.register(new Setting("global", (T)true));
        this.a = "";
        this.b = "";
        this.c = "";
        this.EZ = new String[] { "%player% \u0411\u042b\u041b \u041f\u041e\u041f\u0423\u0429\u0415\u041d", "%player% \u0411\u042b\u041b \u041f\u0420\u0418\u0425\u041b\u041e\u041f\u041d\u0423\u0422 \u0422\u0410\u041f\u041a\u041e\u041c", "%player% EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ", "%player% \u041f\u041e\u0421\u042b\u041f\u0410\u041b\u0421\u042f EZZZZZZZZZ", "%player% \u0422\u042b \u0411\u042b \u0425\u041e\u0422\u042c \u041a\u0418\u041b\u041b\u041a\u0423 \u0412\u041a\u041b\u042e\u0427\u0418\u041b", "%player% \u0421\u041b\u041e\u0416\u0418\u041b\u0421\u042f \u041f\u041e\u041f\u041e\u041b\u0410\u041c", "%player% \u0422\u0423\u0414\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410\u0410", "%player% \u0418\u0417\u0418 \u0411\u041b\u042f\u0422\u042c \u0425\u0410\u0425\u0410\u0425\u0410\u0410\u0425\u0425\u0410\u0425\u0410\u0425\u0410", "%player% \u0423\u041b\u0415\u0422\u0415\u041b \u041d\u0410 \u0422\u041e\u0422 \u0421\u0412\u0415\u0422", "%player% \u041f\u041e\u041f\u0423\u0429\u0415\u041d\u041d\u041d\u041d\u041d\u041d\u041d", "%player% \u0412\u042b\u0422\u0410\u0429\u0418 \u0417\u0410\u041b\u0423\u041f\u0423 \u0418\u0417\u041e \u0420\u0422\u0410", "%player% \u0411\u041e\u0416\u0415 \u0415\u0417\u0417\u041a\u0410", "%player% \u0418\u0417\u0418\u0418\u0418\u0418\u0418\u0418\u0418\u0418\u0418\u0418\u0418\u0418", "%player% \u0427\u0415 \u0422\u0410\u041a \u041b\u0415\u0413\u041a\u041e????", "RAGE OWNS %player% AND ALL", "RAGE \u0417\u0410\u041e\u0412\u041d\u0418\u041b %player%", "%player% \u0410\u0425\u0410\u0425\u0410\u0425\u0410\u0410\u0425\u0410\u0425\u0410\u0425\u0410\u0425\u0425\u0410\u0410\u0425\u0410\u0425", "%player% GET GOOD JOIN RAGE", "%player% \u041b\u0415\u0413\u0427\u0410\u0419\u0428\u0410\u042f", "%player% GG EZ", "%player% \u041d\u0423\u041b\u0418\u041d\u0410", "%player% \u041b\u0415\u0416\u0410\u0422\u042c \u041f\u041b\u042e\u0421 \u0421\u041e\u0421\u0410\u0422\u042c", "%player% \u0418\u0417\u0418 \u0411\u041e\u0422\u042f\u0420\u0410" };
        this.Mode = (Setting<ModeEn>)this.register(new Setting("Mode", (T)ModeEn.Basic));
        this.server = (Setting<ServerMode>)this.register(new Setting("Mode", (T)ServerMode.Universal));
        loadEZ();
    }
    
    public static void loadEZ() {
        try {
            final File file = new File("ThunderHack/misc/AutoEZ.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final File file2;
            FileInputStream fis;
            InputStreamReader isr;
            BufferedReader reader;
            ArrayList<String> lines;
            String line;
            final Object o;
            boolean newline;
            final Iterator<String> iterator;
            String l;
            ArrayList<String> spamList;
            StringBuilder spamChunk;
            final Iterator<String> iterator2;
            String i;
            new Thread(() -> {
                try {
                    fis = new FileInputStream(file2);
                    isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    reader = new BufferedReader(isr);
                    lines = new ArrayList<String>();
                    while (true) {
                        line = reader.readLine();
                        if (o != null) {
                            lines.add(line);
                        }
                        else {
                            break;
                        }
                    }
                    newline = false;
                    lines.iterator();
                    while (iterator.hasNext()) {
                        l = iterator.next();
                        if (l.equals("")) {
                            newline = true;
                            break;
                        }
                    }
                    AutoEZ.EZWORDS.clear();
                    spamList = new ArrayList<String>();
                    if (newline) {
                        spamChunk = new StringBuilder();
                        lines.iterator();
                        while (iterator2.hasNext()) {
                            i = iterator2.next();
                            if (i.equals("")) {
                                if (spamChunk.length() > 0) {
                                    spamList.add(spamChunk.toString());
                                    spamChunk = new StringBuilder();
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                spamChunk.append(i).append(" ");
                            }
                        }
                        spamList.add(spamChunk.toString());
                    }
                    else {
                        spamList.addAll(lines);
                    }
                    AutoEZ.EZWORDS = spamList;
                }
                catch (Exception e) {
                    System.err.println("Could not load file ");
                }
            }).start();
        }
        catch (IOException e2) {
            System.err.println("Could not load file ");
        }
    }
    
    @Override
    public void onEnable() {
        loadEZ();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (this.server.getValue() == ServerMode.Universal) {
            return;
        }
        if (e.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)e.getPacket();
            if (packet.getType() != ChatType.GAME_INFO) {
                this.a = packet.getChatComponent().getFormattedText();
                if (this.a.contains("\u0412\u044b \u0443\u0431\u0438\u043b\u0438 \u0438\u0433\u0440\u043e\u043a\u0430")) {
                    this.b = ThunderUtils.solvename(this.a);
                    if (this.Mode.getValue() == ModeEn.Basic) {
                        final int n = (int)Math.floor(Math.random() * this.EZ.length);
                        this.c = this.EZ[n].replace("%player%", this.b);
                    }
                    else {
                        if (AutoEZ.EZWORDS.isEmpty()) {
                            Command.sendMessage("\u0424\u0430\u0439\u043b \u0441 AutoEZ \u043f\u0443\u0441\u0442\u043e\u0439!");
                            return;
                        }
                        this.c = AutoEZ.EZWORDS.get(new Random().nextInt(AutoEZ.EZWORDS.size()));
                        this.c = this.c.replaceAll("%player%", this.b);
                    }
                    AutoEZ.mc.player.sendChatMessage(((boolean)this.global.getValue()) ? ("!" + this.c) : this.c);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final DeathEvent e) {
        if (this.server.getValue() != ServerMode.Universal) {
            return;
        }
        if (Aura.target != null && Aura.target == e.player) {
            this.sayEZ(e.player.getName());
            return;
        }
        if (C4Aura.target != null && C4Aura.target == e.player) {
            this.sayEZ(e.player.getName());
            return;
        }
        if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).target != null && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).target == e.player) {
            this.sayEZ(e.player.getName());
        }
    }
    
    public void sayEZ(final String pn) {
        if (this.Mode.getValue() == ModeEn.Basic) {
            final int n = (int)Math.floor(Math.random() * this.EZ.length);
            this.c = this.EZ[n].replace("%player%", pn);
        }
        else {
            if (AutoEZ.EZWORDS.isEmpty()) {
                Command.sendMessage("\u0424\u0430\u0439\u043b \u0441 AutoEZ \u043f\u0443\u0441\u0442\u043e\u0439!");
                return;
            }
            this.c = AutoEZ.EZWORDS.get(new Random().nextInt(AutoEZ.EZWORDS.size()));
            this.c = this.c.replaceAll("%player%", pn);
        }
        AutoEZ.mc.player.sendChatMessage(((boolean)this.global.getValue()) ? ("!" + this.c) : this.c);
    }
    
    static {
        AutoEZ.EZWORDS = new ArrayList<String>();
    }
    
    public enum ModeEn
    {
        Custom, 
        Basic;
    }
    
    public enum ServerMode
    {
        Universal, 
        FunnyGame, 
        NexusGrief;
    }
}
