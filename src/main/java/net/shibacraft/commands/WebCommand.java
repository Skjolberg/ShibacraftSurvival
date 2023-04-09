package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

@Command(names = {"web"}, desc = "Web command")
public class WebCommand implements CommandClass {

    private final MessageLoader messageLoader;

    public WebCommand(MainModule mainModule){
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onWebCommand(@Sender CommandSender sender) {
        String web = messageLoader.getMessageSerializable().getWeb();
        sender.sendMessage(web);
    }

}
