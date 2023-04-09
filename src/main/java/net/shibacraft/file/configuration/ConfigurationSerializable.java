package net.shibacraft.file.configuration;


import lombok.Getter;
import lombok.ToString;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Getter
@ToString
@ConfigSerializable
public class ConfigurationSerializable {

    private int citizens = 15;
    private int expire = 15;

}
