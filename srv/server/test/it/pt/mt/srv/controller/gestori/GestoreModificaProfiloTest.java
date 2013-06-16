package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class,
    RegistroConnessioni.class})
public class GestoreModificaProfiloTest {

    /**
     * Test of esegui method, of class GestoreModificaProfilo.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        IUtenteRegistrato a2 = mock(IUtenteRegistrato.class); //non connesso
        List<IUtenteRegistrato> aa =
                new ArrayList<IUtenteRegistrato>(Arrays.asList(a, a2));
        when(u.getAmici()).thenReturn(aa);
        when(a.getEmail()).thenReturn("b@c.com");
        when(a2.getEmail()).thenReturn("c@d.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        NotificaListaModifica nm = mock(NotificaListaModifica.class);
        when(nm.serializza()).thenReturn("not-mod");
        NotificaOperazione no = mock(NotificaOperazione.class);
        when(no.serializza()).thenReturn("not-opr");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaModifica()).thenReturn(nm);
        when(f.creaNotificaOperazione()).thenReturn(no);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreModificaProfilo g = new GestoreModificaProfilo();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setStato("stato");
        g.setUsername("nome");
        g.esegui();

        verify(nm).setEmail("a@b.com");
        verify(nm).setStato("stato");
        verify(nm).setUsername("nome");
        verify(c).send("not-opr");
        verify(ca).send("not-mod");
        verifyNoMoreInteractions(ca);
    }

    /**
     * Test of esegui method, of class GestoreModificaProfilo.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreModificaProfilo g = new GestoreModificaProfilo();
        g.setStato("stato");
        //manca nome
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreModificaProfilo.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreModificaProfilo g = new GestoreModificaProfilo();
        //manca stato
        g.setUsername("nome");
        g.esegui();
    }
}