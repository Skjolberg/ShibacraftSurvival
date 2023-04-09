package net.shibacraft.listener;

import lombok.Getter;
import net.luckperms.api.model.user.User;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.file.message.MessageLoader;
import net.shibacraft.file.message.MessageSerializable;
import net.shibacraft.hook.LuckPermsHook;
import net.shibacraft.modules.FileModule;
import net.shibacraft.player.CitizenInvitation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    @Getter
    private final CityLoader cityLoader;
    @Getter
    private final MessageLoader messageLoader;

    public PlayerChatListener(FileModule fileModule) {
        this.cityLoader = fileModule.getCityLoader();
        this.messageLoader = fileModule.getMessageLoader();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(AsyncPlayerChatEvent event) {
        final MessageSerializable msgSerializable = messageLoader.getMessageSerializable();
        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        final String msg = event.getMessage();

        if (!CitizenInvitation.isPendingPlayer(player.getUniqueId())) return;

        if (msg.equalsIgnoreCase("confirmar")) {
            event.setCancelled(true);

            // President data
            UUID presidentUUID = CitizenInvitation.getRequests().get(playerUUID);
            Player president = Bukkit.getPlayer(presidentUUID);

            // Send message if not null to president
            if(president != null) {
                String presidentMsg = msgSerializable.getHasAcceptedInvitation().replace("{citizen}", player.getName());
                president.sendMessage(presidentMsg);
            }

            // Send message to citizen
            String cityName = cityLoader.getCitySerializable().getCitys().get(presidentUUID).getDisplayName();
            String citizenMsg = msgSerializable.getInvitationAccepted().replace("{city}", cityName);
            player.sendMessage(citizenMsg);

            // City's
            CitySerializable.City city = cityLoader.getCitySerializable().getCity(presidentUUID);
            // Add citizen
            city.addCitizen(playerUUID);
            // Places
            city.setPlaces(city.getPlaces() - 1);

            // Remove citizen from cache
            CitizenInvitation.removePendingPlayer(playerUUID);

            // Add suffix
            User userInvitedLP = LuckPermsHook.getUser(player);
            LuckPermsHook.addSuffix(userInvitedLP, cityName);

        }

    }

}
