import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Person class extends {@link ClubAbstractEntity}.
 * Creating all GUI elements and data for the Person window.
 * Elements will be organized by the given assignment layout.
 */
public class Person extends ClubAbstractEntity
{
    /*Final Variables for Future changes as needed */
    private final static int height = 220;
    private final static int width = 450;
    private final static String title = "Person Clubber's Data";

    private String ID;
    private String name;
    private String surname;
    private String tel;

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField telTextField;

    private JLabel[] asterisk;

    /**
     *Empty constructor initialize Person GUI with Empty Values
     */
    public Person()
    {
        this("","","","");
    }

    /**
     * Parameterized Constructor
     * Using super constructor {@link ClubAbstractEntity}
     * @param ID Person's ID Number
     * @param name Person's Name
     * @param surname Person's Surname
     * @param tel Person's Cellular Number
     */
    public Person(String ID,String name,String surname,String tel)
    {
        setTitle(title);
        setSize(width,height);

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel surnamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel telPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel idLabel = new JLabel("ID");
        JLabel nameLabel = new JLabel("Name");
        JLabel surnameLabel = new JLabel("Surname");
        JLabel telLabel = new JLabel("Tel");

        idTextField = new JTextField(ID);
        telTextField = new JTextField(tel);
        nameTextField = new JTextField(name);
        surnameTextField = new JTextField(surname);

        idTextField.setPreferredSize(new Dimension(335,25));
        telTextField.setPreferredSize(new Dimension(335,25));
        nameTextField.setPreferredSize(new Dimension(335,25));
        surnameTextField.setPreferredSize(new Dimension(335,25));

        this.ID = idTextField.getText();
        this.name = nameTextField.getText();
        this.surname = surnameTextField.getText();
        this.tel = telTextField.getText();

        asterisk = new JLabel[4];
        for (int index = 0 ; index < asterisk.length ; index++)
        {
            asterisk[index] = new JLabel("*");
            asterisk[index].setPreferredSize(new Dimension(10,25));
            asterisk[index].setForeground(new Color(0,0,0,1));
        }

        idPanel.add(idLabel);
        idPanel.add(idTextField);
        idPanel.add(asterisk[0]);

        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        namePanel.add(asterisk[1]);

        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameTextField);
        surnamePanel.add(asterisk[2]);

        telPanel.add(telLabel);
        telPanel.add(telTextField);
        telPanel.add(asterisk[3]);

        addToCenter(idPanel);
        addToCenter(namePanel);
        addToCenter(surnamePanel);
        addToCenter(telPanel);

    }

    /**
     * Get Method For ID
     * @return ID of Person,Student,Solider Object
     */
    public String getID(){return this.ID;}

    /**
     * Determine if Person's ID matches with the given key.
     * @param key Person ID input
     * @return True - Key matches, False - otherwise
     */
    @Override
    public boolean match(String key)
    {
        return ID.equals(key);
    }

    /**
     * Person's {@link javax.swing.JTextField} input text validation.
     * Text Typed must match the specific Person's data validation With Regex<br>
     * @return True - Text is correct format False - otherwise
     */
    @Override
    protected boolean validateData() {
        if (!idTextField.getText().matches("\\d-\\d{7}\\|[1-9]")) {
            asterisk[0].setForeground(Color.RED);
            return false;
        }
        else
            asterisk[0].setForeground(new Color(0,0,0,1));

        if (!nameTextField.getText().matches("[A-Z][a-z]+")) {
            asterisk[1].setForeground(Color.RED);
            return false;
        }
        else
            asterisk[1].setForeground(new Color(0,0,0,1));

        if (!surnameTextField.getText().matches("([A-Z][a-z]*[-']?)+")) {
            asterisk[2].setForeground(Color.RED);
            return false;
        }
        else
            asterisk[2].setForeground(new Color(0,0,0,1));

        if (!telTextField.getText().matches("\\+\\([1-9]\\d{0,2}\\)\\d{1,3}-[1-9]\\d{6}")) {
            asterisk[3].setForeground(Color.RED);
            return false;
        }
        else
            asterisk[3].setForeground(new Color(0,0,0,1));

        return true;
    }

    /**
     * IF {@link #validateData()} returns true,
     * Saves the data from {@link javax.swing.JTextField} to Person
     */
    @Override
    protected void commit() {
        ID = idTextField.getText();
        name = nameTextField.getText();
        surname = surnameTextField.getText();
        tel = telTextField.getText();
    }

    /**
     * Cancel operation, Load Person data to {@link javax.swing.JTextField}
     * And Disable all Asterisks
     * and closes the window.
     */
    @Override
    protected void rollBack() {
        idTextField.setText(ID);
        nameTextField.setText(name);
        surnameTextField.setText(surname);
        telTextField.setText(tel);

        for (JLabel label: asterisk) {
            label.setForeground(new Color(0,0,0,1));
        }
    }
}
