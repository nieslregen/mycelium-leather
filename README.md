# Everything you need to know or might ask about this project:

## Table of content
- [License](#license)
    - [Why?](#why)
    - [Am I allowed to make money?](#am-i-allowed-to-make-money-with-parts-of-this-project)
    - [Important Notice Regarding AI Training!](#important-notice-regarding-ai-training)
- [Project's Philosophy](#projects-philosophy)
  - [Minecraft's Game Philosophy and its problem](#minecrafts-game-philosophy-and-its-problem)
  - [Does simulating an ecosystem help?](#does-simulating-an-ecosystem-help)
  - [Entity driven decisions](#entity-driven-decisions)
  - [Goal and Mod Design Guideline](#goal-and-mod-design-guideline)
- [Change Log](#change-log)
  - [1.0 Release](#10-release)
  - [1.1 Soot Ink](#11-soot-ink)
  - [1.2 Mobs of the Mushroom Fields](#12-mobs-of-the-mushroom-fields)

  
## License

The source code and all current assets in this repository are licensed under the MIT License.

Please note that this repository may include assets from third-party sources in the future. Such assets will be clearly marked and are **not** covered by the MIT License unless explicitly stated otherwise. Please do not use or redistribute those assets without permission from their respective authors.

### Why?

As the maintainer of this project, I can only license content that I own or have the right to distribute.

Under European (including German) copyright law, creators retain certain inalienable rights to their work. Therefore, I cannot grant you permission to use assets created by others unless I have explicitly been granted the necessary rights to do so.

Any third-party assets included in this repository will always be clearly marked and listed **in this READ.ME** file to avoid accidental misuse.

### Am I allowed to make money with (parts of) this project?

In short: Yes, you are.

The MIT License allows you to use, modify, and commercially distribute the source code and assets covered by this license. However, please keep the following aspects in mind:

- You are **not allowed to commercially distribute third-party assets** that are included in this project but are not covered by the MIT License. Please respect the licenses and rights of their respective creators.
- While you are allowed to commercially use and distribute this project, I encourage you to avoid simply redistributing it without meaningful changes. If you want to build upon this project, consider creating a fork and adding your own ideas, improvements, or creative direction.
- When redistributing this project or parts of it, please keep the original copyright notice and include a copy of the MIT License. You are not required to license your own modifications under MIT, but the original license information **must remain included**.

### Important Notice Regarding AI Training!

If this repository is included in a dataset used for training AI or machine learning models, please ensure that all third-party assets are removed unless you have obtained explicit permission from their respective creators.

The MIT License does not grant any rights to assets that are not covered by it. Unauthorized use of such assets may violate the rights of their respective owners.

**Please be aware that a lack of knowledge regarding the origin or licensing status of an asset does not automatically protect against potential legal consequences!**


## Project's Philosophy
There are many ways to play Minecraft and most (if not all of them) are centered around exploitation and maximization.
Which is indeed a valid and fun way to play this game and that is also what most players expect from Minecraft.

### Minecraft's game philosophy and its problem
Minecraft's current game philosophy explicitly states that only the player is allowed to make meaningful changes and alterations
to the world. In other words, this means that no entity should influence or change the environment directly. 
I believe that this design choice causes two problems:
1. Mobs feel boring and random. They often serve only as food source or visual decoration. Most of them (excluding aggressive mobs) do not interact with the player. They wander without any destination in mind.
2. When a mob's singular purpose is to be farmed then it will probably be seen as a resource rather than a living entity. 

But is there a way how Minecraft can feel more alive and how can mobs bring more value to the game?


### Does simulating an ecosystem help?
There are multiple reasons not to simulate an entire ecological system:
- Simulations are quite complicated and technically difficult to implement and maintain properly.
- simulated systems can be massively disrupted when key features are altered or taken away.
- The player should never be the cause of changes he or she cannot directly observe.

Don't worry, simulating an entire ecosystem just to have a meaningful and wild environment is not necessary here.

### Entity driven decisions
To make Minecraft feel *more alive* entities should not aimlessly roam around and do nothing.
Instead, they should react (and adapt) to their environment. Modern mob design already implements this approach:

For example, foxes hunt chickens, sleep during the day and pick up items when lying around.


### Goal and Mod Design Guideline
The following bullet points might help to make the game more vivid:

1. No essential resource should require killing neutral or friendly mobs; their value should exist beyond killing it.
2. Mobs should have unique traits and behaviors based on their environment.
3. Mobs should interact with the player and/or each other (in unique ways).
4. Neutral mobs should engage in combat only for meaningful reasons, such as survival or defense.

This guideline is not set in stone and rather be seen as design philosophy.

## Change log

### 1.0 Release
- Tool: **Spade**
- Item: **Grass Patch**
- Item: **Mycelium Patch**
- Item: **Dried Mycelium Patch**
- Block: **Herbarium Press**


- Recipe: **Dried Mycelium Patch** (Furnace)


- _Patches (Hot Fixes):_
    - _1.0.1_
        - _Added: Additional recipe to craft **Spade**_
       

### 1.1 Soot Ink
- Item: **Soot**
- Item: **Soot Ink**
- Item: **Suspicious Flask**
- Item: **Arrow of Illness**


- Block: **Tiny Cauldron**
- Block: **Charcoal Pile**


- Recipe (Tiny Cauldron): **Soot** (Tiny Cauldron)
- Recipe (Tiny Cauldron): **Arrow of Illness** (Tiny Cauldron)


- Removed: unintentional recipe for *Mycelium Leather* that could be crafted via *Crafting Table*


- _Patches (Hot Fixes):_
  - _1.1.1_
      - _Fix: Using a **Flint and Steel** to ignite a **Charcoal Pile** now reduces its durability_
      - _Updated: durability of **Spade** now matches the **Iron Shovel**'s durability_
      - _Added: **Tiny Cauldron**, **Herbarium Press** and **Charcoal Pile** can now be crafted with **Pale Wood**_
  - _1.1.2_
    - _Hotfix: Tiny cauldron did not drop the correct item when using the same recipe multiple times in a row_

### 1.2 Mobs of the Mushroom Fields
- Mob: **Mushroom Chicken**
- Mob: **Mushroom Squirrel**
- Mob: **Crawler**


- Vegetation: **Huge Mushroom** with **Hollow** (Red & Brown variants)


- Item (Herb, edible): **Mushroom Paste**


- Item (Cooking Ingredient): **Salt**
- Item (Cooking Ingredient, edible): **Truffle**


- Item (Food, edible): **Feast of the Mushroom Fields**
- Item (Food, edible): **Scrambled Eggs**
- Item (Food, edible): **Pan Fried Potatoes**
- Item (Food, edible): **Baked Bread**


- Item (Tool): **Wooden Spade**


- Block: **Frying Pan**
- Block: **Stove**


- Weapon: **Dagger**
  - **Copper Dagger**
  - **Iron Dagger**
  - **Gold Dagger** (4 different effects)
  - **Diamond Dagger**


- Item (variants): **Feather** (Cold, Warm, Mushroom) 


- Recipe (Frying Pan): **Feast of the Mushroom Fields**
- Recipe (Frying Pan): **Scrambled Eggs**
- Recipe (Frying Pan): **Pan Fried Potatoes**


- Recipe (Furnace): **Baked Bread**


- Recipe: **Moss Block** (crafted from Moss Carpet)


