package net.shibacraft.placeholder;

import lombok.extern.java.Log;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.shibacraft.file.city.CityLoader;
import net.shibacraft.file.city.CitySerializable;
import net.shibacraft.library.chat.SLTextColor;
import net.shibacraft.modules.FileModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Log
public class CityExpansion extends PlaceholderExpansion {

    private final CityLoader cityLoader;

    public CityExpansion(FileModule fileModule) {
        this.cityLoader = fileModule.getCityLoader();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "shibacraftsurvival";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DaVaMu";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String str) {
        log.fine("Expansion: " + str);
        if (str.equalsIgnoreCase("citizens")) {
            CitySerializable citySerializable = cityLoader.getCitySerializable();
            CitySerializable.City city = citySerializable.getCityOfCitizen(player.getUniqueId());

            List<UUID> playersUUID = city.getCitizens();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("&eCiudadanos: ").append("&a\n");
            int it = 0;
            for (UUID uuid : playersUUID) {
                it++;
                stringBuilder.append("\n").append( " - ").append(Bukkit.getOfflinePlayer(uuid).getName()).append(" ");
                if(playersUUID.size() == it) stringBuilder.append("\n");
            }
            return SLTextColor.color(stringBuilder.toString());
        }
        return "no valid expansion";

    }

}
