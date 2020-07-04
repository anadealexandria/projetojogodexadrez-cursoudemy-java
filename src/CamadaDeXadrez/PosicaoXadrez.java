package CamadaDeXadrez;

import CamadaDeTabuleiro.Posicao;

public class PosicaoXadrez {
	private char coluna;
	private int linha;

	public PosicaoXadrez(char c, int l) {
		if (c < 'a' || c > 'h' || l < 1 || l > 8) {
			throw new ExcecaoXadrez("Posição não está dentro do tabuleiro!");
		}
		this.coluna = c;
		this.linha = l;
	}

	public PosicaoXadrez(int linha, char coluna) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcecaoXadrez("Posição não está dentro do tabuleiro!");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao transformarPosicaoComum() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static PosicaoXadrez transformarPosicaoDeXadrez(Posicao posicao) {
		return new PosicaoXadrez((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
