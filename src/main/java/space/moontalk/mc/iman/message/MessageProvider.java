package space.moontalk.mc.iman.message;

import org.jetbrains.annotations.NotNull;

public interface MessageProvider {
    // Errors:
    @NotNull String makeFailedToSaveInventoryMessage();
    @NotNull String makeFailedToSetInventoryMessage();
    @NotNull String makeFailedToRemoveInventoryMessage();
    @NotNull String makeMissingInventoryMessage(@NotNull String inventoryName);

    // List:
    @NotNull String makeMissingYourInvenotriesMessage();
    @NotNull String makeMissingInventoriesMessage(@NotNull String playerName);
    @NotNull String makeYourInventoriesMessage();
    @NotNull String makeInventoriesMessage(@NotNull String playerName);
    @NotNull String makeInventoryMessage(@NotNull String inventoryName);

    // Save:
    @NotNull String makeSaveYourInventoryMessage(@NotNull String inventoryName);
    @NotNull String makeSaveInventoryMessage(@NotNull String playerName, @NotNull String inventoryName);

    // Set:
    @NotNull String makeSetYourInventoryMessage(@NotNull String inventoryName);
    @NotNull String makeSetInventoryMessage(@NotNull String playerName, @NotNull String inventoryName);

    // Remove:
    @NotNull String makeRemoveYourInventoryMessage(@NotNull String inventoryName);
    @NotNull String makeRemoveInventoryMessage(@NotNull String playerName, @NotNull String inventoryName);
}
