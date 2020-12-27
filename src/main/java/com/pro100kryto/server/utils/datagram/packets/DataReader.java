package com.pro100kryto.server.utils.datagram.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DataReader{
    protected final DataContainer bufferContainer;
    protected int counter = 0;

    public DataReader(DataContainer data, int pos){
        this(data);
        setPosition(pos);
    }

    public DataReader(DataContainer data){
        this.bufferContainer = data;
    }


    public int getPosition(){
        return counter;
    }

    /**
     * broke current position
     */
    public void setPosition(int pos){
        counter = pos;
    }

    public int getDataLength(){
        return counter;
    }

    public boolean isEmpty(){
        return counter==0;
    }

    /**
     * reset position and data container
     */
    public void reset() {
        setPosition(0);
        bufferContainer.reset();
    }


    public byte readByte(){
        return bufferContainer.get(counter++);
    }

    public short readShort(){
        short sh = ByteBuffer.wrap(new byte[]{
                bufferContainer.get(counter+1), bufferContainer.get(counter)
        }).getShort();
        counter+=2;
        return sh;
    }

    public int readInt(){
        int i = ByteBuffer.wrap(new byte[]{
                bufferContainer.get(counter+3), bufferContainer.get(counter+2),
                bufferContainer.get(counter+1), bufferContainer.get(counter)
        }).getInt();
        counter+=4;
        return i;
    }

    public String readByteString(){
        byte len = readByte();
        return readString(len);
    }

    public String readShortString(){
        short len = readShort();
        return readString(len);
    }

    public String readIntString(){
        int len = readInt();
        return readString(len);
    }

    public String readString(int len){
        byte[] strBytes = bufferContainer.copyOfRange(counter, (counter+=len));
        //ArrayUtils.reverse(strBytes);
        return new String(strBytes, StandardCharsets.UTF_8);
    }

    // ------ static

    public static byte[] readReverse(byte[] data, int pos, int count){
        byte[] res = new byte[count];
        for (int i = 0; i < count; i++) {
            res[count-i-1] = data[i];
        }
        return res;
    }

    public static short readShort(byte[] data, int pos){
        return ByteBuffer.wrap(new byte[]{
                data[pos+1], data[pos]
        }).getShort();
    }

    public static int readInt(byte[] data, int pos){
        return ByteBuffer.wrap(new byte[]{
                data[pos+3], data[pos+2],
                data[pos+1], data[pos]
        }).getInt();
    }

    public static long readLong(byte[] data, int pos){
        return ByteBuffer.wrap(new byte[]{
                data[pos+7], data[pos+6], data[pos+5], data[pos+4],
                data[pos+3], data[pos+2], data[pos+1], data[pos]
        }).getLong();
    }

    @Override
    public DataReader clone() {
        return new DataReader(bufferContainer.clone(), counter);
    }
}
