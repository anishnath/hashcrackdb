package cracker.cracker;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.concurrent.BlockingQueue;

public class SHAConsumer implements Runnable {

	protected BlockingQueue queue = null;
	protected BlockingQueue fileWriterQ=null;
	protected FileWriter fileWriterQueue = null;
	public volatile static boolean isRunning = true;

	public SHAConsumer(BlockingQueue queue, FileWriter fileWriterQueue) {
		this.queue = queue;
		this.fileWriterQueue = fileWriterQueue;
		this.fileWriterQ=fileWriterQueue.getQueue();
	}

	public void run() {
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			while (!queue.isEmpty()) {
				String s = (String) queue.take();
				

				crypt.reset();
				crypt.update(s.getBytes("UTF-8"));
				String hexValue = byteToHex(crypt.digest());
				String hexString = s + "," + hexValue;
				
				
				
				fileWriterQ.put(hexString);
				hexString = null;

				if ("495980d1ebd25d66d0ee1bf33eccdb0d".equals(s)) {
					fileWriterQ.put("495980d1ebd25d66d0ee1bf33eccdb0d");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}