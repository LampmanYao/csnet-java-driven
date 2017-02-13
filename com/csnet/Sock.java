package com.csnet;

import com.csnet.Header;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Thread;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Sock extends Thread {
	private Lock lock;
	private Socket socket;
	private DataOutputStream os;
	private DataInputStream is;

	public Sock(String host, int port) throws IOException {
		lock = new ReentrantLock();
		socket = new Socket(host, port);
		os = new DataOutputStream(socket.getOutputStream());
		is = new DataInputStream(socket.getInputStream());
	}

	public void write(byte[] data) throws IOException {
		lock.lock();
		os.write(data);
		lock.unlock();
	}

	public int read(byte[] buffer) throws IOException {
		int nread = 0;
		lock.lock();
		nread = is.read(buffer);
		lock.unlock();
		return nread;
	}

	public void run() {
		while (true) {
			lock.lock();
			Header header = new Header((short)1, 0);
			byte[] indata = new byte[22];
			try {
				os.write(header.pack.getBytes());	
				int nread = is.read(indata);
				System.out.format("heartbeat: %d\n", nread);
				lock.unlock();
			} catch (Exception e) {
				lock.unlock();
			}

			try {
				Thread.sleep(60000 * 2);
			} catch (Exception e) {

			}
		}
	}
}

