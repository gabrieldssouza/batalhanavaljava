package Jogo;

import java.util.Random;
import java.util.Scanner;

public class Main{
    private static final int TAMANHO_TABULEIRO = 10;
    private static final String[][] tabuleiroJogador1 = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final String[][] tabuleiroJogador2 = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final String[][] tabuleiroCensuradoJogador1 = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final String[][] tabuleiroCensuradoJogador2 = new String[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];

    private static final int[] TAMANHOS_NAVIOS = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        int qntdBarcosJogador1 = 0;
        int qntdBarcosJogador2 = 0;

        inicializarTabuleiros(tabuleiroJogador1, tabuleiroJogador2, tabuleiroCensuradoJogador1, tabuleiroCensuradoJogador2);

        posicionarNavios(tabuleiroJogador1);
        posicionarNavios(tabuleiroJogador2);

        int jogadorAtual = 1;

        while (!jogoAcabou()) {
            if (jogadorAtual == 1) {
                mostrarTabuleiro(tabuleiroJogador1);
                mostrarTabuleiroCensurado(tabuleiroCensuradoJogador2);
                qntdBarcosJogador1 = fazerJogada(tabuleiroJogador2, tabuleiroCensuradoJogador2, qntdBarcosJogador1, jogadorAtual);
            } else {
                mostrarTabuleiro(tabuleiroJogador2);
                mostrarTabuleiroCensurado(tabuleiroCensuradoJogador1);
                qntdBarcosJogador2 = fazerJogada(tabuleiroJogador1, tabuleiroCensuradoJogador1, qntdBarcosJogador2, jogadorAtual);
            }

            jogadorAtual = alternarJogador(jogadorAtual);
        }

        if (todosOsNaviosForamAfundados(tabuleiroJogador1)) {
            System.out.println("Jogador 2 venceu! Todos os navios do Jogador 1 foram afundados.");
        } else {
            System.out.println("Jogador 1 venceu! Todos os navios do Jogador 2 foram afundados.");
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
    // ... (other methods remain the same)

    private static void inicializarTabuleiros(String[][] tabuleiroJogador1, String[][] tabuleiroJogador2, String[][] tabuleiroCensuradoJogador1, String[][] tabuleiroCensuradoJogador2) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabuleiroJogador1[i][j] = ".";
                tabuleiroJogador2[i][j] = ".";
                tabuleiroCensuradoJogador1[i][j] = ".";
                tabuleiroCensuradoJogador2[i][j] = ".";
            }
        }
    }

    private static void mostrarTabuleiro(String[][] tabuleiro) {
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
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
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
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
    // ... (other methods remain the same)
    private static void win (int jogadade) {
        if (jogadade == 1) {
            System.out.println("O jogador venceu!");

        } else if (jogadade == 2) {
            System.out.println("A máquina venceu!");
        }
    }
    private static int fazerJogada(String[][] tabuleiro, String[][] tabuleiroCensurado, int qntdBarcosJogador, int jogador) {
        int jogadade = 1;
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite a linha para atirar (A-J): ");
        String linhaInput = ler.next();
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
            System.out.println("FORAM " + qntdBarcosJogador);
        } else {
            System.out.println("Você errou o tiro!");
            if (!tabuleiroCensurado[linha][coluna].equals("X")) {
                tabuleiroCensurado[linha][coluna] = "O";
            }
        }

        if (jogador == 1) {
            tabuleiroCensuradoJogador2[linha][coluna] = tabuleiro[linha][coluna];
        } else {
            tabuleiroCensuradoJogador1[linha][coluna] = tabuleiro[linha][coluna];
        }

        if (qntdBarcosJogador == 20){
            win(jogadade);
        }
        return qntdBarcosJogador;

    }

    private static int alternarJogador(int jogadorAtual) {
        return jogadorAtual == 1 ? 2 : 1;
    }


    private static boolean jogoAcabou() {
        return todosOsNaviosForamAfundados(tabuleiroJogador1) || todosOsNaviosForamAfundados(tabuleiroJogador2);
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
    // ... (other methods remain the same)
}