package it.pt.mt.srv.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class EMessaggioTest {

    /**
     * Test of class Messaggio.
     */
    @Test
    public void test() {
        EMessaggio m = new EMessaggio();
        m.setMessaggio("messaggio");
        m.setMittente("mitt@ten.te");
        assertEquals("messaggio", m.getMessaggio());
        assertEquals("mitt@ten.te", m.getMittente());
    }
}