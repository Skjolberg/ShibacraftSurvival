package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.command.CommandSender;

@Command(names = {"mapa"}, desc = "Mapa command")
public class MapCommand implements CommandClass {

    private final MessageLoader messageLoader;

    public MapCommand(MainModule mainModule){
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onMapaCommand(@Sender CommandSender sender) {
        String mapa = messageLoader.getMessageSerializable().getMap();
        sender.sendMessage(mapa);
    }

}
