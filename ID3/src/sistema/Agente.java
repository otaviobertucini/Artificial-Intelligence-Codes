package sistema;

import ambiente.*;
import arvore.TreeNode;
import arvore.fnComparator;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import problema.*;
import comuns.*;
import static comuns.PontosCardeais.*;
import java.util.ArrayList;
import java.util.Random;
import net.sourceforge.jFuzzyLogic.FIS;
//import net.sourceforge.jFuzzyLogic.*;

/**
 *
 * @author tacla
 */
public class Agente implements PontosCardeais {

    /* referência ao ambiente para poder atuar no mesmo*/
    Model model;
    Problema prob;
    Estado estAtu; // guarda o estado atual (posição atual do agente)
    int plan[];
    public double custo = 0.0;
    static int ct = -1;
    int count = 0;
    FIS fis;

    public Agente(Model m) {
        this.model = m;
        prob = new Problema();
        prob.criarLabirinto(9, 9);
        prob.crencaLabir.porParedeVertical(0, 1, 0);
        prob.crencaLabir.porParedeVertical(0, 0, 1);
        prob.crencaLabir.porParedeVertical(6, 7, 2);
        prob.crencaLabir.porParedeVertical(2, 2, 4);
        prob.crencaLabir.porParedeVertical(7, 7, 1);
        prob.crencaLabir.porParedeHorizontal(4, 7, 0);
//        prob.crencaLabir.porParedeHorizontal(7, 7, 1);
//        prob.crencaLabir.porParedeHorizontal(3, 5, 2);
        prob.crencaLabir.porParedeHorizontal(3, 5, 3);
//        prob.crencaLabir.porParedeHorizontal(7, 7, 3);
        prob.crencaLabir.porParedeVertical(6, 7, 4);
        prob.crencaLabir.porParedeVertical(5, 6, 5);
        prob.crencaLabir.porParedeVertical(5, 7, 7);

        // Estado inicial, objetivo e atual
        //posiciona fisiscamente o agente no estado inicial
        Estado ini = this.sensorPosicao();
        prob.defEstIni(ini.getLin(), ini.getCol());

        // Estado atual doa agente = estado inicial
        this.estAtu = prob.estIni;

        // Define o estado objetivo
        Random rand = new Random();
        int lin = rand.nextInt(9);
        int col = rand.nextInt(9);
        while(model.labir.isParede(lin, col)){
            lin = rand.nextInt(9);
            col = rand.nextInt(9);
        }
        prob.defEstObj(lin, col);

        custo = 0.0;
        ct = -1;

        fis = FIS.load("/home/otavio/Documents/Sistemas_Inteligentes/ID3/src/sistema/fuzzy.fcl", true);

//        FunctionBlock fb = fis.getFunctionBlock(null);
//        JFuzzyChart.get().chart(fb);

    }

    public void printPlano() {
//        System.out.println("--- PLANO ---");
//        for (int i = 0; i < plan.length; i++) {
//            System.out.print(acao[plan[i]] + ">");
//        }
//        System.out.println("FIM\n\n");
    }

    /**
     * Escolhe qual ação será executada em um ciclo de raciocínio. Na 1a chamada
     * calcula um plano por meio de um algoritmo de busca. A partir da 2a
     * chamada, executa uma ação por vez do plano calculado.
     */
    public int deliberar(int type) {
        // realiza busca na 1a. chamada para elaborar um plano
        if (ct == -1) {
            plan = buscaCheapestFirst(2); //0=c.unif.; 1=A* com colunas; 2=A*=dist. Euclidiana
            if (plan != null) 
                printPlano();
            else {
                System.out.println("SOLUÇÃO NÃO ENCONTRADA");
                return -1;
            }

        }

        // nas demais chamadas, executa o plano já calculado
        ct++;

        // atingiu o estado objetivo então para
        if (prob.testeObjetivo(estAtu)) {
//            System.out.println("!!! ATINGIU ESTADO OBJETIVO !!!");
//            System.out.println("Custo total: " + custo);
            return -1;
        }
        //algo deu errado, chegou ao final do plano sem atingir o objetivo
        if (ct >= plan.length) {
            System.out.println("### ERRO: plano chegou ao fim, mas objetivo não foi atingido");
            return -1;
        }
//        System.out.println("--- Mente do Agente ---");
//        System.out.println("  Estado atual  : " + estAtu.getLin() + "," + estAtu.getCol());
//        System.out.println("  Passo do plano: " + (ct + 1) + " de " + plan.length + " ação=" + acao[plan[ct]] + "\n");
        float a;
        if(plan[ct] % 2 == 0){
            a = (float) 1.0;
        }
        else{
            a = (float) 1.5;
        }

        boolean emp = empurrar(type, estAtu.getLin(), estAtu.getCol());
        if(emp){

            if(model.labir.isOponente(estAtu.getLin(), estAtu.getCol())){
                a *= 3;
            }
            else{
                a *= 4;
            }
        }
        else{
            if(model.labir.isOponente(estAtu.getLin(), estAtu.getCol())){
                a *= 6;
            }
            else{
                a *= 1;
            }
        }

        System.out.println("empurrei com intensidade: " + intensidade(estAtu.getLin(), estAtu.getCol()));

        custo += a;
        executarIr(plan[ct]);
        count++;

        // atualiza o estado atual baseando-se apenas nas suas crenças e
        // na função sucessora (não faz leitura do sensor de posição!!!)
        estAtu = prob.suc(estAtu, plan[ct]);
        return 1;
    }

    public boolean empurrar(int func, int lin, int col){

        //baseline
        if(func == 0){
            Random rand = new Random();
            int r = rand.nextInt(2);
            if(r == 1){
                return true;
            }
            return false;
        }

        //J48
        Oponente o = model.labir.getOponente(lin, col);

//        System.out.println(o.massa + "," + o.altura + "," + o.dente + "," + o.olhos + "," + o.gentil);

        if(o.dente){

            //dente afiado
            if(o.massa <= 99.57){
                if(o.altura <= 1.81){
                    return true;
                }
                return false;
            }
            else{
                return true;
            }

        }
        else{
            //dente não afiado
            if(o.olhos == 0 || o.olhos == 1){
                return false;
            }
            if(o.massa <= 100.25){
                if(o.altura <= 1.82){
                    return true;
                }
                return false;
            }
            return true;
        }

    }

    public double intensidade(int lin, int col){

        Oponente o = model.labir.getOponente(lin, col);

//        FIS fis = FIS.load("/home/otavio/Documents/Sistemas_Inteligentes/ID3/src/sistema/fuzzy.fcl", true);

        if( fis == null){
            System.out.println("Erro ao abrir arquivo!");
            return 0;
        }

        fis.setVariable("massa", o.massa);
        fis.setVariable("altura", o.altura);

        fis.evaluate();

        return fis.getVariable("intensidade").getLatestDefuzzifiedValue();

    }

    /**
     * Atuador: solicita ao agente 'fisico' executar a acao.
     *
     * @param direcao
     * @return 1 caso movimentacao tenha sido executada corretamente
     */
    public int executarIr(int direcao) {
        model.ir(direcao);
        return 1; // deu certo
    }

    /**
     * Simula um sensor que realiza a leitura da posição atual no ambiente e
     * traduz para um par de coordenadas armazenadas em uma instância da classe
     * Estado.
     *
     * @return Estado um objeto que representa a posição atual do agente no
     * labirinto
     */
    private Estado sensorPosicao() {
        int pos[];
        pos = model.lerPos();
        return new Estado(pos[0], pos[1]);
    }

    public void printExplorados(ArrayList<Estado> expl) {
        System.out.println("--- Explorados --- (TAM: " + expl.size() + ")");
        for (Estado e : expl) {
            System.out.print(e.getString() + " ");
        }
        System.out.println("\n");
    }

    public void printFronteira(ArrayList<TreeNode> front) {
        System.out.println("--- Fronteira --- (TAM=" + front.size() + ")");
        for (TreeNode f : front) {
            String str;
            str = String.format("<%s %.2f+%.2f=%.2f> ", f.getState().getString(),
                    f.getGn(), f.getHn(), f.getFn());
            System.out.print(str);
        }
        System.out.println("\n");
    }

    public int[] montarPlano(TreeNode nSol) {
        int d = nSol.getDepth();
        int sol[] = new int[d];
        TreeNode pai = nSol;

        for (int i = sol.length - 1; i >= 0; i--) {
            sol[i] = pai.getAction();
            pai = pai.getParent();
        }
        return sol;
    }

    /**
     * Implementa uma heurística - a número 1 - para a estratégia A* No caso,
     * hn1 é a distância em colunas do estado passado como argumento até o
     * estado objetivo.
     *
     * @param est: estado para o qual se quer calcular o valor de hn
     */
    private float hn1(Estado est) {
        return (float) Math.abs(est.getCol() - prob.estObj.getCol());
    }

    /**
     * Implementa uma heurística - a número 2 - para a estratégia A* No caso,
     * hn2 é a distância Euclidiana do estado passado como argumento até o
     * estado objetivo (calculada por Pitágoras).
     *
     * @param est: estado para o qual se quer calcular o valor de hn
     */
    private float hn2(Estado est) {
        double distCol = Math.abs(est.getCol() - prob.estObj.getCol());
        double distLin = Math.abs(est.getLin() - prob.estObj.getLin());
        return (float) Math.sqrt(Math.pow(distLin, 2) + Math.pow(distCol, 2));
    }

    /**
     * Realiza busca com a estratégia de custo uniforme ou A* conforme escolha
     * realizada na chamada.
     *
     * @param tipo 0=custo uniforme; 1=A* com heurística hn1; 2=A* com hn2
     * @return
     */
    public int[] buscaCheapestFirst(int tipo) {
        // atributos para analise de depenho
        int ctNosArvore = 0; // contador de nos gerados e incluidos na arvore
        // nós que foram inseridos na arvore mas que
        // que não necessitariam porque o estado já
        // foi explorado ou por já estarem na fronteira 
        int ctNosDesprFront = 0;
        int ctNosDesprExpl = 0;

        // Algoritmo de busca
        TreeNode sol = null;     // armazena o nó objetivo
        TreeNode raiz = new TreeNode(null);
        raiz.setState(prob.estIni);
        raiz.setGnHn(0, 0);
        raiz.setAction(-1); // nenhuma acao
        ctNosArvore++;

        // cria FRONTEIRA com estado inicial 
        ArrayList<TreeNode> fronteira = new ArrayList<>(12);
        fronteira.add(raiz);

        // cria EXPLORADOS - lista de estados inicialmente vazia
        ArrayList<Estado> expl = new ArrayList<>(12);

        // estado na inicializacao da arvore de busca
//        System.out.println("\n*****\n***** INICIALIZACAO ARVORE DE BUSCA\n*****\n");
//        System.out.println("\nNós na árvore..............: " + ctNosArvore);
//        System.out.println("Desprezados já na fronteira: " + ctNosDesprFront);
//        System.out.println("Desprezados já explorados..: " + ctNosDesprExpl);
//        System.out.println("Total de nós gerados.......: " + (ctNosArvore + ctNosDesprFront + ctNosDesprExpl));

        while (!fronteira.isEmpty()) {
//            System.out.println("\n*****\n***** Inicio iteracao\n*****\n");
//            printFronteira(fronteira);
            TreeNode nSel = fronteira.remove(0);
//            System.out.println("   Selec. exp.: \n" + nSel.gerarStr() + "\n");

            // teste de objetivo
            if (nSel.getState().igualAo(this.prob.estObj)) {
                sol = nSel;
                //System.out.println("!!! Solução encontrada !!!");
                break;
            }
            expl.add(nSel.getState()); // adiciona estado aos já explorados
//            printExplorados(expl);

            // obtem acoes possiveis para o estado selecionado para expansão
            int acoes[] = prob.acoesPossiveis(nSel.getState());
            // adiciona um filho para cada acao possivel
            for (int ac = 0; ac < acoes.length; ac++) {
                if (acoes[ac] < 0) // a acao não é possível
                {
                    continue;
                }
                // INSERE NÓ FILHO NA ÁRVORE DE BUSCA - SEMPRE INSERE, DEPOIS
                // VERIFICA SE O INCLUI NA FRONTEIRA OU NÃO
                // instancia o filho ligando-o ao nó selecionado (nSel)
                TreeNode filho = nSel.addChild();
                // Obtem estado sucessor pela execução da ação <ac>
                Estado estSuc = prob.suc(nSel.getState(), ac);
                filho.setState(estSuc);
                // custo gn: custo acumulado da raiz ate o nó filho
                float gnFilho;
                gnFilho = nSel.getGn() + prob.obterCustoAcao(nSel.getState(), ac, estSuc);

                switch (tipo) {
                    case 0: // busca custo uniforme
                        filho.setGnHn(gnFilho, (float) 0); // deixa hn zerada porque é busca de custo uniforme  
                        break;
                    case 1: // A* com heurística 1
                        filho.setGnHn(gnFilho, hn1(estSuc));
                        break;
                    case 2: // A* com heurística 2
                        filho.setGnHn(gnFilho, hn2(estSuc));
                        break;
                }

                filho.setAction(ac);

                // INSERE NÓ FILHO NA FRONTEIRA (SE SATISFAZ CONDIÇÕES)
                // Testa se estado do nó filho foi explorado
                boolean jaExplorado = false;
                for (Estado e : expl) {
                    if (filho.getState().igualAo(e)) {
                        jaExplorado = true;
                        break;
                    }
                }
                // Testa se estado do nó filho está na fronteira, caso esteja
                // guarda o nó existente em nFront
                TreeNode nFront = null;
                if (!jaExplorado) {
                    for (TreeNode n : fronteira) {
                        if (filho.getState().igualAo(n.getState())) {
                            nFront = n;
                            break;
                        }
                    }
                }

                // se ainda não foi explorado ...
                if (!jaExplorado) {
                    // e não está na fronteira, então adiciona à fronteira
                    if (nFront == null) {
                        fronteira.add(filho);
                        fronteira.sort(new fnComparator()); // classifica ascendente
                        ctNosArvore++;
                    } else {
                        // se jah estah na fronteira temos que ver se eh melhor 
                        if (nFront.getFn() > filho.getFn()) { // no da fronteira tem custo maior que o filho
                            fronteira.remove(nFront);  // remove no da fronteira: pior
                            nFront.remove(); // retira-se da arvore
                            fronteira.add(filho);      // adiciona o filho que eh melhor
                            fronteira.sort(new fnComparator()); // classifica ascendente
                            // nao soma na arvore porque inclui o melhor e retira o pior
                        } else {
                            // conta como desprezado seja porque o filho eh pior e foi descartado
                            ctNosDesprFront++;

                        }
                    }
                } else {
                    ctNosDesprExpl++;
                }
                // esta contagem de maximos perdeu o sentido porque todos os 
                // nos sao armazenados na arvore de busca. Logo, ultima iteracao
                // contem o maximo de nos na arvore (inclusive com a fronteira
                // e os ja explorados (que tambem estao na arvore)
                /*
                if (fronteira.size() > maxNosFronteira)
                    maxNosFronteira = fronteira.size();
                if (expl.size() > maxNosExplorados)
                    maxNosExplorados = expl.size();
                 */
            }
            //raiz.printSubTree();
//            System.out.println("\nNós na árvore..............: " + ctNosArvore);
//            System.out.println("Desprezados já na fronteira: " + ctNosDesprFront);
//            System.out.println("Desprezados já explorados..: " + ctNosDesprExpl);
//            System.out.println("Total de nós gerados.......: " + (ctNosArvore + ctNosDesprFront + ctNosDesprExpl));
            //System.out.println("Nós desprezados total..........: " + (ctNosDesprFront + ctNosDesprExpl));
            //System.out.println("Máx nós front..: " + maxNosFronteira);
            //System.out.println("Máx nós explor.: " + maxNosExplorados);
        }

        // classifica a fronteira por 
        //Collections.sort(fronteira, new fnComparator());
        if (sol != null) {
//            System.out.println("!!! Solucao encontrada !!!");
//            System.out.println("!!! Custo: " + sol.getGn());
//            System.out.println("!!! Depth: " + sol.getDepth() + "\n");
//            System.out.println("\nNós na árvore..............: " + ctNosArvore);
//            System.out.println("Desprezados já na fronteira: " + ctNosDesprFront);
//            System.out.println("Desprezados já explorados..: " + ctNosDesprExpl);
//            System.out.println("Total de nós gerados.......: " + (ctNosArvore + ctNosDesprFront + ctNosDesprExpl));
            return montarPlano(sol);
        } else {
            System.out.println("### solucao NAO encontrada ###");
            return null;
        }
    }
}
