package com.example.redisspringboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/7/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TimeTest {


    @Test
    public void testTime()
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        System.out.println(c.getTime());
        c.add(Calendar.SECOND,-10);

        System.out.println(c.getTime());
    }
}
