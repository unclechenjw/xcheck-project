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
 * @date: 2016/12/6 9:43
 * http://blog.csdn.net/ruixue0117/article/details/22829557
 */

public class VerifyCodeUtil {

    private static Font BaseFont;
    private static Random random = new Random();
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    static {
        InputStream resourceAsStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            resourceAsStream = VerifyCodeUtil.class.getResourceAsStream("/msyhl.ttc");
            bufferedInputStream = new BufferedInputStream(resourceAsStream);
            BaseFont = Font.createFont(Font.TRUETYPE_FONT, bufferedInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                try {
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }

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
//            Font font = new Font(typeface, Font.ITALIC, fontSize);
            Font font = BaseFont.deriveFont(Font.ITALIC, fontSize);
            g2.setFont(font);
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 2) / verifySize) * i, h / 2 + fontSize / 2 - 20);
        }

        AffineTransform affine = new AffineTransform();
        g2.setTransform(affine);
//        Font font = new Font(typeface, Font.BOLD, 20);
        Font font = BaseFont.deriveFont(Font.ITALIC, 20);
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
        //Plink("粉红色", Color.PINK),
        //Orange("橙色", Color.ORANGE),
        Magenta("品红色", Color.MAGENTA),
        Cyan("青色", Color.CYAN),
        Green("绿色", Color.GREEN),
        //Yellow("黄色", Color.YELLOW),
        Blue("蓝色", Color.BLUE);

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
            String name = "许振1";
            File file = new File(dir, name + i + ".jpg");
            outputImage(w, h, file, verifyCode);
        }*/

        for (int i = 0; i < 50; i++) {
            VerifyCode verifyCode = generateVerifyCodeData();
            byte[] bytes = Base64.decode(verifyCode.getBase64Str());
            System.out.println(verifyCode.getCode());
            String imgFilePath = "d://output//" + verifyCode.getCode() + "______111.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
        }
        //System.out.println(choiceIndex(6, 4));

        //System.out.println(Base64.encode(generateByteData("12BF")));
//        byte[] bytes = Base64.decode(
//                "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"
//        );
//        String imgFilePath = "d://output/222.jpg";//新生成的图片
//        OutputStream out = new FileOutputStream(imgFilePath);
//        out.write(bytes);
//        out.flush();
//        out.close();
    }
}

