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

    @NonNull 
    private final RemoveInventoryExecutor removeExecutor;

    public InventoryExecutor(@NonNull Iman plugin) {
        this.plugin = plugin;

        listExecutor   = new ListInventoriesExecutor(plugin);
        saveExecutor   = new SaveInventoryExecutor(plugin);
        setExecutor    = new SetInventoryExecutor(plugin);
        removeExecutor = new RemoveInventoryExecutor(plugin);
    }

    @Override
    public boolean onCommand(
        @NonNull CommandSender sender, 
        @NonNull Command       command, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) {
        try {
            if (args.length == 0) 
                throwMissingSubcommand();

            val subLabel       = args[0];
            val subcommandName = subLabel.toLowerCase();
            val subArgs        = Arrays.copyOfRange(args, 1, args.length);
            val executor       = switch (subcommandName) {
                case "list", "ls"   -> listExecutor;
                case "save"         -> saveExecutor ;
                case "set"          -> setExecutor;
                case "remove", "rm" -> removeExecutor;
                default             -> {
                    throwInvalidSubcommand(subcommandName);
                    yield null;
                }
            };

            val argsRange = executor.getArgsRange();
            val passed    = subArgs.length;
             
            if (!argsRange.contains(passed))
                throwInvaildArgsNum(argsRange, passed);

            executor.onSubcommand(sender, command, subLabel, subArgs);
        } catch (ComponentException e) {
            sender.sendMessage(e.getComponent());   
        } catch (Exception e) {
            sender.sendMessage(e.getMessage()); 
        }
        return true;
    }

    private void throwMissingSubcommand() throws Exception {
        val message = getPlugin().getMessageProvider().makeMissingSubcommand();
        throw new Exception(message);
    }

    private void throwInvalidSubcommand(@NonNull String subcommand) throws Exception {
        val message = getPlugin().getMessageProvider().makeInvalidSubcommand(subcommand);
        throw new Exception(message);
    }

    private void throwInvaildArgsNum(@NonNull ArgsRange argsRange, int passed) throws Exception {
        val message = getPlugin().getMessageProvider().makeInvalidArgsNum(argsRange, passed);
        throw new Exception(message);
    }
}
