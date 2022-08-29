package space.moontalk.mc.iman.command;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import lombok.val;

import space.moontalk.mc.commands.route.RouteCall;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.persistence.PersistenceManager;

// (list | ls) %p?

public class ListHandler extends AbstractHandler {
    public ListHandler(
        @NotNull MessageProvider    messageProvider,
        @NotNull PersistenceManager persistenceManager
    ) {
        super(messageProvider, persistenceManager);
    }

    @Override
    public void onRoute(@NotNull RouteCall call) throws Exception {
        val player = getPlayer(call);
        val sender = call.getCommandSender();
        val isSame = sender == player;

        if (!sender.hasPermission(isSame ? "iman.inv.list.self" : "iman.inv.list.other"))
            throw new Exception();

        val message = renderMessage(player, isSame);
        sender.sendMessage(message);
    }

    private @NotNull String renderMessage(@NotNull Player player, boolean isSame) throws Exception {
        val list = persistenceManager.getInvenotriesNames(player);

        if (list.isEmpty()) {
            val message = isSame
                        ? messageProvider.makeMissingYourInvenotriesMessage()
                        : messageProvider.makeMissingInventoriesMessage(player.getName());
            
            return message;
        }

        val builder = new StringBuilder(
            isSame ? messageProvider.makeYourInventoriesMessage()
                   : messageProvider.makeInventoriesMessage(player.getName())
        );

        for (val name : list) {
            val item = messageProvider.makeInventoryMessage(name);
            builder.append(item);
        }

        return builder.toString();
    }

    @Override
    public @NotNull String getPermission() {
        return "iman.inv.list";
    }
}
