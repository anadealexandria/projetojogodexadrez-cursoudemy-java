package CamadaDeXadrez;

import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import Pecas.Rei;
import Pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		inicio();
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
	
	private void pecaNoLugarNovo(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).transformarPosicaoComum());
		
	}
	
	private void inicio() {
		pecaNoLugarNovo('b', 6, new Torre (tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('e', 8, new Rei (tabuleiro, Cor.BRANCO));
	}
}
