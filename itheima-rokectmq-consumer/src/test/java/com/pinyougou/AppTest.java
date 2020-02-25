package com.pinyougou;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

/**
 * Unit test for simple App.
 */
@ContextConfiguration("classpath:spring-consumer.xml")
@RunWith(SpringRunner.class)
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        Thread.sleep(1000000);
    }
}
