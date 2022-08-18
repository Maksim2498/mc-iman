package space.moontalk.mc.iman.command;

import lombok.SneakyThrows;

import space.moontalk.mc.commands.completion.PatternTabCompleter;

public class InventoryTabCompleter extends PatternTabCompleter {
    @SneakyThrows
    public InventoryTabCompleter() {
        super(
            "(list | ls) %p",
            "(save | set | remove | rm) %s %p"
        );
    }
}
