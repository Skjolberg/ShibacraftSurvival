package net.shibacraft.file.help;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.HashMap;
import java.util.List;

@ConfigSerializable
public class HelpSerializable {

    @Setting(nodeFromParent = true)
    @Getter
    private HashMap<Integer, List<String>> pages = new HashMap<>();

}
