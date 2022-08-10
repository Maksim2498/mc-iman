package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import space.moontalk.ranges.IntegerRange;

public interface SubcommandExecutor {
    @NotNull IntegerRange getArgsRange();

    void onSubcommand(
        @NotNull CommandSender sender, 
        @NotNull Command       command,
        @NotNull String        label, 
        @NotNull String[]      args
    ) throws Exception;
}
