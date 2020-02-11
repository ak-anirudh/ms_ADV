package adv.main;

import adv.panels.*;
import adv.utility.AppDimensions;
import edu.usfca.xj.appkit.frame.XJWindow;
import edu.usfca.xj.appkit.menu.XJMainMenuBar;
import edu.usfca.xj.appkit.menu.XJMenu;
import edu.usfca.xj.appkit.menu.XJMenuItem;
import edu.usfca.xj.appkit.menu.XJMenuItemDelegate;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends XJWindow implements XJMenuItemDelegate {

    private static final int MENUITEM_SORTING_ALGORITHMS = 201;
    private static final int MENUITEM_GRAPH_ALGORITHMS = 202;
    private static final int MENUITEM_TREE_ALGORITHMS = 203;
    private static final int MENUITEM_HEAP_ALGORITHMS = 204;
    private static final int MENUITEM_HASH_ALGORITHMS = 205;

    protected JTabbedPane viewTabbedPane;
    protected BubbleSortPanel bubbleSortPanel;
    protected DepthFirstSearchPanel depthFirstSearchPanel;
    protected BreadthFirstSearchPanel breadthFirstSearchPanel;
    protected TopologicalSortPanel topologicalSortPanel;
    protected AVLTreePanel avlTreePanel;
    protected BSTreePanel bsTreePanel;
    protected HeapPanel heappanel;
    protected HashPanel hashpanel;

    public Window() {

        setWindowSize();
        // setLookAndFeel();

        viewTabbedPane = new JTabbedPane();
        viewTabbedPane.setTabPlacement(JTabbedPane.LEFT);

        bubbleSortPanel = new BubbleSortPanel(this);

        depthFirstSearchPanel = new DepthFirstSearchPanel(this);
        breadthFirstSearchPanel = new BreadthFirstSearchPanel(this);
        topologicalSortPanel = new TopologicalSortPanel(this);

        avlTreePanel = new AVLTreePanel(this);
        bsTreePanel = new BSTreePanel(this);

        heappanel = new HeapPanel(this);

        hashpanel = new HashPanel(this);

        //viewTabbedPane.add("AVL Tree",avlTreePanel);
        getContentPane().add(viewTabbedPane);
        pack();
    }

    private void setLookAndFeel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
                } catch (UnsupportedLookAndFeelException e) {

                }
                UIManager.getLookAndFeelDefaults().put("ClassLoader",
                        SubstanceBusinessBlackSteelLookAndFeel.class.getClassLoader());
            }

        });
    }

    private void setWindowSize() {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        r.width *= 0.80f;
        r.height *= 0.80f;
        //jFrame.pack();
        getRootPane().setPreferredSize(r.getSize());


    }

    public void setData(Object dataForKey) {

    }

    public Object getData() {
        return null;
    }

    // Adding additional menu between Algorithms and Window Menu
    @Override
    public void customizeMenuBar(XJMainMenuBar menubar) {
        // Add no custom menus
    }

    @Override
    public void customizeEditMenu(XJMenu menu) {
        removeRedundantDeafults(menu);

        menu.setTitle("Algorithms");
        menu.addItem(new XJMenuItem("Sorting Algortihms", KeyEvent.VK_1, MENUITEM_SORTING_ALGORITHMS, this));
        menu.addItem(new XJMenuItem("Graph Algorithms", KeyEvent.VK_2, MENUITEM_GRAPH_ALGORITHMS, this));
        menu.addItem(new XJMenuItem("Tree Algorithms", KeyEvent.VK_3, MENUITEM_TREE_ALGORITHMS, this));
        menu.addItem(new XJMenuItem("Heaps", KeyEvent.VK_4, MENUITEM_HEAP_ALGORITHMS, this));
        menu.addItem(new XJMenuItem("Hash Tables", KeyEvent.VK_5, MENUITEM_HASH_ALGORITHMS, this));

    }

    // Remove the default contents of Edit Menu
    public void removeRedundantDeafults(XJMenu menu) {
        menu.removeAllItems();
    }

    public void handleMenuEvent(XJMenu menu, XJMenuItem item) {
        super.handleMenuEvent(menu, item);

        switch (item.getTag()) {

            case MENUITEM_SORTING_ALGORITHMS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Bubble Sort", bubbleSortPanel);
                break;
            case MENUITEM_GRAPH_ALGORITHMS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Depth First Search", depthFirstSearchPanel);
                viewTabbedPane.add("Breadth First Search", breadthFirstSearchPanel);
                viewTabbedPane.add("Topological Sort", topologicalSortPanel);
                break;
            case MENUITEM_TREE_ALGORITHMS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("AVL Tree",avlTreePanel);
                viewTabbedPane.add("Binary Search Tree",bsTreePanel);
                break;
            case MENUITEM_HEAP_ALGORITHMS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Heaps",heappanel);
                break;
            case MENUITEM_HASH_ALGORITHMS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Hash Tables",hashpanel);
                break;
       }

    }

}
