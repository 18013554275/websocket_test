import javax.jws.WebService;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    static String strFormat = "zywlPAL,TP,74152,00,${x},${y},1.51,54,2011-07-26 14:55:37,35,0,23,1";
    static int x0 = 400;
    static int y0 = 400;
    public static void main(String[] args) throws IOException {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Random ran = new Random();
                int dx = ran.nextInt(59)-30;
                int dy = ran.nextInt(59)-30;
                x0 += dx;
                y0 += dy;
                String str = strFormat.replace("${x}", x0+"");
                str = str.replace("${y}", y0+"");
                try {
                    System.out.println(str);
                    sendData(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

    }

    public static void sendData(String content) throws IOException {
        /**
         * 准备发送端
         * DatagramSocket()
         * 构造一个数据报套接字绑定到本地主机机器上的任何可用的端口。
         */
        DatagramSocket ds = new DatagramSocket();

        /**
         * 准备数据包
         *1、 DatagramPacket(byte[] buf, int length)
         * 构造一个 DatagramPacket length接收数据包的长度
         *2、 String的getBytes()
         * 方法是得到一个操作系统默认的编码格式的字节数组
         *3、 setSocketAddress()
         * 设置SocketAddress(通常是IP地址+端口号)都的远程主机发送数据报。
         * 4、InetSocketAddress(InetAddress addr, int port)
         * 创建一个套接字地址的IP地址和端口号。
         */
        byte[] ch = content.getBytes();
        DatagramPacket dp = new DatagramPacket(ch, ch.length);
        dp.setSocketAddress(new InetSocketAddress("127.0.0.1", 8088));
        ds.send(dp);
        // 关闭套接字
        ds.close();

    }
}
