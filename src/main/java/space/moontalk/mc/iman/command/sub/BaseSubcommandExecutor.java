package space.moontalk.mc.iman.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;

@AllArgsConstructor
public abstract class BaseSubcommandExecutor implements SubcommandExecutor, PluginHolder {
    @Getter
    @NotNull
    private final Iman plugin;

    protected void throwIfMissingPermission(
        @NotNull CommandSender sender, 
        @NotNull Command       command,
        @NotNull String        permission
    ) throws Exception {
        if (sender.hasPermission(permission))
            return;

        val component = command.permissionMessage();

        if (component == null)
            throw new Exception();

        throw new ComponentException(component);
    }

    protected @NotNull Player getPlayerTarget(
        @NotNull CommandSender sender,
        @NotNull String[]      args,
        int                    argNum
    ) throws Exception {
        val target = getTarget(sender, args, argNum);

        if (target instanceof Player player)
            return player;

        val message = getPlugin().getMessageProvider().makeNotAPlayer();

        throw new Exception(message);
    }

    protected @NotNull CommandSender getTarget(
        @NotNull CommandSender sender,
        @NotNull String[]      args, 
        int                    argNum
    ) throws Exception {
        if (argNum >= args.length)
            return sender;

        val server     = Bukkit.getServer();
        val playerName = args[argNum];
        val player     = server.getPlayer(playerName);

        if (player == null) {
            val message = getPlugin().getMessageProvider().makePlayerNotFound(playerName);
            throw new Exception(message);
        }

        return player;
    }
}
