import java.util.Random;

public class PSOimplementation {

    //int numDimensions = 30; //Number of dimensions for problem
    int nbJobs = 10; //Number of tache
    int nbMachines = 2; //Number of dimensions for problem
    int numParticles = 300; //Number of particles in swarm
    int maxIterations = 5000; //Max number of iterations
    public final double c1 = 1.496180; //Cognitive coefficient
    public final double c2 = 1.496180; //Social coefficient
    public final double w = 0.729844; //Inertia coefficient
    public  double[] r1;
    public  double[] r2;
    public double[] best;
    Particle[] particles; //Array to hold all particles
    public PSOimplementation() {
        //PSO algorithm
        particles = new Particle[numParticles];
        PSOEngine PSO = new PSOEngine(nbJobs, numParticles, maxIterations, c1, c2, w);

        //Initialize particles
        PSO.initParticles(particles);

        //PSO loop
        int numIter = 0;

        while (numIter < maxIterations) {
            // Evaluate fitness of each particle
            for (int i=0; i < numParticles; i++) {
                particles[i].fitness = PSO.evaluateFitness(particles[i].position);
                //update personal best position
                if (particles[i].fitness <= PSO.evaluateFitness(particles[i].personalBest)) {
                    particles[i].personalBest = particles[i].position.clone();
                }
            }

            //Find best particle in set
            best = PSO.findBest(particles);

            //Initialize the random vectors for updates
            r1 = new double[nbJobs];
            r2 = new double[nbJobs];
            for (int i=0; i < nbJobs; i++) {
                //générer des entiers aléatoires entre 0 et 1
                Random rand = new Random();
                //r1[i] = rand.nextInt(2);
                //r2[i] = rand.nextInt(2);
                r1[i] = rand.nextInt(nbMachines);
                r2[i] = rand.nextInt(nbMachines);
            }

            //Update the velocity and position vectors
            for (int i=0; i < numParticles;i++) {
                PSO.updateVelocity(particles[i], best, r1, r2);
                PSO.updatePosition(particles[i]);
            }
            numIter++;
        }

        print((best));
        System.out.println(PSO.evaluateFitness(best));
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
