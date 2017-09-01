package cn.medcn.article.jobs;

import cn.medcn.article.service.NewsService;
import org.jdom.JDOMException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by lixuan on 2017/2/13.
 */
public class UpdateNewsThread implements Runnable {
    private NewsService newsService;

    public UpdateNewsThread(NewsService newsService){
        this.newsService = newsService;
    }

    @Override
    public void run() {
        try {
            newsService.updateNews();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
