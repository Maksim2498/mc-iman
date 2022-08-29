package space.moontalk.mc.iman.command;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.route.RouteCall;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.persistence.PersistenceManager;

// save * %p?

@Getter
public class SaveHandler extends AbstractHandler {
    public SaveHandler(
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

        if (!sender.hasPermission(isSame ? "iman.inv.save.self" : "iman.inv.save.other"))
            throw new Exception();
        
        val inventoryName = call.getArgs()[1];
        persistenceManager.saveInventory(player, inventoryName);

        val playerName = player.getName();
        val message    = renderMessage(inventoryName, playerName, isSame);
        sender.sendMessage(message);
    }

    private @NotNull String renderMessage(
        @NotNull String inventoryName,
        @NotNull String playerName,
        boolean         isSame
    ) {
        return isSame ? messageProvider.makeSaveYourInventoryMessage(inventoryName)
                      : messageProvider.makeSaveInventoryMessage(playerName, inventoryName);
    }

    @Override
    public @NotNull String getPermission() {
        return "iman.inv.save";
    }
}
