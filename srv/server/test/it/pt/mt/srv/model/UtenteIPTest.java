/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pt.mt.srv.model;

import it.pt.mt.srv.util.ConditionException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class UtenteIPTest {

    public UtenteIPTest() {
    }

    /**
     * Test of class IPTest.
     */
    @Test
    public void test() throws ConditionException {
        UtenteIP ip = new UtenteIP("1.2.3.4");
        assertEquals("1.2.3.4", ip.getIP());
        assertEquals("1.2.3.4", ip.getNome());

        ip = new UtenteIP("2001:0db8:85a3:0042:1000:8a2e:0370:7334");
        assertNotNull(ip);
    }

    /**
     * Test of class IPTest.
     */
    @Test(expected=ConditionException.class)
    public void testFail0() throws ConditionException {
        UtenteIP ip = new UtenteIP("cicciabubu");
    }

    /**
     * Test of class IPTest.
     */
    @Test(expected=ConditionException.class)
    public void testFail1() throws ConditionException {
        UtenteIP ip = new UtenteIP("1.2.3.4#5");
    }

    /**
     * Test of class IPTest.
     */
    @Test(expected=ConditionException.class)
    public void testFail2() throws ConditionException {
        UtenteIP ip = new UtenteIP("1.2.3.-4");
    }

}
