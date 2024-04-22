package Jogo;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int TAMANHO_TABULEIRO = 10;
    private static final String[][] tabuleiroJogador = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final String[][] tabuleiroComputador = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final String[][] tabuleiroCensurado = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];

    private static final int[] TAMANHOS_NAVIOS = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        int modoJogo;

        System.out.println("Bem-vindo ao jogo Batalha Naval!");
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Singleplayer (Jogador vs Computador)");
        System.out.println("2 - Multiplayer (Jogador vs Jogador)");
        modoJogo = ler.nextInt();

        // Inicializa os tabuleiros
        inicializarTabuleiros();

        // Posiciona os navios
        posicionarNavios(tabuleiroJogador);
        posicionarNavios(tabuleiroComputador);

        switch (modoJogo) {
            case 1:
                // Jogador vs Computador (Singleplayer)
                jogarSingleplayer();
                break;
            case 2:
                // Jogador vs Jogador (Multiplayer)
                jogarMultiplayer();
                break;
            default:
                System.out.println("Modo de jogo inválido. Encerrando...");
        }
    }

    private static void jogarSingleplayer() {
        Scanner ler = new Scanner(System.in);
        int qntdBarcosJogador = 0;
        int qntdBarcosComputador = 0;

        while (!jogoAcabou(tabuleiroJogador, tabuleiroComputador)) {
            // Mostra o tabuleiro do jogador
            mostrarTabuleiro(tabuleiroJogador);

            // Mostra o tabuleiro censurado do computador
            mostrarTabuleiroCensurado(tabuleiroCensurado);

            // Faz a jogada do jogador e atualiza a quantidade de barcos do jogador
            qntdBarcosJogador = fazerJogada(tabuleiroComputador, tabuleiroCensurado, qntdBarcosJogador);

            // Verifica se o jogador venceu após sua jogada
            if (todosOsNaviosForamAfundados(tabuleiroComputador)) {
                System.out.println("Parabéns! Você venceu o jogo de Batalha Naval!");
                return;
            }

            // Faz a jogada do computador e atualiza a quantidade de barcos do computador
            qntdBarcosComputador = fazerJogadaComputador(tabuleiroJogador, qntdBarcosComputador);

            // Verifica se o computador venceu após sua jogada
            if (todosOsNaviosForamAfundados(tabuleiroJogador)) {
                System.out.println("Você perdeu! Todos os seus navios foram afundados.");
                return;
            }
        }
    }

    private static void jogarMultiplayer() {
        Scanner ler = new Scanner(System.in);
        int qntdBarcosJogador1 = 0;
        int qntdBarcosJogador2 = 0;

        while (!jogoAcabou(tabuleiroJogador, tabuleiroComputador)) {
            // Mostra o tabuleiro do jogador 1
            System.out.println("Jogador 1:");
            mostrarTabuleiro(tabuleiroJogador);

            // Faz a jogada do jogador 1 e atualiza a quantidade de barcos do jogador 1
            qntdBarcosJogador1 = fazerJogada(tabuleiroComputador, tabuleiroCensurado, qntdBarcosJogador1);

            // Verifica se o jogador 1 venceu após sua jogada
            if (todosOsNaviosForamAfundados(tabuleiroComputador)) {
                System.out.println("Jogador 1 venceu!");
                return;
            }

            // Mostra o tabuleiro do jogador 2
            System.out.println("Jogador 2:");
            mostrarTabuleiro(tabuleiroJogador);

            // Faz a jogada do jogador 2 e atualiza a quantidade de barcos do jogador 2
            qntdBarcosJogador2 = fazerJogada(tabuleiroJogador, tabuleiroCensurado, qntdBarcosJogador2);

            // Verifica se o jogador 2 venceu após sua jogada
            if (todosOsNaviosForamAfundados(tabuleiroJogador)) {
                System.out.println("Jogador 2 venceu!");
                return;
            }
        }
    }

    private static void inicializarTabuleiros() {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabuleiroJogador[i][j] = ".";
                tabuleiroComputador[i][j] = ".";
                tabuleiroCensurado[i][j] = ".";
            }
        }
    }

    private static void posicionarNavios(String[][] tabuleiro) {
        Random random = new Random();
        for (int tamanho : TAMANHOS_NAVIOS) {
            boolean posicaoValida;
            do {
                int linha = random.nextInt(TAMANHO_TABULEIRO);
                int coluna = random.nextInt(TAMANHO_TABULEIRO);
                boolean horizontal = random.nextBoolean();
                posicaoValida = validarPosicao(tabuleiro, linha, coluna, tamanho, horizontal);
                if (posicaoValida) {
                    marcarNavio(tabuleiro, linha, coluna, tamanho, horizontal);
                }
            } while (!posicaoValida);
        }
    }

    private static boolean validarPosicao(String[][] tabuleiro, int linha, int coluna, int tamanho, boolean horizontal) {
        if (horizontal && coluna + tamanho > TAMANHO_TABULEIRO) {
            return false;
        }
        if (!horizontal && linha + tamanho > TAMANHO_TABULEIRO) {
            return false;
        }
        for (int i = 0; i < tamanho; i++) {
            if (horizontal && !tabuleiro[linha][coluna + i].equals(".")) {
                return false;
            }
            if (!horizontal && !tabuleiro[linha + i][coluna].equals(".")) {
                return false;
            }
        }
        return true;
    }

    private static void marcarNavio(String[][] tabuleiro, int linha, int coluna, int tamanho, boolean horizontal) {
        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                tabuleiro[linha][coluna + i] = "X";
            } else {
                tabuleiro[linha + i][coluna] = "X";
            }
        }
    }

    private static void mostrarTabuleiro(String[][] tabuleiro) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letra = 'A';
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.print(letra + " ");
            letra++;
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void mostrarTabuleiroCensurado(String[][] tabuleiro) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letra = 'A';
        for (String[] linha : tabuleiro) {
            System.out.print(letra + " ");
            letra++;
            for (String celula : linha) {
                if (celula.equals(".")) {
                    System.out.print(". ");
                } else if (celula.equals("X")) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    private static int fazerJogada(String[][] tabuleiro, String[][] tabuleiroCensurado, int qntdBarcosJogador) {
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite a linha para atirar (A-J): ");
        String linhaInput = ler.next().toUpperCase();
        int linha = linhaInput.charAt(0) - 'A';
        System.out.println("Digite a coluna para atirar (1-10): ");
        int coluna = ler.nextInt() - 1;
        if (linha < 0 || linha >= TAMANHO_TABULEIRO || coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
            System.out.println("Posição inválida!");
            return qntdBarcosJogador;
        }
        if (!tabuleiro[linha][coluna].equals(".")) {
            System.out.println("Você acertou um navio!");
            tabuleiro[linha][coluna] = "X";
            tabuleiroCensurado[linha][coluna] = "X";
            qntdBarcosJogador++;
        } else {
            System.out.println("Você errou o tiro!");
            if (!tabuleiroCensurado[linha][coluna].equals("X")) {
                tabuleiroCensurado[linha][coluna] = "O";
            }
        }
        return qntdBarcosJogador;
    }

    private static int fazerJogadaComputador(String[][] tabuleiro, int qntdBarcosComputador) {
        Random random = new Random();
        int linha;
        int coluna;

        do {
            linha = random.nextInt(TAMANHO_TABULEIRO);
            coluna = random.nextInt(TAMANHO_TABULEIRO);
        } while (!tabuleiro[linha][coluna].equals("."));
        if (!tabuleiro[linha][coluna].equals(".")) {
            System.out.println("O computador acertou um navio na posição " + (char) ('A' + linha) + (coluna + 1) + "!");
            tabuleiro[linha][coluna] = "X";
        } else {
            System.out.println("O computador errou o tiro!");
            tabuleiro[linha][coluna] = "O";
        }
        return qntdBarcosComputador;
    }

    private static boolean jogoAcabou(String[][] tabuleiro1, String[][] tabuleiro2) {
        return todosOsNaviosForamAfundados(tabuleiro1) || todosOsNaviosForamAfundados(tabuleiro2);
    }

    private static boolean todosOsNaviosForamAfundados(String[][] tabuleiro) {
        for (String[] linha : tabuleiro) {
            for (String celula : linha) {
                if (celula.equals("X")) {
                    return false;
                }
            }
        }
        return true;
    }
}
