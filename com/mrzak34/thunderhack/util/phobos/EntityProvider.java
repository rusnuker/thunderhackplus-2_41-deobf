//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;

public class EntityProvider
{
    private volatile List<EntityPlayer> players;
    private volatile List<Entity> entities;
    
    public EntityProvider() {
        this.players = Collections.emptyList();
        this.entities = Collections.emptyList();
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPostTick(final PostWorldTick e) {
        this.update();
    }
    
    private void update() {
        if (Util.mc.world != null) {
            this.setLists(new ArrayList<Entity>(Util.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(Util.mc.world.playerEntities));
        }
        else {
            this.setLists(Collections.emptyList(), Collections.emptyList());
        }
    }
    
    private void setLists(final List<Entity> loadedEntities, final List<EntityPlayer> playerEntities) {
        this.entities = loadedEntities;
        this.players = playerEntities;
    }
    
    public List<Entity> getEntities() {
        return this.entities;
    }
    
    public List<EntityPlayer> getPlayers() {
        return this.players;
    }
    
    public List<Entity> getEntitiesAsync() {
        return this.getEntities(!Util.mc.isCallingFromMinecraftThread());
    }
    
    public List<EntityPlayer> getPlayersAsync() {
        return this.getPlayers(!Util.mc.isCallingFromMinecraftThread());
    }
    
    public List<Entity> getEntities(final boolean async) {
        return async ? this.entities : Util.mc.world.loadedEntityList;
    }
    
    public List<EntityPlayer> getPlayers(final boolean async) {
        return async ? this.players : Util.mc.world.playerEntities;
    }
    
    public Entity getEntity(final int id) {
        final List<Entity> entities = this.getEntitiesAsync();
        if (entities != null) {
            return entities.stream().filter(e -> e != null && e.getEntityId() == id).findFirst().orElse(null);
        }
        return null;
    }
}
