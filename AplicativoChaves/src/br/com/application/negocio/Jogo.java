package br.com.application.negocio;



import java.io.*;

public class Jogo {

    private int idJogo;
    private String nome;
    private byte[] img;

    public Jogo(String nome, File img) throws IOException {

        this.nome = nome;
        this.img = ImageToByte(img);

    }

    public Jogo(String nome, byte[] img) throws FileNotFoundException {

        this.nome = nome;
        this.img = img;

    }

    public Jogo(int id, String nome, byte[] img) throws FileNotFoundException {

        this.idJogo = id;
        this.nome = nome;
        this.img = img;

    }

    public int getIdJogo() { return  this.idJogo; }

    public String getNome(){
        return nome;
    }

    public byte[] getImg() {
        return img;
    }

    public byte [] ImageToByte(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {

                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }



        byte[] bytes = bos.toByteArray();
        return bytes;

    }


}
