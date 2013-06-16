package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.util.ConditionException;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class})
public class GestoreRegistrazioneTest {

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);

        GestoreUtenti gut = mock(GestoreUtenti.class);


        GestoreRegistrazione g = new GestoreRegistrazione();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        g.setMittente(c);
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.setUsername("user");
        g.setDomanda("dom");
        g.setRisposta("risp");
        g.esegui();

        verify(gut).nuovoUtente("a@b.com", "user", "password", "dom", "risp");
        verify(n).setOriginale("Registrazione");
        verify(n).setRiuscita(true);
        verify(c).send("not");
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.nuovoUtente(anyString(), anyString(), anyString(),
                anyString(), anyString())).thenThrow(new ConditionException());

        GestoreRegistrazione g = new GestoreRegistrazione();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.setUsername("user");
        g.setDomanda("dom");
        g.setRisposta("risp");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreRegistrazione g = new GestoreRegistrazione();
        //manca email
        g.setPassword("password");
        g.setUsername("user");
        g.setDomanda("dom");
        g.setRisposta("risp");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreRegistrazione g = new GestoreRegistrazione();
        g.setEmail("a@b.com");
        //manca password
        g.setUsername("user");
        g.setDomanda("dom");
        g.setRisposta("risp");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr3() throws Exception {
        GestoreRegistrazione g = new GestoreRegistrazione();
        g.setEmail("a@b.com");
        g.setPassword("password");
        //manca username
        g.setDomanda("dom");
        g.setRisposta("risp");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr4() throws Exception {
        GestoreRegistrazione g = new GestoreRegistrazione();
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.setUsername("user");
        //manca domanda
        g.setRisposta("risp");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRegistrazione.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr5() throws Exception {
        GestoreRegistrazione g = new GestoreRegistrazione();
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.setUsername("user");
        g.setDomanda("dom");
        //manca risposta
        g.esegui();
    }
}