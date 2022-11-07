import java.util.*;

public class Main {
    public static void main(String[] args) {
        double[] input = getSequence(100, 4, 12, true);
        double[] processing = getSequence(100, 2, 8, true);
        double[] startProc = getTime(input, processing);
        double[] bufTime = getBufTime(input, startProc);
        System.out.println(Arrays.toString(input));
        System.out.println(Arrays.toString(processing));
        System.out.println(Arrays.toString(startProc));
        System.out.println(Arrays.toString(bufTime));
        System.out.println(getCountBuf(bufTime));
    }

    public static double rand(double x){
        int a = 39;
        int b = 1;
        int m = 1000;
        return (((a * x) + b) % m);
    }

    public static double[] getSequence(int cnt, int min, int max, boolean custom){
        double[] out = new double[cnt];
        double[] x = new double[cnt];
        Random rnd = new Random();
        if (custom) {
            x[0] = 1;
            for (int i = 1; i < cnt; i++) {
                x[i] = rand(x[i - 1]);
            }
            for (int i = 0; i < cnt; i++) {
                out[i] = (max - min) * (x[i] / 1000) + min;
            }
        }
        else
            for (int i = 0; i < cnt; i++) {
                x[i] = rnd.nextDouble(min, max);
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

    public static double[] getBufTime(double[] input, double[] startTime){
        double[] out = new double[input.length];
        double inputTime = 0;
        for (int i = 0; i < out.length; i++){
            inputTime += input[i];
            double dif = startTime[i] - inputTime;
            out[i] = dif <= 0 ? 0 : dif;
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

    public static HashMap<Integer, ArrayList<Double>> getCountBuf(double[] input, double[] start){
        HashMap<Integer, ArrayList<Double>> count = new HashMap<>();
        double inputSum = 0;
        for (int i = 0; i < input.length; i++){
            int cnt = 1;
            int j = i + 1;
            inputSum += input[i];
            double tmp = inputSum;
            while (tmp < start[i] && j < input.length ){
                if (!count.containsKey(cnt))
                    count.put(cnt, new ArrayList<>());
                count.get(cnt).add(start[i] - tmp);
                tmp += input[j++];
                cnt++;
            }
        }
        return count;
    }
}