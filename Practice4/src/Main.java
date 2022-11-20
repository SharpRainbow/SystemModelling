import java.util.*;

public class Main {
    public static void main(String[] args) {
        double[] input = getSequence(100, 4, 12);
        double[] processing = getSequence(100, 2, 8);
        double[][] bufTime = getBufTime(input, processing, 5);
        //System.out.println("Время прихода заявок: " + Arrays.toString(input));
        //System.out.println("Время обработки заявок: " + Arrays.toString(processing));
        System.out.println("\n" + "-".repeat(58) + "Результаты" + "-".repeat(57));
        String format = "|%1$-14s|%2$-16s|%3$-14s|%4$-17s|%5$-17s|%6$-21s|%7$-18s|\n";
        System.out.println("| Номер заявки | Время с начала |" +
                        " Время начала | Время обработки | Время окончания |" +
                " Количество программ | Время нахождения |");
        System.out.format(format, "", " работы системы", " обработки", "", " обработки", " в буфере", " в буфере");
        System.out.println("-".repeat(125));
        for (double[] doubles : bufTime) {
            String[] row = new String[doubles.length];
            for (int i = 0; i < 7; i++) {
                if (i == 0 || i ==5 || doubles[i] == 0)
                    row[i] = String.format("%.0f", doubles[i]);
                else
                    row[i] = String.format("%.3f", doubles[i]);
            }
            System.out.format(format, row);
        }
        System.out.println("-".repeat(125));
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
        double[][] bufTime1 = getBufTime(input1, processing1, 5);
        //System.out.println("Время прихода заявок: " + Arrays.toString(input1));
        //System.out.println("Время обработки заявок: " + Arrays.toString(processing1));
        System.out.println("\n" + "-".repeat(58) + "Результаты" + "-".repeat(57));
        System.out.println("| Номер заявки | Время с начала |" +
                " Время начала | Время обработки | Время окончания |" +
                " Количество программ | Время нахождения |");
        System.out.format(format, "", " работы системы", " обработки", "", " обработки", " в буфере", " в буфере");
        System.out.println("-".repeat(125));
        for (double[] doubles : bufTime1) {
            String[] row = new String[doubles.length];
            for (int i = 0; i < 7; i++) {
                if (i == 0 || i ==5 || doubles[i] == 0)
                    row[i] = String.format("%.0f", doubles[i]);
                else
                    row[i] = String.format("%.3f", doubles[i]);
            }
            System.out.format(format, row);
        }
        System.out.println("-".repeat(125));
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

    public static double[][] getBufTime(double[] input, double[] proc, int n){
        //1 - номер заявки; 2 - время с начала работы системы; 3 - время начала обработки заявки; 4 - время обработки заявки;
        //5 - время окончания обработки; 6 - количество программ в буфере;7 - время нахождения в буфере
        double[][] out = new double[input.length][7];
        out[0][0] = 1;
        out[0][1] = out[0][2] = input[0];
        out[0][3] = proc[0];
        out[0][4] = input[0] + proc[0];
        double inputTime = input[0];
        for (int i = 1; i < input.length; i++){
            inputTime += input[i];
            double startProc = Math.max(inputTime, out[i - 1][4]);
            double endProc = startProc + proc[i];
            int m = 0;
            for (int j = 0; j < i; j++){
                if (out[j][4] > inputTime)
                    m++;
            }
            out[i][0] = i + 1;
            out[i][1] = inputTime;
            if (m > n) {
                out[i][5] = n;
                continue;
            }
            out[i][2] = startProc;
            out[i][3] = proc[i];
            out[i][4] = endProc;
            out[i][5] = m;
            out[i][6] = startProc - inputTime;
        }
        return out;
    }

    public static double getCountBuf(double[][] input, int size){
        double res = 0;
        int count = 0;
        for (double[] doubles : input) {
            if (doubles[5] == size) {
                count++;
                res += doubles[6];
            }
        }
        System.out.format("Вероятность нахождения в буфере %d заявок: %.2f\n", size, (count * 1.0 / input.length));
        return count == 0 ? 0 : (res / count);
    }
}