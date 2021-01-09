package JDBCData;
import java.util.*;

public class costList implements Comparable{
    private int event;
    private String  userName;
    private int count;
    private String  Reason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        costList costList = (costList) o;
        return event == costList.event &&
                count == costList.count &&
                Objects.equals(userName, costList.userName) &&
                Objects.equals(Reason, costList.Reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, userName, count, Reason);
    }

    public int getEvent() {
        return event;
    }



    public int getCount() {
        return count;
    }


    public String getReason() {
        return Reason;
    }

    @Override
    /**
     * 按count大小排降序
     */
    public int compareTo(Object o) {
        if(o instanceof costList){
            costList costList=(JDBCData.costList)o;
            if(this.count>costList.count)
                return -1;
            else if(this.count<costList.count)
                return 1;
            else return 0;
        }
        throw new RuntimeException("类型不匹配");
    }
}
