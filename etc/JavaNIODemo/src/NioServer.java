import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wyy on 17-2-26.
 */
public class NioServer
{
	public static int PORT = 1208;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;

	public NioServer() throws IOException
	{
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(PORT));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
	}

	private String receive(SocketChannel socketChannel) throws Exception
	{
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] bytes = null;
		int size = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((size = socketChannel.read(buffer)) > 0)
		{
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
		}
		bytes = baos.toByteArray();
		System.out.println(socketChannel.hashCode()+"receive done");

		return new String(bytes);
	}

	public void start()
	{
		while (true)
		{
			try
			{
				selector.select();
				Iterator<SelectionKey> selectionKeyIterator = selector
						.selectedKeys().iterator();
				while (selectionKeyIterator.hasNext())
				{
					SelectionKey key = selectionKeyIterator.next();
					selectionKeyIterator.remove();

					if (key.isValid() == false)
					{
						continue;
					}
					System.out.println("key: "+ key.hashCode());
					if (key.isAcceptable())
					{
						System.out.println("key: "+ key.hashCode()+" isAcceptable ");
						SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
					}
					else if (key.isReadable())
					{
						System.out.println("key: "+ key.hashCode()+" isReadable ");
						SocketChannel channel = (SocketChannel) key.channel();
						String receive = receive(channel);
						System.out.println(receive);
						key.interestOps(SelectionKey.OP_WRITE);
						System.out.println(key.interestOps());
					}
					else if (key.isWritable())
					{
						System.out.println("key: "+ key.hashCode()+" isWriteable ");
						SocketChannel channel = (SocketChannel) key.channel();
						StringBuilder sb = new StringBuilder();
						sb.append("HTTP/1.1 200 OK").append("\r\n");
						String hello = "hello world..." + channel.hashCode();
						sb.append("Content-Length:" + hello.length())
								.append("\r\n");
						sb.append("Content-Type:text/html").append("\r\n");
						sb.append("Connection:keep-alive").append("\r\n");
						sb.append("\r\n");
						sb.append(hello);
						ByteBuffer buffer = ByteBuffer
								.wrap(sb.toString().getBytes());
							channel.write(buffer);
						//System.out.print(sb.toString());
						key.interestOps(SelectionKey.OP_READ);
						System.out.println(sb.toString());
						System.out.println(key.interestOps());
					}
					else
					{
						System.out.println("key: "+ key.hashCode()+" mother fucker ");
					}
				}
			}
			catch (Exception e)
			{


				e.printStackTrace();
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception ee)
				{

				}

			}

		}

	}

}