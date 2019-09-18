package sistema;

import problema.*;
import ambiente.*;
import arvore.TreeNode;
import arvore.fnComparator;
import comuns.*;
import static comuns.PontosCardeais.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

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

        custo = 0.0;
        current_action = 0;
    }

    public Double uniform_cost_hn(ArrayList<Estado> list){
        return (double) 0;
    }

    public Double heuristc_one(ArrayList<Estado> list){
        if(list == null){
            return (double) 0;
        }

//        System.out.println("in heuristc 1");
//        System.out.println(list.get(0).getString());
//        System.out.println(list.get(1).getString());

        double cost = (Math.abs(list.get(0).getCol() - list.get(1).getCol()) +
                Math.abs(list.get(0).getLin() - list.get(1).getLin()));

        return cost;
    }

    public Double heuristic_two(ArrayList<Estado> list){
        if(list == null){
            return (double) 0;
        }

        int x1 = list.get(0).getCol();
        int y1 = list.get(0).getLin();
        int x2 = list.get(1).getCol();
        int y2 = list.get(1).getLin();

        double cost = Math.sqrt( Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) );
        return cost;
    }

    public int[] search(Function<ArrayList<Estado>, Double> hn_method){

        ArrayList<Integer> made_plan = new ArrayList<Integer>();

        TreeNode root = new TreeNode(null);
        root.setState(prob.estIni);
        root.setHn(hn_method.apply(null).floatValue());
        root.setGn(0);

        List<TreeNode> border = new ArrayList<TreeNode>();
        border.add(root);

        List<Estado> explored = new ArrayList<Estado>();
        explored.add(root.getState());
        TreeNode curr_node;
        boolean solved = false;
        TreeNode solution = null;

        ArrayList<Estado> compare = new ArrayList<Estado>();
        int n_nodes = 1;
        int n_explored = 1;

        while( !border.isEmpty() && !solved ){
            curr_node = border.remove(0);
            n_explored++;
            explored.add(curr_node.getState());

            int[] poss_states = prob.acoesPossiveis(curr_node.getState());
            poss_states = check_wall(poss_states, curr_node.getState());

            for(int i = 0; i < poss_states.length; i++){
                if(prob.testeObjetivo(curr_node.getState())){
                    solved = true;
                    solution = curr_node;
                    break;
                }
                if(poss_states[i] == 1){
                    n_nodes++;
                    Estado new_state = prob.suc(curr_node.getState(), i);
                    if( !isExplored(explored, new_state) ){
                        explored.add(new_state);
                        TreeNode new_node = curr_node.addChild();
                        new_node.setState(new_state);
                        new_node.setGn(curr_node.getGn() + prob.obterCustoAcao(curr_node.getState(), i, new_state));
                        compare.add(curr_node.getState());
                        compare.add(prob.estObj);
                        new_node.setHn(hn_method.apply(compare).floatValue());
                        compare.clear();
                        new_node.setAction(i);
                        border.add(new_node);
                    }
                }
            }
            Collections.sort(border, new NodeComparator());
        }

        TreeNode aux = solution;
        while( aux.getParent() != null ){
            made_plan.add(0, aux.getAction());
            aux = aux.getParent();
        }
        System.out.println("Nós explorados: " + n_explored);
        System.out.println("Nós criados: " + n_nodes);

        return made_plan.stream().mapToInt(Integer::intValue).toArray();
    }

    public boolean isExplored(List<Estado> list, Estado x){
        for(int i = 0; i < list.size(); i++){
            if( x.igualAo(list.get(i)) ){
                return true;
            }
        }
        return false;
    }

    public int[] check_wall(int[] actions, Estado est){
        int[] acoes = actions;

        // testa se ha paredes no entorno l, c
        int l = est.getLin();
        int c = est.getCol();

        // testa se ha parede na direcao N
        if (acoes[0] != -1 && model.labir.parede[l - 1][c] == 1) {
            acoes[0] = -1;
        }

        // testa se ha parede na direcao NE
        if (acoes[1] != -1 && model.labir.parede[l - 1][c + 1] == 1) {
            acoes[1] = -1;
        }

        // testa se ha parede na direcao l
        if (acoes[2] != -1 && model.labir.parede[l][c + 1] == 1) {
            acoes[2] = -1;
        }

        // testa se ha parede na direcao SE
        if (acoes[3] != -1 && model.labir.parede[l + 1][c + 1] == 1) {
            acoes[3] = -1;
        }

        // testa se ha parede na direcao S
        if (acoes[4] != -1 && model.labir.parede[l + 1][c] == 1) {
            acoes[4] = -1;
        }

        // testa se ha parede na direcao SO
        if (acoes[5] != -1 && model.labir.parede[l + 1][c - 1] == 1) {
            acoes[5] = -1;
        }

        // testa se ha parede na direcao O
        if (acoes[6] != -1 && model.labir.parede[l][c - 1] == 1) {
            acoes[6] = -1;
        }

        // testa se ha parede na direcao NO
        if (acoes[7] != -1 && model.labir.parede[l - 1][c - 1] == 1) {
            acoes[7] = -1;
        }

        return acoes;
    }
    
     /**
     * Agente escolhe qual acao será executada em um ciclo de raciocinio.
     * Observar que o agente executa somente uma acao por ciclo.
     */
    public int deliberar(String heuristic) {
        if(plan == null){

            Function<ArrayList<Estado>, Double> uniform_hn = this::uniform_cost_hn;
            if(heuristic.charAt(0) == '1'){
                uniform_hn = this::heuristc_one;
            }
            if(heuristic.charAt(0) == '2'){
                uniform_hn = this::heuristic_two;
            }

            plan = search(uniform_hn);
            return 1;
        }
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

class NodeComparator implements Comparator<TreeNode>{

    @Override
    public int compare(TreeNode a, TreeNode b){
        if(a.getFn() < b.getFn()){
            return -1;
        }
        if(a.getFn() > b.getFn()){
            return 1;
        }
        return 0;
    }

}
