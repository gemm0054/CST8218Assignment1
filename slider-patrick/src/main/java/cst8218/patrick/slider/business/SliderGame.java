package cst8218.patrick.slider.business;

import cst8218.patrick.slider.game.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.EJB;
import java.util.List;

@Singleton
@Startup
public class SliderGame {

    @EJB
    private SliderFacade sliderFacade; // Inject SliderFacade

    private static final int CHANGE_RATE = 60; // 60 updates per second
    private List<Slider> sliders;

    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                // The game loop runs indefinitely
                while (true) {
                    // Update all the sliders and save changes to the database
                    sliders = sliderFacade.findAll();
                    for (Slider slider : sliders) {
                        slider.timeStep(); // Update slider state
                        sliderFacade.edit(slider); // Save the updated slider
                    }

                    // Sleep while waiting for the next frame of the animation
                    try {
                        // Wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long)(1.0 / CHANGE_RATE * 1000));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start(); // Start the thread
    }
}
