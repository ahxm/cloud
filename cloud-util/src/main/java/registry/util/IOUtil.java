package registry.util;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by anzhen on 2016/5/25.
 */
public class IOUtil {

    /**
     * 追加拷贝文件
     *
     * @param source 文件源
     * @param target 目标文件
     * @param start  开始
     * @param length 长度
     * @throws java.io.IOException
     */

    public static void copy(File source,File target,long start,long length) throws IOException{
        if(source == null || target == null){
            return;
        }
        if(!target.exists()){
            target.createNewFile();
        }
        if(length<0){
            return;
        }
        if(start < 0){
            start = 0;
        }

        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try{
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target,true);
            in = inputStream.getChannel();
            out = outputStream.getChannel();
            in.transferTo(start,length,out);
            out.force(false);
        }finally {
            Closeables.close(inputStream);
            Closeables.close(in);
            Closeables.close(outputStream);
            Closeables.close(out);
        }
    }

    /**
     * 快速增量拷贝文件，采用随机文件读写方式，不覆盖文件
     *
     * @param source    源文件
     * @param target    目标文件
     * @param srcStart  源文件开始位置
     * @param length    长度
     * @param targetPos 目标文件位置
     * @throws java.io.IOException
     */

    public static void copy(File source, File target, long srcStart, long length, long targetPos) throws IOException {
        if (source == null || target == null) {
            return;
        }
        if (!target.exists()) {
            target.createNewFile();
        }
        if (length <= 0) {
            return;
        }
        if (srcStart < 0) {
            srcStart = 0;
        }
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        RandomAccessFile raf = null;
        try {

            inStream = new FileInputStream(source);
            raf = new RandomAccessFile(target, "rw");
            raf.seek(targetPos);
            in = inStream.getChannel();
            out = raf.getChannel();
            in.transferTo(srcStart, length, out);
            out.force(false);
        } finally {
            Closeables.close(inStream);
            Closeables.close(in);
            Closeables.close(raf);
            Closeables.close(out);
        }
    }

    /**
     * 追加拷贝文件
     *
     * @param source 文件源
     * @param target 目标文件
     * @param length 长度
     * @throws java.io.IOException
     */

    public static void copy(File source, File target, long length) throws IOException {
        copy(source, target, 0, length);
    }

    /**
     * 拷贝覆盖文件
     *
     * @param source 文件源
     * @param target 目标文件
     * @throws java.io.IOException
     */
    public static void copy(File source, File target) throws IOException {
        if (source == null || target == null) {
            return;
        }
        if (!target.exists()) {
            target.createNewFile();
        }
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            // 采用zeroCopy技术拷贝
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
            out.force(false);
        } finally {
            Closeables.close(inStream);
            Closeables.close(in);
            Closeables.close(outStream);
            Closeables.close(out);
        }
    }

    /**
     * 流拷贝
     *
     * @param is     输入
     * @param os     输出
     * @param length 长度
     * @throws java.io.IOException
     */
    public static void copy(InputStream is, OutputStream os, long length) throws IOException {
        copy(is, os, 0, length);
    }

    /**
     * 流拷贝
     *
     * @param is       输入
     * @param os       输出
     * @param srcStart 源起始位置
     * @param length   长度
     * @throws java.io.IOException
     */
    public static void copy(InputStream is, OutputStream os, long srcStart, long length) throws IOException {
        if (is == null || os == null) {
            return;
        }

        if (is.getClass() == FileInputStream.class && os.getClass() == FileOutputStream.class) {
            FileChannel in = null;
            FileChannel out = null;
            try {
                in = ((FileInputStream) is).getChannel();
                out = ((FileOutputStream) os).getChannel();
                in.transferTo(srcStart, length, out);
                out.force(false);
            } finally {
                Closeables.close(in);
                Closeables.close(out);
            }
        } else {
            long bytes = 0;
            if (srcStart > 0) {
                bytes = is.skip(srcStart);
                if (bytes < srcStart) {
                    return;
                }
            }
            byte buffer[] = new byte[1024 * 4];
            int c = 0;
            bytes = 0;
            while (bytes < length && ((c = is.read(buffer, 0, (int) Math.min(buffer.length, length - bytes))) >= 0)) {
                os.write(buffer, 0, c);
                bytes += c;
            }
        }

    }

    /**
     * 流拷贝
     *
     * @param is     输入
     * @param os     输出
     * @param length 长度
     * @throws java.io.IOException
     */
    public static void copy(InputStream is, DataOutput os, long length) throws IOException {
        if (is == null || os == null) {
            return;
        }
        byte buffer[] = new byte[1024 * 4];
        int c = 0;
        long pos = 0;
        while (pos < length && ((c = is.read(buffer, 0, (int) Math.min(buffer.length, length - pos))) >= 0)) {
            os.write(buffer, 0, c);
            pos += c;
        }
    }

    /**
     * move文件
     *
     * @param from 源文件
     * @param to   目标文件
     * @return 成功标示
     * @throws IOException
     */
    public static boolean move(File from, File to) throws IOException {
        if (from == null || to == null || !from.exists()) {
            return false;
        }

        // If a simple rename/mv does not work..
        // 删除目标文件
        to.delete();
        // 重命名
        if (!from.renameTo(to)) {
            // 不成功则采用流复制
            copy(from, to);
            // 删除
            from.delete();
        }
        return true;
    }

    /**
     * 递归删除目录和文件
     *
     * @param root
     */
    public static void deleteDirectory(File root) {
        if (root == null || !root.exists()) {
            return;
        }
        if (root.isFile()) {
            root.delete();
        } else if (root.isDirectory()) {
            File[] children = root.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isFile()) {
                        child.delete();
                    } else if (child.isDirectory()) {
                        deleteDirectory(child);
                    }
                }
            }
            root.delete();
        }
    }

    /**
     * 创建目录
     *
     * @param directory
     * @return
     */
    public static boolean createDirectory(File directory) {
        if (directory == null) {
            return false;
        }
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                if (!directory.exists()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 创建文件
     *
     * @param file 文件
     * @return 成功标示
     */
    public static boolean createFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        try {
            if (file.createNewFile()) {
                return true;
            }
            return file.exists();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 写文件
     *
     * @param file    文件
     * @param content 消息
     * @throws IOException
     */
    public static void write(File file, String content) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.newLine();
        } finally {
            Closeables.close(writer);
        }
    }

    /**
     * 读文件内容
     *
     * @param file 文件
     * @return 文件内容
     */
    public static String read(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (file == null || !file.exists() || file.length() < 1) {
            return builder.toString();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count > 0) {
                    builder.append("\n");
                }
                builder.append(line);
                count++;
            }
        } catch (FileNotFoundException e) {
            //忽略文件不存在
        } finally {
            Closeables.close(reader);
        }
        return builder.toString();
    }

}
