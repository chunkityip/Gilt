package gilts;

import gilts.CantAffordGiltException;
import gilts.Gilt;
import gilts.GiltPortfolio;
import gilts.GiltPricingEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class GiltPortfolioTest {
    @Mock
    static GiltPricingEngine pricingEngine;
    @Mock
    static Gilt sharedGilt;
    static GiltPortfolio sharedPortfolio;
    @BeforeEach
    void setup() {
        pricingEngine = mock(GiltPricingEngine.class);
        sharedGilt = mock(Gilt.class);
        sharedPortfolio = new GiltPortfolio(pricingEngine, 1000);
    }

    @Test
    void createPortfolio() {
        GiltPortfolio p = new GiltPortfolio(pricingEngine, 100);
        assertEquals(100, p.getBalance());
    }

    @Test
    void tickBond() {
        when(sharedGilt.tick()).thenReturn(0.125);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt)), 0);
        p.tick();
        verify(sharedGilt).tick();
        assertEquals(0.125,p.getBalance());
        assertEquals(1, p.getPortfolio().size());
    }

    @Test
    void tickExpiringBond() {
        when(sharedGilt.tick()).thenReturn(103.25);
        when(sharedGilt.expired()).thenReturn(true);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt)), 0);
        p.tick();
        verify(sharedGilt).tick();
        assertEquals(103.25,p.getBalance(), 0.01);
        assertEquals(0, p.getPortfolio().size());
    }

    @Test
    void tickMultipleBonds() {
        Gilt gilt2 = mock(Gilt.class);
        when(sharedGilt.tick()).thenReturn(0.125);
        when(gilt2.tick()).thenReturn(0.75);
        when(sharedGilt.expired()).thenReturn(false);
        when(gilt2.expired()).thenReturn(false);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt, gilt2)), 0);
        p.tick();
        verify(sharedGilt).tick();
        verify(gilt2).tick();
        assertEquals(0.875,p.getBalance(), 0.01);
        assertEquals(2, p.getPortfolio().size());
    }

    @Test
    void tickMultipleBondsOneExpires() {
        Gilt gilt2 = mock(Gilt.class);
        when(sharedGilt.tick()).thenReturn(100.125);
        when(gilt2.tick()).thenReturn(0.75);
        when(sharedGilt.expired()).thenReturn(true);
        when(gilt2.expired()).thenReturn(false);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt, gilt2)), 0);
        p.tick();
        verify(sharedGilt).tick();
        verify(gilt2).tick();
        assertEquals(100.875,p.getBalance(), 0.01);
        assertEquals(1, p.getPortfolio().size());
    }

    // Since the user has balance £150 and the price of Gilt is 100. Therefore:
    // 150-100 = 50;
    // Getting 1 share at the user portfolio
    @Test
    void buyBond() {
        when(pricingEngine.getPrice(sharedGilt)).thenReturn(100.0);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(), 150);
        p.buyGilt(sharedGilt);
        assertEquals(1, p.getPortfolio().size());
        assertEquals(50, p.getBalance(), 0.01);
    }

    @Test
    void buyBondBoundary() {
        when(pricingEngine.getPrice(sharedGilt)).thenReturn(150.0);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(), 150.0);
        p.buyGilt(sharedGilt);
        assertEquals(1, p.getPortfolio().size());
        assertEquals(0, p.getBalance(), 0.01);
    }

    @Test
    void buyBondCantAfford() {
        when(pricingEngine.getPrice(sharedGilt)).thenReturn(200.0);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(), 150);
        assertThrows(CantAffordGiltException.class, () -> p.buyGilt(sharedGilt));
        assertEquals(0, p.getPortfolio().size());
        assertEquals(150, p.getBalance(), 0.01);
    }

    @Test
    void sellBond() {
        when(pricingEngine.getPrice(sharedGilt)).thenReturn(150.0);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt)), 0);
        p.sellGilt(sharedGilt);
        assertEquals(0, p.getPortfolio().size());
        assertEquals(150, p.getBalance(), 0.01);
    }

    @Test
    void sellOneBondOfTwo() {
        Gilt gilt2 = mock(Gilt.class);
        when(pricingEngine.getPrice(sharedGilt)).thenReturn(150.0);
        when(pricingEngine.getPrice(gilt2)).thenReturn(100.0);
        GiltPortfolio p = new GiltPortfolio(pricingEngine, new ArrayList<>(Arrays.asList(sharedGilt, gilt2)), 100.0);
        p.sellGilt(sharedGilt);
        assertEquals(250.00,p.getBalance(), 0.01);
        assertEquals(1, p.getPortfolio().size());
    }
}
