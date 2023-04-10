package AnimationStudio3;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Animation {

    private ArrayList<AnimationData> frames;
    private ArrayList<PDouble> timePoints;
    private AnimationData data;

    private int currentFrame;
    private AnimationData startData;
    private double startTime;
    private boolean paused = true;

    private double passedFrameSum;
    private double timePassed;

    public Animation(ArrayList<AnimationData> frames, ArrayList<PDouble> timePoints, AnimationData data) {
        this.frames = frames;
        this.timePoints = timePoints;
        this.data = data;

        if (timePoints.size() != frames.size()) {
            throw new IllegalArgumentException("Animation::new: timePoints.length == frames.length;\n timePoints.length: " + timePoints.size() + "\t frames.length: " + frames.size() + "\n");
        }
    }

    public Animation(ArrayList<AnimationData> frames, double[] timePoints, AnimationData data) {
        this(frames, PDouble.ArrayListFromArray(timePoints), data);
    }

    public void play() {
        startData = data.clone();
        startTime = System.currentTimeMillis();
        paused = false;
        currentFrame = 0;
        passedFrameSum = 0;
    }

    public void pause() {
        if (paused) {
            return;
        }

        paused = true;
        timePassed = System.currentTimeMillis() - startTime;
    }

    public void resume() {
        if (!paused) {
            return;
        }
        paused = false;
        startTime = System.currentTimeMillis() - (timePassed + passedFrameSum);
    }

    public boolean isPaused() {
        return paused;
    }

    public void update() {
        if (paused) {
            return;
        }

        if (!isFinished()) {
            timePassed = ((System.currentTimeMillis() - startTime) / 1000.0) - passedFrameSum;
            moveData(currentFrame, timePassed);
        }
    }

    public boolean isFinished() {
        return currentFrame >= frames.size();
    }

    private void moveData(int currentFrame, double timePassed) {
        if (isFinished()) {
//            for (int i = 0; i < data.size(); i++) {
//                data.set(i, frames.get(frames.size() - 1).get(i).val);
//                data.set(i, frames.get(0).get(i).val);
//            }
            play();
            return;
        }

        double currentFrameTime = timePoints.get(currentFrame).val;
        if (timePassed > currentFrameTime) {
            this.currentFrame++;
            passedFrameSum += currentFrameTime;
            moveData(this.currentFrame, timePassed - currentFrameTime);
        } else {
            double fraction = timePassed / currentFrameTime;
            AnimationData previousFrame = currentFrame == 0 ? startData : frames.get(currentFrame - 1);
            for (int i = 0; i < data.size(); i++) {
                double lastVal = previousFrame.get(i).val;
                double nextVal = frames.get(currentFrame).get(i).val;

                data.set(i, lastVal + (nextVal - lastVal) * fraction);
            }
        }
    }

    public AnimationData getDataAtFrame(int index) {
        return frames.get(index);
    }

    public PDouble getTimeAtFrame(int index) {
        return timePoints.get(index);
    }

    public void setDataAtFrame(int index, AnimationData data) {
        if (index < frames.size()) {
            frames.set(index, data);
        } else {
            frames.add(data);
        }
    }

    public void setTimeAtFrame(int index, PDouble time) {
        if (index < timePoints.size()) {
            timePoints.get(index).val = time.val;
        } else {
            timePoints.add(time);
        }
    }

    public void removeFrame(int frameIndex) {
        frames.remove(frameIndex);
        timePoints.remove(frameIndex);
    }

    public int getIndex(AnimationData data) {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i) == data) {
                return i;
            }
        }

        return -1;
    }

    public AnimationData getDisplayData() {
        return data;
    }

    public void setDisplayData(AnimationData data) {
        this.data = data;
    }

    public int getNumFrames() {
        return frames.size();
    }

    public void timeScale(double scalar) {
        for (int i = 0; i < timePoints.size(); i++) {
            timePoints.get(i).val *= scalar;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < frames.size(); i++) {
            stringBuilder.append(frames.get(i)).append('\n');
        }

        stringBuilder.append('\n').append(Util.PDoubleArrayListToString(timePoints));

        return stringBuilder.toString();
    }

    public static Animation fromString(String string) {
        ArrayList<AnimationData> frames = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(string, "\n]");

        int numTokens = tokenizer.countTokens() - 1;
        for (int i = 0; i < numTokens; i++) {
            String nextStr = tokenizer.nextToken();
            frames.add(AnimationData.fromString(nextStr + ']'));
        }

        double[] timesPoints = Util.ParseDoubleArray(tokenizer.nextToken() + "]");

        return new Animation(frames, timesPoints, new AnimationData(frames.get(0).size()));
    }

}
