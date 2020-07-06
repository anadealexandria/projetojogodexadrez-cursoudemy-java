package Execucoes;
import CamadaDeXadrez.PartidaDeXadrez;

public class Principal {

	public static void main(String[] args) {

		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		UseInterface.imprimirTabuleiro(partidaDeXadrez.getPeca());

	}

}
