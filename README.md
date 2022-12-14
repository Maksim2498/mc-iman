# IMan - Minecraft Inventory Manager Plugin

![Logo](/images/logo.png)

## Index

- [Index](#index);
- [About](#about);
- [Download](#download);
- [Installation](#installation);
    - [As Plugin](#as-plugin);
    - [As Dependency](#as-dependency);
- [Building](#building);
- [Commands](#commands);
- [API](#api);
- [Configuration](#configuration);
    - [`message` Section](#message-section);
    - [`persistence` Section](#persistence-section);
- [Permissions](#permissions).

## About

This plugin provides inventory managment API for
other plugins and respective commands for in-game use.

**Warning: plugin isn't finished yet, it's just a preview.**

## Download

See [releases page](https://github.com/Maksim2498/mc-iman/releases).

## Installation 

Installation method varies depending on your goal:
wheater you want just to add plugin to your server
or use it's API from another plugin.

### As Plugin

[Download](#download) latest plugin release and just put a jar in
your server's plugins folder.

### As Dependency

First, add MoonTalk repository to your pom.xml:

```xml
<repository>
    <id>moontalk<id>
    <url>https://repo.moontalk.space/repository/maven-releases/</id>
</repository>
```

Second, add plugin to your dependencies:

```xml
<dependency>
    <groupId>space.moontalk.mc</groupId>
    <artifactId>iman<artifactId>
    <version>1.1.1</version>
</dependency>
```

Done.

## Building

To build plugin you need to install [Maven](https://maven.apache.org/) first.
Next just `cd` to plugin folder run `mvn verify`. 
Done.

## Commands:

| Command                                            | Description               |
|----------------------------------------------------|---------------------------|
| `/inventory list [player name]`                    | list player's inventories |
| `/inventory ls [player name]`                      | ^                         |
| `/inventory save <inventory name> [player name]`   | save player's inventory   |
| `/inventory set <inventory name> [player name]`    | set player's inventory    |
| `/inventory remove <inventory name> [player name]` | remove player's inventory |
| `/inventory rm <inventory name> [player name]`     | ^                         |

*`/inv` alias is also available.*

## API:

To use API you have to get plugin object and then get it's `PersistenceManager` object using following method:

```java
@NonNull PersistenceManager getPersistenceManager()
```

The following is the list of `PersistenceManager` methods.

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

Remove player's inventory:

```java
void removeInventory(@NonNull Player player, @NonNull String inventoryName) throws Exception
```

## Configuration:

You can configure plugin's messages and persistence properties.
The following is a set of property tables divided by sections.

### `message` Section:

This section specifies messages that the plugins sends under certain criteria.
Messages can contain placeholders like \<player\>.
For example of using placeholders see [default config](/src/main/resources/config.yml).

| Message                      | Shown when                                                                                          |
|------------------------------|-----------------------------------------------------------------------------------------------------|
| `failed-to-save-inventory`   | inventory saving failed due to server's internal error                                              |
| `failed-to-set-inventory`    | inventory reading failed due to server's internal error                                             |
| `failed-to-remove-inventory` | inventory removing failed due to server's internal error                                            |
| `missing-your-inventories`   | you issue `/inv list` and had no saved inventories                                                  |
| `missing-inventories`        | `/inv list <player name>` issued and specified player had no saved inventories                      |
| `your-inventories`           | you issue `/inv list` and had saved inventories (inventory list header)                             |
| `inventories`                | `/inv list <player name>` issued and specified player had saved inventories (inventory list header) |
| `inventory`                  | `/inv list [player name]` and you (or specified player) had saved inventories (inventory list item) |
| `save-your-inventory`        | `/inv save <inventory name> [your name]` issued                                                     |
| `save-inventory`             | `/inv save <inventory name> <player name>` issued                                                   |
| `set-your-inventory`         | `/inv set <inventory name> [your name]` issued                                                      |
| `set-inventory`              | `/inv set <inventory name> <player name>` issued                                                    |
| `remove-your-inventory`      | `/inv remove <inventory name> [your name]` issued                                                   |
| `remove-inventory`           | `/inv remove <inventory name> <player name>` issued                                                 |

### `persistence` Section:

This section specifies how and where to store inventories.

| Option     | Value      | Description                                                                                                         |
|------------|------------|---------------------------------------------------------------------------------------------------------------------|
| `method`   | `file`     | Specifies storage method (whether to store data in plain files or in database (only file storage is now available)) |
| `dir-name` | **string** | Specifies directory name where to store data if specified `method` is `file`                                        |

**Warning: currently options of this section doesn't effect plugin's behavior.**

## Permissions:

Plugin provides you a list of permissions for every subcommand and it's every variation.

| Permission              | Allowes to                  | Default  |
|-------------------------|-----------------------------|----------|
| `iman.inv`              | run any subcommand          | **op**   |
| `iman.inv.list`         | list inventories            | **op**   |
| `iman.inv.list.self`    | list your own inventories   | **true** |
| `iman.inv.list.other`   | list others' inventories    | **op**   |
| `iman.inv.save`         | save inventories            | **op**   |
| `iman.inv.save.self`    | save your own inventory     | **op**   |
| `iman.inv.save.other`   | save others' inventory      | **op**   |
| `iman.inv.set`          | set inventories             | **op**   |
| `iman.inv.set.self`     | set your own inventory      | **op**   |
| `iman.inv.set.other`    | set others' inventory       | **op**   |
| `iman.inv.remove`       | remove inventories          | **op**   | 
| `iman.inv.remove.self`  | remove your own inventories | **op**   |
| `iman.inv.remove.other` | remove others' inventories  | **op**   |
