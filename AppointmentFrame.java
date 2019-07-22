
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AppointmentFrame extends JFrame{

	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 800;

	private Calendar date;
	private ArrayList<Appointment> appointments;

	SimpleDateFormat simpleFormat;
	SimpleDateFormat hmFormat;

	JTextArea description;

	JTextField day;
	JTextField month;
	JTextField year;
	JTextField hour;
	JTextField minute;


	//Label at top containing selected date
	private JLabel label;

	//Placed in scrollpane
	private JTextArea textarea;

	//Scrollpane containing appointment information on selected date
	private JScrollPane scrollpane;

	//Control panel container for date, action, and description sub-panels
	private JPanel controlPanel;

	public AppointmentFrame(){

		//SimpleDateFormat Objects
		simpleFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
		hmFormat = new SimpleDateFormat("HH:mm");

		//ArrayList of Appointment objects
		appointments = new ArrayList<Appointment>();

		//Initially set date to today's date
		date = new GregorianCalendar();

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLayout(new BorderLayout());

		//Display window label
		label = new JLabel(simpleFormat.format(date.getTime()));
		add(label, BorderLayout.NORTH);

		//Display scrollpane
		textarea = new JTextArea(10,10);
		scrollpane = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollpane, BorderLayout.CENTER);

		//Add control panel to frame
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());

		createDatePanel();
		createActionPanel();
		createDescriptionPanel();

		add(controlPanel, BorderLayout.SOUTH);
	}

	//Create Date Panel
	public void createDatePanel(){

		JPanel datePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel dateEntryPanel = new JPanel();
		JPanel showPanel = new JPanel();

		JTextArea description;

		JButton forward = new JButton(">");
		JButton back = new JButton("<");
		JButton show = new JButton("Show");

		JLabel d = new JLabel("Day");
		JLabel m = new JLabel("Month");
		JLabel y = new JLabel("Year");

		day = new JTextField(2);
		month = new JTextField(2);
		year = new JTextField(4);

		class showListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  
				date.set(Integer.parseInt(year.getText()), Integer.parseInt(month.getText()) - 1, Integer.parseInt(day.getText()));
				label.setText(simpleFormat.format(date.getTime()));
				printAppointments(Integer.parseInt(day.getText()), Integer.parseInt(month.getText()) - 1, Integer.parseInt(year.getText()));
			}
		}

		class forwardListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  
				date.add(Calendar.DAY_OF_MONTH, 1);
				label.setText(simpleFormat.format(date.getTime()));
				printAppointments(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
			}
		}

		class backListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  
				date.add(Calendar.DAY_OF_MONTH, -1);
				label.setText(simpleFormat.format(date.getTime()));
				printAppointments(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
			}
		}

		//NORTH, CENTER, SOUTH Layout
		datePanel.setLayout(new BorderLayout());
		//Panel for < > buttons
		buttonPanel.setLayout(new GridLayout(1,2));
		//Panel for line of JTextFields and JLabels 
		dateEntryPanel.setLayout(new FlowLayout());
		//Panel to make Show a small, centered button
		showPanel.setLayout(new FlowLayout());

		//Adding < > buttons to NORTH
		buttonPanel.add(back, BorderLayout.WEST);
		back.addActionListener(new backListener());
		buttonPanel.add(forward, BorderLayout.EAST);
		forward.addActionListener(new forwardListener());
		datePanel.add(buttonPanel, BorderLayout.NORTH);

		//Adding appropriate JTextFields and JLabels to CENTER
		dateEntryPanel.add(d);
		dateEntryPanel.add(day);
		dateEntryPanel.add(m);
		dateEntryPanel.add(month);
		dateEntryPanel.add(y);
		dateEntryPanel.add(year);
		datePanel.add(dateEntryPanel, BorderLayout.CENTER);

		//Adding Show button
		show.addActionListener(new showListener());
		showPanel.add(show, BorderLayout.CENTER);
		datePanel.add(showPanel, BorderLayout.SOUTH);

		//Adding datePanel to controlPanel
		datePanel.setBorder(new TitledBorder("Date"));
		controlPanel.add(datePanel, BorderLayout.NORTH);
	}

	//Create Action Panel
	public void createActionPanel(){

		JPanel actionPanel = new JPanel();
		JPanel hmPanel = new JPanel();
		JPanel ccPanel = new JPanel();

		JLabel h = new JLabel("Hour");
		JLabel m = new JLabel("Minute");
		JLabel empty = new JLabel("\n");

		hour = new JTextField(4);
		minute = new JTextField(4);

		JButton create = new JButton("CREATE");
		JButton cancel = new JButton("CANCEL");

		class createListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  
				if(minute.getText().equals("")){
					createAppointment(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hour.getText()), 00);
				}
				else{
					createAppointment(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hour.getText()), Integer.parseInt(minute.getText()));
				}
			}
		}

		class cancelListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{
				if(minute.getText().equals("")){
					cancelAppointment(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hour.getText()), 00);
					printAppointments(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
				}
				else{
					cancelAppointment(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hour.getText()), Integer.parseInt(minute.getText()));
					printAppointments(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
				}
			}
		}	

		//NORTH, CENTER (blank), SOUTH Layout
		actionPanel.setLayout(new BorderLayout());
		hmPanel.setLayout(new FlowLayout());
		ccPanel.setLayout(new FlowLayout());

		//Adding JTextFields and JLabels to hmPanel
		hmPanel.add(h);
		hmPanel.add(hour);
		hmPanel.add(m);
		hmPanel.add(minute);
		actionPanel.add(hmPanel, BorderLayout.NORTH);

		//For the space in between
		actionPanel.add(empty, BorderLayout.CENTER);

		//Adding CREATE and CANCEL buttons to ccPanel
		create.addActionListener(new createListener());
		cancel.addActionListener(new cancelListener());
		ccPanel.add(create);
		ccPanel.add(cancel);
		actionPanel.add(ccPanel, BorderLayout.SOUTH);

		//Adding actionPanel to controlPanel
		actionPanel.setBorder(new TitledBorder("Action"));
		controlPanel.add(actionPanel, BorderLayout.CENTER);
	}

	//Create Description Panel
	public void createDescriptionPanel(){

		JPanel dPanel = new JPanel();
		description = new JTextArea(5, 20);

		//Add JTextArea to dPanel
		dPanel.add(description);

		//Add dPanel to controlPanel
		dPanel.setBorder(new TitledBorder("Description"));
		controlPanel.add(dPanel, BorderLayout.SOUTH);
	}

	//Clear textarea
	public void clear(){
		textarea.setText("");
	}

	//Clear description area
	public void clearDesc(){
		description.setText("");
	}

	//Print appointment
	public void printAppointments(int day, int month, int year){
		clear();
		for(int i = 0; i < appointments.size(); i++){
			if(appointments.get(i).occursOn(day, month, year)){
				textarea.append(hmFormat.format(appointments.get(i).getDate().getTime()) + " " + appointments.get(i).getDescription() + "\n\n");
			}
		}
		setVisible(true);
	}

	//Create appointment
	public void createAppointment(int year, int month, int day, int hour, int minute){
		//Check if an appointment with the same time exists
		for(int i = 0; i < appointments.size(); i++){
			if(appointments.get(i).occursOn(year, month, day, hour, minute)){
				description.setText("CONFLICT!!");
				return;
			}

		}
		//If not, create appointment
		clear();
		appointments.add(new Appointment(year, month, day, hour, minute, description.getText()));
		Collections.sort(appointments);
		printAppointments(day, month, year);
		clearDesc();
	}

	public void cancelAppointment(int year, int month, int day, int hour, int minute){

		for(int i = 0; i < appointments.size(); i++){

			if(appointments.get(i).occursOn(year, month, day, hour, minute)){
				appointments.remove(i);
				clearDesc();
				return;
			}
		}
	}
}
