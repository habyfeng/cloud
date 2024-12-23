package com.albert;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void teststartAlone() {
        AppDubboServer server = new AppDubboServer();
        server.startAlone();
    }

    @Test
    public void testStartWithRegistry() {
        AppDubboServer server = new AppDubboServer();
        server.startWithRegistry();
    }
}
