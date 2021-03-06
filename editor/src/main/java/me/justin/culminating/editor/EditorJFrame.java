package me.justin.culminating.editor;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import me.justin.culminating.Input;

/**
 * Created by justin on 25/04/15.
 */
public class EditorJFrame extends JFrame {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            //http://forum.worldwindcentral.com/showthread.php?11051-Menu-Bar-is-hidden-behind-Canvas
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private EditorApplication editor;

    private LwjglAWTCanvas lwjglAWTCanvas;
    private EditorPropertiesComponent editorPropertiesComponent;
    private EditorToolbarComponent paletteComponent;
    private EditorEntityListComponent entityListComponent;

    // https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests-lwjgl/src/com/badlogic/gdx/tests/lwjgl/SwingLwjglTest.java
    public EditorJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        menuBar.add(makeMenu("File", new String[] {"Open"},
                new ActionListener[] {
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                editorPropertiesComponent.changeObject(new TestEditorObject());
                            }
                        }
         }));

        setJMenuBar(menuBar);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        editor = new EditorApplication(new Input.DefaultInput());

        lwjglAWTCanvas = new LwjglAWTCanvas(editor);
        Canvas canvas = lwjglAWTCanvas.getCanvas();
        container.add(canvas, BorderLayout.CENTER);

        paletteComponent = new EditorToolbarComponent(this, editor);
        container.add(paletteComponent, BorderLayout.NORTH);

        editorPropertiesComponent = new EditorPropertiesComponent(editor);
        entityListComponent = new EditorEntityListComponent(editor);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.add(entityListComponent);
        sidebar.add(editorPropertiesComponent);
        container.add(sidebar, BorderLayout.EAST);

        pack();
        setSize(800, 600);
        setVisible(true);

        //Centre on screen: http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-the-monitor-resolution
        setLocationRelativeTo(null);

        canvas.requestFocus();
    }

    private static JMenu makeMenu(String menuName, String[] names, ActionListener[] actions) {
        JMenu menu = new JMenu(menuName);

        for (int i=0; i<names.length && i < actions.length; i++) {
            JMenuItem item = new JMenuItem(names[i]);
            item.addActionListener(actions[i]);
            menu.add(item);
        }

        return menu;
    }
}
