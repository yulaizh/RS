package bean;
/**
 * 没用呢
 * */
public class LoadBean {

    long[] currentTimeArray = new long[14];

    public void setCurrentTimeArray(long[] currentTimeArray, int index ,long currentTime) {
        currentTimeArray[index] = currentTime;
        this.currentTimeArray = currentTimeArray;
    }

    public long getCurrentTimeArray(int index) {
        return currentTimeArray[index];
    }
}
