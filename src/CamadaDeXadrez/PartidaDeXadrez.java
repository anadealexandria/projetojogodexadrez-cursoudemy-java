package CamadaDeXadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import CamadaDeTabuleiro.Peca;
import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import Pecas.Rei;
import Pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		inicio();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaDeXadrez[][] getPeca() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.transformarPosicaoComum();
		validarPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).movimentoPossivel();
	}

	public void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new ExcecaoXadrez("A peca nao pode se mover para a posicao de destino!");
		}
	}

	private Peca makeMove(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;

	}

	public PecaDeXadrez executarMovimento(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.transformarPosicaoComum();
		Posicao destino = posicaoDeDestino.transformarPosicaoComum();
		validarPosicaoDeOrigem(origem);
		validarPosicaoDeDestino(origem, destino);
		Peca capturarPeca = makeMove(origem, destino);

		if (testarCheck(jogadorAtual)) {
			undoMove(origem, destino, capturarPeca);
			throw new ExcecaoXadrez("Voce nao pode estar em check!");
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		return (PecaDeXadrez) capturarPeca;
	}

	private void undoMove(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

	}

	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.existeUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe peca na posicao de origem!");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("Essa peca nao e sua!");
		}
		if (!tabuleiro.peca(posicao).existePeloMenosUmMovimentoPossivel()) {
			throw new ExcecaoXadrez("Nao existe movimento para a peça escolhida!");
		}
	}

	private void pecaNoLugarNovo(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).transformarPosicaoComum());
		pecasNoTabuleiro.add(peca);
	}

	private void inicio() {
		pecaNoLugarNovo('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('e', 1, new Rei(tabuleiro, Cor.BRANCO));
//        pecaNoLugarNovo('e', 2, new Torre(tabuleiro, Cor.BRANCO));
//        pecaNoLugarNovo('e', 1, new Torre(tabuleiro, Cor.BRANCO));
//        pecaNoLugarNovo('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		pecaNoLugarNovo('b', 8, new Torre(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('a', 8, new Rei(tabuleiro, Cor.PRETO));
//        pecaNoLugarNovo('d', 7, new Torre(tabuleiro, Cor.PRETO));
//        pecaNoLugarNovo('e', 7, new Torre(tabuleiro, Cor.PRETO));
//        pecaNoLugarNovo('e', 8, new Torre(tabuleiro, Cor.PRETO));
//        pecaNoLugarNovo('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;

			}
		}
		throw new IllegalStateException("Nao existe peca da cor " + cor + "no tabuleiro");
	}

	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().transformarPosicaoComum();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentoPossivel();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movimentoPossivel();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoXadrez().transformarPosicaoComum();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = makeMove(origem, destino);
						boolean testarCheck = testarCheck(cor);
						undoMove(origem, destino, pecaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
