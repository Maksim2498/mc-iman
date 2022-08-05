package space.moontalk.mc.iman.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import lombok.val;

public class InventoryTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(
        @NonNull CommandSender sender,
        @NonNull Command       command,
        @NonNull String        alias, 
        @NonNull String[]      args
    ) {
        return switch (args.length) {
            case 1 -> {
                val list = new LinkedList<String>();

                list.add("list");
                list.add("save");
                list.add("set");
                list.add("remove");

                removeNotStartingWith(list, args[0]); 

                yield list;
            }

            case 2 -> switch (args[0].toLowerCase()) {
                case "list" -> getPlayersNamesStartingWith(args[1]);
                default     -> Collections.emptyList();                    
            };

            case 3 -> switch (args[0].toLowerCase()) {
                case "save", "set", "remove" -> getPlayersNamesStartingWith(args[2]);
                default                      -> Collections.emptyList();
            };

            default -> Collections.emptyList();
        };
    }

    private static @NonNull List<@NonNull String> getPlayersNamesStartingWith(@NonNull String prefix) {
        return removeNotStartingWith(getPlayersNames(), prefix);
    }

    private static @NonNull List<@NonNull String> getPlayersNames() {
        return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    private static @NonNull List<@NonNull String> removeNotStartingWith(@NonNull List<String> strings, @NonNull String prefix) {
        val lowerPrefix = prefix.toLowerCase();
        strings.removeIf(s -> !s.toLowerCase().startsWith(lowerPrefix));
        return strings;
    }
}
