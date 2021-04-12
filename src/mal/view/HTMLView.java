package mal.view;

import mal.Controller;
import mal.vo.AnimeTitle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLView implements View{
    private Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("[.]", "/") + "/statistic.html";

    @Override
    public void update(List<AnimeTitle> urls) {
        try {
            String newContent = getUpdatedFileContent(urls);
            updateFile(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUrlsEmu() {
        controller.onGetUrls("https://myanimelist.net/animelist/iruchi?status=2&tag=");
    }

    private String getUpdatedFileContent(List<AnimeTitle> urls) {
        try {
            Map<String, Integer> statistic = new HashMap<>();
            for (String s : controller.getModel().getGenresSet()) {
                statistic.put(s, 0);
            }
            Document document = getDocument();
            Elements templates = document.getElementsByClass("title template");
            Element template = templates.clone().removeAttr("style").removeClass("template").get(0);

            Elements prevTitle = document.getElementsByClass("title");
            for (Element element : prevTitle) {
                if (!element.hasClass("template"))
                    element.remove();
            }

            for (AnimeTitle animeTitle : urls) {
                Element titleElement = template.clone();

                Element titleLink = titleElement.getElementsByAttribute("href").get(0);
                titleLink.appendText(animeTitle.getTitle());
                titleLink.attr("href", "https://myanimelist.net" + animeTitle.getLink());

                Element titleGenres = titleElement.getElementsByClass("genres").get(0);
                for (String s : animeTitle.getGenres()) {
                    titleGenres.appendText(s + " ");
                    statistic.put(s, statistic.get(s) + 1);
                }
                templates.before(titleElement.outerHtml());
            }

            Elements templatesG = document.getElementsByClass("genre template");
            Element templateGenre = templatesG.clone().removeAttr("style").removeClass("template").get(0);

            Elements prevGenres = document.getElementsByClass("genre");
            for (Element element : prevGenres) {
                if (!element.hasClass("template"))
                    element.remove();
            }

            for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
                Element genreElement = templateGenre.clone();

                Element element = genreElement.getElementsByClass("genre1").get(0);
                element.appendText(entry.getKey());
                Element countElement = genreElement.getElementsByClass("count").get(0);
                countElement.appendText(entry.getValue().toString());

                templatesG.before(genreElement.outerHtml());
            }
            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void updateFile(String content) {
        File file = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
