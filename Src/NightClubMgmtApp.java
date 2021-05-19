import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * NightClubMgmtApp class extends {@link javax.swing.JFrame}.
 * Creating all GUI elements for the application.
 * @author Barak Litvinov And Eliran Ben Itzhak
 */
public class NightClubMgmtApp extends JFrame
{
    private ArrayList<ClubAbstractEntity> clubbersArr;

    private JPanel appPanel;
    private JButton searchButt;
    private JButton createButt;
    private JButton exitButt;

    private JComboBox<String> entityType;
    private String[] entityNames = {"Person", "Student", "Soldier"};

    private JPanel counterClubberPanel;
    private JLabel counterClubberLabel;

    private ButtonsHandler buttHandler;

    /**
     * Default Constructor, Sets the GUI window base settings.
     * Initialize all data members
     */
    public NightClubMgmtApp()
    {
        /*App(JFrame) Properties*/
        setTitle("B.K Night Club");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500,500);

        /*Set Icon Of JFrame*/
        ImageIcon img = new ImageIcon("Bk_Logo.png");
        setIconImage(img.getImage());

        /*Initialize JLabel For Background App*/
        JLabel background = new JLabel();
        background.setSize(new Dimension(500,500));
        try {
            BufferedImage backImg = ImageIO.read(new File("BkBackground.jpg"));
            Image imageBackground = backImg.getScaledInstance(background.getWidth(),
                    background.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon iconBackImg = new ImageIcon(imageBackground);
            background = new JLabel(iconBackImg);
            setContentPane(background);
        }
         catch(IOException e) {
             e.printStackTrace();
         }

        setLayout(new BorderLayout());
        /*Screen Dimension For Center The App on Screen*/
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)((dimension.getWidth() - getWidth()) / 2), ((int)(dimension.getHeight() - getHeight()) / 2));

        /*Button Handler*/
        buttHandler = new ButtonsHandler();

        /*Main App Panel*/
        appPanel = new JPanel(new GridLayout(4 , 1));
        appPanel.setOpaque(false);

        /*Title Label Initialize*/
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("B.K Club Management App", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC,30));
        titleLabel.setSize(titleLabel.getPreferredSize());
        titleLabel.setBackground(new Color(0,0,0,1)); //Transparency
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titlePanel.add(titleLabel);
        titlePanel.setOpaque(false);
        appPanel.add(titlePanel);

        /*Combo Box Of Entities and Select Title Panel*/
        JPanel selectivePanel = new JPanel(new FlowLayout());
        JLabel selectTitle = new JLabel("Select Clubber :");
        selectTitle.setSize(selectTitle.getPreferredSize());
        selectTitle.setFont(new Font("SansSerif",Font.BOLD+Font.ITALIC,22));
        selectTitle.setForeground(Color.WHITE);
        selectTitle.setBackground(new Color(0,0,0 ,1));
        selectTitle.setOpaque(true);
        entityType = new JComboBox<String>(entityNames);
        entityType.setPreferredSize(new Dimension(150,30));
        selectivePanel.add(selectTitle);
        selectivePanel.add(entityType);
        selectivePanel.setOpaque(false);
        appPanel.add(selectivePanel);

        /*Search and Create Panel*/
        JPanel optionButtons = new JPanel();
        searchButt = new JButton("Search");
        createButt = new JButton("Create New Clubber");
        searchButt.setPreferredSize(new Dimension(150,50));
        searchButt.addActionListener(buttHandler);
        createButt.setPreferredSize(new Dimension(150, 50));
        createButt.addActionListener(buttHandler);
        searchButt.setBackground(new Color(0,0,0));
        createButt.setBackground(new Color(0,0,0));
        searchButt.setForeground(Color.WHITE);
        createButt.setForeground(Color.WHITE);
        createButt.setFont(new Font("SansSerif",Font.BOLD,12));
        optionButtons.add(searchButt);
        optionButtons.add(createButt);
        optionButtons.setOpaque(false);
        appPanel.add(optionButtons);

        /*Save and Exit Panel*/
        exitButt = new JButton("Exit");
        JPanel exitOperationPanel = new JPanel();
        exitButt.setPreferredSize(new Dimension(200, 80));
        exitButt.setFont(new Font("SansSerif", Font.BOLD, 20));
        exitButt.addActionListener(buttHandler);
        exitOperationPanel.add(exitButt);
        exitButt.setBackground(new Color(0,0,0));
        exitButt.setForeground(Color.WHITE);
        exitOperationPanel.setOpaque(false);
        appPanel.add(exitOperationPanel);

        add(appPanel,BorderLayout.CENTER);

        /*Initialize New Clubbers Array List*/
        clubbersArr = new ArrayList<>();

        /*Load Clubbers From DB*/
        loadClubbersDBFromFile();

        /*Initialize Counter Of Clubbers*/
        counterClubberLabel = new JLabel("Total Clubbers : "+clubbersArr.size());
        counterClubberPanel = new JPanel();
        counterClubberPanel.add(counterClubberLabel);
        add(counterClubberPanel,BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Prompt for input - Key.
     * A Clubber that has matching key will be
     * shown on screen.
     * Using {@link Person#match(String)} for Person, {@link Student#match(String)} for Person, {@link Soldier#match(String)} for Soldier
     * Otherwise - corresponding message will be shown.
     */
    private void manipulateDB()
    {
        String input;
        boolean found = false;

            input = JOptionPane.showInputDialog(this,"Please Enter The Clubber's Key",
                    "Clubber's Search", JOptionPane.OK_CANCEL_OPTION);

            if (input == null || input.equals(""))
            {
                JOptionPane.showMessageDialog(this,"Search Key is Empty!");
            }

            for(ClubAbstractEntity clubber : clubbersArr)
                if(clubber.match(input))
                {
                    found = true;
                    clubber.setVisible(true);
                    setEnabled(false);
                    clubber.toFront();
                    clubber.addWindowListener(new WindowAdapter()
                    {
                        @Override
                        /**
                         * Override Method that will give access back to the Main Frame
                         * @param e holds the event
                         */
                        public void windowDeactivated(WindowEvent e) {
                            if (!clubber.isVisible())
                            {
                                setEnabled(true);
                                toFront();
                            }
                        }
                    });
                    break;
                }

            if(!found)
                JOptionPane.showMessageDialog(this,"Clubber with key " + input +
                        " Does Not Exist", "Search Result",JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Load Clubbers from DB file into application's memory.
     * Using {@link java.io.FileInputStream} {@link java.io.ObjectInputStream}
     * DB File: "BKCustomers.dat"
     */
    private void loadClubbersDBFromFile()
    {
        FileInputStream inputFile = null;
        ObjectInputStream inputObject = null;
        try
        {
            inputFile = new FileInputStream("BKCustomers.dat");
            inputObject = new ObjectInputStream(inputFile);
            clubbersArr = (ArrayList<ClubAbstractEntity>)inputObject.readObject();
        }
        catch (FileNotFoundException fileNotFound)
        {
            JOptionPane.showMessageDialog(this,"Couldn't Find Given File. " +
                            "File will be created upon exiting the app",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(this,"Couldn't Read Object From File",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (ClassNotFoundException foundException)
        {
            JOptionPane.showMessageDialog(this,"ClubAbstractEntity Class Not Found",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        finally
        {
            try
            {
                if(inputObject!= null)
                    inputObject.close();
                if (inputFile!= null)
                    inputFile.close();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Save Clubbers from application's memory into DB File
     * Using {@link java.io.FileOutputStream} {@link java.io.ObjectOutputStream}
     * DB File: "BKCustomers.dat"
     */
    private void writeClubbersDBtoFile()
    {
        //Write all the objects data in clubbers ArrayList into the file
        FileOutputStream outputFile = null;
        ObjectOutputStream outputObject = null;
        try
        {
            outputFile = new FileOutputStream("BKCustomers.dat");
            outputObject = new ObjectOutputStream(outputFile);

            outputObject.writeObject(clubbersArr);
        }
        catch (FileNotFoundException fileNotFound)
        {
            JOptionPane.showMessageDialog(this,"Couldn't Find Given File." +
                            "File Will Be Created When Exiting The App",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (IOException exception)
        {
            JOptionPane.showMessageDialog(this,"Couldn't Write Object To File",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        finally
        {
            try
            {
                if(outputObject!= null)
                    outputObject.close();
                if (outputFile!= null)
                    outputFile.close();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Creates a new type of Clubber.
     * Prompt a window to type in Clubber's INFO
     */
    private void createNewClubber()
    {

        ClubAbstractEntity newClubber = null;

        switch(entityType.getSelectedIndex())
        {
            case 0:
                newClubber = new Person();
                break;
            case 1:
                newClubber = new Student();
                break;
            case 2:
                newClubber = new Soldier();
                break;
        }

        newClubber.setLocationRelativeTo(this); /*Open In Center Of Screen*/
        setEnabled(false);

        ClubAbstractEntity finalNewClubber = newClubber;
        newClubber.addWindowListener(new WindowAdapter()
                                     {
                                         /**
                                          * Override Method that will Block the access to the Main Frame
                                          * @param e holds the event
                                          */
                                         public void windowDeactivated(WindowEvent e)
                                         {
                                            if (finalNewClubber.isVisible())
                                                return;

                                            setEnabled(true);
                                            toFront();
                                            finalNewClubber.removeWindowListener(this);

                                             for (ClubAbstractEntity clubber : clubbersArr)
                                             {
                                                 if (clubber.match(finalNewClubber.getID()))
                                                 {
                                                     JOptionPane.showMessageDialog(null,
                                                             "ID Is Already Exists", "Clubber Addition Error",
                                                             JOptionPane.ERROR_MESSAGE);
                                                     return;
                                                 }
                                             }
                                            clubbersArr.add(finalNewClubber);
                                            counterClubberLabel.setText("Total Clubbers : " + clubbersArr.size());
                                         }
                                     });
    }

    /**
     * Handler for Buttons Press.
     * Implements {@link java.awt.event.ActionListener}
     */
    private class ButtonsHandler implements ActionListener
    {
        /**
         * Defines the behaviour of button press action.
         * @param event ActionEvent button action event
         */
        @Override
        public void actionPerformed (ActionEvent event)
        {
            if (event.getSource() == exitButt) {
                writeClubbersDBtoFile();
                System.exit(0);
            }
            else if (event.getSource() == searchButt)
                manipulateDB();
            else  if (event.getSource() == createButt)
                createNewClubber();
        }
    }

    /**
     * Main Program Method, Opens CLubbers Application
     * @param args Application Activation Parameters - Disabled
     */
    public static void main(String[] args)
    {
        NightClubMgmtApp application = new NightClubMgmtApp();
    }
}
