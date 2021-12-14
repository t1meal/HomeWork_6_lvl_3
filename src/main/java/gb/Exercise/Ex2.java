package gb.Exercise;

import java.util.Arrays;

public class Ex2 {
    public static void main(String[] args) {
        Integer [] origin_arr = {1, 2, 4 , 4 ,2, 3, 4, 1, 7};
        System.out.println("Оригинальный массив: " + Arrays.asList(origin_arr));
        System.out.println("Обработанный массив: " + Arrays.asList(retrieveChangeArr(origin_arr)));


    }

    private static <T extends Number> T[] retrieveChangeArr (T [] arr) throws MyException {
        T[] result = null;

        for (int i = arr.length-1; i > 0 ; i--) {
            if (arr[i].equals(4)) {
                result = (T[]) new Number[arr.length-i-1];
                System.arraycopy(arr, i+1, result, 0, arr.length-i-1);
                break;
            }
        }

        if(result == null) throw new MyException("В оригинальном массивые нет ни одно четверки!");

        return result;
    }
}


