package space.moontalk.mc.iman.message;

import org.jetbrains.annotations.NotNull;

import space.moontalk.ranges.IntegerRange;

public interface MessageProvider {
    // Errors:
    @NotNull String makeMissingSubcommand();
    @NotNull String makeInvalidSubcommand(@NotNull String subcommand);
    @NotNull String makeInvalidArgsNum(@NotNull IntegerRange argsRange, int passed);
    @NotNull String makeNotAPlayer();
    @NotNull String makePlayerNotFound(@NotNull String playerName); 
    @NotNull String makeFailedToSaveInventory();
    @NotNull String makeFailedToSetInventory();
    @NotNull String makeFailedToRemoveInventory();
    @NotNull String makeMissingInventory(@NotNull String inventoryName);

    // List:
    @NotNull String makeMissingYourInvenotries();
    @NotNull String makeMissingInventories(@NotNull String playerName);
    @NotNull String makeYourInventories();
    @NotNull String makeInventories(@NotNull String playerName);
    @NotNull String makeInventory(@NotNull String inventoryName);

    // Save:
    @NotNull String makeSaveYourInventory(@NotNull String inventoryName);
    @NotNull String makeSaveInventory(@NotNull String playerName, @NotNull String inventoryName);

    // Set:
    @NotNull String makeSetYourInventory(@NotNull String inventoryName);
    @NotNull String makeSetInventory(@NotNull String playerName, @NotNull String inventoryName);

    // Remove:
    @NotNull String makeRemoveYourInventory(@NotNull String inventoryName);
    @NotNull String makeRemoveInventory(@NotNull String playerName, @NotNull String inventoryName);
}
