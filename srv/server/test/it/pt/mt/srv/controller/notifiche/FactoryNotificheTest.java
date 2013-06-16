package it.pt.mt.srv.controller.notifiche;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactoryNotificheTest {

    /**
     * Test of classFactoryNotifiche.
     */
    @Test
    public void test() {
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaDomandaSicurezza());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaEsitiRicerca());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaSegnale());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaListaAggiungi());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaListaElimina());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaListaModifica());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaNuovaRichiestaAmicizia());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaNuovoMessaggio());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaOperazione());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaProprioIP());
        assertNotNull(
                FactoryNotifiche.getInstance().creaNotificaProprioUtente());
    }
}