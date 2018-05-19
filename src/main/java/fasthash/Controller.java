package fasthash;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import bdb.HashBDBEnv;
import jmx.FileProcessor;

public class Controller {

    private static final int NUMBER_OF_CONSUMERS = 30;
    private static final int NUMBER_OF_PRODUCERS = 1;
    private static final int QUEUE_SIZE = 32767;
    private static BlockingQueue<String> queue;
    private static Collection<Thread> producerThreadCollection, allThreadCollection;

    public static void main(String[] args) {
        producerThreadCollection = new ArrayList<Thread>();
        allThreadCollection = new ArrayList<Thread>();
        queue = new LinkedBlockingDeque<String>(QUEUE_SIZE);
        
        
        HashBDBEnv db = new HashBDBEnv(new File("/tmp/jdb/"));
        
        FileProcessor fileProcessor=null;
        try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("hash.mbeans:type=Controller");
			fileProcessor= new  FileProcessor();
			mbs.registerMBean(fileProcessor, name);
			Thread.sleep(100);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        createAndStartProducers();
        createAndStartConsumers(db,fileProcessor);

        for(Thread t: allThreadCollection){
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        
        System.out.println("Controller finished");
    }

    private static void createAndStartProducers(){
        for(int i = 1; i <= NUMBER_OF_PRODUCERS; i++){
            Producer producer = new Producer(Paths.get("/tmp/xab"), queue);
            Thread producerThread = new Thread(producer,"producer-"+i);
            producerThreadCollection.add(producerThread);
            producerThread.start();
        }
        allThreadCollection.addAll(producerThreadCollection);
    }

    private static void createAndStartConsumers(HashBDBEnv db, FileProcessor fileProcessor){
        for(int i = 0; i < NUMBER_OF_CONSUMERS; i++){
            Thread consumerThread = new Thread(new Consumer(queue,db,fileProcessor), "MD5");
            allThreadCollection.add(consumerThread);
            consumerThread.start();
        }
    }

    public static boolean isProducerAlive(){
        for(Thread t: producerThreadCollection){
            if(t.isAlive())
                return true;
        }
        return false;
    }
}