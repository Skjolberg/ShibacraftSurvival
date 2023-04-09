package net.shibacraft.modules;

import lombok.Getter;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.commands.Internal.CommandLoader;
import net.shibacraft.commands.Internal.CommandTranslatorProvider;
import net.shibacraft.console.logger.ConsoleLoader;
import net.shibacraft.library.loader.SLLoader;
import org.checkerframework.checker.units.qual.C;


public class MainModule implements SLLoader {

    @Getter
    private static MainModule mainModule;
    private final ShibacraftSurvival plugin;
    @Getter
    private FileModule fileModule;
    @Getter
    private EventModule eventModule;
    private CommandLoader commandLoader;

    public MainModule(ShibacraftSurvival plugin){
        this.plugin = plugin;
    }

    @Override
    public void load() {
        mainModule = this;

        final ConsoleLoader consoleLoader = new ConsoleLoader(plugin);
        consoleLoader.load();

        fileModule = new FileModule();
        fileModule.load();

        eventModule = new EventModule(plugin, fileModule);
        eventModule.load();

        final CommandTranslatorProvider commandTranslatorProvider = new CommandTranslatorProvider();
        commandLoader = new CommandLoader(plugin, commandTranslatorProvider, this);
        commandLoader.load();

    }

    @Override
    public void unload() {
        commandLoader.unload();
        fileModule.unload();
    }

    @Override
    public void reload() {
        fileModule.reload();
        commandLoader.reload();
    }

}
