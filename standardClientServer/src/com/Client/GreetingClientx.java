package com.Client;
// File Name GreetingClientx.java
// untuk compile     : javac GreetingClientx.java
// untuk menjalankan : java GreetingClientx localhost 4321

import java.net.*;
import java.io.*;

public class GreetingClientx
{
   public static void main(String [] args)
   {
      String serverName = "localhost";
      int port = 1234;
      try
      {
         System.out.println("Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to "
                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =
                       new DataOutputStream(outToServer);

         out.writeUTF("client0 "
                      + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
         System.out.println("Server says :" + in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}


