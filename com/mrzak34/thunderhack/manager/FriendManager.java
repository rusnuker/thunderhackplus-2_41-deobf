//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import java.io.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.command.*;
import java.util.*;

public class FriendManager
{
    public static List<String> friends;
    
    public static void loadFriends() {
        try {
            final File file = new File("ThunderHack/misc/friends.txt");
            if (file.exists()) {
                try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        FriendManager.friends.add(reader.readLine());
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void saveFriends() {
        final File file = new File("ThunderHack/misc/friends.txt");
        try {
            file.createNewFile();
        }
        catch (Exception ex) {}
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final String friend : FriendManager.friends) {
                writer.write(friend + "\n");
            }
        }
        catch (Exception ex2) {}
    }
    
    public boolean isFriend(final String name) {
        return FriendManager.friends.stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }
    
    public boolean isFriend(final EntityPlayer player) {
        return this.isFriend(player.getName());
    }
    
    public boolean isEnemy(final EntityPlayer player) {
        return false;
    }
    
    public void removeFriend(final String name) {
        FriendManager.friends.remove(name);
    }
    
    public List<String> getFriends() {
        return FriendManager.friends;
    }
    
    public void addFriend(final String friend) {
        FriendManager.friends.add(friend);
        try {
            ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + friend + "/100.png", friend);
        }
        catch (Exception e) {
            Command.sendMessage("\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0441\u043a\u0438\u043d!");
        }
    }
    
    public void clear() {
        FriendManager.friends.clear();
    }
    
    static {
        FriendManager.friends = new ArrayList<String>();
    }
}
