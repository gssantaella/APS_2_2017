import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class Cena implements Config {

    public static Tile[][] tiledMap;
    public static BufferedImage bg;
    private int faseAtual;

    public Cena() {
        tiledMap = new Tile[LINHAS_TILE][COLUNAS_TILE];
    }
    
    public void initFase(int fase, int cena) {
        
        faseAtual = fase;

        try {
            bg = ImageIO.read(getClass().getResource("img/bg.jpg"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        InputStream is = this.getClass().getResourceAsStream("fases/fase_"+String.valueOf(fase)+"_"+String.valueOf(cena)+".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String l = null;
        String[] tileNaLinha = new String[LINHAS_TILE];

        try {
            int i = 0;
            while((l = reader.readLine()) != null){
                tileNaLinha = l.split(" ");
                for(int j = 0; j < COLUNAS_TILE; j++){
                    if (!tileNaLinha[j].equalsIgnoreCase("0")){
                        tiledMap[i][j] = novaInstanciaTile(tileNaLinha[j], i, j);
                    } 
                    else {
                        tiledMap[i][j] = null;
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Tile novaInstanciaTile(String nome, int i, int j) {
        switch (nome) {
            case "1": //chao
                return new Bloco("bloco", "bloco1", COR_BRANCO, i, j);
            case "2": // agua
                if (faseAtual < 3) return new Agua("agua", "aguasuja1", COR_AGUA_SUJA, i, j);
                else return new Agua("agua", "agua1", COR_AGUA_LIMPA, i, j);
            case "3": //bloco cheio
                return new Bloco("blocomeio", "bloco2", COR_BRANCO,i,j);
            case "4": // agua cheia
                if (faseAtual < 3) return new Agua("agua", "aguasuja2", COR_AGUA_SUJA, i, j);
                else return new Agua("agua", "agua2", COR_AGUA_LIMPA, i, j);
            case "X":
                return new Obstaculo("obs1", "obs1", COR_VERMELHO, i, j);
            case "Z":
                return new Obstaculo("obs2", "obs2", COR_VERMELHO, i, j);
            case "C":
                return new Obstaculo("obs3", "obs3", COR_VERMELHO, i, j);
            case "L":
                return new Colecionavel("col1", "col1", COR_VERDE, i, j);
            case "J":
                return new Colecionavel("col2", "col2", COR_VERDE, i, j);
            case "K":
                return new Colecionavel("col3", "col3", COR_VERDE, i, j);
            case "F":
                return new Colecionavel("fim", "fim", COR_VERDE, i, j);
        }
        return null;
    }

    public static void esvaziaTile(int atualL, int atualC) {
        tiledMap[atualL][atualC] = null;
    }

}