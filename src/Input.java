import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener {
    private final ArrayList<KeyEvent> keyEvents;

    public Input() {
        keyEvents = new ArrayList<KeyEvent>();
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    public synchronized void keyPressed(KeyEvent e) {
        keyEvents.add(e);
    }

    public synchronized ArrayList<KeyEvent> getKeyEvents() {
        ArrayList<KeyEvent> events = new ArrayList<KeyEvent>(keyEvents);
        keyEvents.clear();
        return events;
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
    }
}
