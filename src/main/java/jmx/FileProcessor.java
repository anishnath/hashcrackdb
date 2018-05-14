package jmx;

import javax.management.NotificationBroadcasterSupport;

public class FileProcessor extends NotificationBroadcasterSupport implements FileProcessorMBean {

	private long x = 0;
	
	@Override
	public String displayWord(String word) {
		return word;
	}

	@Override
	public long getCacheSize() {
		return this.x;
	}

	@Override
	public void setCacheSize(long size) {
		this.x=size;
		
	}

}
