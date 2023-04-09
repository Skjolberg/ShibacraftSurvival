package net.shibacraft.file.help;

import lombok.Getter;
import lombok.extern.java.Log;
import net.shibacraft.ShibacraftSurvival;
import net.shibacraft.file.FileReference;
import net.shibacraft.file.configuration.ConfigurationSerializable;
import net.shibacraft.library.loader.SLLoader;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.reference.ConfigurationReference;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public class HelpLoader implements SLLoader {

    @Getter
    private HelpSerializable helpSerializable;

    @Override
    public void load() {
        Path helpPath = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath()).resolve("help.yml");
        if (Files.notExists(helpPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("help.yml")) {
                Files.copy(in, helpPath);
                log.info("A help.yml has been created because it did not exist.");
            } catch (IOException e) {
                log.severe("Unable to create wallet config");
            }
        } else {
            log.info("A help.yml has not been created because it already exists.");
        }

        try(FileReference fileReference = new FileReference(helpPath)) {
            ConfigurationReference<CommentedConfigurationNode> helpReferenceBuilder = fileReference.getConfigurationReference();
            CommentedConfigurationNode helpNode = helpReferenceBuilder.loader().load();

            if (helpNode.isNull()) {
                log.warning("HelpSerializable -> The help.yml is empty or does not exist.");
            }

            helpSerializable = helpNode.get(HelpSerializable.class);
            helpNode.set(HelpSerializable.class, helpSerializable);
            helpReferenceBuilder.save(helpNode);
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
