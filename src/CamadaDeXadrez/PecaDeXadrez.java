package CamadaDeXadrez;

import CamadaDeTabuleiro.Peca;
import CamadaDeTabuleiro.Tabuleiro;

public class PecaDeXadrez extends Peca {

	private Cor cor;
	private int contagem;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}	

}
