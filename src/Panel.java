import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Components.UI.GradientButton;

public class Panel extends JPanel{
    
    public Panel(){
        initPanel();
    }

    private void initPanel(){
        setBounds(0, 0, 600, 600);
        setLayout(null);
        addgradientButton();
        // setBackground(Color.decode("#191c31"));
    }

    private void addgradientButton(){
        add(new GradientButton("Start", 50, 50, Color.decode("#ff0000"), 200, 75, Color.decode("#0000ff"), Color.decode("#ffffff")));
    }

    // @Override
    // public void paintComponent(Graphics g){
    //     super.paintComponent(g);
    //     Graphics2D g2d = (Graphics2D) g;

    //     GradientPaint gradientColor = new GradientPaint(
    //         0, 0, Color.red,
    //         getWidth(), getHeight(), Color.blue
    //     );

    //     g2d.setPaint(gradientColor);

    //     g2d.fillRect(0, 0, getWidth(), getHeight());

    // }
}
