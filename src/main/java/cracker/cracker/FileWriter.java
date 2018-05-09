package cracker.cracker;

import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

public class FileWriter implements Runnable {

	public volatile static boolean isRunning = true;
	protected BlockingQueue queue = null;
	protected String fileName = null;
	public static long x = 0;

	private BufferedWriter bw = null;
	private java.io.FileWriter fw = null;

	public FileWriter(BlockingQueue queue, String fileName) {
		this.queue = queue;
		this.fileName = fileName;
	}
	
	

	public BlockingQueue getQueue() {
		return this.queue;
	}



	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}



	@Override
	public void run() {
		
		try {
			fw = new java.io.FileWriter(this.fileName);
			bw = new BufferedWriter(fw);
			while (true) {

				//System.out.println("queue.isEmpty() ++ " + queue.isEmpty() + " isRunning  " + isRunning);
//				if(queue.isEmpty() &&  !isRunning)
//					break;
					
				String s = (String) queue.take();
				
				//System.out.println("File Writer  --" + s);
				

				if ("495980d1ebd25d66d0ee1bf33eccdb0d".equals(s)) {
					System.out.println("Never Ture===");
					break;
				}
				
				bw.write(s);
				bw.write("\n");
				bw.flush();
				x++;
				//System.out.println(s);

			}
		} catch (Exception e) {
			e.printStackTrace();
			//isRunning=false;
			System.out.println("Able to arite This Much File " +x);
			
		} finally {
			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		}

	}

}
