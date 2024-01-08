import java.util.Random;
public class PSOEngine {

    //int numDimensions = 30; //Number of dimensions for problem
    int nbJobs = 2; //Number of tache
    int nbMachines = 2; //Number of dimensions for problem
    int[] processingTimes = new int[]{2,3,1,4};
    int numParticles;
    int maxIterations = 10000;
    double c1 = 1.0;
    double c2 = 1.0;
    double w = 1.0;
    public PSOEngine (int nbJobs, int numParticles, int maxIterations, double c1, double c2, double w) {
        this.nbJobs = nbJobs;
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
    }

    public void initParticles(Particle[] particles) {
        //For each particle
        for (int i=0; i<particles.length;i++) {
            double[] positions = new double[nbJobs];
            double[] velocities = new double [nbJobs];

            for (int j=0; j<nbJobs; j++) {
                //générer des entiers aléatoires entre 0 et nbMachines
                Random rand = new Random();
                positions[j] = rand.nextInt(nbMachines);
                velocities[j] = rand.nextInt(nbMachines);
            }
            //Create the particle
            particles[i] = new Particle(positions, velocities);
            //Set particles personal best to initialized values
            particles[i].personalBest = particles[i].position.clone();
        }
    }

    public void updateVelocity(Particle particle, double[] best, double[] r1, double[] r2) {
        //First we clone the velocities, positions, personal and neighbourhood best
        double[] velocities = particle.velocity.clone();
        double[] personalBest = particle.personalBest.clone();
        double[] positions = particle.position.clone();
        double[] bestNeigh = best.clone();

        double[] cognitiveTerm = new double[nbJobs];
        double[] socialTerm = new double[nbJobs];

        for (int i=0; i<nbJobs; i++)
        {
        //Calculate c1*r1*(personal best - current position)
            cognitiveTerm[i] = c1*r1[i] * (personalBest[i] - positions[i]);
        //Calculate c2*r2*(bestNeigh[i] - positions[i])
            socialTerm[i] = c2 * r2[i] * (bestNeigh[i] - positions[i]);
        //Update particles velocity at all dimensions
            particle.velocity[i] = w * velocities[i] + cognitiveTerm[i] + socialTerm[i];
        }
    }

    public void updatePosition(Particle particle) {
        //Since new position is ALWAYS calculated after calculating new velocity, it is okay to just add old position to the current velocity (as velocity would have already been updated).
        for (int i=0; i<nbJobs; i++) {
            particle.position[i] = (particle.position[i] + particle.velocity[i]);
        }
    }

    public double[] findBest(Particle[] particles) {
        double[] best = null;
        double bestFitness = Integer.MAX_VALUE;

        best = particles[0].personalBest;

        for(int i=0; i<numParticles; i++) {

            if (evaluateFitness(particles[i].personalBest, processingTimes, nbMachines)<= bestFitness) {
                bestFitness = evaluateFitness(particles[i].personalBest, processingTimes, nbMachines);
                best = particles[i].personalBest;
            }
        }
        return best;
    }

    public static int evaluateFitness(double[] individual, int[] processingTimes, int numMachines) {
        int[] machineTime = new int[numMachines];
        for (int machine = 1; machine <= numMachines; machine++) {
            for (int gene = 0; gene < individual.length; gene++) {
                if (individual[gene] == machine) {
                    machineTime[(int) individual[gene] - 1] += processingTimes[gene];
                }
            }
        }
        int maxTime = Integer.MIN_VALUE;
        for (int time : machineTime) {
            maxTime = Math.max(maxTime, time);
        }
        return maxTime;
    }

//    public double evaluateFitness(double[] positions, int[] processingTimes, int numMachines) {
//        double fitness = 0;
//        for (int i=0; i<nbJobs; i++) {
//            fitness = fitness + (Math.pow(positions[i],2));
//        }
//        return fitness;
//    }

}