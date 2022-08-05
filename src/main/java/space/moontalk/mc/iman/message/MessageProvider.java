package space.moontalk.mc.iman.message;

import org.checkerframework.checker.nullness.qual.NonNull;

import space.moontalk.mc.iman.command.sub.ArgsRange;

public interface MessageProvider {
    // Errors:
    @NonNull String makeMissingSubcommand();
    @NonNull String makeInvalidSubcommand(@NonNull String subcommand);
    @NonNull String makeInvalidArgsNum(@NonNull ArgsRange argsRange, int passed);
    @NonNull String makeNotAPlayer();
    @NonNull String makePlayerNotFound(@NonNull String playerName); 
    @NonNull String makeFailedToSaveInventory();
    @NonNull String makeFailedToSetInventory();
    @NonNull String makeFailedToRemoveInventory();
    @NonNull String makeMissingInventory(@NonNull String inventoryName);

    // List:
    @NonNull String makeMissingYourInvenotries();
    @NonNull String makeMissingInventories(@NonNull String playerName);
    @NonNull String makeYourInventories();
    @NonNull String makeInventories(@NonNull String playerName);
    @NonNull String makeInventory(@NonNull String inventoryName);

    // Save:
    @NonNull String makeSaveYourInventory(@NonNull String inventoryName);
    @NonNull String makeSaveInventory(@NonNull String playerName, @NonNull String inventoryName);

    // Set:
    @NonNull String makeSetYourInventory(@NonNull String inventoryName);
    @NonNull String makeSetInventory(@NonNull String playerName, @NonNull String inventoryName);

    // Remove:
    @NonNull String makeRemoveYourInventory(@NonNull String inventoryName);
    @NonNull String makeRemoveInventory(@NonNull String playerName, @NonNull String inventoryName);
}
