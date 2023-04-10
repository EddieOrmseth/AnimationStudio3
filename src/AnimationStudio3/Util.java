package AnimationStudio3;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Util {

    public static double[] ParseDoubleArray(String string) {
        String withoutBrackets = string.substring(1, string.length() - 1);
        StringTokenizer tokenizer = new StringTokenizer(withoutBrackets, ",");

        int numTokens = tokenizer.countTokens();
        double[] data = new double[numTokens];
        for (int i = 0; i < numTokens; i++) {
            data[i] = Double.parseDouble(tokenizer.nextToken());
        }

        return data;
    }

    public static String PDoubleArrayToString(PDouble[] array) {
        StringBuilder stringBuilder = new StringBuilder("[" + array[0].val);

        for (int i = 1; i < array.length; i++) {
            stringBuilder.append(", ").append(array[i].val);
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    public static String PDoubleArrayListToString(ArrayList<PDouble> array) {
        return PDoubleArrayToString(array.toArray(new PDouble[0]));
    }

}
