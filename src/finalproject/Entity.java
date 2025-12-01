/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject;

/** Describes an entity with kinematic properties that produces and observes
 * and emitted frequency.
 *
 * @author Yasmine and Jacques
 */
public class Entity {
    private double position;
    private double velocity;
    private double acceleration;
    
    private double sourceFrequency;
    private double observedFrequency;
    
    private double maxPosition = Double.MAX_VALUE;

    public Entity() {
        
    }
    
    public Entity(double sourceFrequency) {
        this.sourceFrequency = sourceFrequency;
    }
    
    public Entity(double position, double velocity, double acceleration, double sourceFrequency) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.sourceFrequency = sourceFrequency;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = Math.max(0, Math.min(position, maxPosition));
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getSourceFrequency() {
        return sourceFrequency;
    }

    public void setSourceFrequency(double sourceFrequency) {
        this.sourceFrequency = sourceFrequency;
    }

    public double getObservedFrequency() {
        return observedFrequency;
    }

    public void setObservedFrequency(double observedFrequency) {
        this.observedFrequency = observedFrequency;
    }

    public double getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(double maxPosition) {
        this.maxPosition = maxPosition;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.position) ^ (Double.doubleToLongBits(this.position) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.velocity) ^ (Double.doubleToLongBits(this.velocity) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.acceleration) ^ (Double.doubleToLongBits(this.acceleration) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.sourceFrequency) ^ (Double.doubleToLongBits(this.sourceFrequency) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.observedFrequency) ^ (Double.doubleToLongBits(this.observedFrequency) >>> 32));
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
        final Entity other = (Entity) obj;
        if (Double.doubleToLongBits(this.position) != Double.doubleToLongBits(other.position)) {
            return false;
        }
        if (Double.doubleToLongBits(this.velocity) != Double.doubleToLongBits(other.velocity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.acceleration) != Double.doubleToLongBits(other.acceleration)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sourceFrequency) != Double.doubleToLongBits(other.sourceFrequency)) {
            return false;
        }
        return Double.doubleToLongBits(this.observedFrequency) == Double.doubleToLongBits(other.observedFrequency);
    }
}
