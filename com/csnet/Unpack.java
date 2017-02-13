package com.csnet;

import java.lang.System;
import java.util.Arrays;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Unpack {
	private int length_;
	private int offset_;
	private byte[] bytes_;

	public Unpack(byte[] bytes, int length) {
		length_ = length;
		offset_ = 0;
		bytes_ = Arrays.copyOf(bytes, length);
	}

	public long readLong() {
		long ret = ((bytes_[offset_+ 7] & 0xFF) << 56) |
			((bytes_[offset_ + 6] & 0xFF) << 48) |
			((bytes_[offset_ + 5] & 0xFF) << 40) |
			((bytes_[offset_ + 4] & 0xFF) << 32) |
			((bytes_[offset_ + 3] & 0xFF) << 24) |
			((bytes_[offset_ + 2] & 0xFF) << 16) |
			((bytes_[offset_ + 1] & 0xFF) << 8)  |
			((bytes_[offset_] & 0xFF));
		offset_ += 8;
		return ret;
	}

	public int readInt() {
		int ret = ((bytes_[offset_ + 3] & 0xFF) << 24) |
			((bytes_[offset_ + 2] & 0xFF) << 16) |
			((bytes_[offset_ + 1] & 0xFF) << 8)  |
			((bytes_[offset_] & 0xFF));
		offset_ += 4;
		return ret;
	}

	public short readShort() {
		short ret = (short)(((bytes_[offset_ + 1] & 0xFF) << 8) | ((bytes_[offset_] & 0xFF)));
		offset_ += 2;
		return ret;
	}

	public byte readByte() {
		byte ret = (byte)((bytes_[offset_] & 0xFF));
		offset_ += 1;
		return ret;
	}

	public String readString() throws Exception {
		short strLen = readShort();
		byte[] bytes = Arrays.copyOfRange(bytes_, offset_, offset_ + strLen);
		offset_ += strLen;
		return new String(bytes, "UTF-8");
	}
}

