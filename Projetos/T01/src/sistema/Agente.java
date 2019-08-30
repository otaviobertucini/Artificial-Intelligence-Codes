package sistema;

import problema.*;
import ambiente.*;
import arvore.TreeNode;
import arvore.fnComparator;
import comuns.*;
import static comuns.PontosCardeais.*;
import java.util.ArrayList;

/**
 *
 * @author tacla
 */
public class Agente implements PontosCardeais {
    /* referência ao ambiente para poder atuar no mesmo*/
    Model model;
    Estado estAtu; // guarda o estado atual (posição atual do agente)
    int current_action;
    int plan[];
    double custo;
    static int ct = -1;
    Problema prob;
           
    public Agente(Model m) {
        this.model = m; 
        
        // Posiciona o agente fisicamente no ambiente na posicao inicial
        model.setPos(8,0);

        estAtu = null;

        //Cria o problema
        prob = new Problema();
        prob.criarLabirinto(9, 9);
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 8);

        plan = new int[]{N, N, N, N, L, L, L, L, L, L, L, L, N, N};
        custo = 0.0;
        current_action = 0;
    }
    
     /**
     * Agente escolhe qual acao será executada em um ciclo de raciocinio.
     * Observar que o agente executa somente uma acao por ciclo.
     */
    public int deliberar() {               
        //  contador de acoes
        ct++;
        // @todo T1: perceber por meio do sensor a posicao atual e imprimir
        // @todo T1: a cada acao escolher uma acao {N, NE, L, SE, S, SO, O, NO}

        estAtu = sensorPosicao();
        if(prob.testeObjetivo(estAtu)){
            System.out.println( "CHEGUEI" );
            return -1;
        }

        executarIr(plan[current_action]);

        custo += prob.obterCustoAcao(estAtu, plan[current_action], estAtu);

        System.out.println("X: " + estAtu.getCol() + " Y: " + estAtu.getLin());
        int[] poss = prob.acoesPossiveis(estAtu);
//        System.out.println( "Ações possíveis: " + poss[0] + poss[1] + poss[2] + poss[3] + poss[4] + poss[5] + poss[6]
//                + poss[7]);
        System.out.print( "Ações possíveis: {" );
        for(int i = 0; i < poss.length; i++){
            System.out.print(" " + poss[i]);
        }
        System.out.println( "}" );
        System.out.println( "Ação escolhida: " + plan[current_action] );
        System.out.println( "Custo até o momento: " + custo );

        current_action++;

        return 1; // Se retornar -1, encerra o agente
    }

    /**
    * Atuador: executa 'fisicamente' a acao Ir
    * @param direcao um dos pontos cardeais
    */
    public int executarIr(int direcao) {
        //@todo T1 - invocar metodo do Model - atuar no ambiente

        this.model.ir(direcao);
        return 1; // deu certo
    }

    /**
     * Simula um sensor que realiza a leitura da posição atual no ambiente e
     * traduz para um par de coordenadas armazenadas em uma instância da classe
     * Estado.
     * @return Estado contendo a posição atual do agente no labirinto 
     */
    public Estado sensorPosicao() {
        //@todo T1 - sensor deve ler a posicao do agente no labirinto (environment)

        int[] pos = this.model.lerPos();

        return new Estado(pos[0], pos[1]);
    }
    
}
