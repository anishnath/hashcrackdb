package hash;

import java.io.File;
import java.security.Security;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.sleepycat.je.Database;

import bdb.HashBDBEnv;

public class BDBHashDumper {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void main(String[] argv) {
		if (argv.length < 2) {
			usage();
			return;
		}

		File envHomeDirectory = new File(argv[0]);
		HashBDBEnv example = new HashBDBEnv(envHomeDirectory);

		BlockingQueue sha1 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha224 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha256 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha384 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha512 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue tiger = new ArrayBlockingQueue(32768);
		;
		BlockingQueue whirpool = new ArrayBlockingQueue(32768);
		;
		BlockingQueue md5 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue md2 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue ripemd160 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue ripemd256 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue ripemd320 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue blake2b160 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue blake2b256 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue blake2b384 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue blake2b512 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sm3 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue gost3411 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue gost34112012256 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha3224 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue sha3256 = new ArrayBlockingQueue(32768);
		;
		BlockingQueue ntlm = new ArrayBlockingQueue(32768);
		;

		HashProducer hashProducer = new HashProducer(sha1, sha224, sha256, sha384, sha512, tiger, whirpool, md5, md2,
				 ripemd160, ripemd256,ripemd320, blake2b160, blake2b256, blake2b384, blake2b512, sm3, gost3411,
				gost34112012256, sha3224, sha3256, ntlm, argv[1]);

		MD5Consumer md5Consumer = new MD5Consumer(md5, example.getMd5());
		MD2Consumer md2Consumer = new MD2Consumer(md2, example.getMd2());
		SHAConsumer shaConsumer = new SHAConsumer(sha1, example.getSha1());
		SHAConsumer224 shaConsumer224 = new SHAConsumer224(sha224, example.getSha224());
		SHAConsumer256 shaConsumer256 = new SHAConsumer256(sha256, example.getSha256());
		SHAConsumer384 shaConsumer384 = new SHAConsumer384(sha384, example.getSha384());
		SHAConsumer512 shaConsumer512 = new SHAConsumer512(sha512, example.getSha512());
		
		
		Ripemd160Consumer ripemd160Consumer = new Ripemd160Consumer(ripemd160, example.getRipemd160());
		Ripemd256Consumer ripemd256Consumer = new Ripemd256Consumer(ripemd256, example.getRipemd256());
		Ripemd320Consumer ripemd320Consumer = new Ripemd320Consumer(ripemd320, example.getRipemd320());
		
		SM3Consumer sm3Consumer = new SM3Consumer(sm3, example.getSm3());
		
		TigerConsumer tigerConsumer = new TigerConsumer(tiger, example.getTiger());
		WhirlpoolConsumer whirlpoolConsumer = new WhirlpoolConsumer(whirpool, example.getWhirpool());
		
		Blake2b160Consumer blake2b160Consumer = new Blake2b160Consumer(blake2b160, example.getBlake2b160());
		Blake2b256Consumer blake2b256Consumer = new Blake2b256Consumer(blake2b256, example.getBlake2b256());
		Blake2b384Consumer blake2bConsumer = new Blake2b384Consumer(blake2b384, example.getBlake2b384());
		Blake2b512Consumer blake2b512Consumer = new Blake2b512Consumer(blake2b512, example.getBlake2b512());
		
		NTLMConsumer ntlmConsumer = new NTLMConsumer(ntlm, example.getNtlm());
		
		new Thread(hashProducer).start();
		new Thread(md5Consumer).start();
		new Thread(md2Consumer).start();
		new Thread(shaConsumer).start();
		new Thread(shaConsumer224).start();
		new Thread(shaConsumer256).start();
		new Thread(shaConsumer384).start();
		new Thread(shaConsumer512).start();
		
		
		new Thread(ripemd160Consumer).start();
		new Thread(ripemd256Consumer).start();
		new Thread(ripemd320Consumer).start();
		
		new Thread(sm3Consumer).start();
		new Thread(tigerConsumer).start();
		
		
		new Thread(whirlpoolConsumer).start();
		
		new Thread(blake2b160Consumer).start();
		new Thread(blake2b256Consumer).start();
		new Thread(blake2bConsumer).start();
		new Thread(blake2b512Consumer).start();
		
		new Thread(ntlmConsumer).start();
		
		
		

	}

	public static void usage() {
		System.out.println("usage: java " + "hash.BDBHashDumper " + "<dbEnvHomeDirectory> " + "<inputFile> ");
		System.exit(1);
	}

}
