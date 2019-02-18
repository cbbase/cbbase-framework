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
    
    private long datacenterIdBits = 5L;
    private long workerIdBits = 5L;
    private long sequenceBits = 12L;
    
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long workerIdMask = -1L ^ (-1L << workerIdBits);
    
    //
    private long workerId;
    private long datacenterId;
    private long sequence;
    
    private static IdWorker idWorker = null;
    
    static {
    	idWorker = new IdWorker();
    }
    
    private IdWorker(){
        this.datacenterId = 1;
        this.workerId = getMachineNum() & workerIdMask;
        this.sequence = 1;
    }
    
    public IdWorker(long workerId, long datacenterId){
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("workerId error");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId error");
        }
        
        this.datacenterId = datacenterId;
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

    public long getDatacenterId(){
        return datacenterId;
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
                (datacenterId << datacenterIdShift) |
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
