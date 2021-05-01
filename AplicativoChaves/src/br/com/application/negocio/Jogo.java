package br.com.application.negocio;

import org.postgresql.core.Utils;

import java.io.*;

public class Jogo {

    String nome;
    byte[] img;

    public Jogo(String nome, File img) throws FileNotFoundException {

        this.nome = nome;
        this.img = ImageToByte(img);

    }

    public Jogo(String nome, byte[] img) throws FileNotFoundException {

        this.nome = nome;
        this.img = img;

    }

    public String getNome(){
        return nome;
    }

    public byte[] getImg() {
        return img;
    }

    public byte [] ImageToByte(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {

                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");

            }

        } catch (IOException ex) {

        }

        byte[] bytes = bos.toByteArray();
        return bytes;

    }


}
