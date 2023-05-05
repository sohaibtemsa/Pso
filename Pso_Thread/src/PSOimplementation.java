public class PSOimplementation {

    int numDimensions = 15; //Number of dimensions for problem
    int numParticles = 1000; //Number of particles in swarm
    int maxIterations = 10000; //Max number of iterations
    double c1 = 1.496180; //Cognitive coefficient
    double c2 = 1.496180; //Social coefficient
    double w = 0.729844; //Inertia coefficient
    public  double[] r1;
    public  double[] r2;
    public double[] best;
    Particle[] particles1; //Array to hold all particles
    Particle[] particles2; //Array to hold all particles
    public double[] best2;

    public PSOimplementation() throws InterruptedException {
        //PSO algorithm
        particles1 = new Particle[numParticles];
//        particles2 = new Particle[(numParticles/2)];

        //Initialize particles
        PSOEngine PSO1 = new PSOEngine(numDimensions, numParticles, maxIterations, c1, c2, w);
//        PSOEngine PSO2 = new PSOEngine(numDimensions, (numParticles/2), maxIterations, c1, c2, w);

        PSO1.initParticles(particles1);
//        PSO2.initParticles(particles2);


        //PSO loop 11555
        int numIter = 0;
        double a = 1;

        while (numIter<maxIterations) {
//            ThreadOne grp2 = new ThreadOne(particles2, PSO2);
//
//            Thread t1 = new Thread(new Runnable() {
//                @Override
//                public synchronized void run() {
//                    grp2.findPersonalBest(numParticles/2);
//                }
//            });
//            t1.start();
//            t1.join();
            // Evaluate fitness of each particle
            for (int i=0; i < (numParticles); i++) {
                particles1[i].fitness = PSO1.evaluateFitness(particles1[i].position);
                //update personal best position
                if (particles1[i].fitness <= PSO1.evaluateFitness(particles1[i].personalBest)) {
                    particles1[i].personalBest = particles1[i].position.clone();
                }
            }

            //Find best particle in set
            best = PSO1.findBest(particles1);
//            best2 = PSO2.findBest(particles1);
//            if (PSO2.evaluateFitness(best2) < PSO1.evaluateFitness(best)) {
//                best = best2;
//            }
            if (PSO1.evaluateFitness(best) == 0) {
                break;
            }

            //Initialize the random vectors for updates
            r1 = new double[numDimensions];
            r2 = new double[numDimensions];
            for (int i=0; i < numDimensions; i++) {
                r1[i] = Math.random();
                r2[i] = Math.random();
            }

            //Update the velocity and position vectors
            for (int i=0; i < (numParticles/2);i++) {
                PSO1.updateVelocity(particles1[i], best, r1, r2);
                PSO1.updatePosition(particles1[i]);
//                PSO2.updateVelocity(particles1[i], best, r1, r2);
//                PSO2.updatePosition(particles1[i]);
            }
            numIter++;

//            a = PSO1.evaluateFitness(best);
        }

        print((best));
        System.out.println(PSO1.evaluateFitness(best));
    }

    public void print (double[] a) {
            System.out.print("< ");
            for (int i=0; i<a.length; i++) {
                System.out.print(a[i]  + " ");
            }
            System.out.println(" > ");
    }
}
