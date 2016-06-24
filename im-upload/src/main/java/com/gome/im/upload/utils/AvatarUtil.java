package com.gome.im.upload.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.model.ImageReSizeModel;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class AvatarUtil {
	static Logger log = LoggerFactory.getLogger(AvatarUtil.class);
	
	public static String TFS_URL;
	public static String DEFAULT_AVATAR;

	public static float LARGE_SIZE = 480;
	public static String BIG_SIZE ="_large";

	public static float SMQLL_SIZE = 180;	
	public static String SMALL_SIZE = "_small";

	static {
		Properties prop = new Properties();
		InputStream in = ImageUtil.class.getClassLoader().getResourceAsStream("/avatarsize.properties");
		try {
			prop.load(in);
			TFS_URL = prop.getProperty("avatar_url_path").trim();
			DEFAULT_AVATAR = prop.getProperty("default_avatar").trim();
			String normal = prop.getProperty("avatar_size_large").trim();
			int iNormal = Integer.parseInt(normal);
			if (iNormal > 0) {
				LARGE_SIZE = iNormal;
			}
			String small = prop.getProperty("avatar_size_small").trim();
			int iSmall = Integer.parseInt(small);
			if (iSmall > 0) {
				SMQLL_SIZE = iSmall;
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
	/**
	 * 聊天消息图片源图大小
	 * 
	 * @param input
	 * @return
	 */
	public static ImageReSizeModel AvatarOriginSize(InputStream input){
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
	 * 聊天消息图片缩放--小图片，small
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
			int srcHeight = inputBufImage.getHeight();// 原图片宽度
			int srcWidth = inputBufImage.getWidth();// 原图片高度

			// 横幅
			if (srcWidth > srcHeight) {
				if (srcWidth / srcHeight > fScale) {
					float desWith = srcHeight * fScale;
					height = SMQLL_SIZE;
					with = desWith;
					if (srcHeight <= SMQLL_SIZE) {
						height = srcHeight;
					} else {
						float f = SMQLL_SIZE / srcHeight;
						with = f * desWith;
					}

					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, (int) desWith, srcHeight)
							.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
							.outputFormat(format).toOutputStream(out);
				} else {
					height = SMQLL_SIZE;
					with = srcWidth;
					if (srcHeight <= SMQLL_SIZE) {
						height = srcHeight;
					} else {
						float f = SMQLL_SIZE / srcHeight;
						with = f * srcWidth;
					}

					Thumbnails.of(inputBufImage).size((int) with, (int) height).outputFormat(format).outputQuality(0.5)
							.toOutputStream(out);
				}
			} else if (srcWidth < srcHeight) {// 竖幅
				if (srcHeight / srcWidth > fScale) {
					float desHeight = srcWidth * fScale;
					height = desHeight;
					with = SMQLL_SIZE;
					if (srcWidth <= SMQLL_SIZE) {
						with = srcWidth;
					} else {
						float f = SMQLL_SIZE / srcWidth;
						height = f * desHeight;
					}

					Thumbnails.of(inputBufImage).sourceRegion(Positions.CENTER, srcWidth, (int) desHeight)
							.size((int) with, (int) height).keepAspectRatio(false).outputQuality(0.5)
							.outputFormat(format).toOutputStream(out);
				} else {
					with = SMQLL_SIZE;
					height = srcHeight;
					if (srcWidth <= SMQLL_SIZE) {
						with = srcWidth;
					} else {
						float f = SMQLL_SIZE / srcWidth;
						height = f * srcHeight;
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
				if (srcWidth > LARGE_SIZE) {// 720
					desWhith = (int)LARGE_SIZE;
					desHeight = (int) (srcHeight * (LARGE_SIZE / srcWidth));
				}
			}
			// 横图
			else if (srcHeight < srcWidth) {
				if (srcHeight > LARGE_SIZE) {
					desHeight = (int)LARGE_SIZE;
					desWhith = (int) (srcWidth * (LARGE_SIZE / srcHeight));
				}
			}
			// 方图
			else if (desWhith > LARGE_SIZE) {
				desWhith = (int)LARGE_SIZE;
				desHeight = (int)LARGE_SIZE;
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

}
