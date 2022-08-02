package space.moontalk.mc.iman;

import java.util.List;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.command.*;
import space.moontalk.mc.iman.message.*;

public final class Iman extends JavaPlugin {
    @Getter
    private final MessageProvider messageProvider = new DefaultMessageProvider(this);

    @Override
    public void onEnable() {
        setupConfig();
        setupCommand();
    }

    private void setupConfig() {
        saveDefaultConfig();
    }

    private void setupCommand() {
        val command   = Objects.requireNonNull(getCommand("inventory"));
        val executor  = new InventoryExecutor(this);
        val completer = new InventoryTabCompleter();

        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }

    public @NonNull List<@NonNull String> getInvenotriesNames(@lombok.NonNull Player player) throws Exception {
        return getInvenotriesNamesUnsafe(player);
    }

    public @NonNull List<@NonNull String> getInvenotriesNamesUnsafe(@NonNull Player player) throws Exception {
        throw new Exception("§cNot implemented.");
    }

    public void saveInventory(@lombok.NonNull Player player, @lombok.NonNull String name) throws Exception {
        saveInventoryUnsafe(player, name);
    }

    public void saveInventoryUnsafe(@NonNull Player player, @NonNull String name) throws Exception {
        throw new Exception("§cNot implemented.");
    }

    public void setInventory(@lombok.NonNull Player player, @lombok.NonNull String name) throws Exception {
        setInventoryUnsafe(player, name);
    }

    public void setInventoryUnsafe(@NonNull Player player, @NonNull String name) throws Exception {
        throw new Exception("§cNot implemented.");
    }
}
