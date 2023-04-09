package net.shibacraft.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.luckperms.api.model.user.User;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.hook.LuckPermsHook;
import net.shibacraft.modules.MainModule;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Command(names = {"ciudadano"}, desc = "Ciudadano command")
public class CitizenCommand implements CommandClass {

    private final CityLoader cityLoader;
    private final MessageLoader messageLoader;

    public CitizenCommand(MainModule mainModule){
        this.cityLoader = mainModule.getFileModule().getCityLoader();
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
    }

    @Command(names = "")
    public void onCitizenCommand(@Sender Player sender) {
        List<String> noArg = messageLoader.getMessageSerializable().getUsageCitizen();
        for (String str : noArg) {
            sender.sendMessage(str);
        }
    }

    @Command(names = "abandon")
    public void onCitizenAbandonCommand(@Sender Player sender) {
        // CitySerializable
        CitySerializable citySerializable = cityLoader.getCitySerializable();
        UUID citizenUUID = sender.getUniqueId();
        if (!citySerializable.isCitizen(citizenUUID)) {
            String noCitizen = messageLoader.getMessageSerializable().getUserNoCitizen();
            sender.sendMessage(noCitizen);
            return;
        }

        // Suffix remove
        User user = LuckPermsHook.getUser(sender);
        CitySerializable.City city = citySerializable.getCityOfCitizen(citizenUUID);
        String displayNameCity = city.getDisplayName();
        LuckPermsHook.removeSuffix(user, displayNameCity);

        // Remove on city
        citySerializable.removeCitizen(citizenUUID);
        String citizenAbandon = messageLoader.getMessageSerializable().getCitizenAbandon().replace("{city}", displayNameCity);
        sender.sendMessage(citizenAbandon);
    }

}
