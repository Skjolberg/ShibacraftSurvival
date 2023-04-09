package net.shibacraft.hook;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.entity.Player;

public class LuckPermsHook {

    public static User getUser(Player player) {
        return LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);
    }

    public static void addSuffix(User user, String suffix) {
        user.data().add(SuffixNode.builder(suffix, 1).build());
        LuckPermsProvider.get().getUserManager().saveUser(user);
    }

    public static void removeSuffix(User user, String suffix) {
        user.data().remove(SuffixNode.builder(suffix, 1).build());
        LuckPermsProvider.get().getUserManager().saveUser(user);
    }

}
