package cn.lite.flow.common.utils;

import cn.lite.flow.common.model.Tuple;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

/**
 * Created by luya on 2018/11/16.
 */
public class FileUtils {

    private static Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static Tuple<Integer, String> readFile(String filePath, long offset, int length) {
        Tuple<Integer, String> result = new Tuple(0, "");
        FileInputStream fis = null;
        InputStream is = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            fis.skip(offset - 1);       //文件指向前一字节

            is = new BufferedInputStream(fis);
            StringBuilder data = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                if (len < length) {
                    data.append(new String(buffer));
                    length -= len;
                    continue;
                }
                data.append(new String(Arrays.copyOf(buffer, length)));
                break;
            }
            result.setA(data.toString().getBytes().length);
            result.setB(data.toString());
        } catch (FileNotFoundException e) {
            LOG.error("file not found error!filePath:{}", filePath, e);
        } catch (Exception e) {
            LOG.error("read file error!filePath:{}, offset:{}, length:{}", filePath, offset, length, e);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(is);
        }
        return result;
    }

    public static void main(String[] args) {
        String filePath = "log/1";
        long offset = 1;
        int length = 200;
        Tuple<Integer, String> result = readFile(filePath, 1, 200);
        StringBuilder log = new StringBuilder();
        while (result.getA() > 0) {
            log.append(result.getB());
            offset += 200;
            result = readFile(filePath, offset, length);
        }
        if (log != null) {

        }
    }
}
