package net.shibacraft.commands.presidente.subcommand;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.luckperms.api.model.user.User;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.commands.presidente.PresidentCommand;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.file.configuration.ConfigurationLoader;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.file.message.MessageSerializable;
import net.shibacraft.hook.LuckPermsHook;
import net.shibacraft.player.CitizenInvitation;
import net.shibacraft.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Command(names = {"add"}, desc = "Add command")
public class AddSubcommand implements CommandClass {

    private final ShibacraftSurvival plugin = ShibacraftSurvival.getPlugin();
    private final CityLoader cityLoader;
    private final MessageLoader messageLoader;
    private final ConfigurationLoader configurationLoader;

    public AddSubcommand(PresidentCommand presidentCommand){
        this.cityLoader = presidentCommand.getCityLoader();
        this.messageLoader = presidentCommand.getMessageLoader();
        this.configurationLoader = presidentCommand.getConfigurationLoader();
    }

    @Command(names = "city")
    public void onCityCommand(@Sender Player sender, @Named("city") String city) {
        MessageSerializable messageSerializable = messageLoader.getMessageSerializable();
        String cityNoColor = city.replaceAll("(?i)&[0-9A-FRX]", "");

        // No formats in name
        if (StringUtil.STRIP_AMPERSAND_FORMATS.matcher(city).find()) {
            String noFormats = messageSerializable.getInvalidCityName();
            sender.sendMessage(noFormats);
        }

        // Name length
        if (cityNoColor.length() > 15) {
            String invalidLength = messageSerializable.getInvalidCityLength();
            sender.sendMessage(invalidLength);
            return;
        }

        // City exists
        CitySerializable citySerializable = cityLoader.getCitySerializable();
        for (CitySerializable.City cityObject : citySerializable.getCitys().values()) {
            if (cityObject.getName().equalsIgnoreCase(cityNoColor)) {
                String cityExists = messageSerializable.getCityAlreadyAdded();
                sender.sendMessage(cityExists);
            }
        }

        // Add city & president
        UUID presidentUUID = sender.getUniqueId();
        int places = configurationLoader.getConfigurationSerializable().getCitizens();
        citySerializable.addPresident(presidentUUID, cityNoColor, city, places);
        // Add suffix
        User userInvitedLP = LuckPermsHook.getUser(sender);
        LuckPermsHook.addSuffix(userInvitedLP, city);
        sender.sendMessage(messageSerializable.getCitySuccessfullyAdded());
    }

    @Command(names = "user")
    public void onUserCommand(@Sender Player president, @Named("user") String user) {
        MessageSerializable msg = messageLoader.getMessageSerializable();
        UUID presidentUUID = president.getUniqueId();

        // check if it has created city
        if(!cityLoader.getCitySerializable().isPresident(presidentUUID)) {
            president.sendMessage(msg.getCreateCityFirst());
            return;
        }

        // check if it is online
        Player citizen = Bukkit.getPlayer(user);
        if(citizen == null) {
            president.sendMessage(msg.getOffline());
            return;
        }

        // check if it has a city
        UUID invited = citizen.getUniqueId();
        if(cityLoader.getCitySerializable().isCitizen(invited)) {
            president.sendMessage(msg.getAlreadyHasCity());
            return;
        }

        // places available
        CitySerializable.City city = cityLoader.getCitySerializable().getCity(presidentUUID);
        if(city.getPlaces() < 1) {
            president.sendMessage(msg.getMaxCitizens());
            return;
        }

        // he has a pending invitation
        if (CitizenInvitation.isPendingPlayer(invited)) {
            if(CitizenInvitation.isPendingPlayerOther(invited, citizen.getUniqueId())) {
                president.sendMessage(msg.getCitizenPendingOther().replace("{citizen}", user));
            } else {
                president.sendMessage(msg.getCitizenPending().replace("{citizen}", user));
            }
            return;
        }

        // add pending player
        CitizenInvitation.addPendingPlayer(invited, presidentUUID);
        Bukkit.getScheduler().runTaskLater(plugin, () -> CitizenInvitation.removePendingPlayer(invited), 300);

        // messages
        citizen.sendMessage(msg.getHasBeenInvited().replace("{president}", president.getName())
                .replace("{city}", city.getDisplayName()));
        president.sendMessage(msg.getInvitationSent().replace("{citizen}", citizen.getName()));

    }

}
