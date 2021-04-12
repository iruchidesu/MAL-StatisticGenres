package mal;

import mal.model.Model;

public class Controller {
    private Model model;

    public Controller(Model model) {
        if (model == null) throw new IllegalArgumentException();
        this.model = model;
    }

    public void onGetUrls(String url) {
        model.getListURL(url);
        //model.test();
    }

    public Model getModel() {
        return model;
    }
}
