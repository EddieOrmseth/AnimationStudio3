package AnimationStudio3;

import java.io.Serializable;

public class AnimationData implements Cloneable, Serializable {

    private final PDouble[] data;

    public AnimationData(int length) {
        data = new PDouble[length];
        for (int i = 0; i < length; i++) {
            data[i] = new PDouble(0.0);
        }
    }

    public AnimationData(PDouble[] data) {
        this.data = data;
    }

    public AnimationData(double[] data) {
        this.data = new PDouble[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new PDouble(data[i]);
        }
    }

    public AnimationData(AnimationData dataIn) {
        data = new PDouble[dataIn.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = new PDouble(dataIn.get(i).val);
        }
    }

    public int size() {
        return data.length;
    }

    public PDouble get(int index) {
        return data[index];
    }

    public void set(int index, double value) {
        data[index].val = value;
    }

    public void set(AnimationData newData) {
        if (size() != newData.size()) {
            throw new IllegalArgumentException("AnimationData::set(AnimationData): Input data must have the same length as object!\nObject Length: " + size() + "\nNew Data Length: " + newData.size());
        }

        for (int i = 0; i < newData.size(); i++) {
            data[i].val = newData.get(i).val;
        }
    }

    public void add(PDouble addition) {
        for (int i = 0; i < data.length; i++) {
            set(i, get(i).val + addition.val);
        }
    }

    public void scale(double scalar) {
        for (int i = 0; i < data.length; i++) {
            set(i, get(i).val * scalar);
        }
    }

    @Override
    public AnimationData clone() {
        return new AnimationData(this);
    }

    @Override
    public String toString() {
        return Util.PDoubleArrayToString(data);
    }

    public static AnimationData fromString(String string) {
        return new AnimationData(Util.ParseDoubleArray(string));
    }


}
