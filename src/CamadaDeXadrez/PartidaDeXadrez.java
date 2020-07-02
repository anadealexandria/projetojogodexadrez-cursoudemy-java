package CamadaDeXadrez;

import CamadaDeTabuleiro.Tabuleiro;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	
	public PecaDeXadrez[][] getPeca(){
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j]=(PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
}
