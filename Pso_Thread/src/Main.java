// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

//z = somme(f(j), y(j)) + somme(somme(c(i)(j), x(i)(j))

public class Main {
    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée


        PSOimplementation p = new PSOimplementation();



        long endTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée
        long duration = endTime - startTime;
        System.out.println("Le programme a mis " + duration + " millisecondes à s'exécuter");
//        5100
    }
}