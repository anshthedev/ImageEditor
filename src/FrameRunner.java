import javax.swing.JFrame;

public class FrameRunner {
  public static void main(String[] args) {
    JFrame f = new JFrame("Porche 911 GT3 RS"); 
    ImageEditorPanel p = new ImageEditorPanel();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(p);
    f.pack();
    p.requestFocusInWindow();
    f.setVisible(true);
    p.run();
  }
}