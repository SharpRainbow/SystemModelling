import java.util.*;

public class Main {
    public static void main(String[] args) {
        double[] input = getSequence(100, 4, 12);
        double[] processing = getSequence(100, 2, 8);
        double[] startProc = getTime(input, processing);
        double[][] bufTime = getBufTime(input, processing, startProc);
        System.out.println("Время прихода заявок: " + Arrays.toString(input));
        System.out.println("Время обработки заявок: " + Arrays.toString(processing));
        System.out.println("Время начала обработки заявки: " + Arrays.toString(startProc));
        for (double[] doubles : bufTime) {
            for (double aDouble : doubles) {
                System.out.format("%.3f ", aDouble);
            }
            System.out.println();
        }
        double taskTime;
        int counter = 1;
        do {
            taskTime = getCountBuf(bufTime, counter);
            System.out.format("Cреднее время нахождения в буфере %d заявок: %.2f\n", counter, taskTime);
            counter++;
        }
        while (taskTime != 0);
        System.out.println("------------------------------------------------");
        double[] input1 = getExpSeq(100, 1.0/3);
        double[] processing1 = getExpSeq(100, 1.0/4);
        double[] startProc1 = getTime(input1, processing1);
        double[][] bufTime1 = getBufTime(input1, processing1, startProc1);
        System.out.println("Время прихода заявок: " + Arrays.toString(input1));
        System.out.println("Время обработки заявок: " + Arrays.toString(processing1));
        System.out.println("Время начала обработки заявки: " + Arrays.toString(startProc1));
        for (double[] doubles : bufTime1) {
            for (double aDouble : doubles) {
                System.out.format("%.3f ", aDouble);
            }
            System.out.println();
        }
        counter = 1;
        do {
            taskTime = getCountBuf(bufTime1, counter);
            System.out.format("Cреднее время нахождения в буфере %d заявок: %.2f\n", counter, taskTime);
            counter++;
        }
        while (taskTime != 0);


    }

    public static double rand(double x){
        int a = 39;
        int b = 1;
        int m = 1000;
        return (((a * x) + b) % m);
    }

    public static double[] getSequence(int cnt, int min, int max){
        double[] out = new double[cnt];
        double[] x = new double[cnt];

            x[0] = 1;
            for (int i = 1; i < cnt; i++) {
                x[i] = rand(x[i - 1]);
            }
            for (int i = 0; i < cnt; i++) {
                out[i] = (max - min) * (x[i] / 1000) + min;
            }

        return out;
    }

    public static double[] getExpSeq(int cnt, double lambda){
        double[] out = new double[cnt];
        for (int i = 0; i < cnt; i++){
            out[i] = (Math.log(Math.random())) / (-lambda);
        }
        return out;
    }

    public static double[] getTime(double[] inp, double[] proc){
        double[] out = new double[inp.length];
        out[0] = inp[0];
        double sumInp = inp[0];
        double endProc = out[0] + proc[0];
        for (int i = 1; i < inp.length; i++){
            //out[i] = out[i - 1] + Math.max(inp[i], proc[i - 1]);
            sumInp += inp[i];
            out[i] = Math.max(sumInp, endProc);
            endProc = out[i] + proc[i];
        }
        return out;
    }

    /*public static double[] getBufTime(double[] input, double[] startTime){
        double[] out = new double[input.length];
        double inputTime = 0;
        for (int i = 0; i < out.length; i++){
            inputTime += input[i];
            double dif = startTime[i] - inputTime;
            out[i] = dif <= 0 ? 0 : dif;
        }
        return out;
    }*/

    public static double[][] getBufTime(double[] input, double[] proc, double[] startProc){
        //1 - время с начала работы системы;2 - количество программ в буфере;3 - время нахождения в буфере
        double[][] out = new double[input.length][3];
        double[] endProc = new double[input.length];//Время окончания обработки заявки
        out[0][0] = input[0];
        endProc[0] = input[0] + proc[0];
        double inputTime = input[0];
        for (int i = 1; i < input.length; i++){
            out[i][0] = (inputTime += input[i]);
            endProc[i] = startProc[i] + proc[i];
            out[i][2] = startProc[i] - inputTime;
            int m = 0;
            for (int j = 0; j < i; j++){
                if (endProc[j] > inputTime)
                    m++;
            }
            out[i][1] = m;
        }
        return out;
    }

    public static HashMap<Integer, ArrayList<Double>> getCountBuf(double[] buffer) {
        HashMap<Integer, ArrayList<Double>> count = new HashMap<>();
        int cnt = 0;
        for (double d : buffer) {
            if (d != 0) {
                cnt++;
                if (!(count.containsKey(cnt)))
                    count.put(cnt, new ArrayList<>());
                count.get(cnt).add(d);
            }
            else
                cnt = 0;
        }
        return count;
    }

    public static double getCountBuf(double[][] input, int size){
        double res = 0;
        int count = 0;
        for (double[] doubles : input) {
            if (doubles[1] == size) {
                count++;
                res += doubles[2];
            }
        }
        System.out.format("Вероятность нахождения в буфере %d заявок: %.2f\n", size, (count * 1.0 / input.length));
        return count == 0 ? 0 : (res / count);
    }
}