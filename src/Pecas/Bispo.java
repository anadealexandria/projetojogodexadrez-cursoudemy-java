package Pecas;

import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import CamadaDeXadrez.Cor;
import CamadaDeXadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//ParaCimaEsquerda
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
			mat[p.getLinha()][p.getColuna()]=true;
			p.setValores(p.getLinha()-1, p.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			}
		
		//ParaCimaDireita
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
			mat[p.getLinha()][p.getColuna()]=true;
			p.setValores(p.getLinha()-1, p.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			}
		
		//ParaBaixoEsquerda
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
			mat[p.getLinha()][p.getColuna()]=true;
			p.setValores(p.getLinha()+1, p.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			}
		
		//ParaBaixoDireita
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
			mat[p.getLinha()][p.getColuna()]=true;
			p.setValores(p.getLinha()+1, p.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			}
				
		return mat;
	}
}
