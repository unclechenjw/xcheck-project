package com.c.j.w.security.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xuzhen
 * @module 图片验证码
 * @company 杭州动享互联网技术有限公司
 * @date: 2016/12/6 9:43
 * http://blog.csdn.net/ruixue0117/article/details/22829557
 */

public class VerifyCodeUtil {

    private static Random random = new Random();
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            outputImage(w, h, bufferedOutputStream, code);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        BufferedImage image = getBufferedImage(w, h, code, null);
        ImageIO.write(image, "jpg", os);
    }

    /**
     * 生成验证码
     * 默认长度200，宽度80
     *
     * @param code 验证码
     * @return 返回字节流
     */
    public static byte[] generateByteData(String code) {
        return generateByteData(200, 80, code);
    }

    /**
     * 生成验证码
     *
     * @param w    长度
     * @param h    高度
     * @param code 验证码
     * @return 返回字节流
     */
    public static byte[] generateByteData(int w, int h, String code) {
        BufferedImage image = getBufferedImage(w, h, code, null);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = null;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "jpg", imOut);
            return bs.toByteArray();
            //Base64.encode(bs.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imOut.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


    public static VerifyCode generateVerifyCodeData() {
        return generateVerifyCodeData(200, 80, generateVerifyCode(8), choiceIndex(8, 4));
    }

    /**
     * 生成验证码
     *
     * @param w      长度
     * @param h      高度
     * @param code   验证码
     * @param indexs 变色索引
     * @return 返回字节流
     */
    public static VerifyCode generateVerifyCodeData(int w, int h, String code, List<Integer> indexs) {
        BufferedImage image = getBufferedImage(w, h, code, indexs);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = null;
        byte[] bytes = null;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "jpg", imOut);
            bytes = bs.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imOut.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        String codeStr = "";
        for (int i = 0; i < code.length(); i++) {
            if (indexs.contains(i)) {
                codeStr += code.charAt(i);
            }
        }
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setBase64Str(Base64.encode(bytes));
        verifyCode.setCode(codeStr);
        return verifyCode;
    }

    private static BufferedImage getBufferedImage(int w, int h, String code, List<Integer> indexs) {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 5, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.02f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        char[] chars = code.toCharArray();

        Color defult = Color.BLACK;
        FontColor randomFontColor = FontColor.getRandomColor();

        for (int i = 0; i < verifySize; i++) {
            if (indexs != null && indexs.contains(i)) {
                g2.setColor(randomFontColor.color);
            } else {
                g2.setColor(defult);
            }
            int fontSize = h - 35;
            Font font = new Font("宋体", Font.ITALIC, fontSize);
            g2.setFont(font);
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 2) / verifySize) * i, h / 2 + fontSize / 2 - 20);
        }

        AffineTransform affine = new AffineTransform();
        g2.setTransform(affine);
        Font font = new Font("宋体", Font.BOLD, 20);
        g2.setFont(font);
        g2.setColor(randomFontColor.color);
        g2.drawString("请输入" + randomFontColor.showName + "字", 30, 70);

        g2.dispose();
        return image;
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }

    private static List<Integer> choiceIndex(int totalNum, int choiceNum) {
        List<Integer> nums = new ArrayList<Integer>();
        Integer canDiscardNum = totalNum - choiceNum;//可丢弃数量
        Random random = new Random();
        Integer continuity = 0;//连续相同标志个数超过2次则转为另外的标志
        Boolean lastFlag = null;
        for (int i = 0; i < totalNum; i++) {
            if (canDiscardNum > 0) {
                Boolean flag = random.nextBoolean();
                if (i == 0) {
                    lastFlag = flag;
                    continuity++;
                } else if (lastFlag.equals(flag)) {
                    if (continuity >= 2) {
                        flag = !flag;
                        lastFlag = flag;
                        continuity = 1;
                    } else {
                        continuity++;
                    }
                } else {
                    lastFlag = flag;
                    continuity = 1;
                }
                if (flag && nums.size() < choiceNum) {
                    nums.add(i);
                } else {
                    canDiscardNum--;
                }
            } else {
                nums.add(i);
            }
        }
        return nums;
    }

    enum FontColor {

        Red("红色", Color.RED),
        GREEN("绿色", Color.GREEN),
        BLUE("蓝色", Color.BLUE);

        public String showName;
        public Color color;

        FontColor(String showName, Color color) {
            this.showName = showName;
            this.color = color;
        }

        public static FontColor getRandomColor() {
            FontColor[] values = FontColor.values();
            Random random = new Random();
            int index = random.nextInt(values.length);
            return values[index];
        }
    }

    public static void main(String[] args) throws IOException {
        /*File dir = new File("E:/verifies");
        int w = 200, h = 80;
        for (int i = 0; i < 50; i++) {
            // String verifyCode = "\u8bb8\u632f\u4f60\u597d\u554a";
            String verifyCode = generateVerifyCode(8);
            String name = "xx1";
            File file = new File(dir, name + i + ".jpg");
            outputImage(w, h, file, verifyCode);
        }*/

        for (int i = 0; i < 50; i++) {
            VerifyCode verifyCode = generateVerifyCodeData();
            byte[] bytes = Base64.decode(verifyCode.getBase64Str());
            System.out.println(verifyCode.getCode());
            String imgFilePath = "d://output//" + verifyCode.getCode() + "______222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
        }
        //System.out.println(choiceIndex(6, 4));

        /*//System.out.println(Base64.encode(generateByteData("12BF")));
        byte[] bytes = Base64.decode("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");
        String imgFilePath = "d://222.jpg";//新生成的图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(bytes);
        out.flush();
        out.close();*/
    }
}

