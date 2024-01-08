import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Optimizer {

    public static final Scanner input=new Scanner(System.in);
    public static final PrintStream console=System.out;
    public static final File log=new File("log.txt");
    public static PrintStream logger;

    public static void main(String[] args){
        int i=0;

        //try to initiate logger, exit if failed
        boolean status=false;
        try{
            logger=new PrintStream(log);
            status=true;
        } catch (FileNotFoundException e) {
            System.err.println("Log file cannot be written.");
        } catch (SecurityException e) {
            System.err.println("Log write access was denied.");
        }
        if(!status) System.exit(-1);

        System.out.println("\nThis program attempts to use PSO for container scheduling\n");

        //start taking inputs and initialize stuff
        int epochs = takeInt("Enter the number of epochs: ");
        int[] space = takeSpace();
        FitnessDetails fitness = new FitnessDetails(space[1],space[2]);
        Swarm hive = new Swarm(space,fitness);

        //run optimization
        System.out.print("\n");
        try {
            i=search(hive,epochs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\rEpoch "+(i-1)+" : ");
        declare(hive);
        pause();
    }

    //search the space for the given number of epochs, with the given swarm
    private static int search(Swarm hive, int epochs) throws InterruptedException {
        int i;
        float[] parameters = takeParameters();
        System.out.print("\n");
        for (i=0; i<epochs; i++){
            //TimeUnit.MILLISECONDS.sleep(3);
            System.out.print("\rEpoch "+i+" : ");
            if(hive.update(parameters)){
                declare(hive);
            }
            else{
                System.out.print("No change");
            }
            logParticles(hive,i);
        }
        return i;
    }

    //announce the global best position so far and its fitness value
    private static void declare(Swarm hive){
        System.out.print("          \n");
        System.out.println("\tGlobal best position = "+hive.getBestPos().toString());
        System.out.println("\tGlobal best evaluation = "+hive.getBestEval());
    }

    //take swarm size, number of containers (dimension) and number of hosts (upper bound, exclusive) from user input
    private static int[] takeSpace(){
        int[] space = new int[3];
        space[0]=takeInt("Enter the number of particles: ");
        space[1]=takeInt("Enter the number of containers: ");
        space[2]=takeInt("Enter the number of hosts: ");
        return space;
    }

    //take inertia (<1), cognitive component and social component from user input
    private static float[] takeParameters(){
        float[] parameters = new float[4];
        parameters[0]=takeFloat("Enter inertia: ",1);
        parameters[1]=takeFloat("Enter cognitive component: ",Float.POSITIVE_INFINITY);
        parameters[2]=takeFloat("Enter social component: ",Float.POSITIVE_INFINITY);
        parameters[3]=takeFloat("Enter limit of randomness: ",Float.POSITIVE_INFINITY);
        return parameters;
    }

    //take int input from user (>0)
    private static int takeInt(String msg) {
        int num;
        while (true) {
            System.out.print(msg);
            try{
                num = input.nextInt();
                if (num <= 0) {
                    System.out.println("This input must be a positive integer.");
                } else {
                    break;
                }
            }catch(Exception e){
                System.out.println("Invalid input.");
            }
        }
        return num;
    }

    //take float input from user (0 < input < given limit)
    private static float takeFloat(String msg, float limit) {
        float num;
        while (true) {
            System.out.print(msg);
            try{
                num = input.nextFloat();
                if (num<=0 || num>=limit) {
                    System.out.println("Number must be positive and less than "+limit+".");
                } else {
                    break;
                }
            }catch(Exception e){
                System.out.println("Invalid input.");
            }
        }
        return num;
    }

    //log the position and velocity of all particles in the given swarm
    private static void logParticles(Swarm hive, int i){
        System.setOut(logger);
        System.out.println("\nEPOCH "+i+" :");
        System.out.println("\nPositions");
        hive.printPositions();
        System.out.println("\nVelocities");
        hive.printVelocities();
        System.setOut(console);
    }

    //attempt to clear the console output screen
    public static void cls(){
        try{
            if(System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }catch(IOException | InterruptedException ex){
            System.out.print("\n");
        }
    }

    //attempt to hold the console output screen
    private static void pause(){
        try{
            System.out.print("\n");
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "echo Press any key to exit && pause >nul").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("read -p \"Press any key to exit\"");
        }catch(IOException | InterruptedException ex){
            System.out.println("\nRuntime process failed (pause).");
        }
    }

}
