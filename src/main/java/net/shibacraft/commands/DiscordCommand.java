package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

@Command(names = {"discord"}, desc = "Discord command")
public class DiscordCommand implements CommandClass {

    private final MessageLoader messageLoader;

    public DiscordCommand(MainModule mainModule){
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onDiscordCommand(@Sender CommandSender sender) {
        String discord = messageLoader.getMessageSerializable().getDiscord();
        sender.sendMessage(discord);
    }

}
