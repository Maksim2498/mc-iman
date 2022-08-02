package space.moontalk.mc.iman.message;

import org.checkerframework.checker.nullness.qual.NonNull;

import space.moontalk.mc.iman.command.sub.ArgsRange;

public interface MessageProvider {
    // Errors:
    @NonNull String makeMissingSubcommand();
    @NonNull String makeInvalidSubcommand(@NonNull String subcommand);
    @NonNull String makeInvalidArgsNum(@NonNull ArgsRange argsRange, int passed);
    @NonNull String makeNotAPlayer();
    @NonNull String makePlayerNotFound(@NonNull String name); 

    // List:
    @NonNull String makeMissingYourInvenotries();
    @NonNull String makeMissingInventories(@NonNull String name);
    @NonNull String makeYourInventories();
    @NonNull String makeInventories(@NonNull String name);
    @NonNull String makeInventory(@NonNull String name);

    // Save:
    @NonNull String makeSaveYourInventory(@NonNull String name);
    @NonNull String makeSaveInventory(@NonNull String playerName, @NonNull String inventoryName);

    // Set:
    @NonNull String makeSetYourInventory(@NonNull String name);
    @NonNull String makeSetInventory(@NonNull String playerName, @NonNull String inventoryName);
}
