import java.util.*;

public class Particle {

    Vector position;
    Vector velocity;
    Vector bestPos;
    float bestEval;
    final FitnessDetails fitness;
    Vector globalBestPos;

    public Particle(int dimension, int range, FitnessDetails fitness){
        position=new Vector(dimension, range, true);//true=cannot be less than 0
        velocity=new Vector(dimension, range, false);
        bestPos=position;
        bestEval=fitness.eval(bestPos);
        this.fitness=fitness;
    }

    public Vector getPosition(){
        return position;
    }

    public Vector getVelocity(){
        return velocity;
    }

    public Vector getBestPos(){
        return bestPos;
    }

    public float getBestEval(){
        return bestEval;
    }

    public float update(float[] parameters){
        updatePos();
        updateVel(parameters);
        updatePersonal();
        return bestEval;
    }

    private void updatePos(){
        position=position.add(velocity);
    }

    private void updateVel(float[] parameters){
        float inertia=parameters[0];
        float cognitive=parameters[1];
        float social=parameters[2];
        float randLim=parameters[3];

        Random impulse=new Random();
        Vector[] terms=new Vector[3];
        Vector pDist=bestPos.sub(position);
        Vector gDist=globalBestPos.sub(position);

        terms[0]=velocity.mul(inertia);//old velocity * inertia
        terms[1]=pDist.mul(cognitive*(impulse.nextFloat(randLim+1)));//cognitive component * random
        terms[2]=gDist.mul(social*(impulse.nextFloat(randLim+1)));//social component * random

        velocity=terms[0].add(terms[1]).add(terms[2]);
    }

    private void updatePersonal(){
        float currEval=fitness.eval(position);
        if(currEval<fitness.eval(bestPos)){
            bestPos=position;
            bestEval=currEval;
        }
    }

    public void updateGlobal(Vector globalBestPos){
        this.globalBestPos=globalBestPos;
    }

}
