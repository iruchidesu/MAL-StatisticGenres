package mal.vo;

import java.util.List;
import java.util.Objects;

public class AnimeTitle {
    private String title;
    private String link;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeTitle that = (AnimeTitle) o;
        return Objects.equals(title, that.title) && Objects.equals(link, that.link) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, genres);
    }
}
