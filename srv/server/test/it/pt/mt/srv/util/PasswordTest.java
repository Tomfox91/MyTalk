package it.pt.mt.srv.util;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * 
 */
public class PasswordTest {
    /**
     * Test of class Password.
     */
    @Test
    public void test() {
        assertTrue(Password.checkPassword(
                "password",
                Password.hashPassword("password")));
    }
}
