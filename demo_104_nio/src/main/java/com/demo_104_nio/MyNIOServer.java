package com.demo_104_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MyNIOServer implements Runnable {
    private Selector selector; //多路复用器
    private ByteBuffer readBuf = ByteBuffer.allocate(1024); //缓冲区

    public MyNIOServer(int port) { //构造函数
        try {
            //1、打开多路复用器
            this.selector = Selector.open();
            //2、打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3、服务器通道记得设置为feizuse
            ssc.configureBlocking(false);
            //4、服务器通道绑定地址
            SocketAddress local = new InetSocketAddress(port);
            ssc.bind(local);
            //5、将服务器通道注册到多路复用器那,并且监听阻塞事件
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器已经启动，端口号为:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) { //一直循环，让多路复用器一直监听
            try {
                //1、让多路复用器开始监听(必须的)
                this.selector.select();
                //2、返回多路复用器已经选择的结果集
                Set<SelectionKey> keys = this.selector.selectedKeys();
                //3、遍历结果集，进行处理
                Iterator<SelectionKey> ite = keys.iterator();
                while (ite.hasNext()) {
                    SelectionKey key = ite.next();
                    //获取后直接从容器中移出就可以了
                    ite.remove();
                    if (key.isValid()) {//如果key是有效的
                        if (key.isAcceptable()) { //如果key为阻塞状态（这个和注册的时候绑定的状态对应）
                            this.accept(key);
                        }
                        if (key.isReadable()) {  //如果是可读状态
                            this.read(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void accept(SelectionKey key) {

        try {
            // 先获取服务器通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //调用服务器端的accpet方法获取客户端通道
            SocketChannel sc = ssc.accept();
            //设置为非阻塞
            sc.configureBlocking(false);
            //将客户端通道注册到多路复用器中
            sc.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个方法是读取客户端发送给服务端的数据的。因为客户端通道注册时的注册状态为read
     *
     * @param key
     */
    private void read(SelectionKey key) {

        try {
            //1、先清空缓冲区，防止有上一次的读数据
            this.readBuf.clear();
            //2、获取客户端通道
            SocketChannel sc = (SocketChannel) key.channel();
            //3、看客户端是否有输入
            int count = sc.read(this.readBuf);
            if (count == -1) {//如果没有数据
                sc.close();
                key.cancel();
            }
            //4、如果有数据则进行读取，读取之前记得要进行缓冲区复位。
            this.readBuf.flip();
            //5、根据缓冲区的数据长度创建对应大小的byte数组，接受缓冲区的数据
            byte[] data = new byte[this.readBuf.remaining()];
            //6、将缓冲区数据弄到byte数组里面
            this.readBuf.get(data);
            //7、将byte数组转为字符串打印出来
            String result = new String(data);
            System.out.println("Server接受到client的数据:" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//开启一个线程，保证多路复用器一直在轮询
        new Thread(new MyNIOServer(8765)).start();
    }
}

