package cn.medcn.transfer.utils;

import cn.medcn.transfer.Constants;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/14.
 */
public class FileUtils {

    private static final String FILE_BASE_KEY = "file.base";

    private static final String FILE_BASE_FILE_NAME = "file_base.properties";

    private static final String HEAD_IMG_BASE = "headimg/";

    private static final String COURSE_BASE = "course/";

    private static final String MEET_MATERIAL_BASE = "material/";

    private static final String GOODS_IMG_BASE = "goods/image/";

    private static final String GOODS_BOOK_BASE = "goods/book/";

    protected static final String DATA_FILE_BASE = "data/";


    public static String saveMedicineFile(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+DATA_FILE_BASE+"/"+ Constants.MEDICINE_MANUAL_ROOT_CATEGORY_ID+"/", blob, suffix);
        return DATA_FILE_BASE+ Constants.MEDICINE_MANUAL_ROOT_CATEGORY_ID+"/"+fileName;
    }

    public static String saveClinicalGuide(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+DATA_FILE_BASE+"/"+ Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID+"/", blob, suffix);
        return DATA_FILE_BASE+ Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID+"/"+fileName;
    }




    public static String saveMaterial(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+MEET_MATERIAL_BASE, blob, suffix);
        return MEET_MATERIAL_BASE+fileName;
    }

    /**
     * 保存头像
     * @param blob
     * @param suffix
     * @throws IOException
     * @throws SQLException
     */
    public static String saveHeadImg(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+HEAD_IMG_BASE, blob, suffix);
        return HEAD_IMG_BASE+fileName;
    }


    public static String savePPTImg(Blob blob, Integer courseId, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+COURSE_BASE+courseId+"/ppt/", blob, suffix);
        if(fileName == null){
            LogUtils.warm(FileUtils.class, "转换文件到文件系统失败 !!!");
            return null;
        }
        return COURSE_BASE+courseId+"/ppt/"+fileName;
    }

    public static String savePPTAudio(Blob blob, Integer courseId, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+COURSE_BASE+courseId+"/audio/", blob, suffix);
        if(fileName == null){
            LogUtils.warm(FileUtils.class, "转换文件到文件系统失败 !!!");
            return null;
        }
        return COURSE_BASE+courseId+"/audio/"+fileName;
    }

    public static String saveGoodsImg(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+GOODS_IMG_BASE, blob, suffix);
        if(fileName == null){
            LogUtils.warm(FileUtils.class, "转换文件到文件系统失败 !!!");
            return null;
        }
        return GOODS_IMG_BASE+fileName;
    }

    public static String saveGoodsBook(Blob blob, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+GOODS_BOOK_BASE, blob, suffix);
        if(fileName == null){
            LogUtils.warm(FileUtils.class, "转换文件到文件系统失败 !!!");
            return null;
        }
        return GOODS_BOOK_BASE+fileName;
    }



    public static String savePPTVideo(Blob blob, Integer courseId, String suffix) throws IOException, SQLException {
        String fileBase = PropertyUtils.readPropFromCache(FILE_BASE_FILE_NAME, FILE_BASE_KEY);
        String fileName = blobToFile(fileBase+COURSE_BASE+courseId+"/video/", blob, suffix);
        if(fileName == null){
            LogUtils.warm(FileUtils.class, "转换文件到文件系统失败 !!!");
            return null;
        }
        return COURSE_BASE+courseId+"/video/"+fileName;
    }

    public static String blobToFile(String fileDir, Blob blob, String suffix) throws SQLException, IOException {
        if (blob != null) {
            InputStream is = blob.getBinaryStream();
            String fileName = StringUtils.getNowStringID() + suffix;
            File file = new File(fileDir + fileName);
            File dir = new File(fileDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            OutputStream os = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            is.close();
            return fileName;
        }
        return null;
    }

    public static void copyFile(File source, File dest){
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e){

        }finally {
            try {
                inputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean isLeaf(File dir){
        boolean isLeaf = false;
        for (File file : dir.listFiles()){
            if (file.isFile()){
                isLeaf = true;
                break;
            }
        }
        return isLeaf;
    }


    public static float getFileKSize(String filePath){
        File file = new File(filePath);
        return file.length() / 1024;
    }

}
