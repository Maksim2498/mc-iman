package space.moontalk.mc.iman.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.MissingPermissionException;
import space.moontalk.mc.commands.SubcommandCall;
import space.moontalk.mc.commands.SubcommandExecutorMeta;
import space.moontalk.mc.commands.SubcommandExecutorWithMeta;

import space.moontalk.mc.iman.message.*;
import space.moontalk.mc.iman.persistence.*;

import space.moontalk.ranges.IntegerRange;

public abstract class AbstractSubcommandExecutor implements SubcommandExecutorWithMeta,
                                                            MessageProviderHolder,
                                                            PersistenceManagerHolder {
    @Getter
    private final @NotNull MessageProvider messageProvider;

    @Getter
    private final @NotNull PersistenceManager persistenceManager;

    private final @NotNull SubcommandExecutorMeta meta;

    protected AbstractSubcommandExecutor(
        @NotNull MessageProvider    messageProvider,
        @NotNull PersistenceManager persistenceManager,
        @NotNull IntegerRange       argsRange
    ) {
        this.messageProvider    = messageProvider;
        this.persistenceManager = persistenceManager;

        meta = SubcommandExecutorMeta.builder()
                                     .argsRange(argsRange)
                                     .build();
    }

    @Override
    public @NotNull SubcommandExecutorMeta getSubcommandExecutorMeta() {
        return meta;
    }

    protected void throwIfMissingPermission(
        @NotNull CommandSender sender, 
        @NotNull String        permission
    ) throws MissingPermissionException {
        if (!sender.hasPermission(permission))
            throw new MissingPermissionException(permission);
    }

    protected @NotNull Player getPlayerTarget(@NotNull SubcommandCall call, int argNum) throws Exception {
        val target = getTarget(call, argNum);

        if (target instanceof Player player)
            return player;

        val message = messageProvider.makeNotAPlayerMessage();

        throw new Exception(message);
    }

    protected @NotNull CommandSender getTarget(@NotNull SubcommandCall call, int argNum) throws Exception {
        val args = call.getSubargs();

        if (argNum >= args.length)
            return call.getSender();

        val server     = Bukkit.getServer();
        val playerName = args[argNum];
        val player     = server.getPlayer(playerName);

        if (player == null) {
            val message = messageProvider.makePlayerNotFoundMessage(playerName);
            throw new Exception(message);
        }

        return player;
    }
}
