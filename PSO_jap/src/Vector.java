import java.util.*;

public class Vector {

    public final int dimension;
    public final int range;
    boolean nonNeg;
    private int[] coordinates;

    public Vector(int dimension, int range, boolean nonNeg){
        this.dimension=dimension;
        this.range=range;
        this.nonNeg=nonNeg;
        coordinates=new int[dimension];
        randomize();
    }

    private void randomize(){
        Random chaos=new Random();
        for(int i=0;i<dimension;i++){
            if(nonNeg) setCoordinate(i,chaos.nextInt(range));
            else setCoordinate(i,chaos.nextInt(-range+1,range));
        }
    }

    public Vector add(Vector toAdd){
        Vector sum=new Vector(dimension,range,nonNeg);
        for(int i=0;i<dimension;i++){
            sum.setCoordinate(i,(coordinates[i]+toAdd.getCoordinate(i)));
        }
        return sum;
    }

    public Vector sub(Vector toSub){
        Vector diff=new Vector(dimension,range,false);//nonNeg has to remain false here
        for(int i=0;i<dimension;i++){
            diff.setCoordinate(i,(coordinates[i]-toSub.getCoordinate(i)));
        }
        return diff;
    }

    public Vector mul(float toMul){
        Vector prod=new Vector(dimension,range,nonNeg);
        for(int i=0;i<dimension;i++){
            prod.setCoordinate(i, (toMul*coordinates[i]));
        }
        return prod;
    }

    public int getCoordinate(int axis){
        return coordinates[axis];
    }

    public void setCoordinate(int axis, int coordinate){
        if(nonNeg && (coordinate<0)) coordinate=0;
        if(coordinate>=range) coordinate=(range-1);
        coordinates[axis]=coordinate;
    }

    public void setCoordinate(int axis, float coordinate){
        setCoordinate(axis,Math.round(coordinate));
    }

    public int[] get(){
        return coordinates.clone();
    }

    public String toString(){
        return Arrays.toString(coordinates);
    }

}
