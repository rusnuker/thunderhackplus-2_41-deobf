//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import java.nio.charset.*;
import java.io.*;
import com.mrzak34.thunderhack.command.*;
import java.util.*;
import java.net.*;

public class Spammer extends Module
{
    public static ArrayList<String> SpamList;
    public Setting<Boolean> global;
    public Setting<Integer> delay;
    private final Setting<ModeEn> Mode;
    private final Timer timer_delay;
    private String word_from_api;
    
    public Spammer() {
        super("Spammer", "\u0441\u043f\u0430\u043c\u043c\u0435\u0440", Category.MISC);
        this.global = (Setting<Boolean>)this.register(new Setting("global", (T)true));
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)5, (T)1, (T)30));
        this.Mode = (Setting<ModeEn>)this.register(new Setting("Mode", (T)ModeEn.API));
        this.timer_delay = new Timer();
        this.word_from_api = "-";
    }
    
    public static void loadSpammer() {
        try {
            final File file = new File("ThunderHack/misc/spammer.txt");
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
                    Spammer.SpamList.clear();
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
                    Spammer.SpamList = spamList;
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
        loadSpammer();
    }
    
    @Override
    public void onUpdate() {
        if (this.timer_delay.passedS(this.delay.getValue())) {
            if (this.Mode.getValue() != ModeEn.Custom) {
                this.getMsg();
                if (!Objects.equals(this.word_from_api, "-")) {
                    this.word_from_api = this.word_from_api.replace("<p>", "");
                    this.word_from_api = this.word_from_api.replace("</p>", "");
                    this.word_from_api = this.word_from_api.replace(".", "");
                    this.word_from_api = this.word_from_api.replace(",", "");
                    Spammer.mc.player.sendChatMessage(((boolean)this.global.getValue()) ? ("!" + this.word_from_api) : this.word_from_api);
                }
            }
            else {
                if (Spammer.SpamList.isEmpty()) {
                    Command.sendMessage("\u0424\u0430\u0439\u043b spammer \u043f\u0443\u0441\u0442\u043e\u0439!");
                    this.toggle();
                    return;
                }
                final String c = Spammer.SpamList.get(new Random().nextInt(Spammer.SpamList.size()));
                Spammer.mc.player.sendChatMessage(((boolean)this.global.getValue()) ? ("!" + c) : c);
            }
            this.timer_delay.reset();
        }
    }
    
    public void getMsg() {
        URL api;
        final BufferedReader bufferedReader;
        BufferedReader in;
        String inputLine;
        final Object o;
        new Thread(() -> {
            try {
                api = new URL("https://fish-text.ru/get?format=html&number=1");
                new BufferedReader(new InputStreamReader(api.openStream(), StandardCharsets.UTF_8));
                in = bufferedReader;
                inputLine = in.readLine();
                if (o != null) {
                    this.word_from_api = inputLine;
                }
            }
            catch (Exception ex) {}
        }).start();
    }
    
    static {
        Spammer.SpamList = new ArrayList<String>();
    }
    
    public enum ModeEn
    {
        Custom, 
        API;
    }
}
