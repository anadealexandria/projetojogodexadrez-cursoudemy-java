package Execucoes;
import java.util.Scanner;

import CamadaDeXadrez.PartidaDeXadrez;
import CamadaDeXadrez.PecaDeXadrez;
import CamadaDeXadrez.PosicaoXadrez;

public class Principal {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			
			UseInterface.imprimirTabuleiro(partidaDeXadrez.getPeca());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UseInterface.lerPosicaoXadrez(entrada);
			
			System.out.println();
			System.out.println("Destino: ");
			PosicaoXadrez destino = UseInterface.lerPosicaoXadrez(entrada);
			
			PecaDeXadrez capturarPeca = partidaDeXadrez.executarMovimento(origem, destino);
		}

	}

}
