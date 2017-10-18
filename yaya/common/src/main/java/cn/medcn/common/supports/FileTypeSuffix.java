package cn.medcn.common.supports;

/**
 * Created by lixuan on 2017/7/19
 */
public enum FileTypeSuffix {

    IMAGE_SUFFIX_JPG("jpg"),
    IMAGE_SUFFIX_PNG("png"),
    IMAGE_SUFFIX_GIF("gif"),
    IMAGE_SUFFIX_JPEG("jpeg"),

    AUDIO_SUFFIX_MP3("mp3"),
    AUDIO_SUFFIX_WAV("wav"),
    AUDIO_SUFFIX_AAC("aac"),
    AUDIO_SUFFIX_AMR("amr"),

    VIDEO_SUFFIX_MP4("mp4"),

    EXCEL_SUFFIX_XLSX("xlsx"),
    EXCEL_SUFFIX_XLS("xls"),

    WORD_SUFFIX_DOC("doc"),
    WORD_SUFFIX_DOCX("docx"),

    PPT_SUFFIX_PPT("ppt"),
    PPT_SUFFIX_PPTX("pptx"),

    PDF_SUFFIX("pdf");

    public String suffix;

    FileTypeSuffix(String suffix){
        this.suffix = suffix;
    }

}
