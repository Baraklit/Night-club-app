import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Abstract Class ClubAbstractEntity extends {@link javax.swing.JFrame}.
 * Implements Serializable {@link java.io.Serializable}
 * Sets the base GUI window layout for Clubber
 * and the base behavior.
 * @author Barak Litvinov And Eliran Ben Itzhak
 */
public abstract class ClubAbstractEntity extends JFrame implements Serializable {
    private JButton okButton;
    private JButton cancelButton;
    private JPanel centerPanel;
    private ButtonHandler handler;

    /**
     * Default Constructor, Sets the GUI window base settings.
     * Adds handlers to the buttons on the base window layout.
     */
    public ClubAbstractEntity () {

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        centerPanel = new JPanel(new GridLayout(0,1));
        handler = new ButtonHandler();

        JPanel buttonsPanel = new JPanel();

        cancelButton.setEnabled(false);

        okButton.addActionListener(handler);
        cancelButton.addActionListener(handler);

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        add(centerPanel);
        add(buttonsPanel,BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

    /**
     * Adds GUI elements to the center of the window.
     * @param guiComponent Component to be added to central panel
     */
    protected void addToCenter(Component guiComponent)
    {
        centerPanel.add(guiComponent);
    }

    /**
     * Abstract method. Determine if Clubber key matches with the given key.
     * @param key The key to search
     * @return True - Key matches, False - otherwise
     */
    public abstract boolean match(String key);

    /**
     * Get Method for ID
     * @return The ID Of Clubber
     */
    public abstract String getID();

    /**
     * Abstract method. {@link javax.swing.JTextField} input text validation.
     * Text Typed must match the specific Clubber's data validation
     * @return True - Text is in correct format False - otherwise
     */
    protected abstract boolean validateData();

    /**
     * Abstract Method. Saves {@link javax.swing.JTextField} to the
     * specific Clubber's object members.
     */
    protected abstract void commit();

    /**
     * Abstract Method. Reload data from Clubber's object to {@link javax.swing.JTextField}
     * and closes the window.
     */
    protected abstract void rollBack();

    /**
     * Handler for Undo and Clear button press. Implements {@link java.awt.event.ActionListener}
     */
    private class ButtonHandler implements ActionListener,Serializable {
        /**
         * Defines the behaviour of button press action.
         * @param event ActionEvent button action event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == okButton){
                if (validateData()) {
                    commit();
                    cancelButton.setEnabled(true);
                    setVisible(false);
                }
                else return;
            }

            else if (event.getSource() == cancelButton){
                rollBack();
                setVisible(false);
            }
        }
    }
}


