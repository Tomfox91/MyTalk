package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.controller.RegistroConnessioni;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistroConnessioni.class)
public class CreatoreConnessioniTest {

    /**
     * Test of createWebSocketInbound method, of class CreatoreConnessioni.
     */
    @Test
    public void testCreateWebSocketInbound() {
        RegistroConnessioni rconn = mock(RegistroConnessioni.class);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rconn);

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRemoteAddr()).thenReturn("1.2.3.4");

        Connessione conn = (Connessione) new CreatoreConnessioni()
                .createWebSocketInbound("", req);

        assertEquals("1.2.3.4", conn.getIP());
        verify(rconn).aggiungiConnessione("1.2.3.4", conn);
    }
}