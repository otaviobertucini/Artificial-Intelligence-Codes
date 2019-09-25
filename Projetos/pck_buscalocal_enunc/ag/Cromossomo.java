/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ag;

import agNRainhas.AGNRainhas;
import bits.Bits;
import java.util.BitSet;
import java.util.Random;

/**
 * Permite implementar um cromossomo com codificacao binaria. O cromossomo eh
 * subdividido em varios genes sendo cada um dos genes decompostos em locus. O
 * programador pode atribuir valor a um gene
 *
 * @author tacla
 */
public class Cromossomo implements ConfigAG {

    /**
     * fitness do cromossomo
     */
    public float fitness;


    // DESEMPENHO
    /**
     * Contagem de chamadas ao metodo de fitness - acumula para todos os
     * cromossomos
     */
    public static long ctChamadasFitness = 0;

    /**
     * codificacao binaria do cromossomo
     */
    public BitSet bits;

    public Cromossomo() {
        this.bits = new BitSet(ConfigAG.NUM_BITS);
    }

    public Cromossomo clonar() {
        Cromossomo clone = new Cromossomo();
        for (int i = 0; i < ConfigAG.NUM_BITS; i++) {
            clone.bits.set(i, this.bits.get(i));
        }
        clone.fitness = this.fitness;
        return clone;
    }

    /**
     * Lê o cromossomo cujo indice eh index. <br>
     * Ex.: um cromossomo  com 3 genes de 2 locus cada: [11,10,01]. <br>
     * O index zero corresponde ao gene mais a direita. Logo, <ul>
     * <li>lerGene(0) retorna 1L</li>
     * <li>lerGene(1) retorna 2L</li>
     * <li>lerGene(2) retorna 3L</li> </ul>
     * @param index
     * @return valor long do gene
     */
    public long lerGene(int index) {
        return Bits.getGroupLong(index, this.bits, ConfigAG.NUM_LOCUS);
    }

    /**
     * Permite setar o valor de um gene utilizando um valor definido em long.<br>
     * Ex.: um cromossomo zerado com 3 genes de 2 locus cada: [00,00,00]
     * <br>
     * O index zero corresponde ao gene mais a direita. <br>
     * Logo, ao fazer chamadas sucessivas ao setarGene como indicado abaixo obtera<ul>
     * <li>setarGene(0, 1L) resulta em [00,00,01]</li>
     * <li>setarGene(1, 2L) retorna em [00,10,01]</li>
     * <li>setarGene(2, 3L) retorna em [11,10,01]</li> </ul>
     *
     * @param index o indice do gene a ser setado
     * @param valor o valor a ser atribuido ao gene
     *
     */
    public void setarGene(int index, long valor) {
        Bits.setGroupLong(index, this.bits, ConfigAG.NUM_LOCUS, valor);
    }

    /**
     * Imprime uma variável do tipo BitSet     *
     * @param titulo título que aparecerá no printf
     */
    public void imprimir(String titulo) {
        System.out.println("--- " + titulo + " --- ");
        System.out.println(this.fitness + "," + Bits.printDecCSV(bits, ConfigAG.NUM_LOCUS, ConfigAG.NUM_BITS));
        System.out.println("");
    }

    /**
     * Imprime cromossomo no formato 'valores separados por virgula' 
     * @return string fitness,gene 0,...,gene n, tal que gene 0 a n corresponde
     * ao valor em decimal de cada gene
     */
    public String imprimirCSV() {
        return this.fitness + "," + Bits.printDecCSV(bits, ConfigAG.NUM_LOCUS, ConfigAG.NUM_BITS);
    }

    /**
     * FAZER
     */
    private void reparar() {
 
    }

    /**
     * FAZER
     */
    private int infactivel() {
        
        return -1;
    }

    /**
     * Calcular fitness: dependente do problema. Se solucao for infactivel e
     * flag PENALIZACAO=true entao penalizar o fitness; caso contrario, reparar
     * o individuo
     */
    protected void calcularFitness() {
   
        ctChamadasFitness++;

        if (!ConfigAG.PENALIZACAO) {
            this.reparar();
        } else if (this.infactivel() >= 0) {
            // CALCULAR FITNESS;
            return;
        }

        // CALCULAR FITNESS PARA SITUACOES NORMAIS
        // this.fitness = <CALCULO> 
    }

    /**
     * Preencher um cromossomo com valores iniciais (depende do problema)
     */
    public void inicializarCromossomo() {

        Random rand = new Random();
        int n;

        for(int i = 0; i < NUM_GENES; i++) {

            n = rand.nextInt(8);
            Bits.setGroupLong(i, this.bits, NUM_LOCUS, (long) Math.pow(2, n));

        }

//        System.out.println(Bits.printBinCSV(this.bits, NUM_GENES, NUM_BITS));
    }
}
