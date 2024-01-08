public class Threadd implements Runnable {
    Particle[] particles;
    PSOEngine PSO;
    private boolean done = false;
    public double[] best;
    public int[] processingTimes;
    public int nbMachines;
    public Threadd(Particle[] particles, PSOEngine PSO, int[] processingTimes, int nbMachines) {
        this.particles = particles;
        this.PSO = PSO;
        this.processingTimes = processingTimes;
        this.nbMachines = nbMachines;
    }

    public void run() {
        for (int i = 0; i < (particles.length); i++) {
            particles[i].fitness = PSO.evaluateFitness(particles[i].position, processingTimes, nbMachines);
            //update personal best position
            synchronized (this) {
                if (particles[i].fitness <= PSO.evaluateFitness(particles[i].personalBest, processingTimes, nbMachines)) {
                    particles[i].personalBest = particles[i].position.clone();
                }
            }
            //Find best particle in set
            best = PSO.findBest(particles);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        done = true;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public double[] getValue() throws InterruptedException {
        synchronized (this) {
            if (!done) {
                System.out.println(Thread.currentThread().getName()+ " is waiting");
                this.wait();
            }
        }
        return this.best;
    }
}