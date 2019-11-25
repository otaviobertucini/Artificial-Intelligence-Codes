/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import ambiente.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author tacla
 */
public class Main {
    public static void main(String args[]) throws IOException {
        // Cria o ambiente (modelo) = labirinto com suas paredes
        Model model = new Model(9, 9);
        model.labir.porParedeVertical(0, 1, 0);
        model.labir.porParedeVertical(0, 0, 1);
        model.labir.porParedeVertical(6, 7, 2);
        model.labir.porParedeVertical(2, 2, 4);
        model.labir.porParedeVertical(7, 7, 1);
        model.labir.porParedeHorizontal(4, 7, 0);
//        model.labir.porParedeHorizontal(7, 7, 1);
//        model.labir.porParedeHorizontal(3, 5, 2);
        model.labir.porParedeHorizontal(3, 5, 3);
//        model.labir.porParedeHorizontal(7, 7, 3);
        model.labir.porParedeVertical(6, 7, 4);
        model.labir.porParedeVertical(5, 6, 5);
        model.labir.porParedeVertical(5, 7, 7);

        int linha = 8;
        int type = 0;
        String type_txt = "baseline";

        BufferedWriter w = new BufferedWriter(new FileWriter("result.txt", true));

        for (int i = 0; i < 1; i++) {

            System.out.println("i = " + i);

            model.iniciarOponentes(linha);
//        model.labir.printOponentes();

            // seta a posição inicial do agente no ambiente - corresponde ao estado inicial
//            model.setPos(8, 0);

            Random rand = new Random();
            int lin = rand.nextInt(9);
            int col = rand.nextInt(9);
            while(model.labir.isParede(lin, col)){
                lin = rand.nextInt(9);
                col = rand.nextInt(9);
            }
            model.setPos(lin, col);

//            System.out.println("lin = " + lin);
//            System.out.println("col = " + col);

            // Cria um agente
            Agente ag = new Agente(model);
            // marca no ambiente onde estah o objetivo - somente para visualizacao
            model.setObj(ag.prob.estObj.getLin(), ag.prob.estObj.getCol());

            // Ciclo de execucao do sistema
            // desenha labirinto
            model.desenhar();

            // agente escolhe proxima açao e a executa no ambiente (modificando
            // o estado do labirinto porque ocupa passa a ocupar nova posicao)

//            System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");
            if(i % 2 == 0){
                type = 0;
                type_txt = "baseline";
            }
            else{
                type = 1;
                type_txt = "j48";
            }
            while (ag.deliberar(type) != -1) {
                model.desenhar();
            }

            linha += model.labir.tam_labir - model.labir.count_parede + 1;
            model.labir.resetLabir();
            System.out.println("CUSTO TOTAL EXECUÇÃO: " + ag.custo);

            String write = "<" + type_txt + ", " + i + ", " + ag.custo + ">\n";
            w.write(write);

            System.out.println("--------------- NEW GAME ---------------");

        }

        w.close();

    }
}
