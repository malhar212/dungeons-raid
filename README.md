# Dungeons

## Overview
Dungeons is dungeon game that creates a maze of caves and tunnels and starts off a player at the start cave.\
Player can navigate through the maze collecting any treasures in the caves along the way or picking up arrows.\
Player can shoot the arrows to kill monsters they encounter along the way\
There will always be a monster at the end cave.

## List of Features
1. Player can collect treasure.
2. Player can collect arrows.
3. Players can move in NORTH, SOUTH, EAST, WEST direction if the cave has those directions open.
4. Player can shoot an arrow in the directions that are available from the current location.
5. Player can kill a monster(Otyugh).
6. Player location is visible on a Graphical User Interface.
7. Player can be moved using arrow keys or mouseclick.
8. Arrow can be shot by using Ctrl + arrow key combination.
9. Restart game with same dungeon.
10. Restart game with a new dungeon with same configuration as previously provided.
11. Create new game using GUI.

## How to run
1. Make sure java is installed on the system.
2. Navigate to the directory where the jar file is stored using terminal.
3. Run command `java -jar Project5.jar` or `java -jar Project5.jar true` to start program with a graphical user interface.
4. Or command `java -jar Project5.jar false 5 4 true 5 50 3` to start as text based game.
5. Where command arguments are `java -jar Project5.jar gui rows columns wrapped interconnectivity treasureAndArrowPercentage numberOfMonsters`
6. `gui` whether to start the program with gui enabled or not. If true other parameters are ignored.
7. `rows` integer number of rows of dungeon maze.
8. `columns` integer number of columns of the dungeon maze.
9. `wrapped` boolean for maze wrapped around edges or not.
10. `interconnectivity` integer interconnectivity of the maze.
11. `treasureAndArrowPercentage` integer percentage of caves to be filled with treasure and percentage of all locations to be filled with arrows.
12. `numberOfMonsters` number of monsters in the dungeon. Even if number of monsters passed is 0 there will be a monster at the end.

## How to use Program
The game can be played by following instructions as provided on the screen.\

### Common controls are to enter the corresponding alphabet irrespective of case\
MOVE: M SHOOT: S PICKUP: P QUIT: Q\
You can input N for North, E for East, S for South, W for West\
for the command you want to perform.

### Controls for the Graphical User Interface
You can use arrow keys or mouse for navigation\
'A' to pickup arrows\
'T' to pickup treasure\
'Ctrl + arrow key' to shoot in that direction then enter the distance in the dialog box.

Under Menu there are the following menu options:\
New Game - To create new game.\
Restart same dungeon - Restart the game with same dungeon.\
Restart same config - Restart the game with same dungeon configuration, however paths and start, end locations will change.\
Help - Help on using the game.\
Quit - To exit program. 

### To create a GUI game
To create a new DungeonView:
```
DungeonView view = new DungeonGraphicalView();
```

To create a DungeonController to interact with the game:
```
DungeonGraphicalController guiController = new DungeonSwingController();      
```

To set the view
```
guiController.setView(view);
```

To start the game:
```
guiController.play();
```

The Dungeon interface is the main model of game.\
To create a new Dungeon:
```
Randomizer randomizer = new GameRandomizer();
Dungeon dungeon = new DungeonModel(rows,columns,wrapped,
interconnectivity,treasureAndArrowPercentage,numberOfMonsters, randomizer);
```

To create a DungeonController to interact with the game:
```
DungeonController controller = new DungeonConsoleController(
            new InputStreamReader(System.in), System.out);
```
In the above example we're using System.in as the input source and System.out as the output appendable.

To start the game:
```
controller.play(dungeon);
```
To get startLocation of dungeon:
```
Location startLocation = dungeon.getStartLocation();
```

To get endLocation of dungeon:
```
Location endLocation = dungeon.getEndLocation();
```

To get player current Location of dungeon:
```
Location playerCurrentLocation = dungeon.getPlayerCurrentLocation();
```

Next moves available from current location:
`Set<Move> nextMoves = dungeon.getAvailableMoves();`

To move player:\
`movePlayer(move);`

To check if current location has treasure:
```
Location playerCurrentLocation = dungeon.getPlayerCurrentLocation();
playerCurrentLocation.hasTreasure();
```

To pick treasure at the location\
`dungeon.playerPickTreasure()`

We can use `Player player = dungeon.getPlayerDescription();` to get info about player.
Details of a player can be obtained by:
```
boolean hasTreasure = player.hasTreasure();
Map<Treasure, Integer> treasure = player.getTreasure();
```

##Description of Example
Examples of output

Intro after starting the game
```
Welcome to the dungeons
You can input N for North, E for East, S for South, W for West
You are in a cave,
There are 3 arrows here

You can move in
NORTH: N

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

We enter the alphabet to perform a command

The below example shows 1 ply of moving
```
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
M

Where do we go?
n

You are in a tunnel,
There are 3 arrows here

You can move in
NORTH: N
SOUTH: S

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

Picking up treasure
```
You are in a cave,
There is a very strong rancid smell somewhere near
There is treasure here
You find 4 sapphires
You can move in
NORTH: N
EAST: E
WEST: W

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
p

What to pick? Enter A for arrows or T for treasure
t

You picked up 4 sapphires
You now have the following treasure 4 sapphires
You have 9 arrows left

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

Picking up arrows
```
You are in a tunnel,
There are 3 arrows here

You can move in
NORTH: N
SOUTH: S

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
p

What to pick? Enter A for arrows or T for treasure
a

You have picked up 3 arrows
Player has no treasure
You have 9 arrows left

What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

After you've performed a pick operation there is a player description printed
```
You now have the following treasure 9 sapphires 5 diamonds
You have 11 arrows left
```

Example of shooting arrows
```
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
s

Where do you want to shoot?
w

How far do you want to shoot? (1-5)
1

You hear a painful roar and wild thrashing in the darkness and then silence. It seems you've killed an Otyugh
You have 9 arrows left
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

After a monster is injured
```
You hear a painful roar in the distance. It seems your arrow hit an Otyugh
You have 10 arrows left
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

After a monster is killed
```
You hear a painful roar and wild thrashing in the darkness and then silence. It seems you've killed an Otyugh
You have 9 arrows left
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
```

If your arrow misses
```
Your arrow goes whistling through the dungeon and there's a clunk as it falls to the ground after hitting a cave wall
You have 4 arrows left
What do you want to do? MOVE: M SHOOT: S QUIT: Q
```

Example of killed by a monster
```
What do you want to do? MOVE: M SHOOT: S QUIT: Q
m

Where do we go?
s

You were killed. You died a gruesome death at the hands of the Otyugh
```

Example of winning the game
```
What do you want to do? MOVE: M SHOOT: S PICKUP: P QUIT: Q
m

Where do we go?
n

You have escaped the mines of Moria
You collected these treasures on your journey SAPPHIRES: 9 DIAMONDS: 5
```

### Example of Graphical user interface

New game dialogbox\
![New Game Screenshot](newgame.png)

Start of a new game\
![Start of a new game Screenshot](newstart.png)

After moving\
![Moving Screenshot](movement.png)

Shooting using Ctrl + arrow\
![Shooting Screenshot](askshootingdistance.png)

Before arrow picking\
![Before Arrow pick Screenshot](beforearrowpick.png)

After arrow picking\
![After Arrow pick Screenshot](afterarrowpick.png)

Before treasure picking\
![Before treasure pick Screenshot](beforetreasurepick.png)

After treasure picking\
![After treasure pick Screenshot](aftertreasurepick.png)

After shooting miss\
![shooting miss Screenshot](shootmiss.png)

After shooting injure\
![shooting injure Screenshot](shootinjure.png)

After shooting killed\
![shooting killed Screenshot](shootkill.png)

Faint smell of monster\
![Faint smell Screenshot](faintsmell.png)

Strong smell of monster\
![Strong smell Screenshot](strongsmell.png)

Otyugh monster\
![Otyugh Screenshot](otyugh.png)

Game Lost\
![Game Lost Screenshot](gamelost.png)

Game Win\
![Game Lost Screenshot](gamewin.png)


## Design Changes
The following changes were made to the previous design: 
1. Added Custom implementation of JLabel for implementing dungeon
2. Changed method signature of some methods.
3. Added enum MenuItems for menu items in graphical user interface.
4. Added methods to view to establish communication between View and Controller.
5. Some changes to method name and return types.

## Assumptions
The following assumptions were made:
1. Player is instantiated inside Dungeon model.
2. The Dungeon model creation is responsibility of Controller.
3. On pickup command all items of a type will be picked up.
4. Arrows can only be fired at a distance of 1-5.
5. Even if number of monsters passed is 0 there will be a monster at the end.

## Limitations
1. Players cannot set their player names due to requirement of the project.
2. Next location cannot be fetched without moving to that location.
3. Arrows can only be fired at a distance of 1-5.

## Citations
1. [Random number generation between range](https://www.delftstack.com/howto/java/java-random-number-in-range/)
2. [Running jar file](https://stackoverflow.com/questions/1238145/how-to-run-a-jar-file)
3. [Markdown guide](https://www.markdownguide.org/cheat-sheet/)
4. [Implementing BFS to certain depth](https://stackoverflow.com/questions/10258305/how-to-implement-a-breadth-first-search-to-a-certain-depth)
5. [JScrollable](https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html)
6. [GridBagLayout](https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html)
7. [Getting component on click](https://stackoverflow.com/a/8128729)
8. Image overlay as shown in class.
