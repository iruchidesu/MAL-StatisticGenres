package mal.model;

import mal.view.View;
import mal.vo.AnimeTitle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {
    private View view;
    private Set<String> genresSet = new HashSet<>();

    public Model(View view) {
        if (view == null) throw new IllegalArgumentException();
        this.view = view;
    }

    public void getListURL(String url) {
        List<String> urls = new ArrayList<>();

        try {
            //Document doc = getDocument(url, "https://myanimelist.net/animelist/iruchi");
            Document doc = getDocument();

            Elements urlsHtmlList = doc.getElementsByClass("animetitle");
            for (Element element : urlsHtmlList) {
                String link = element.attributes().get("href");
                urls.add(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getTitle(urls);
    }

    public void getTitle(List<String> urls) {
        List<AnimeTitle> titleList = new ArrayList<>();
        int i = 1;
        try {
            for (String url : urls) {
                System.out.print(i + (i % 35 == 0 ? "...\n" : "..."));
                Document doc = getDocument("https://myanimelist.net" + url, "https://myanimelist.net/animelist/iruchi?status=2&tag=");
                Element titleName = doc.getElementsByClass("title-name h1_bold_none").get(0);
                Elements genres = doc.getElementsByAttributeValue("itemprop", "genre");

                List<String> genresList = new ArrayList<>();
                for (Element genre : genres) {
                    genresList.add(genre.text());
                    genresSet.add(genre.text());
                }

                AnimeTitle title = new AnimeTitle();
                title.setTitle(titleName.text());
                title.setLink(url);
                title.setGenres(genresList);

                titleList.add(title);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.update(titleList);
    }

    public void test() {
        List<AnimeTitle> list = new ArrayList<>();
        AnimeTitle t = new AnimeTitle();
        t.setTitle("Air");
        t.setLink("/anime/4546/Air/");
        List<String> g = new ArrayList<>();
        g.add("Comedy");
        g.add("Romance");
        genresSet.add("Comedy");
        genresSet.add("Romance");
        t.setGenres(g);
        list.add(t);
        view.update(list);
    }

    protected Document getDocument(String url, String referrer) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0")
                .referrer(referrer)
                .get();
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File("./src/mal/view/list.html"), "UTF-8");
    }

    public Set<String> getGenresSet() {
        return genresSet;
    }
}
