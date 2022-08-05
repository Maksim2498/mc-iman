package space.moontalk.mc.iman.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;

@Getter
public class FilePersistenceManager implements PersistenceManager, PluginHolder {
    public static final @NonNull String EXTENSION = ".inv";

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

        databaseFile.mkdir();
    }

    @Override
    public @NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception {
        val dir   = getPlayerFile(player);
        val files = dir.listFiles();

        return files == null ? Collections.emptyList()
                             : Arrays.stream(files)
                                     .map(f -> f.getName())
                                     .filter(s -> s.endsWith(EXTENSION))
                                     .map(f -> f.substring(0, f.length() - EXTENSION.length()))
                                     .collect(Collectors.toList());
    }

    @Override
    public void saveInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception {
        BukkitObjectOutputStream stream = null;

        try {
            val file = getPlayerInventoryFile(player, inventoryName); 

            file.createNewFile();

            stream = new BukkitObjectOutputStream(new FileOutputStream(file));

            writePlayer(stream, player);
        } catch (Exception e) {
            onSaveError(e, player, inventoryName); // Throws
        } finally {
            if (stream != null)
                stream.close();
        }
    }

    private void writePlayer(@NonNull BukkitObjectOutputStream stream, @NonNull Player player) throws Exception {
        writePlayerStats(stream, player);
        writePlayerInventory(stream, player);
    }

    private void writePlayerStats(@NonNull BukkitObjectOutputStream stream, @NonNull Player player) throws Exception {
        stream.writeDouble(player.getHealth());
        stream.writeFloat(player.getExp());
        stream.writeInt(player.getLevel());
    }

    private void writePlayerInventory(@NonNull BukkitObjectOutputStream stream, @NonNull Player player) throws Exception {
        val inventory     = player.getInventory();
        val inventorySize = inventory.getSize();

        for (int i = 0; i < inventorySize; ++i) {
            val item = inventory.getItem(i);
            stream.writeObject(item);
        }
    }

    private void onSaveError(
        @NonNull Exception exception, 
        @NonNull Player    player, 
        @NonNull String    inventoryName
    ) throws Exception {
        logFailedToSave(exception, player, inventoryName);
        throwFailedToSave();
    }

    private void logFailedToSave(@NonNull Exception exception, @NonNull Player player, @NonNull String inventoryName) {
        logFailed(exception, player, inventoryName, "save");
    }

    private void throwFailedToSave() throws Exception {
        val messageProvider = plugin.getMessageProvider();
        val message         = messageProvider.makeFailedToSaveInventory();

        throw new Exception(message);
    }

    @Override
    public void setInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception {
        BukkitObjectInputStream stream = null;

        try {
            val file = getPlayerInventoryFile(player, inventoryName); 

            if (!file.exists()) 
                throwMissingInventory(inventoryName);

            stream = new BukkitObjectInputStream(new FileInputStream(file));

            readPlayer(stream, player);
        } catch (IOException | SecurityException e) { // Bypass Missing Inventory Exception
            onSetError(e, player, inventoryName);
        } finally {
            if (stream != null)
                stream.close();
        }
    }

    private void readPlayer(@NonNull BukkitObjectInputStream stream, @NonNull Player player) throws Exception {
        readPlayerStats(stream, player);
        readPlayerInventory(stream, player);
    }

    private void readPlayerStats(@NonNull BukkitObjectInputStream stream, @NonNull Player player) throws Exception {
        player.setHealth(stream.readDouble());
        player.setExp(stream.readFloat());
        player.setLevel(stream.readInt());
    }

    private void readPlayerInventory(@NonNull BukkitObjectInputStream stream, @NonNull Player player) throws Exception {
        val inventory     = player.getInventory();
        val inventorySize = inventory.getSize();
        
        for (int i = 0; i < inventorySize; ++i) {
            val item = (ItemStack) stream.readObject();
            inventory.setItem(i, item);
        }
    }

    private void onSetError(
        @NonNull Exception exception,
        @NonNull Player    player,
        @NonNull String    inventoryName
    ) throws Exception {
        logFailedToSet(exception, player, inventoryName);
        throwFailedToSet();
    }

    private void logFailedToSet(@NonNull Exception exception, @NonNull Player player, @NonNull String inventoryName) {
        logFailed(exception, player, inventoryName, "set");
    }

    private void throwFailedToSet() throws Exception {
        val messageProvider = plugin.getMessageProvider();
        val message         = messageProvider.makeFailedToSetInventory();

        throw new Exception(message);
    }

    @Override
    public void removeInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception {
        try {
            val file = getPlayerInventoryFile(player, inventoryName);

            if (!file.exists())
                throwMissingInventory(inventoryName);

            file.delete();
        } catch (SecurityException e) {
            onRemoveError(e, player, inventoryName);
        }
    }

    private void throwMissingInventory(@NonNull String inventoryName) throws Exception {
        val messageProvider = plugin.getMessageProvider();
        val message         = messageProvider.makeMissingInventory(inventoryName);

        throw new Exception(message);
    }

    private void onRemoveError(
        @NonNull Exception exception, 
        @NonNull Player    player,
        @NonNull String    inventoryName
    ) throws Exception {
        logFailedToRemove(exception, player, inventoryName);
        throwFailedToRemove();
    }

    private void logFailedToRemove(@NonNull Exception exception, @NonNull Player player, @NonNull String inventoryName) {
        logFailed(exception, player, inventoryName, "remove");
    }

    private void throwFailedToRemove() throws Exception {
        val messageProvider = plugin.getMessageProvider();
        val message         = messageProvider.makeFailedToRemoveInventory();

        throw new Exception(message);
    }

    private void logFailed(
        @NonNull Exception exception,
        @NonNull Player    player,
        @NonNull String    inventoryName,
        @NonNull String    what
    ) {
        val logger        = plugin.getLogger();
        val loggerMessage = String.format(
            "Failed to %s %s's inventory as %s: %s.",
            what,
            player.getName(),
            inventoryName,
            exception.getMessage()
        );

        logger.info(loggerMessage);
    }

    public File getPlayerInventoryFile(@NonNull Player player, @NonNull String inventoryName) {
        return new File(getPlayerFile(player), inventoryName + EXTENSION);
    }

    public File getPlayerFile(@NonNull Player player) {
        val file = new File(databaseFile, player.getUniqueId().toString());
    
        file.mkdir();

        return file;
    }
}