package com.pragone.jphash;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.Logger;
import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.image.radial.RadialHashAlgorithm;
import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;
import com.sun.org.apache.xpath.internal.operations.Equals;
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;

import sun.print.resources.serviceui;
import tw.com.ruten.util.ImageUtility;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.hadoop.*;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.spi.LoggerFactory;

public class fingerPrintPro {

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

	public static void main(String[] args) throws IOException, URISyntaxException, ParseException {

		try {
			File file = new File("test.txt"); // 讀取測試檔
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();

			String[] arrayBF = stringBuffer.toString().split("}");// 切割字串
			JSONParser parser = new JSONParser();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
			Date current = new Date();
			FileWriter fw = new FileWriter("output");

			for (int i = 0; i < arrayBF.length - 1; i++) { // 拚imagePath

				Object obj = parser.parse(arrayBF[i] + "}");
				JSONObject jsonObject = (JSONObject) obj;
				JSONObject jsonObject1 = new JSONObject();

				if (String.valueOf(jsonObject.get("G_IMG")).contains("(null)")) {// 過濾G_IMG為(null)

				} else {

					String imagePath = ImageUtility.getImgPath(String.valueOf(jsonObject.get("G_NO")),
							String.valueOf(jsonObject.get("USER_NICK")), String.valueOf(jsonObject.get("G_STORAGE")))
							+ String.valueOf(jsonObject.get("G_IMG")).substring(0,
									jsonObject.get("G_IMG").toString().length() - 4)
							+ "_s.jpg";
					File file2 = new File("/mnt/" + imagePath);

					if (imagePath.contains("null") || imagePath.contains(",") || imagePath.contains("gif")

							|| imagePath.contains("png") || imagePath.contains("bmp") || imagePath.contains("jpeg")) {

					} else if (file2.exists() && !file2.isDirectory()) {

						System.out.println(String.valueOf(file2));
						RadialHash hash2 = jpHash.getImageRadialHash("/mnt/" + imagePath);
						String Hash2 = String.valueOf(hash2);
						jsonObject1.put("G_NO", String.valueOf(jsonObject.get("G_NO")));
						jsonObject1.put("_SOUTCE_TIME", sdf.format(current));
						jsonObject1.put("HASH_IMG", String.valueOf(jsonObject.get("G_IMG")).substring(0,
								jsonObject.get("G_IMG").toString().length() - 4) + "_s.jpg");
						jsonObject1.put("IMG_HASH_V1", Hash2);
						// System.out.println(Hash2);
						fw.write(jsonObject1.toString() + "\r\n");
						System.out.println(jsonObject1);

					}
				}

			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
