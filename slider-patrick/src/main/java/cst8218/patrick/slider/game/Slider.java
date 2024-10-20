package cst8218.patrick.slider.game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Slider implements Serializable {

    private static final long serialVersionUID = 1L;

    // Constants
    public static final int INITIAL_SIZE = 10;
    public static final int TRAVEL_SPEED = 2;
    public static final int MAX_DIR_CHANGES = 5;
    public static final int DECREASE_RATE = 1;
    public static final int X_LIMIT = 500;
    public static final int Y_LIMIT = 500;
    public static final int SIZE_LIMIT = 100;
    public static final int MAX_TRAVEL_LIMIT = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(1)
    @Max(SIZE_LIMIT)
    private Integer size = INITIAL_SIZE;

    @NotNull
    @Min(0)
    @Max(X_LIMIT)
    private Integer x;

    @NotNull
    @Min(0)
    @Max(Y_LIMIT)
    private Integer y;

    @NotNull
    @Min(-MAX_TRAVEL_LIMIT)
    @Max(MAX_TRAVEL_LIMIT)
    private Integer currentTravel = INITIAL_SIZE;

    @NotNull
    @Min(1)
    @Max(MAX_TRAVEL_LIMIT)
    private Integer maxTravel;

    @NotNull
    private Integer mvtDirection = 1; // 1 for right, -1 for left

    @NotNull
    @Min(0)
    private Integer dirChangeCount = 0;

    // Getters and setters for all properties
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Integer currentTravel) {
        this.currentTravel = currentTravel;
    }

    public Integer getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(Integer maxTravel) {
        this.maxTravel = maxTravel;
    }

    public Integer getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(Integer mvtDirection) {
        this.mvtDirection = mvtDirection;
    }

    public Integer getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(Integer dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }

   /**
 * Updates the properties to simulate the passing of one unit of time.
 */
public void timeStep() {
    // Check if the slider is still allowed to move, i.e., maxTravel is greater than 0
    if (maxTravel > 0) {
        // Update the currentTravel by adding or subtracting TRAVEL_SPEED depending on the direction
        currentTravel += mvtDirection * TRAVEL_SPEED;
        
        // Check if the currentTravel has reached or exceeded the maxTravel distance (either positively or negatively)
        if (Math.abs(currentTravel) >= maxTravel) {
            // Reverse the direction of movement (if moving right, start moving left and vice versa)
            mvtDirection = -mvtDirection;
            
            // Increment the counter for how many times the direction has changed
            dirChangeCount++;
            
            // If the direction has changed more times than the allowed MAX_DIR_CHANGES
            if (dirChangeCount > MAX_DIR_CHANGES) {
                // Decrease the maxTravel distance by a constant DECREASE_RATE (slows down the slider)
                maxTravel -= DECREASE_RATE;
                
                // Reset the direction change counter to 0 (to track future direction changes)
                dirChangeCount = 0;
            }
        }
    }
}


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Slider)) {
            return false;
        }
        Slider other = (Slider) object;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "cst8218.patrick.slider.game.Slider[ id=" + id + " ]";
    }
    
    
}
