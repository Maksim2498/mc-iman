package space.moontalk.mc.iman.command;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;
import space.moontalk.mc.iman.command.sub.*;

@Getter
public class InventoryExecutor implements CommandExecutor, PluginHolder {
    @NonNull 
    private final Iman plugin;

    @NonNull
    private final ListInventoriesExecutor listExecutor;

    @NonNull
    private final SaveInventoryExecutor saveExecutor;

    @NonNull
    private final SetInventoryExecutor setExecutor;

    public InventoryExecutor(@NonNull Iman plugin) {
        this.plugin  = plugin;
        listExecutor = new ListInventoriesExecutor(plugin);
        saveExecutor = new SaveInventoryExecutor(plugin);
        setExecutor  = new SetInventoryExecutor(plugin);
    }

    @Override
    public boolean onCommand(
        @NonNull CommandSender sender, 
        @NonNull Command       command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) {
        try {
            if (args.length == 0) {
                val message = getPlugin().getMessageProvider().makeMissingSubcommand();
                throw new Exception(message);
            }

            val subLabel       = args[0];
            val subcommandName = subLabel.toLowerCase();
            val subArgs        = Arrays.copyOfRange(args, 1, args.length);
            val executor       = switch (subcommandName) {
                case "list" -> listExecutor;
                case "save" -> saveExecutor ;
                case "set"  -> setExecutor;
                default     -> {
                    val message = getPlugin().getMessageProvider().makeInvalidSubcommand(subcommandName);
                    throw new Exception(message);
                }
            };

            val argsRange = executor.getArgsRange();
             
            if (!argsRange.contains(subArgs.length)) {
                val message = getPlugin().getMessageProvider().makeInvalidArgsNum(argsRange, subArgs.length);
                throw new Exception(message);
            }

            executor.onSubcommand(sender, command, subLabel, subArgs);
        } catch (ComponentException e) {
            sender.sendMessage(e.getComponent());   
        } catch (Exception e) {
            sender.sendMessage(e.getMessage()); 
        }
        return true;
    }
}
