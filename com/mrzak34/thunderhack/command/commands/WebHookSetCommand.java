//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;

public class WebHookSetCommand extends Command
{
    public WebHookSetCommand() {
        super("webhook");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("\u041d\u0430\u043f\u0438\u0448\u0438 URL \u0432\u0435\u0431\u0445\u0443\u043a\u0430");
            return;
        }
        DiscordWebhook.saveurl(commands[0]);
        sendMessage("\u0423\u0441\u043f\u0435\u0448\u043d\u043e!");
    }
}
