package gb.Exercise;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class Ex3Test {

    private static Ex3 ex3;

    @BeforeClass
    public static void init (){
        System.out.println("Initialization...\n");
        ex3 = new Ex3();
    }

    @Test
    public void test_1 () {
        int [] arr = {1, 1, 4, 4, 1};
        final boolean result = ex3.checkArr(arr);
        checkTrue(result);
    }
    @Test
    public void test_2 () {
        int [] arr = {1, 1, 2, 2, 1};
        final boolean result = ex3.checkArr(arr);
        checkFalse(result);
    }
    @Test
    public void test_3 () {
        int [] arr = {2, 2, 4, 4, 2};
        final boolean result = ex3.checkArr(arr);
        checkFalse(result);
    }


    private static void checkTrue (boolean result){
        Assert.assertTrue(result);
        System.out.println("Test is completed!");
    }
    private static void checkFalse (boolean result){
        Assert.assertFalse(result);
        System.out.println("Test is completed!");
    }


}