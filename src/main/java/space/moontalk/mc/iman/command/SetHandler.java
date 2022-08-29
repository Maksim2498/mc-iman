package space.moontalk.mc.iman.command;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.route.RouteCall;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.persistence.PersistenceManager;

// set * %p?

@Getter
public class SetHandler extends AbstractHandler {
    public SetHandler(
        @NotNull MessageProvider    messageProvider,
        @NotNull PersistenceManager persistenceManager
    ) {
        super(messageProvider, persistenceManager);
    }

    @Override
    public void onRoute(@NotNull RouteCall call) throws Exception {
        val player = getPlayer(call, 1);
        val sender = call.getCommandSender();
        val isSame = sender == player;

        if (!sender.hasPermission(isSame ? "iman.inv.set.self" : "iman.inv.set.other"))
            throw new Exception();
        
        val inventoryName = call.getArgs()[1]; 
        persistenceManager.setInventory(player, inventoryName);

        val playerName = player.getName();
        val message    = renderMessage(inventoryName, playerName, isSame);
        sender.sendMessage(message);
    }

    private @NotNull String renderMessage(
        @NotNull String inventoryName,
        @NotNull String playerName,
        boolean         isSame
    ) {
        return isSame ? messageProvider.makeSetYourInventoryMessage(inventoryName)
                      : messageProvider.makeSetInventoryMessage(playerName, inventoryName);
    }

    @Override
    public @NotNull String getPermission() {
        return "iman.inv.set";
    }
}
