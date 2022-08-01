package space.moontalk.mc.iman.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.val;

public interface SubcommandExecutor {
    @NonNull ArgumentsRange getArgsRange();

    void onSubcommand(
        @NonNull CommandSender sender, 
        @NonNull Subcommand    subcommand, 
        @NonNull String        label, 
        @NonNull String[]      args
    ) throws Exception;

    static @NonNull Player getPlayerTarget(
        @NonNull CommandSender sender,
        @NonNull String[]      args,
        int                    argNum
    ) throws Exception {
        val target = getTarget(sender, args, argNum);

        if (target instanceof Player player)
            return player;

        throw new Exception("§cThis command can only be run by player.");
    }

    static @NonNull CommandSender getTarget(
        @NonNull CommandSender sender,
        @NonNull String[]      args, 
        int                    argNum
    ) throws Exception {
        if (argNum >= args.length)
            return sender;

        val server     = Bukkit.getServer();
        val playerName = args[argNum];
        val player     = server.getPlayer(playerName);

        if (player == null) {
            val message = String.format("§cPlayer %s not found.", playerName);
            throw new Exception(message);
        }

        return player;
    }
}
