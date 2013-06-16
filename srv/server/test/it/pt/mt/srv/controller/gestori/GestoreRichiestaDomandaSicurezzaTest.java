package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaDomandaSicurezza;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class})
public class GestoreRichiestaDomandaSicurezzaTest {

    /**
     * Test of esegui method, of class GestoreRichiestaDomandaSicurezza.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);

        NotificaDomandaSicurezza n = mock(NotificaDomandaSicurezza.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaDomandaSicurezza()).thenReturn(n);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getDomanda()).thenReturn("domanda");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        GestoreRichiestaDomandaSicurezza g =
                new GestoreRichiestaDomandaSicurezza();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);

        g.setMittente(c);
        g.setEmail("a@b.com");
        g.esegui();

        verify(n).setDomanda("domanda");
        verify(c).send("not");
    }

    /**
     * Test of esegui method, of class GestoreDomandaSicurezza.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com"))
                .thenThrow(new ConditionException());

        GestoreRichiestaDomandaSicurezza g =
                new GestoreRichiestaDomandaSicurezza();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setEmail("a@b.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreDomandaSicurezza.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreRichiestaDomandaSicurezza g =
                new GestoreRichiestaDomandaSicurezza();
        //manca email
        g.esegui();
    }
}