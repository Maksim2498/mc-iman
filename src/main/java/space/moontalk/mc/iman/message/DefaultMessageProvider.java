package space.moontalk.mc.iman.message;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import space.moontalk.mc.iman.*;
import space.moontalk.mc.iman.command.sub.ArgsRange;

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
        return getString("invalid-subcommand")
              .replaceAll("<subcommand>", subcommand);
    }

    @Override
    public @NonNull String makeInvalidArgsNum(@NonNull ArgsRange argsRange, int passed) {
        return getString("invalid-args-num")
              .replaceAll("<low>", Integer.toString(argsRange.getLow()))
              .replaceAll("<high>", Integer.toString(argsRange.getHigh()))
              .replaceAll("<passed>", Integer.toString(passed));
    }

    @Override
    public @NonNull String makeNotAPlayer() {
        return getString("not-a-player"); 
    }

    @Override
    public @NonNull String makePlayerNotFound(@NonNull String name) {
        return getString("player-not-found")
              .replaceAll("<player>", name);
    }

    @Override
    public @NonNull String makeMissingYourInvenotries() {
        return getString("missing-your-inventories");
    }

    @Override
    public @NonNull String makeMissingInventories(@NonNull String name) {
        return getString("missing-inventories");
    }

    @Override
    public @NonNull String makeYourInventories() {
        return getString("your-inventories");
    }

    @Override
    public @NonNull String makeInventories(@NonNull String name) {
        return getString("inventories")
              .replaceAll("<player>", name);
    }

    @Override
    public @NonNull String makeInventory(@NonNull String name) {
        return getString("inventory")
              .replaceAll("<inventory>", name);
    }

    @Override
    public @NonNull String makeSaveYourInventory(@NonNull String name) {
        return getString("save-your-inventory")
              .replaceAll("<inventory>", name);
    }

    @Override
    public @NonNull String makeSaveInventory(@NonNull String playerName, @NonNull String inventoryName) {
        return getString("save-inventory")
              .replaceAll("<player>", playerName)
              .replaceAll("<inventory>", inventoryName);
    }

    @Override
    public @NonNull String makeSetYourInventory(@NonNull String name) {
        return getString("set-your-inventory")
              .replaceAll("<inventory>", name);
    }

    @Override
    public @NonNull String makeSetInventory(@NonNull String playerName, @NonNull String inventoryName) {
        return getString("set-inventory")
              .replaceAll("<player>", playerName)
              .replaceAll("<inventory>", inventoryName);
    }

    private @NonNull String getString(@NonNull String path) {
        return plugin.getConfig().getString(path);
    }
}
