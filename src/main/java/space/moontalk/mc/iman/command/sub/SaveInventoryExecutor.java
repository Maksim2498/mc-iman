package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

import space.moontalk.mc.iman.*;

public class SaveInventoryExecutor extends BaseSubcommandExecutor {
    public SaveInventoryExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NonNull ArgsRange getArgsRange() {
        return new ArgsRange(1, 2);
    }

    @Override
    public void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception {
        val plugin        = getPlugin();
        val player        = getPlayerTarget(sender, args, 1);
        val inventoryName = args[0];

        plugin.saveInventoryUnsafe(player, inventoryName);

        val messageProvider = plugin.getMessageProvider();
        val message         = sender == player 
                            ? messageProvider.makeSaveYourInventory(inventoryName)
                            : messageProvider.makeSaveInventory(player.getName(), inventoryName);
        
        sender.sendMessage(message);
    }
}
