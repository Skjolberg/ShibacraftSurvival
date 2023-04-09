package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Strict;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.file.wallet.WalletLoader;
import net.shibacraft.modules.MainModule;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

@Command(names = {"wallet"}, desc = "Wallet command")
public class WalletCommand implements CommandClass {

    private final WalletLoader walletLoader;

    public WalletCommand(MainModule mainModule){
        this.walletLoader = mainModule.getFileModule().getWalletLoader();
    }

    @Command(names = "")
    public void onWalletCommand(@Sender Player sender, @Strict @OptArg("1") int page) {
        final HashMap<Integer, List<String>> map = walletLoader.getWalletSerializable().getPages();
        if (map.isEmpty() || !map.containsKey(page)) return;
        map.get(page).forEach(sender::sendMessage);
    }

}
