package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Strict;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.help.HelpLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

@Command(names = {"ayuda"}, desc = "Ayuda command")
public class HelpCommand implements CommandClass {

    private final HelpLoader helpLoader;

    public HelpCommand(MainModule mainModule){
        this.helpLoader = mainModule.getFileModule().getHelpLoader();
    }

    @Command(names = "")
    public void onAyudaCommand(@Sender CommandSender sender, @Strict @OptArg("1") int page) {
        final HashMap<Integer, List<String>> map = helpLoader.getHelpSerializable().getPages();
        if (map.isEmpty() || !map.containsKey(page)) return;
        map.get(page).forEach(sender::sendMessage);
    }

}
