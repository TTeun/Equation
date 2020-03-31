import eqn.Eqn;
import eqn.parser.EqnParser;
import eqn.parser.exception.EqnException;

public class Main {
    public static void main(String[] args) {
        try {
            Eqn equation = EqnParser.parseEquation("f(x,y) = x + y + z +j");
        } catch (EqnException e) {
            System.out.println(e.toString());
        }
//        launch(args);
    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle("GoPheR");
//        StackPane layout = new StackPane();
//
//        Scene scene = new Scene(layout, 300, 250);
//        stage.setScene(scene);
//        stage.show();
//
//    }
}
