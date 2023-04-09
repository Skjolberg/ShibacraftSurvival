package net.shibacraft.player;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class CitizenInvitation {

    private final Map<UUID, UUID> requests = new HashMap<>();

    public void addPendingPlayer(UUID invited, UUID president) {
        requests.put(invited, president);
    }

    public void removePendingPlayer(UUID invited) {
        requests.remove(invited);
    }

    public boolean isPendingPlayer(UUID invited) {
        return requests.containsKey(invited);
    }

    public boolean isPendingPlayerOther(UUID invited, UUID president) {
        return requests.get(invited).equals(president);
    }

    public Map<UUID, UUID> getRequests() {
        return requests;
    }

    public UUID getPresidentUUID(UUID uuid) {
        return requests.get(uuid);
    }


}
