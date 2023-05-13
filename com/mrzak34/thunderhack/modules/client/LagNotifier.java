//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import java.text.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.notification.*;

public class LagNotifier extends Module
{
    private final ResourceLocation ICON;
    private final Setting<Integer> timeout;
    private Timer notifTimer;
    private Timer packetTimer;
    private Timer rubberbandTimer;
    private boolean isLag;
    
    public LagNotifier() {
        super("LagNotifier", "\u043e\u043f\u043e\u0432\u0435\u0449\u0430\u0435\u0442 \u043e-\u043f\u0440\u043e\u0431\u043b\u0435\u043c\u0430\u0445 \u0441 \u0441\u0435\u0440\u0432\u0435\u0440\u043e\u043c", "LagNotifier", Category.CLIENT);
        this.ICON = new ResourceLocation("textures/lagg.png");
        this.timeout = (Setting<Integer>)this.register(new Setting("Timeout", (T)5, (T)5, (T)30));
        this.notifTimer = new Timer();
        this.packetTimer = new Timer();
        this.rubberbandTimer = new Timer();
        this.isLag = false;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.rubberbandTimer.reset();
        }
        this.packetTimer.reset();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (!this.rubberbandTimer.passedMs(5000L)) {
            if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.#");
                FontRender.drawCentString6("\u041e\u0431\u043d\u0430\u0440\u0443\u0436\u0435\u043d \u0440\u0443\u0431\u0431\u0435\u0440\u0431\u0435\u043d\u0434! " + decimalFormat.format((5000.0f - this.rubberbandTimer.getTimeMs()) / 1000.0f), (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
            }
            else {
                final DecimalFormat decimalFormat = new DecimalFormat("#.#");
                FontRender.drawCentString6("Rubberband detected! " + decimalFormat.format((5000.0f - this.rubberbandTimer.getTimeMs()) / 1000.0f), (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
            }
        }
        if (this.packetTimer.passedMs(this.timeout.getValue() * 1000)) {
            if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.#");
                FontRender.drawCentString6("\u0421\u0435\u0440\u0432\u0435\u0440 \u043f\u0435\u0440\u0435\u0441\u0442\u0430\u043b \u043e\u0442\u0432\u0435\u0447\u0430\u0442\u044c! " + decimalFormat.format(this.packetTimer.getTimeMs() / 1000.0f), (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
                Drawable.drawTexture(this.ICON, (float)e.getScreenWidth() / 2.0f - 40.0f, (float)e.getScreenHeight() / 3.0f - 120.0f, 80.0, 80.0, new Color(16768768));
            }
            else {
                final DecimalFormat decimalFormat = new DecimalFormat("#.#");
                FontRender.drawCentString6("Server offline! " + decimalFormat.format(this.packetTimer.getTimeMs() / 1000.0f), (float)e.getScreenWidth() / 2.0f, (float)e.getScreenHeight() / 3.0f, new Color(16768768).getRGB());
                Drawable.drawTexture(this.ICON, (float)e.getScreenWidth() / 2.0f - 40.0f, (float)e.getScreenHeight() / 3.0f - 120.0f, 80.0, 80.0, new Color(16768768));
            }
        }
        if (Thunderhack.serverManager.getTPS() < 10.0f && this.notifTimer.passedMs(60000L)) {
            if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                NotificationManager.publicity("LagNotifier \u0422\u041f\u0421 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u043d\u0438\u0436\u0435 10! \u0420\u0435\u043a\u043e\u043c\u0435\u043d\u0434\u0443\u0435\u0442\u0441\u044f \u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c TPSSync", 8, Notification.Type.ERROR);
            }
            else {
                NotificationManager.publicity("LagNotifier TPS below 10! It is recommended to enable TPSSync", 8, Notification.Type.ERROR);
            }
            this.isLag = true;
            this.notifTimer.reset();
        }
        if (Thunderhack.serverManager.getTPS() > 15.0f && this.isLag) {
            if (((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
                NotificationManager.publicity("\u0422\u041f\u0421 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0441\u0442\u0430\u0431\u0438\u043b\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u043b\u0441\u044f!", 8, Notification.Type.SUCCESS);
            }
            else {
                NotificationManager.publicity("TPS of the server has stabilized!", 8, Notification.Type.SUCCESS);
            }
            this.isLag = false;
        }
    }
}
