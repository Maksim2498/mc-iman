package space.moontalk.mc.iman;

import java.util.List;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.NonNull;

import lombok.val;

public final class Iman extends JavaPlugin {
    @Override
    public void onEnable() {
        val command   = Objects.requireNonNull(getCommand("inventory"));
        val executor  = new InventoryExecutor(this);
        val completer = new InventoryTabCompleter();

        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }

    public @NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception {
        throw new Exception("§cNot implemented.");
    }

    public void saveInventory(@NonNull Player player, @NonNull String name) throws Exception {
        throw new Exception("§cNot implemented.");
    }

    public void setInventory(@NonNull Player player, @NonNull String name) throws Exception {
        throw new Exception("§cNot implemented.");
    }
}
