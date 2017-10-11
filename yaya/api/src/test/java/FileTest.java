import cn.jiguang.common.utils.StringUtils;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.service.DataFileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.medcn.common.utils.UUIDUtil.getNowStringID;

/**
 * Created by LiuLP on 2017/8/23/023.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class FileTest {

    @Autowired
    private DataFileService dataFileService;
    @Test
    public void test1() throws IOException {
        File category = new File("C:\\Users\\Administrator\\Desktop\\Disease Briefings 2016 PDFs");
        String path = "C:\\Users\\Administrator\\Desktop\\Disease Briefings 2016 PDFs\\";
        for(File file:category.listFiles()){
            if(file.getName().endsWith(".html")){
                String filename = file.getName().substring(0,file.getName().lastIndexOf("."));
                Document doc = Jsoup.parse(file, "UTF-8");
                List<String> list = new ArrayList<>();
                Elements elements = doc.getElementsByTag("img");
                for(Element element:elements){
                    String img = element.attr("src");
                    list.add(img);
                }

                Elements e2 = doc.getElementsByTag("EMBED");
                for(Element element:e2){
                    String img = element.attr("src");
                    list.add(img);
                }

                Elements e3 = doc.getElementsByTag("PARAM");
                for(Element element:e3){
                    String img = element.attr("VALUE");
                    if(img.contains(".jpg") || img.contains(".swf") || img.contains(".gif")||img.contains(".png")){
                        list.add(img);
                    }
                }



                for(String s :list){
                    String fileName = s.substring(s.lastIndexOf("/")+1);
                    String urlPath = "https://integrity.thomson-pharma.com"+ s;
                    String saveDir = "F:/img/";
                    s = s.substring(0,s.lastIndexOf("/"));
                    String[] arg = s.split("/");
                    for(String s1:arg){
                        if(!StringUtils.isEmpty(s1)){
                            saveDir += s1+"/";
                        }
                    }
                    FileUtils.downloadNetWorkFile(urlPath,saveDir,fileName);

                }

                File pdf = new File(path+filename+".pdf");
                Elements e4 = doc.getElementsByTag("title");
                String title = e4.text();
                String id = getNowStringID();
                int a = (int)(pdf.length()/1024);
                float fileSize = (float)a;
                DataFile dataFile = new DataFile();
                dataFile.setId(id);
                dataFile.setCategoryId("17051816403418566342");
                dataFile.setTitle(title);
                dataFile.setAuthor("汤森路透");
                dataFile.setFilePath("data/17051816403418566342/"+id+".pdf");
                dataFile.setFileSize(fileSize);
                dataFile.setUpdateDate(new Date());
                dataFile.setRootCategory("17051816403418566342");
                dataFile.setDataFrom("汤森路透");
                dataFile.setAuthed(true);
                dataFile.setHtmlPath("data/17051816403418566342/"+id+".html");
                dataFileService.insert(dataFile);
                file.renameTo(new File(path+id+".html"));
                pdf.renameTo(new File(path+id+".pdf"));

            }

        }

    }






}
