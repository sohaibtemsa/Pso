

public class PSOEngine {

    int numDimensions = 1;
    int numParticles = 20;
    int maxIterations = 10000;
    double c1 = 1.496180;
    double c2 = 1.496180;
    double w = 0.729844;
    int[][] c_i_j;
    double[] f_j;
    int nbr_choice_entrepots;
    public PSOEngine (int numDimensions, int numParticles, int maxIterations, double c1, double c2, double w, int[][] c_i_j, double[] f_j, int nbr_choice_entrepots ) {
        this.numDimensions = numDimensions;
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
        this.c_i_j = c_i_j;
        this.f_j = f_j;
        this.nbr_choice_entrepots = nbr_choice_entrepots;
    }

    public void initParticles(Particle[] particles) {
        int a = 5;

        //For each particle
        for (int i=0; i<particles.length;i++) {
            double[] positions = new double[numDimensions];
            double[] velocities = new double [numDimensions];

            for (int j=0; j<numDimensions; j++) {
                positions[j] = Math.random();
//                positions[j] = 0;
//                if (j % nbr_choice_entrepots == 1)
//                    positions[j] = 1;
                velocities[j] = 0;
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

        double[] cognitiveTerm = new double[numDimensions];
        double[] socialTerm = new double[numDimensions];

        for (int i=0; i<numDimensions; i++)
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
        for (int i=0; i<numDimensions; i++) {
            particle.position[i] = (particle.position[i] + particle.velocity[i]);

            if (particle.position[i] >= 0.8 && particle.position[i] <= 1.2)
                particle.position[i] = 1;
            else if (particle.position[i] <= 0.2 && particle.position[i] >= -0.2)
                particle.position[i] = 0;
            if (particle.position[i] >= 0.5)
                particle.position[i] = 1;
            else
                particle.position[i] = 0;
        }
    }

//    do kk while jj idees

    public double[] findBest(Particle[] particles) {
        double[] best = null;
        double bestFitness = Integer.MAX_VALUE;
        double a,b;
        double x;
        a = 0;
        b = 0;
        x = 1;

        best = particles[0].personalBest;

        for(int i=0; i<numParticles; i++) {
//            //contrainte 1: somme(x[i][j]) = 1
//            for(int j=0; j < nbr_choice_entrepots; j++) {
//                x =  x + particles[i].position[j];
//            }

            if (evaluateFitness(particles[i].personalBest)<= bestFitness
                && a <= b && x == 1) {
                bestFitness = evaluateFitness(particles[i].personalBest);
                best = particles[i].personalBest;
            }
        }
        return best;
    }
// public double evaluateFitness(double[] positions) {
//     double fitness = 0.0;
//     double[] usedCapacity = new double[numFacilities]; // tableau pour stocker la capacité utilisée par chaque installation

//     // calcul de la distance entre chaque client et chaque installation
//     for (int i = 0; i < numClients; i++) {
//         double shortestDist = Double.POSITIVE_INFINITY;
//         for (int j = 0; j < numFacilities; j++) {
//             double dist = distanceMatrix[i][j] * positions[j];
//             if (dist < shortestDist) {
//                 shortestDist = dist;
//                 assignments[i] = j; // on attribue le client à l'installation la plus proche
//             }
//         }
//         fitness += shortestDist; // on ajoute la distance au fitness total
//         usedCapacity[assignments[i]] += demands[i]; // on ajoute la demande du client à la capacité utilisée de l'installation
//     }

//     // vérification des contraintes de capacité
//     for (int j = 0; j < numFacilities; j++) {
//         if (usedCapacity[j] > capacities[j]) {
//             fitness += (usedCapacity[j] - capacities[j]) * penaltyFactor; // on ajoute une pénalité proportionnelle à la surcapacité
//         }
//     }

//     return fitness;
// }
    public double evaluateFitness(double[] positions) {
        double fitness1 = 0;
        double fitness2 = 0;
        double fitness = 0;
        int a = (positions.length - nbr_choice_entrepots + 1);

        for (int i = a; i <= positions.length; i++) {
            fitness = fitness + f_j[i - a] * positions[i - 2];
        }

        int j = 0;
        for (int i = 1; i <= (positions.length - nbr_choice_entrepots); i++) {
            if (((i - 1) % nbr_choice_entrepots == 0)  && i != 1)
                j++;
            fitness2 =  fitness2 + c_i_j[j][(i - 1) % nbr_choice_entrepots] * positions[i - 1];
        }
        fitness = fitness1 + fitness2;
        return fitness;

        }
}
