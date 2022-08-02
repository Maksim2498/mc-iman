package space.moontalk.mc.iman.command.sub;

import org.bukkit.command.CommandSender;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface SubcommandExecutor {
    @NonNull ArgsRange getArgsRange();

    void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Subcommand    subcommand, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception;
}
