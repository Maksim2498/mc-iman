package space.moontalk.mc.iman.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.CommandCall;
import space.moontalk.mc.commands.placeholder.Placeholder;

import space.moontalk.mc.iman.persistence.PersistenceManager;
import space.moontalk.mc.iman.persistence.PersistenceManagerHolder;

@Getter
@AllArgsConstructor
public class InventoryPlaceholder implements Placeholder<String>,
                                             PersistenceManagerHolder {
    private final @NotNull PersistenceManager persistenceManager;

    @Override
    public @NotNull List<String> evalVariants(@NotNull CommandCall call) {
        val sender = call.getCommandSender();

        if (!(sender instanceof Player player))
            return Collections.emptyList();

        try {
            return persistenceManager.getInvenotriesNames(player);
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    @Override
    public char getShortName() {
        return 'i';
    }

    @Override
    public @NotNull String variantToObject(@NotNull CommandCall call, @NotNull String variant) throws Exception {
        return variant;
    }
}
