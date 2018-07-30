package com.pragone.jphash;

import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.image.radial.RadialHashAlgorithm;
import com.sun.jmx.snmp.SnmpUnknownSubSystemException;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * User: pragone Date: 27/04/2014 Time: 5:34 PM
 */
public class jpHash {
	public static RadialHash getImageRadialHash(String path) throws IOException {
		return RadialHashAlgorithm.getHash(path);
	}

	public static RadialHash getImageRadialHash(File file) throws IOException {
		return RadialHashAlgorithm.getHash(file);
	}

	public static RadialHash getImageRadialHash(InputStream is) throws IOException {
		return RadialHashAlgorithm.getHash(is);
	}

	public static RadialHash getImageRadialHash(BufferedImage bi) throws IOException {
		return RadialHashAlgorithm.getHash(bi);
	}

	public static double getSimilarity(RadialHash hash1, RadialHash hash2) {
		return RadialHashAlgorithm.getSimilarity(hash1, hash2);
	}

	// 16進位轉2進位
	public static String hexToBinary(String hexnumber) {
		BigInteger temp = new BigInteger(hexnumber, 16);
		return temp.toString(2);

	}

	// hammingDiatance
	public static int hammingDistance(BigInteger i, BigInteger i2) {
		return i.xor(i2).bitCount();
	}

	public static void main(String[] args) throws IOException {

		// Figure1
		RadialHash hash1 = jpHash.getImageRadialHash("airpods/airpods1.jpg");
		String Hash1 = hash1.toString();

		// 原圖hash
		// System.out.println("hash: " + hash1);
		// System.out.println("fingerPrint: " + hexToBinary(Hash1));
		// System.out.println("---------------------------------");

		// FigureN
		File file = new File("D:\\workspace2\\jphash\\airpods");
		String[] filenames;

		if (file.isDirectory()) {

			filenames = file.list();
			ArrayList dataList = new ArrayList();

			for (int i = 0; i < filenames.length; i++)

			{

				RadialHash hash2 = jpHash.getImageRadialHash(file + "/" + filenames[i]);
				String Hash2 = hash2.toString();// 算出的Fingerprint

				// System.out.println(filenames[i]);
				// System.out.println("hash: " + hash2);
				// System.out.println("fingerPrint: " + hexToBinary(Hash2));

				BigInteger i1 = new BigInteger(Hash1, 16);
				BigInteger i2 = new BigInteger(Hash2, 16);
				int distance = hammingDistance(i1, i2);

				// System.out.println("Hamming Distance: " + distance);
				// System.out.println("Similarity: " + jpHash.getSimilarity(hash1, hash2));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'"); // 格式化時間
				Date current = new Date();
				// System.out.println(sdf.format(current));

				JSONObject obj = new JSONObject();

				obj.put("G_NO", filenames[i]);
				obj.put("G_FINGERPRINT", hexToBinary(Hash2));
				obj.put("_SOURCE_TIME", sdf.format(current));
				dataList.add(i, obj);

				// System.out.println(obj);
				// System.out.println("--------------------------------");

			}

			System.out.println(dataList);

		}

	}

}
