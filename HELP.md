# HELP

There are some considerations you should know before you run the project, to avoid some problems.
* We put `.css` files in `target/classes/` directory. When you first compile the project, the `target/` directory we provide sometimes will be deleted and recreated, so you should copy the `.css` files to `target/classes/` directory manually. Their path should be same as we provide.
* we put `.css` files in `target/classes/` directory. If you want to run this project in `VSCode`, you should copy `.css` files to `bin/` directory manually. Their path should be same as we provide.
* As we use `MySQL` as the database in this project, you should install `MySQL` and create a database as we do. There is a easy for you to just run the `develop` branch without database.

Our database is named `pvz_db` and the table is named `info`. The table structure is as follows:
```sql
CREATE TABLE info (
    user_id VARCHAR(40) NOT NULL,
    level INT,
    money INT,
    peashooter TINYINT(1),
    wallnut TINYINT(1),
    sunflower TINYINT(1),
    squash TINYINT(1),
    cherrybomb TINYINT(1),
    jalapeno TINYINT(1),
    melonpult TINYINT(1),
    PRIMARY KEY (user_id)
);
```