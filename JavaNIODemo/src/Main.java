public class Main {

    public static void main(String[] args)
    {
        try
        {
            NioServer nioServer = new NioServer();
            nioServer.start();
        }
        catch (Exception e )
        {
            e.printStackTrace();
        }




    }
}
