import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Student class extends {@link Person}.
 * Creating all GUI elements and data for the Student's window.
 * Elements will be organized by the given assignment layout.
 */
public class Student extends Person
{
    /*Final Variables for Future changes as needed */
    private final static String title = "Student Clubber's Data";
    private final static int height = 250;
    private final static int width = 450;

    private String studentID;

    private JTextField stuIDTextField;

    private JLabel stuIDAsterisk;

    /**
     * Empty constructor initialize Student GUI with Empty Values
     */
    public Student()
    {
        this("","","","","");
    }

    /**
     * Parameterized Constructor
     * @param ID Student's ID Number
     * @param name Student's Name
     * @param surname Student's Surname
     * @param tel Student's Cellular Number
     * @param studentID Student's Student ID Number
     */
    public Student(String ID,String name,String surname,String tel,String studentID)
    {
        super(ID,name,surname,tel);

        setTitle(title);
        setSize(width,height);

        JPanel stuIDPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel stuIDLabel = new JLabel("Student ID ");

        stuIDAsterisk = new JLabel("*");
        stuIDAsterisk.setPreferredSize(new Dimension(10,25));
        stuIDAsterisk.setForeground(new Color(0,0,0,1));

        stuIDTextField = new JTextField(studentID);
        stuIDTextField.setPreferredSize(new Dimension(335,25));
        this.studentID = stuIDTextField.getText();

        stuIDPanel.add(stuIDLabel);
        stuIDPanel.add(stuIDTextField);
        stuIDPanel.add(stuIDAsterisk);

        addToCenter(stuIDPanel);
    }

    /**
     * Determine if Student's ID matches with the given key.
     * IF not try to match Student ID Number.
     * Using {@link Person#match(String)}
     * @param key ID/Student ID No. input
     * @return True - Key matches, False - otherwise
     */
    @Override
    public boolean match(String key)
    {
        return super.match(key) || studentID.endsWith(key);
    }

    /**
     * Student's {@link javax.swing.JTextField} input text validation with Regex.
     * Text Typed must match the specific Student's data validation
     * ID - Name - Surname - Tel Number as in Parent Class {@link Person#validateData}
     * @return True - Text is in correct format False - otherwise
     */
    @Override
    protected boolean validateData(){

         if (!super.validateData())
             return false;

         if (!stuIDTextField.getText().matches("[A-Z]{3}/\\d{5}")) {
             stuIDAsterisk.setForeground(Color.RED);
             return false;
         }
         else
             stuIDAsterisk.setForeground(new Color( 0,0,0,1));

         return true;
    }

    /**
     * If {@link #validateData()} returns true,
     * Saves the data from {@link javax.swing.JTextField} to Student
     */
    @Override
    protected void commit() {
        super.commit();
        studentID = stuIDTextField.getText();
    }

    /**
     * Cancel operation, Load Student data to {@link javax.swing.JTextField}
     * and closes the window.
     */
    @Override
    protected void rollBack() {
        super.rollBack();
        stuIDTextField.setText(studentID);

        stuIDAsterisk.setForeground(new Color(0,0,0,1));
    }
}
