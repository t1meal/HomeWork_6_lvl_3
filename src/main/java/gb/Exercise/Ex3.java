package gb.Exercise;

public class Ex3 {
    public static void main(String[] args) {
        int [] arr = {1, 4, 1, 1, 4};

        System.out.println("Результат проверки: " + checkArr(arr));
    }

    private static boolean checkArr (int [] arr){

        boolean one = false;
        boolean four = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) one = true;
            if (arr[i] == 4) four = true;
        }
        return one & four;
    }
}
