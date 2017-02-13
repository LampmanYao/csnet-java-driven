package com.csnet;

import com.csnet.Pack;
import java.util.Arrays;

public class Header {
	public static final int HEADER_SIZE = 22;
	public static final byte VERSION = 1;
	public static final byte COMPRESS_NON = 0;
	public Pack pack;

	public byte version_;
	public byte compress_;
	public short cmd_;
	public short status_;
	public long ctxid_;
	public int sid_;
	public int len_;

	public Header(short cmd, int contentLen) {
		version_ = VERSION;
		compress_ = COMPRESS_NON;
		cmd_ = cmd;
		status_ = 0;
		ctxid_ = 0;
		sid_ = 0;
		len_ = HEADER_SIZE + contentLen;
		pack = new Pack(HEADER_SIZE);
		pack.writeByte(version_);
		pack.writeByte(compress_);
		pack.writeShort(cmd_);
		pack.writeShort(status_);
		pack.writeLong(ctxid_);
		pack.writeInt(sid_);
		pack.writeInt(len_);
	}

	public Header() {
	
	}
}

