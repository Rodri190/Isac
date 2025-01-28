package Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.awt.Font;

public class Fuente {
    private Map<String, Font> fonts;

    public Fuente() {
        fonts = new HashMap<>();
    }

    public Font getFuente(String tipoFuente, int tamanio) {
        Font fuente = null;
        String tamanioString = "" + tamanio;
        try {
            fonts.put("Regular", Font.createFont(Font.TRUETYPE_FONT, new File("src/Resources/Fonts/Roboto-Regular.ttf")));
            fonts.put("Bold", Font.createFont(Font.TRUETYPE_FONT, new File("src/Resources/Fonts/Roboto-ExtraBold.ttf")));
            fonts.put("Italic", Font.createFont(Font.TRUETYPE_FONT, new File("src/Resources/Fonts/Roboto-Italic.ttf")));

            switch (tipoFuente) {
                case "normal":
                    fuente = fonts.get("Regular").deriveFont(Float.parseFloat(tamanioString));
                    break;
                case "negrita":
                    fuente = fonts.get("Bold").deriveFont(Float.parseFloat(tamanioString));
                    break;
                case "cursiva":
                    fuente = fonts.get("Italic").deriveFont(Float.parseFloat(tamanioString));
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fuente;
    }

}
