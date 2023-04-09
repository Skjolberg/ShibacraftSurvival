package net.shibacraft.modules;

import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.library.loader.SLLoader;
import net.shibacraft.listener.PlayerChatListener;

import static org.bukkit.Bukkit.getServer;

public class EventModule implements SLLoader {

    private final ShibacraftSurvival plugin;
    private final FileModule fileModule;

    public EventModule(ShibacraftSurvival plugin, FileModule fileModule) {
        this.plugin = plugin;
        this.fileModule = fileModule;
    }

    @Override
    public void load() {
        getServer().getPluginManager().registerEvents(new PlayerChatListener(fileModule), plugin);
    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {

    }


}
