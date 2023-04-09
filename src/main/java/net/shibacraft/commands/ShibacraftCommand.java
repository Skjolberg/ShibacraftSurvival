package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

@Command(names = {"shibacraft"}, permission = "shibacraft.use", desc = "Shibacraft main command")
public class ShibacraftCommand implements CommandClass {

    private final MainModule mainModule;
    private final MessageLoader messageLoader;

    public ShibacraftCommand(MainModule mainModule){
        this.mainModule = mainModule;
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onMainCommand(@Sender CommandSender sender) {
        sender.sendMessage(messageLoader.getMessageSerializable().getNoArguments());
    }

    @Command(names = "reload", permission = "shibacraft.admin")
    public void onTestCommand(@Sender CommandSender sender) {
        mainModule.reload();
        String reload = messageLoader.getMessageSerializable().getPluginReload();
        sender.sendMessage(reload);
    }

}
