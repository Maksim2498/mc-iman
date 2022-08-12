package space.moontalk.mc.iman.command.sub;

import org.jetbrains.annotations.NotNull;

import lombok.val;

import space.moontalk.ranges.IntegerRange;

import space.moontalk.mc.commands.SubcommandCall;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.persistence.PersistenceManager;

public class RemoveInventoryExecutor extends AbstractSubcommandExecutor {
    public RemoveInventoryExecutor(
        @NotNull MessageProvider    messageProvider,
        @NotNull PersistenceManager persistenceManager
    ) {
        super(messageProvider, persistenceManager, new IntegerRange(1, 2)); 
    }

    @Override
    public void onSubcommand(@NotNull SubcommandCall call) throws Exception {
        val player = getPlayerTarget(call, 1);
        val sender = call.getSender();
        val isSame = sender == player;

        throwIfMissingPermission(sender, isSame ? "iman.inv.remove.self" : "iman.inv.remove.other");

        val persistenceManager = getPersistenceManager();
        val inventoryName      = call.getSubargs()[0]; 

        persistenceManager.removeInventory(player, inventoryName);

        val messageProvider = getMessageProvider();
        val message         = isSame
                            ? messageProvider.makeRemoveYourInventoryMessage(inventoryName)
                            : messageProvider.makeRemoveInventoryMessage(player.getName(), inventoryName);

        sender.sendMessage(message);
    }
}
