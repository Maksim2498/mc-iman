package space.moontalk.mc.iman.command;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;
import space.moontalk.mc.iman.command.sub.*;
import space.moontalk.ranges.IntegerRange;

@Getter
public class InventoryExecutor implements CommandExecutor, PluginHolder {
    @NotNull 
    private final Iman plugin;

    @NotNull
    private final ListInventoriesExecutor listExecutor;

    @NotNull
    private final SaveInventoryExecutor saveExecutor;

    @NotNull
    private final SetInventoryExecutor setExecutor;

    @NotNull 
    private final RemoveInventoryExecutor removeExecutor;

    public InventoryExecutor(@NotNull Iman plugin) {
        this.plugin = plugin;

        listExecutor   = new ListInventoriesExecutor(plugin);
        saveExecutor   = new SaveInventoryExecutor(plugin);
        setExecutor    = new SetInventoryExecutor(plugin);
        removeExecutor = new RemoveInventoryExecutor(plugin);
    }

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender, 
        @NotNull Command       command, 
        @NotNull String        label, 
        @NotNull String[]      args
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

    private void throwInvalidSubcommand(@NotNull String subcommand) throws Exception {
        val message = getPlugin().getMessageProvider().makeInvalidSubcommand(subcommand);
        throw new Exception(message);
    }

    private void throwInvaildArgsNum(@NotNull IntegerRange argsRange, int passed) throws Exception {
        val message = getPlugin().getMessageProvider().makeInvalidArgsNum(argsRange, passed);
        throw new Exception(message);
    }
}
