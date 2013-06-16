package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.util.DBConf;
import java.nio.CharBuffer;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.WsOutbound;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.powermock.reflect.Whitebox;

public class ControllerIntTest {

    private static String email0 = "0@ww.com";
    private static String email1 = "1@ww.com";

    @Before
    public void setUpDB() throws Exception {
        Process p = Runtime.getRuntime().exec("ant create-tables-audao-hsql-test");
        int res = p.waitFor();
        if (res != 0) {
            System.exit(res);
        }
        DBConf.setPath("db/testdb");
    }

    @After
    public void tearDownDB() {
        RegistroDao.getInstance().shutdownDB();
    }

    private static void verificaInvio(WsOutbound wsout, CharBuffer... bufs)
            throws Exception {
        for (CharBuffer i : bufs) {
            verify(wsout).writeTextMessage(i);
        }
        verifyNoMoreInteractions(wsout);
        reset(wsout);
    }

    private static CharBuffer registrazione(String email, String user,
            String pass, String dom, String risp) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"Registrazione\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\","
                + "     \"username\": \"" + user + "\","
                + "     \"password\": \"" + pass + "\","
                + "     \"domanda\": \"" + dom + "\","
                + "     \"risposta\": \"" + risp + "\""
                + " }"
                + "}");
    }

    private static CharBuffer recuperaPass(String email, String risp, String nuova) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"RecuperaPassword\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\","
                + "     \"risposta\": \"" + risp + "\","
                + "     \"nuova\": \"" + nuova + "\""
                + " }"
                + "}");
    }

    private static CharBuffer ricDomSic(String email) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"RichiestaDomandaSicurezza\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\""
                + " }"
                + "}");
    }

    private static CharBuffer cambioPass(String vecchia, String nuova) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"ModificaPassword\","
                + " \"evento\": {"
                + "     \"vecchia\": \"" + vecchia + "\","
                + "     \"nuova\": \"" + nuova + "\""
                + " }"
                + "}");
    }

    private static CharBuffer ricerca(String pattern) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"RicercaUtenti\","
                + " \"evento\": {"
                + "     \"pattern\": \"" + pattern + "\""
                + " }"
                + "}");
    }

    private static CharBuffer modProf(String user, String stato) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"ModificaProfilo\","
                + " \"evento\": {"
                + "     \"username\": \"" + user + "\","
                + "     \"stato\": \"" + stato + "\""
                + " }"
                + "}");
    }

    private static CharBuffer login(String email, String pass) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"Login\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\","
                + "     \"password\": \"" + pass + "\""
                + " }"
                + "}");
    }

    private static CharBuffer logout() {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"Logout\","
                + " \"evento\": {}"
                + "}");
    }

    private static CharBuffer nuovaAm(String email) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"NuovaAmicizia\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\""
                + " }"
                + "}");
    }

    private static CharBuffer accetAm(boolean accettata, String email) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"AccettazioneAmicizia\","
                + " \"evento\": {"
                + "     \"accettata\": \"" + accettata + "\","
                + "     \"email\": \"" + email + "\""
                + " }"
                + "}");
    }

    private static CharBuffer elimAm(String email) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"EliminaAmicizia\","
                + " \"evento\": {"
                + "     \"email\": \"" + email + "\""
                + " }"
                + "}");
    }

    private static CharBuffer messSegr(String dest, String mess) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"MessaggioSegreteria\","
                + " \"evento\": {"
                + "     \"destinatario\": \"" + dest + "\","
                + "     \"messaggio\": \"" + mess + "\""
                + " }"
                + "}");
    }

    private static CharBuffer segnale(String dest, String sotto, int porta,
            String segnale) {
        return CharBuffer.wrap(
                "{"
                + " \"tipo\": \"Segnale\","
                + " \"evento\": {"
                + "     \"destinatario\": \"" + dest + "\","
                + "     \"sottotipo\": \"" + sotto + "\","
                + "     \"porta\": " + porta + ","
                + "     \"segnale\": " + segnale
                + " }"
                + "}");
    }

    private static CharBuffer nSegnale(String mitt, String sotto, int porta,
            String segnale) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"Segnale\","
                + "\"evento\":{"
                + "\"mittente\":\"" + mitt + "\","
                + "\"sottotipo\":\"" + sotto + "\","
                + "\"porta\":" + porta + ","
                + "\"segnale\":" + segnale
                + "}"
                + "}");
    }

    private static CharBuffer nOperazione(String operazione, boolean esito) {
        return CharBuffer.wrap("{"
                + "\"tipo\":\"Operazione\","
                + "\"evento\":{"
                + "\"riuscita\":" + esito + ","
                + "\"originale\":\"" + operazione + "\"}"
                + "}");
    }

    private static CharBuffer nProUt(String email, String username, String stato) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"ProprioUtente\","
                + "\"evento\":{"
                + "\"email\":\"" + email + "\","
                + "\"username\":\"" + username + "\","
                + "\"stato\":\"" + stato + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nRichAm(String email, String username) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"NuovaRichiestaAmicizia\","
                + "\"evento\":{"
                + "\"email\":\"" + email + "\","
                + "\"username\":\"" + username + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nNuoMess(String mitt, String mess) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"NuovoMessaggio\","
                + "\"evento\":{"
                + "\"mittente\":\"" + mitt + "\","
                + "\"messaggio\":\"" + mess + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nListAgg(String email, String username,
            String stato) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"ListaAggiungi\","
                + "\"evento\":{"
                + "\"email\":\"" + email + "\","
                + "\"username\":\"" + username + "\","
                + "\"stato\":\"" + stato + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nListMod(String email, String username,
            String stato) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"ListaModifica\","
                + "\"evento\":{"
                + "\"email\":\"" + email + "\","
                + "\"username\":\"" + username + "\","
                + "\"stato\":\"" + stato + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nListElim(String email) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"ListaElimina\","
                + "\"evento\":{"
                + "\"email\":\"" + email + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nDomSic(String domanda) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"DomandaSicurezza\","
                + "\"evento\":{"
                + "\"domanda\":\"" + domanda + "\""
                + "}"
                + "}");
    }

    private static CharBuffer nEsRic(String email, String nome) {
        return CharBuffer.wrap(
                "{"
                + "\"tipo\":\"EsitiRicerca\","
                + "\"evento\":{"
                + "\"mappa\":{"
                + "\"" + email + "\":\"" + nome + "\""
                + "}"
                + "}"
                + "}");
    }

    @Test
    public void testAccount() throws Exception {
        CreatoreConnessioni cc = new CreatoreConnessioni();

        HttpServletRequest req0 = mock(HttpServletRequest.class);
        when(req0.getRemoteAddr()).thenReturn("42.42.0.42");
        Connessione c0 = (Connessione) cc.createWebSocketInbound("", req0);
        WsOutbound wsout0 = mock(WsOutbound.class);
        Whitebox.setInternalState(c0, "outbound", wsout0);
        assertNotNull(RegistroConnessioni.getInstance().getConnessione("42.42.0.42"));

        //test registrazione
        c0.onTextMessage(registrazione(email0, "user0", "pwd0", "d0", "r0"));
        assertEquals(email0, GestoreUtenti.getInstance()
                .getUtenteRegistrato(email0).getEmail());
        verificaInvio(wsout0, nOperazione("Registrazione", true));

        //test recupero password
        c0.onTextMessage(ricDomSic(email0));
        verificaInvio(wsout0, nDomSic("d0"));
        c0.onTextMessage(recuperaPass(email0, "r0", "pwd0"));
        verificaInvio(wsout0, nOperazione("RecuperaPassword", true));

        //test login
        c0.onTextMessage(login(email0, "pwd0"));
        assertNotNull(RegistroConnessioni.getInstance().getConnessione(email0));
        verificaInvio(wsout0, nProUt(email0, "user0", "Online"));

        //test cambio password
        c0.onTextMessage(cambioPass("pwd0", "pass"));
        verificaInvio(wsout0, nOperazione("ModificaPassword", true));

        //test logout/login
        c0.onTextMessage(logout());
        c0.onTextMessage(login(email0, "pass"));
        assertNotNull(RegistroConnessioni.getInstance().getConnessione(email0));
        verificaInvio(wsout0, nProUt(email0, "user0", "Online"));

        //test password sbagliate
        c0.onTextMessage(cambioPass("cicciabubu", "pass"));
        verificaInvio(wsout0, nOperazione("ModificaPassword", false));
        c0.onTextMessage(logout());
        c0.onTextMessage(recuperaPass(email0, "cicciabubu", "pwd0"));
        verificaInvio(wsout0, nOperazione("RecuperaPassword", false));
    }

    @Test
    public void testInterazione() throws Exception {
        CreatoreConnessioni cc = new CreatoreConnessioni();

        HttpServletRequest req0 = mock(HttpServletRequest.class);
        when(req0.getRemoteAddr()).thenReturn("42.42.0.42");
        Connessione c0 = (Connessione) cc.createWebSocketInbound("", req0);
        WsOutbound wsout0 = mock(WsOutbound.class);
        Whitebox.setInternalState(c0, "outbound", wsout0);
        assertNotNull(RegistroConnessioni.getInstance().getConnessione("42.42.0.42"));

        HttpServletRequest req1 = mock(HttpServletRequest.class);
        when(req1.getRemoteAddr()).thenReturn("42.42.1.42");
        Connessione c1 = (Connessione) cc.createWebSocketInbound("", req1);
        WsOutbound wsout1 = mock(WsOutbound.class);
        Whitebox.setInternalState(c1, "outbound", wsout1);
        assertNotNull(RegistroConnessioni.getInstance().getConnessione("42.42.1.42"));

        //test comunicazione tra ip
        c0.onTextMessage(segnale("42.42.1.42", "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout1, nSegnale("42.42.0.42", "w", 42, "\"cicciabubu\""));
        c1.onTextMessage(segnale("42.42.0.42", "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout0, nSegnale("42.42.1.42", "w", 42, "\"cicciabubu\""));

        //test registrazione
        c0.onTextMessage(registrazione(email0, "user0", "pwd0", "d0", "r0"));
        verificaInvio(wsout0, nOperazione("Registrazione", true));

        //test login
        c0.onTextMessage(login(email0, "pwd0"));
        verificaInvio(wsout0, nProUt(email0, "user0", "Online"));

        //test comunicazione email-ip
        c0.onTextMessage(segnale("42.42.1.42", "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout1, nSegnale(email0, "w", 42, "\"cicciabubu\""));
        c1.onTextMessage(segnale(email0, "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout0, nSegnale("42.42.1.42", "w", 42, "\"cicciabubu\""));

        //registrazione e login per l'altro utente
        c1.onTextMessage(registrazione(email1, "user1", "pwd1", "d1", "r1"));
        verificaInvio(wsout1, nOperazione("Registrazione", true));
        c1.onTextMessage(login(email1, "pwd1"));
        verificaInvio(wsout1, nProUt(email1, "user1", "Online"));

        //test comunicazione email-email
        c0.onTextMessage(segnale(email1, "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout1, nSegnale(email0, "w", 42, "\"cicciabubu\""));
        c1.onTextMessage(segnale(email0, "w", 42, "\"cicciabubu\""));
        verificaInvio(wsout0, nSegnale(email1, "w", 42, "\"cicciabubu\""));

        //test ricerca
        c1.onTextMessage(ricerca(email0));
        verificaInvio(wsout1, nEsRic(email0, "user0"));
        c1.onTextMessage(ricerca("user0"));
        verificaInvio(wsout1, nEsRic(email0, "user0"));

        //test nuova amicizia
        c0.onTextMessage(nuovaAm(email1));
        verificaInvio(wsout0, nOperazione("NuovaAmicizia", true));
        verificaInvio(wsout1, nRichAm(email0, "user0"));

        //test logout
        c1.onTextMessage(logout());

        //test messaggio
        c0.onTextMessage(messSegr(email1, "cicciabubu"));
        verificaInvio(wsout0, nOperazione("MessaggioSegreteria", true));

        //test notifiche login
        c1.onTextMessage(login(email1, "pwd1"));
        verificaInvio(wsout1,
                nProUt(email1, "user1", "Online"),
                nNuoMess(email0, "cicciabubu"),
                nRichAm(email0, "user0"));

        //test accettazione amicizia
        c1.onTextMessage(accetAm(true, email0));
        verificaInvio(wsout1, nListAgg(email0, "user0", "Online"));
        verificaInvio(wsout0, nListAgg(email1, "user1", "Online"));

        //test cambio stato
        c1.onTextMessage(modProf("user1", "stato1"));
        verificaInvio(wsout1, nOperazione("ModificaProfilo", true));
        verificaInvio(wsout0, nListMod(email1, "user1", "stato1"));

        //test notifiche logout/login
        c1.onTextMessage(logout());
        verificaInvio(wsout0, nListMod(email1, "user1", "Offline"));
        c1.onTextMessage(login(email1, "pwd1"));
        verificaInvio(wsout1,
                nProUt(email1, "user1", "Online"),
                nListAgg(email0, "user0", "Online"));
        verificaInvio(wsout0, nListMod(email1, "user1", "Online"));

        //test eliminazione amicizia
        c0.onTextMessage(elimAm(email1));
        assertEquals(0, GestoreUtenti.getInstance()
                .getUtenteRegistrato(email0).getAmici().size());
        assertEquals(0, GestoreUtenti.getInstance()
                .getUtenteRegistrato(email1).getAmici().size());
        verificaInvio(wsout1, nListElim(email0));
        verificaInvio(wsout0, nListElim(email1));
    }
}
