import java.util.*;

/**
 * OthelloAI.java
 * Simple Othello implementation with Minimax (alpha-beta) AI and heuristic evaluation.
 *
 * Usage:
 *  javac OthelloAI.java
 *  java OthelloAI
 *
 * Controls: Console-based. Follow prompts.
 */

public class othello {

    // Board constants
    static final int EMPTY = 0;
    static final int BLACK = 1;   // AI or player
    static final int WHITE = -1;  // Opponent

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Othello - Console");
        System.out.println("Choose mode: 1) Human vs AI  2) AI vs AI");
        int mode = sc.nextInt();

        Board board = new Board();
        board.initStart();

        Player blackPlayer, whitePlayer;
        if (mode == 1) {
            System.out.println("You play as BLACK (X) and go first. AI is WHITE (O). Depth? (e.g., 4)");
            int depth = sc.nextInt();
            blackPlayer = new HumanPlayer(BLACK, sc);
            whitePlayer = new MinimaxPlayer(WHITE, depth);
        } else {
            System.out.println("AI vs AI. Enter depth for AI (both):");
            int depth = sc.nextInt();
            blackPlayer = new MinimaxPlayer(BLACK, depth);
            whitePlayer = new MinimaxPlayer(WHITE, depth);
        }

        int current = BLACK;
        while (true) {
            board.printBoard();
            List<Move> moves = board.getValidMoves(current);
            if (moves.isEmpty()) {
                if (board.getValidMoves(-current).isEmpty()) {
                    // Game over
                    int score = board.getScore();
                    System.out.println("Game over. Final score (Black - White): " + score);
                    if (score > 0) System.out.println("Black wins!");
                    else if (score < 0) System.out.println("White wins!");
                    else System.out.println("Draw!");
                    break;
                } else {
                    System.out.println((current == BLACK ? "Black" : "White") + " has no moves. Skipping turn.");
                    current = -current;
                    continue;
                }
            }
            Player player = (current == BLACK ? blackPlayer : whitePlayer);
            Move chosen = player.chooseMove(board);
            if (chosen == null) {
                // should not happen, but just in case
                System.out.println("No move chosen by player, skipping.");
                current = -current;
                continue;
            }
            board.applyMove(current, chosen);
            current = -current;
        }
        sc.close();
    }

    // ----- Move representation -----
    static class Move {
        int r, c;
        Move(int r, int c) { this.r = r; this.c = c; }
        public String toString() { return "(" + r + "," + c + ")"; }
    }

    // ----- Board class -----
    static class Board {
        int[][] b = new int[8][8];

        void initStart() {
            for (int i=0;i<8;i++) for (int j=0;j<8;j++) b[i][j]=EMPTY;
            b[3][3] = WHITE; b[3][4] = BLACK;
            b[4][3] = BLACK; b[4][4] = WHITE;
        }

        Board copy() {
            Board nb = new Board();
            for (int i=0;i<8;i++) System.arraycopy(this.b[i], 0, nb.b[i], 0, 8);
            return nb;
        }

        void printBoard() {
            System.out.println("  0 1 2 3 4 5 6 7");
            for (int i=0;i<8;i++){
                System.out.print(i + " ");
                for (int j=0;j<8;j++){
                    char ch = (b[i][j]==BLACK ? 'X' : (b[i][j]==WHITE ? 'O' : '.'));
                    System.out.print(ch + " ");
                }
                System.out.println();
            }
            System.out.println("Score (Black - White): " + getScore());
            System.out.println();
        }

        // returns list of valid moves for player (BLACK or WHITE)
        List<Move> getValidMoves(int player) {
            List<Move> moves = new ArrayList<>();
            for (int r=0;r<8;r++){
                for (int c=0;c<8;c++){
                    if (b[r][c] == EMPTY && wouldFlip(player, r, c)) moves.add(new Move(r,c));
                }
            }
            return moves;
        }

        // helper: if placing at (r,c) would flip any pieces
        boolean wouldFlip(int player, int r, int c) {
            for (int dr=-1; dr<=1; dr++){
                for (int dc=-1; dc<=1; dc++){
                    if (dr==0 && dc==0) continue;
                    if (countFlips(player, r, c, dr, dc) > 0) return true;
                }
            }
            return false;
        }

        // counts how many would be flipped in direction (dr,dc)
        int countFlips(int player, int r, int c, int dr, int dc) {
            int i = r + dr, j = c + dc, cnt = 0;
            while (in(i,j) && b[i][j] == -player) { cnt++; i += dr; j += dc; }
            if (cnt > 0 && in(i,j) && b[i][j] == player) return cnt;
            return 0;
        }

        boolean in(int r,int c){ return r>=0 && r<8 && c>=0 && c<8; }

        // apply move (assumes valid)
        void applyMove(int player, Move m) {
            b[m.r][m.c] = player;
            for (int dr=-1; dr<=1; dr++){
                for (int dc=-1; dc<=1; dc++){
                    if (dr==0 && dc==0) continue;
                    int flips = countFlips(player, m.r, m.c, dr, dc);
                    if (flips > 0) {
                        int i = m.r + dr, j = m.c + dc;
                        for (int k=0;k<flips;k++){
                            b[i][j] = player;
                            i += dr; j += dc;
                        }
                    }
                }
            }
        }

        // convenience: get current disc counts
        int getScore() {
            int sum=0;
            for (int i=0;i<8;i++) for (int j=0;j<8;j++) sum += b[i][j];
            return sum; // positive = black ahead
        }

        int countPieces(int player) {
            int cnt=0;
            for (int i=0;i<8;i++) for (int j=0;j<8;j++) if (b[i][j]==player) cnt++;
            return cnt;
        }

        // corners
        int cornerControl(int player) {
            int score = 0;
            int[][] corners = {{0,0},{0,7},{7,0},{7,7}};
            for (int[] c : corners) if (b[c[0]][c[1]] == player) score++;
            return score;
        }

        // mobility
        int mobility(int player) {
            return getValidMoves(player).size();
        }
    }

    // ----- Player interface and implementations -----
    interface Player {
        Move chooseMove(Board board);
    }

    // Human player via console
    static class HumanPlayer implements Player {
        int player;
        Scanner sc;
        HumanPlayer(int player, Scanner sc) { this.player = player; this.sc = sc; }
        public Move chooseMove(Board board) {
            List<Move> moves = board.getValidMoves(player);
            if (moves.isEmpty()) return null;
            System.out.println("Your valid moves: " + moves);
            System.out.println("Enter r c:");
            while (true) {
                int r = sc.nextInt(), c = sc.nextInt();
                for (Move m : moves) if (m.r==r && m.c==c) return m;
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    // Minimax AI player (with alpha-beta pruning)
    static class MinimaxPlayer implements Player {
        int player;
        int maxDepth;
        MinimaxPlayer(int player, int depth) { this.player = player; this.maxDepth = depth; }
        public Move chooseMove(Board board) {
            List<Move> moves = board.getValidMoves(player);
            if (moves.isEmpty()) return null;
            Move best = null;
            int bestVal = Integer.MIN_VALUE;
            for (Move m : moves) {
                Board nb = board.copy();
                nb.applyMove(player, m);
                int val = minimax(nb, maxDepth-1, -player, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (val > bestVal) { bestVal = val; best = m; }
            }
            System.out.println("AI chooses: " + best + " eval=" + bestVal);
            return best;
        }

        // minimax with alpha-beta
        int minimax(Board board, int depth, int curPlayer, int alpha, int beta) {
            // terminal or depth 0
            List<Move> moves = board.getValidMoves(curPlayer);
            List<Move> oppMoves = board.getValidMoves(-curPlayer);
            if (moves.isEmpty() && oppMoves.isEmpty()) {
                // terminal: final score from perspective of this.player
                int finalScore = board.getScore(); // black - white
                return (this.player == BLACK) ? finalScore : -finalScore;
            }
            if (depth == 0) {
                return evaluate(board);
            }

            if (curPlayer == player) {
                // maximizing
                int value = Integer.MIN_VALUE;
                if (moves.isEmpty()) {
                    // pass turn
                    value = Math.max(value, minimax(board, depth-1, -curPlayer, alpha, beta));
                    alpha = Math.max(alpha, value);
                    if (alpha >= beta) return value;
                } else {
                    for (Move m : moves) {
                        Board nb = board.copy();
                        nb.applyMove(curPlayer, m);
                        value = Math.max(value, minimax(nb, depth-1, -curPlayer, alpha, beta));
                        alpha = Math.max(alpha, value);
                        if (alpha >= beta) break; // prune
                    }
                }
                return value;
            } else {
                // minimizing opponent
                int value = Integer.MAX_VALUE;
                if (moves.isEmpty()) {
                    value = Math.min(value, minimax(board, depth-1, -curPlayer, alpha, beta));
                    beta = Math.min(beta, value);
                    if (alpha >= beta) return value;
                } else {
                    for (Move m : moves) {
                        Board nb = board.copy();
                        nb.applyMove(curPlayer, m);
                        value = Math.min(value, minimax(nb, depth-1, -curPlayer, alpha, beta));
                        beta = Math.min(beta, value);
                        if (alpha >= beta) break; // prune
                    }
                }
                return value;
            }
        }

        // Heuristic evaluation (from perspective of this.player)
        int evaluate(Board board) {
            int myPieces = board.countPieces(player);
            int oppPieces = board.countPieces(-player);
            int pieceDiff = myPieces - oppPieces; // small weight

            int myMob = board.mobility(player);
            int oppMob = board.mobility(-player);
            int mobility = myMob - oppMob;

            int cornerMy = board.cornerControl(player);
            int cornerOpp = board.cornerControl(-player);
            int corner = cornerMy - cornerOpp;

            // Weighted combination (tuneable)
            int score = 1 * pieceDiff + 5 * mobility + 25 * corner;

            // If black/white perspective mismatch, keep sign consistent
            return score;
        }
    }
}