package cn.ken.egou;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        String s = ".1.2.3";
        String[] split = s.split("\\.");
        System.out.println(Arrays.toString(split));
    }
}
