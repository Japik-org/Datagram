package com.pro100kryto.server.utils.datagram.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class DataCreator extends DataReader{

    public DataCreator(int bufferSize){
        this(new byte[bufferSize]);
    }

    public DataCreator(byte[] buffer){
        this(new DataContainer(buffer));
    }

    public DataCreator(DataContainer bufferContainer){
        super(bufferContainer);
    }

    public int getRemainingSize(){
        return bufferContainer.getLength()-getPosition();
    }

    public void writeFromByteStream(ByteArrayInputStream stream) {
        int len = Math.min(stream.available(), bufferContainer.getLength());
        for (int i = 0; i < len; i++) {
            bufferContainer.set(counter++, (byte) stream.read());
        }
    }

    public void writeFromByteStream(PipedInputStream stream) throws IOException {
        int len = Math.min(stream.available(), bufferContainer.getLength());
        for (int i = 0; i < len; i++) {
            bufferContainer.set(counter++, (byte) stream.read());
        }
    }

    public void writeFromByteStream(ByteArrayOutputStream stream) throws IOException {
        write(stream.toByteArray());
    }

    public void write(boolean... bools){
        for (boolean b: bools){
            bufferContainer.set(counter, (byte)(b?1:0));
            counter++;
        }
    }

    public void write(byte... bytes){
        int len = bytes.length;
        bufferContainer.writeFrom(bytes, 0, counter, len);
        counter+=len;
    }

    public void write(byte[] bytes, int from, int len){
        bufferContainer.writeFrom(bytes, from, counter, len);
    }

    public void write(short... shorts){
        for (short s: shorts){
            writeTo(bufferContainer.getRaw(), bufferContainer.getPositionOfRaw(counter), s, byteOrder);
            counter+=2;
        }
    }

    public void write(int... ints){
        for (int i: ints){
            writeTo(bufferContainer.getRaw(), bufferContainer.getPositionOfRaw(counter), i, byteOrder);
            counter += 4;
        }
    }

    public void write(long... longs){
        for (long l: longs){
            writeTo(bufferContainer.getRaw(), bufferContainer.getPositionOfRaw(counter), l, byteOrder);
            counter += 8;
        }
    }

    public void write(float... floats){
        for (float f: floats){
            writeTo(bufferContainer.getRaw(), bufferContainer.getPositionOfRaw(counter), f, byteOrder);
            counter += 4;
        }
    }

    public void write(String... strings){
        for (String i: strings){
            byte[] iBytes = i.getBytes(StandardCharsets.UTF_8);
            write(iBytes);
        }
    }

    public void writeByteStrings(String... strings){
        for (String i: strings){
            byte[] iBytes = i.getBytes(StandardCharsets.UTF_8);
            write((byte) iBytes.length);
            write(iBytes);
        }
    }

    public void writeShortStrings(String... strings){
        for (String i: strings){
            byte[] iBytes = i.getBytes(StandardCharsets.UTF_8);
            write((short)iBytes.length);
            write(iBytes);
        }
    }

    public void writeIntStrings(String... strings){
        for (String i: strings){
            byte[] iBytes = i.getBytes(StandardCharsets.UTF_8);
            write((int)iBytes.length);
            write(iBytes);
        }
    }

    /**
     * cut unnecessary length
     */
    public void close(){
        bufferContainer.setLength(counter);
    }

    public byte[] createData(){
        return bufferContainer.copyData();
    }

    /**
     * cut unnecessary length and get data (recommended)
     */
    public byte[] closeAndCreateData(){
        close();
        return createData();
    }

    // utility
    public IPacketInProcess createPacketInProcess(InetAddress address, int port){
        final Packet packet = new Packet(bufferContainer, address, port);
        packet.getDataCreator().setPosition(getDataLength()+1);
        return packet;
    }

    // ----------- static

    public static void writeTo(byte[] dataDes, int pos, short val, ByteOrder byteOrder){
        final ByteBuffer buffer = ByteBuffer.allocate(2).order(byteOrder).putShort(val);
        buffer.rewind();
        buffer.get(dataDes, pos, 2);
    }

    public static void writeTo(byte[] dataDes, int pos, int val, ByteOrder byteOrder){
        final ByteBuffer buffer = ByteBuffer.allocate(4).order(byteOrder).putInt(val);
        buffer.rewind();
        buffer.get(dataDes, pos, 4);
    }

    public static void writeTo(byte[] dataDes, int pos, long val, ByteOrder byteOrder) {
        final ByteBuffer buffer = ByteBuffer.allocate(8).order(byteOrder).putLong(val);
        buffer.rewind();
        buffer.get(dataDes, pos, 8);
    }

    public static void writeTo(byte[] dataDes, int pos, float val, ByteOrder byteOrder) {
        final ByteBuffer buffer = ByteBuffer.allocate(4).order(byteOrder).putFloat(val);
        buffer.rewind();
        buffer.get(dataDes, pos, 4);
    }

    public void cutLeft(int count) {
        bufferContainer.cutLeft(count);
    }

    public void addLeft(boolean doNotResize, byte... data) {
        bufferContainer.addLeft(doNotResize, data);
        counter += data.length;
    }
}
