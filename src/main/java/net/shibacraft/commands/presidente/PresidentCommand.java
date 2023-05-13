package net.shibacraft.commands.presidente;

import lombok.Getter;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.shibacraft.commands.presidente.subcommand.AddSubcommand;
import net.shibacraft.commands.presidente.subcommand.RemoveSubcommand;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.file.configuration.ConfigurationLoader;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.file.message.MessageSerializable;
import net.shibacraft.modules.MainModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Command(names = {"presidente"}, permission = "shibacraft.presidente", desc = "Presidente command")
@SubCommandClasses({
        RemoveSubcommand.class,
        AddSubcommand.class
})
public class PresidentCommand implements CommandClass {

    @Getter
    private final CityLoader cityLoader;
    @Getter
    private final MessageLoader messageLoader;
    @Getter
    private final ConfigurationLoader configurationLoader;

    public PresidentCommand(MainModule mainModule){
        this.cityLoader = mainModule.getFileModule().getCityLoader();
        this.messageLoader = mainModule.getFileModule().getMessageLoader();
        this.configurationLoader = mainModule.getFileModule().getConfigurationLoader();
    }

    @Command(names = "")
    public void onPresidenteCommand(@Sender Player sender) {
        List<String> noArg = messageLoader.getMessageSerializable().getUsagePresident();
        for (String str : noArg) {
            sender.sendMessage(str);
        }
    }

    @Command(names = "list")
    public void onPresidenteStatusCommand(@Sender Player sender) {
        CitySerializable citySerializable = cityLoader.getCitySerializable();
        UUID presidentUUID = sender.getUniqueId();
        // is president
        if(!citySerializable.isPresident(presidentUUID)) {
            String noCity = messageLoader.getMessageSerializable().getCreateCityFirst();
            sender.sendMessage(noCity);
            return;
        }

        final CitySerializable.City city = citySerializable.getCity(presidentUUID);
        final MessageSerializable serializableMsg = messageLoader.getMessageSerializable();

        // no there citizens
        if (city.getCitizens().isEmpty()) {
            String noCitizens = serializableMsg.getListEmpty();
            sender.sendMessage(noCitizens);
        }

        // citizens print
        sender.sendMessage(serializableMsg.getCitizenListHeader());
        int it = 1;
        for (UUID uuid : city.getCitizens()) {
            String playerName = Bukkit.getOfflinePlayer(uuid).getName();
            sender.sendMessage(it + ". " + playerName);
            it++;
        }
        sender.sendMessage(serializableMsg.getCitizenListFooter());
    }

}
