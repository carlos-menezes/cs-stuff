/*==============================================================================

    Systems Software Project

    Social Music Server

    Barnaby Keene 2016

==============================================================================*/


package socialmusicserverchat;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;


class HandleServer extends Thread
{

	private final Socket client;

	HandleServer(Socket connection)
	{
		System.out.println("HandleServer constructed");
		this.client = connection;
	}

	@Override
	public void run()
	{
		System.out.println("Running HandleServer");
		String line;
		BufferedReader input = null;
		PrintWriter output = null;

		try
		{
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
		}
		catch(IOException e)
		{
			e.printStackTrace(System.out);
			return;
		}

		while(true)
		{
			
			try
			{
				System.out.println("Waiting...");
				line = input.readLine();
				String reply = onReceive(line);
				
				if(reply == null)
				{
					System.out.println("Replying with NULL\\n");
					reply = "NULL";
				}

				int size = reply.length();
					
				System.out.printf("Replying with reply: %d '%s'\n", size, reply);
				output.write(size);
				output.print(reply);
				output.print("\n");
				
				output.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace(System.out);
				break;
			}
		}
	}
	
	public String onReceive(String s)
	{
		System.out.printf("onReceive '%s'\n", s);
		String a[] = s.split("\\s+");
		
		System.out.printf("length: %d\n", a.length);
		if(a.length == 0)
		{
			// No commands, quit
			return null;
		}
		
		if(a.length == 1)
		{
			// Handle parameterless commands
			return null;
		}
				
	
		System.out.printf("a[0]: %s\n", a[0]);

		switch(a[0])
		{
			case "MSSG":
			{
				// a[1] is user ID
				// a[2] is message
				return "RECEIVED MESSAGE";
			}

			case "JOIN":
			{
				// a[1] is user ID
				return "USER JOINED";
			}

			case "PART":
			{
				// a[1] is user ID
				return "USER DEPARTED";
			}
		}

		return null;
	}

	protected void finalize()
	{
		try
		{
			this.client.close();
		}
		catch(IOException e)
		{
			e.printStackTrace(System.out);
			System.exit(-1);
		}
	}
}