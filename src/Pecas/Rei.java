package Pecas;

import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import CamadaDeXadrez.Cor;
import CamadaDeXadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);

	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	@Override
	public boolean[][] movimentoPossivel() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
		p.setValores(posicao.getLinha()-1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
				
		//abaixo
		p.setValores(posicao.getLinha()+1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//diagonalDireitaAcima
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//diagonalEsquerdaAcima
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//diagonalDireitaAbaixo
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//diagonalEsquerdaAbaixo
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		return mat;
	}

}
