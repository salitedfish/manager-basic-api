package com.ruoyi.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtils {

    /**
     * 图片压缩
     * @param file
     * @return
     * @throws IOException
     */
    static public InputStream resizeImage(MultipartFile file) throws IOException {
        // 读取图片文件
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        System.out.println("原来的宽度："+originalImage.getWidth());
        System.out.println("原来的高度："+originalImage.getHeight());

        // 计算缩放比例，保持图片比例
        double scaleX = (double) 1280 / originalImage.getWidth();
        double scaleY = (double) 720 / originalImage.getHeight();
        double scale = Math.min(scaleX, scaleY);
        System.out.println("缩放比："+scale);


        // 计算缩放后的宽度和高度
        int scaledWidth = (int) (originalImage.getWidth() * scale);
        int scaledHeight = (int) (originalImage.getHeight() * scale);
        System.out.println("压缩后的宽度："+scaledWidth);
        System.out.println("压缩后的高度："+scaledHeight);

        // 创建缩放后的图片
        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        scaledImage.getGraphics().drawImage(originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH), 0, 0, null);

        // 将缩放后的图片写入字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, "jpg", baos);

        return new ByteArrayInputStream(baos.toByteArray());
    }

    // 根据扩展名判断是不是图片
    static public boolean isImage(String extName) throws IOException {
        String[] imgTypes = {"png", "jpg", "jpeg", "gif", "svg"};
        for(String ext: imgTypes) {
            if(extName.indexOf(ext) != -1) {
                return true;
            }
        }
        return false;
    }
}