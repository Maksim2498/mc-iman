package space.moontalk.mc.iman.message;

import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.val;

import space.moontalk.ranges.IntegerRange;

import space.moontalk.placeholders.DefaultSubstituter;

@AllArgsConstructor
public class DefaultMessageProvider implements MessageProvider {
    private final @NotNull Configuration      config;
    private final @NotNull DefaultSubstituter substituter = new DefaultSubstituter();

    @Override
    public @NotNull String makeInvalidArgsNumMessage(@NotNull IntegerRange argsRange, int passed) {
        return getFormatedString(
            "invalid-args-num",
            "low",    Integer.toString(argsRange.getLow()),
            "high",   Integer.toString(argsRange.getHigh()),
            "passed", Integer.toString(passed)
        );
    }

    @Override
    public @NotNull String makeMissingSubcommandMessage() {
        return getString("missing-subcommand");
    }

    @Override
    public @NotNull String makeMissingPermissionMessage(@NotNull String permission) {
        return getFormatedString(
            "missing-permission",
            "permission", permission
        );
    }

    @Override
    public @NotNull String makeInvalidSubcommandMessage(@NotNull String subcommand) {
        return getFormatedString(
            "invalid-subcommand",
            "subcommand", subcommand
        );
    }

    @Override
    public @NotNull String makeNotAPlayerMessage() {
        return getString("not-a-player"); 
    }

    @Override
    public @NotNull String makePlayerNotFoundMessage(@NotNull String playerName) {
        return getFormatedString(
            "player-not-found",
            "player", playerName
        );
    }


    @Override
    public @NotNull String makeFailedToSaveInventoryMessage() {
        return getString("failed-to-save-inventory");
    }

    @Override
    public @NotNull String makeFailedToSetInventoryMessage() {
        return getString("failed-to-set-inventory");
    }

    @Override
    public @NotNull String makeFailedToRemoveInventoryMessage() {
        return getString("failed-to-remove-inventory");
    }

    @Override
    public @NotNull String makeMissingInventoryMessage(@NotNull String inventoryName) {
        return getFormatedString(
            "missing-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeMissingYourInvenotriesMessage() {
        return getString("missing-your-inventories");
    }

    @Override
    public @NotNull String makeMissingInventoriesMessage(@NotNull String playerName) {
        return getFormatedString(
            "missing-inventories",
            "player", playerName
        );
    }

    @Override
    public @NotNull String makeYourInventoriesMessage() {
        return getString("your-inventories");
    }

    @Override
    public @NotNull String makeInventoriesMessage(@NotNull String playerName) {
        return getFormatedString(
            "inventories",
            "player", playerName
        );
    }

    @Override
    public @NotNull String makeInventoryMessage(@NotNull String inventoryName) {
        return getFormatedString(
            "inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSaveYourInventoryMessage(@NotNull String inventoryName) {
        return getFormatedString(
            "save-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSaveInventoryMessage(@NotNull String playerName, @NotNull String inventoryName) {
        return getFormatedString(
            "save-inventory",
            "player",    playerName,
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSetYourInventoryMessage(@NotNull String inventoryName) {
        return getFormatedString(
            "set-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSetInventoryMessage(@NotNull String playerName, @NotNull String inventoryName) {
        return getFormatedString(
            "set-inventory",
            "player",    playerName,
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeRemoveYourInventoryMessage(@NotNull String inventoryName) {
        return getFormatedString(
            "remove-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeRemoveInventoryMessage(@NotNull String playerName, @NotNull String inventoryName) {
        return getFormatedString(
            "remove-inventory",
            "player",    playerName,
            "inventory", inventoryName
        );
    }

    private @NotNull String getFormatedString(@NotNull String path, @NotNull String... replacements) {
        val string          = getString(path);
        val formattedString = substituter.substitute(string, replacements);

        return formattedString;
    }

    private @NotNull String getString(@NotNull String path) {
        return config.getString("messages." + path);
    }
}
