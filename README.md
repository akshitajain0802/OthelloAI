🎮 Othello AI (Minimax + Alpha-Beta Pruning + Heuristic Evaluation)

A fully functional console-based Othello/Reversi game written in Java, featuring:

Human vs AI mode

AI vs AI mode

Minimax search with Alpha-Beta pruning

Heuristic evaluation function (mobility, corners, piece advantage)

Clean, readable, object-oriented code

This project is ideal for learning:

Game Theory

Minimax algorithm

Alpha-Beta pruning

Heuristic design

Board evaluation

Turn-based AI decision making

📌 Features

✔ 8×8 Othello board
✔ Human Player or AI Player
✔ Configurable AI search depth
✔ Minimax + Alpha-Beta pruning
✔ Heuristic scoring:

Corner control

Mobility (valid moves)

Piece difference

✔ Automatic game-over detection
✔ Complete flipping logic in all 8 directions
✔ Clean separation of Board, Player, Move & AI components

🧠 How the AI Works
🔹 Minimax Algorithm

The AI simulates future game states and picks the move that maximizes its guaranteed score.

🔹 Alpha-Beta Pruning

Cuts off branches that cannot influence the final decision → greatly improves performance.

🔹 Heuristic Evaluation Function

Used when search depth is reached:

score =
    1 * pieceDifference
  + 5 * mobility
  + 25 * cornerControl


Corner control is highest priority

Mobility ensures flexibility

Piece difference shows board advantage

This combination makes the AI competitive and smart.

📁 Project Structure
OthelloAI/
│
├── othello.java        # Main game (Board + Player + AI + Logic)
└── README.md           # Project documentation

▶ How to Run
Compile:
javac othello.java

Run:
java othello

🎮 Game Modes

After launching the program:

Choose mode:
1) Human vs AI
2) AI vs AI

🧑 Human vs 👾 AI

You play as BLACK (X)

AI plays WHITE (O)

Choose AI depth (recommended 3–5)

🤖 vs 🤖 AI vs AI

Watch two AIs compete—useful for analysis and evaluation.

🖥 Sample Output
Your valid moves: [(2,3), (3,2), (4,5), (5,4)]
Enter r c:


AI example:

AI chooses: (2,4) eval=132

📊 Board Representation
  0 1 2 3 4 5 6 7
0 . . . . . . . .
1 . . . . . . . .
2 . . . . . . . .
3 . . . O X . . .
4 . . . X O . . .
5 . . . . . . . .
6 . . . . . . . .
7 . . . . . . . .

Score (Black - White): 0


Symbols:

X → BLACK

O → WHITE

. → Empty slot

💡 Algorithms Used
Component	Technique
Game Tree Search	Minimax
Optimization	Alpha-Beta Pruning
Scoring Method	Heuristic Evaluation
Player Types	Human + AI
Board Mechanics	8-direction flipping
📜 License

MIT License

👩‍💻 Author

Akshita Jain
GitHub: https://github.com/akshitajain0802
