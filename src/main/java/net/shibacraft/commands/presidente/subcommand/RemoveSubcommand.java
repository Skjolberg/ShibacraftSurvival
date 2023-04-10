package net.shibacraft.commands.presidente.subcommand;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.shibacraft.commands.presidente.PresidentCommand;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.file.message.MessageSerializable;
import net.shibacraft.hook.LuckPermsHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Command(names = {"remove"}, desc = "Remove command")
public class RemoveSubcommand implements CommandClass {

    private final CityLoader cityLoader;
    private final MessageLoader messageLoader;

    public RemoveSubcommand(PresidentCommand presidentCommand) {
        this.cityLoader = presidentCommand.getCityLoader();
        this.messageLoader = presidentCommand.getMessageLoader();
    }

    @Command(names = "city")
    public void onCityCommand(@Sender Player sender) {
        UUID presidentUUID = sender.getUniqueId();
        CitySerializable citySerializable = cityLoader.getCitySerializable();
        MessageSerializable messageSerializable = messageLoader.getMessageSerializable();
        // Return if it not president
        if (!citySerializable.isPresident(presidentUUID)) {
            String noCity = messageSerializable.getCreateCityFirst();
            sender.sendMessage(noCity);
            return;
        }

        // Citizens remove
        CitySerializable.City cityObject = citySerializable.getCity(presidentUUID);
        cityObject.removeCitizens();

        // Suffix President remove
        User userInvitedLP = LuckPermsHook.getUser(sender);
        LuckPermsHook.removeSuffix(userInvitedLP, cityObject.getDisplayName());
        // City & president remove
        citySerializable.removePresident(sender.getUniqueId());
        String allRemoved = messageSerializable.getCitySuccessfullyEliminated();
        sender.sendMessage(allRemoved);
    }

    @Command(names = "user")
    public void onUserCommand(@Sender Player sender, @Named("user") String user) {
        MessageSerializable messageSerializable = messageLoader.getMessageSerializable();
        CitySerializable citySerializable = cityLoader.getCitySerializable();
        UUID presidentUUID = sender.getUniqueId();

        // is president
        if (!citySerializable.isPresident(presidentUUID)) {
            sender.sendMessage(messageSerializable.getCreateCityFirst());
            return;
        }

        // is citizen
        UUID citizenUUID = Bukkit.getOfflinePlayer(user).getUniqueId();
        boolean isCitizenFromPresident = citySerializable.getCity(presidentUUID).getCitizens().contains(citizenUUID);

        if (!isCitizenFromPresident) {
            sender.sendMessage(messageSerializable.getCitizenNoFromYourCity());
            return;
        }

        // Suffix Citizen remove
        UserManager userManager = LuckPermsProvider.get().getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(citizenUUID);
        CitySerializable.City city = cityLoader.getCitySerializable().getCity(presidentUUID);

        userFuture.thenAcceptAsync(userI -> LuckPermsHook.removeSuffix(userI, city.getDisplayName()));

        // Remove from city
        citySerializable.removeCitizen(citizenUUID);
        String citizenRemoved = messageSerializable.getCitizenKicked();
        sender.sendMessage(citizenRemoved);

        Player citizen = Bukkit.getPlayer(citizenUUID);
        if (citizen != null) {
            citizen.sendMessage(messageSerializable.getCitizenKickedFromPresident());
        }

    }

}
