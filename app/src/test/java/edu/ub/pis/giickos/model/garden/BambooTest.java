package edu.ub.pis.giickos.model.garden;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.UUID;

public class BambooTest extends TestCase {

    public void testCanBeWatered() {
        Bamboo bamboo = new Bamboo(0, "Test", new HashMap<>(), 0, 0, UUID.randomUUID().toString());

        // Bamboo can be watered once per day.
        assertTrue(bamboo.canBeWatered());

        assertTrue(bamboo.water());

        // Should no longer be waterable again in the same day
        assertFalse(bamboo.canBeWatered());
        assertFalse(bamboo.water());

        // And should once again be waterable if the day changes
        bamboo.setLastWatered(0);
        assertTrue(bamboo.canBeWatered());
    }

    public void testHarvest() {
        Bamboo bamboo = new Bamboo(0, "Test", new HashMap<>(), 0, 1, UUID.randomUUID().toString());

        // Bamboo is harvestable once growth reaches the maxGrowth
        // In this test it needs to be watered once (to go from 0 -> 1 growth)
        assertFalse(bamboo.canHarvest());

        bamboo.water();

        assertTrue(bamboo.canHarvest());

        // Check that the method still works if the bamboo is watered beyond
        // its requirement
        bamboo.setLastWatered(0);
        bamboo.water();

        assertTrue(bamboo.canHarvest());
    }
}