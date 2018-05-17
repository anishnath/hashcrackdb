package fasthash;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sleepycat.je.DatabaseEntry;

import bdb.HashBDBEnv;
import cracker.cracker.SHAConsumer;
import jmx.FileProcessor;

public class Consumer implements Runnable{
	private HashBDBEnv db = new HashBDBEnv(new File("/tmp/jdb"));
    private BlockingQueue<String> queue;
    private FileProcessor fileProcessor=null;
    
    public static long x = 0;
    public Consumer(BlockingQueue<String> q){
        queue = q;
    }
    
    public Consumer(BlockingQueue<String> q,HashBDBEnv dbenv,FileProcessor fileProcessor){
        queue = q;
        this.db=dbenv;
        this.fileProcessor=fileProcessor;
    }
    


    public void run(){
    	
		
    	long startTime = System.currentTimeMillis();
        while(true){
            String line = queue.poll();

            if(line == null && !Controller.isProducerAlive())
            {
            	long stopTime = System.currentTimeMillis();
    			long elapsedTime = stopTime - startTime;

    			System.out.println("Total time " + elapsedTime);

                return;
            }

            if(line != null){
            	x++;
                //System.out.println(Thread.currentThread().getName()+" processing line: "+line);
                //Do something with the line here like see if it contains a string
            	
            	fileProcessor.setCacheSize(x);
				fileProcessor.displayWord(line);
            	
            	try {
        			MessageDigest crypt = MessageDigest.getInstance(Thread.currentThread().getName());
        			while (true) {
        				String s = (String) queue.take();
        				crypt.reset();
        				crypt.update(s.getBytes("UTF-8"));
        				String hexValue = SHAConsumer.byteToHex(crypt.digest());
        				//System.out.println("MD5 Consumer -- " + hexValue);
        				DatabaseEntry theKey = new DatabaseEntry(hexValue.toUpperCase().getBytes("UTF-8"));
        				DatabaseEntry theData = new DatabaseEntry(s.getBytes("UTF-8"));
        				db.getMd5().put(null, theKey, theData);
        			}
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            }

        }
    }
}
