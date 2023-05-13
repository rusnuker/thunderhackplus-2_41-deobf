//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.concurrent.*;

public class NotificationManager extends Module
{
    private static final List<Notification> notificationsnew;
    private final Setting<Float> position;
    
    public NotificationManager() {
        super("Notifications", "aga", Module.Category.CLIENT);
        this.position = (Setting<Float>)this.register(new Setting("Position", (T)1.0f, (T)0.0f, (T)1.0f));
    }
    
    public static void publicity(final String content, final int second, final Notification.Type type) {
        NotificationManager.notificationsnew.add(new Notification(content, type, second * 1000));
        if (type == Notification.Type.SUCCESS) {
            SoundUtil.playSound(SoundUtil.ThunderSound.SUCCESS);
        }
        if (type == Notification.Type.ERROR) {
            SoundUtil.playSound(SoundUtil.ThunderSound.ERROR);
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        if (NotificationManager.notificationsnew.size() > 8) {
            NotificationManager.notificationsnew.remove(0);
        }
        float startY = (float)(event.getScreenHeight() * this.position.getValue() - 36.0);
        for (int i = 0; i < NotificationManager.notificationsnew.size(); ++i) {
            final Notification notification = NotificationManager.notificationsnew.get(i);
            NotificationManager.notificationsnew.removeIf(Notification::shouldDelete);
            notification.render(startY);
            startY -= (float)(notification.getHeight() + 3.0);
        }
    }
    
    static {
        notificationsnew = new CopyOnWriteArrayList<Notification>();
    }
}
