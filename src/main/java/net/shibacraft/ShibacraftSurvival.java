package net.shibacraft;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import net.shibacraft.modules.MainModule;

public class ShibacraftSurvival extends JavaPlugin {

    @Getter
    private static ShibacraftSurvival plugin;
    private MainModule mainModule;

    @Override
    public void onEnable() {
        ShibacraftSurvival.plugin = this;

        mainModule = new MainModule(this);
        mainModule.load();

    }

    @Override
    public void onDisable() {
        mainModule.unload();
    }


}
