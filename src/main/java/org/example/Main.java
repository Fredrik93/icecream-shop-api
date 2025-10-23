package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static int count = 0; // shared resource

    public void increment() {
        count++; // not thread-safe
    }

    public static void main(String[] args) throws IOException
    {
        HttpServer server  = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/hi", new Controller());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8080/hi");
        //set up two threads counting to 1000 each
        // show sum ( 2000 )
        // display the race condition ( it will not always be 2000 if i do threading wrong)
        // save results to cassandra db
        // send data to promethesu
        // visualise in grafana
        Main m  = new Main();
        // create a thread
        Runnable counter = () -> {
            for(int i = 0; i <= 1000; i++)
            {
              m.increment();
            }
        };

        // run method. wont work with "run", you need "start".

        Thread t = new Thread(counter);
        Thread t2 = new Thread(counter);
        // this is a race condition issue, it prints 1675, 1954, 2001, etc. it should always print 2000
        // but since the two threads might at the same time reach i = 10 and increment it to 11 simultaneously one inkrementation gets lost.
        t.start();
        t2.start();

        try
        {
            t.join();
            t2.join();
        }
        catch(InterruptedException e)
        {
        }
        System.out.println("Count: " + count);
    }

}