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
		int sum = 0;

		for (int i = 0; i < arrayBF.length; i++) { // 拚imagePath

			Object obj = parser.parse(arrayBF[i]);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject jsonObject1 = new JSONObject();

			if (String.valueOf(jsonObject.get("G_IMG")).contains("(null)")) { // 過濾G_IMG為NULL

			} else {
				String[] G_IMGPath = String.valueOf(jsonObject.get("G_IMG")).split(","); // 如果有多個G_IMG取第一個
				String G_IMGPath1 = G_IMGPath[0].toString(); // 第一個G_IMG
				if (G_IMGPath1.contains(".")) { // 篩選副檔名
					sum = sum + 1;
					String[] G_IMGPath2 = G_IMGPath1.split("\\."); // 擷取副檔名
					try {
						String str = G_IMGPath2[1]; // 副檔名名稱
						String imageName = G_IMGPath2[0]; // IMG name
						switch (str) {
						case "jpg":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "jpeg":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "JPG":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "JPEG":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "png":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "PNG":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						case "Jpg":
							getFingerPrint(str, jsonObject, jsonObject1, imageName, sdf, fw);
							break;

						}
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}

				}

			}

		}
		System.out.println(sum);

		fw.close();

	}

	static void getFingerPrint(String str, JSONObject jsonObject, JSONObject jsonObject1, String imageName,
			SimpleDateFormat sdf, FileWriter fw) {
		String imagePath = ImageUtility.getImgPath(String.valueOf(jsonObject.get("G_NO")),
				String.valueOf(jsonObject.get("USER_NICK")), String.valueOf(jsonObject.get("G_STORAGE"))) + imageName
				+ "_s." + str;

		if (imagePath.contains("null")) {

		} else {

			System.out.println(imagePath);
			Date current = new Date();
			jsonObject1.put("G_NO", String.valueOf(jsonObject.get("G_NO")));
			jsonObject1.put("_SOUTCE_TIME", sdf.format(current));
			jsonObject1.put("HASH_IMG", imageName + "_s." + str);
			jsonObject1.put("IMG_HASH_V1", "tt");
			try {
				fw.write(jsonObject1.toString() + "\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(jsonObject1);
		}

	}

}
