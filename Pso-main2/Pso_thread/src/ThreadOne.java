public class ThreadOne extends Thread {
    Particle[] particles;
    PSOEngine PSO;

    public ThreadOne(Particle[] particles, PSOEngine PSO) {
        this.particles = particles;
        this.PSO = PSO;
    }

    public void findPersonalBest(int end) {

        for (int start = 0; start < end; start++) {
            particles[start].fitness = PSO.evaluateFitness(particles[start].position);
            //update personal best position
            if (particles[start].fitness <= PSO.evaluateFitness(particles[start].personalBest)) {
                particles[start].personalBest = particles[start].position.clone();
            }
        }
    }
}

