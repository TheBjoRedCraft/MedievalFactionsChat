package dev.thebjoredcraft.medievalfactionschat;

import org.bukkit.plugin.java.JavaPlugin;

public final class MedievalFactionsChat extends JavaPlugin {
    public static MedievalFactionsChat instance;

    public static MedievalFactionsChat getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getCommand("factionchat").setExecutor(new ChatCMD());
    }
    @Override
    public void onLoad() {
        instance = this;
    }
}
