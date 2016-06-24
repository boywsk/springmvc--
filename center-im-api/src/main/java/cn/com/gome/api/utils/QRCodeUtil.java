package cn.com.gome.api.utils;

import cn.com.gome.api.global.Global;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangshikai on 2015/11/26.
 */
public class QRCodeUtil {

    /**
     * 根据内容生成二维码
     * @param content  待生成二维码的内容
     * @return
     */
    public static byte[] buildQRCodeImage(String content) {
        byte[] buf = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, Global.QRCODE_WIDTH, Global.QRCODE_HEIGHT, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", out);
            buf = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf;
    }

    /**
     * 解析二维码
     * @param filePath  二维码文件地址
     * @return
     */
    public static String parseQRCodeImage(String filePath) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }

//    public static void main(String[] args) {
//        String file = "E:\\image\\二维码.jpg";
//        System.out.println("二维码内容:" + parseQRCodeImage(file));
//    }

}
