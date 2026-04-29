package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.List;

public class TranzactieService{
    public void serializeaza(List<Tranzactie> tranzactii, String filePath) throws IOException{
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))){
            out.writeObject(tranzactii);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Tranzactie> deserializeaza(String filePath)
            throws IOException, ClassNotFoundException{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
            return (List<Tranzactie>) in.readObject();
        }
    }
}