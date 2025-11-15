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
    private Entity entityB;
    private Entity entityA;
    private double time;
   
    public DopplerModel(double velocityWave, Entity source, Entity observer, double time) { 
        this.velocityWave = velocityWave;
        this.entityB = source;
        this.entityA = observer;
        this.time = time;
    }
    
    /**
     * Updates the position, velocity, and observed frequencies of both observers
     * @param dt Delta-time: the time since the last update
     */
    public void update(double dt) {
        updateEntity(entityA, dt);
        updateEntity(entityB, dt);
        
        updateObservedFrequency();
    }
    
    /**
     * Updates an entity's position and velocity depending on the time since
     * last update
     * @param entity The entity to update
     * @param dt Delta-time: the time since the last update
     */
    private void updateEntity(Entity entity, double dt) {
        entity.setVelocity(entity.getVelocity() + entity.getAcceleration() * dt);
        entity.setPosition(entity.getPosition() + entity.getVelocity() * dt);
    }
      
    /**
     * calculate the observed frequency depending on the case chosen 
     * and the position of the entity
     */  
    public void updateObservedFrequency() {
        boolean sourceVelocityNull = entityB.getVelocity() == 0;
        boolean observerVelocityNull = entityA.getVelocity() == 0;
        boolean positionSourceLeft = entityB.getPosition() < entityA.getPosition();
        boolean positionSourceRight = entityB.getPosition() > entityA.getPosition();
        boolean sourceVelocityNegative = entityB.getVelocity() < 0;
        boolean sourceVelocityPositive = entityB.getVelocity() > 0;
        boolean observerVelocityNegative = entityA.getVelocity() < 0;
        boolean observerVelocityPositive = entityA.getVelocity() > 0;
        
        if (entityA.getSourceFrequency() == 0) {
        //calculation for the most basic case and assuming that the entityA is going toward (that the entityB position < then entityA so going toward)
        if (sourceVelocityNull && positionSourceLeft) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave + entityA.getVelocity()) / velocityWave));
        }
        
        //basic case and entityA is moving away
        if (sourceVelocityNull && positionSourceRight) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave - entityA.getVelocity()) / velocityWave));
        }
        
        //no one is moving
        if (sourceVelocityNull && observerVelocityNull) {
           entityA.setObservedFrequency(entityB.getSourceFrequency());
        }
        
        //stationary entityA ans entityB moving toward
        if (observerVelocityNull && positionSourceLeft) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * (velocityWave / (velocityWave - entityB.getVelocity())));
        }
        
        //stationary entityA and entityB moving away
        if (observerVelocityNull && positionSourceRight) {
           entityA.setObservedFrequency(entityB.getSourceFrequency() * (velocityWave / (velocityWave + entityB.getVelocity())));
        }
        
        //observer and entityB going toward
        if (positionSourceLeft && sourceVelocityPositive && observerVelocityNegative) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave + entityA.getVelocity()) / (velocityWave - entityB.getVelocity())));
        }
        
        if (positionSourceRight && sourceVelocityNegative && observerVelocityPositive) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave + entityA.getVelocity()) / (velocityWave - entityB.getVelocity())));
        }
        
        //observer going toward and entityB going away ?????
        if (positionSourceRight && sourceVelocityPositive && observerVelocityPositive) {
            entityA.setObservedFrequency(entityB.getSourceFrequency()* ((velocityWave + entityA.getVelocity()) / (velocityWave + entityB.getVelocity())));
        }
        
        if (positionSourceLeft && sourceVelocityNegative && observerVelocityNegative) {
            entityA.setObservedFrequency(entityB.getSourceFrequency()* ((velocityWave + entityA.getVelocity()) / (velocityWave + entityB.getVelocity())));
        }
        
        //observer going away and entityB going toward???????
        if (positionSourceLeft && sourceVelocityPositive && observerVelocityPositive) {
            entityA.setObservedFrequency(entityB.getSourceFrequency()* ((velocityWave - entityA.getVelocity()) / (velocityWave - entityB.getVelocity())));
        }
        
        if (positionSourceRight && sourceVelocityNegative && observerVelocityNegative) {
            entityA.setObservedFrequency(entityB.getSourceFrequency()* ((velocityWave - entityA.getVelocity()) / (velocityWave - entityB.getVelocity())));
        }
        
        //observer and entityB moving away 
        if (positionSourceRight && observerVelocityNegative && sourceVelocityPositive) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave - entityA.getVelocity() / (velocityWave + entityB.getVelocity()))));
        }    
        
        if (positionSourceLeft && observerVelocityPositive && sourceVelocityNegative) {
            entityA.setObservedFrequency(entityB.getSourceFrequency() * ((velocityWave - entityA.getVelocity() / (velocityWave + entityB.getVelocity()))));
        }   
    }
        
        if (entityB.getSourceFrequency() == 0) {
               //calculation for the most basic case and assuming that the entityA is going toward (that the entityB position < then entityA so going toward)
        if (sourceVelocityNull && positionSourceLeft) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave + entityB.getVelocity()) / velocityWave));
        }
        
        //basic case and entityA is moving away
        if (sourceVelocityNull && positionSourceRight) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave - entityB.getVelocity()) / velocityWave));
        }
        
        //no one is moving
        if (sourceVelocityNull && observerVelocityNull) {
           entityB.setObservedFrequency(entityA.getSourceFrequency());
        }
        
        //stationary entityA ans entityB moving toward
        if (observerVelocityNull && positionSourceLeft) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * (velocityWave / (velocityWave - entityA.getVelocity())));
        }
        
        //stationary entityA and entityB moving away
        if (observerVelocityNull && positionSourceRight) {
           entityB.setObservedFrequency(entityA.getSourceFrequency() * (velocityWave / (velocityWave + entityA.getVelocity())));
        }
        
        //observer and entityB going toward
        if (positionSourceLeft && sourceVelocityPositive && observerVelocityNegative) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave + entityB.getVelocity()) / (velocityWave - entityA.getVelocity())));
        }
        
        if (positionSourceRight && sourceVelocityNegative && observerVelocityPositive) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave + entityB.getVelocity()) / (velocityWave - entityA.getVelocity())));
        }
        
        //observer going toward and entityB going away ?????
        if (positionSourceRight && sourceVelocityPositive && observerVelocityPositive) {
            entityB.setObservedFrequency(entityA.getSourceFrequency()* ((velocityWave + entityB.getVelocity()) / (velocityWave + entityA.getVelocity())));
        }
        
        if (positionSourceLeft && sourceVelocityNegative && observerVelocityNegative) {
            entityB.setObservedFrequency(entityA.getSourceFrequency()* ((velocityWave + entityB.getVelocity()) / (velocityWave + entityA.getVelocity())));
        }
        
        //observer going away and entityB going toward???????
        if (positionSourceLeft && sourceVelocityPositive && observerVelocityPositive) {
            entityB.setObservedFrequency(entityA.getSourceFrequency()* ((velocityWave - entityB.getVelocity()) / (velocityWave - entityA.getVelocity())));
        }
        
        if (positionSourceRight && sourceVelocityNegative && observerVelocityNegative) {
            entityB.setObservedFrequency(entityA.getSourceFrequency()* ((velocityWave - entityB.getVelocity()) / (velocityWave - entityA.getVelocity())));
        }
        
        //observer and entityB moving away 
        if (positionSourceRight && observerVelocityNegative && sourceVelocityPositive) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave - entityB.getVelocity() / (velocityWave + entityA.getVelocity()))));
        }    
        
        if (positionSourceLeft && observerVelocityPositive && sourceVelocityNegative) {
            entityB.setObservedFrequency(entityA.getSourceFrequency() * ((velocityWave - entityB.getVelocity() / (velocityWave + entityA.getVelocity()))));
        }   
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
         hash = 59 * hash + (int) (Double.doubleToLongBits(this.velocityWave) ^ (Double.doubleToLongBits(this.velocityWave) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.entityB);
        hash = 59 * hash + Objects.hashCode(this.entityA);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.time) ^ (Double.doubleToLongBits(this.time) >>> 32));
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
        if (!Objects.equals(this.entityB, other.entityB)) {
            return false;
        }
        return Objects.equals(this.entityA, other.entityA);
    }
    
     public double getVelocityWave() {
        return velocityWave;
    }

    public void setVelocityWave(double velocityWave) {
        this.velocityWave = velocityWave;
    }

    public Entity getEntityB() {
        return entityB;
    }

    public void setEntityB(Entity entityB) {
        this.entityB = entityB;
    }

    public Entity getEntityA() {
        return entityA;
    }

    public void setEntityA(Entity entityA) {
        this.entityA = entityA;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
 
}
