package space.moontalk.mc.iman.command.sub;

import org.jetbrains.annotations.NotNull;

import lombok.val;

import space.moontalk.ranges.IntegerRange;

import space.moontalk.mc.commands.SubcommandCall;

import space.moontalk.mc.iman.message.MessageProvider;
import space.moontalk.mc.iman.persistence.PersistenceManager;

public class ListInventoriesExecutor extends AbstractSubcommandExecutor {
    public ListInventoriesExecutor(
        @NotNull MessageProvider    messageProvider,
        @NotNull PersistenceManager persistenceManager
    ) {
        super(messageProvider, persistenceManager, new IntegerRange(0, 1)); 
    }

    @Override
    public void onSubcommand(@NotNull SubcommandCall call) throws Exception {
        val player = getPlayerTarget(call, 0);
        val sender = call.getSender();
        val isSame = sender == player;

        throwIfMissingPermission(sender, isSame ? "iman.inv.list.self" : "iman.inv.list.other");

        val messageProvider    = getMessageProvider();
        val persistenceManager = getPersistenceManager();
        val list               = persistenceManager.getInvenotriesNames(player);

        if (list.isEmpty()) {
            val message = isSame
                        ? messageProvider.makeMissingYourInvenotriesMessage()
                        : messageProvider.makeMissingInventoriesMessage(player.getName());
            
            sender.sendMessage(message);

            return;
        }

        val builder = new StringBuilder(
            isSame ? messageProvider.makeYourInventoriesMessage()
                   : messageProvider.makeInventoriesMessage(player.getName())
        );

        for (val name : list) {
            val item = messageProvider.makeInventoryMessage(name);
            builder.append(item);
        }

        sender.sendMessage(builder.toString());
    }
}
