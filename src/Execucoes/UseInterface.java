package Execucoes;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import CamadaDeXadrez.Cor;
import CamadaDeXadrez.PartidaDeXadrez;
import CamadaDeXadrez.PecaDeXadrez;
import CamadaDeXadrez.PosicaoXadrez;

public class UseInterface {
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner entrada) {
		try {
			String sc = entrada.nextLine();
			char coluna = sc.charAt(0);
			int linha = Integer.parseInt(sc.substring(1));
			return new PosicaoXadrez(coluna, linha);

		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao digitar a posi��o. Valores v�lidos de 1 a 8.");
		}
	}

	public static void imprimirPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capturadas) {
		imprimirTabuleiro(partidaDeXadrez.getPeca());
		System.out.println();
		imprimirPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partidaDeXadrez.getTurno());
		System.out.println("Esperando jogador: " + partidaDeXadrez.getJogadorAtual());
		if(partidaDeXadrez.getCheck()) {
			System.out.println("CHECK!");
		}
	}

	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void imprimirPeca(PecaDeXadrez peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void imprimirPecasCapturadas(List<PecaDeXadrez> capturadas) {
		List<PecaDeXadrez> branco = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaDeXadrez> preto = capturadas.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Pecas capturadas: ");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Preto: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
	}
}
