package CamadaDeXadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import CamadaDeTabuleiro.Peca;
import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import Pecas.Bispo;
import Pecas.Cavalo;
import Pecas.Peao;
import Pecas.Queen;
import Pecas.Rei;
import Pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez enPassantVuneravel;
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
	
	public PecaDeXadrez getEnPassantVuneravel() {
		return enPassantVuneravel;
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
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(origem);
		p.avancarContagemMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// #Movimento Especial RookPequeno Torre
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez rook = (PecaDeXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(rook, destinoT);
			rook.avancarContagemMovimento();
		}
		// #Movimento Especial RookGrande Torre
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez rook = (PecaDeXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(rook, destinoT);
			rook.avancarContagemMovimento();
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
		
		PecaDeXadrez pecaQMoveu = (PecaDeXadrez)tabuleiro.peca(destino);
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		// #Movimento especial en passant
		if(pecaQMoveu instanceof Peao && (destino.getLinha()==destino.getLinha()-2
				&& (destino.getLinha()==destino.getLinha()+2))) {
			
			enPassantVuneravel=pecaQMoveu;
		}else {
			enPassantVuneravel=null;
		}
		
		return (PecaDeXadrez) capturarPeca;
	}

	private void undoMove(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.recuarContagemMovimento();
		tabuleiro.lugarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		// #Movimento Especial RookPequeno Torre
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez rook = (PecaDeXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.lugarPeca(rook, origemT);
			rook.recuarContagemMovimento();
		}
		// #Movimento Especial RookGrande Torre
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez rook = (PecaDeXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.lugarPeca(rook, origemT);
			rook.recuarContagemMovimento();
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
		pecaNoLugarNovo('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('d', 1, new Queen(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		pecaNoLugarNovo('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		pecaNoLugarNovo('f', 1, new Bispo(tabuleiro, Cor.BRANCO));

		pecaNoLugarNovo('a', 8, new Torre(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('d', 8, new Queen(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('h', 8, new Torre(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
		pecaNoLugarNovo('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		pecaNoLugarNovo('f', 8, new Bispo(tabuleiro, Cor.PRETO));
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
