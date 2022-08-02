package space.moontalk.mc.iman.subcommand;

import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

import space.moontalk.mc.iman.*;

public class ListInventoriesExecutor extends PluginHolder implements SubcommandExecutor {
    public ListInventoriesExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public @NonNull ArgumentsRange getArgsRange() {
        return new ArgumentsRange(0, 1); 
    }

    @Override
    public void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Subcommand    command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception {
        val player = SubcommandExecutor.getPlayerTarget(sender, args, 0);
        val plugin = getPlugin();
        val list   = plugin.getInvenotriesNamesUnsafe(player);        

        if (list.isEmpty()) {
            val message = sender == player 
                        ? "You have no inventories."
                        : String.format("%s has no inventories.", player.getName());
            
            sender.sendMessage(message);

            return;
        }

        val builder = new StringBuilder(
            sender == player ? "Your inventories:"
                             : String.format("%s's intenotries:", player.getName())
        );

        
        for (val name : list) {
            val item = String.format("* %s;", name);
            builder.append(item);
        }

        sender.sendMessage(builder.toString());
    }
}
