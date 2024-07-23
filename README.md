# Toy-PVZ

A toy PVZ as course homework of Java.

## Introduction

Our project is a toy PVZ game named `Toy-PVZ`.

It's inspired by the official **Plants vs. Zombies** and we turn this classic game into a light version, which is implemented in about 2000 lines of code.

For developers:
* The game is implemented in `Java(jdk 1.8)` and `JavaFX`.
* Since the code is entirely written by ourselves, it may be not sophisticated enough but easy to realize.
* Our frame is easy to extend and modify, so you can add new plants, zombies, strategies, etc. to the game or modify the existing ones to make the game more interesting.

For players:
* Most of the game rules are the same as the official version, so you can get familiar with the game quickly.
* There are some differences between our game and the official version, some are for simplification and some are for fun.
* We don't promise that our game is bug-free, but we will try our best to fix them.

Screenshot of the game:
![gameview](gameview.png)

Video of the game: We will upload it later.

## Modules

In this section, we just introduce the modules of our code for kind TA.
* `database`: store the data of players, it's implemented by `MySQL` and `jdbc` locally.
* `game`: the core module of the game, it contains the game logic and the game view.
* `menu`: main menu, setting menu and choice menu.
* `node`: you can easily design you node by extending abstract classes.
  * `plant`
  * `zombie`
  * `bullet`
* `strategy`: the design of different levels(you can consider `Test.java` as a template).
* `util`: utils of the project.

## Branches

Till now, we have two branches:
* `main`: the main branch, access all the parts of the project.
* `develop`: without database ops, for developers to test even if they don't have a database.

## Problems

There are several problems, which need to solve.
* As the animation is implemented by `GIF`, not image by image, we can't handle animation flexibly. So the animation is not great enough(without squash attack, cherrybomb boom), but zombie attack exists.
* For simplification, sunshine is not dropping.
