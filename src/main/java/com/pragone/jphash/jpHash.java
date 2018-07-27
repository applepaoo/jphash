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

	public static void main(String[] args) throws IOException {

		// Figure1
		RadialHash hash1 = jpHash.getImageRadialHash("tmp/earth1.jpg");
		String Hash1 = hash1.toString();

		System.out.println("jpHash_Goods1: " + hash1);
		System.out.println("jpHash_Goods1_fingerPrint: " + hexToBinary(Hash1));

		File file = new File("D:\\workspace2\\jphash\\tmp");
		String[] filenames;

		if (file.isDirectory()) {

			filenames = file.list();

			for (int i = 0; i < filenames.length; i++)

			{

				RadialHash hash3 = jpHash.getImageRadialHash("tmp/" + filenames[i]);
				String Hash3 = hash3.toString();
				System.out.println(filenames[i]);
				System.out.println("jpHash_Goods2: " + hash3);
				System.out.println("jpHash_Goods2_fingerPrint: " + hexToBinary(Hash3));

				BigInteger i1 = new BigInteger(Hash1, 16);
				BigInteger i2 = new BigInteger(Hash3, 16);
				int distance = hammingDistance(i1, i2);
				System.out.println("jpHash_Similarity: " + jpHash.getSimilarity(hash1, hash3));
				System.out.println("Hamming Distance: " + distance);

			}

		}

	}

	public static String hexToBinary(String hexnumber) {
		BigInteger temp = new BigInteger(hexnumber, 16);
		return temp.toString(2);

	}

	// hammingDiatance
	public static int hammingDistance(BigInteger i, BigInteger i2) {
		return i.xor(i2).bitCount();
	}
}
