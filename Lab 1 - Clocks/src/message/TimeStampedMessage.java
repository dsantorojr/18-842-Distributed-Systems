/**
 * 18-842 Distributed Systems
 * Spring 2016
 * Lab 1: Clocks
 * TimeStampedMessage.java
 * Daniel Santoro // ddsantor. Akansha Patel // akanshap.
 */
 
// implement a package for this lab assignment
package DistSystComm;

// import utility packages
import java.util.ArrayList;

/**
 * TimeStampedMessage extends Message and implements Comparable
 * 
 * Description:
 *  - TimeStampedMessage extends Message to inherit all methods and fields contained within
 *  - TimeStampedMessage implements comparable to make it very simple for the 
 *      logger to sort events.
 *  - TimeStampedMessage adds a serializable timestamp to the beginning of each message
 *      so the system can keep track of global time.
 */
public class TimeStampedMessage extends Message implements Comparable<TimeStampedMessage> {
    private static final long serialVersionUID = 1L;

    // native timestamp field
    private TimeStamp timeStamp;
    
    /**
     * TimeStampedMessage constructor 
     * 
     * @param message - message to which a time stamp needs added
     * @param timeStamp - timestamp to be added to the mesage;
     */
    public TimeStampedMessage(Message message, TimeStamp timeStamp) {
        super();
        this.setKind(message.getKind());
        this.setSource(message.getSource());
        this.setDestination(message.getDestination());
        this.setSeqNum(message.getSeqNum());
        this.setData(message.getData());
        this.timeStamp = timeStamp.copyOf();
    }
    
    /** 
     * TimeStampedMessage constructor 
     * 
     * @param kind - type of message
     * @param source - source of the message
     * @param destination - destination of the message
     * @param seqNum - sequence number of the message
     * @param data - data to be contained in the message
     * @param timeStamp - times stamp to be added ot the message
     */
    public TimeStampedMessage(String kind, String source, String destination, 
                                int seqNum, Object data, TimeStamp timeStamp) {
        super(kind, source, destination, seqNum, data);
        this.timeStamp = timeStamp.copyOf();
    }
    
    /**
     * setTimeSTamp
     * 
     * @param timeStamp - new time stamp
     */
    public void setTimeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp.copyOf();
    }
    
    /**
     * getTimeStamp
     * 
     * @returns current time stamp
     */
    public TimeStamp getTimeStamp() {
        return this.timeStamp;
    }

    /**
     * compareTo - compares this timestamp to another time stamp to establish
     *  ordering.
     * 
     * @param compare - time stamp to which to compare
     * @returns integer indicating the relative time of the two messages
     */
    @Override
    public int compareTo(TimeStampedMessage compare) {
        int compareVal = 0;
        TimeStamp compareTimeStamp = compare.getTimeStamp();
        
        // Logical time stamp comparison
        if(compareTimeStamp instanceof LogicalTimeStamp) {
            LogicalTimeStamp currentTime = (LogicalTimeStamp)this.timeStamp;
            LogicalTimeStamp compareTime = (LogicalTimeStamp)compareTimeStamp;
            compareVal = currentTime.compareTo(compareTime);
        } 
        // Vector time stamp comparison
        else if (compareTimeStamp instanceof VectorTimeStamp) {
            VectorTimeStamp currentTime = (VectorTimeStamp)this.timeStamp;
            VectorTimeStamp compareTime = (VectorTimeStamp)compareTimeStamp;
            compareVal = currentTime.compareTo(compareTime);            
        }
        return compareVal;
    }
    
    @Override
    public String toString() {
        return "TSM[" + timeStamp.toString() + ", seqNum=" + this.getSeqNum() + 
            " , src->dest=" + this.getSource() + "->" + 
            this.getDestination() + ", kind=" + this.getKind() +
            ", payload=" + this.getData() + "]";
    }
}