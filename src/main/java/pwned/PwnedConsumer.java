package pwned;

import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

import mysql.MySQLDump;

public class PwnedConsumer implements Runnable {

	private static MySQLDump mySQLDump = new MySQLDump();
	
	private static boolean isRunning = true;
	
	public static long y = 0;

	protected BlockingQueue queue = null;

	public PwnedConsumer(BlockingQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String s = (String) queue.take();
				
				StringTokenizer stringTokenizer =  new StringTokenizer(s, ":");
				String HASH_VALUE="";
				String repeated="";
				int REPEATED = 0 ;
				 while (stringTokenizer.hasMoreTokens())
				 {
					 HASH_VALUE= stringTokenizer.nextToken();
					 repeated =  stringTokenizer.nextToken();
					 
					 //System.out.println("repeated " + repeated);
				 }
				
				
				
				String inserttStatement = "insert into bruteforce(HASH_VALUE,HASH_SUFFIX,HASH_TYPE,REPEATED) values ('"+HASH_VALUE+ "','"+  HASH_VALUE.substring(0,5)  +"',1,"+ repeated + ")";
				
//				 System.out.println("HASH_VALUE " + HASH_VALUE);
//				 System.out.println("REPEATED " + REPEATED);
	//			 System.out.println(inserttStatement);
				 
				 
				 mySQLDump.deleteorInsert(inserttStatement);
				
				 y++;
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
