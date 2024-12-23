package com.albert;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testCallDirectly() {
        AppDubboClient client = new AppDubboClient();
        client.callDirectly();
    }

    @Test
    public void testCallWithRegistry() {
        AppDubboClient client = new AppDubboClient();
        client.callWithRegistry();
    }
}
