package com.pragone.jphash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tw.com.ruten.util.ImageUtility;

public class jphashTest {

	public static void main(String[] args) throws IOException, URISyntaxException, ParseException {

		System.out.println("inputPath: ");
		Scanner scanner = new Scanner(System.in);
		String inputPath = scanner.nextLine();
		System.out.println("outputPath: ");
		String outputPath = scanner.next();

		File file = new File(inputPath); // 讀取測試檔
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}
		fileReader.close();

		String[] arrayBF = stringBuffer.toString().split("\n");// 切割字串
		JSONParser parser = new JSONParser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'"); //
		FileWriter fw = new FileWriter(outputPath);

		for (int i = 0; i < arrayBF.length; i++) { // 拚imagePath

			Object obj = parser.parse(arrayBF[i]);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject jsonObject1 = new JSONObject();

			if (!String.valueOf(jsonObject.get("G_IMG")).contains("(null)")) {

				String imagePath = ImageUtility.getImgPath(String.valueOf(jsonObject.get("G_NO")),
						String.valueOf(jsonObject.get("USER_NICK")), String.valueOf(jsonObject.get("G_STORAGE")))
						+ String.valueOf(jsonObject.get("G_IMG")).substring(0,
								String.valueOf(jsonObject.get("G_IMG")).length() - 4)
						+ "_s.jpg";
				File file2 = new File("/mnt/" + imagePath);

				if (imagePath.contains("null") || imagePath.contains(",") || imagePath.contains("gif")) {

				} else {

					System.out.println(imagePath);
					Date current = new Date();
					// System.out.println(file2);
					jsonObject1.put("G_NO", String.valueOf(jsonObject.get("G_NO")));
					jsonObject1.put("_SOUTCE_TIME", sdf.format(current));
					jsonObject1.put("HASH_IMG", String.valueOf(jsonObject.get("G_IMG")).substring(0,
							jsonObject.get("G_IMG").toString().length() - 4) + "_s.jpg");
					jsonObject1.put("IMG_HASH_V1", "tt");
					// RadialHash hash2 = jpHash.getImageRadialHash("/mnt/" + imagePath);
					// String Hash2 = String.valueOf(hash2);
					// System.out.println(Hash2);
					//fw.write(jsonObject1.toString() + "\r\n");
					System.out.println(jsonObject1);
					
				}

			} else {

			}

		}

		fw.close();

	}

}
