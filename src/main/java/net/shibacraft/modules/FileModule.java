package net.shibacraft.modules;

import lombok.Getter;
import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.configuration.ConfigurationLoader;
import net.shibacraft.file.help.HelpLoader;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.file.wallet.WalletLoader;
import net.shibacraft.library.loader.SLLoader;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public class FileModule implements SLLoader {


    @Getter
    private MessageLoader messageLoader;
    @Getter
    private ConfigurationLoader configurationLoader;
    @Getter
    private HelpLoader helpLoader;
    @Getter
    private WalletLoader walletLoader;
    @Getter
    private CityLoader cityLoader;

    @Override
    public void load() {
        // Plugin directory
        Path pluginPath = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath());
        if (Files.notExists(pluginPath)) {
            try {
                Files.createDirectory(pluginPath);
            } catch (IOException e) {
                log.severe("Unable to create plugin directory");
            }
        }

        messageLoader = new MessageLoader();
        messageLoader.load();

        configurationLoader = new ConfigurationLoader();
        configurationLoader.load();

        helpLoader = new HelpLoader();
        helpLoader.load();

        walletLoader = new WalletLoader();
        walletLoader.load();

        cityLoader = new CityLoader();
        cityLoader.load();
    }

    @Override
    public void unload() {
        cityLoader.unload();
    }

    @Override
    public void reload() {
        messageLoader.reload();
        configurationLoader.reload();
        helpLoader.reload();
        walletLoader.reload();
        cityLoader.reload();
    }

}
