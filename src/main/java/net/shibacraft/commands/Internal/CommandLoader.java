package net.shibacraft.commands.Internal;

import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.SimplePartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.command.Command;
import me.fixeddev.commandflow.translator.DefaultTranslator;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.commands.*;
import net.shibacraft.commands.presidente.PresidentCommand;
import net.shibacraft.library.loader.SLLoader;
import net.shibacraft.modules.MainModule;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader implements SLLoader {

    private final CommandManager commandManager;
    private final MainModule mainModule;
    private final CommandTranslatorProvider commandTranslatorProvider;

    public CommandLoader(ShibacraftSurvival plugin, CommandTranslatorProvider commandTranslatorProvider,
                         MainModule mainModule) {
        this.commandManager = new BukkitCommandManager(plugin.getName());
        this.commandManager.setTranslator(new DefaultTranslator(commandTranslatorProvider));
        this.mainModule = mainModule;
        this.commandTranslatorProvider = commandTranslatorProvider;
    }

    @Override
    public void load() {
        PartInjector partInjector = new SimplePartInjector();
        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder treeBuilder = new AnnotatedCommandTreeBuilderImpl(partInjector);

        List<Command> commands = new ArrayList<>();
        commands.addAll(treeBuilder.fromClass(new ShibacraftCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new PresidentCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new CitizenCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new DiscordCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new HelpCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new MapCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new WalletCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new WebCommand(mainModule)));
        commands.addAll(treeBuilder.fromClass(new WikiCommand(mainModule)));

        commandManager.registerCommands(commands);

    }

    @Override
    public void unload() {
        commandManager.unregisterAll();
    }

    @Override
    public void reload() {
        commandTranslatorProvider.reload();
    }

}
