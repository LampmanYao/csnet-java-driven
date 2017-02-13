package com.csnet;

import java.lang.System;
import java.util.Arrays;

public class Pack {
	private static final int MAX_LENGTH = 32 * 1024;
	private int offset_;
	private byte[] bytes_;

	public Pack() {
		offset_ = 0;
		bytes_ = new byte[MAX_LENGTH];
	}

	public Pack(int size) {
		offset_ = 0;
		bytes_ = new byte[size];
	}

	public int writeLong(long value) {
		if (offset_ + 8 > MAX_LENGTH) {
			return -1;
		}
		bytes_[offset_ + 7] = (byte)((value >>> 56) & 0xFF);
		bytes_[offset_ + 6] = (byte)((value >>> 48) & 0xFF);
		bytes_[offset_ + 5] = (byte)((value >>> 40) & 0xFF);
		bytes_[offset_ + 4] = (byte)((value >>> 32) & 0xFF);
		bytes_[offset_ + 3] = (byte)((value >>> 24) & 0xFF);
		bytes_[offset_ + 2] = (byte)((value >>> 16) & 0xFF);
		bytes_[offset_ + 1] = (byte)((value >>> 8) & 0xFF);
		bytes_[offset_] = (byte)((value) & 0xFF);
		offset_ += 8;
		return 0;
	}

	public int writeInt(int value) {
		if (offset_ + 4 > MAX_LENGTH) {
			return -1;
		}
		bytes_[offset_ + 3] = (byte)((value >>> 24) & 0xFF);
		bytes_[offset_ + 2] = (byte)((value >>> 16) & 0xFF);
		bytes_[offset_ + 1] = (byte)((value >>> 8) & 0xFF);
		bytes_[offset_] = (byte)((value) & 0xFF);
		offset_ += 4;
		return 0;
	}

	public int writeShort(int value) {
		if (offset_ + 4 > MAX_LENGTH) {
			return -1;
		}
		bytes_[offset_ + 1] = (byte)((value >>> 8) & 0xFF);
		bytes_[offset_] = (byte)((value) & 0xFF);
		offset_ += 2;
		return 0;
	}

	public int writeByte(byte value) {
		if (offset_ + 1 > MAX_LENGTH) {
			return -1;
		}
		bytes_[offset_] = (byte)(value & 0xFF);
		offset_ += 1;
		return 0;
	}

	public int writeString(String str) {
		int strLen = str.getBytes().length;
		if (offset_ + 2 + strLen > MAX_LENGTH) {
			return -1;
		}
		writeShort(strLen);
		System.arraycopy(str.getBytes(), 0, bytes_, offset_, strLen);
		offset_ += strLen;
		return 0;
	}

	public int getLength() {
		return offset_;
	}

	public byte[] getBytes() {
		return bytes_;
	}
}

