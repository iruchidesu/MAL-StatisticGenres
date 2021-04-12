package mal.view;

import mal.Controller;
import mal.vo.AnimeTitle;

import java.util.List;

public interface View {
    void update(List<AnimeTitle> urls);
    void setController(Controller controller);
}
