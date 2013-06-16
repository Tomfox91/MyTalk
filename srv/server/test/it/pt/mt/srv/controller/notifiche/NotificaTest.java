package it.pt.mt.srv.controller.notifiche;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificaTest {

    /**
     * Test of serializza method, of class Notifica.
     */
    @Test
    public void testSerializza() {
        assertEquals(
                "{\"tipo\":\"Finta\",\"evento\":{\"pippo\":\"pluto\",\"lista\":[21,42,84]}}",
                new NotificaFinta().serializza());
    }

    /**
     * Test of class NotificaOperazione.
     */
    @Test
    public void testNotificaOperazione() {
        NotificaOperazione n = new NotificaOperazione();
        n.setOriginale("RichiestaOriginale");
        n.setRiuscita(true);
        assertEquals("{\"tipo\":\"Operazione\",\"evento\":{\"riuscita\":true,\"originale\":\"RichiestaOriginale\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaDomandaSicurezza.
     */
    @Test
    public void testNotificaDomandaSicurezza() {
        NotificaDomandaSicurezza n = new NotificaDomandaSicurezza();
        n.setDomanda("domanda?");
        assertEquals("{\"tipo\":\"DomandaSicurezza\",\"evento\":{\"domanda\":\"domanda?\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaEsitiRicerca.
     */
    @Test
    public void testNotificaEsitiRicerca() {
        NotificaEsitiRicerca n = new NotificaEsitiRicerca();
        Map<String, String> m = new HashMap<String, String>();
        m.put("nome@dominio.com", "Nome Cognome");
        n.setMappa(m);
        assertEquals("{\"tipo\":\"EsitiRicerca\",\"evento\":{\"mappa\":{\"nome@dominio.com\":\"Nome Cognome\"}}}",
                n.serializza());
    }

    /**
     * Test of class NotificaSegnale.
     */
    @Test
    public void testNotificaSegnale() {
        NotificaSegnale n = new NotificaSegnale();
        n.setMittente("tu@me.com");
        n.setSottotipo("#####");
        n.setPorta(42);
        n.setSegnale(JsonNodeFactory.instance.textNode("@@@@@"));
        assertEquals("{\"tipo\":\"Segnale\",\"evento\":{\"mittente\":\"tu@me.com\",\"sottotipo\":\"#####\",\"porta\":42,\"segnale\":\"@@@@@\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaProprioIP.
     */
    @Test
    public void testNotificaProprioIP() {
        NotificaProprioIP n = new NotificaProprioIP();
        n.setIP("1.2.3.4");
        assertEquals("{\"tipo\":\"ProprioIP\",\"evento\":{\"ip\":\"1.2.3.4\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaProprioUtente.
     */
    @Test
    public void testNotificaProprioUtente() {
        NotificaProprioUtente n = new NotificaProprioUtente();
        n.setEmail("io@noi.com");
        n.setStato("ora");
        n.setUsername("io");
        assertEquals("{\"tipo\":\"ProprioUtente\",\"evento\":{\"email\":\"io@noi.com\",\"username\":\"io\",\"stato\":\"ora\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaListaAggiungi.
     */
    @Test
    public void testNotificaListaAggiungi() {
        NotificaListaAggiungi n = new NotificaListaAggiungi();
        n.setEmail("io@noi.com");
        n.setStato("ora");
        n.setUsername("io");
        assertEquals("{\"tipo\":\"ListaAggiungi\",\"evento\":{\"email\":\"io@noi.com\",\"username\":\"io\",\"stato\":\"ora\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaListaModifica.
     */
    @Test
    public void testNotificaListaModifica() {
        NotificaListaModifica n = new NotificaListaModifica();
        n.setEmail("io@noi.com");
        n.setStato("ora");
        n.setUsername("io");
        assertEquals("{\"tipo\":\"ListaModifica\",\"evento\":{\"email\":\"io@noi.com\",\"username\":\"io\",\"stato\":\"ora\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaListaElimina.
     */
    @Test
    public void testNotificaListaElimina() {
        NotificaListaElimina n = new NotificaListaElimina();
        n.setEmail("io@noi.com");
        assertEquals("{\"tipo\":\"ListaElimina\",\"evento\":{\"email\":\"io@noi.com\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaNuovaRichiestaAmicizia.
     */
    @Test
    public void testNotificaNuovaRichiestaAmicizia() {
        NotificaNuovaRichiestaAmicizia n =
                new NotificaNuovaRichiestaAmicizia();
        n.setEmail("io@noi.com");
        n.setUsername("io");
        assertEquals("{\"tipo\":\"NuovaRichiestaAmicizia\",\"evento\":{\"email\":\"io@noi.com\",\"username\":\"io\"}}",
                n.serializza());
    }

    /**
     * Test of class NotificaNuovoMessaggio.
     */
    @Test
    public void testNotificaNuovoMessaggio() {
        NotificaNuovoMessaggio n = new NotificaNuovoMessaggio();
        n.setMittente("io@noi.com");
        n.setMessaggio("testo");
        assertEquals("{\"tipo\":\"NuovoMessaggio\",\"evento\":{\"mittente\":\"io@noi.com\",\"messaggio\":\"testo\"}}",
                n.serializza());
    }
}
