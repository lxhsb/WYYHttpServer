import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
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
	public static int PORT = 8000;
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
		ByteBuffer buffer = ByteBuffer.allocate(1024<<3);
		byte[] bytes = null;
		int size = 0;
		try
		{
			size = socketChannel.read(buffer);
			if(size == -1 )
			{
				logger(socketChannel.hashCode()+" close at 44");
				socketChannel.close();
				socketChannel.keyFor(selector).cancel();
				throw new IOException("maybe shut down");
			}
		}
		catch (IOException e )
		{
			logger(socketChannel.hashCode()+" close at 51");
			socketChannel.close();
			socketChannel.keyFor(selector).cancel();
			throw  e ;
		}
		bytes = new byte[size];
		System.arraycopy(buffer.array(),0,bytes,0,size);


		return new String(bytes);
	}
	private synchronized void logger(String s )
	{
		System.out.println(s);
	}

	public void start()
	{
		int j  = 1 ;
		while (true)
		{
			logger("this is "+(j++)+" time search ");
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
					if (key.isAcceptable())
					{

						SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						logger("new connection from "+socketChannel.socket().getRemoteSocketAddress()+" "+socketChannel.hashCode());
					}
					else if (key.isReadable())
					{
						try
						{

							SocketChannel channel = (SocketChannel) key.channel();
							logger(channel.hashCode()+" is readable");
							String receive = receive(channel);
							//System.out.println(receive);
							logger(receive);
							key.interestOps(SelectionKey.OP_WRITE);
							//System.out.println(key.interestOps());
						}
						catch (Exception e )
						{
							logger("channel "+ key.channel().hashCode()+"close at 102");
							e.printStackTrace();
							key.channel().close();
							key.cancel();

						}

					}
					else if (key.isWritable())
					{
						try
						{

							SocketChannel channel = (SocketChannel) key.channel();
							logger(channel.hashCode()+" is writeable");
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
							//System.out.println(sb.toString());
							//System.out.println(key.interestOps());
						}
						catch (Exception e )
						{
							logger(key.channel().hashCode()+"close at 141");
							e.printStackTrace();
							key.channel().close();
							key.cancel();
						}

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