import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Soldier class extends {@link Person}.
 * Creating all GUI elements and data for the Soldier's Screen.
 * Elements will be organized by the given assignment layout.
 */
public class Soldier extends Person{
    /*Final Variables for Future changes as needed */
    private final static String title = "Soldier Clubber's Data";
    private final static int height = 250;
    private final static int width = 450;

    private String personalNum;

    private JTextField personalNumTextField;

    private JLabel personalNumAsterisk;

    /**
     *Empty constructor initialize Soldier GUI with Empty Values
     */
    public Soldier()
    {
        this("","","","","");
    }

    /**
     * Parameterized Constructor
     * @param ID Soldier ID Number
     * @param name Soldier Name
     * @param surname Soldier Surname
     * @param tel Soldier Cellular Number
     * @param personalNum Soldier Personal No.
     */
    public Soldier(String ID,String name,String surname,String tel,String personalNum)
    {
        super(ID,name,surname,tel);

        setTitle(title);
        setSize(width,height);

        JPanel personalNumPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel personalNumLabel = new JLabel("Personal No.");

        personalNumAsterisk = new JLabel("*");
        personalNumAsterisk.setPreferredSize(new Dimension(10,25));
        personalNumAsterisk.setForeground(new Color(0,0,0,1));

        personalNumTextField = new JTextField(personalNum);
        personalNumTextField.setPreferredSize(new Dimension(335,25));
        this.personalNum = personalNumTextField.getText();

        personalNumPanel.add(personalNumLabel);
        personalNumPanel.add(personalNumTextField);
        personalNumPanel.add(personalNumAsterisk);

        addToCenter(personalNumPanel);
    }

    /**
     * Determine if Soldier's ID matches with the given key.
     * IF not try to match Personal No.
     * Using {@link Person#match(String)}
     * @param key Soldier ID/Personal No. input
     * @return True - Key matches, False - otherwise
     */
    @Override
    public boolean match(String key)
    {
        return super.match(key) || personalNum.equals(key);
    }

    /**
     * Soldier {@link javax.swing.JTextField} input text validation with Regex.
     * Text Typed must match the specific Soldier's data validation
     * ID - Name - Surname - Cellular Number as in Parent Class {@link Person#validateData}
     * @return True - Text is correct format ,False - otherwise
     */
    @Override
    protected boolean validateData(){

        if (!super.validateData())
            return false;

        if (!personalNumTextField.getText().matches("[ROC]/[1-9]\\d{6}") ){
            personalNumAsterisk.setForeground(Color.RED);
            return false;
        }
        else
            personalNumAsterisk.setForeground(new Color(0,0,0,1));

        return true;
    }

    /**
     * If {@link #validateData()} returns true,
     * Save the data from {@link javax.swing.JTextField} to Soldier
     */
    @Override
    protected void commit() {
        super.commit();
        personalNum = personalNumTextField.getText();
    }

    /**
     * Cancel operation, Load Soldier data to {@link javax.swing.JTextField}
     * and Disable Asterisk of Personal Number
     * and closes the window.
     */
    @Override
    protected void rollBack() {
        super.rollBack();
        personalNumTextField.setText(personalNum);

        personalNumAsterisk.setForeground(new Color(0,0,0,1));
    }
}
