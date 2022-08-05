package space.moontalk.mc.iman.persistence;

import java.util.List;

import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface PersistenceManager {
    @NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception;
    void saveInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception;
    void setInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception;
    void removeInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception;
}
