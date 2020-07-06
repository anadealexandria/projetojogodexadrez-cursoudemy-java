package CamadaDeXadrez;

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
		pecaNoLugarNovo('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        pecaNoLugarNovo('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        pecaNoLugarNovo('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        pecaNoLugarNovo('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        pecaNoLugarNovo('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        pecaNoLugarNovo('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        pecaNoLugarNovo('c', 7, new Torre(tabuleiro, Cor.PRETO));
        pecaNoLugarNovo('c', 8, new Torre(tabuleiro, Cor.PRETO));
        pecaNoLugarNovo('d', 7, new Torre(tabuleiro, Cor.PRETO));
        pecaNoLugarNovo('e', 7, new Torre(tabuleiro, Cor.PRETO));
        pecaNoLugarNovo('e', 8, new Torre(tabuleiro, Cor.PRETO));
        pecaNoLugarNovo('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}
