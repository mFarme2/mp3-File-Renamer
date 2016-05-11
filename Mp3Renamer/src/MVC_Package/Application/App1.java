package MVC_Package.Application;

import MVC_Package.Controller.MP3NameChangerController;
import MVC_Package.View.MP3NameChangerView;
import MVC_Package.Model.MP3NameChangerModel;

import javax.swing.SwingUtilities;

/**
 * Created by Morgan Farmer on 5/1/2016.
 */
public class App1 {
    private static MP3NameChangerController controller;
    private static MP3NameChangerModel model;
    private static MP3NameChangerView view;

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){

                controller = new MP3NameChangerController();
                model = new MP3NameChangerModel(controller);
                view = new MP3NameChangerView(controller);

                view.setVisible(true);
                controller.setModelAndView(view, model);
                model.update();

            }
        });
    }
}
