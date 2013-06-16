package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.util.DBConf;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistroDao.class)
public class ServerListenerTest {

    /**
     * Test of contextInitialized method, of class ServerListener.
     */
    @Test
    public void testContextInitialized() {
        RegistroDao rdao = mock(RegistroDao.class);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(rdao);
        ServerListener l = new ServerListener();
        ServletContext c = mock(ServletContext.class);
        when(c.getInitParameter("db-path")).thenReturn("path");
        ServletContextEvent e = mock(ServletContextEvent.class);
        when(e.getServletContext()).thenReturn(c);

        l.contextInitialized(e);
        verifyNoMoreInteractions(rdao);
        assertEquals("path", DBConf.getPath());
    }

    /**
     * Test of contextDestroyed method, of class ServerListener.
     */
    @Test
    public void testContextDestroyed() {
        RegistroDao rdao = mock(RegistroDao.class);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(rdao);
        ServerListener l = new ServerListener();
        l.contextDestroyed(null);
        verify(rdao).shutdownDB();
    }
}