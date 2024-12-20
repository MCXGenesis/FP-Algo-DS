package ConnectFour;

import java.awt.*;

public class Cell {
    public static final int SIZE = 80;
    public Seed content;

    public Cell() {
        reset();
    }

    public void reset() {
        content = Seed.EMPTY;
    }

    public void paint(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, SIZE, SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, SIZE, SIZE);

        if (content == Seed.RED) {
            g.setColor(Color.RED);
            g.fillOval(x + 10, y + 10, SIZE - 20, SIZE - 20);
        } else if (content == Seed.YELLOW) {
            g.setColor(Color.YELLOW);
            g.fillOval(x + 10, y + 10, SIZE - 20, SIZE - 20);
        }
    }
}
