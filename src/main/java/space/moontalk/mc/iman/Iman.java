package space.moontalk.mc.iman;

import java.util.Objects;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.completion.InvalidPatternException;

import space.moontalk.mc.iman.command.*;
import space.moontalk.mc.iman.message.*;
import space.moontalk.mc.iman.persistence.*;

@Getter
public final class Iman extends    JavaPlugin
                        implements PersistenceManagerHolder,
                                   MessageProviderHolder {
    private final @NotNull MessageProvider messageProvider = new DefaultMessageProvider(getConfig());

    private @Nullable PersistenceManager persistenceManager;

    @Override
    public void onEnable() {
        try {
            setupConfig();
            setupPersistenceManager();
            setupCommand();
        } catch (Exception exception) {
            getLogger().info(exception.getMessage());
            getServer().getPluginManager().disablePlugin(this); 
        }
    }

    private void setupConfig() {
        saveDefaultConfig();
    }

    private void setupPersistenceManager() throws Exception {
        val config = getConfig();
        val method = config.getString("persistence.method");

        persistenceManager = switch (method) {
            case "file" -> new FilePersistenceManager(config, getDataFolder(), messageProvider);
            default     -> {
                val message = String.format("Invalid persistence.method value (%s).", method);
                throw new InvalidConfigurationException(message);
            }
        };
    }

    private void setupCommand() throws InvalidPatternException {
        val command   = Objects.requireNonNull(getCommand("inventory"));
        val executor  = new InventoryExecutor(messageProvider, persistenceManager);
        val completer = new InventoryTabCompleter();
        
        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }
}
