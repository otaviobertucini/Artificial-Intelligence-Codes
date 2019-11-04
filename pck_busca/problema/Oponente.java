package problema;

public class Oponente {

    public float massa;
    public float altura;
    public boolean dente; //true = afiado
    public int olhos; //0 = escuro, 1 = claro, 2 = vermelha
    public boolean gentil;

    public Oponente(float massa, float altura, boolean dente, int olhos, boolean gentil){

        this.massa = massa;
        this.altura = altura;
        this.dente = dente;
        this.olhos = olhos;
        this.gentil = gentil;

    }

}