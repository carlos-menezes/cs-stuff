/*==============================================================================

    Systems Software Project

    Social Music Server

    Barnaby Keene 2016

==============================================================================*/


package socialmusicserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class ListenServer extends Thread
{

	protected ServerSocket listenSocket;
	protected int socketPort;
	
	private List listeners_ = new ArrayList();

	public ListenServer(int port)
	{
		super();
		try
		{
			listenSocket = new ServerSocket(port);
			socketPort = port;
			System.out.format("Created Main ServerSocket on %d\n", port);
		}
		catch(IOException except)
		{
			except.printStackTrace(System.out);
		}
	}

	public synchronized void addListener(EventListener l)
	{
		listeners_.add(l);
	}

	public synchronized void remListener(EventListener l)
	{
		listeners_.remove(l);
	}

	protected synchronized String listenEvent(HandleServer hsvr, String args[])
	{
		ListenEvent evnt = new ListenEvent(this, hsvr, args);
		Iterator listeners = listeners_.iterator();
		String reply = "";
		String tmp;

		while(listeners.hasNext())
		{
			tmp = ((EventListener)listeners.next()).msgRecv(evnt);
			System.out.format("return is: %s\n", tmp);
			reply += tmp;
		}
		
		return reply;
    }

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				Socket s = listenSocket.accept();
				String[] args = new String[1];

				HandleServer handleServer = new HandleServer(this, s);

				args[0] = "CONN";
				listenEvent(handleServer, args);

				handleServer.run();

			}
		}
		catch(IOException except)
		{
			except.printStackTrace(System.out);
		}
	}
}
