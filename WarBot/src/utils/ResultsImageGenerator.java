package utils;

import gui.ava.html.image.generator.HtmlImageGenerator;
import models.Player;

import java.io.File;
import java.util.List;

public class ResultsImageGenerator {

    public static void generate(List<Player> players, String outputPath) {
        final int MAX_LINES = 40;
        int columnsDivision = (players.size() > MAX_LINES) ? 6 : 3;
        final String DEFAULT_STYLE = "font-family:Comic Sans MS;font-size:18px;";
        final String DEAD_STYLE = DEFAULT_STYLE + "background-color:#fc3d3d;";
        String style;

        int linesCont = 1;
        int cont = 1;

        StringBuilder html = new StringBuilder();
        html.append("<table><tr><td><table>");
        for (Player player : players) {
            style = player.isDead() ? DEAD_STYLE : DEFAULT_STYLE;
            html.append("<tr><td style='").append(style).append("'>")
                    .append(player.getName())
                    .append("</td></tr>");
            if (linesCont == MAX_LINES || linesCont == players.size() / columnsDivision) {
                linesCont = 0;
                html.append("</table></td>");
                if (cont < players.size()) {
                    html.append("<td><table>");
                }
            }
            cont++;
            linesCont++;
        }
        html.append("</tr></table>");
        generateImageFromHtml(html.toString(), outputPath);
    }

    private static void generateImageFromHtml(String html, String outputPath) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(new File(outputPath));
    }
}
