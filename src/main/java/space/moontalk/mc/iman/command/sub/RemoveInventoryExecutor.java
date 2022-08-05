package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

import space.moontalk.mc.iman.*;

public class RemoveInventoryExecutor extends BaseSubcommandExecutor {
    public RemoveInventoryExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NonNull ArgsRange getArgsRange() {
        return new ArgsRange(1, 2);
    }

    @Override
    public void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Command       command,
        @NonNull String        label,
        @NonNull String[]      args
    ) throws Exception {
        val player = getPlayerTarget(sender, args, 1);
        val isSame = sender == player;

        throwIfMissingPermission(sender, command, isSame ? "iman.inv.remove.self" : "iman.inv.remove.other");

        val plugin             = getPlugin();
        val persistenceManager = plugin.getPersistenceManager();
        val inventoryName      = args[0]; 

        persistenceManager.removeInventory(player, inventoryName);

        val messageProvider = plugin.getMessageProvider();
        val message         = isSame
                            ? messageProvider.makeRemoveYourInventory(inventoryName)
                            : messageProvider.makeRemoveInventory(player.getName(), inventoryName);

        sender.sendMessage(message);
    }
}
