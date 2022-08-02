package space.moontalk.mc.iman.subcommand;

import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

import space.moontalk.mc.iman.*;

public class SetInventoryExecutor extends PluginHolder implements SubcommandExecutor {
    public SetInventoryExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NonNull ArgumentsRange getArgsRange() {
        return new ArgumentsRange(1, 2);
    }

    @Override
    public void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Subcommand    command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception {
        val player        = SubcommandExecutor.getPlayerTarget(sender, args, 1);
        val inventoryName = args[0]; 
        val plugin        = getPlugin();

        plugin.setInventoryUnsafe(player, inventoryName);

        val message = sender == player 
                    ? String.format("Your current inventory has been set to %s.", inventoryName)
                    : String.format("%s's current inventory has been set to %s.", player.getName(), inventoryName);

        sender.sendMessage(message);
    }
}
