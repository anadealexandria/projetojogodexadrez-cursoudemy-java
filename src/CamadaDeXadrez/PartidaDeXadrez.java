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
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
		
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno=1;
		jogadorAtual=Cor.BRANCO;
		inicio();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean [][] movimentosPossiveis (PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.transformarPosicaoComum();
		validarPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).movimentoPossivel();
	}
	
	public PecaDeXadrez executarMovimento(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.transformarPosicaoComum();
		Posicao destino = posicaoDeDestino.transformarPosicaoComum();
		validarPosicaoDeOrigem(origem);
		validarPosicaoDeDestino(origem, destino);
		Peca capturarPeca = makeMove(origem, destino);
		
		if(testarCheck(jogadorAtual)) {
			undoMove(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode estar em check!");
		}
		proximoTurno();
		return (PecaDeXadrez) capturarPeca;
	}
	
	public void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new ExcecaoXadrez("A peca nao pode se mover para a posicao de destino!");
		}
	}
	
	private Peca makeMove(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
		
	}
	
	private void undoMove(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removerPeca(destino);		
		tabuleiro.lugarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.lugarPeca(p, origem);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
	}
	
	private void validarPosicaoDeOrigem(Posicao posicao) {
		if(!tabuleiro.existeUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe peca na posicao de origem!");
		}
		if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("Essa peca nao e sua!");
		}
		if(!tabuleiro.peca(posicao).existePeloMenosUmMovimentoPossivel()) {
			throw new ExcecaoXadrez("Nao existe movimento para a peça escolhida!");
		}
	}
	private void pecaNoLugarNovo(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).transformarPosicaoComum());
		pecasNoTabuleiro.add(peca);
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
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.BRANCO : Cor.PRETO;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x->((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p: lista) {
			if(p instanceof Rei) {
				return (PecaDeXadrez) p;
				
			}
		}
		throw new IllegalStateException("Nao existe peca da cor " + cor + "no tabuleiro");		
	}
	
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().transformarPosicaoComum();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x->((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentoPossivel();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
}
