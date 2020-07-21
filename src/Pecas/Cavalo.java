package Pecas;

import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import CamadaDeXadrez.Cor;
import CamadaDeXadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public boolean[][] movimentoPossivel() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
				
		
		p.setValores(posicao.getLinha()-2, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()-2, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		//
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+2, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+2, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		return mat;
	}

	
	
}
