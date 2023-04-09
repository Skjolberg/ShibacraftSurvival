package net.shibacraft.file.message;

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
public class MessageLoader implements SLLoader {

    @Getter
    private MessageSerializable messageSerializable;

    @Override
    public void load() {
        Path messagesPath = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath()).resolve("messages.yml");
        if (Files.notExists(messagesPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("messages.yml")) {
                Files.copy(in, messagesPath);
                log.info("A messages.yml has been created because it did not exist.");
            } catch (IOException e) {
                log.severe("Unable to create wallet config");
            }
        } else {
            log.info("A messages.yml has not been created because it already exists.");
        }

        try(FileReference fileReference = new FileReference(messagesPath)) {
            ConfigurationReference<CommentedConfigurationNode> messagesReferenceBuilder = fileReference.getConfigurationReference();
            CommentedConfigurationNode messagesNode = messagesReferenceBuilder.loader().load();

            if (messagesNode.isNull()) {
                log.warning("MessagesSerializable -> The messages.yml is empty or does not exist.");
            }

            messageSerializable = messagesNode.get(MessageSerializable.class);
            messagesNode.set(MessageSerializable.class, messageSerializable);
            messagesReferenceBuilder.save(messagesNode);
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
