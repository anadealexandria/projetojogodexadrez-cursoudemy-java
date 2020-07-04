package CamadaDeTabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca pecas[][];

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcecaoTabuleiro(
					"Erro ao criar um tabuleiro: � necess�rio que aja, pelo menos, uma linha" + " e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new ExcecaoTabuleiro("Posi��o n�o existe no tabuleiro!");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Posi��o n�o existe no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void lugarPeca(Peca peca, Posicao posicao) {
		if (existeUmaPeca(posicao)) {
			throw new ExcecaoTabuleiro("J� existe uma pe�a na posi��o: " + posicao);

		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}

	public boolean existeUmaPeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Essa posi��o n�o existe!");
		}
		return peca(posicao) != null;
	}

}
