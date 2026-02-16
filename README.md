# 🎮 CORESV-PROJECT

> Minecraft 1.8 Hardcore/PvP Core Plugin - **MySQL-based** single-server system

[![Minecraft](https://img.shields.io/badge/Minecraft-1.8-brightgreen.svg)](https://www.spigotmc.org/)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-Required-blue.svg)](https://www.mysql.com/)

## 📋 O Projekcie

Core plugin dla serwera Minecraft Hardcore/PvP **bez systemu sektorów** (single server). 
Rozbudowany system z gildami, custom dropem, rankingiem, achievementami i wieloma innymi.

**Legacy code** - "Very old core for minecraft 1.8" (według autora).

## 🏗️ Architektura

```
Single Spigot Server
         │
    ┌────▼────┐
    │  Core   │
    │ Plugin  │
    └────┬────┘
         │
    ┌────▼────────┐
    │   MySQL     │
    │  Database   │
    └─────────────┘
```

- ✅ MySQL persistence
- ✅ Guild system z cuboid protection
- ✅ Custom drop system
- ✅ Ranking & leaderboards
- ✅ Achievement system
- ✅ Combat tag
- ✅ Backup system
- ✅ Ban/Whitelist
- ✅ Shop & economy

## 📦 Struktura Główna

```
pl.blackwater.hardcore/
│
├── Core.java                  # Main plugin class
│
├── data/                      # Data models
│   ├── User.java              # ⭐ Player data (33+ fields!)
│   ├── Rank.java              # Permission ranks
│   └── TextCommand.java       # Custom commands
│
├── guilds/                    # ⭐ Guild system
│   ├── data/
│   │   ├── Guild.java         # Guild object
│   │   ├── Member.java        # Member data
│   │   ├── Alliance.java      # Alliance data
│   │   └── Cuboid.java        # Protected area
│   ├── managers/
│   │   ├── GuildManager.java
│   │   ├── MemberManager.java
│   │   └── AllianceManager.java
│   ├── commands/user/         # 20+ guild commands
│   ├── listeners/             # Guild events
│   └── inventories/           # Guild GUIs
│
├── drop/                      # ⭐ Custom drop system
│   ├── data/drops/            # Drop configs
│   ├── listeners/
│   │   ├── DropBlockBreakListener.java
│   │   └── GeneratorListener.java  # Cobblestone generators
│   └── managers/
│       └── DropManager.java
│
├── managers/                  # ⭐ Core systems
│   ├── UserManager.java       # Player management
│   ├── RankManager.java       # Permission system
│   ├── GuildManager.java      # Guild system
│   ├── ChatManager.java       # Chat control
│   ├── BanManager.java        # Ban system
│   ├── BackupManager.java     # Inventory backups
│   ├── CombatManager.java     # Combat tag
│   ├── KitManager.java        # Kit system
│   ├── EventManager.java      # Special events
│   ├── EnableManager.java     # Feature toggles
│   └── AntyMacroManager.java  # Anti-macro
│
├── commands/                  # 50+ commands
│   ├── GamemodeCommand.java
│   ├── BanCommand.java
│   ├── GuildCommand.java
│   └── ...
│
├── listeners/                 # Event handlers
│   ├── PlayerJoinQuitListener.java
│   ├── EntityDamageByEntityListener.java
│   ├── AsyncPlayerChatListener.java
│   ├── BorderListener.java
│   └── ...
│
├── storage/                   # MySQL storage
│   ├── RankStorage.java
│   ├── KitsStorage.java
│   ├── BackupStorage.java
│   └── ...
│
├── tasks/                     # Async tasks
│   ├── TabUpdateTask.java
│   ├── CombatTask.java
│   ├── BackupTask.java
│   └── TopUpdateTask.java
│
├── inventories/               # GUI systems
│   ├── DropInventory.java
│   ├── ShopInventory.java
│   ├── TopInventory.java
│   └── ...
│
├── ranking/                   # Leaderboard system
├── events/                    # Event system
└── settings/                  # Configuration
    ├── CoreConfig.java
    ├── GuildConfig.java
    └── BackupConfig.java
```

## ⚙️ Główne Systemy

### 1. User System - Rozbudowany Player Data

```java
// User.java - 33+ fields per player!
public class User {
    // Identyfikacja
    private UUID uuid;
    private String lastName, firstIP, lastIP;
    
    // Statystyki PvP
    private int kills, deaths, assists, points;
    private String lastKill;
    private long lastKillTime;
    
    // Czas i aktywność
    private long lastJoin;
    private int timePlay, logouts;
    
    // Rangi i uprawnienia
    private Rank rank;
    private boolean fly, god;
    private GameMode gameMode;
    
    // Lokacje
    private Location homeLocation, lastLocation;
    
    // Kity
    private List<Long> kitTimes;  // Cooldowns
    
    // Drop system
    private List<Integer> drops;  // 15 różnych drop types
    
    // Economy
    private int coins;
    private int depositKox, depositRef, depositPearl;
    
    // Eventy
    private long turboDropTime, turboExpTime;
    private boolean turboDrop, turboExp;
    private double bonusDrop;
    
    // Leveling
    private double exp;
    private int level;
    
    // Statystyki consumption
    private int eatedRef, eatedKox, throwedPearl;
    
    // Achievement system
    private List<String> takenAchievements;
    private int openedChests;
    
    // Enderchest
    private ItemStack[] enderChest;
    
    // Player class
    private PlayerClassType playerClassType;  // TANK, ARCHER, etc
    
    // Messages
    private List<MessageType> messages;  // Toggles dla różnych typów wiadomości
}
```

**Insert do MySQL:**
```sql
INSERT INTO `users` (
    `uuid`, `lastName`, `firstIP`, `lastIP`, `lastKill`, `lastJoin`,
    `kitTimes`, `drops`, `lastKillTime`, `kills`, `deaths`, `logouts`,
    `assists`, `points`, `timePlay`, `depositKox`, `depositRef`, 
    `depositPearl`, `fly`, `god`, `gameMode`, `homeLocation`, 
    `lastLocation`, `rank`, `turboDropTime`, `turboExpTime`, 
    `turboDrop`, `turboExp`, `bonusDrop`, `coins`, `exp`, `level`,
    `eatedKox`, `eatedRef`, `throwedPearl`, `enderChest`, 
    `takenAchievements`, `openedChests`, `playerClass`, `messages`
) VALUES (...)
```

### 2. Guild System

```java
// Guild.java
public class Guild {
    private String name, tag;
    private Set<Member> members;
    private UUID owner;
    
    // Settings
    private boolean pvp;           // PvP w gildii
    private boolean preDeleted;    // Przed usunięciem
    
    // Timing
    private long createTime;
    private long expireTime;       // Wygaśnięcie (7 dni default)
    private long lastExplodeTime;
    private long liveCool;         // Cooldown na życie
    
    // Limits
    private int playerLimits;      // Limit członków
    private int lives;             // Życia gildii (3 default)
    
    // Invites
    private Set<UUID> invites;
    
    // Territory
    private Location homeLocation;
    private Cuboid cuboid;         // Protected area
    
    // Economy
    private int deposit;           // Skarbiec gildii
    
    // GUIs
    private InventoryGUI guildInventory;
    private InventoryGUI depositInventory;
}

// Cuboid.java - Protected area
public class Cuboid {
    private World world;
    private int centerX, centerZ;
    private int size;              // Rozmiar (np. 100x100)
    
    public boolean isInCuboid(Location loc) {
        return loc.getBlockX() >= centerX - size 
            && loc.getBlockX() <= centerX + size
            && loc.getBlockZ() >= centerZ - size
            && loc.getBlockZ() <= centerZ + size;
    }
}

// Member.java
public class Member {
    private UUID uuid;
    private String guild;
    private Position position;     // LIDER, ZALOZYCIEL, MEMBER
    private Set<GuildPermission> permissions;
}
```

**Guild Commands (20+):**
```
/guild create <tag> <name>
/guild delete
/guild invite <player>
/guild kick <player>
/guild join <tag>
/guild leave
/guild sethome
/guild home
/guild pvp
/guild deposit <amount>
/guild withdraw <amount>
/guild enlarge
/guild extend
/guild ally <tag>
/guild unally <tag>
/guild info [tag]
/guild top
... i więcej
```

### 3. Drop System

```java
// Drop.java
public class Drop {
    private String name;
    private Material what;         // Co breakować
    private Material whatdrop;     // Co dropuje
    private int height;            // Min wysokość
    private DropType type;         // STONE, FORTUNE, EXP
    private int chance;            // Szansa %
    private int amount;            // Ilość
    private int exp;               // EXP
}

// DropType enum
public enum DropType {
    STONE,      // Wykop kamienia
    FORTUNE,    // Z fortune
    EXP         // EXP drop
}
```

**Drop Config Example:**
```yaml
drops:
  - name: "COBBLESTONE"
    what: STONE
    whatdrop: COBBLESTONE
    height: 0
    type: STONE
    chance: 100
    amount: 1
    exp: 0
```

**Generator System:**
```java
// GeneratorListener.java - obsługuje cobble generators
// Gdy lava + water = cobblestone
// Może customizować co się generuje
```

### 4. Ranking System

```java
// TopUpdateTask.java - aktualizuje co 30 sekund
public class TopUpdateTask {
    
    private void updateTop() {
        // TOP KILLS
        List<User> topKills = users.stream()
            .sorted((u1, u2) -> Integer.compare(u2.getKills(), u1.getKills()))
            .limit(10)
            .collect(Collectors.toList());
        
        // TOP DEATHS
        List<User> topDeaths = users.stream()
            .sorted((u1, u2) -> Integer.compare(u2.getDeaths(), u1.getDeaths()))
            .limit(10)
            .collect(Collectors.toList());
            
        // TOP ASSISTS
        // TOP POINTS
        // TOP TIME
        // ... etc
    }
}
```

### 5. Combat System

```java
// CombatManager.java
public class CombatManager {
    private static Map<UUID, Long> combatTime = new HashMap<>();
    
    public static void addCombat(Player p) {
        combatTime.put(p.getUniqueId(), 
                      System.currentTimeMillis() + TimeUtil.SECOND.getTime(30));
    }
    
    public static boolean hasCombat(Player p) {
        if (!combatTime.containsKey(p.getUniqueId())) return false;
        return combatTime.get(p.getUniqueId()) > System.currentTimeMillis();
    }
}

// EntityDamageByEntityListener.java
// Gdy gracz atakuje gracza → dodaj combat tag
// Podczas combat:
// - Nie można /home
// - Nie można /spawn
// - Logout = backup + death
```

### 6. Backup System

```java
// BackupManager.java
public enum BackupType {
    DEATH,          // Śmierć
    LOGOUT,         // Logout podczas PvP
    CLOSESERVER,    // Wyłączenie serwera
    COMMAND         // Komenda admin
}

public class Backup {
    private String backupUUID;
    private String owner;
    private ItemStack[] armor;
    private ItemStack[] inventory;
    private long backupTime;
    private long takeBackupTime;
    private BackupType backupType;
    private String backupCreator;
}

// BackupTask.java - automatyczne backup co X minut
```

### 7. Ban System

```java
// BanManager.java
public class Ban {
    private UUID uuid;
    private String reason;
    private String admin;
    private long createTime;
    private long expireTime;  // -1 = permanent
    private boolean unban;
}

// BanIPManager.java - analogicznie dla IP

// Commands:
/ban <player> <reason>
/tempban <player> <time> <reason>
/unban <player>
/banip <ip> <reason>
/tempbanip <ip> <time> <reason>
/unbanip <ip>
```

### 8. Achievement System

```java
// AchievementType enum
public enum AchievementType {
    KILL_10,
    KILL_50,
    KILL_100,
    KILL_500,
    KILL_1000,
    DEATH_10,
    DEATH_50,
    // ... więcej
}

// User ma:
private List<String> takenAchievements;

public boolean isAchievementCompleted(String achievement) {
    return takenAchievements.contains(achievement);
}
```

### 9. Kit System

```java
// KitManager.java
public class Kit {
    private String name;
    private long time;             // Cooldown (ms)
    private ItemStack[] items;
    private ItemStack[] armor;
}

// User ma:
private List<Long> kitTimes;  // Ostatnie użycie każdego kitu

/kit <name>
```

### 10. Shop System

```java
// ShopInventory.java
// GUI z przedmiotami do kupienia za coins

// User ma:
private int coins;  // Waluta

/shop
```

### 11. Event System

```java
// EventManager.java
public class Event {
    private String name;
    private EventType type;    // TURBODROP, TURBOEXP
    private long endTime;
    private int multiplier;    // x2, x3, etc
}

/event start <type> <time> <multiplier>
/event stop
```

### 12. Custom Crafting

```java
// CustomCraftingInventory.java
// Własne receptury craftingu

/crafting
```

### 13. Anti-Macro System

```java
// AntyMacroManager.java
// Wykrywa botów i macro
// Wysyła captcha/zadania do wykonania
```

## 📊 MySQL Tables

```sql
-- users (main player data)
CREATE TABLE users (
    id INT AUTO_INCREMENT,
    uuid VARCHAR(255),
    lastName VARCHAR(16),
    firstIP VARCHAR(20),
    lastIP VARCHAR(20),
    kills INT,
    deaths INT,
    points INT,
    coins INT,
    rank VARCHAR(16),
    ... (33+ kolumn!)
);

-- guilds
CREATE TABLE guilds (
    id INT AUTO_INCREMENT,
    name VARCHAR(32),
    tag VARCHAR(4),
    owner VARCHAR(255),
    pvp TINYINT(1),
    createTime BIGINT,
    expireTime BIGINT,
    playerLimits INT,
    lives INT,
    homeLocation VARCHAR(255),
    cuboidSize INT,
    deposit INT,
    ...
);

-- members
CREATE TABLE members (
    id INT AUTO_INCREMENT,
    uuid VARCHAR(255),
    guild TEXT,
    position TEXT,
    permissions TEXT
);

-- alliances
CREATE TABLE alliances (
    id INT AUTO_INCREMENT,
    guild1 TEXT,
    guild2 TEXT
);

-- backups
CREATE TABLE backups (
    id INT AUTO_INCREMENT,
    backupUUID VARCHAR(255),
    owner VARCHAR(255),
    armor TEXT,
    inventory TEXT,
    backupTime BIGINT,
    backupType VARCHAR(12),
    backupCreator VARCHAR(16)
);

-- bans
CREATE TABLE bans (
    id INT AUTO_INCREMENT,
    uuid VARCHAR(255),
    reason VARCHAR(255),
    admin VARCHAR(255),
    createTime BIGINT,
    expireTime BIGINT,
    unban INT
);

-- ipbans
CREATE TABLE ipbans (
    id INT AUTO_INCREMENT,
    ip VARCHAR(32),
    reason VARCHAR(255),
    admin VARCHAR(255),
    createTime BIGINT,
    expireTime BIGINT,
    unban INT
);

-- whitelisted
CREATE TABLE whitelisted (
    id INT AUTO_INCREMENT,
    uuid VARCHAR(255)
);
```

## 🚀 Startup Sequence

```java
// Core.java onEnable()
1. Create MySQL tables (8 tables)
2. Implement managers (19 managers)
3. Setup storages (configs)
4. Load data from MySQL
   - Ranks
   - Kits
   - Text commands
   - Users
   - Bans
   - Guilds
   - Achievements
5. Register listeners (20+ listeners)
6. Register commands (50+ commands)
7. Register tasks (7 async tasks)
8. Register BungeeCord channel
```

## 🎯 Key Features

**PvP Systems:**
- ✅ Combat tag (30s)
- ✅ Killstreaks
- ✅ Assists tracking
- ✅ Points system
- ✅ Death backups

**Guild Systems:**
- ✅ Cuboid protection
- ✅ Member permissions
- ✅ Alliance system
- ✅ Guild deposit (treasury)
- ✅ Lives system (3 lives)
- ✅ Expire time (7 days)
- ✅ Guild enlarge/extend
- ✅ Guild PvP toggle

**Economy:**
- ✅ Coins system
- ✅ Shop GUI
- ✅ Deposit system (kox, ref, pearl)

**Events:**
- ✅ TurboDrop events
- ✅ TurboEXP events
- ✅ Time-based multipliers

**Player Features:**
- ✅ Level & EXP system
- ✅ Achievement system
- ✅ Player classes (TANK, ARCHER, etc)
- ✅ Custom drop config per player
- ✅ Enderchest storage
- ✅ Home system

**Admin Tools:**
- ✅ Ban/TempBan system
- ✅ IP bans
- ✅ Whitelist
- ✅ Backup system
- ✅ Vanish mode
- ✅ God mode
- ✅ Fly mode
- ✅ Inventory backups

## 📝 Example Commands

```bash
# Guild
/guild create ABC MojaGildia
/guild invite Player123
/guild home
/guild deposit 1000
/guild enlarge

# User
/spawn
/home
/kit <name>
/top [kills/deaths/points]
/deposit [kox/ref/pearl]
/shop

# Admin
/ban Player123 Cheating
/tempban Player123 7d Spam
/backup create Player123
/event start turbodrop 1h 2
/ranking set Player123 kills 100
```

## 🔧 Konfiguracja

### CoreConfig.java
- Toggle dla features (drop, exp, events)
- Cooldowns
- Limits

### GuildConfig.java
```yaml
GUILD_MEMBERS_LIMIT_START: 10
CUBOID_SIZE_START: 50
GUILD_CREATE_COST: 1000
```

### BackupConfig.java
```yaml
BACKUP_AUTOMATIC_DELAY: 300  # 5 minut
```

## 🐛 Known Issues

- "Very old core" - legacy code
- Brak optymalizacji MySQL queries
- Synchroniczne update() calls
- Brak connection pooling
- Hardcoded wartości w kodzie

## 👨‍💻 Autor

**Mateusz (CzarnaWoda / BlackWater)**
- Package: `pl.blackwater.hardcore`
- Projekt: **ProjectHardcore** / **CoreSV**
- Period: ~2018-2020 (estimate based on "very old")

---
