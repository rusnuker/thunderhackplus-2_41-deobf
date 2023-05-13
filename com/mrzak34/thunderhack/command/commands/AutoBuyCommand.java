//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.misc.*;
import java.util.*;

public class AutoBuyCommand extends Command
{
    public AutoBuyCommand() {
        super("ab");
    }
    
    public void execute(final String[] args) {
        if (args.length >= 4) {
            if (args[0] == null) {
                Command.sendMessage(this.usage());
            }
            if (args[0].equals("add")) {
                final String itemName = args[1];
                final String price = args[2].toUpperCase();
                final String ench1 = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 3, args.length - 1));
                final String[] ench2 = ench1.split(" ");
                final AutoBuy.AutoBuyItem item = new AutoBuy.AutoBuyItem(itemName, ench2, Integer.parseInt(price), 0, args.length == 4);
                AutoBuy.items.add(item);
                sendMessage("\u0414\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u043f\u0440\u0435\u0434\u043c\u0435\u0442 " + itemName + " \u0441\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c\u044e \u0434\u043e " + price + ((args.length == 4) ? " \u0431\u0435\u0437 \u0447\u0430\u0440\u043e\u0432" : (" \u0441 \u0447\u0430\u0440\u0430\u043c\u0438 " + ench1)));
            }
        }
        else if (args.length > 1) {
            if (args[0].equals("list")) {
                AutoBuy.items.forEach(itm -> sendMessage("\n####################\n\u041f\u0440\u0435\u0434\u043c\u0435\u0442: " + itm.getName() + "\n\u041c\u0430\u043a\u0441 \u0446\u0435\u043d\u0430: " + itm.getPrice1() + "\n\u0427\u0430\u0440\u044b: " + Arrays.toString(itm.getEnchantments()) + "\n####################"));
            }
            if (args[0].equals("remove")) {
                final String itemName = args[1];
                boolean removed = false;
                for (final AutoBuy.AutoBuyItem abitem : AutoBuy.items) {
                    if (Objects.equals(abitem.getName(), itemName)) {
                        AutoBuy.items.remove(abitem);
                        removed = true;
                    }
                }
                if (removed) {
                    sendMessage("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d!");
                }
                else {
                    sendMessage("\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u0430 \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442!");
                }
            }
        }
        else {
            sendMessage(this.usage());
        }
    }
    
    String usage() {
        return ".ab add/remove/list   \u043f\u0440\u0438\u043c\u0435\u0440: .ab add bow 51(1) 48(5)\n\u0410\u0439\u0434\u0438 \u0437\u0430\u0447\u0430\u0440\u043e\u0432 \u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u043d\u0430 \u0441\u0430\u0439\u0442\u0435 https://idpredmetov.ru/id-zacharovanij/\n\u0415\u0441\u043b\u0438 \u043b\u0435\u0432\u0435\u043b\u0430 \u0437\u0430\u0447\u0430\u0440\u0430 \u043d\u0435\u0442 - \u043f\u0438\u0448\u0435\u043c \u043f\u0435\u0440\u0432\u044b\u0439 \u043b\u0435\u0432\u0435\u043b";
    }
}
