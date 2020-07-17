package CamadaDeXadrez;

import CamadaDeTabuleiro.Peca;
import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

	private Cor cor;
	private int contagem;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}	
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.transformarPosicaoDeXadrez(posicao);
	}
	
	protected boolean existePecaOponente(Posicao posicao) {
		PecaDeXadrez p =(PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
		
	}
	

}
