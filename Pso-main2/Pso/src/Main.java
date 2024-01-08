// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

//z = somme(f(j), y(j)) + somme(somme(c(i)(j), x(i)(j))

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée


        int nbr_client = 5;
        int nbr_choice_entrepots = 5;

        double[] f_j = new double[]{3501, 9541, 6920, 4590, 4300};
        int[][] c_i_j = new int[][]{{28, 22, 20, 29, 29}, {27, 19, 20, 30, 29},
                {20, 23, 20, 21, 29}, {25, 22, 20, 29, 29}, {35, 23, 24, 19, 42}};

        PSOimplementation p = new PSOimplementation(nbr_choice_entrepots, nbr_client, c_i_j, f_j);



        long endTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée
        long duration = endTime - startTime;
        System.out.println("Le programme a mis " + duration + " millisecondes à s'exécuter");
    }
}