package net.shibacraft.file.configuration;

import lombok.Getter;
import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.file.FileReference;
import net.shibacraft.library.loader.SLLoader;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.reference.ConfigurationReference;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public class ConfigurationLoader implements SLLoader {

    @Getter
    private ConfigurationSerializable configurationSerializable;

    @Override
    public void load() {
        Path configPath = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath()).resolve("config.yml");
        if (Files.notExists(configPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml")) {
                Files.copy(in, configPath);
                log.info("A config.yml has been created because it did not exist.");
            } catch (IOException e) {
                log.severe("Unable to create wallet config");
            }
        } else {
            log.info("A config.yml has not been created because it already exists.");
        }

        try(FileReference fileReference = new FileReference(configPath)) {
            ConfigurationReference<CommentedConfigurationNode> configReferenceBuilder = fileReference.getConfigurationReference();
            CommentedConfigurationNode configNode = configReferenceBuilder.loader().load();

            if (configNode.isNull()) {
                log.warning("ConfigurationSerializable -> The config.yml is empty or does not exist.");
            }

            configurationSerializable = configNode.get(ConfigurationSerializable.class);
            configNode.set(ConfigurationSerializable.class, configurationSerializable);
            configReferenceBuilder.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {
        load();
    }
}
