package com.bosssoft.hr.train.j2se.basic.example.socket;

/**
 * @description: 客户端启动类
 * @author: lwh
 * @create: 2020/11/5 14:51
 * @version: v1.0
 **/
public class ClientSocketApplication {
    public static void main(String[] args){
        Starter clientSocket=new ClientSocket();
        clientSocket.start();
    }
}
