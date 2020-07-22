package Pecas;

import CamadaDeTabuleiro.Posicao;
import CamadaDeTabuleiro.Tabuleiro;
import CamadaDeXadrez.Cor;
import CamadaDeXadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		if(getCor()==Cor.BRANCO) {
			p.setValores(posicao.getLinha()-1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()-2, posicao.getColuna());
			Posicao p2=new Posicao(posicao.getLinha()-1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p) && getContagem()==0 &&
					getTabuleiro().posicaoExiste(p2) && !getTabuleiro().existeUmaPeca(p2)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
			if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
			if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			
		}else {
			p.setValores(posicao.getLinha()+1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()+2, posicao.getColuna());
			Posicao p2=new Posicao(posicao.getLinha()+1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p) && getContagem()==0 &&
					getTabuleiro().posicaoExiste(p2) && !getTabuleiro().existeUmaPeca(p2)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
			if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
			p.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
			if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()]=true;
			}
		}
		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}

}
