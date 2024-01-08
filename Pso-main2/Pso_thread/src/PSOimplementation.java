import java.util.Random;

public class PSOimplementation {

    //int numDimensions = 30; //Number of dimensions for problem
    int nbJobs = 4; //Number of tache
    int nbMachines = 2; //Number of dimensions for problem
    int[] processingTimes = new int[]{2,3,1,4};
    int numParticles = 300; //Number of particles in swarm
    int maxIterations = 5000; //Max number of iterations
    public final double c1 = 1.496180; //Cognitive coefficient
    public final double c2 = 1.496180; //Social coefficient
    public final double w = 0.729844; //Inertia coefficient
    public  double[] r1;
    public  double[] r2;
    public double[] best;
    Particle[] p1;//Array to hold all particles
    Particle[] p2;//Array to hold all particles
    public PSOimplementation() throws InterruptedException {
        //PSO algorithm
//        particles = new Particle[numParticles];
        p1 = new Particle[(numParticles/2)];
        p2 = new Particle[(numParticles/2)];

        PSOEngine PSO = new PSOEngine(nbJobs, (numParticles/2), maxIterations, c1, c2, w);

        //Initialize particles
        PSO.initParticles(p1);
        PSO.initParticles(p2);

        //PSO loop
        int numIter = 0;

        while (numIter < maxIterations) {

            Threadd task1 = new Threadd(p1, PSO, processingTimes, nbMachines);
            Thread t1 = new Thread(task1);
            t1.start();
            Threadd task2 = new Threadd(p2, PSO, processingTimes, nbMachines);
            Thread t2 = new Thread(task2);
            t2.start();

            best = PSO.findBest(p1);

            //best = task1.getValue();
            if (PSO.evaluateFitness(PSO.findBest(p2), processingTimes, nbMachines)
                    < PSO.evaluateFitness(best, processingTimes, nbMachines))
                best = PSO.findBest(p2);
                //best = task2.getValue();



            //Initialize the random vectors for updates
            r1 = new double[nbJobs];
            r2 = new double[nbJobs];
            for (int i=0; i < nbJobs; i++) {
                //générer des entiers aléatoires entre 0 et 1
                Random rand = new Random();
                r1[i] = Math.random();
                r2[i] = Math.random();
                //r1[i] = rand.nextInt(nbMachines);
                //r2[i] = rand.nextInt(nbMachines);
            }

            //Update the velocity and position vectors
            for (int i=0; i < (numParticles/2);i++) {
                PSO.updateVelocity(p1[i], best, r1, r2);
                PSO.updatePosition(p2[i]);
            }
            numIter++;
        }

        print((best));
        System.out.println(PSO.evaluateFitness(best, processingTimes, nbMachines));
        System.out.println(numIter);
    }

    public void print (double[] a) {
            System.out.print("< ");
            for (int i=0; i<a.length; i++) {
                System.out.print(a[i]  + " ");
            }
            System.out.println(" > ");
    }
}
