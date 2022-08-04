package space.moontalk.mc.iman.persistence;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;

@Getter
public class FilePersistenceManager implements PersistenceManager, PluginHolder {
    @NonNull
    private final Iman plugin;

    @NonNull
    private final File databaseFile;

    public FilePersistenceManager(Iman plugin) throws Exception {
        this.plugin = plugin;

        val config  = plugin.getConfig();
        val dirName = config.getString("persistence.dir-name");

        if (dirName == null)
            throw new InvalidConfigurationException("Invalid persistence.dir-name value.");

        databaseFile = new File(plugin.getDataFolder(), dirName);
    }

    @Override
    public @NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception {
        val dir   = getPlayerFile(player);
        val files = dir.listFiles();

        return files == null ? Collections.emptyList()
                             : Arrays.stream(files)
                                     .map(f -> f.getName())
                                     .collect(Collectors.toList());
    }

    @Override
    public void saveInventory(@NonNull Player player, @NonNull String name) throws Exception {
        // TODO
    }

    @Override
    public void setInventory(@NonNull Player player, @NonNull String name) throws Exception {
        // TODO
    }

    public File getPlayerInventoryFile(@NonNull Player player, @NonNull String inventoryName) {
        return new File(getPlayerFile(player), inventoryName);
    }

    public File getPlayerFile(@NonNull Player player) {
        return new File(databaseFile, player.getUniqueId().toString());
    }
}
