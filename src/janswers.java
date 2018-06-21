import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class janswers extends Application {
    private boolean locked = true;
    private boolean stopped = false;
    public final String[] proposals = {"Jimmy, please answers", "Jimmy", "Jim", "Jim, please answer",
            "Jimmy, I have a question for you", "Jim, I have a question for you"};
    private final String DEFAULT = "Hmmm... That is an interesting question that I will have to explore more into...";
    private String answer = "";
    private TextField question = new TextField();
    private TextField propose = new TextField();
    private Button submit = new Button("Submit!");

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane window = new BorderPane();
        VBox cntr = new VBox();
        cntr.setFillWidth(false);
        propose.setPromptText("Make a proposal!");
        propose.setPrefColumnCount(20);
        question.setPromptText("Ask a question!");
        question.setPrefColumnCount(20);
        Label output = new Label();
        Label title = new Label("Jimmy Answers!");
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextFill(Paint.valueOf("WHITE"));
        title.setFont(new Font("Bodoni 72 Oldstyle Book Italic", 60));
        output.setAlignment(Pos.CENTER);
        output.setTextAlignment(TextAlignment.CENTER);
        output.setTextFill(Paint.valueOf("WHITE"));
        output.setFont(new Font("Bodoni 72 Bold", 40));
        output.setWrapText(true);
        submit.setDisable(true);
        propose.textProperty().addListener((observable, oldValue, newValue) -> {
            enable();
            if (!stopped) {
                if (locked) {
                    if ((newValue.length() == 1) && (newValue.charAt(0) == '.')) {
                        locked = false;
                        propose.setText(oldValue + proposals[4].charAt(newValue.length() - 1) + "\n");
                    }
                } else {
                    if (oldValue.length() < newValue.length()) {
                        answer = answer + newValue.charAt(newValue.length() - 1);
                        propose.setText(oldValue + proposals[4].charAt(newValue.length() - 1) + "\n");
                        if (newValue.charAt(newValue.length() - 1) == '.') {
                            stopped = true;
                        }
                    } else if (oldValue.length() > newValue.length()) {
                        answer = answer.substring(0, answer.length() - 1);
                    }
            }
        } else if (oldValue.length() > newValue.length()) {
                if (answer.length()  == newValue.length()) {
                    answer = answer.substring(0, answer.length() - 1);
                    stopped = false;
                }
        }});
        question.textProperty().addListener((observable, oldValue, newValue) -> {
                    enable();
        });
        submit.setOnAction(event -> {
            if (answer.equals("")) {
                answer = DEFAULT;
            } else {
                answer = answer.substring(0, answer.length() - 1);
            }
            output.setText("JIMMY: " + answer);
            locked = true;
            stopped = false;
            answer = "";
            propose.setText("");
            question.setText("");
        });
        cntr.getChildren().add(propose);
        cntr.getChildren().add(question);
        cntr.getChildren().add(submit);
        cntr.setAlignment(Pos.CENTER);
        window.setTop(title);
        window.setAlignment(title, Pos.CENTER);
        window.setAlignment(output, Pos.CENTER);
        window.setPadding(new Insets(80, 0, 50, 0));
        window.setCenter(cntr);
        window.setBottom(output);
        Scene scene = new Scene(window, 600, 400);
        window.setBackground(new Background(new BackgroundImage(new Image("fire.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
        stage.setScene(scene);
        stage.show();
        window.requestFocus();
    }
    public void enable() {
        submit.setDisable(!((question.getLength() > 0) && (propose.getLength() > 0)));
    }
}