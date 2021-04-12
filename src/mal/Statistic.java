package mal;

import mal.model.Model;
import mal.view.HTMLView;

public class Statistic {
    public static void main(String[] args) {
        HTMLView view = new HTMLView();
        Model model = new Model(view);
        Controller controller = new Controller(model);

        view.setController(controller);
        view.getUrlsEmu();
    }
}
