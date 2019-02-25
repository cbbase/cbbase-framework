package com.cbbase.core.tools;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * Twitter的SnowFlake
 * @author Administrator
 *
 */
public class IdWorker{

    private long twepoch = 1483200000000L;
    
    private long dataCenterIdBits = 5L;
    private long workerIdBits = 5L;
    private long sequenceBits = 12L;
    
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxdataCenterId = -1L ^ (-1L << dataCenterIdBits);

    private long workerIdShift = sequenceBits;
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    //
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long workerIdMask = -1L ^ (-1L << workerIdBits);
    
    //
    private long workerId;
    private long dataCenterId;
    private long sequence;
    
    private static IdWorker idWorker = null;
    
    static {
    	idWorker = new IdWorker();
    }
    
    private IdWorker(){
        this.dataCenterId = 1;
        this.workerId = getMachineNum() & workerIdMask;
        this.sequence = 1;
    }
    
    public IdWorker(long workerId, long dataCenterId){
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("workerId error");
        }
        if (dataCenterId > maxdataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId error");
        }
        
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.sequence = 1;
    }
    
    public static IdWorker getDefault() {
    	return idWorker;
    }

    private long getMachineNum() {
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (e.hasMoreElements()) {
            sb.append(e.nextElement().toString());
        }
        long machinePiece = sb.toString().hashCode();
        return machinePiece;
    }

    private long lastTimestamp = -1L;

    public long getWorkerId(){
        return workerId;
    }

    public long getdataCenterId(){
        return dataCenterId;
    }

    public long getTimestamp(){
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("clock is moving backwards");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }

}
