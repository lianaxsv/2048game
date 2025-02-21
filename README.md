<h1>Description</h1>
This is a remake of the popular 2048 game!

<h1>Programming Languages Used</h1>
For this project, I used Java and Java Swing for the UI

<h1>Core Concepts Used</h1>

  1. <b>Collections:<b>
  I implemented utilizing collections by making an "undo" button that allows the user to undo previous
   moves (unlimited). The undoStack in the GameBoard class is implemented using a Deque (specifically, an ArrayDeque)
   to keep track of game states. The GameState class, which holds the state of the game, includes a 2D array of Tile
   objects and an integer for the score. Deque is used to maintain a stack-like structure, which is essential for
   implementing the undo feature in the game. The use of a custom GameState class allows combining the game board state
   and the score together, providing a convenient way to push and pop complete game states onto and from the undo stack.

  3. 2D Arrays: The Tile class uses a 2D array to represent the game board. Each element of this array is an instance
  of the Tile class. The GameBoard class also uses a 2D array to represent the playing field. The 2D array is a best
  fit for representing the game board as it aligns with the grid-based structure of the 2048 game. It allows easy
  access to individual tiles using row and column indices, making it convenient for implementing game logic.

  4. File I/O: I initially wanted to just do a high score saving using File I/O, but was given feedback by TAs against
  doing so, so, I created a GameFileManager class that utilizes File I/O for saving and loading game states. The game s
  tate is saved in a file named "game_state.ser" using ObjectOutputStream. The saved game state is loaded using
  ObjectInputStream. File I/O is crucial for persisting the game state between sessions. The serialization of the
  GameBoard object allows saving the entire state in a file. The use of ObjectOutputStream and ObjectInputStream
  makes the process of saving and loading game states easier.

  5. JUnit: I implemented JUnit testing into my game code and added tests  for the Tile, GameBoard, and GameCourt
  classes. The tests cover a wide range of functionalities, including tile operations, game board state, spawning,
  resetting, saving, loading, undoing, and game over conditions. In doing so, I can make sure the mechanics
  are functioning properly within my game.


<h1>Classes</h1>

  My code has several classes to implement the 2048 game. The Tile class represents individual tiles on the game board,
  containing values (2,4,8,16, etc.) and colors that correspond to the values. GameBoard class manages the game state,
  handling logic for movements, spawning tiles, and game over conditions. Run2048 is the main class for GUI setup and
  game initiation,  utilizing GameCourt as the main playing area. Additional classes like GameState manage states,
  and GameFileManager handles saving and loading game states


<h1>Issues</h1>
  I had a lot of issues with implementing the File I/O component. Integrating the save and load functionality was super
   hard, especially in handling serialization and deserialization of complex game states. Making sure that the flow of
   data between the game board and the file manager was correct was tedious too. Debugging also involved fixed
   weird behaviors during the read and write processes. Also, when I was implementing my buttons, they would stay
   pressed down for some reason, and would not let the player continue playing the game after they were pressed. To
   fix this, I had to use the court.requestFocusInWindow(); to prevent this from happening again. This took some
   research to figure out how to implement.


<h1>External Resources</h1>

Game Tutorials/Code Help:

* https://www.jetbrains.com/help/idea/add-items-to-project.html
* https://techvidvan.com/tutorials/java-2048-game/
* https://www.stefanonsoftware.com/post/gamedev-keyboard-input
* https://www.cs.princeton.edu/courses/archive/fall20/cos226/assignments/2048/index.html

Images and Color Palettes:
* https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F771804454887166592%2F&psig=AOvVaw1iEdOMO7
oUa9S-cPrbeDEE&ust=1702350281090000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKDd85WzhoMDFQAAAAAdAAAAABAD
* https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F831125306209247598%2F&psig=AOvVaw1iEdOMO7o
Ua9S-cPrbeDEE&ust=1702350281090000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKDd85WzhoMDFQAAAAAdAAAAABAI
* https://www.color-hex.com/color-palettes/?page=2
