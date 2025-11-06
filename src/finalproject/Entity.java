/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject;

/**
 *
 * @author yasmi
 */
public class Entity extends Model {
    private double position;
    private double velocity;
    private double acceleration;

    public Entity(double position, double velocity, double acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.position) ^ (Double.doubleToLongBits(this.position) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.velocity) ^ (Double.doubleToLongBits(this.velocity) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.acceleration) ^ (Double.doubleToLongBits(this.acceleration) >>> 32));
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
        return Double.doubleToLongBits(this.acceleration) == Double.doubleToLongBits(other.acceleration);
    }
    
    
}
