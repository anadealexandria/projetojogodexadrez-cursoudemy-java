package Execucoes;
import java.util.InputMismatchException;
import java.util.Scanner;

import CamadaDeXadrez.ExcecaoXadrez;
import CamadaDeXadrez.PartidaDeXadrez;
import CamadaDeXadrez.PecaDeXadrez;
import CamadaDeXadrez.PosicaoXadrez;

public class Principal {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			
			try {
			
			UseInterface.limparTela();
			UseInterface.imprimirTabuleiro(partidaDeXadrez.getPeca());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UseInterface.lerPosicaoXadrez(entrada);
			
			boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
			UseInterface.limparTela();
			UseInterface.imprimirTabuleiro(partidaDeXadrez.getPeca(), movimentosPossiveis);
			System.out.println();
			System.out.println("Destino: ");
			PosicaoXadrez destino = UseInterface.lerPosicaoXadrez(entrada);	
			
			PecaDeXadrez capturarPeca = partidaDeXadrez.executarMovimento(origem, destino);
			}
			catch(ExcecaoXadrez e) {
				System.out.println(e.getMessage());
				entrada.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				entrada.nextLine();
			}
		}

	} 

}
