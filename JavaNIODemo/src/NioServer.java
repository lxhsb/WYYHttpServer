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
					if (key.isValid() == false)
					{
						continue;
					}
					if (key.isAcceptable())
					{
						SocketChannel socketChannel = serverSocketChannel
								.accept();
						if (socketChannel == null)
							continue;
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
					}
					else if (key.isReadable())
					{
						SocketChannel socketChannel = (SocketChannel) key
								.channel();
						SocketChannel channel = (SocketChannel) key.channel();
						channel.configureBlocking(false);
						String receive = receive(channel);
						BufferedReader b = new BufferedReader(
								new StringReader(receive));
						String s = b.readLine();
						while (s != null)
						{
							System.out.println(s);
							s = b.readLine();
						}
						b.close();
						key.interestOps(SelectionKey.OP_WRITE);
					}
					else if (key.isWritable())
					{
						SocketChannel channel = (SocketChannel) key.channel();
						StringBuilder sb = new StringBuilder();
						sb.append("HTTP/1.1 200 OK").append("\r\n");
						String hello = "hello world..." + channel.hashCode();
						sb.append("Content-Length:" + hello.length())
								.append("\r\n");
						sb.append("Content-Type:text/html").append("\r\n");
						sb.append("\r\n");
						sb.append(hello);
						ByteBuffer buffer = ByteBuffer
								.wrap(sb.toString().getBytes());
						channel.write(buffer);
						System.out.print(sb.toString());
						key.interestOps(SelectionKey.OP_READ);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();

			}

		}

	}

}
