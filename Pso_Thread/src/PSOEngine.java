import static sun.security.util.FilePermCompat.nb;

public class PSOEngine {

    int numDimensions;
    int numParticles;
    int maxIterations;
    double c1;
    double c2;
    double w;
    public PSOEngine (int numDimensions, int numParticles, int maxIterations, double c1, double c2, double w) {
        this.numDimensions = numDimensions;
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
    }

    public void initParticles(Particle[] particles) {
        int a = 5;

        //For each particle
        for (int i=0; i<particles.length;i++) {
            double[] positions = new double[numDimensions];
            double[] velocities = new double [numDimensions];

            for (int j=0; j<numDimensions; j++) {
                positions[j] = ((Math.random()* ((5.12-(-5.12)))) - 5.12);
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

    public double evaluateFitness(double[] positions) {
        double fitness = 0;

        for (int i=0; i<numDimensions; i++) {
            fitness = fitness + (Math.pow(positions[i],2)-(10*Math.cos(2*Math.PI*positions[i])));
        }
        fitness = fitness + (10*numDimensions);
        return fitness;

        }
}