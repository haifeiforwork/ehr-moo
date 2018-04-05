/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.batch;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * ThumbnailConverter
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailConverter.java 16258 2011-08-18 05:37:22Z giljae $
 */
public final class ThumbnailConverter {

	public final static int IMAGE_WIDTH = 140;

	public final static int IMAGE_HEIGHT = 140;

	public final static double IMAGE_RATIO = 1.2;

	private ThumbnailConverter() {
		throw new AssertionError();
	}

	/**
	 * 썸네일 이미지 변환
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static File thumbnailConvert(File file, String thumbnailWidthSize, String thumbnailHeightSize) {

		BufferedImage outImage = null;
		File thumbnailFile = null;
		BufferedImage inImage = null;

		try {

			// 파일에서 이미지 형태로 불러온다.
			inImage = ImageIO.read(file);

			int width = 0;
			int height = 0;
			double ratio = 1.0F;

			int srcWidth = inImage.getWidth(null);
			int srcHeight = inImage.getHeight(null);

			if (srcWidth < 0 && srcHeight < 0) {
				return null;
			}
			double srcRatio = (double) srcWidth / (double) srcHeight;

			int tgtWidth = IMAGE_WIDTH; // 원하는 가로 크기
			int tgtHeight = IMAGE_HEIGHT; // 원하는 세로 크기

			// 지정된 변환 사이즈로 셋팅함
			if (!thumbnailWidthSize.equals("")) {
				tgtWidth = Integer.valueOf(thumbnailWidthSize);
			}
			if (!thumbnailHeightSize.equals("")) {
				tgtHeight = Integer.valueOf(thumbnailHeightSize);
			}

			double tgtRatio = (double) tgtWidth / (double) tgtHeight;

			// 가로,세로 모두가 타겟보다 작은 경우,
			if (tgtWidth > srcWidth && tgtHeight > srcHeight) {
				width = srcWidth;
				height = srcHeight;
			} else {
				if (tgtRatio < srcRatio) {
					ratio = (double) tgtWidth / (double) srcWidth;

					width = tgtWidth;
					height = (int) (ratio * srcHeight * IMAGE_RATIO);
				}
				if (tgtRatio == srcRatio) {
					ratio = (double) tgtWidth / (double) srcWidth;

					width = (int) (ratio * srcWidth);
					height = (int) (ratio * srcHeight);
				}
				if (tgtRatio > srcRatio) {
					ratio = (double) tgtHeight / (double) srcHeight;

					width = (int) (ratio * srcWidth * IMAGE_RATIO);
					height = tgtHeight;
				}
			}
			// Paint image.
			outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = outImage.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2d.drawImage(inImage, 0, 0, width, height, null);

			if (outImage != null) {
				thumbnailFile = new File(new File(file.getParent()), "THUMB_" + file.getName());
				ImageIO.write(outImage, "jpg", thumbnailFile);

			}

		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if (outImage != null) {
				outImage.flush();
			}
			if (inImage != null) {
				inImage.flush();
			}
		}
		return thumbnailFile;
	}

}
