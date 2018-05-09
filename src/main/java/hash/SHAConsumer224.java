package hash;

import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;



public class SHAConsumer224 implements Runnable {

	protected BlockingQueue queue = null;
	protected Database db;

	public SHAConsumer224(BlockingQueue queue, Database md5database) {
		this.queue = queue;
		this.db = md5database;
	}

	@Override
	public void run() {

		try {
			MessageDigest crypt = MessageDigest.getInstance("sha-224");
			while (true) {
				String s = (String) queue.take();
				crypt.reset();
				crypt.update(s.getBytes("UTF-8"));
				String hexValue = cracker.cracker.SHAConsumer.byteToHex(crypt.digest());
				
				
				//System.out.println("SHA Consumer -- " + hexValue.toUpperCase());
				DatabaseEntry theKey = new DatabaseEntry(hexValue.toUpperCase().getBytes("UTF-8"));
				DatabaseEntry theData = new DatabaseEntry(s.getBytes("UTF-8"));
				db.put(null, theKey, theData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
