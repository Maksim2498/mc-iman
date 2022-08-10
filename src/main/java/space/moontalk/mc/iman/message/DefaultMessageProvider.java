package space.moontalk.mc.iman.message;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;
import space.moontalk.ranges.IntegerRange;

@AllArgsConstructor
public class DefaultMessageProvider implements MessageProvider, PluginHolder {
    @Getter
    @NonNull
    private final Iman plugin;

    @Override
    public @NonNull String makeMissingSubcommand() {
        return getString("missing-subcommand");
    }

    @Override
    public @NonNull String makeInvalidSubcommand(@NonNull String subcommand) {
        return getFormatedString(
            "invalid-subcommand",
            "<subcommand>", subcommand
        );
    }

    @Override
    public @NonNull String makeInvalidArgsNum(@NonNull IntegerRange argsRange, int passed) {
        return getFormatedString(
            "invalid-args-num",
            "<low>",    Integer.toString(argsRange.getLow()),
            "<high>",   Integer.toString(argsRange.getHigh()),
            "<passed>", Integer.toString(passed)
        );
    }

    @Override
    public @NonNull String makeNotAPlayer() {
        return getString("not-a-player"); 
    }

    @Override
    public @NonNull String makePlayerNotFound(@NonNull String playerName) {
        return getFormatedString(
            "player-not-found",
            "<player>", playerName
        );
    }


    @Override
    public @NonNull String makeFailedToSaveInventory() {
        return getString("failed-to-save-inventory");
    }

    @Override
    public @NonNull String makeFailedToSetInventory() {
        return getString("failed-to-set-inventory");
    }

    @Override
    public @NonNull String makeFailedToRemoveInventory() {
        return getString("failed-to-remove-inventory");
    }

    @Override
    public @NonNull String makeMissingInventory(@NonNull String inventoryName) {
        return getFormatedString(
            "missing-inventory",
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeMissingYourInvenotries() {
        return getString("missing-your-inventories");
    }

    @Override
    public @NonNull String makeMissingInventories(@NonNull String playerName) {
        return getFormatedString(
            "missing-inventories",
            "<player>", playerName
        );
    }

    @Override
    public @NonNull String makeYourInventories() {
        return getString("your-inventories");
    }

    @Override
    public @NonNull String makeInventories(@NonNull String playerName) {
        return getFormatedString(
            "inventories",
            "<player>", playerName
        );
    }

    @Override
    public @NonNull String makeInventory(@NonNull String inventoryName) {
        return getFormatedString(
            "inventory",
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeSaveYourInventory(@NonNull String inventoryName) {
        return getFormatedString(
            "save-your-inventory",
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeSaveInventory(@NonNull String playerName, @NonNull String inventoryName) {
        return getFormatedString(
            "save-inventory",
            "<player>",    playerName,
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeSetYourInventory(@NonNull String inventoryName) {
        return getFormatedString(
            "set-your-inventory",
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeSetInventory(@NonNull String playerName, @NonNull String inventoryName) {
        return getFormatedString(
            "set-inventory",
            "<player>",    playerName,
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeRemoveYourInventory(@NonNull String inventoryName) {
        return getFormatedString(
            "remove-your-inventory",
            "<inventory>", inventoryName
        );
    }

    @Override
    public @NonNull String makeRemoveInventory(@NonNull String playerName, @NonNull String inventoryName) {
        return getFormatedString(
            "remove-inventory",
            "<player>",    playerName,
            "<inventory>", inventoryName
        );
    }

    private @NonNull String getFormatedString(@NonNull String path, @NonNull String... replacements) {
        return replacePlaceholders(getString(path), replacements);
    }

    private static @NonNull String replacePlaceholders(@NonNull String string, @NonNull String[] replacements) {
        assert replacements.length % 2 == 0;

        val substrings = splitByPlaceHolders(string);

        replacePlaceholdersInSplited(substrings, replacements); 

        return String.join("", substrings);
    }

    private static @NonNull String[] splitByPlaceHolders(@NonNull String string) {
        return string.split("(?=<[^<>]*>)|(?<=<[^<>]*>)");   
    }

    private static void replacePlaceholdersInSplited(@NonNull String[] substrings, @NonNull String[] replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            val placeholder = replacements[i];
            val changer     = replacements[i + 1]; 

            for (int j = 0; j < substrings.length; ++j) 
                if (substrings[j].equals(placeholder))
                    substrings[j] = changer;
        }
    }

    private @NonNull String getString(@NonNull String path) {
        return plugin.getConfig().getString("messages." + path);
    }
}
