package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
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
public class GestoreMessaggioSegreteriaTest {

    /**
     * Test of esegui method, of class GestoreMessaggioSegreteria.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato a = mock(IUtenteRegistrato.class);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);

        GestoreMessaggioSegreteria g = new GestoreMessaggioSegreteria();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        g.setMittente(c);
        g.setDestinatario("b@c.com");
        g.setMessaggio("messaggio");
        g.esegui();

        verify(a).nuovoMessaggio("a@b.com", "messaggio");
        verify(n).setOriginale("MessaggioSegreteria");
        verify(n).setRiuscita(true);
        verify(c).send("not");
    }

    /**
     * Test of esegui method, of class GestoreMessaggioSegreteria.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("b@c.com"))
                .thenThrow(new ConditionException());

        GestoreMessaggioSegreteria g = new GestoreMessaggioSegreteria();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setDestinatario("b@c.com");
        g.setMessaggio("messaggio");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreMessaggioSegreteria.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreMessaggioSegreteria g = new GestoreMessaggioSegreteria();
        g.setDestinatario("b@c.com");
        // manca messaggio
        g.esegui();
    }
    /**
     * Test of esegui method, of class GestoreMessaggioSegreteria.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("b@c.com"))
                .thenThrow(new ConditionException());

        GestoreMessaggioSegreteria g = new GestoreMessaggioSegreteria();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        //manca destinatario
        g.setMessaggio("messaggio");
        g.esegui();
    }
}