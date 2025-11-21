# 🎮 Othello AI (Minimax + Alpha‑Beta Pruning + Heuristic Evaluation)

**A simple, console-based Othello / Reversi implementation in Java** with a configurable AI that uses Minimax search + Alpha‑Beta pruning and a lightweight heuristic.

---

## Overview

This project implements an 8×8 Othello board, full flipping logic in all 8 directions, game-over detection, and two player types:

* **Human** (console input)
* **AI** (Minimax with Alpha‑Beta pruning)

The AI's evaluation function combines corner control, mobility (number of valid moves), and piece difference to select competitive moves while keeping search times reasonable.

---

## ✅ Features

* 8×8 Othello board
* Human vs AI and AI vs AI modes
* Configurable AI search depth (recommended 3–5)
* Minimax search with Alpha‑Beta pruning
* Heuristic evaluation combining:

  * Corner control
  * Mobility (valid moves)
  * Piece difference
* Automatic game-over detection
* Full flipping logic in 8 directions
* Clean separation of `Board`, `Player`, `Move` & `AI` components (single-file implementation for quick usage)

---

## 🧠 How the AI Works (short)

* **Minimax**: Simulates possible moves to a configured depth and assumes both players play optimally.
* **Alpha‑Beta pruning**: Prunes branches that cannot affect the final decision to reduce search time.
* **Heuristic** (used when depth limit reached):

```
score = 1 * pieceDifference
      + 5 * mobility
      + 25 * cornerControl
```

Corner control has the highest weight because corners are extremely valuable in Othello. Mobility encourages flexible positions; piece difference represents material advantage.

---

## 📁 Project Structure

```
OthelloAI/
│
├── othello.java        # Main game (Board + Player + AI + Logic)
└── README.md           # Project documentation
```

> This repository ships as a single-file Java program (`othello.java`) for easy compilation and execution. If you prefer, you can refactor into multiple classes/files.

---

## ▶ How to Run

1. **Compile**

```bash
javac othello.java
```

2. **Run**

```bash
java othello
```

When launched you'll be prompted to choose a mode and, for AI players, an AI search depth.

---

## 🕹️ Game Modes

After launching the program you'll see a prompt like:

```
Choose mode:
1) Human vs AI
2) AI vs AI
```

* **Human vs AI** — You play as **BLACK (X)**, AI plays **WHITE (O)**. Pick AI depth (recommended 3–5).
* **AI vs AI** — Watch two AIs play against each other. Useful for tuning the heuristic and depth.

---

## 🖥️ Sample Console Output

```
Your valid moves: [(2,3), (3,2), (4,5), (5,4)]
Enter r c:

AI chooses: (2,4) eval=132

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
```

Symbols:

* `X` → BLACK (human)
* `O` → WHITE (AI)
* `.` → empty slot

---

## 💡 Implementation Notes

* The `Board` class handles board state, move generation, and flipping logic in 8 directions.
* The `AI` implements recursive Minimax with Alpha‑Beta pruning and returns both the chosen move and the heuristic evaluation value for logging.
* The heuristic weights (1, 5, 25) are intentionally simple — good starting point for experimentation.
* The program performs shallow-to-medium depth searches (e.g., depth 3–5) to keep runtime responsive in a single-threaded console app.

---

## ✨ Improvements / Extensions

If you want to expand the project later, consider:

* Refactoring into multiple classes/files (e.g., `Board.java`, `AI.java`, `Player.java`) for maintainability
* Adding iterative deepening and a time limit per move
* Move ordering heuristics (try capturing corners or high‑mobility moves first) to improve pruning
* A simple GUI (Swing/JavaFX) to visualize the game
* Persistent logging of game results for AI evaluation

---

## 🧾 License

This project is released under the **MIT License** — see the `LICENSE` file or include the following in your repo:

```
MIT License

Copyright (c) 2025 Akshita Jain

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

[...standard MIT text...]
```

---

## 👩‍💻 Author

**Akshita Jain**
GitHub: [https://github.com/akshitajain0802](https://github.com/akshitajain0802)

---

## 📬 Contribution

Contributions, issues, and feature requests are welcome. If you'd like to contribute:

1. Fork the repo
2. Create a branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -m "Add feature"`
4. Push to the branch: `git push origin feature-name`
5. Open a pull request

---

If you'd like, I can also:

* Provide a cleaned, single-file `othello.java` ready-to-paste into the repo (compile & run tested here locally), or
* Break the single-file program into multiple Java class files with clearer separation.

Tell me which you'd prefer and I'll add it to the repository content.

