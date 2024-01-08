// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        long startTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée

        /*System.out.print("a = ");
        Scanner inp =new Scanner(System.in);
        int a = inp.nextInt();*/
        //exit(0);
        PSOimplementation p = new PSOimplementation();

//        System.out.println("Thread main started");
//        int[] cnt = new int[]{29, 20, 0, 7, 10};
//        int[] cnt2 = new int[]{2, 150, 0, 7, 1};
//        Threadd task1 = new Threadd(cnt);
//        Thread t1 = new Thread(task1);
//        t1.start();
//        Threadd task2 = new Threadd(cnt2);
//        Thread t2 = new Thread(task2);
//        t2.start();
//
//        int max = task1.getValue();
//        if (task1.getValue() < task2.getValue())
//            max = task2.getValue();
//        System.out.println("Max " + max);
//        System.out.println("Thread main finished");



        long endTime = System.currentTimeMillis(); // ou System.nanoTime() pour une précision plus élevée
        long duration = endTime - startTime;
        System.out.println("Le programme a mis " + duration + " millisecondes à s'exécuter");
    }
}

/*
#fitness function which is the maximum completion time
def calculate_makespan(individual, processing_times,num_machines):
    machine_time=[0 for _ in range(num_machines)]
    for machine in range(1,num_machines+1):
        for gene in range(len(individual)):
            if individual[gene]==machine:
                machine_time[individual[gene]-1]+=processing_times[gene]
    return max(machine_time)
num_machines=3
num_jobs=5
processing_time=[1,1,1,1,1]
a,b=genetic_algorithm(POP_SIZE,1000, processing_time,num_jobs,num_machines)
print(a,b)
*/