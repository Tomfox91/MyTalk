package it.pt.mt.srv.util;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 *
 */
public class HashTest {

    /**
     * Test of toString method, of class Hash.
     */
    @Test
    public void testToString() {
        assertEquals(
                new Hash("iwheuifhiu weahfiauwie owahefiuhwae").toString(),
                "c3f3830bc584329862697d5f8884c05b");
    }

    /**
     * Test of getHash method, of class Hash.
     */
    @Test
    public void testGetHash() {
        assertArrayEquals(
                new Hash("fhewifuhewiufhewiuohiu").getHash(),
                new Hash("fhewifuhewiufhewiuohiu").getHash());
    }

    /**
     * Test of equals method, of class Hash.
     */
    @Test
    @SuppressWarnings("IncompatibleEquals")
    public void testEquals() {
        assertTrue(
                new Hash("uiewhfiulweha feiuwhiuaehfilc aehiwl feguwak").equals(
                new Hash("uiewhfiulweha feiuwhiuaehfilc aehiwl feguwak")));
        assertFalse(
                new Hash("fuwehfiuwaehiouf efiuweh oufhuiewhiuwe hf").equals(
                new Hash("fuwehfiuwaehiouf efiuweh oufhuiewhiuwe fh")));
        assertFalse(
                new Hash("fuwehfiuwaehiouf efiuweh oufhuiewhiuwe hf").equals(
                "viuwofehoiuwejfoiew"));
    }

    /**
     * Test of hashCode method, of class Hash.
     */
    @Test
    public void testHashCode() {
        assertEquals(
                new Hash("ivbwiuefiuehiufhei fuiwehb iufhiuewh ew").hashCode(),
                new Hash("ivbwiuefiuehiufhei fuiwehb iufhiuewh ew").hashCode());

    }
    
    /**
     * Test of ultimoByte method, of class Hash.
     */
    @Test
    public void testUltimoByte() {
        assertEquals(
                new Hash("ytggtfrrdftsdhjvbfsafdvh").ultimoByte(), -94);

    }
}
