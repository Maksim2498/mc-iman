package space.moontalk.mc.iman.persistence;

import java.util.List;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public interface PersistenceManager {
    @NotNull List<@NotNull String> getInvenotriesNames(@NotNull Player player) throws Exception;
    void saveInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception;
    void setInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception;
    void removeInventory(@NotNull Player player, @NotNull String inventoryName) throws Exception;
}
