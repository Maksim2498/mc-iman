package space.moontalk.mc.iman.command;

import space.moontalk.mc.commands.completion.InvalidPatternException;
import space.moontalk.mc.commands.completion.PatternTabCompleter;

public class InventoryTabCompleter extends PatternTabCompleter {
    public InventoryTabCompleter() throws InvalidPatternException {
        super(
            "(list | ls) %p",
            "(save | set | remove | rm) %s %p"
        );
    }
}
