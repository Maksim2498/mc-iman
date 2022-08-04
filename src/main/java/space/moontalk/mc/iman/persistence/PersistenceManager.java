package space.moontalk.mc.iman.persistence;

import java.util.List;

import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface PersistenceManager {
    public @NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception;
    public void saveInventory(@NonNull Player player, @NonNull String name) throws Exception;
    public void setInventory(@NonNull Player player, @NonNull String name) throws Exception;
}
