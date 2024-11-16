package dev.jsinco.discord.framework.settings;

import dev.jsinco.discord.framework.FrameWork;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.standard.StandardSerdes;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractOkaeriConfig extends OkaeriConfig {

    public static final Path dataFolderPath = FrameWork.getDataFolderPath();


    public static <T extends AbstractOkaeriConfig> T createConfig(Class<T> configClass) {
        return createConfig(configClass, configClass.getSimpleName().toLowerCase() + ".yml");
    }

    public static <T extends AbstractOkaeriConfig> T createConfig(Class<T> configClass, String filename, BidirectionalTransformer<?, ?>... transformers) {
        return createConfig(configClass, dataFolderPath, filename, new YamlSnakeYamlConfigurer(), transformers);
    }

    public static <T extends AbstractOkaeriConfig> T createConfig(Class<T> configClass, Path path, String filename, Configurer configurer, BidirectionalTransformer<?, ?>... transformers) {
        return ConfigManager.create(configClass, (it) -> {
            it.withConfigurer(configurer, new StandardSerdes());
            it.withBindFile(path.resolve(filename));
            it.withRemoveOrphans(true);
            it.saveDefaults();

            for (BidirectionalTransformer<?, ?> pack : transformers) {
                it.withSerdesPack(registry -> registry.register(pack));
            }

            it.load(true);
        });
    }

    public void saveAsync() throws OkaeriException {
        CompletableFuture.runAsync(this::save);
    }
}
