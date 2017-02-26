import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
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

	}



}
