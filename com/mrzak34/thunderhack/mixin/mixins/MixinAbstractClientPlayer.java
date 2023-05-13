//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fml.client.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.image.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer
{
    public ResourceLocation caperes;
    HashMap<String, ResourceLocation> users;
    
    public MixinAbstractClientPlayer() {
        this.users = new HashMap<String, ResourceLocation>();
    }
    
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        final String name = Objects.requireNonNull(this.getPlayerInfo()).getGameProfile().getName();
        if (ThunderUtils.isTHUser(name) && ((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).showcapes.getValue()) {
            if (!this.users.containsKey(name)) {
                try {
                    final BufferedImage image = ThunderUtils.getCustomCape(name);
                    final DynamicTexture texture = new DynamicTexture(image);
                    final PNGtoResourceLocation.WrappedResource wr = new PNGtoResourceLocation.WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name, texture));
                    callbackInfoReturnable.setReturnValue((Object)(this.caperes = wr.location));
                    this.users.put(name, this.caperes);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                callbackInfoReturnable.setReturnValue((Object)this.users.get(name));
            }
        }
    }
}
