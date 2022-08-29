package space.moontalk.mc.iman;

import java.util.Objects;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.CommandHandler;

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

    private void setupCommand() throws Exception {
        val handler = new CommandHandler();
        val router  = handler.getRouter();
        
        val routeParser          = router.getRouteParser();
        val placeholderManager   = routeParser.getPlaceholderManager();
        val inventoryPlaceholder = new InventoryPlaceholder(persistenceManager);
        placeholderManager.addPlaceholder(inventoryPlaceholder);

        val listHandler = new ListHandler(messageProvider, persistenceManager);
        router.addRoute("(list | ls) %p?", listHandler);

        val setHandler = new SetHandler(messageProvider, persistenceManager);
        router.addRoute("set %i %p?", setHandler);

        val saveHandler = new SaveHandler(messageProvider, persistenceManager);
        router.addRoute("save * %p?", saveHandler);

        val removeHandler = new RemoveHandler(messageProvider, persistenceManager);
        router.addRoute("(remove | rm) %i %p?", removeHandler);

        val command = Objects.requireNonNull(getCommand("inventory"));
        handler.attach(command);
    }
}
