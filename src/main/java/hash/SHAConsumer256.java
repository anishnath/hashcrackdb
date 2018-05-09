package hash;

import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;



public class SHAConsumer256 implements Runnable {

	protected BlockingQueue queue = null;
	protected Database db;

	public SHAConsumer256(BlockingQueue queue, Database md5database) {
		this.queue = queue;
		this.db = md5database;
	}

	@Override
	public void run() {

		try {
			MessageDigest crypt = MessageDigest.getInstance("sha-256");
			while (true) {
				String s = (String) queue.take();
				crypt.reset();
				crypt.update(s.getBytes("UTF-8"));
				String hexValue = cracker.cracker.SHAConsumer.byteToHex(crypt.digest());
				
				
				//System.out.println("SHA Consumer 256 -- " + hexValue.toUpperCase());
				DatabaseEntry theKey = new DatabaseEntry(hexValue.toUpperCase().getBytes("UTF-8"));
				DatabaseEntry theData = new DatabaseEntry(s.getBytes("UTF-8"));
				db.put(null, theKey, theData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
