package gb.Exercise;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class Ex2Test {

    private static Ex2 ex2;

    @BeforeClass
    public static void init () {
        System.out.println("Initialization...\n");
        ex2 = new Ex2();
    }

    @Test
    public void incomingArr_1 () {
        Integer [] testArr1 = {1, 2, 4 , 4 ,2, 3, 1, 1, 7};
        Integer [] expectedArr = {2, 3, 1, 1, 7};
        check(testArr1, expectedArr);

    }
    @Test
    public void incomingArr_2 () {
        Integer [] testArr2 = {1, 2, 4 , 4 ,2, 4, 1, 1, 7};
        Integer [] expectedArr = {1, 1, 7};
        check(testArr2, expectedArr);
    }
    @Test
    public void incomingArr_3 () {
        Integer [] testArr3 = {1, 4, 1 , 1 ,2, 3, 1, 1, 7};
        Integer [] expectedArr = {1, 1, 2, 3, 1, 1, 8};
        check(testArr3, expectedArr);

    }

    private static <T> void check (T[] testArr1, Integer [] expectedArr){
        final Integer[] retrieveArr = (Integer[]) ex2.retrieveChangeArr(testArr1);
        Assert.assertArrayEquals(expectedArr, retrieveArr);
        System.out.println("Test is completed!");
    }

}