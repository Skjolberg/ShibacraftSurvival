package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

@Command(names = {"wiki"}, desc = "Wiki command")
public class WikiCommand implements CommandClass {

    private final MessageLoader messageLoader;

    public WikiCommand(MainModule mainModule){
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onWikiCommand(@Sender CommandSender sender) {
        String wiki = messageLoader.getMessageSerializable().getWiki();
        sender.sendMessage(wiki);
    }

}
