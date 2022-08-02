# Minecraft Inventory Manager Plugin

This plugin provides inventory managment API for
another plugins and respective commands for server operators.

**Warning: plugin isn't finished yet, it's just a preview.**

## Commands:
- `/inventory list [player name]` - list player's inventories;
- `/inventory save <inventory name> [player name]` - save player's inventory;
- `/inventory set <inventory name> [player name]` - set player's inventory.

*`/inv` alias is also available.*

## API:
- `@NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception` - list player's inventories;
- `void saveInventory(@NonNull Player player, @NonNull String name) throws Exception` - save players's inventory;
- `void setInventory(@NonNull Player player, @NonNull String name) throws Exception` - set player's inventory.

## Configuration:

You can configure plugin's messages.
The following is it's messages names.
Messages can contain placeholders like <player>.
For example of using placeholders see [default config](src/main/resources/config.yml).

### Error:
- `missing-subcommand` - `/inv` called without parameters;
- `invalid-subcommand` - `/inv` called with invalid first parameter;
- `invalid-args-num` - `/inv <subcommand name>` called with invalid number of arguments;
- `not-a-player` - issued version of a command must be run by a player;
- `player-not-found` - specified player was not found.

### List:
- `missing-your-inventories` - shown when you issue `/inv list` and have no saved inventories;
- `missing-inventories` - shown when you issue `/inv list <player name>` and target player has no saved inventories;
- `your-inventories` - heading of your inventories list shown after issuing `/inv list` when you have saved inventories;
- `inventories` - heading of target player inventories list shown after issuing `/inv list <player name>` when target player has saved inventories;
- `inventory` - item of inventories list shown after issuing `/inv list [player name]` and target player has saved inventories.

### Save:
- `save-your-inventory` - shown after issuing `/inv save <inventory name>`;
- `save-inventory` - show after issuing `/inv save <inventory name> <player name>`.

### Set:
- `set-your-inventory` -;shown after issuing `/inv set <inventory name>`;
- `set-inventory` - show after issuing `/inv set <inventory name> <player name>`.
