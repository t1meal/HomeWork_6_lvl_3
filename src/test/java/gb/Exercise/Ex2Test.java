package gb.Exercise;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
//@RunWith(value = Parameterized.class)
public class Ex2Test {

    private static Ex2 ex2;
//    private Integer [] x1;
//    private Integer [] x2;
//    private Integer [] x3;

//    public Ex2Test(Integer[] x1, Integer[] x2, Integer[] x3) {
//        this.x1 = x1;
//        this.x2 = x2;
//        this.x3 = x3;
//    }

//    @Parameterized.Parameters
//    public static Collection data(){
//        return Arrays.asList(new Object[][]{
//                {1, 2, 4 , 4 ,2, 3, 1, 1, 7},
//                {1, 2, 4 , 4 ,2, 4, 1, 1, 7},
//                {1, 4, 1 , 1 ,2, 3, 1, 1, 7}
//        });
//    }


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