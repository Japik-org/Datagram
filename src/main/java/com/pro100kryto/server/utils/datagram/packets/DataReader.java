package com.pro100kryto.server.utils.datagram.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class DataReader{
    public static final ByteOrder BYTE_ORDER_DEFAULT = ByteOrder.LITTLE_ENDIAN;
    protected final DataContainer bufferContainer;
    protected int counter = 0;
    protected ByteOrder byteOrder;

    public DataReader(DataContainer data, int pos){
        this(data, pos, BYTE_ORDER_DEFAULT);
    }

    public DataReader(DataContainer data, int pos, ByteOrder byteOrder){
        bufferContainer = data;
        setPosition(pos);
        this.byteOrder = byteOrder;
    }

    public DataReader(DataContainer data){
        this(data, BYTE_ORDER_DEFAULT);
    }

    public DataReader(DataContainer data, ByteOrder byteOrder){
        this.bufferContainer = data;
        this.byteOrder = byteOrder;
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
     * reset position, min pos, max pos
     */
    public void reset() {
        setPosition(0);
        bufferContainer.reset();
    }

    public boolean readBool(){
        return readByte() == 1;
    }

    public byte readByte(){
        return bufferContainer.get(counter++);
    }

    public short readShort(){
        final short sh = readShort(
                bufferContainer.getRaw(),
                bufferContainer.getPositionOfRaw(counter),
                byteOrder);
        counter+=2;
        return sh;
    }

    public int readInt(){
        final int i = readInt(
                bufferContainer.getRaw(),
                bufferContainer.getPositionOfRaw(counter),
                byteOrder);
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

    public float readFloat(){
        final float f = readFloat(
                bufferContainer.getRaw(),
                bufferContainer.getPositionOfRaw(counter),
                byteOrder);
        counter+=4;
        return f;
    }

    public String readString(int len){
        byte[] strBytes = bufferContainer.copyOfRange(counter, counter+=len);
        return new String(strBytes, StandardCharsets.UTF_8);
    }

    public DataContainer getDataContainer() {
        return bufferContainer;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    // ------ static

    public static byte[] copyReverse(byte[] data, int pos, int count){
        byte[] res = new byte[count];
        int resIndex = 0;
        for (int i = pos+count-1; i >= pos; i--) {
            res[resIndex++] = data[i];
        }
        return res;
    }

    public static short readShort(byte[] data, int pos, ByteOrder byteOrder){
        return ByteBuffer.wrap(data, pos, 2).order(byteOrder).getShort();
    }

    public static int readInt(byte[] data, int pos, ByteOrder byteOrder){
        return ByteBuffer.wrap(data, pos, 4).order(byteOrder).getInt();
    }

    public static long readLong(byte[] data, int pos, ByteOrder byteOrder){
        return ByteBuffer.wrap(data, pos, 8).order(byteOrder).getLong();
    }

    public static float readFloat(byte[] data, int pos, ByteOrder byteOrder){
        return ByteBuffer.wrap(data, pos, 4).order(byteOrder).getFloat();
    }

    @Override
    public DataReader clone() {
        return new DataReader(bufferContainer.clone(), counter);
    }

}
