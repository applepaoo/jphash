package com.pragone.jphash;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.image.radial.RadialHashAlgorithm;
import tw.com.ruten.util.ImageUtility;

public class jphashTest {

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
			
			 File file3 = new File("tmp/21306287664252_485_s.jpg");
			 BufferedImage test = ImageIO.read(file3);
			
		} catch (Exception e) {

		}finally {
			
			try {
				File file = new File("tmp/test.txt"); // 讀取測試檔
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'"); //
				FileWriter fw = new FileWriter("tmp/output");

				for (int i = 0; i < arrayBF.length - 1; i++) { // 拚imagePath

					Object obj = parser.parse(arrayBF[i] + "}");
					JSONObject jsonObject = (JSONObject) obj;
					JSONObject jsonObject1 = new JSONObject();

					if (String.valueOf(jsonObject.get("G_IMG")).contains("(null)")) {

					} else {
						String imagePath = ImageUtility.getImgPath(String.valueOf(jsonObject.get("G_NO")),
								String.valueOf(jsonObject.get("USER_NICK")), String.valueOf(jsonObject.get("G_STORAGE")))
								+ String.valueOf(jsonObject.get("G_IMG")).substring(0,
										jsonObject.get("G_IMG").toString().length() - 4)
								+ "_s.jpg";
						File file2 = new File("/mnt/" + imagePath);
						if (imagePath.contains("null") || imagePath.contains(",") || imagePath.contains("gif")) {

						} else {
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
							// fw.write(jsonObject1.toString()+"\r\n");
							 System.out.println(jsonObject1);
							
						}
					}

				}
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}



	

		// FigureN
		// File file = new File("D:\\workspace2\\jphash\\airpods");
		// String[] filenames;
		//
		// if (file.isDirectory()) {
		//
		// filenames = file.list();
		// ArrayList dataList = new ArrayList();
		//
		// for (int i = 0; i < filenames.length; i++)
		//
		// {
		//
		// RadialHash hash2 = jpHash.getImageRadialHash(file + "/" + filenames[i]);
		// String Hash2 = hash2.toString();// 算出的Fingerprint
		//
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'"); //
		// 格式化時間
		// Date current = new Date();
		//
		// JSONObject obj = new JSONObject();
		//
		// obj.put("G_NO", filenames[i]);
		// // obj.put("G_FINGERPRINT", hexToBinary(Hash2));
		// obj.put("G_HASH", Hash2);
		// obj.put("_SOURCE_TIME", sdf.format(current));
		// dataList.add(i, obj);
		//
		// }
		//
		// System.out.println(dataList);
		//
		// }

	}

}
