package space.moontalk.mc.iman;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.subcommand.*;

@Getter
public class InventoryExecutor extends PluginHolder implements CommandExecutor {
    @NonNull
    private final ListInventoriesExecutor listExecutor = new ListInventoriesExecutor(getPlugin());

    @NonNull
    private final SaveInventoryExecutor saveExecutor = new SaveInventoryExecutor(getPlugin());

    @NonNull
    private final SetInventoryExecutor setExecutor = new SetInventoryExecutor(getPlugin());

    public InventoryExecutor(@NonNull Iman plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(
        @NonNull CommandSender sender, 
        @NonNull Command       command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) {
        try {
            if (!sender.isOp())
                throw new Exception("§cYou have no permission to run this command.");

            if (args.length == 0)
                throw new Exception("§cMissing subcommand.");

            val subLabel       = args[0];
            val subcommandName = subLabel.toLowerCase();
            val subcommand     = new Subcommand(subcommandName);
            val subArgs        = Arrays.copyOfRange(args, 1, args.length);
            val executor       = switch (subcommandName) {
                case "list" -> listExecutor;
                case "save" -> saveExecutor ;
                case "set"  -> setExecutor;
                default     -> {
                    val message = String.format("§cInvalid subcommand (%s).", subcommandName);
                    throw new Exception(message);
                }
            };

            val argsRange = executor.getArgsRange();
             
            if (!argsRange.contains(subArgs.length)) {
                val message = String.format(
                    "§cFrom %d to %d subcommand arguments were expected but %d were passed",
                    argsRange.getLow(),
                    argsRange.getHigh(),
                    subArgs.length
                );

                throw new Exception(message);
            }

            executor.onSubcommand(sender, subcommand, subLabel, subArgs);
        } catch (Exception e) {
            sender.sendMessage(e.getMessage()); 
        }

        return true;
    }
}
