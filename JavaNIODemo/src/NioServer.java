import java.io.IOException;
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

	public void start()
	{
		System.out.println("1");
		Map<SocketChannel,BlockingQueue<ByteBuffer>>mp = new ConcurrentHashMap<>();
		while(true)
		{
			//System.out.println("2");
			try
			{
				selector.select();
				Iterator<SelectionKey>selectionKeyIterator = selector.selectedKeys().iterator();
				while(selectionKeyIterator.hasNext())
				{
					SelectionKey key = selectionKeyIterator.next();
					if(key.isValid()==false)
					{
						continue;
					}
					if(key.isAcceptable())
					{
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector,SelectionKey.OP_READ);
					}
					else if(key.isReadable())
					{
						SocketChannel socketChannel = (SocketChannel) key.channel();
						BlockingQueue now = mp.get(socketChannel);
						if(now == null)
						{
							now = new LinkedBlockingQueue();
							mp.put(socketChannel,now);
						}
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int num = socketChannel.read(buffer);
						System.out.println(buffer.array());
						if(num == -1 )
						{
							socketChannel.close();
							key.cancel();
							continue;
						}
						else
						{
							now.put(buffer);
							key.interestOps(SelectionKey.OP_WRITE|SelectionKey.OP_READ);
						}


					}
					else if(key.isWritable())
					{
						SocketChannel socketChannel = (SocketChannel)key.channel();
						BlockingQueue now = mp.get(socketChannel);
						if(now == null)
						{
							System.out.println("???");
							//continue;
						}
						else
						{
							while(!now.isEmpty())
							{
								ByteBuffer b = (ByteBuffer) now.remove();
								System.out.println(b.toString());
								socketChannel.write(b);
							}
						}
						key.interestOps(SelectionKey.OP_READ);

					}
				}
			}
			catch (Exception e )
			{
				e.printStackTrace();

			}


		}

	}




}
