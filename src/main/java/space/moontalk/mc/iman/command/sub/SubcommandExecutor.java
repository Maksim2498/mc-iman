package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

import space.moontalk.ranges.IntegerRange;

public interface SubcommandExecutor {
    @NonNull IntegerRange getArgsRange();

    void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Command       command,
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception;
}
