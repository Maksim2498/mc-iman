package space.moontalk.mc.iman.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.message.MessageProvider;

@Getter
public class FilePersistenceManager implements PersistenceManager {
    public static final @NotNull String EXTENSION = ".inv";

    private final @NotNull MessageProvider messageProvider;
    private final @NotNull File            databaseFile;

    public FilePersistenceManager(
        @NotNull Configuration   config, 
        @NotNull File            dataFolder, 
        @NotNull MessageProvider messageProvider
    ) throws Exception {
        val dirName = config.getString("persistence.dir-name");

        if (dirName == null)
            throw new InvalidConfigurationException("Invalid persistence.dir-name value.");

        databaseFile = new File(dataFolder, dirName);
        databaseFile.mkdir();

        this.messageProvider = messageProvider;
    }

    @Override
    public @NotNull List<@NotNull String> getInvenotriesNames(@NotNull Player player) throws Exception {
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
    public void saveInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception {
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

    private void writePlayer(@NotNull BukkitObjectOutputStream stream, @NotNull Player player) throws Exception {
        writePlayerStats(stream, player);
        writePlayerInventory(stream, player);
    }

    private void writePlayerStats(@NotNull BukkitObjectOutputStream stream, @NotNull Player player) throws Exception {
        stream.writeDouble(player.getHealth());
        stream.writeInt(player.getFoodLevel());
        stream.writeFloat(player.getExp());
        stream.writeInt(player.getLevel());
    }

    private void writePlayerInventory(@NotNull BukkitObjectOutputStream stream, @NotNull Player player) throws Exception {
        val inventory     = player.getInventory();
        val inventorySize = inventory.getSize();

        for (int i = 0; i < inventorySize; ++i) {
            val item = inventory.getItem(i);
            stream.writeObject(item);
        }
    }

    private void onSaveError(
        @NotNull Exception exception, 
        @NotNull Player    player, 
        @NotNull String    inventoryName
    ) throws Exception {
        logFailedToSave(exception, player, inventoryName);
        throwFailedToSave();
    }

    private void logFailedToSave(@NotNull Exception exception, @NotNull Player player, @NotNull String inventoryName) {
        logFailed(exception, player, inventoryName, "save");
    }

    private void throwFailedToSave() throws Exception {
        val message = messageProvider.makeFailedToSaveInventoryMessage();
        throw new Exception(message);
    }

    @Override
    public void setInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception {
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

    private void readPlayer(@NotNull BukkitObjectInputStream stream, @NotNull Player player) throws Exception {
        readPlayerStats(stream, player);
        readPlayerInventory(stream, player);
    }

    private void readPlayerStats(@NotNull BukkitObjectInputStream stream, @NotNull Player player) throws Exception {
        player.setHealth(stream.readDouble());
        player.setFoodLevel(stream.readInt());
        player.setExp(stream.readFloat());
        player.setLevel(stream.readInt());
    }

    private void readPlayerInventory(@NotNull BukkitObjectInputStream stream, @NotNull Player player) throws Exception {
        val inventory     = player.getInventory();
        val inventorySize = inventory.getSize();
        
        for (int i = 0; i < inventorySize; ++i) {
            val item = (ItemStack) stream.readObject();
            inventory.setItem(i, item);
        }
    }

    private void onSetError(
        @NotNull Exception exception,
        @NotNull Player    player,
        @NotNull String    inventoryName
    ) throws Exception {
        logFailedToSet(exception, player, inventoryName);
        throwFailedToSet();
    }

    private void logFailedToSet(@NotNull Exception exception, @NotNull Player player, @NotNull String inventoryName) {
        logFailed(exception, player, inventoryName, "set");
    }

    private void throwFailedToSet() throws Exception {
        val message = messageProvider.makeFailedToSetInventoryMessage();
        throw new Exception(message);
    }

    @Override
    public void removeInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception {
        try {
            val file = getPlayerInventoryFile(player, inventoryName);

            if (!file.exists())
                throwMissingInventory(inventoryName);

            file.delete();
        } catch (SecurityException e) {
            onRemoveError(e, player, inventoryName);
        }
    }

    private void throwMissingInventory(@NotNull String inventoryName) throws Exception {
        val message = messageProvider.makeMissingInventoryMessage(inventoryName);
        throw new Exception(message);
    }

    private void onRemoveError(
        @NotNull Exception exception, 
        @NotNull Player    player,
        @NotNull String    inventoryName
    ) throws Exception {
        logFailedToRemove(exception, player, inventoryName);
        throwFailedToRemove();
    }

    private void logFailedToRemove(@NotNull Exception exception, @NotNull Player player, @NotNull String inventoryName) {
        logFailed(exception, player, inventoryName, "remove");
    }

    private void throwFailedToRemove() throws Exception {
        val message = messageProvider.makeFailedToRemoveInventoryMessage();
        throw new Exception(message);
    }

    private void logFailed(
        @NotNull Exception exception,
        @NotNull Player    player,
        @NotNull String    inventoryName,
        @NotNull String    what
    ) {
        val logger  = Bukkit.getLogger();
        val message = String.format(
            "Failed to %s %s's inventory as %s: %s.",
            what,
            player.getName(),
            inventoryName,
            exception.getMessage()
        );

        logger.info(message);
    }

    public File getPlayerInventoryFile(@NotNull Player player, @NotNull String inventoryName) {
        return new File(getPlayerFile(player), inventoryName + EXTENSION);
    }

    public File getPlayerFile(@NotNull Player player) {
        val file = new File(databaseFile, player.getUniqueId().toString());
    
        file.mkdir();

        return file;
    }
}
