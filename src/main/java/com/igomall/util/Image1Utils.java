
package com.igomall.util;

import com.igomall.common.Setting;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.im4java.core.*;
import org.springframework.util.Assert;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utils - 图片处理
 * 
 * @author blackboy
 * @version 1.0
 */
public final class Image1Utils {

	public static void zoom(File srcFile,File destFile,Integer width,Integer height){
		try {
			Thumbnails.of(srcFile)
					.forceSize(width, height)
					.toFile(destFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}