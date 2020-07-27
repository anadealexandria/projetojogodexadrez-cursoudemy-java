package Execucoes;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import CamadaDeXadrez.ExcecaoXadrez;
import CamadaDeXadrez.PartidaDeXadrez;
import CamadaDeXadrez.PecaDeXadrez;
import CamadaDeXadrez.PosicaoXadrez;

public class Principal {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturadas = new ArrayList<>();
		
		while (!partidaDeXadrez.getCheckMate()) {
			
			try {
			
			UseInterface.limparTela();
			UseInterface.imprimirPartida(partidaDeXadrez, capturadas);
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UseInterface.lerPosicaoXadrez(entrada);
			
			boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
			UseInterface.limparTela();
			UseInterface.imprimirTabuleiro(partidaDeXadrez.getPeca(), movimentosPossiveis);
			System.out.println();
			System.out.println("Destino: ");
			PosicaoXadrez destino = UseInterface.lerPosicaoXadrez(entrada);	
			
			PecaDeXadrez pecaCapturada = partidaDeXadrez.executarMovimento(origem, destino);
			
			if(pecaCapturada != null) {
				capturadas.add(pecaCapturada);
			}
			if(partidaDeXadrez.getPromocao() != null) {
				System.out.print("Digite a peca para a promocao (B/Q/T/C):");
				String type = entrada.nextLine();
				partidaDeXadrez.substituirPecaPromovida(type);
			}
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
		UseInterface.limparTela();
		UseInterface.imprimirPartida(partidaDeXadrez, capturadas);

	} 

}
