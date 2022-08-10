package space.moontalk.mc.iman.message;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;
import space.moontalk.ranges.IntegerRange;
import space.moontalk.placeholders.DefaultSubstituter;

@AllArgsConstructor
public class DefaultMessageProvider implements MessageProvider, PluginHolder {
    @Getter
    @NotNull
    private final Iman plugin;

    private final DefaultSubstituter substituter = new DefaultSubstituter();

    @Override
    public @NotNull String makeMissingSubcommand() {
        return getString("missing-subcommand");
    }

    @Override
    public @NotNull String makeInvalidSubcommand(@NotNull String subcommand) {
        return getFormatedString(
            "invalid-subcommand",
            "subcommand", subcommand
        );
    }

    @Override
    public @NotNull String makeInvalidArgsNum(@NotNull IntegerRange argsRange, int passed) {
        return getFormatedString(
            "invalid-args-num",
            "low",    Integer.toString(argsRange.getLow()),
            "high",   Integer.toString(argsRange.getHigh()),
            "passed", Integer.toString(passed)
        );
    }

    @Override
    public @NotNull String makeNotAPlayer() {
        return getString("not-a-player"); 
    }

    @Override
    public @NotNull String makePlayerNotFound(@NotNull String playerName) {
        return getFormatedString(
            "player-not-found",
            "player", playerName
        );
    }


    @Override
    public @NotNull String makeFailedToSaveInventory() {
        return getString("failed-to-save-inventory");
    }

    @Override
    public @NotNull String makeFailedToSetInventory() {
        return getString("failed-to-set-inventory");
    }

    @Override
    public @NotNull String makeFailedToRemoveInventory() {
        return getString("failed-to-remove-inventory");
    }

    @Override
    public @NotNull String makeMissingInventory(@NotNull String inventoryName) {
        return getFormatedString(
            "missing-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeMissingYourInvenotries() {
        return getString("missing-your-inventories");
    }

    @Override
    public @NotNull String makeMissingInventories(@NotNull String playerName) {
        return getFormatedString(
            "missing-inventories",
            "player", playerName
        );
    }

    @Override
    public @NotNull String makeYourInventories() {
        return getString("your-inventories");
    }

    @Override
    public @NotNull String makeInventories(@NotNull String playerName) {
        return getFormatedString(
            "inventories",
            "player", playerName
        );
    }

    @Override
    public @NotNull String makeInventory(@NotNull String inventoryName) {
        return getFormatedString(
            "inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSaveYourInventory(@NotNull String inventoryName) {
        return getFormatedString(
            "save-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSaveInventory(@NotNull String playerName, @NotNull String inventoryName) {
        return getFormatedString(
            "save-inventory",
            "player",    playerName,
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSetYourInventory(@NotNull String inventoryName) {
        return getFormatedString(
            "set-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeSetInventory(@NotNull String playerName, @NotNull String inventoryName) {
        return getFormatedString(
            "set-inventory",
            "player",    playerName,
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeRemoveYourInventory(@NotNull String inventoryName) {
        return getFormatedString(
            "remove-your-inventory",
            "inventory", inventoryName
        );
    }

    @Override
    public @NotNull String makeRemoveInventory(@NotNull String playerName, @NotNull String inventoryName) {
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
        return plugin.getConfig().getString("messages." + path);
    }
}
