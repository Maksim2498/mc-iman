package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import lombok.val;

import space.moontalk.ranges.IntegerRange;
import space.moontalk.mc.iman.*;

public class ListInventoriesExecutor extends BaseSubcommandExecutor {
    public ListInventoriesExecutor(@NotNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NotNull IntegerRange getArgsRange() {
        return new IntegerRange(0, 1); 
    }

    @Override
    public void onSubcommand(
        @NotNull CommandSender sender, 
        @NotNull Command       command,
        @NotNull String        label, 
        @NotNull String[]      args
    ) throws Exception {
        val player = getPlayerTarget(sender, args, 0);
        val isSame = sender == player;

        throwIfMissingPermission(sender, command, isSame ? "iman.inv.list.self" : "iman.inv.list.other");

        val plugin             = getPlugin();
        val messageProvider    = plugin.getMessageProvider();
        val persistenceManager = plugin.getPersistenceManager();
        val list               = persistenceManager.getInvenotriesNames(player);

        if (list.isEmpty()) {
            val message = isSame
                        ? messageProvider.makeMissingYourInvenotries()
                        : messageProvider.makeMissingInventories(player.getName());
            
            sender.sendMessage(message);

            return;
        }

        val builder = new StringBuilder(
            isSame ? messageProvider.makeYourInventories()
                   : messageProvider.makeInventories(player.getName())
        );

        
        for (val name : list) {
            val item = messageProvider.makeInventory(name);
            builder.append(item);
        }

        sender.sendMessage(builder.toString());
    }
}
