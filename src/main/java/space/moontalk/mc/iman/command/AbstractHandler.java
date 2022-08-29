package space.moontalk.mc.iman.command;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.route.RouteCall;
import space.moontalk.mc.commands.route.RouteHandler;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.message.MessageProviderHolder;
import space.moontalk.mc.iman.persistence.PersistenceManager;
import space.moontalk.mc.iman.persistence.PersistenceManagerHolder;

@Getter
@AllArgsConstructor
public abstract class AbstractHandler implements RouteHandler,
                                                 MessageProviderHolder,
                                                 PersistenceManagerHolder {
    protected final @NotNull MessageProvider    messageProvider;
    protected final @NotNull PersistenceManager persistenceManager;

    protected @NotNull Player getPlayer(@NotNull RouteCall call) throws Exception {
        return getPlayer(call, 0);
    }

    protected @NotNull Player getPlayer(@NotNull RouteCall call, int phIndex) throws Exception {
        Player phPlayer = call.getPlaceholdedAtOrNull(phIndex);

        if (phPlayer == null) {
            val sender = call.getCommandSender();

            if (!(sender instanceof Player sPlayer))
                throw new Exception();

            return sPlayer;
        }

        return phPlayer;
    }
}
