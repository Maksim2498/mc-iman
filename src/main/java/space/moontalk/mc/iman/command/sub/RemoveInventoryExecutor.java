package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import lombok.val;

import space.moontalk.ranges.IntegerRange;
import space.moontalk.mc.iman.*;

public class RemoveInventoryExecutor extends BaseSubcommandExecutor {
    public RemoveInventoryExecutor(@NotNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NotNull IntegerRange getArgsRange() {
        return new IntegerRange(1, 2);
    }

    @Override
    public void onSubcommand(
        @NotNull CommandSender sender, 
        @NotNull Command       command,
        @NotNull String        label,
        @NotNull String[]      args
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
