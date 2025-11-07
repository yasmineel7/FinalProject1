/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject;

import java.util.Objects;

/**
 *
 * @author yasmi
 */
public class DopplerModel {
    
    //variables
    private double velocityWave;
    private Entity source;
    private Entity observer;
    private double time;
   
      public DopplerModel(double velocityWave, Entity source, Entity observer, double time) { 
        this.velocityWave = velocityWave;
        this.source = source;
        this.observer = observer;
        this.time = time;
    }
      
    public void updateObservedFrequency() {
        boolean sourceVelocityNull = source.getVelocity() == 0;
        boolean observerVelocityNull = observer.getVelocity() == 0;
        boolean positionSourceLeft = source.getPosition() < observer.getPosition();
        boolean positionSourceRight = source.getPosition() > observer.getPosition();
        boolean sourceVelocityNegative = source.getVelocity() < 0;
        boolean sourceVelocityPositive = source.getVelocity() > 0;
        boolean observerVelocityNegative = observer.getVelocity() < 0;
        boolean observerVelocityPositive = observer.getVelocity() > 0;
        //the velocity is 0 if the observer or source is stationary
        //need a starting position?? to know if going toward/away or changing place depending on the case?
        
        //calculation for the most basic case and assuming that the observer is going toward (that the source position < then observer so going toward)
        if (sourceVelocityNull && positionSourceLeft) {
            observer.setObservedFrequency(source.getSourceFrequency() * ((velocityWave + observer.getVelocity()) / velocityWave));
        }
        
        //basic case and observer is moving away
        if (sourceVelocityNull && positionSourceRight) {
            //frequencyObserver = frequencySource * ((velocityWave - observer.getVelocity()) / velocityWave);
        }
        
        //no one is moving
        if (sourceVelocityNull && observerVelocityNull) {
            //frequencyObserver = frequencySource;
        }
        
        //stationary observer ans source moving toward
        if (observerVelocityNull && positionSourceLeft) {
            //frequencyObserver = frequencySource * (velocityWave / (velocityWave - source.getVelocity()));
        }
        
        //stationary observer and source moving away
        if (observerVelocityNull && positionSourceRight) {
            //frequencyObserver = frequencySource * (velocityWave / (velocityWave + source.getVelocity()));
        }
        
        //observer and source going toward
        if (positionSourceLeft && !sourceVelocityNull && !observerVelocityNull) {
            //frequencyObserver = frequencySource * ((velocityWave + observer.getVelocity()) / (velocityWave - source.getVelocity()));
        }
        
        //observer going toward and source going away ?????
        if (positionSourceRight && sourceVelocityPositive && observerVelocityNegative) {
            
        }
        
        if (position sourceLeft && sourceVelocityNegative )
        //observer going away and source going toward???????
        //observer and source moving away 
        if (positionSourceRight && !sourceVelocityNull&& !observerVelocityNull) {
            frequencyObserver = frequencySource * ((velocityWave - observer.getVelocity() / (velocityWave + source.getVelocity())));
        }    
    }

    @Override
    public int hashCode() {
        int hash = 7;
         hash = 59 * hash + (int) (Double.doubleToLongBits(this.velocityWave) ^ (Double.doubleToLongBits(this.velocityWave) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.source);
        hash = 59 * hash + Objects.hashCode(this.observer);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.time) ^ (Double.doubleToLongBits(this.time) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.frequencySource) ^ (Double.doubleToLongBits(this.frequencySource) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.frequencyObserver) ^ (Double.doubleToLongBits(this.frequencyObserver) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DopplerModel other = (DopplerModel) obj;
        if (Double.doubleToLongBits(this.velocityWave) != Double.doubleToLongBits(other.velocityWave)) {
            return false;
        }
        if (Double.doubleToLongBits(this.time) != Double.doubleToLongBits(other.time)) {
            return false;
        }
        if (Double.doubleToLongBits(this.frequencySource) != Double.doubleToLongBits(other.frequencySource)) {
            return false;
        }
        if (Double.doubleToLongBits(this.frequencyObserver) != Double.doubleToLongBits(other.frequencyObserver)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return Objects.equals(this.observer, other.observer);
    }
    
     public double getVelocityWave() {
        return velocityWave;
    }

    public void setVelocityWave(double velocityWave) {
        this.velocityWave = velocityWave;
    }

    public Entity getSource() {
        return source;
    }

    public void setSource(Entity source) {
        this.source = source;
    }

    public Entity getObserver() {
        return observer;
    }

    public void setObserver(Entity observer) {
        this.observer = observer;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getFrequencySource() {
        return frequencySource;
    }

    public void setFrequencySource(double frequencySource) {
        this.frequencySource = frequencySource;
    }

    public double getFrequencyObserver() {
        return frequencyObserver;
    }

    public void setFrequencyObserver(double frequencyObserver) {
        this.frequencyObserver = frequencyObserver;
    }
 
}
