package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RegistruBinarService{
    private static final int RECORD_SIZE = 32;
    private static final int DATA_SIZE = 10;
    private static final int STATUS_OFFSET = 23;

    public void scrieInitial(List<InregistrareTranzactie> tranzactii, String filePath)
            throws IOException{
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null){
            parent.mkdirs();
        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))){
            for (InregistrareTranzactie tranzactie : tranzactii){
                scrieInregistrare(out, tranzactie);
            }
        }
    }

    public InregistrareTranzactie citeste(String filePath, int idx) throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")){
            raf.seek((long) idx * RECORD_SIZE);

            byte[] record = new byte[RECORD_SIZE];
            raf.readFully(record);

            return decode(record);
        }
    }

    public void actualizeazaStatus(String filePath, int idx, StatusTranzactie status)
            throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")){
            raf.seek((long) idx * RECORD_SIZE + STATUS_OFFSET);
            raf.write(statusToByte(status));
        }
    }

    private void scrieInregistrare(DataOutputStream out, InregistrareTranzactie t)
            throws IOException{
        out.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(t.getId()).array());

        out.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(t.getSuma()).array());

        byte[] dataBytes = t.getData().getBytes(StandardCharsets.US_ASCII);
        for (int i = 0; i < DATA_SIZE; i++){
            if (i < dataBytes.length) {
                out.writeByte(dataBytes[i]);
            } else{
                out.writeByte(' ');
            }
        }

        out.writeByte(tipToByte(t.getTip()));
        out.writeByte(statusToByte(t.getStatus()));

        for (int i = 0; i < 8; i++){
            out.writeByte(0);
        }
    }

    private InregistrareTranzactie decode(byte[] record){
        ByteBuffer buffer = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

        int id = buffer.getInt(0);
        double suma = buffer.getDouble(4);

        String data = new String(record, 12, DATA_SIZE, StandardCharsets.US_ASCII).trim();

        TipTranzactie tip = byteToTip(record[22]);
        StatusTranzactie status = byteToStatus(record[23]);

        return new InregistrareTranzactie(id, suma, data, tip, status);
    }

    private byte tipToByte(TipTranzactie tip){
        return (byte) (tip == TipTranzactie.CREDIT ? 0 : 1);
    }

    private TipTranzactie byteToTip(byte value){
        return value == 0 ? TipTranzactie.CREDIT : TipTranzactie.DEBIT;
    }

    private byte statusToByte(StatusTranzactie status){
        if (status == StatusTranzactie.PENDING){
            return 0;
        }

        if (status == StatusTranzactie.PROCESSED){
            return 1;
        }

        return 2;
    }

    private StatusTranzactie byteToStatus(byte value){
        if (value == 0){
            return StatusTranzactie.PENDING;
        }

        if (value == 1){
            return StatusTranzactie.PROCESSED;
        }

        return StatusTranzactie.REJECTED;
    }
}