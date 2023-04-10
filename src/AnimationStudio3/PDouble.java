package AnimationStudio3;

import java.util.ArrayList;

public class PDouble {

    public double val = 0.0;

    public PDouble(double val) {
        this.val = val;
    }

    public int intValue() {
        return (int) val;
    }

    public static PDouble[] ArrayFromArray(double[] array) {
        PDouble[] newArray = new PDouble[array.length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = new PDouble(array[i]);
        }

        return newArray;
    }

    public static ArrayList<PDouble> ArrayListFromArray(double[] array) {
        ArrayList<PDouble> newArrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            newArrayList.add(new PDouble(array[i]));
        }

        return newArrayList;
    }

    public static ArrayList<PDouble> fromArrayList(ArrayList<Double> arrayList) {
        ArrayList<PDouble> newArrayList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            newArrayList.add(new PDouble(arrayList.get(i)));
        }

        return newArrayList;
    }

}
