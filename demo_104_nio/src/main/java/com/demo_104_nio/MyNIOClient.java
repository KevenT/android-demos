package com.demo_104_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MyNIOClient {
    public static void main(String[] args){

        try {
            //1、创建以一个客户端通道
            SocketChannel sc = SocketChannel.open();
            //这里是服务器端的IP地址和端口号
            InetSocketAddress add = new InetSocketAddress("127.0.0.1", 8765);
            //客户端通道连接服务器端通道
            sc.connect(add);
            //定义缓冲区，拿来接收用户的输入
            ByteBuffer buf = ByteBuffer.allocate(1204);
            while(true){  //死循环，用户可以无限输入
                //定义一个字节数组，然后使用系统的输入功能
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                //将数据放入缓冲区
                buf.put(bytes);
                //记得进行复位操作
                buf.flip();
                //写出数据
                sc.write(buf);
                //清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
