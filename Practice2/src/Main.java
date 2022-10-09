import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
    static int A = 0;
    static int B = 10;

    static Random rnd = new Random();

    public static void main(String[] args) {
        System.out.println("----------- N = 10^" + 2 + " -----------");
        getSequence((int)Math.pow(10, 2), true);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 3 + " -----------");
        getSequence((int)Math.pow(10, 3), true);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 4 + " -----------");
        getSequence((int)Math.pow(10, 4), true);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 5 + " -----------");
        getSequence((int)Math.pow(10, 5), true);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 2 + " -----------");
        getSequence((int)Math.pow(10, 2), false);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 3 + " -----------");
        getSequence((int)Math.pow(10, 3), false);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 4 + " -----------");
        getSequence((int)Math.pow(10, 4), false);
        System.out.println("--------------------------------");
        System.out.println("----------- N = 10^" + 5 + " -----------");
        getSequence((int)Math.pow(10, 5), false);
        System.out.println("--------------------------------");
    }

    public static void getSequence(int len, boolean custom){
        int x0 = 1;
        long m = 2L << 31;
        ArrayList<Double> rParamsArr = new ArrayList<>();
        if (custom) {
            ArrayList<Double> rNumsArr = new ArrayList<>();
            rNumsArr.add(rand(x0));
            rnd.nextDouble(0, 10);
            for (int i = 1; i < len; i++) {
                rNumsArr.add(rand(rNumsArr.get(i - 1)));
            }
            for (int i = 0; i < len; i++) {
                rNumsArr.set(i, rNumsArr.get(i) / m);
            }

            for (int i = 0; i < len; i++) {
                rParamsArr.add(A + (B - A) * rNumsArr.get(i));
            }
        }
        else
            for (int i = 0; i < len; i++) {
                rParamsArr.add(rnd.nextDouble(A, B));
            }
        //rParamsArr.forEach(System.out::println);//Вывод всего массива
        System.out.println("Max value: " + rParamsArr.stream().max(Comparator.naturalOrder()).get());
        System.out.println("Min value: " + rParamsArr.stream().min(Comparator.naturalOrder()).get());
        errRate(rParamsArr, len);
        double[] resX = new double[10];
        for (int i = 0; i < 10; i++){
            resX[i] = ((B - A)/10.0) * (0.5 + i);
        }
        System.out.println("resX = " + Arrays.toString(resX));
        double[] resY = getFreqDistr(rParamsArr, 10);
        System.out.println("resY = " + Arrays.toString(resY));
        double pierson = 0;
        for (int i = 0; i < 10; i++){
            pierson += Math.pow(1.0 / 10 - resY[i], 2) / resY[i];
        }
        System.out.println("pierson = " + pierson);
    }

    public static void errRate(ArrayList<Double> paramsArr, int len){
        double M = (A + B) / 2.0;
        double D = (Math.pow(B - A, 2) / 12);
        System.out.println("M = " + M + " D = " + D);
        double M_e = paramsArr.stream().mapToDouble(Double::doubleValue).sum()/len;
        System.out.println("M_e = " + M_e);
        double EpsM = Math.abs((M - M_e) / M) * 100;
        System.out.println("EpsM = " + EpsM);
        double D_e = (paramsArr.stream().map(x -> Math.pow(x, 2)).mapToDouble(Double::doubleValue).sum()/len - Math.pow(M_e, 2))*((len * 1.0)/(len-1));
        System.out.println("D_e = " + D_e);
        double EpsD = Math.abs((D - D_e) / D) * 100;
        System.out.println("EpsD = " + EpsD);
        randPeriod(paramsArr);
    }

    public static void randPeriod(ArrayList<Double> paramsArr){
        double[] result = new double[]{-1, -1, -1};
        for (int i = 0; i < paramsArr.size(); i++){
            double element = paramsArr.get(i);
            for (int j = i; j < paramsArr.size() - 1; j++){
                if (element == paramsArr.get(j) && i != j){
                    result[0] = j - i;
                    result[1] = i;
                    result[2] = j;
                    System.out.println(Arrays.toString(result));
                    return;
                }
            }
        }
        System.out.println("Test = " + Arrays.toString(result));
    }

    public static double[] getFreqDistr(ArrayList<Double> paramsArr, int intervals){
        double[] freq = new double[intervals];
        double dY = (B - A) * 1.0 /intervals;
        for (Double aDouble : paramsArr) {
            freq[(int) (aDouble / dY)] += 1;
        }
        for (int i = 0; i < intervals; i++){
            freq[i] = freq[i] / (paramsArr.size() * dY);
        }
        return freq;
    }

    public static double rand(double x){
        int a = 22695477;
        int b = 1;
        long m = 2L << 31;
        return (((a * x) + b) % m);
    }
}