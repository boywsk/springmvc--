package com.gome.im.upload.utils;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.gome.im.upload.model.ImageReSizeModel;
import com.mortennobel.imagescaling.ResampleOp;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片缩放工具类
 */
public class ImageUtil {
	static Logger log = LoggerFactory.getLogger(ImageUtil.class);
	
	public static String TFS_URL;

	public static float NORMAL_SIZE = 480;// 默认值
	public static String MIDDLE_SIZE ="_Middle";

	public static float SMQLL_SIZE = 360;// 默认值,之前180，2016-04-11调整至360
	public static String SMALL_SIZE = "_Small";

	static {
		Properties prop = new Properties();
		// InputStream in = Object.class.getResourceAsStream("/tfs.properties");
		InputStream in = ImageUtil.class.getClassLoader().getResourceAsStream("/imgsize.properties");
		try {
			prop.load(in);
			TFS_URL = prop.getProperty("url_path").trim();
			String normal = prop.getProperty("img_size_normal").trim();
			int iNormal = Integer.parseInt(normal);
			if (iNormal > 0) {
				NORMAL_SIZE = iNormal;
			}
			String small = prop.getProperty("img_size_small").trim();
			int iSmall = Integer.parseInt(small);
			if (iSmall > 0) {
				SMQLL_SIZE = iSmall;
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 接收输入流输生成图片
	 * 
	 * @param input
	 * @param writePath
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static ImageReSizeModel resizeImage(InputStream input, String format) {
		try {
			ImageReSizeModel irs = new ImageReSizeModel();
			BufferedImage inputBufImage = ImageIO.read(input);
			int srcHeight = inputBufImage.getHeight();
			int srcWidth = inputBufImage.getWidth();

			int desHeight = srcHeight;
			int desWhith = srcWidth;
			// 竖图
			if (srcHeight > srcWidth) {
				if (srcWidth > NORMAL_SIZE) {// 720
					desWhith = (int) NORMAL_SIZE;
					desHeight = (int) (srcHeight * (NORMAL_SIZE / srcWidth));
				}
			}
			// 横图
			else if (srcHeight < srcWidth) {
				if (srcHeight > NORMAL_SIZE) {
					desHeight = (int) NORMAL_SIZE;
					desWhith = (int) (srcWidth * (NORMAL_SIZE / srcHeight));
				}
			}
			// 方图
			else if (desWhith > NORMAL_SIZE) {
				desWhith = (int) NORMAL_SIZE;
				desHeight = (int) NORMAL_SIZE;
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// http://www.juapk.com/thread-5363-1-1.html
			Thumbnails.of(inputBufImage).size(desWhith, desHeight).outputQuality(0.7).outputFormat(format)
					.toOutputStream(out);
			irs.setHeight(desHeight);
			irs.setWidth(desWhith);
			irs.setData(out.toByteArray());

			return irs;
		} catch (Exception e) {
			log.error("resizeImage", e);
			return null;
		}
	}

	/**
	 * 接收输入流输生成图片
	 * 
	 * @param input
	 * @param writePath
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static byte[] resizeImage(InputStream input, String format, Map<Object, Object> retMap) {
		int maxLen = 2000;
		int baseLen = 480;
		try {
			BufferedImage inputBufImage = ImageIO.read(input);
			int srcHeight = inputBufImage.getHeight();
			int srcWidth = inputBufImage.getWidth();

			int desHeight = srcHeight;
			int desWhith = srcWidth;

			// 竖图
			if (srcHeight > srcWidth) {
				float f = Math.min((float) baseLen / srcWidth, (float) maxLen / (float) desHeight);
				desWhith = (int) (srcWidth * f);
				desHeight = (int) (srcHeight * f);
			}
			// 横图
			else if (srcHeight < srcWidth) {
				float f = Math.min((float) maxLen / srcWidth, (float) baseLen / (float) desHeight);
				desWhith = (int) (srcWidth * f);
				desHeight = (int) (srcHeight * f);
			}
			// 方图
			else if (desWhith > 480) {
				desWhith = 480;
				desHeight = 480;
			}

			if (retMap != null) {
				retMap.put("whith", desWhith);
				retMap.put("height", desHeight);
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Thumbnails.of(inputBufImage).size(desWhith, desHeight).outputFormat(format).outputQuality(0.7)
					.toOutputStream(out);

			return out.toByteArray();
		} catch (IOException e) {
			log.error("resizeImage", e);
			return null;
		}
	}

	/**
	 * 压缩图标
	 * 
	 * @param input
	 * @param format
	 * @return
	 */
	public static byte[] resizeIcon(InputStream input, String format) {
		try {
			BufferedImage inputBufImage = ImageIO.read(input);
			int srcHeight = inputBufImage.getHeight();
			int srcWidth = inputBufImage.getWidth();

			int desHeight = srcHeight;
			int desWhith = srcWidth;

			if (srcWidth > srcHeight) { // 150*100
				desWhith = 150;
				desHeight = 100;
			} else if (srcWidth < srcHeight) { // 100*150
				desWhith = 100;
				desHeight = 150;
			} else {
				desWhith = 100;
				desHeight = 100;
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Thumbnails.of(inputBufImage).size(desWhith, desHeight).outputFormat(format).outputQuality(0.7)
					.toOutputStream(out);

			return out.toByteArray();
		} catch (IOException e) {
			log.error("resizeIcon", e);
			return null;
		}
	}

	/**
	 * 娱乐小图片240*240
	 * 
	 * @param input
	 * @param format
	 * @return
	 */
	public static byte[] resizeForumIcon(InputStream input, String format) {
		final float maxSize = 240.0f;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			BufferedImage inputBufImage = ImageIO.read(input);
			int srcHeight = inputBufImage.getHeight();// 原图片宽度
			int srcWidth = inputBufImage.getWidth();// 原图片高度

			// 横幅
			if (srcWidth > srcHeight) {
				if (srcHeight > (int) maxSize) {
					float fScale = maxSize / srcHeight;
					BufferedImage buff = Thumbnails.of(inputBufImage).scale(fScale).outputFormat(format)
							.asBufferedImage();

					Thumbnails.of(buff).scale(1).sourceRegion(Positions.CENTER, (int) maxSize, (int) maxSize)
							.outputFormat(format).outputQuality(0.7).toOutputStream(out);

				} else {
					Thumbnails.of(inputBufImage).scale(1).sourceRegion(Positions.CENTER, srcHeight, srcHeight)
							.outputFormat(format).toOutputStream(out);

				}
			} else if (srcHeight >= srcWidth) {
				if (srcWidth > (int) maxSize) {
					float fScale = maxSize / srcWidth;
					BufferedImage buff = Thumbnails.of(inputBufImage).scale(fScale).outputFormat(format)
							.asBufferedImage();

					Thumbnails.of(buff).scale(1).sourceRegion(Positions.CENTER, (int) maxSize, (int) maxSize)
							.outputFormat(format).toOutputStream(out);

				} else {
					Thumbnails.of(inputBufImage).scale(1).sourceRegion(Positions.CENTER, srcWidth, srcWidth)
							.outputFormat(format).toOutputStream(out);

				}
			}
		} catch (Exception e) {
			log.error("resizeForumIcon", e);
			return null;
		}
		return out.toByteArray();
	}
	
	/**
	 * 聊天消息图片源图大小
	 * 
	 * @param input
	 * @return
	 */
	public static ImageReSizeModel imgOriginSize(InputStream input){
		ImageReSizeModel irs = new ImageReSizeModel();
		try{			
			BufferedImage inputBufImage = ImageIO.read(input);
			int srcHeight = inputBufImage.getHeight();// 原图片宽度
			int srcWidth = inputBufImage.getWidth();// 原图片高度
			irs.setHeight(srcHeight);
			irs.setWidth(srcWidth);	
			return irs;
		}catch(IOException e) {
			log.error("resizeChatIcon", e);
			irs.setHeight(0);//出错全0
			irs.setWidth(0);	
			return irs;
		}
	}
	/**
	 * 聊天消息图片缩放
	 * 
	 * @param input
	 * @param format
	 * @return
	 */

	public static ImageReSizeModel resizeChatIcon(InputStream input, String format) {
		ImageReSizeModel irs = new ImageReSizeModel();
		float height = 0;
		float with = 0;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			float fScale = 16.0f / 9.0f;
			BufferedImage inputBufImage = ImageIO.read(input);
//			int srcHeight = inputBufImage.getHeight();// 原图片宽度
//			int srcWidth = inputBufImage.getWidth();// 原图片高度
			float srcHeight = inputBufImage.getHeight();// 原图片宽度
			float srcWidth = inputBufImage.getWidth();// 原图片高度

			// 横幅
			if (srcWidth > srcHeight) {
				if (srcWidth / srcHeight > fScale) {
					float desWith = (int)srcHeight * fScale;
					height = SMQLL_SIZE;
					with = (int)desWith;
					if (srcHeight <= SMQLL_SIZE) {
						height = srcHeight;
					} else {
						float f = SMQLL_SIZE / srcHeight;
						with = (int)(f * desWith);
					}

//					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, (int) desWith, srcHeight)
//							.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
//							.outputFormat(format).toOutputStream(out);
					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, (int) desWith, (int)srcHeight)
					.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
					.outputFormat(format).toOutputStream(out);
				} else {
					height = SMQLL_SIZE;
					with = srcWidth;
					if (srcHeight <= SMQLL_SIZE) {
						height = srcHeight;
					} else {
						float f = SMQLL_SIZE / srcHeight;
						with = (int)(f * srcWidth);
					}

					Thumbnails.of(inputBufImage).size((int) with, (int) height).outputFormat(format).outputQuality(0.5)
							.toOutputStream(out);
				}
			} else if (srcWidth < srcHeight) {// 竖幅
				if (srcHeight / srcWidth > fScale) {
					float desHeight = (int)srcWidth * fScale;
					height = (int)desHeight;
					with = SMQLL_SIZE;
					if (srcWidth <= SMQLL_SIZE) {
						with = srcWidth;
					} else {
						float f = SMQLL_SIZE / srcWidth;
						height = (int)(f * desHeight);
					}

//					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, srcWidth, (int) desHeight)
//							.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
//							.outputFormat(format).toOutputStream(out);
					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, (int)srcWidth, (int) desHeight)
					.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
					.outputFormat(format).toOutputStream(out);
				} else {
					with = SMQLL_SIZE;
					height = (int)srcHeight;
					if (srcWidth <= SMQLL_SIZE) {
						with = srcWidth;
					} else {
						float f = SMQLL_SIZE / srcWidth;
						height = (int)(f * srcHeight);
					}

					Thumbnails.of(inputBufImage).size((int) with, (int) height).outputFormat(format).outputQuality(0.5)
							.toOutputStream(out);
				}
			} else {// 方图
				with = SMQLL_SIZE;
				height = SMQLL_SIZE;
				if (srcWidth <= SMQLL_SIZE) {
					height = srcHeight;
					with = srcWidth;
				}

				Thumbnails.of(inputBufImage).size((int) with, (int) height).outputFormat(format).outputQuality(0.5)
						.toOutputStream(out);
			}
			irs.setHeight(height);
			irs.setWidth(with);
			irs.setData(out.toByteArray());
			return irs;
		} catch (IOException e) {
			log.error("resizeChatIcon", e);
			return null;
		}
	}

	/**
	 * 接收输入流输生成图片
	 * 
	 * @param input
	 * @param writePath
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static byte[] resizeImage(InputStream input, Integer width, Integer height, String format) {
		try {
			BufferedImage inputBufImage = ImageIO.read(input);
			int imageHeight = inputBufImage.getHeight();
			int imageWidth = inputBufImage.getWidth();

			// 得到合适的压缩大小，按比例。
			if (imageHeight <= height) {
				height = imageHeight;
			}
			if (imageWidth <= width) {
				width = imageWidth;
			}
			if (imageWidth >= imageHeight) {
				height = (int) Math.round((imageHeight * width * 1.0 / imageWidth));
			} else {
				width = (int) Math.round((imageWidth * height * 1.0 / imageHeight));
			}
			ResampleOp resampleOp = new ResampleOp(width, height);// 转换
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(rescaledTomato, format, new DataOutputStream(out));

			return out.toByteArray();
		} catch (IOException e) {
			log.error("resizeImage", e);
			return null;
		}
	}

	/**
	 * 接收File输出图片
	 * 
	 * @param file
	 * @param writePath
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static boolean resizeImage(String intPath, String writePath, Integer width, Integer height, String format) {
		try {
			File file = new File(intPath);
			BufferedImage inputBufImage = ImageIO.read(file);
			int imageHeight = inputBufImage.getHeight();
			int imageWidth = inputBufImage.getWidth();

			// 得到合适的压缩大小，按比例。
			if (imageWidth >= imageHeight) {
				height = (int) Math.round((imageHeight * width * 1.0 / imageWidth));
			} else {
				width = (int) Math.round((imageWidth * height * 1.0 / imageHeight));
			}
			ResampleOp resampleOp = new ResampleOp(width, height);// 转换
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
			ImageIO.write(rescaledTomato, format, new File(writePath));

			return true;
		} catch (IOException e) {
			log.error("resizeImage", e);
			return false;
		}

	}

	/**
	 * 接收字节数组生成图片
	 * 
	 * @param RGBS
	 * @param writePath
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static byte[] resizeImage(byte[] RGBS, File file, Integer width, Integer height, String format) {
		InputStream input = new ByteArrayInputStream(RGBS);
		return resizeImage(input, width, height, format);
	}

	public static byte[] readBytesFromIS(InputStream is) throws IOException {
		int total = is.available();
		byte[] bs = new byte[total];
		is.read(bs);
		return bs;
	}

	/**
	 * 计算图片方向
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 * @throws MetadataException
	 * @throws ImageProcessingException
	 */
	public static int readImagOrientation(InputStream is) {
		int degree = 0;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(is);

			ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

			// Directory directory =
			// metadata.getDirectory(ExifIFD0Directory.class);
			if (directory == null) {
				return -1;
			}
			if (!directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
				return -1;
			}
			int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
			switch (orientation) {
			case 3:

				degree = 180;
				break;
			case 6:

				degree = 90;
				break;
			case 8:

				degree = 270;
				break;
			default:

				degree = 0;
			}
		} catch (Exception e) {
			log.error("readImagOrientation", e);
		}

		return degree;
	}

	/**
	 * 图片旋转
	 * 
	 * @param image
	 * @param degree
	 * @param bgcolor
	 * @return
	 * @throws IOException
	 */
	public static InputStream rotateImg(BufferedImage image, int degree, String format) throws IOException {
		int iw = image.getWidth();// 原始图象的宽度
		int ih = image.getHeight();// 原始图象的高度
		int w = 0;
		int h = 0;
		int x = 0;
		int y = 0;
		degree = degree % 360;
		if (degree < 0) {
			degree = 360 + degree;// 将角度转换到0-360度之间
		}
		double ang = Math.toRadians(degree);// 将角度转为弧度
		/**
		 * 确定旋转后的图象的高度和宽度
		 */
		if (degree == 180 || degree == 0 || degree == 360) {
			w = iw;
			h = ih;
		} else if (degree == 90 || degree == 270) {
			w = ih;
			h = iw;
		} else {
			int d = iw + ih;
			w = (int) (d * Math.abs(Math.cos(ang)));
			h = (int) (d * Math.abs(Math.sin(ang)));
		}

		x = (w / 2) - (iw / 2);// 确定原点坐标
		y = (h / 2) - (ih / 2);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
		rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);

		AffineTransform at = new AffineTransform();
		at.rotate(ang, w / 2, h / 2);// 旋转图象
		at.translate(x, y);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		op.filter(image, rotatedImage);
		image = rotatedImage;

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);
		ImageIO.write(image, format, iamgeOut);

		InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray());

		return inputStream;
	}

	public static BufferedImage rotateImg2(BufferedImage image, int degree, String format) throws IOException {
		Rotation rotation = null;
		switch (degree) {
		case 90:
			rotation = Rotation.CW_90;
			break;
		case 180:
			rotation = Rotation.CW_180;
			break;
		case 270:
			rotation = Rotation.CW_270;
			break;
		}
		BufferedImage outImg = Scalr.rotate(image, rotation);
		return outImg;
	}
}
