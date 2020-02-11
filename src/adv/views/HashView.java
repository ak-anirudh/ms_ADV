package adv.views;

/**
 * Created by santosh on 31-Jul-16.
 */

import java.awt.*;

import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;
import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.ds.shapes.DSShapeSingleLLU;
import edu.usfca.xj.appkit.gview.base.Vector2D;



    public class HashView extends View {


        public final static int INSERT = 1;
        public final static int DELETE = 2;
        public final static int FIND = 3;
        public final static int HASHINTEGER = 4;
        protected final int ELEMENT_WIDTH = 75;
        protected final int ELEMENT_HEIGHT = 50;
        protected final int ARRAY_ELEMENT_WIDTH = 80;
        protected final int ARRAY_ELEMENT_HEIGHT = 30;


        protected final int NUMSTEPS = 25;
        protected  int HASHTABLE_SIZE = 12;

        protected long hashvalue;

        protected DSShapeNullPointer frame[];
        protected HashElement HashList[];
        protected GElementLabel index[];
        protected int X_position[];
        protected final int Y_position = 400;

        protected final int Y_SPACING = ELEMENT_HEIGHT + 15;
        protected final int INITIAL_Y = Y_position - Y_SPACING;

        protected GElementLabel elementLabel;
        protected GElementLabel elementIndexLabel;

        protected void runAlgorithm(int function) {
            switch (function) {
                case HASHINTEGER:
                    deleteElements();
                    break;
                 }
        }

        protected void runAlgorithm(int function, Object parameter) {
            switch (function) {

                case INSERT:
                        insert(((Integer) parameter).intValue());
                    break;
                case DELETE:
                        delete(((Integer) parameter).intValue());
                    break;
                case FIND:
                        find(((Integer) parameter).intValue());
                    break;

            }
        }

        public HashView() {

            int i;
            HASHTABLE_SIZE = 12;

            X_position = new int[HASHTABLE_SIZE];
            HashList = new HashElement[HASHTABLE_SIZE];
            index = new GElementLabel[HASHTABLE_SIZE];
            frame = new DSShapeNullPointer[HASHTABLE_SIZE];

            for (i = 0; i < HASHTABLE_SIZE; i++) {
                X_position[i] = i * ARRAY_ELEMENT_WIDTH + 2 * ARRAY_ELEMENT_WIDTH / 3;
                frame[i] = createNullPointer(X_position[i], Y_position, ARRAY_ELEMENT_WIDTH, ARRAY_ELEMENT_HEIGHT);
                index[i] = createLabel(String.valueOf(i), X_position[i], Y_position + ARRAY_ELEMENT_HEIGHT / 2 + 12);
                index[i].setLabelColor(Color.BLUE);
                HashList[i] = null;
            }

        }

        private void colorlabel(int ht_index) {
            elementIndexLabel.setLabelColor(Color.RED);
            this.index[ht_index].setLabelColor(Color.RED);
            repaintwait();
            this.index[ht_index].setLabelColor(Color.BLUE);
            elementIndexLabel.setLabelColor(Color.BLACK);
            elementLabel.setLabelColor(Color.RED);

        }

        protected void insertinto(int insert_index, String key) {
            colorlabel(insert_index);

            HashView.HashElement InsertElement = new HashElement();
            InsertElement.next = HashList[insert_index];
            InsertElement.element = createSingleLinkedListRectU(key, 300, 40, ELEMENT_WIDTH, ELEMENT_HEIGHT);
            InsertElement.element.setPointerVoid(true);
            repaintwait();
            if (HashList[insert_index] == null) {
                frame[insert_index].setNull(false);
            } else {
                InsertElement.element.setPointerVoid(false);
                removeLink(frame[insert_index], HashList[insert_index].element);
                GLink link2 = createLink(InsertElement.element, HashList[insert_index].element, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                link2.setSourceOffset(0, ELEMENT_HEIGHT * InsertElement.element.getpercentLink() / 2);
            }
            GLink link1 = createLink(frame[insert_index], InsertElement.element, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            link1.setSourceOffset(0, ARRAY_ELEMENT_HEIGHT / 2);
            HashList[insert_index] = InsertElement;
            repaintwait();
            UpdateList(HashList[insert_index], X_position[insert_index]);

        }


        protected void insert(int insertvalue) {
            int insertindex = insertvalue % HASHTABLE_SIZE;
            if (insertindex < 0)
                insertindex += HASHTABLE_SIZE;

            String insertstring = String.valueOf(insertvalue);
            hashvalue = insertvalue;
            GElement label1 = createLabel("Inserting the following elements ->", -10, -10);
            elementLabel = createLabel(insertstring, -10, -10);

            GElement lineup[] = {label1, elementLabel};
            LineupHorizontal(new Vector2D(20, 10), lineup);
            repaintwait();
            GElementLabel graphic_label = createLabel("Hash the entered value into (" + insertvalue + ") = " + insertvalue + " % " + HASHTABLE_SIZE + " = ", -10, -10);
            elementIndexLabel = createLabel(String.valueOf(insertindex), -10, -10);
            GElement lineup2[] = {graphic_label, elementIndexLabel};
            LineupHorizontal(new Vector2D(20, 30), lineup2);
            repaintwait();
            insertinto(insertindex, String.valueOf(insertvalue));
            removeAny(label1);
            removeAny(graphic_label);
            removeAny(elementIndexLabel);
            removeAny(elementLabel);
        }


        protected void delete(int deletevalue) {

            int deleteindex = deletevalue % HASHTABLE_SIZE;
            if (deleteindex < 0)
                deleteindex += HASHTABLE_SIZE;

            String deletestring = String.valueOf(deletevalue);
            hashvalue = deletevalue;
            GElement link1 = createLabel("Delete the element entered", -10, -10);
            elementLabel = createLabel(deletestring, -10, -10);
            GElement lineup[] = {link1, elementLabel};
            LineupHorizontal(new Vector2D(20, 10), lineup);
            repaintwait();
            GElementLabel g_label = createLabel("hash(" + deletevalue + ") = " + deletevalue + " % " + HASHTABLE_SIZE + " = ", -10, -10);
            elementIndexLabel = createLabel(String.valueOf(deleteindex), -10, -10);

            GElement lineup2[] = {g_label, elementIndexLabel};
            LineupHorizontal(new Vector2D(20, 30), lineup2);

//delete the element and remove the respective position in the lsit and the graphic elements as well
            repaintwait();
            deletefrom(deleteindex, deletestring);
            removeAny(link1);
            removeAny(g_label);
            removeAny(elementIndexLabel);
            removeAny(elementLabel);


        }

        protected void deletefrom(int delete_index, String deletestring) {
            HashElement temp;
            colorlabel(delete_index);
            if (HashList[delete_index] == null) {
                return;
            }
            elementLabel.setLabelColor(Color.RED);
            HashList[delete_index].element.setLabelColor(Color.RED);
            repaintwait();
            HashList[delete_index].element.setLabelColor(Color.BLACK);
            if (HashList[delete_index].element.getLabel().compareTo(deletestring) == 0) {
                elementLabel.setLabelColor(Color.BLACK);

                removeLink(frame[delete_index], HashList[delete_index].element);
                if (HashList[delete_index].next != null) {
                    removeLink(HashList[delete_index].element, HashList[delete_index].next.element);
                    GLink link2 = createLink(frame[delete_index], HashList[delete_index].next.element, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                    link2.setSourceOffset(0, ARRAY_ELEMENT_HEIGHT / 2);
                } else {
                    frame[delete_index].setNull(true);
                }
                removeAny(HashList[delete_index].element);
                HashList[delete_index] = HashList[delete_index].next;
                UpdateList(HashList[delete_index], X_position[delete_index]);
                return;
            }

            for (temp = HashList[delete_index]; temp.next != null; temp = temp.next) {
                temp.next.element.setLabelColor(Color.RED);
                repaintwait();
                temp.next.element.setLabelColor(Color.BLACK);
                if (temp.next.element.getLabel().compareTo(deletestring) == 0) {
                    elementLabel.setLabelColor(Color.BLACK);
                    removeLink(temp.element, temp.next.element);
                    if (temp.next.next == null) {
                        temp.element.setPointerVoid(true);
                    } else {
                        removeLink(temp.next.element, temp.next.next.element);
                        GLink l2 = createLink(temp.element, temp.next.next.element, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                        l2.setSourceOffset(0, ELEMENT_HEIGHT * temp.element.getpercentLink() / 2);

                    }
                    removeAny(temp.next.element);
                    temp.next = temp.next.next;
                    UpdateList(HashList[delete_index], X_position[delete_index]);
                    return;

                }
            }

        }




        protected void find_element(int f_index, String key) {
            boolean found = false;
           colorlabel(f_index);
            for (HashElement tmp = HashList[f_index]; tmp != null; tmp = tmp.next) {
                tmp.element.setLabelColor(Color.RED);
                repaintwait();
                tmp.element.setLabelColor(Color.BLACK);
                if (key.compareTo(tmp.element.getLabel()) == 0) {
                    found = true;
                    break;
                }

            }
            if (found) {
                GElementLabel link = createLabel("Element " + key + " is found", -10, -10);
                GElement lineup[] = {link};
                LineupHorizontal(new Vector2D(20, 50), lineup);
                HoldoverGraphics.add(link);
            } else {
                GElementLabel link = createLabel("Element " + key + " is NOT in the Hash_Table", -10, -10);
                GElement lineup[] = {link};
                LineupHorizontal(new Vector2D(20, 50), lineup);
                HoldoverGraphics.add(link);

            }
            repaintwait();
        }

        protected void find(int value) {
            int insertindex = value % HASHTABLE_SIZE;
            if (insertindex < 0)
                insertindex += HASHTABLE_SIZE;

            String insertstring = String.valueOf(value);

            hashvalue = value;

            GElement link1 = createLabel("Finding the element", -10, -10);
            elementLabel = createLabel(insertstring, -10, -10);
            GElement lineup[] = {link1, elementLabel};
            LineupHorizontal(new Vector2D(20, 10), lineup);

            repaintwait();


            GElementLabel glab = createLabel("Hashing the value into (" + value + ") = " + value + " % " + HASHTABLE_SIZE + " = ", -10, -10);
            elementIndexLabel = createLabel(String.valueOf(insertindex), -10, 10);
            GElement lineup2[] = {glab, elementIndexLabel};
            LineupHorizontal(new Vector2D(20, 30), lineup2);

            repaintwait();

            find_element(insertindex, String.valueOf(value));

            removeAny(glab);
            removeAny(link1);
            removeAny(elementLabel);
            removeAny(elementIndexLabel);


        }

        protected void deleteElements() {
                   int i;
                   HashElement temp;
                   for (i = 0; i < HASHTABLE_SIZE; i++) {
                       if (HashList[i] != null) {
                           removeLink(frame[i], HashList[i].element);
                           for (temp = HashList[i]; temp != null; temp = temp.next) {
                               if (temp.next != null) {
                                   removeLink(temp.element, temp.next.element);
                               }
                               removeAny(temp.element);
                           }
                           HashList[i] = null;
                           frame[i].setNull(true);
                       }
                   }
        }

        protected class HashElement {
            public DSShapeSingleLLU element;
            public Vector2D newlocation;
            public HashElement next;
            public Vector2D path[];
        }

        protected void UpdateList(HashElement list, int xpos) {
            HashElement temp;
            int currentY = INITIAL_Y;
            for (temp = list; temp != null; temp = temp.next) {
                temp.newlocation = new Vector2D(xpos, currentY);
                temp.path = createPath(temp.element.getPosition(), temp.newlocation, NUMSTEPS);
                currentY -= Y_SPACING;
            }
            for (int j = 0; j < NUMSTEPS; j++) {
                for (temp = list; temp != null; temp = temp.next) {
                    temp.element.moveToPosition(temp.path[j]);
                }
                repaintwaitmin();
            }


        }

    }



