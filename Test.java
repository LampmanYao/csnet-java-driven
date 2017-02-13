import com.csnet.Pack;
import com.csnet.Unpack;
import com.csnet.Header;
import com.csnet.Sock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Hello {
	public Header header_;
	public String msg_;
	public Unpack unpack_;

	public Hello(byte[] bytes, int length) throws Exception {
		unpack_ = new Unpack(bytes, length);
		header_ = new Header();
		header_.version_ = unpack_.readByte();
		header_.compress_ = unpack_.readByte();
		header_.cmd_ = unpack_.readShort();
		header_.status_ = unpack_.readShort();
		header_.ctxid_ = unpack_.readLong();
		header_.sid_ = unpack_.readInt();
		header_.len_ = unpack_.readInt();
		msg_ = unpack_.readString();
	}
	public String getMsg() {
		return msg_;
	}

};

public class Test {
	public static void main(String[] args) throws IOException {
		String str = "hello java";
		Pack pack = new Pack();
		pack.writeString(str);
		byte[] pk = pack.getBytes();
		Header header = new Header((short)4097, pack.getLength());
		byte[] data = new byte[Header.HEADER_SIZE + pack.getLength()];
		System.arraycopy(header.pack.getBytes(), 0, data, 0, header.pack.getLength());
		System.arraycopy(pack.getBytes(), 0, data, header.pack.getLength(), pack.getLength());
		Sock sock = new Sock("10.211.55.3", 9998);
		sock.start();

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			
			}
		sock.write(data);
		byte[] indata = new byte[100];
		int nread = sock.read(indata);
		System.out.format("--read %d bytes\n", nread);
		try  {
			Hello hello = new Hello(indata, nread);
			System.out.println(hello.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return;
	}
}

