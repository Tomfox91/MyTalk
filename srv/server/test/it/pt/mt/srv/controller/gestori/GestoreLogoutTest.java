package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class,
    RegistroConnessioni.class})
public class GestoreLogoutTest {

    /**
     * Test of esegui method, of class GestoreLogout.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        List<IUtenteRegistrato> aa =
                new ArrayList<IUtenteRegistrato>(Arrays.asList(a));
        when(u.getAmici()).thenReturn(aa);
        when(a.getEmail()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        NotificaListaModifica n = mock(NotificaListaModifica.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaModifica()).thenReturn(n);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreLogout g = new GestoreLogout();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.esegui();

        verify(rcon).rimuoviConnessione("a@b.com");

        verify(c).setEmail(null);
        verify(u).setStato("Offline");

        verify(n).setEmail("a@b.com");
        verify(n).setStato("Offline");
        verify(ca).send("not");
    }
}