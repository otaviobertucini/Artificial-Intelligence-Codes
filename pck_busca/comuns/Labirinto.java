
package comuns;

import problema.Oponente;

/**Labirinto representa um labirinto com paredes. A indexação das posições do
 * labirinto é dada por par ordenado (linha, coluna). A linha inicial é zero e
 * a linha máxima é (maxLin - 1). A coluna inicial é zero e a máxima é 
 * (maxCol - 1).
 *
 * @author Tacla 
 */
public class Labirinto {
    /*Array que representa o labirinto sendo as posições = 1 aquelas que 
      contêm paredes */
    public int parede[][];
    public int count_parede = 0;
    public  int tam_labir;
    /*Número máximo de colunas do labirinto */
    private final int maxCol;      
    /*Número máximo de linhas do labirinto */
    private final int maxLin;

    public Oponente oponentes[][];
    int oponent_count = 0;
    int lin_oponente = 0;
    int col_oponente = 0;
    
    public Labirinto(int maxLinhas, int maxColunas) {
        this.maxCol = maxColunas;
        this.maxLin = maxLinhas;
        parede = new int[maxLin][maxCol];
        oponentes = new Oponente[maxLin][maxCol];
        tam_labir = maxCol*maxLin;
    }
    
    public int getMaxLin() {
        return this.maxLin;
    }
    public int getMaxCol() {
        return this.maxCol;
    }
    
   /** Constroi parede horizontal da coluna inicial até a final na linha indicada.
    * @param ini: coluna inicial entre 0 e número máximo de colunas - 1
    * @param fim: coluna final (deve ser maior que coluna inicial)
    * @param linha: em qual linha colocar a parede (entre 0 e máx. de linhas - 1)
    */
    public void porParedeHorizontal(int ini, int fim, int linha) {
        if (fim >= ini && ini >= 0 && fim < maxCol && linha >= 0 && linha < maxLin) {
            for (int c = ini; c <= fim; c++) {
                parede[linha][c] = 1;
                count_parede++;
            }
        }
    }
    /** Constroi parede vertical da linha inicial até a final na coluna indicada.
     * até a final 
    * @param ini: linha inicial entre 0 e  máximo de linhas - 1
    * @param fim: linha final (deve ser maior do que linha inicial)
    * @param coluna: em qual coluna colocar a parede (entre 0 e máx. de colunas - 1)
    */
    public void porParedeVertical(int ini, int fim, int coluna) {
        if (fim >= ini && ini >= 0 && fim < maxLin && coluna >= 0 && coluna < maxCol) {
            for (int l = ini; l <= fim; l++) {
                parede[l][coluna] = 1;
                count_parede++;
            }
        }
    }

    public boolean porOponente(Oponente o){

        if(oponent_count == tam_labir - count_parede){
            return false;
        }

        while (parede[lin_oponente][col_oponente] == 1){

            col_oponente++;
            if(col_oponente == maxCol){
                lin_oponente++;
                col_oponente = 0;
            }

        }

        oponentes[lin_oponente][col_oponente] = o;
        oponent_count++;

        col_oponente++;
        if(col_oponente == maxCol){
            lin_oponente++;
            col_oponente = 0;
        }

        return true;
    }

    public void printOponentes(){

        for(int i = 0; i < maxLin; i++){
            for(int j = 0; j < maxCol; j++){
                if(parede[i][j] != 1){
                    System.out.println(oponentes[i][j].massa  + ", " + i + ", " + j);
                }
            }
        }
    }
    
}
