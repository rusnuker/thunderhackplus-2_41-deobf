//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.util.*;
import java.text.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.google.gson.*;
import java.util.stream.*;
import java.util.*;
import java.io.*;
import com.mrzak34.thunderhack.modules.render.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class ConfigManager implements Util
{
    public static File MainFolder;
    public static File ConfigsFolder;
    public static File CustomImages;
    public static File TempFolder;
    public static File SkinsFolder;
    public static File CapesFolder;
    public static File HeadsFolder;
    public static File DiscordEmbeds;
    public static File MiscFolder;
    public static File KitsFolder;
    public static File currentConfig;
    
    public static void init() {
        if (!ConfigManager.MainFolder.exists()) {
            ConfigManager.MainFolder.mkdirs();
        }
        if (!ConfigManager.ConfigsFolder.exists()) {
            ConfigManager.ConfigsFolder.mkdirs();
        }
        if (!ConfigManager.CustomImages.exists()) {
            ConfigManager.CustomImages.mkdirs();
        }
        if (!ConfigManager.TempFolder.exists()) {
            ConfigManager.TempFolder.mkdirs();
        }
        if (!ConfigManager.SkinsFolder.exists()) {
            ConfigManager.SkinsFolder.mkdirs();
        }
        if (!ConfigManager.CapesFolder.exists()) {
            ConfigManager.CapesFolder.mkdirs();
        }
        if (!ConfigManager.HeadsFolder.exists()) {
            ConfigManager.HeadsFolder.mkdirs();
        }
        if (!ConfigManager.MiscFolder.exists()) {
            ConfigManager.MiscFolder.mkdirs();
        }
        if (!ConfigManager.KitsFolder.exists()) {
            ConfigManager.KitsFolder.mkdirs();
        }
        if (!ConfigManager.DiscordEmbeds.exists()) {
            ConfigManager.DiscordEmbeds.mkdirs();
        }
    }
    
    public static String getConfigDate(final String name) {
        final File file = new File(ConfigManager.ConfigsFolder, name + ".th");
        if (!file.exists()) {
            return "none";
        }
        final long x = file.lastModified();
        final DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm");
        final Date sol = new Date(x);
        return obj.format(sol);
    }
    
    public static void load(final String name) {
        final File file = new File(ConfigManager.ConfigsFolder, name + ".th");
        if (!file.exists()) {
            Command.sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433\u0430 " + name + " \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442!");
            return;
        }
        if (ConfigManager.currentConfig != null) {
            save(ConfigManager.currentConfig);
        }
        Thunderhack.moduleManager.onUnload();
        Thunderhack.moduleManager.onUnloadPost();
        load(file);
        Thunderhack.moduleManager.onLoad();
    }
    
    public static void load(final File config) {
        if (!config.exists()) {
            save(config);
        }
        try {
            final FileReader reader = new FileReader(config);
            final JsonParser parser = new JsonParser();
            JsonArray array = null;
            try {
                array = (JsonArray)parser.parse((Reader)reader);
            }
            catch (ClassCastException e3) {
                save(config);
            }
            JsonArray modules = null;
            try {
                final JsonObject modulesObject = (JsonObject)array.get(0);
                modules = modulesObject.getAsJsonArray("Modules");
            }
            catch (Exception e4) {
                System.err.println("Module Array not found, skipping!");
            }
            if (modules != null) {
                modules.forEach(m -> {
                    try {
                        parseModule(m.getAsJsonObject());
                    }
                    catch (NullPointerException e) {
                        System.err.println(e.getMessage());
                    }
                    return;
                });
            }
            Command.sendMessage("\u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d \u043a\u043e\u043d\u0444\u0438\u0433 " + config.getName());
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        ConfigManager.currentConfig = config;
        saveCurrentConfig();
    }
    
    public static void save(final String name) {
        final File file = new File(ConfigManager.ConfigsFolder, name + ".th");
        if (file.exists()) {
            Command.sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433 " + name + " \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442");
            return;
        }
        save(file);
        Command.sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433 " + name + " \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d!");
    }
    
    public static void save(final File config) {
        try {
            if (!config.exists()) {
                config.createNewFile();
            }
            final JsonArray array = new JsonArray();
            final JsonObject modulesObj = new JsonObject();
            modulesObj.add("Modules", (JsonElement)getModuleArray());
            array.add((JsonElement)modulesObj);
            final FileWriter writer = new FileWriter(config);
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson((JsonElement)array, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            Command.sendMessage("Cant write to config file!");
        }
    }
    
    private static void parseModule(final JsonObject object) throws NullPointerException {
        final Module module = Thunderhack.moduleManager.modules.stream().filter(m -> object.getAsJsonObject(m.getName()) != null).findFirst().orElse(null);
        if (module != null) {
            final JsonObject mobject = object.getAsJsonObject(module.getName());
            for (final Setting setting2 : module.getSettings()) {
                try {
                    final String type = setting2.getType();
                    switch (type) {
                        case "Parent": {
                            continue;
                        }
                        case "Boolean": {
                            setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsBoolean());
                            continue;
                        }
                        case "Double": {
                            setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsDouble());
                            continue;
                        }
                        case "Float": {
                            setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsFloat());
                            continue;
                        }
                        case "Integer": {
                            setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsInt());
                            continue;
                        }
                        case "String": {
                            setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsString().replace("_", " "));
                            continue;
                        }
                        case "Bind": {
                            final JsonArray array4 = mobject.getAsJsonArray("Keybind");
                            setting2.setValue(new Bind.BindConverter().doBackward(array4.get(0)));
                            setting2.getValue().setHold(array4.get(1).getAsBoolean());
                            continue;
                        }
                        case "ColorSetting": {
                            final JsonArray array5 = mobject.getAsJsonArray(setting2.getName());
                            setting2.getValue().setColor(array5.get(0).getAsInt());
                            setting2.getValue().setCycle(array5.get(1).getAsBoolean());
                            setting2.getValue().setGlobalOffset(array5.get(2).getAsInt());
                            continue;
                        }
                        case "PositionSetting": {
                            final JsonArray array6 = mobject.getAsJsonArray(setting2.getName());
                            setting2.getValue().setX(array6.get(0).getAsFloat());
                            setting2.getValue().setY(array6.get(1).getAsFloat());
                            continue;
                        }
                        case "SubBind": {
                            setting2.setValue(new SubBind.SubBindConverter().doBackward((JsonElement)mobject.getAsJsonPrimitive(setting2.getName())));
                            continue;
                        }
                        case "Enum": {
                            try {
                                final EnumConverter converter = new EnumConverter(setting2.getValue().getClass());
                                final Enum value = converter.doBackward((JsonElement)mobject.getAsJsonPrimitive(setting2.getName()));
                                setting2.setValue((value == null) ? setting2.getDefaultValue() : value);
                            }
                            catch (Exception ex) {}
                            continue;
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(module.getName());
                    System.out.println(setting2);
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static String parseLastServer(final JsonObject object) throws NullPointerException {
        Command.sendMessage(object.getAsString());
        final JsonObject mobject = object.getAsJsonObject("ClientSettings");
        return mobject.getAsJsonPrimitive("LastConfigServer").getAsString().replace("_", " ");
    }
    
    private static JsonArray getModuleArray() {
        final JsonArray modulesArray = new JsonArray();
        for (final Module m : Thunderhack.moduleManager.modules) {
            modulesArray.add((JsonElement)getModuleObject(m));
        }
        return modulesArray;
    }
    
    public static JsonObject getModuleObject(final Module m) {
        final JsonObject attribs = new JsonObject();
        final JsonParser jp = new JsonParser();
        for (final Setting setting : m.getSettings()) {
            if (setting.isEnumSetting()) {
                final EnumConverter converter = new EnumConverter(setting.getValue().getClass());
                attribs.add(setting.getName(), converter.doForward(setting.getValue()));
            }
            else {
                if (setting.isStringSetting()) {
                    final String str = setting.getValue();
                    setting.setValue(str.replace(" ", "_"));
                }
                if (setting.isColorSetting()) {
                    final JsonArray array = new JsonArray();
                    array.add((JsonElement)new JsonPrimitive((Number)setting.getValue().getRawColor()));
                    array.add((JsonElement)new JsonPrimitive(Boolean.valueOf(setting.getValue().isCycle())));
                    array.add((JsonElement)new JsonPrimitive((Number)setting.getValue().getGlobalOffset()));
                    attribs.add(setting.getName(), (JsonElement)array);
                }
                else if (setting.isPositionSetting()) {
                    final JsonArray array = new JsonArray();
                    final float num2 = setting.getValue().getX();
                    final float num3 = setting.getValue().getY();
                    array.add((JsonElement)new JsonPrimitive((Number)num2));
                    array.add((JsonElement)new JsonPrimitive((Number)num3));
                    attribs.add(setting.getName(), (JsonElement)array);
                }
                else if (setting.isBindSetting()) {
                    final JsonArray array = new JsonArray();
                    final String key = setting.getValueAsString();
                    final boolean hold = setting.getValue().isHold();
                    array.add((JsonElement)new JsonPrimitive(key));
                    array.add((JsonElement)new JsonPrimitive(Boolean.valueOf(hold)));
                    attribs.add(setting.getName(), (JsonElement)array);
                }
                else {
                    try {
                        attribs.add(setting.getName(), jp.parse(setting.getValueAsString()));
                    }
                    catch (Exception ex) {}
                }
            }
        }
        final JsonObject moduleObject = new JsonObject();
        moduleObject.add(m.getName(), (JsonElement)attribs);
        return moduleObject;
    }
    
    public static boolean delete(final File file) {
        return file.delete();
    }
    
    public static boolean delete(final String name) {
        final File file = new File(ConfigManager.ConfigsFolder, name + ".th");
        return file.exists() && delete(file);
    }
    
    public static List<String> getConfigList() {
        if (!ConfigManager.MainFolder.exists() || ConfigManager.MainFolder.listFiles() == null) {
            return null;
        }
        final List<String> list = new ArrayList<String>();
        if (ConfigManager.ConfigsFolder.listFiles() != null) {
            for (final File file : Arrays.stream(ConfigManager.ConfigsFolder.listFiles()).filter(f -> f.getName().endsWith(".th")).collect((Collector<? super File, ?, List<? super File>>)Collectors.toList())) {
                list.add(file.getName().replace(".th", ""));
            }
        }
        return list;
    }
    
    public static void saveCurrentConfig() {
        final File file = new File("ThunderHack/misc/currentcfg.txt");
        try {
            if (file.exists()) {
                final FileWriter writer = new FileWriter(file);
                writer.write(ConfigManager.currentConfig.getName().replace(".th", ""));
                writer.close();
            }
            else {
                file.createNewFile();
                final FileWriter writer = new FileWriter(file);
                writer.write(ConfigManager.currentConfig.getName().replace(".th", ""));
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static File getCurrentConfig() {
        final File file = new File("ThunderHack/misc/currentcfg.txt");
        String name = "config";
        try {
            if (file.exists()) {
                final Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    name = reader.nextLine();
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ConfigManager.currentConfig = new File(ConfigManager.ConfigsFolder, name + ".th");
    }
    
    public static void loadAlts() {
        try {
            final File file = new File("ThunderHack/misc/alts.txt");
            if (file.exists()) {
                try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        final String name = reader.readLine();
                        Thunderhack.alts.add(name);
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void saveAlts() {
        final File file = new File("ThunderHack/misc/alts.txt");
        try {
            new File("ThunderHack").mkdirs();
            file.createNewFile();
        }
        catch (Exception ex) {}
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final String name : Thunderhack.alts) {
                writer.write(name + "\n");
            }
        }
        catch (Exception ex2) {}
    }
    
    public static void loadSearch() {
        try {
            final File file = new File("ThunderHack/misc/search.txt");
            if (file.exists()) {
                try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        final String name = reader.readLine();
                        Search.defaultBlocks.add(getRegisteredBlock(name));
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void saveSearch() {
        final File file = new File("ThunderHack/misc/search.txt");
        try {
            new File("ThunderHack").mkdirs();
            file.createNewFile();
        }
        catch (Exception ex) {}
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final Block name : Search.defaultBlocks) {
                writer.write(name.getRegistryName() + "\n");
            }
        }
        catch (Exception ex2) {}
    }
    
    private static Block getRegisteredBlock(final String blockName) {
        return (Block)Block.REGISTRY.getObject((Object)new ResourceLocation(blockName));
    }
    
    static {
        ConfigManager.MainFolder = new File(ConfigManager.mc.gameDir, "ThunderHack");
        ConfigManager.ConfigsFolder = new File(ConfigManager.MainFolder, "configs");
        ConfigManager.CustomImages = new File(ConfigManager.MainFolder, "images");
        ConfigManager.TempFolder = new File(ConfigManager.MainFolder, "temp");
        ConfigManager.SkinsFolder = new File(ConfigManager.TempFolder, "skins");
        ConfigManager.CapesFolder = new File(ConfigManager.TempFolder, "capes");
        ConfigManager.HeadsFolder = new File(ConfigManager.TempFolder, "heads");
        ConfigManager.DiscordEmbeds = new File(ConfigManager.TempFolder, "embeds");
        ConfigManager.MiscFolder = new File(ConfigManager.MainFolder, "misc");
        ConfigManager.KitsFolder = new File(ConfigManager.MiscFolder, "kits");
        ConfigManager.currentConfig = null;
    }
}
