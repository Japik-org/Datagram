package com.pro100kryto.server.utils.datagram.packet;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataContainer {
    private final byte[] buffer;
    private int minPos = 0;
    private int maxPos;

    public DataContainer(int bufferSize){
        this(new byte[bufferSize], bufferSize);
    }

    public DataContainer(byte[] buffer) {
        this(buffer, buffer.length);
    }

    public DataContainer(byte[] buffer, int maxPos) {
        this.buffer = buffer;
        this.maxPos = maxPos;
    }

    public DataContainer(byte[] buffer, int maxPos, int minPos) {
        this.buffer = buffer;
        this.maxPos = maxPos;
        this.minPos = minPos;
    }

    public final void cutLeft(int length) {
        this.minPos += length;
    }

    public final int getLength(){
        return maxPos-minPos;
    }

    public final void setLength(int len) {
        this.maxPos = Math.min(len+minPos,buffer.length);
    }

    public final int getMaxLength(){
        return buffer.length-minPos;
    }

    public final void setMaxLength(){
        setLength(getMaxLength());
    }

    public final void cutRight(int length) {
        this.maxPos -= length;
    }

    public final void set(int pos, byte val){
        buffer[pos+minPos] = val;
    }

    public final byte get(int pos){
        return buffer[pos+minPos];
    }

    public final byte[] copyOfRange(int from, int to){
        return Arrays.copyOfRange(buffer, from+minPos, to+minPos);
    }

    public final void writeFrom(byte[] src, int srcPos, int destPos, int length) {
        System.arraycopy(src, srcPos, buffer, destPos+minPos, length);
    }

    public final byte[] copyData(){
        return Arrays.copyOfRange(buffer, minPos, maxPos);
    }

    public final int getBufferSize(){
        return buffer.length;
    }

    /**
     * Slow operation!
     * @param doNotResize More faster, but loss bytes on right side
     */
    public final void addLeft(boolean doNotResize, byte... data) {
        int len = data.length;
        if (!doNotResize) maxPos = Math.min(buffer.length, maxPos+len);

        // step 1 move all data to right
        for (int i=maxPos-len-1; i>=minPos; i--){
            buffer[i+len] = buffer[i];
        }
        // step 2 add new data to left
        for (int i = 0; i < len; i++) {
            buffer[i+minPos] = data[i];
        }
    }

    /**
     * use with caution. Access to full buffer
     */
    public final byte[] getRaw(){
        return buffer;
    }

    public void reset(){
        minPos=0;
        maxPos=buffer.length;
    }

    public void putDataInDatagramPacket(final DatagramPacket datagramPacketOut){
        datagramPacketOut.setData(getRaw(), minPos, getLength());
    }

    @Override
    protected DataContainer clone() {
        return new DataContainer(
                buffer.clone(),
                maxPos, minPos);
    }

    public int getOffsetOfRaw() {
        return minPos;
    }

    public int getPositionOfRaw(int position){
        return position + minPos;
    }
}
