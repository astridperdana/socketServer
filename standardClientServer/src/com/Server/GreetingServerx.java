package com.Server;

// File Name GreetingServerx.java
// untuk compile     : javac GreetingServerx.java
// untuk menjalankan : java GreetingServerx 4321
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GreetingServerx extends Thread {

    private ServerSocket serverSocket;

    public GreetingServerx(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        String strku, usrChoice, path;

        System.out.println("Waiting for client on port "
                + serverSocket.getLocalPort() + "...");

        while (true) {
            try {

                Socket server = serverSocket.accept();

                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                strku = in.readUTF();
                System.out.println(" ");
                System.out.println(strku);

                usrChoice = in.readUTF();

                if (usrChoice.equals("0")) {
                    out.writeUTF("Terimakasih Telah Terkoneksi ke Server " + server.getLocalSocketAddress() + "\n"
                            + "\nBerikut Menu yang Telah Kami Siapkan :"
                            + "\n1. Membuat Direktori"
                            + "\n2. Melihat Isi Direktori"
                            + "\n3. Mendelete file/folder"
                            + "\nSilahkan ganti pilihan anda.");
                } else if (usrChoice.equals("1")) {
                    path = in.readUTF();
                    File folder = new File(path);
                    if (folder.exists()) {
                        out.writeUTF("Nama Folder sudah ada.");
                    } else {
                        folder.mkdir();
                        System.out.println("Folder telah dibuat pada lokasi : " + path);
                        out.writeUTF("Folder Telah Dibuat pada lokasi : " + path + " .");
                    }
                } else if (usrChoice.equals("2")) {
                    path = in.readUTF();
                    String f = "", d = "";
                    ArrayList file1 = new ArrayList();
                    ArrayList dir1 = new ArrayList();
                    File directory = new File(path);
                    //get all the files from a directory
                    File[] fList = directory.listFiles();
                    for (File file : fList) {
                        if (file.isFile()) {
                            file1.add(file.getName());
                        } else if (file.isDirectory()) {
                            dir1.add(file.getName());
                        }
                    }
                    Iterator fileit = file1.iterator();
                    Iterator dirit = dir1.iterator();
                    while (fileit.hasNext()) {
                        f += fileit.next() + "\n";
                    }
                    while (dirit.hasNext()) {
                        d += dirit.next() + "\n";
                    }
                    out.writeUTF("Folder : \n" + d + "File : \n" + f);
                    System.out.println("List File dan Direktori Telah Dikirimkan.");
                } else if (usrChoice.equals("3")) {
                    path = in.readUTF();
                    File file = new File(path);

                    if (file.delete()) {
                        out.writeUTF("Folder/file telah terhapus.");
                    } else {
                        out.writeUTF("Gagal menghapus file/folder.");
                    }
                } else {
                    out.writeUTF("Pilihan ini tidak ada.");
                }

                server.close();
            } catch (SocketTimeoutException s) {
                System.out.print(".");   // menunggu
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 1234;
        try {
            Thread t = new GreetingServerx(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
