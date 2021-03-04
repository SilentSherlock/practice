package com.bosssoft.hr.train.j2se.basic.example.socket;

/**
 * @description: 服务端启动类
 * @author: lwh
 * @create: 2020/11/5 14:51
 * @version: v1.0
 **/
public class ServerSocketApplication {
    public static void main(String[] args){
        Starter serverSocket=new ServerSocket();
        serverSocket.start();
    }
}
