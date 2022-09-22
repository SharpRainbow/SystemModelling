import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*double[] seria1 = new double[5];
        double[] seria2 = new double[5];
        double[] seria3 = new double[5];
        double[] seria4 = new double[5];
        double[] seria5 = new double[5];
        double[] eps1 = new double[5];
        double[] eps2 = new double[5];
        double[] eps3 = new double[5];
        double[] eps4 = new double[5];
        double[] eps5 = new double[5];
        double[] sE = new double[5];
        double[] epsSE = new double[5];
        int x0 = 1;
        int y0 = 2;
        int r0 = 5;
        double[] expNumb = new double[]{Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6), Math.pow(10, 7), Math.pow(10, 8)};
        for (int i = 0; i < expNumb.length; i++) {
            seria1[i] = calcPI(x0, y0, r0, expNumb[i]);
            seria2[i] = calcPI(x0, y0, r0, expNumb[i]);
            seria3[i] = calcPI(x0, y0, r0, expNumb[i]);
            seria4[i] = calcPI(x0, y0, r0, expNumb[i]);
            seria5[i] = calcPI(x0, y0, r0, expNumb[i]);
            eps1[i] = Math.abs((seria1[i] - Math.PI) / Math.PI);
            eps2[i] = Math.abs((seria2[i] - Math.PI) / Math.PI);
            eps3[i] = Math.abs((seria3[i] - Math.PI) / Math.PI);
            eps4[i] = Math.abs((seria4[i] - Math.PI) / Math.PI);
            eps5[i] = Math.abs((seria5[i] - Math.PI) / Math.PI);
            sE[i] = (seria1[i] + seria2[i] + seria3[i] + seria4[i] + seria5[i]) / 5;
            epsSE[i] = Math.abs((sE[i] - Math.PI) / Math.PI);
        }
        System.out.println("seria1: " + Arrays.toString(seria1) + '\n' + "seria2: " + Arrays.toString(seria2) + '\n' +
                "seria3: " + Arrays.toString(seria3) + '\n' + "seria4: " + Arrays.toString(seria4) + '\n' +
                "seria5: " + Arrays.toString(seria5) + '\n' + "eps1: " + Arrays.toString(eps1) + '\n' +
                "eps2: " + Arrays.toString(eps2) + '\n' + "eps3: " + Arrays.toString(eps3) + '\n' +
                "eps4: " + Arrays.toString(eps4) + '\n' + "eps5: " + Arrays.toString(eps5) + '\n' + "Se: " + Arrays.toString(sE) +
        '\n' + "epsSE: " + Arrays.toString(epsSE));*/
        System.out.println(calcIntegral(0, 2, Math.pow(10, 4)));
    }

    public static double f(double x) {
        return Math.pow(x, 3) + 1;
    }

    public static double calcIntegral(int a, int b, double expNumb) {
        int xmin = a;
        int xmax = b;
        int ymin = 0;
        double ymax = f(b);
        int m = 0;
        for (int i = 0; i < expNumb; i++) {
            double x = (xmax - xmin) * Math.random() + xmin;
            double y = (ymax - ymin) * Math.random() + ymin;
            if (f(x) > y)
                m += 1;
        }
        return (m / expNumb) * (b - a) * f(b);
    }

    public static double calcPI(int x0, int y0, int r0, double expNmb) {
        int xmin = x0 - r0;
        int xmax = x0 + r0;
        int ymin = y0 - r0;
        int ymax = y0 + r0;
        int m = 0;
        for (int i = 0; i < expNmb; i++) {
            double x = (xmax - xmin) * Math.random() + xmin;
            double y = (ymax - ymin) * Math.random() + ymin;
            if (Math.pow(x - x0, 2) + Math.pow(y - y0, 2) < Math.pow(r0, 2))
                m += 1;
        }
        return (m / expNmb) * 4;
    }
}