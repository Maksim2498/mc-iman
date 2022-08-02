# Minecraft Inventory Manager Plugin

This plugin provides inventory managment API for
another plugins and respective commands for server operators.

## Commands:
- `/inventory list [player name]` – list player's inventories;
- `/inventory save <inventory name> [player name]` – save player's inventory;
- `/inventory set <inventory name> [player name]` – set player's inventory.

`/inv` alias is also available.

## API:
- `@NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception` – list player's inventories;
- `void saveInventory(@NonNull Player player, @NonNull String name) throws Exception` – save players's inventory;
- `void setInventory(@NonNull Player player, @NonNull String name) throws Exception` – set player's inventory.
