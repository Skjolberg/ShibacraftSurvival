package net.shibacraft.file.city;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.shibacraft.hook.LuckPermsHook;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@ConfigSerializable
public class CitySerializable {

    @Setting(nodeFromParent = true)
    private HashMap<UUID, City> city = new HashMap<>();

    @Getter
    @NoArgsConstructor
    @ConfigSerializable
    public static class City {

        private String name = "none";
        @Setter
        private String displayName = "none";
        private List<UUID> citizens = new ArrayList<>();
        @Setter
        private int places = 20;

        public City(String name) {
            this(name, name, new ArrayList<>(), 20);
        }

        public City(String name, String displayName) {
            this(name, displayName, new ArrayList<>(), 20);
        }

        public City(String name, String displayName, int places) {
            this(name, displayName, new ArrayList<>(), places);
        }

        public City(String name, String displayName, List<UUID> citizens, int places) {
            this.name = name;
            this.displayName = displayName;
            this.citizens = citizens;
            this.places = places;
        }

        public void addCitizen(UUID citizen) {
            citizens.add(citizen);
        }

        public void removeCitizen(UUID citizen) {
            citizens.remove(citizen);
        }

        public void removeCitizens() {
            for (UUID citizen : citizens) {
                UserManager userManager = LuckPermsProvider.get().getUserManager();
                CompletableFuture<User> userFuture = userManager.loadUser(citizen);

                userFuture.thenAcceptAsync(userI -> {
                    LuckPermsHook.removeSuffix(userI, displayName);
                });
            }
            citizens.clear();
        }


    }

    public City getCity(UUID president) {
        return city.get(president);
    }

    public City getCityOfCitizen(UUID citizen) {
        for (City cityI : city.values()) {
            if(cityI.getCitizens().contains(citizen)) {
                return cityI;
            }
        }
        return null;
    }

    public void addPresident(UUID president, String cityName, String displayName, int places) {
        city.put(president, new City(cityName, displayName, places));
        city.get(president).getCitizens().add(president);
    }

    public UUID getPresident(UUID citizen) {
        for (Map.Entry<UUID, City> city : city.entrySet()) {
            if(city.getValue().getCitizens().contains(citizen)) {
                return city.getKey();
            }
        }
        return null;
    }

    public void removePresident(UUID president) {
        city.remove(president);
    }

    public void removeCitizen(UUID citizen) {
        city.forEach((key, value) -> value.getCitizens().remove(citizen));
    }

    public boolean isPresident(UUID president) {
        return city.containsKey(president);
    }

    public boolean isCitizen(UUID citizen) {
        return city.entrySet().stream().anyMatch(cityEntry -> cityEntry.getValue().getCitizens().contains(citizen));
    }

    public Map<UUID, City> getCitys() {
        return city;
    }

}
