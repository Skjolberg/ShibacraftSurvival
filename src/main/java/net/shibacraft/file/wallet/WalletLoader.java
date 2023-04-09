package net.shibacraft.file.wallet;

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
public class WalletLoader implements SLLoader {

    @Getter
    private WalletSerializable walletSerializable;

    @Override
    public void load() {
        Path walletPath = Paths.get(ShibacraftSurvival.getPlugin().getDataFolder().getPath()).resolve("wallet.yml");
        if (Files.notExists(walletPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("wallet.yml")) {
                Files.copy(in, walletPath);
                log.info("A wallet.yml has been created because it did not exist.");
            } catch (IOException e) {
                log.severe("Unable to create wallet config");
            }
        } else {
            log.info("A wallet.yml has not been created because it already exists.");
        }


        try(FileReference fileReference = new FileReference(walletPath)) {
            ConfigurationReference<CommentedConfigurationNode> walletReferenceBuilder = fileReference.getConfigurationReference();
            CommentedConfigurationNode walletNode = walletReferenceBuilder.loader().load();

            if (walletNode.isNull()) {
                log.warning("WalletSerializable -> The wallet.yml is empty or does not exist.");
            }

            walletSerializable = walletNode.get(WalletSerializable.class);
            walletNode.set(WalletSerializable.class, walletSerializable);
            walletReferenceBuilder.save(walletNode);
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
