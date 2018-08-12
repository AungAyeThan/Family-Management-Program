package ict373assignment2;


import javax.swing.*; // import package
import java.awt.*;// import package
import java.awt.event.*;// import package
import java.io.File;// import package
import java.io.FileInputStream;// import package
import java.io.FileOutputStream;// import package
import java.io.IOException;// import package
import java.io.ObjectInputStream;// import package
import java.io.ObjectOutputStream;// import package
import javax.swing.event.TreeSelectionEvent;// import package
import javax.swing.event.TreeSelectionListener;// import package
import javax.swing.filechooser.FileNameExtensionFilter;// import package
import javax.swing.text.AttributeSet;// import package
import javax.swing.text.BadLocationException;// import package
import javax.swing.tree.DefaultMutableTreeNode;// import package
import javax.swing.text.PlainDocument;// import package
/*

Title    : family Tree management program. 
Author   : Aung Aye Than 
Date     : 29th March 2018.
Filename : familyTreeManagement.java
Version  : 1.0

Purpose of the program is to manage person family tree and display information of the family member which are included in the family-tree.


For the input condition, user have to enter numbers only for street number and postcode input, and user have to enter either male or female for the gender input. 
Furthermore, there is the word input limitations that user can only enter only 28 words, and 11 words limitation for the numbers input.

For the expected output will be depending on the input data that user has been saved and submitted. Every output data will be shown on the view mode function.

*/



class familyTreemanagement extends JFrame
{   
    Container newContainers = getContentPane(); // inplementing container
    
    
    JPanel menuPanel,leftPanel, rightPanel; //Declaring the JPanel variable as global variable
    JButton addTreeButton, loadTreeButton, submitbutton, cancelButton,saveTreeButton,HomeButton,managePerson; //Declaring the JButton variable as global variable
    JLabel firstnameLabel,surnameBirthLabel,surnameMarriageLabel, GenderLabel, paragraphLabel,addressLabel, address_StreetNameLabel, 
           address_SuburbLabel,addressPostcodeLabel,fatherLabel,motherLabel,SpouseLabel,childrenLabel,displayLabel,HomeLabel,
           rightLabel; //Declaring the JLabel variable as global variable
    JTextField  firstname,surnameBirth, surnameMarriage,Gender,address_StreetNo,address_StreetName,address_Suburb,addressPostcode,father,mother,Spouse,children,
               personInput, personInput2; //Declaring the JTextField variable as global variable
    JTree tree; //Declaring the JTree variable as global variable
    JTextArea Paragraph;
    JSplitPane splitPane; //Declaring the JSplitPane variable as global variable
    JScrollPane rightScrollPane,leftScrollPane; //Declaring the JScrollPane variable as global variable
    JComboBox relativelist;//Declaring the JComboBox variable as global variable
   
    
    person personData;
    
    boolean personInputCheck = false;//Declaring and initialising variable as boolean as false 
    boolean motherDatacheck = false;//Declaring and initialising variable as boolean as false 
    boolean fatherDatacheck = false;//Declaring and initialising variable as boolean as false 
    boolean spouseDatacheck = false;//Declaring and initialising variable as boolean as false 
    boolean childspouseDatacheck = false;//Declaring and initialising variable as boolean as false

    
    class ShowAddObjListener implements ActionListener  //action listener to perform when certain event occurs
    {   
        public void actionPerformed( ActionEvent e )
        {  
            
            if (personInputCheck ==true) //if there is previous data in the program 
            {
                int answer = JOptionPane.showConfirmDialog(leftPanel, "There is previous person data. Proceed to add new data without saving will lose previous data\nContinue?","Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //pop-up yes/no question to ask user about decision
                if (answer == JOptionPane.YES_OPTION) //if user decided to continue 
                {
                    AddUserDetail();//proceed to start over new person creation
                } else if (answer == JOptionPane.NO_OPTION) //if user decided not to continue
                {
                   JOptionPane.showMessageDialog(leftPanel, "Please make sure to save previous data before adding a new data"); //pop-up message to tell user to save 
                }
                else if (answer == JOptionPane.CLOSED_OPTION) //if user close the message box 
                {
                    //do nothing
                }
            }
            else
            {
                AddUserDetail(); //proceed to create new person if there is no previous data
            }
            
            
        }
    }
    
    
    class cancelObjListener implements ActionListener  //action listener for cancel button
    {
        public void actionPerformed (ActionEvent e)//action listener for cancel button
        {
            AddUserDetail(); // invoking method
        }
    }
    
    class cancelEdit implements ActionListener  //action listener for cancel button in loadData method
    {
        public void actionPerformed(ActionEvent e)
        {
            loadData(personData);// invoking method
        }
    }
    class saveObjListener implements ActionListener //action listener for save button 
    {
        public void actionPerformed(ActionEvent e)
        {
            saveTreeFile(); // invoking method
        }
    }
    
    class showLoadObjListener implements ActionListener //action listener for load tree button 
    {
        public void actionPerformed (ActionEvent e)
        {
            openfile(); //invoking method
        }
    }
    
   
    
    class submitObjListener implements ActionListener //action listener for submit button in add user detail method
    {
        public void actionPerformed (ActionEvent e)
        {
           createData(); //invoking method
        }
    }
    
    class editbuttonObjListener implements ActionListener //action listener for edit button in add user detail method
    {
        public void actionPerformed (ActionEvent e)
        {
            try // try-catch block to catch exception
            {
                //JTree tree = (JTree) e.getSource(); 
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();//getting last selected node of user selected
                Object selectedObject = selectedNode.getUserObject();
                editGUI((person)selectedObject); //invoking method and cast it to person object
            }
            catch(NullPointerException| ClassCastException z) // proceed when there is null pointer exception
            {
                JOptionPane.showMessageDialog(rightPanel, "Please select person from tree that you would like to edit"); //prompt dialog to show when there is exception
            }
            
        }
    }

    
    class relativeButtionListener implements ActionListener //action listener for choose relative
    {
        public void actionPerformed (ActionEvent e)
        {
            addrelative(); //invoking method
        }
    }

    class SelectionListener implements TreeSelectionListener //tree selection listener for tree to know what user select
    {
        public void valueChanged (TreeSelectionEvent e) 
        {
            JTree tree = (JTree) e.getSource(); 
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();//getting last selected node of user selected

            if (selectedNode == null)
            {
                return;
            }
            try
            {
                Object selectedObject = selectedNode.getUserObject();
                loadData((person)selectedObject);
            }
            catch(ClassCastException k)
            {
                
            }
        }
    }
    
    class relativeObjectListener implements ActionListener //action listener for  submit relative data
    {
        public void actionPerformed (ActionEvent e)
        {
            try // try-catch block to catch exception
            {
                submitrelativedata(); //invoking method
            }
            catch(NullPointerException z) // proceed when there is null pointer exception
            {
                JOptionPane.showMessageDialog(rightPanel, "Please select person from tree that you would like to add relative data");//prompt dialog to show when there is exception
            }
            
        }
    }
    
    
    class saveChangesObjListener implements ActionListener//action listener for  submit changes in edit data
    {
        public void actionPerformed(ActionEvent e)
        {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            try
            {
                Object selectedObject = selectedNode.getUserObject();
                saveeditChanges((person)selectedObject);//invoking method
            }
            catch(ClassCastException k)
            {
                //do nothing
            }
        }
    }
    
    public class JTextFieldLimit extends PlainDocument //class that will perform the input word count limitation
    {
        private int limitamount; //set variable as int

        JTextFieldLimit(int limit) 
        {
            super();
            this.limitamount = limit; // initialising limit amount
        }

        public void insertString( int offset, String  string, AttributeSet attribute ) throws BadLocationException 
        {
            if (string == null) //if there is no input inside
            {    
                return;
            }

            if ((getLength() + string.length()) <= limitamount) 
            {
                super.insertString(offset, string, attribute);
            }
        }
    }

    

    
    
    
    familyTreemanagement() //class name
    {   
        
        super("Family Tree Management Program"); // frame name
        setExtendedState(familyTreemanagement.MAXIMIZED_BOTH); //setting size to maximum 
        
        menuPanel = new JPanel();   menuPanel.setBackground(Color.WHITE); //creating new JPanel
        leftPanel = new JPanel();  leftPanel.setBackground(Color.WHITE);//creating new JPanel
        rightPanel = new JPanel(); rightPanel.setBackground(Color.LIGHT_GRAY);//creating new JPanel
        leftScrollPane = new JScrollPane();//creating new JScrollPane
        rightScrollPane = new JScrollPane();//creating new JScrollPane
        splitPane = new JSplitPane(); //creating new JScrollPane
        splitPane.setRightComponent(rightPanel); //setting right component for rightpanel
        splitPane.setLeftComponent(leftPanel);//setting left component for leftpanel
        splitPane.setResizeWeight(.5d);//setting a place to locate for spliting left and right
        
       
        newContainers.setLayout(new BorderLayout());//setting layout for container 
        newContainers.add(menuPanel, BorderLayout.NORTH);
        newContainers.add(splitPane,BorderLayout.CENTER);
       
        
        
        MenuOption(); //invoking method
        
        addWindowListener //action listener when closing frame
	( new WindowAdapter() 
              { public void windowClosing( WindowEvent e )
                { System.exit(0); }
              }
         );
        
        
    }

    
    void MenuOption() //method to show menu option
    {   
        leftPanel.removeAll();//clearing all last data that is added to left panel
        rightPanel.removeAll();//clearing all last data that is added to right panel
        loadTreeButton = new JButton("Load Tree"); // implementing new button and named as load tree 
        addTreeButton = new JButton("Create New Tree");// implementing new button and named as Create New tree 
        saveTreeButton = new JButton ("Save Tree");// implementing new button and named as Save tree 
        
        menuPanel.setLayout(new GridLayout(0,3)); //set layout for menu panel
        
        JLabel menulabel = new JLabel("Welcome to Family Tree Program"); //creating new label
        menulabel.setFont(menulabel.getFont().deriveFont(Font.BOLD, 17f));// setting label font as bold  
        menuPanel.add(new JLabel(""));// adding label to menupanel to display on the panel
        menuPanel.add(menulabel);// adding label to menupanel to display on the panel
        menuPanel.add(new JLabel(""));// adding label to menupanel to display on the panel
        menuPanel.add(addTreeButton);// adding button to menupanel to display on the panel
        menuPanel.add(loadTreeButton);// adding button to menupanel to display on the panel
        menuPanel.add(saveTreeButton);// adding button to menupanel to display on the panel
        
        addTreeButton.addActionListener(new ShowAddObjListener()); // implementing actionlistener for button
        loadTreeButton.addActionListener(new showLoadObjListener());// implementing actionlistener for button
        saveTreeButton.addActionListener(new saveObjListener());// implementing actionlistener for button
    }

    void AddUserDetail()//method to create new person tree 
    {
        
        leftPanel.removeAll();//clearing all last data that is added to left panel
        rightPanel.removeAll();//clearing all last data that is added to right panel
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        JLabel createDatalabel = new JLabel("Create FamilyTree"); //implementing new label
        createDatalabel.setFont(createDatalabel.getFont().deriveFont(Font.BOLD, 17f)); // setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(createDatalabel,c);// adding label in leftPanel to display on the panel
        
        
        JLabel personal = new JLabel("Person Information");//implementing new label
        personal.setFont(personal.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.add(personal,c);// adding label in leftPanel to display on the panel

        firstnameLabel = new JLabel("Name: "); //creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        leftPanel.add(firstnameLabel,c);// adding label in leftPanel to display on the panel

        firstname = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 2;
        leftPanel.add(firstname,c);// adding label in leftPanel to display on the panel
        firstname.setDocument(new JTextFieldLimit(28));

        surnameBirthLabel = new JLabel ("Surname: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        leftPanel.add(surnameBirthLabel,c);
        
        surnameBirth = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 3;
        leftPanel.add(surnameBirth,c);
        surnameBirth.setDocument(new JTextFieldLimit(28));
        
        surnameMarriageLabel = new JLabel ("Maiden Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        leftPanel.add(surnameMarriageLabel,c);

        surnameMarriage = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 4;
        leftPanel.add(surnameMarriage,c);
        surnameMarriage.setDocument(new JTextFieldLimit(28));

        GenderLabel = new JLabel ("Gender: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        leftPanel.add(GenderLabel,c);

        Gender = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        leftPanel.add(Gender,c);
        Gender.setDocument(new JTextFieldLimit(28));
        
        paragraphLabel = new JLabel ("Life Description: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;        
        c.gridx = 0;
        c.gridy = 6;
        leftPanel.add(paragraphLabel,c);

        Paragraph = new JTextArea(5,30);//creating new textfield to let user enter input
        Paragraph.setPreferredSize(new Dimension(500, 550));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridx = 3;
        c.gridy = 6;
        Paragraph.setDocument(new JTextFieldLimit(28));
        JScrollPane areaScrollPane = new JScrollPane(Paragraph,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        leftPanel.add(areaScrollPane,c);
        
        
        JLabel addressText =new JLabel("Address Information");//implementing new label
        addressText.setFont(addressText.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        leftPanel.add(addressText,c);// adding label in leftPanel to display on the panel
        
        addressLabel = new JLabel ("Street Number: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        leftPanel.add(addressLabel,c);
        
        address_StreetNo = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 8;
        leftPanel.add(address_StreetNo,c);
        address_StreetNo.setDocument(new JTextFieldLimit(28));
        
        address_StreetNameLabel = new JLabel ("Street Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        leftPanel.add(address_StreetNameLabel,c);

        address_StreetName = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 9;
        leftPanel.add(address_StreetName,c);
        address_StreetName.setDocument(new JTextFieldLimit(28));
        
        address_SuburbLabel = new JLabel ("Suburb: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        leftPanel.add(address_SuburbLabel,c);

        address_Suburb= new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 10;
        leftPanel.add(address_Suburb,c);
        address_Suburb.setDocument(new JTextFieldLimit(28));
        
        addressPostcodeLabel = new JLabel ("Postcode: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        leftPanel.add(addressPostcodeLabel,c);
        
        addressPostcode = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 11;
        leftPanel.add(addressPostcode,c);
        addressPostcode.setDocument(new JTextFieldLimit(28));
        
        submitbutton = new JButton ("Submit ");//creating new button and name it as submit for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 12;
        leftPanel.add(submitbutton,c);
        
        
        
        submitbutton.addActionListener(new submitObjListener());// adding actionlistener for submit button
        cancelButton = new JButton ("Reset");//creating new button and name it as submit for cancel input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 12;
        leftPanel.add(cancelButton,c);
        
        cancelButton.addActionListener(new cancelObjListener());// adding actionlistener for cancel button
        
        rightPanel.setLayout(new GridBagLayout());// setting layout for right panel
        
        JLabel viewLabel = new JLabel ("No family tree detected to display"); //creating new label to show information
        viewLabel.setFont(viewLabel.getFont().deriveFont(Font.BOLD, 15f)); // setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 0.5;
        c.weighty = 1;
        c.insets = new Insets(10,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        rightPanel.add(viewLabel,c);

        leftPanel.setVisible(false);// setting leftpanel as false not to visible on the panel
        rightPanel.setVisible(false);// setting rightPanel as false not to visible on the panel
        leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
        rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel
        
        
    }

    void loadData(person personData) //method that perform as view mode to display all available person
    {
        leftPanel.removeAll();//removing all the previous data from left panel
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel viewmodetext = new JLabel("View Mode"); //implementing new label to show information on the panel
        viewmodetext.setFont(viewmodetext.getFont().deriveFont(Font.BOLD, 20f));//setting font size and bold for label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(viewmodetext,c);// adding label in leftPanel to display on the panel
        
        
        JLabel personal = new JLabel("Person Information");//implementing new label
        personal.setFont(personal.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.add(personal,c);// adding label in leftPanel to display on the panel

        firstnameLabel = new JLabel("Name: "); //creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        leftPanel.add(firstnameLabel,c);// adding label in leftPanel to display on the panel

        firstname = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 2;
        leftPanel.add(firstname,c);// adding label in leftPanel to display on the panel
        firstname.setDocument(new JTextFieldLimit(28));

        surnameBirthLabel = new JLabel ("Surname: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        leftPanel.add(surnameBirthLabel,c);
        
        surnameBirth = new JTextField(10);//creating new text field for user input
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 3;
        leftPanel.add(surnameBirth,c);
        surnameBirth.setDocument(new JTextFieldLimit(28));
        
        surnameMarriageLabel = new JLabel ("Maiden Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        leftPanel.add(surnameMarriageLabel,c);

        surnameMarriage = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 4;
        leftPanel.add(surnameMarriage,c);
        surnameMarriage.setDocument(new JTextFieldLimit(28));

        GenderLabel = new JLabel ("Gender: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        leftPanel.add(GenderLabel,c);

        Gender = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        leftPanel.add(Gender,c);
        Gender.setDocument(new JTextFieldLimit(28));
        
        paragraphLabel = new JLabel ("Life Description: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;        
        c.gridx = 0;
        c.gridy = 6;
        leftPanel.add(paragraphLabel,c);

        Paragraph = new JTextArea(5,30);//creating new textfield to let user enter input
        Paragraph.setPreferredSize(new Dimension(500, 550));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridx = 3;
        c.gridy = 6;
        Paragraph.setDocument(new JTextFieldLimit(28));
        JScrollPane areaScrollPane = new JScrollPane(Paragraph,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        leftPanel.add(areaScrollPane,c);
        
        
        JLabel addressText =new JLabel("Address Information");//implementing new label
        addressText.setFont(addressText.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        leftPanel.add(addressText,c);// adding label in leftPanel to display on the panel
        
        addressLabel = new JLabel ("Street Number: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        leftPanel.add(addressLabel,c);
        
        address_StreetNo = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 8;
        leftPanel.add(address_StreetNo,c);
        address_StreetNo.setDocument(new JTextFieldLimit(28));
        
        address_StreetNameLabel = new JLabel ("Street Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        leftPanel.add(address_StreetNameLabel,c);

        address_StreetName = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 9;
        leftPanel.add(address_StreetName,c);
        address_StreetName.setDocument(new JTextFieldLimit(28));
        
        address_SuburbLabel = new JLabel ("Suburb: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        leftPanel.add(address_SuburbLabel,c);

        address_Suburb= new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 10;
        leftPanel.add(address_Suburb,c);
        address_Suburb.setDocument(new JTextFieldLimit(28));
        
        addressPostcodeLabel = new JLabel ("Postcode: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        leftPanel.add(addressPostcodeLabel,c);
       
        addressPostcode = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 11;
        leftPanel.add(addressPostcode,c);
        addressPostcode.setDocument(new JTextFieldLimit(28));
        
        JLabel relativeText = new JLabel("Relative Information");//implementing new label to show information on the panel
        relativeText.setFont(relativeText.getFont().deriveFont(Font.BOLD, 16f));//setting font size and bold for label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 12;
        leftPanel.add(relativeText,c);
        
        fatherLabel = new JLabel ("Father : ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 13;
        leftPanel.add(fatherLabel,c);
        
        
        father = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 13;
        father.setText(personData.getfather());//setting label text as according to object data 
        father.setEditable(false);//setting editable as false to not let user edit or make changes in view mode            
        leftPanel.add(father,c);
        
        
        
        motherLabel = new JLabel ("Mother : ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 14;
        leftPanel.add(motherLabel,c);
        
        
        
        
        
        mother = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 14;
        mother.setText(personData.getMother());//setting label text as according to  object data 
        mother.setEditable(false);//setting editable as false to not let user edit or make changes in view mode
        leftPanel.add(mother,c);
        
        
        
        SpouseLabel = new JLabel ("Spouse :");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 15;
        leftPanel.add(SpouseLabel,c);
        
        
        
        Spouse = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 15;
        Spouse.setText(personData.getspouse());//setting label text as according to object data 
        Spouse.setEditable(false);//setting editable as false to not let user edit or make changes in view mode
        leftPanel.add(Spouse,c);
        
        
        firstname.setText(personData.getFirstname());//setting label text as according to object data 
        firstname.setEditable(false);//setting editable as false to not let user edit or make changes in view mode
        surnameBirth.setText(personData.getsurnameBirth());//setting label text as according to object data 
        surnameBirth.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        surnameMarriage.setText(personData.getsurnameMarriage());//setting label text as according to object data 
        surnameMarriage.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        Gender.setText(personData.getGender());//setting label text as according to object data 
        Gender.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        Paragraph.setText(personData.getlifeDescription());//setting label text as according to object data 
        Paragraph.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        address_StreetNo.setText(Integer.toString(personData.getaddressStreetNo()));//setting label text as according to object data 
        address_StreetNo.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        address_StreetName.setText(personData.getaddressStreetName());//setting label text as according to object data 
        address_StreetName.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        address_Suburb.setText(personData.getsuburb());//setting label text as according to object data 
        address_Suburb.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        addressPostcode.setText(Integer.toString(personData.getpostcode()));//setting label text as according to object data 
        addressPostcode.setEditable(false);//setting editable as true to let user edit or make changes in edit mode
        JTextField childrenData[] = new JTextField[personData.getChildrenListSize()]; //creating the array of jtext field with the size of childrenlist size

        if(personData.getChildren()!=null)//only perform when there is children data to display
        {
            childrenLabel = new JLabel ("Children :");//creating new label
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 16;
            leftPanel.add(childrenLabel,c);
            
            children = new JTextField(10);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 3;
            c.gridy = 16;
            children.setText(personData.getChildren()); ////setting label text as according to object data 
            leftPanel.add(children,c);//adding textfield in leftpanel to display on the panel
            leftPanel.add( new JLabel(""));//adding label in leftpanel to display on the panel
        }
        if (personData.getChildrenListSize()!=0)
        {
        
            childrenLabel = new JLabel ("Children :");//creating new label
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 16;
            leftPanel.add(childrenLabel,c);
            for (int i = 0; i <personData.getChildrenListSize(); i++) //for loop to read until the end of children data
            {
                c.fill = GridBagConstraints.HORIZONTAL;
                c.anchor = GridBagConstraints.FIRST_LINE_END;
                c.weightx = 1;
                c.weighty = 0;
                c.gridx = 3;
                c.gridy = 16+i;
                
                childrenData[i] = new JTextField(); //creating jtextfield according to the amount of children arrry size
                childrenData[i].setText(personData.getChildrens(i).toString()); //setting label text as according to object data 
                childrenData[i].setEditable(false);//setting editable as false to not let user edit or make changes in view mode
                leftPanel.add(childrenData[i],c);//adding textfield in leftpanel to display on the panel
                leftPanel.add(new JLabel(""));//adding label in leftpanel to display on the panel
            }
        }
        
        JTextField grandchildrenData[] = new JTextField[personData.getChildrenListSize()]; //creating array of JTextField with the size of children
        
        if (personData.getgrandChildrenListSize()!=0) //proceed when there is data
        {
            childrenLabel = new JLabel ("Grand Children :");//creating new label
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 17 + personData.getChildrenListSize();
            leftPanel.add(childrenLabel,c);
            leftPanel.add(new JLabel(""));
            for (int ii = 0; ii<personData.getgrandChildrenListSize();ii++)
            {
                c.fill = GridBagConstraints.HORIZONTAL;
                c.anchor = GridBagConstraints.FIRST_LINE_END;
                c.weightx = 1;
                c.weighty = 0;
                c.gridx = 3;
                c.gridy = 17+personData.getChildrenListSize()+ii;
                grandchildrenData[ii] = new JTextField(); //creating jtextfield according to the amount of children arrry size
                grandchildrenData[ii].setText(personData.getgrandChildren(ii).toString()); //setting label text as according to object data 
                grandchildrenData[ii].setEditable(false);//setting editable as false to not let user edit or make changes in view mode
                leftPanel.add(new JLabel(""));
                leftPanel.add(grandchildrenData[ii],c);//adding textfield in leftpanel to display on the panel
                leftPanel.add(new JLabel(""));//adding label in leftpanel to display on the panel
                leftPanel.revalidate();
                leftPanel.repaint();
            }
        }
       
        
        JButton editbutton = new JButton ("Edit Details ");//implementing edit button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 18+personData.getChildrenListSize()+personData.getgrandChildrenListSize();
        leftPanel.add(editbutton,c);
        editbutton.addActionListener(new editbuttonObjListener()); //implementing action listener for edit button 
        
        
        
        JButton AddRealtiveButton = new JButton ("Add Relative");//implementing edit button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 18+personData.getChildrenListSize()+personData.getgrandChildrenListSize();
        leftPanel.add(AddRealtiveButton,c);
        AddRealtiveButton.addActionListener(new relativeButtionListener());//implementing action listener for add relative button listener
        
        
        leftPanel.setVisible(false);// setting leftpanel as false to visible on the panel
        leftPanel.setVisible(true);// setting leftpanel as false to visible on the panel
        rightPanel.setVisible(false);// setting right panel as true to visible on the panel
        rightPanel.setVisible(true);// setting right panel as true to visible on the panel
        
    }
    
    
    void editGUI(person personData)throws NullPointerException // method which perform as editmode in gui, implementing with exception
    {
        leftPanel.removeAll(); //removing all the previous data from left panel
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel editmodetext = new JLabel("Edit Mode"); //creating label to display information
        editmodetext.setFont(editmodetext.getFont().deriveFont(Font.BOLD, 17f)); // setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(editmodetext,c);// adding label in leftPanel to display on the panel

        JLabel personal = new JLabel("Person Information");//implementing new label
        personal.setFont(personal.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.add(personal,c);// adding label in leftPanel to display on the panel

        firstnameLabel = new JLabel("Name: "); //creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        leftPanel.add(firstnameLabel,c);// adding label in leftPanel to display on the panel

        firstname = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 2;
        leftPanel.add(firstname,c);// adding label in leftPanel to display on the panel
        firstname.setDocument(new JTextFieldLimit(28));

        surnameBirthLabel = new JLabel ("Surname: ");
        c.fill = GridBagConstraints.HORIZONTAL; //constraint for gribbaglayout
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        leftPanel.add(surnameBirthLabel,c);//adding label together with constraint 
        
        surnameBirth = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 3;
        leftPanel.add(surnameBirth,c);
        surnameBirth.setDocument(new JTextFieldLimit(28)); //setting word limit
        
        surnameMarriageLabel = new JLabel ("Maiden Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        leftPanel.add(surnameMarriageLabel,c);// adding data with constraint in leftPanel to display on the panel

        surnameMarriage = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 4;
        leftPanel.add(surnameMarriage,c);// adding data with constraint in leftPanel to display on the panel
        surnameMarriage.setDocument(new JTextFieldLimit(28));

        GenderLabel = new JLabel ("Gender: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        leftPanel.add(GenderLabel,c);// adding label with constraint in leftPanel to display on the panel

        Gender = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        leftPanel.add(Gender,c);// adding data together with constraint in leftPanel to display on the panel
        Gender.setDocument(new JTextFieldLimit(28));// setting limit for input word count
        
        paragraphLabel = new JLabel ("Life Description: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;        
        c.gridx = 0;
        c.gridy = 6;
        leftPanel.add(paragraphLabel,c);// adding label with constraint in leftPanel to display on the panel

        Paragraph = new JTextArea(5,30);//creating new textfield to let user enter input
        Paragraph.setPreferredSize(new Dimension(500, 550));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridx = 3;
        c.gridy = 6;
        Paragraph.setDocument(new JTextFieldLimit(28));// setting limit for input word count
        JScrollPane areaScrollPane = new JScrollPane(Paragraph,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        leftPanel.add(areaScrollPane,c);// adding data together with constraint
        
        
        JLabel addressText =new JLabel("Address Information");//implementing new label
        addressText.setFont(addressText.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        leftPanel.add(addressText,c);// adding label in leftPanel to display on the panel
        
        addressLabel = new JLabel ("Street Number: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        leftPanel.add(addressLabel,c);// adding data together with constraint
        
        address_StreetNo = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 8;
        leftPanel.add(address_StreetNo,c);// adding data together with constraint
        address_StreetNo.setDocument(new JTextFieldLimit(28));// setting limit for input word count
        
        address_StreetNameLabel = new JLabel ("Street Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        leftPanel.add(address_StreetNameLabel,c);// adding label together with constraint

        address_StreetName = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 9;
        leftPanel.add(address_StreetName,c);// adding data together with constraint
        address_StreetName.setDocument(new JTextFieldLimit(28));// setting limit for input word count
        
        address_SuburbLabel = new JLabel ("Suburb: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        leftPanel.add(address_SuburbLabel,c);// adding label together with constraint

        address_Suburb= new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 10;
        leftPanel.add(address_Suburb,c);// adding data together with constraint
        address_Suburb.setDocument(new JTextFieldLimit(28));// setting limit for input word count
        
        addressPostcodeLabel = new JLabel ("Postcode: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL; // constraint for gridbaglayout
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        leftPanel.add(addressPostcodeLabel,c);// adding label together with constraint
        
        addressPostcode = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 11;
        leftPanel.add(addressPostcode,c); // adding label together with constraint
        addressPostcode.setDocument(new JTextFieldLimit(28)); // setting limit for input word count
        
        
        JButton saveChanges = new JButton ("Save new changes ");//creating new button named save new changes
       
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 12;
        leftPanel.add(saveChanges,c);// adding label together with constraint
        
        
        saveChanges.addActionListener(new saveChangesObjListener());//implement action listener for save button to perform when it is being clicked 
        
       
        JButton cancel = new JButton ("Cancel"); //creating new button named cancel 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 12;
        leftPanel.add(cancel,c);// adding label together with constraint
        
        cancel.addActionListener(new cancelEdit());//implement action listener for cancel button
        
        
        firstname.setText(personData.getFirstname());//setting label text as according to object data 
        firstname.setEditable(false);//setting editable as false to not let user edit or make changes for name input
        surnameBirth.setText(personData.getsurnameBirth());//setting label text as according to object data 
        surnameBirth.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        surnameMarriage.setText(personData.getsurnameMarriage());//setting label text as according to object data 
        surnameMarriage.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        Gender.setText(personData.getGender());//setting label text as according to object data 
        Gender.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        Paragraph.setText(personData.getlifeDescription());//setting label text as according to object data 
        Paragraph.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        address_StreetNo.setText(Integer.toString(personData.getaddressStreetNo()));//setting label text as according to object data 
        address_StreetNo.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        address_StreetName.setText(personData.getaddressStreetName());//setting label text as according to object data 
        address_StreetName.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        address_Suburb.setText(personData.getsuburb());//setting label text as according to object data 
        address_Suburb.setEditable(true);//setting editable as true to let user edit or make changes in edit mode
        addressPostcode.setText(Integer.toString(personData.getpostcode()));//setting label text as according to object data 
        addressPostcode.setEditable(true);//setting editable as true to let user edit or make changes in edit mode

        
        leftPanel.setVisible(false);// setting leftpanel as false to visible on the panel
        leftPanel.setVisible(true);// setting leftpanel as false to visible on the panel
        rightPanel.setVisible(false);// setting rightpanel as false to visible on the panel
        rightPanel.setVisible(true);// setting rightpanel as true to visible on the panel
        
        
    }
    
   
    void addrelative()//method to implement to add relative for the person
    {
        
        leftPanel.removeAll(); //removing all the previous data from left panel
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
       
        
        String[]message = {"Father", "Mother","Spouse","Children"}; //array which contains type of relative for user to select
        
        JLabel relative = new JLabel("Add relative information");//adding new label
        relative.setFont(relative.getFont().deriveFont(Font.BOLD, 17f)); // setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(relative,c);// adding label in leftPanel to display on the panel
        
        relativelist = new<String> JComboBox(message); // adding the array into the jcombobox as String 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 0;
        leftPanel.add(relativelist,c);// adding label in leftPanel to display on the panel
        
        
        JLabel personal = new JLabel("Person Information");//implementing new label
        personal.setFont(personal.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        leftPanel.add(personal,c);// adding label in leftPanel to display on the panel

        firstnameLabel = new JLabel("Name: "); //creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        leftPanel.add(firstnameLabel,c);// adding label in leftPanel to display on the panel

        firstname = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 3;
        leftPanel.add(firstname,c);// adding label in leftPanel to display on the panel
        firstname.setDocument(new JTextFieldLimit(28));

        surnameBirthLabel = new JLabel ("Surname: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        leftPanel.add(surnameBirthLabel,c);
        
        surnameBirth = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 4;
        leftPanel.add(surnameBirth,c);
        surnameBirth.setDocument(new JTextFieldLimit(28));
        
        surnameMarriageLabel = new JLabel ("Maiden Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        leftPanel.add(surnameMarriageLabel,c);

        surnameMarriage = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 5;
        leftPanel.add(surnameMarriage,c);
        surnameMarriage.setDocument(new JTextFieldLimit(28));

        GenderLabel = new JLabel ("Gender: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 6;
        leftPanel.add(GenderLabel,c);

        Gender = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 6;
        leftPanel.add(Gender,c);
        Gender.setDocument(new JTextFieldLimit(28));
        
        paragraphLabel = new JLabel ("Life Description: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;        
        c.gridx = 0;
        c.gridy = 7;
        leftPanel.add(paragraphLabel,c);

        Paragraph = new JTextArea(5,30);//creating new textfield to let user enter input
        Paragraph.setPreferredSize(new Dimension(500, 550));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridx = 3;
        c.gridy = 7;
        Paragraph.setDocument(new JTextFieldLimit(28));
        JScrollPane areaScrollPane = new JScrollPane(Paragraph,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        leftPanel.add(areaScrollPane,c);
        
        
        JLabel addressText =new JLabel("Address Information");//implementing new label
        addressText.setFont(addressText.getFont().deriveFont(Font.BOLD, 16f));// setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        leftPanel.add(addressText,c);// adding label in leftPanel to display on the panel
        
        addressLabel = new JLabel ("Street Number: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        leftPanel.add(addressLabel,c);
        
        address_StreetNo = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 9;
        leftPanel.add(address_StreetNo,c);
        address_StreetNo.setDocument(new JTextFieldLimit(28));
        
        address_StreetNameLabel = new JLabel ("Street Name: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        leftPanel.add(address_StreetNameLabel,c);

        address_StreetName = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 10;
        leftPanel.add(address_StreetName,c);
        address_StreetName.setDocument(new JTextFieldLimit(28));
        
        address_SuburbLabel = new JLabel ("Suburb: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        leftPanel.add(address_SuburbLabel,c);

        address_Suburb= new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 11;
        leftPanel.add(address_Suburb,c);
        address_Suburb.setDocument(new JTextFieldLimit(28));
        
        addressPostcodeLabel = new JLabel ("Postcode: ");//creating new label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 12;
        leftPanel.add(addressPostcodeLabel,c);
        
        addressPostcode = new JTextField(10);//creating new text field for user input
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 12;
        leftPanel.add(addressPostcode,c);
        addressPostcode.setDocument(new JTextFieldLimit(28));
        
        JButton save = new JButton ("Save Relative"); //creating save button to save user relative input data
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 13;
        leftPanel.add(save,c);
        
        save.addActionListener(new relativeObjectListener());//implementing actionlistener for the save button to perform particular action when it is clicked

        JButton cancel = new JButton ("Cancel"); //creating cancel button to go back to last stage     
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 13;
        leftPanel.add(cancel,c);
        cancel.addActionListener( new cancelEdit());//implementing actionlistener for the cal button to perform particular action when it is clicked

         leftPanel.setVisible(false);// setting leftpanel as false to visible on the panel
         leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
        
    }

    void submitrelativedata()//method to save relative data when actionlistener is performed
    {
        
        String selected = (String)relativelist.getSelectedItem(); //getting data of what user chose the relative type
        
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        String selectedtreedata = selectedNode.toString();
        
        if (firstname.getText().isEmpty() !=true&& Gender.getText().isEmpty() !=true&&address_StreetNo.getText().isEmpty() != true && addressPostcode.getText() .isEmpty() != true) //only let user to save data when conditions are met
                               
        {
            try //try-catch block to catch exception when necessary
            {
                int Postcode = Integer.valueOf(addressPostcode.getText()); //converting string to int
                int streetNum = Integer.valueOf(address_StreetNo.getText());//converting string to int

               
                
                if (selected.equalsIgnoreCase("mother")) //if-else condition to check what relative type does user chose and to perform only  mother relative is not created in the data
                {
                    if (motherDatacheck!=true)
                    {
                        if(Gender.getText().equalsIgnoreCase("female"))//to let user save data only when mom gender is female
                        {
                            person motherDetail = new person();//creating new person object
                            motherDetail.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                                Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode); //collecting and saving input user data from text field 
                            motherDetail.setChildren(personData.getFirstname());//setting children method                      
                            motherDetail.setSpouse(personData.getfather());//setting mother method
                            personData.addMotherData(motherDetail);// adding all mother data to persondata object 
                            personData.setmother(personData.getMotherData().getFirstname());//setting mother name in person data after mother relative is created
                            motherDatacheck = true; //setting flag as true after mom data is created to let user to enter 1 mother only
                            JOptionPane.showMessageDialog(leftPanel, "Successfully added a mother relative");//Messagebox to let user know what has been done
                            loadData(personData);//calling load data object to show view mode
                            JTreeInformation();//calling method to display tree data 
                            leftPanel.setVisible(false);// setting leftpanel as false to hide on the panel
                            leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
                            rightPanel.setVisible(false);// setting rightPanel as false to hide on the panel
                            rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel
                        }
                        else // if user input gender becomes male for mom gender
                        {
                            JOptionPane.showMessageDialog(leftPanel, "Input Gender type should only be Female for  Mother relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for mom relative only
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(leftPanel, "only 1 mother is allowed to add data for selected family member","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                    }
                }
                else if (selected.equalsIgnoreCase("father"))//if-else condition to check what relative type does user chose and to perform only  whether father relative is not created in the data
                {
                    if (fatherDatacheck!=true)
                    {
                        if(Gender.getText().equalsIgnoreCase("male"))//to let user save data only when father gender is male
                        {
                            person fatherDetail = new person();//creating new person object to store father data
                            fatherDetail.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                                Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);//collecting and saving input user data from text field 
                            fatherDetail.setChildren(personData.getFirstname());//setting children data if it is available
                            fatherDetail.setSpouse(personData.getMother());//setting spouse data if it is available
                            personData.addFatherData(fatherDetail);//adding father data as a father person object
                            personData.setfather(personData.getFatherData().getFirstname());//setting father name after father object is created


                            fatherDatacheck = true;//setting flag as true to know whether father data has already created
                            JOptionPane.showMessageDialog(leftPanel, "Successfully added a father relative");//Messagebox to let user know what has been done
                            loadData(personData);//calling load data object to show view mode
                            JTreeInformation();//calling method to display tree data 

                            leftPanel.setVisible(false);// setting leftpanel as false to hide on the panel
                            leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
                            rightPanel.setVisible(false);// setting rightPanel as false to hide on the panel
                            rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(leftPanel, "Input Gender type should only be Male for Father relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for mom relative only
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(leftPanel, "only 1 father is allowed to add data for selected family member","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                    }

                }
                
                else if (selected.equalsIgnoreCase("spouse"))//if-else condition to check what relative type does user chose and to perform only when father relative is not created in the data
                {
                    if (selectedtreedata.equals(personData.getFirstname()) )// condition to check whether selected tree node equals with root person and there is no previous spouse data added 
                    {
                        if (spouseDatacheck!=true)
                        {
                            if((personData.getGender().equalsIgnoreCase("male") && Gender.getText().equalsIgnoreCase("female") || (personData.getGender().equalsIgnoreCase("female") && Gender.getText().equalsIgnoreCase("male")) ))//to check conditon not to let user enter male & male, female & female for spouse data input
                            {
                                person spouseData = new person();//creating new person object to store spouse data
                                spouseData.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                                    Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);//collecting and saving input user data from text field 
                                spouseData.setSpouse(personData.getFirstname());
                                personData.addSpouseData(spouseData);//adding all spouse data and create as an  person object
                                personData.setSpouse(personData.getSpouseData().getFirstname()); //adding spouse name
                                spouseDatacheck =true;//set flag as true after a spouse is created successfully
                                JOptionPane.showMessageDialog(leftPanel, "Successfully added a spouse relative");//Messagebox to let user know what has been done
                                loadData(personData);//calling load data object to show view mode
                                JTreeInformation();//calling load data object to show view mode
                                leftPanel.setVisible(false);// setting leftpanel as false to hide on the panel
                                leftPanel.setVisible(true);// setting leftpanel as false to visible on the panel
                                rightPanel.setVisible(false);// setting rightPanel as false to hide on the panel
                                rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(leftPanel, "Please enter matched gender input\nFor example, Male & Female (OR) Female & Male","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                            } 
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(leftPanel, "only 1 spouse is allowed to add data for selected family member","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                        }
                    }
                    
                        
                    else if(selectedtreedata.equals(personData.getFatherData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any spouse relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else if(selectedtreedata.equals(personData.getMotherData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any spouse relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else if(selectedtreedata.equals(personData.getSpouseData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any spouse relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else
                    {
                        boolean check = false;
                        for (int z=0; z<personData.getChildrenListSize();z++)
                        {
                            if(selectedtreedata.equals(personData.getChildrens(z).toString()) && personData.getChildrens(z).getspouse() == null)// condition to check whether selected tree node equals with root child person and there is no previous child spouse data added 
                            {
                                if((personData.getChildrens(z).getGender().equalsIgnoreCase("male") && Gender.getText().equalsIgnoreCase("female") || (personData.getChildrens(z).getGender().equalsIgnoreCase("female") && Gender.getText().equalsIgnoreCase("male")) ))//to check conditon not to let user enter male & male, female & female for spouse data input
                                {
                                    person childWife = new person();
                                    childWife.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                            Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);//collecting and saving input user data from text field 
                                    
                                    childWife.setSpouse(personData.getChildrens(z).toString());
                                    personData.addChildSpouse(childWife);
                                    personData.getChildrens(z).setSpouse(firstname.getText());
                                    JOptionPane.showMessageDialog(leftPanel, "Successfully added a spouse relative");
                                    check = true;
                                    loadData(personData); //calling load data object to show in view mode
                                    JTreeInformation();
                                    leftPanel.setVisible(false);// setting leftpanel as false to hide on the panel
                                    leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
                                    rightPanel.setVisible(false);// setting rightPanel as false to hide on the panel
                                    rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(leftPanel, "Please enter matched gender input\nFor example, Male & Female (OR) Female & Male","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                                }

                            }
                        }
                        if (check !=true)
                        {
                            JOptionPane.showMessageDialog(leftPanel, "Error : Cannot save the spouse data","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user to enter female for spouse relative only
                        }
                    }
                }
                
                else if (selected.equalsIgnoreCase("children"))//if-else condition to check what relative type does user chose and perform if user select it as children
                {
                    
                    if (selectedtreedata.equals(personData.getFirstname()))
                    {
                        person childData = new person();//creating new person object to store child data
                        childData.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                            Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);//collecting and saving input user data from text field 
                        childData.setfather(personData.getFirstname());//adding father name for children if available
                        childData.setmother(personData.getspouse());//adding mother name for children if available
                        personData.addChildren(childData); //adding child data as the person object
                        JOptionPane.showMessageDialog(leftPanel, "Successfully added a child relative");
                        loadData(personData); //calling load data object to show in view mode
                        JTreeInformation();//calling tree data to show in view mode
                    }
                    else if(selectedtreedata.equals(personData.getFatherData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any child relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else if(selectedtreedata.equals(personData.getMotherData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any child relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else if(selectedtreedata.equals(personData.getSpouseData().toString()))
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Sorry. This family member is not allowed to add any child relative","Input Warning",JOptionPane.WARNING_MESSAGE);//error mesage to tell user 
                    }
                    else
                    {
                        for (int z=0; z<personData.getChildrenListSize();z++)
                        {
                            if(selectedtreedata.equals(personData.getChildrens(z).toString()))
                            {
                                person grandChild = new person();
                                grandChild.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                            Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);
                                grandChild.setfather(personData.getChildrens(z).toString());//collecting and saving input user data from text field 
                                personData.addGrandChildren(grandChild);//adding child data as the person object
                                JOptionPane.showMessageDialog(leftPanel, "Successfully saved child relative");
                                loadData(personData); //calling load data object to show in view mode
                                JTreeInformation(); //calling tree data to show in view mode
                            }
                        }
                    }

                    leftPanel.setVisible(false);// setting leftpanel as false to invisible on the panel
                    leftPanel.setVisible(true);// setting leftpanel as true to visible on the panel
                    rightPanel.setVisible(false);// setting rightPanel as false to invisible on the panel
                    rightPanel.setVisible(true);// setting rightPanel as true to visible on the panel

                }
                
            }
            catch( NullPointerException | NumberFormatException | IOException e) //catching exception such as null input, wrong input (for eg input being a string in which required as int )
            {
                JOptionPane.showMessageDialog(leftPanel, "Street Number and postcode input must be Numbers only ","Input Invaild Error",JOptionPane.ERROR_MESSAGE);//displaying error message as the message popup box
            }
        }
        else
        {
            JOptionPane.showMessageDialog(leftPanel, "Gender must be only Female for Mother relative input\nGender must be only Male for Father relative input \nStreet Number and postcode input must be number only\nOnly 1 parent, 1 mother and 1 father relative and 1 spouse input are allowed to save ","Input Notice",JOptionPane.WARNING_MESSAGE);//displaying error message as the message popup box
        }
    }

    void createData()//method which will collect every input in creating new person and save it as new person object
    {
        if (firstname.getText().isEmpty() !=true&& Gender.getText().isEmpty() !=true&&address_StreetNo.getText().isEmpty() != true && addressPostcode.getText() .isEmpty() != true)
                               
        {
           
            try//try-catch block to know if there is any exception from user input 
            {

                personData = new person(); //creating new person object
                int Postcode = Integer.valueOf(addressPostcode.getText());//converting String to int
                int streetNum = Integer.valueOf(address_StreetNo.getText());//converting String to int
                
                if (Gender.getText().equalsIgnoreCase("male")||Gender.getText().equalsIgnoreCase("female"))
                {
                    personData.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),Paragraph.getText(),
                                    streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode);//creating object by collecting data from text field input

                    personInputCheck = true;//set flag as true after a person is created successfully
                    //personData.setlifeDescription();


                    motherDatacheck = false;//resetting mother flag_check as false
                    fatherDatacheck = false;//resetting father flag_check as false
                    spouseDatacheck = false;//resetting spouse flag_check as false
                    childspouseDatacheck = false;
                    JOptionPane.showMessageDialog(leftPanel, "Successfully saved");
                    rightPanel.setVisible(false);// setting rightpanel as false to invisible on the panel
                    rightPanel.setVisible(true);// setting rightpanel as true to visible on the panel
                    JTreeInformation(); //calling method to display tree 
                }
                else
                {
                    JOptionPane.showMessageDialog(leftPanel, "Gender must be either Male or Female only  ","Input Invaild Error",JOptionPane.ERROR_MESSAGE); //display error message as pop-up message to let user know about error
                }

            }
            catch( NullPointerException | NumberFormatException | IOException e) //catching exception such as null input, wrong input (for eg input being a string in which required as int )
            {
                JOptionPane.showMessageDialog(leftPanel, "Street Number and postcode input must be Numbers only ","Input Invaild Error",JOptionPane.ERROR_MESSAGE); //display error message as pop-up message to let user know about error
            }
        }
        else //if user doesn't enter input for name, gender , street number , and postcode 
        {
            JOptionPane.showMessageDialog(leftPanel, "Do not leave Blanks for Name, Gender, Street Number and Postcode Field\nGender must be either Male or Female\nStreet Number and postcode input must be number only ","Input Notice",JOptionPane.WARNING_MESSAGE);//display error message as pop-up message to let user know about error
        }
    } 



    
    void saveeditChanges(person personData) //method which is used to save all the changes in the edit mode
    {
       
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        String selectedtreedata = selectedNode.toString();
        
        //try-catch block
        if (firstname.getText().isEmpty() !=true&& Gender.getText().isEmpty() !=true&&address_StreetNo.getText().isEmpty() != true && addressPostcode.getText() .isEmpty() != true
            )
                               
        {
            try
            {
                //personData= new person();
                int Postcode = Integer.valueOf(addressPostcode.getText());//converting string to int 
                int streetNum = Integer.valueOf(address_StreetNo.getText());//converting string to int 
                
                if (selectedtreedata.equals(personData.toString()))// only save changes according to the selected treenote
                {
                    if (Gender.getText().equalsIgnoreCase("male")||Gender.getText().equalsIgnoreCase("female"))
                    {
                        personData.createPerson(firstname.getText(),surnameBirth.getText(),surnameMarriage.getText(), Gender.getText(),
                                            Paragraph.getText(),streetNum,address_StreetName.getText(),address_Suburb.getText(),Postcode); //creating object by collecting data from text field input
                        JOptionPane.showMessageDialog(leftPanel, "Successfully saved"); //message pop-up box to let user know when data is saved and changed successfully
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(leftPanel, "Gender must be either Male or Female Only","Input Invaild Error",JOptionPane.ERROR_MESSAGE);//display error message as pop-up message to let user know about error
                    }
                        
                }
                else
                {
                    JOptionPane.showMessageDialog(leftPanel,"Error");//error message as message pop-up box
                }

                loadData(personData); //calling load data object to show in view mode
                JTreeInformation();
                leftPanel.setVisible(false);// setting leftPanel as false to invisible on the panel
                leftPanel.setVisible(true);// setting leftPanel as true to visible on the panel
                rightPanel.setVisible(false);// setting rightpanel as false to invisible on the panel
                rightPanel.setVisible(true);// setting rightpanel as true to invisible on the panel

            }
            catch( NullPointerException | NumberFormatException | IOException e)//catching exception such as null input, wrong input (for eg input being a string in which required as int )
            {
                JOptionPane.showMessageDialog(leftPanel, "Street Number and postcode input must be Numbers only ","Input Invaild Error",JOptionPane.ERROR_MESSAGE);//display error message as pop-up message to let user know about error
            }
        }
        else
        {
            JOptionPane.showMessageDialog(leftPanel, "Do not leave blanks for Name, Gender, Street Number and Postcode Field\nGender must be either Male or Female\nStreet Number and postcode input must be number only ","Input Notice",JOptionPane.WARNING_MESSAGE);//display error message as pop-up message to let user know about error
        }
        
    }
    

    void JTreeInformation() //method to create and generate family tree according to data
    {
        leftPanel.removeAll();//removing all the previous data from left panel
        rightPanel.removeAll();//removing all the previous data from right panel
        
        leftPanel.setLayout(new GridBagLayout()); //setting left panel layout as gridbaglayout
        GridBagConstraints c = new GridBagConstraints(); //adding some constraints to match and restrict place
        
        JLabel viewLabel = new JLabel ("Select a family data from the tree to view information"); //creating new label to show information
        viewLabel.setFont(viewLabel.getFont().deriveFont(Font.BOLD, 15f)); // setting label size and font as bold 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 0.5;
        c.weighty = 1;
        c.insets = new Insets(10,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(viewLabel,c);
        
        Icon leafIcon = new ImageIcon("leaf.gif");//implementing leaf icon
        Icon openIcon = new ImageIcon("open.gif");//implementing open icon
        Icon closedIcon = new ImageIcon("closed.gif");//implementing closed icon
        
        JLabel info = new JLabel("Family Tree Information");//creating new label to show information
        info.setFont(info.getFont().deriveFont(Font.BOLD, 14f));// setting label size and font as bold 
        
        UIManager.put("Tree.leafIcon", leafIcon); //setting the icon under UImanager
        UIManager.put("Tree.openIcon", openIcon);//setting the icon under UImanager
        UIManager.put("Tree.closedIcon", closedIcon);//setting the icon under UImanager
        
        if (personData.getFirstname() !=null) //display tree only if object contains data
        {
            rightPanel.add(info); //adding label into leftpanel
            rightPanel.add(new JLabel (""));//adding label into leftpanel
            rightPanel.add(new JLabel (""));//adding label into leftpanel
            DefaultMutableTreeNode Person1 = new DefaultMutableTreeNode(personData); //creating tree node
            
            if (personData.getFatherData() !=null || personData.getMotherData() !=null) //proceed to creating parent node only there is parent data
            {
                DefaultMutableTreeNode PersonParent = new DefaultMutableTreeNode ("Parent"); //creating tree node
                Person1.add(PersonParent); //adding node 
                if (personData.getFatherData() !=null )
                {

                        DefaultMutableTreeNode PersonFatherLabel = new DefaultMutableTreeNode ("Father");//creating tree node
                        DefaultMutableTreeNode PersonFather = new DefaultMutableTreeNode(personData.getFatherData());//creating tree node depending on data
                        PersonParent.add(PersonFatherLabel);//adding node into tree
                        PersonFatherLabel.add(PersonFather);//adding node into tree

                }
                if (personData.getMotherData()!=null)
                {
                    DefaultMutableTreeNode PersonMotherLabel = new DefaultMutableTreeNode ("Mother");//creating tree node
                    DefaultMutableTreeNode PersonMother = new DefaultMutableTreeNode(personData.getMotherData());//creating tree node depending on data
                    PersonParent.add(PersonMotherLabel);//adding node
                    PersonMotherLabel.add(PersonMother);//adding node
                }
            }
            if (personData.getSpouseData()!=null)//proceed to creating parent node only there is spouse data
            {
                DefaultMutableTreeNode PersonSpouse = new DefaultMutableTreeNode("Spouse");//creating tree node
                DefaultMutableTreeNode PersonSpouse1 = new DefaultMutableTreeNode(personData.getSpouseData());//creating tree node depending on data
                Person1.add(PersonSpouse);//adding node
                PersonSpouse.add(PersonSpouse1);//adding node
                
                
            }    

            if(personData.getChildrenListSize() !=0) //proceed to creating parent node only there is children data
            {
                DefaultMutableTreeNode PersonChildren = new DefaultMutableTreeNode("Children");//creating tree node
                for(int i=0; i<personData.getChildrenListSize();i++) //for loop array to read until the end of child data array 
                {
                    DefaultMutableTreeNode PersonChildren1 = new DefaultMutableTreeNode(personData.getChildrens(i));//creating tree node depending on data
                    Person1.add(PersonChildren); //adding node
                    PersonChildren.add(PersonChildren1); //adding node
                    
                    if (personData.getChildSpouseSize() !=0)//proceed to creating parent node only there is children wife data
                    {             
                        for (int ii = 0; ii<personData.getChildSpouseSize();ii++)
                        {
                            if (personData.getChildrens(i).toString().equals(personData.getchildSpouse(ii).getspouse()))
                            {
                                DefaultMutableTreeNode childSpouse = new DefaultMutableTreeNode("Spouse");//creating tree node
                                DefaultMutableTreeNode childSpouse1 = new DefaultMutableTreeNode(personData.getchildSpouse(ii));//creating tree node
                                PersonChildren1.add(childSpouse);//adding node 
                                childSpouse.add(childSpouse1);//adding node 
                            }
                        }
                    }
                    
                    
                    
                    if (personData.getgrandChildrenListSize()!=0)//proceed to creating parent node only there is grandchild data
                    {
                        DefaultMutableTreeNode PersonGrandChildren = new DefaultMutableTreeNode("Children");//creating tree node
                        for(int ii=0; ii<personData.getgrandChildrenListSize();ii++)
                        {
                            if (personData.getgrandChildren(ii).getfather().equals(personData.getChildrens(i).toString())) // add the child node as only if child dad and person name is the same
                            {
                                DefaultMutableTreeNode PersonGrandChildren1 = new DefaultMutableTreeNode(personData.getgrandChildren(ii));//creating tree node
                                PersonChildren1.add(PersonGrandChildren);//adding node 
                                PersonGrandChildren.add(PersonGrandChildren1);//adding node 
                            }
                        }
                    }
                }
                
                
            }
            tree = new JTree(Person1); //creating new tree
            tree.putClientProperty("JTree.lineStyle", "Angled"); //implementing tree design
            tree.addTreeSelectionListener(new SelectionListener()); //adding tree listener to know what user is selected
            rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS)); //setting up the rightpanel layout
            rightScrollPane.getViewport().add(tree);//adding tree into rightScrollPane
            rightPanel.add(rightScrollPane);//adding rightScrollPane into right panel
        }
        else
        {
            System.out.println("There is no data inside the tree"); //error message
        }
        leftPanel.setVisible(false);// setting leftPanel as false to hide on the panel
        leftPanel.setVisible(true);// setting leftPanel as true to make it visible on the panel
        rightPanel.setVisible(false);// setting leftPanel as false to hide on the panel
        rightPanel.setVisible(true);// setting leftPanel as true to make it visible on the panel
    }
    
    void saveTreeFile() //method to save the tree data file as .bin format
    {
        
        if (personData!=null) //proceed only there is data to save
        {
            
            try //try-catch block to know any exception from user
            {
                JFileChooser file = new JFileChooser("."); //file chooser to pop-up folder directory 

                FileNameExtensionFilter filter = new FileNameExtensionFilter("BINARY FILES", "bin"); //to filter file only as .bin format
                file.setFileFilter(filter); //setting file filter to show .bin only 
                file.setFileSelectionMode(file.FILES_AND_DIRECTORIES);//showing directory as both file and directories
                file.setDialogTitle("Save file"); //setting pop-up dialog title
                int returnVal = file.showSaveDialog(this); 

                if (returnVal == JFileChooser.APPROVE_OPTION)  //if the file chooser is successfully open
                {
                    File filename = file.getSelectedFile(); //getting file name
                    if (!filename.getName().toLowerCase().endsWith(".bin"))  //if user input filename does not contains ".bin " as to prevent .bin duplication
                    {
                        filename = new File(filename.getParentFile(), filename.getName() + ".bin"); //adding file name + ".bin"
                        
                    }  
                    else
                    {
                        filename = new File(filename.getParentFile(), filename.getName()); //getting filename
                    }
                    try
                    {
                        if(!filename.exists()) //if user input filename does not exists or doesn't same with other file 
                        {
                            writeObject(filename.getName()); //invoking method which will do serialisation
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Cannot save file: same file name exists"); // error pop-up message to let user know that same filename exists
                        }
                    }
                    catch (IOException e) //if user enter wrong file format
                    {
                        JOptionPane.showMessageDialog(rightPanel, "Invalid type format"); // error pop-up message to let user know that file type is invalid
                    } 

                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(rightPanel, "Invalid type format"); // error pop-up message to let user know that file type is invalid
            }
          
        }
        else
        {
            JOptionPane.showMessageDialog(rightPanel, "No available tree to save"); // error pop-up message to let user know that there is no data to save
        }
        
    }
    
        
    void openfile() //method which perform for opening and load the file
    {
        JFileChooser file = new JFileChooser(".");//file chooser to pop-up folder directory 
        
        
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BINARY FILES", "bin");//to filter file only as .bin format
        file.setFileFilter(filter);//setting file filter to show .bin only 
        file.setFileSelectionMode(file.FILES_AND_DIRECTORIES);//showing directory as both file and directories
        int returnVal = file.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) //if the file chooser is successfully open
        {
            try
            {
                readFile(file.getSelectedFile().getAbsolutePath()); //method which will read the file and do deserialisation
       
            }
            catch(ClassNotFoundException | IOException e)//catching the exception when user open invalid file
            {
                 JOptionPane.showMessageDialog(null, "Invalid: Cannot open file","Wrong file Format",JOptionPane.ERROR_MESSAGE);// error pop-up message to let user know that open file type is invalid
            }    
            
        }
        
    }
    void writeObject(String filename)throws IOException //method which will do serialisation and save it as the .bin file
    {
        
        ObjectOutputStream objectOutputStream = new ObjectOutputStream (new FileOutputStream(filename)); //implementing object output strem

        objectOutputStream.writeObject(personData); //serialised as the object
        objectOutputStream.close(); //closing the ObjectOutputStream
        JOptionPane.showMessageDialog(null, "Successfully saved the "+filename+" file");

    }
    void readFile(String name) throws IOException, ClassNotFoundException //method which will do de-serialisation and load the .bin file
    {

        rightPanel.removeAll();//removing every previous data that has saved in the right panel
        leftPanel.removeAll();//removing every previous data that has saved in the left panel
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream (new FileInputStream (name));//implementing object input strem 
            personData = null; //resetting the person object and clearing data
            personData = new person(); //creating person object
            personData = (person)objectInputStream.readObject(); // doing object de-serialisation and putting data into the object
            objectInputStream.close(); //closing ObjectInputStream to let the program know that de-serialised has been completed
            JOptionPane.showMessageDialog(null, "Successfully loaded the "+name+" file");
        }
        catch(ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid type format");// error pop-up message to let user know that open file type is invalid
        }
        JTreeInformation();//displaying tree data according to object that has been de-serialised
    }
}