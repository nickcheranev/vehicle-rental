package ru.cheranev.rental.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Cheranev N.
 * created on 26.05.2019.
 */
public class RegNumberSequenceTest {

    @Test
    @Ignore
    public void getNextRegNumber() {
        String nextNumber = RegNumberSequence.getNextRegNumber();
        Assert.assertEquals(nextNumber, "А006АА59");
        nextNumber = RegNumberSequence.getNextRegNumber();
        Assert.assertEquals(nextNumber, "А007АА59");
    }
}