package it.pt.mt.srv.controller;

import it.pt.mt.srv.controller.network.IConnessione;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegistroConnessioniTest {

    /**
     * Test of class RegistroConnessioni.
     */
    @Test
    public void test() {
        RegistroConnessioni r = RegistroConnessioni.getInstance();
        assertEquals(r, RegistroConnessioni.getInstance());

        assertNull(r.getConnessione("a@b.it"));

        IConnessione c0 = mock(IConnessione.class);
        IConnessione c1 = mock(IConnessione.class);
        IConnessione c2 = mock(IConnessione.class);
        IConnessione c3 = mock(IConnessione.class);
        IConnessione c4 = mock(IConnessione.class);

        r.aggiungiConnessione("10.0.1.2", c0);
        r.aggiungiConnessione("c@d.com", c0);
        r.aggiungiConnessione("2001:0db8:85a3:0042:1000:8a2e:0370:7334", c1);
        r.aggiungiConnessione("e@f.com", c1);
        r.aggiungiConnessione("g@h.com", c2);
        r.aggiungiConnessione("32@esempio.com", c3);
        r.aggiungiConnessione("64@esempio.com", c4);

        assertEquals(c0, r.getConnessione("10.0.1.2"));
        assertEquals(c0, r.getConnessione("c@d.com"));
        assertEquals(c1,
                r.getConnessione("2001:0db8:85a3:0042:1000:8a2e:0370:7334"));
        assertEquals(c1, r.getConnessione("e@f.com"));
        assertEquals(c2, r.getConnessione("g@h.com"));
        assertEquals(c3, r.getConnessione("32@esempio.com"));
        assertEquals(c4, r.getConnessione("64@esempio.com"));

        r.rimuoviConnessione("10.0.1.2");
        assertNull(r.getConnessione("10.0.1.2"));
        assertEquals(c0, r.getConnessione("c@d.com"));
    }
}