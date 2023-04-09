package net.shibacraft.file.city;

import lombok.Getter;
import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.file.FileReference;
import net.shibacraft.library.loader.SLLoader;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.reference.ConfigurationReference;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public class CityLoader implements SLLoader {

    @Getter
    private CitySerializable citySerializable;
    private final Path CITY_PATH = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath()).resolve("city.yml");
    private ConfigurationReference<CommentedConfigurationNode> cityReferenceBuilder;

    @Override
    public void load() {
        loadCity();
    }

    @Override
    public void unload() {
        saveCity();
    }

    @Override
    public void reload() {
        loadCity();
    }

    private void loadCity() {
        try(FileReference fileReference = new FileReference(CITY_PATH)) {
            cityReferenceBuilder = fileReference.getConfigurationReference();
            CommentedConfigurationNode cityNode = cityReferenceBuilder.loader().load();

            if (cityNode.isNull()) {
                log.warning("CitySerializable -> The city.yml is empty or does not exist.");
            }

            citySerializable = cityNode.get(CitySerializable.class);
            cityNode.set(CitySerializable.class, citySerializable);
            cityReferenceBuilder.save(cityNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCity() {
        try {
            CommentedConfigurationNode cityNode = cityReferenceBuilder.loader().load();
            if (cityNode.isNull()) {
                log.warning("CitySerializable -> The city.yml is empty or does not exist.");
            }
            cityNode.set(CitySerializable.class, citySerializable);
            cityReferenceBuilder.save(cityNode);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
