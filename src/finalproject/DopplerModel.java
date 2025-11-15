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
        
        updateObservedFrequency(entityA, entityB);
        updateObservedFrequency(entityB, entityA);
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
     * Updates an entity's observed frequency based on the other entity's source frequency
     * @param observer The entity who's observed frequency should be updated
     * @param source The entity who provides the source frequency
     */
    private void updateObservedFrequency(Entity observer, Entity source) {
        double sourceFrequency = source.getSourceFrequency();
        double observedFrequency;
        
        double sourceVelocity = source.getVelocity();
        double observerVelocity = observer.getVelocity();
        
        // <= instead of < since we want the 'source left of observer' equation
        // when both the source and observer are at the same position
        boolean sourceLeftOfObserver = source.getPosition() <= observer.getPosition();
        
        if (sourceLeftOfObserver) {
            observedFrequency = sourceFrequency * (velocityWave - observerVelocity) / (velocityWave - sourceVelocity);
        } else {
            observedFrequency = sourceFrequency * (velocityWave + observerVelocity) / (velocityWave + sourceVelocity);
        }
        
        observer.setObservedFrequency(observedFrequency);
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
