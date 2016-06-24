package com.gome.im.platform.utils;

import java.util.Arrays;

public class FileFormat {

	/**
	 * File extensions.
	 */
	public static final String[] FILE_EXTS = { "jpg", "png", "gif" };
	/**
	 * Magic bytes in a file with above extension.
	 */
	private static final byte[][] FILE_MAGS = new byte[][] {
			new byte[] { (byte) 0xFF, (byte) 0xD8 }, // JPG

			new byte[] { (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47 }, // PNG
			new byte[] { (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38 } // GIF
	};

	/**
	 * Get file format by contents.
	 * 
	 * @param contents
	 *            file contents
	 * @return file format, null if unsupported.
	 */
	public static String getFileFormat(byte[] contents) {
		for (int i = 0; i < FILE_MAGS.length; i++) {
			byte[] mag = FILE_MAGS[i];
			if (contents.length >= mag.length) {
				if (Arrays.equals(Arrays.copyOf(contents, mag.length), mag)) {
					return FILE_EXTS[i];
				}
			}
		}
		return null;
	}

}
