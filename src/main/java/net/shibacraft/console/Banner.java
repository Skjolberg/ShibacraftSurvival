package net.shibacraft.console;

import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.library.loader.SLLoader;

@Log
public class Banner implements SLLoader {

    private final ShibacraftSurvival plugin;

    public Banner(ShibacraftSurvival plugin){
        this.plugin = plugin;
    }

    @Override
    public void load() {
        log.info("&7Plugin: &2" + plugin.getName());
        log.info("&7Version: &2" + plugin.getDescription().getVersion());
        log.info("&7Author: &b" + plugin.getDescription().getAuthors().get(0));
        log.info("");
    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {

    }

}