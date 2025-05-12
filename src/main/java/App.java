import java.util.Scanner;

public final class App {
    // Запрещаем создание экземпляров класса
    private App() {
        throw new UnsupportedOperationException(
                "Utility class should not be instantiated");
    }

    /** Ход игрока. */
    private static final char PLAYER_MARK = 'X';
    /** Ход компьютера. */
    private static final char COMPUTER_MARK = 'O';
    /** Отсутствие победителя. */
    private static final byte NO_WINNER = 0;
    /** Победа игрока. */
    private static final byte PLAYER_WIN = 1;
    /** Победа компьютера. */
    private static final byte COMPUTER_WIN = 2;
    /** Ничья. */
    private static final byte DRAW = 3;
    /** Размер игрового поля. */
    private static final int BOARD_SIZE = 9;
    /** Выигрышные комбинации. */
    private static final int[][] WIN_COMBINATIONS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // горизонтали
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // вертикали
            {0, 4, 8}, {2, 4, 6}              // диагонали
    };
    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(final String[] args) {
        Scanner scan = new Scanner(System.in);

        // Запрос имени игрока
        System.out.println("Enter your name:");
        String userName = scan.nextLine(); // Получаем имя игрока


        char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        byte winner = NO_WINNER;

        System.out.println(
                "Enter box number " + userName + " to select. Enjoy!\n");

        while (true) {
            // Отображение игрового поля
            printBoard(board);

            // Проверка окончания игры
            if (winner != NO_WINNER) {
                switch (winner) {
                    case PLAYER_WIN:
                        System.out.println("You won the game!\nCreated by "
                                + userName + ". Thanks for playing!");
                        break;
                    case COMPUTER_WIN:
                        System.out.println("You lost!\n"
                                + userName + ", Try again!");
                        break;
                    case DRAW:
                        System.out.println("It's a draw!\n"
                                + userName + ", You can do better!");
                        break;
                    default:
                        System.out.println("Hello!\n"
                                + userName + ", Try restarting the game!");
                }
                break;
            }

            // Ход игрока
            int playerMove = getPlayerMove(scan, board);
            board[playerMove] = PLAYER_MARK;

            // Проверка победы игрока
            if (checkWin(board, PLAYER_MARK)) {
                winner = PLAYER_WIN;
                continue;
            }

            // Проверка на ничью
            if (isBoardFull(board)) {
                winner = DRAW;
                continue;
            }

            // Ход компьютера
            int computerMove = getComputerMove(board);
            board[computerMove] = COMPUTER_MARK;

            // Проверка победы компьютера
            if (checkWin(board, COMPUTER_MARK)) {
                winner = COMPUTER_WIN;
            }
        }
    }

    // Вывод игрового поля
    private static void printBoard(final char[] board) {
        System.out.println(
                "\n\n " + board[0] + " | " + board[1] + " | " + board[2] + " ");
        System.out.println(
                "-----------");
        System.out.println(
                " " + board[3] + " | " + board[4] + " | " + board[5] + " ");
        System.out.println(
                "-----------");
        System.out.println(
                " " + board[6] + " | " + board[7] + " | " + board[8] + " \n");
    }

    // Получение хода игрока
    private static int getPlayerMove(final Scanner scan, final char[] board) {
        while (true) {
            /* Сохраняет пользовательскую переменную,
              сохраняя её в byte-значение*/
            byte input = scan.nextByte();
            if (input <= 0 || input > BOARD_SIZE) {
                System.out.println("Invalid input. Enter again.");
            } else {
                int position = input - 1;
                if (board[position]
                        == PLAYER_MARK || board[position] == COMPUTER_MARK) {
                    System.out.println(
                            "That one is already in use. Enter another.");
                } else {
                    return position;
                }
            }
        }
    }

    // Получение хода компьютера
    private static int getComputerMove(final char[] board) {
        while (true) {
            // Случайное значение хода компьютера
            int move = (int) (Math.random() * BOARD_SIZE);
            // Отсеивание заполненных ходов Игрока и Компьютера,
            if (board[move] != PLAYER_MARK && board[move] != COMPUTER_MARK) {
                return move;
            }
        }
    }

    // Проверка заполненности доски
    private static boolean isBoardFull(final char[] board) {
        for (char cell : board) {
            if (cell != PLAYER_MARK && cell != COMPUTER_MARK) {
                return false;
            }
        }
        return true;
    }

    // Проверка на победу
    private static boolean checkWin(final char[] board, final char mark) {
        for (int[] combination : WIN_COMBINATIONS) {
            if (board[combination[0]] == mark
                    && board[combination[1]] == mark
                    && board[combination[2]] == mark) {
                return true;
            }
        }
        return false;
    }
}
