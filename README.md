# DatabaseManagement
This plugin creates databases for you and gives the api for accesing it. Exclusive for Nukkit.


**Attention!**

This plugin is still in development and it's not usable (yet!).
Only SQLITE3 and MySQL support for now.
Only for Nukkit. (2.0 API)

Thank you for attention and hope to be a good plugin for you.

**Features:**

* You can add whatever name you want to database in config.
* You can use which type of database you want to have.
* You can choose SQLITE3 and MySLQ.
* Everything is ASYNC, so you don't have problems with lagging.

Example config for now *Autogenerated by plugin*:
```
{
  "usedDatabases" : {
    "1" : true,
    "2" : true
  },
  "typeDatabase" : {
    "1" : "SQLITE3",
    "2" : "MySQL"
  },
  "databaseOptions" : {
    "2" : {
      "password" : "password",
      "port" : "3306",
      "adress" : "localhost",
      "username" : "username"
    }
  },
  "poolsize" : {
    "1" : 10,
    "2" : 10
  },
  "messages" : {
    "nycuro.prefix" : "&7[&aDatabaseManagement&7]&r"
  }
}
```

**Info:**
* About `databaseOptions` , is necessary only for MYSQL bases.
* About `1` and `2`, this can be replaced with any string. Those are name of databases.
* If `countDatabases` have value `0`, all databases using that type will be not created.
