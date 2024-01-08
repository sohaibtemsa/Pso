public class PSOimplementation {

    int numDimensions = 30; //Number of dimensions for problem
    int numParticles = 300; //Number of particles in swarm
    int maxIterations = 5000; //Max number of iterations
    double c1 = 1.496180; //Cognitive coefficient
    double c2 = 1.496180; //Social coefficient
    double w = 0.729844; //Inertia coefficient
    public  double[] r1;
    public  double[] r2;
    public double[] best;
    Particle[] particles; //Array to hold all particles
    public int nbr_client1;
    public int nbr_choice_entrepots1;

    public PSOimplementation(int nbr_choice_entrepots, int nbr_client, int[][] c_i_j, double[] f_j) {
        //PSO algorithm
//        (nbr_client + 1) : + 1 ---> pour y(j) (tableau des entrepots)
        numDimensions = (nbr_choice_entrepots) * (nbr_client + 1);
        nbr_client1 = nbr_client;
        nbr_choice_entrepots1 = nbr_choice_entrepots;
        particles = new Particle[numParticles];
        PSOEngine PSO = new PSOEngine(numDimensions, numParticles, maxIterations, c1, c2, w, c_i_j, f_j, nbr_choice_entrepots);

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
            r1 = new double[numDimensions];
            r2 = new double[numDimensions];
            for (int i=0; i < numDimensions; i++) {
                r1[i] = Math.random();
                r2[i] = Math.random();
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
    }

    public void print (double[] a) {
        System.out.println("\n<-------------------");
        int j = 1;

        System.out.print("ENTREPOTS" + "\t\t");
        for (int i = a.length - nbr_choice_entrepots1 + 1; i <= a.length; i++) {
            System.out.print(a[i - 1]  + "\t\t\t");
        }

        System.out.print("\n\n");
        for (int i = 1; i <= (a.length - nbr_choice_entrepots1); i++) {
            if ((i - 1) % (nbr_choice_entrepots1) == 0) {
                System.out.print("CLIENT" + j + "\t\t\t");
                j++;
            }

            System.out.print(a[i - 1]  + "\t\t\t");

            if (i % (nbr_choice_entrepots1) == 0 && i!= 1) {
                System.out.print("\n");
            }
        }
        System.out.println("------------------->\n");
//            System.out.print("< ");
//            for (int i=0; i<a.length; i++) {
//                System.out.print(a[i]  + " ");
//            }
//            System.out.println(" > ");
    }
}
