package net.shibacraft.modules;

import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.library.loader.SLLoader;
import net.shibacraft.listener.PlayerChatListener;
import net.shibacraft.placeholder.CityExpansion;
import org.bukkit.Bukkit;

import static org.bukkit.Bukkit.getServer;

@Log
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

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            CityExpansion cityExpansion = new CityExpansion(fileModule);
            cityExpansion.register();
            cityExpansion.persist();

            log.info("PlaceholderAPI has been registered.");
        } else {
            log.warning("PlaceholderAPI was not found.");
        }

    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {

    }


}
