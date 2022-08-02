package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

import space.moontalk.mc.iman.*;

public class SetInventoryExecutor extends BaseSubcommandExecutor {
    public SetInventoryExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NonNull ArgsRange getArgsRange() {
        return new ArgsRange(1, 2);
    }

    @Override
    public void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Subcommand    command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception {
        val plugin        = getPlugin();
        val player        = getPlayerTarget(sender, args, 1);
        val inventoryName = args[0]; 

        plugin.setInventoryUnsafe(player, inventoryName);

        val messageProvider = plugin.getMessageProvider();
        val message         = sender == player
                            ? messageProvider.makeSetYourInventory(inventoryName)
                            : messageProvider.makeSetInventory(player.getName(), inventoryName);

        sender.sendMessage(message);
    }
}
