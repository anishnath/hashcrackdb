package hash;

import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;

import jcifs.smb.NtlmPasswordAuthentication;



public class NTLMConsumer implements Runnable {

	protected BlockingQueue queue = null;
	protected Database db;

	public NTLMConsumer(BlockingQueue queue, Database md5database) {
		this.queue = queue;
		this.db = md5database;
	}

	@Override
	public void run() {

		try {
			
			while (true) {
				String s = (String) queue.take();
				byte[] hash = NtlmPasswordAuthentication.nTOWFv1(s);
				
				String hexValue = cracker.cracker.SHAConsumer.byteToHex(hash);
				
				//System.out.println("NTLM Consumer -- " + hexValue.toUpperCase());
				DatabaseEntry theKey = new DatabaseEntry(hexValue.toUpperCase().getBytes("UTF-8"));
				DatabaseEntry theData = new DatabaseEntry(s.getBytes("UTF-8"));
				db.put(null, theKey, theData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
