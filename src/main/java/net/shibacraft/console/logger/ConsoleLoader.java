package net.shibacraft.console.logger;

import lombok.extern.java.Log;
import net.shibacraft.library.loader.SLLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

@Log
public class ConsoleLoader implements SLLoader {

    private final Logger parentLogger;
    private final ConsoleHandler consoleHandler;

    public ConsoleLoader(JavaPlugin plugin) {
        this.parentLogger = Logger.getLogger("net.shibacraft");
        this.consoleHandler = new ConsoleHandler(plugin);
    }

    @Override
    public void load() {
        parentLogger.setUseParentHandlers(false);
        parentLogger.addHandler(consoleHandler);
    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {

    }

    public void debug(boolean debug){
        if (debug) {
            parentLogger.setLevel(Level.FINE);
        } else {
            parentLogger.setLevel(Level.INFO);
        }
    }

}
