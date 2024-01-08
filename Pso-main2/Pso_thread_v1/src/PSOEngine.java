import java.util.Random;
public class PSOEngine {

    //int numDimensions = 30; //Number of dimensions for problem
    int nbJobs = 2; //Number of tache
    int nbMachines = 2; //Number of dimensions for problem
    int numParticles = 20;
    int maxIterations = 10000;
    double c1 = 1.0;
    double c2 = 1.0;
    double w = 1.0;
    int[][] temps = new int[][]{{28, 22},{28, 22} };
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
                //générer des entiers aléatoires entre 0 et 1
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

            if (evaluateFitness(particles[i].personalBest)<= bestFitness) {
                bestFitness = evaluateFitness(particles[i].personalBest);
                best = particles[i].personalBest;
            }
        }
        return best;
    }

//    public double evaluateFitness(double[] positions) {
//        double[] fin = new double[nbMachines]; // temps de fin pour chaque machine
//        double[] debut = new double[nbJobs]; // temps de début pour chaque tâche
//
//        // Parcours des tâches dans l'ordre spécifié
//        for (int i = 0; i < nbJobs; i++) {
//            int tache = (int) positions[i];
//            // Parcours des machines
//            for (int j = 0; j < nbMachines; j++) {
//                if (j == 0) {
//                    debut[tache] = fin[0];
//                } else {
//                    debut[tache] = Math.max(debut[tache], fin[j]);
//                }
//                fin[j] = debut[tache] + temps[tache][j];
//            }
//        }
//        // Calcul de la durée totale
//        double dureeTotale = 0;
//        for (int i = 0; i < nbMachines; i++) {
//            dureeTotale = Math.max(dureeTotale, fin[i]);
//        }
//        return dureeTotale; // On cherche à minimiser la durée totale
//    }
    public double evaluateFitness(double[] positions) {
        double fitness = 0;

        for (int i = 0; i < nbJobs; i++){
            fitness = fitness + Math.pow(positions[i], 2);
        }
        return fitness;
    }

}