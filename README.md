# Minecraft Inventory Manager Plugin

This plugin provides inventory managment API for
other plugins and respective commands for in-game use.

**Warning: plugin isn't finished yet, it's just a preview.**

## Commands:

- `/inventory list [player name]` - list player's inventories;
- `/inventory save <inventory name> [player name]` - save player's inventory;
- `/inventory set <inventory name> [player name]` - set player's inventory.

*`/inv` alias is also available.*

## API:

List player's inventories:
```java
@NonNull List<@NonNull String> getInvenotriesNames(@NonNull Player player) throws Exception
```
 
Save player's inventory:
```java
void saveInventory(@NonNull Player player, @NonNull String name) throws Exception
```

Set player's inventory:
```java
void setInventory(@NonNull Player player, @NonNull String name) throws Exception
```

## Configuration:

You can configure plugin's messages and persistence properties.
The following is a set of property tables divided by sections.

### `message` Section:

This section specifies messages that the plugins sends under certain criteria.
Messages can contain placeholders like \<player\>.
For example of using placeholders see [default config](src/main/resources/config.yml).

| Message                    | Shown when                                                                                          |
|----------------------------|-----------------------------------------------------------------------------------------------------|
| `missing-subcommand`       | `/inv` issued without parameters                                                                    |
| `invalid-subcommand`       | `/inv` issued with invalid first parameter                                                          |
| `invalid-args-num`         | `/inv <subcommand>` issued with invalid number of arguments                                         |
| `not-a-player`             | version of command only for players issued not by a player                                          |
| `player-not-found`         | specified player was not found                                                                      |
| `missing-your-inventories` | you issue `/inv list` and had no saved inventories                                                  |
| `missing-inventories`      | `/inv list <player name>` issued and specified player had no saved inventories                      |
| `your-inventories`         | you issue `/inv list` and had saved inventories (inventory list header)                             |
| `inventories`              | `/inv list <player name>` issued and specified player had saved inventories (inventory list header) |
| `inventory`                | `/inv list [player name]` and you (or specified player) had saved inventories (inventory list item) |
| `save-your-inventory`      | `/inv save <inventory name> [your name]` issued                                                     |
| `save-inventory`           | `/inv save <inventory name> <player name>` issued                                                   |
| `set-your-inventory`       | `/inv set <inventory name> [your name]` issued                                                      |
| `set-inventory`            | `/inv set <inventory name> <player name>` issued                                                    |
  
### `persistence` Section:

This section specifies how and where to store inventories.

| Option     | Value      | Description                                                                                                         |
|------------|------------|---------------------------------------------------------------------------------------------------------------------|
| `method`   | `file`     | Specifies storage method (whether to store data in plain files or in database (only file storage is now available)) |
| `dir-name` | **string** | Specifies directory name where to store data if specified `method` is `file`                                        |
  
**Warning: currently options of this section doesn't effect plugin's behavior.**
  
## Permissions:

Plugin provides you a list of permissions for every subcommand and it's every variation.

| Permission            | Allowes to                | Default  |
|-----------------------|---------------------------|----------|
| `iman.inv`            | run any subcommand        | **op**   |
| `iman.inv.list`       | list inventories          | **op**   |
| `iman.inv.list.self`  | list your own inventories | **true** |
| `iman.inv.list.other` | list others' inventories  | **op**   |
| `iman.inv.save`       | save inventories          | **op**   |
| `iman.inv.save.self`  | save your own inventory   | **op**   |
| `iman.inv.save.other` | save others' inventory    | **op**   |
| `iman.inv.set`        | set inventories           | **op**   |
| `iman.inv.set.self`   | set your own inventory    | **op**   |
| `iman.inv.set.other`  | set others' inventory     | **op**   |
