import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

//import MakeJTable.ScanResult;

public class IPScannerTest extends JFrame {
	private JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel jp4 = new JPanel(new GridLayout(0,1));
	private JPanel jp5 = new JPanel(new GridLayout(0,1));
	private JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private	JLabel l1 = new JLabel("IP Range:");
	private	JLabel l2 = new JLabel("to");
	private	JLabel l3 = new JLabel("Hostname:");
	private	JComboBox<String> jc1 = new JComboBox<String>();
	private	JComboBox<String> jc2 = new JComboBox<String>();
	private	JButton jb1 = new JButton("¡è IP");
	private	JButton jb2 = new JButton("¡æ Start");
	
	JTextField t1 = new JTextField(10);
	JTextField t2 = new JTextField(10);
	JTextField t3 = new JTextField(10);
	//Object[][] stats = initializeTableData();
	private String titles[] = new String[] {
			"IP","Ping","TTL","Hostname","Port"
	};
	
	
	// Æ÷Æ® ½ºÄµ¿ë ¸â¹ö º¯¼ö
	String severAddr;
	int port;

	
	private JTable jT = new JTable();
	static DefaultTableModel dTM = new DefaultTableModel();
	static IPScannerTest pg;
	String fixedIp;
	public IPScannerTest() {
		super("AngryIPScanner");
		// MakeJTable();
		dTM = new DefaultTableModel(null, titles) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jT = new JTable(dTM);
		
		
		//menu begin
		
		JMenuBar menubar = new JMenuBar();
		
		setJMenuBar(menubar);
		
		JMenu scanMenu = new JMenu("Scan");
		scanMenu.setMnemonic('S');
		JMenu gotoMenu = new JMenu("Go to");
		gotoMenu.setMnemonic('G');
		JMenu commandsMenu = new JMenu("Commands");
		commandsMenu.setMnemonic('C');
		JMenu favoritesMenu = new JMenu("Favorites");
		favoritesMenu.setMnemonic('F');
		JMenu toolsMenu = new JMenu("Tools");
		toolsMenu.setMnemonic('T');
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		
		menubar.add(scanMenu);
		menubar.add(gotoMenu);
		menubar.add(commandsMenu);
		menubar.add(favoritesMenu);
		menubar.add(toolsMenu);
		menubar.add(helpMenu);
		
		JMenuItem scan1 = new JMenuItem("Load from file...");
		JMenuItem scan2 = new JMenuItem("Export all");
		JMenuItem scan3 = new JMenuItem("Export selection...");
		JMenuItem scan4 = new JMenuItem("Quit");
		
		scan4.addActionListener(new ActionListener() {
			
			 public void actionPerformed(ActionEvent e) {
	          System.exit(0); }
		});

		JMenuItem goto1 = new JMenuItem("Next alive host");
		JMenuItem goto2 = new JMenuItem("Next open port");
		JMenuItem goto3 = new JMenuItem("Next dead host");
		JMenuItem goto4 = new JMenuItem("Previous alive host");
		JMenuItem goto5 = new JMenuItem("Previous open port");
		JMenuItem goto6 = new JMenuItem("Previous dead host");
		JMenuItem goto7 = new JMenuItem("Find..");
		
		JMenuItem commands1 = new JMenuItem("show details");
		JMenuItem commands2 = new JMenuItem("Rescan IP(s)");
		JMenuItem commands3 = new JMenuItem("Delete IP(s)");
		JMenuItem commands4 = new JMenuItem("Copy IP");
		JMenuItem commands5 = new JMenuItem("Copy details");
		JMenuItem commands6 = new JMenuItem("Open");
		
		JMenuItem favorites1 = new JMenuItem("Add current...");
		JMenuItem favorites2 = new JMenuItem("Manage favorites...");
		
		JMenuItem tools1 = new JMenuItem("Preferences...");
		JMenuItem tools2 = new JMenuItem("Fetchers...");
		JMenuItem tools3 = new JMenuItem("Selection");
		JMenuItem tools4 = new JMenuItem("Scan statistics");
		
		JMenuItem help1 = new JMenuItem("Getting Started");
		JMenuItem help2 = new JMenuItem("Official Website");
		JMenuItem help3 = new JMenuItem("FAQ");
		JMenuItem help4 = new JMenuItem("Report an issue");
		JMenuItem help5 = new JMenuItem("Plugins");
		JMenuItem help6 = new JMenuItem("Command-line usage");
		JMenuItem help7 = new JMenuItem("Check for newer version...");
		JMenuItem help8 = new JMenuItem("About");
		
		JScrollPane jSP = new JScrollPane(jT);
		scanMenu.add(scan1);
		scanMenu.add(scan2);
		scanMenu.add(scan3);
		scanMenu.addSeparator();
		scanMenu.add(scan4);
		
		gotoMenu.add(goto1);
		gotoMenu.add(goto2);
		gotoMenu.add(goto3);
		gotoMenu.addSeparator();
		gotoMenu.add(goto4);
		gotoMenu.add(goto5);
		gotoMenu.add(goto6);
		gotoMenu.addSeparator();
		gotoMenu.add(goto7);
		
		commandsMenu.add(commands1);
		commandsMenu.addSeparator();
		commandsMenu.add(commands2);
		commandsMenu.add(commands3);
		commandsMenu.addSeparator();
		commandsMenu.add(commands4);
		commandsMenu.add(commands5);
		commandsMenu.addSeparator();
		commandsMenu.add(commands6);
		
		favoritesMenu.add(favorites1);
		favoritesMenu.add(favorites2);
		favoritesMenu.addSeparator();
		
		toolsMenu.add(tools1);
		toolsMenu.add(tools2);
		toolsMenu.addSeparator();
		toolsMenu.add(tools3);
		toolsMenu.add(tools4);
		
		helpMenu.add(help1);
		helpMenu.addSeparator();
		helpMenu.add(help2);
		helpMenu.add(help3);
		helpMenu.add(help4);
		helpMenu.add(help5);
		helpMenu.addSeparator();
		helpMenu.add(help6);
		helpMenu.addSeparator();
		helpMenu.add(help7);
		helpMenu.addSeparator();
		helpMenu.add(help8);
		
		//menu end
		
		jb2.addActionListener(new ActionListener() {
			
			 public void actionPerformed(ActionEvent e) {
			
	         }
		});
		
		//status bar begin
		
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(jSP,BorderLayout.CENTER);
		add(jp4,BorderLayout.NORTH);	
		add(jp5,BorderLayout.SOUTH);
		jp4.add(jp1);
		jp4.add(jp2);
		jp5.add(statusPanel,BorderLayout.SOUTH);
		JLabel readyLabel = new JLabel("Ready");
		JLabel displayLabel = new JLabel("Display All");
		JLabel threadLabel = new JLabel("Thread : 0");
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);
		readyLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		displayLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		threadLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		readyLabel.setPreferredSize(new Dimension(250, 20));
		displayLabel.setPreferredSize(new Dimension(100, 20));
		threadLabel.setPreferredSize(new Dimension(100, 20));
		
		//status bar end		
		
		jp1.add(l1);
		jp1.add(t1);
		jp1.add(l2);
		jp1.add(t2);
		jc1.addItem("IP Range");
		jc1.addItem("Random");
		jc1.addItem("Text file");
		jp1.add(jc1);
		jp2.add(l3);
		jp2.add(t3);
		jp2.add(jb1);
		jc2.addItem("Netmask");
		jp2.add(jc2);
		jp2.add(jb2);

		String myIP = null;
		String myHostname = null;
		
		try {

			InetAddress ia = InetAddress.getLocalHost();
			
			myHostname = ia.getHostName();
			myIP = ia.getHostAddress();
		} catch (Exception e) {
			// TODO: handle exception
		}
			fixedIp = myIP.substring(0, myIP.lastIndexOf(".")+1);
			
			t1.setText(fixedIp+"0");
			t2.setText(fixedIp+"255");
			t3.setText(myHostname);
			
		setSize(500,580);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		setVisible(true);
		for (int i = 0; i < jT.getColumnCount(); i++) {
			jT.getColumnModel().getColumn(i).setResizable(false);
			jT.getTableHeader().setReorderingAllowed(false);
		}
		
		setAction();
	}
	public static void main(String[] args) {
		
		pg = new IPScannerTest();
		
	}
	
	void setAction() {
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pg.add();
				}
			}
		);
	}
	
	public Object[][] initializeTableData() {
		Object[][] results = new Object[254][5];
		return results;
	}
	
	void add() {
		dTM.setRowCount(0);
		
		Object row[] = new Object[5];
		for (int i = 0; i < 255; i++) {
			row[0] = fixedIp + i;
			//JOptionPane.showMessageDialog(null, fixedIp);
			IPScannerTest.dTM.addRow(row);
		}
			
		for (int i = 0; i < 255; i++) {
			thisisThread mt = new thisisThread(i, fixedIp + i);
			mt.start();
		}
	}
	
 } 

class thisisThread extends Thread {
	
	String ip;
	int row;

	public thisisThread(int row, String ip) {
		this.row = row;
		this.ip = ip;
	}

	@Override
	public synchronized void run() {
		Object row[] = new Object[5];
		
		String pingCmd = "ping -a " + ip;
		
	
		try {
			InetAddress address = InetAddress.getByName(ip);
				if(address.isReachable(200)) {
				Runtime run = Runtime.getRuntime();
				Process p = run.exec(pingCmd);
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					
					Pattern pattern1 = Pattern.compile("(\\d+)ms\\s+T",
							Pattern.CASE_INSENSITIVE);
					Matcher matcher1 = pattern1.matcher(inputLine);
					if(matcher1.find())	
						row[1] = matcher1.group(1)+ " ms";
					
					
					pattern1 = Pattern.compile("TTL=(\\d+)",
							Pattern.CASE_INSENSITIVE);
					matcher1 = pattern1.matcher(inputLine);
					
					if (matcher1.find())
						row[2] = matcher1.group(1);
				
					
					Pattern pattern2 = Pattern.compile("Ping\\s+(\\S+)\\s+",
							Pattern.CASE_INSENSITIVE);
					Matcher matcher2 = pattern2.matcher(inputLine);
					if(matcher2.find()) {
						row[3] = matcher2.group(1);
						if( row[3].toString().contains(ip)){
							row[3] = "[n/a]";
						
							}
						} 
					
					/*Port
					final ExecutorService es = Executors.newFixedThreadPool(20);
					int timeout = 200;
					ArrayList<Future<ScanResult>> futures = new ArrayList<>();
					for(int port = 1; port <= 1024; port++) {
						futures.add(portisOpen(es,ip,port,timeout));
					}
					try {
						es.awaitTermination(200L,TimeUnit.MILLISECONDS);
						int openPorts = 0;
						getPort = "";
						for(final Future<ScanResult> f : futures) {
							if(f.get().isOpen()) {
								openPorts++;
								if(getPort.equals("")) {
									getPort = Integer.toString(f.get().getPort());
									row[4] = getPort;
								}
								else {
									getPort +=", " + Integer.toString(f.get().getPort()));
									row[4] = getPort;
								}
							}
						}
					}	catch(Exception ee) {
						ee.printStackTrace();
					}
					
				
					*/
					IPScannerTest.dTM.setValueAt(row[1],this.row,1);
					IPScannerTest.dTM.setValueAt(row[2],this.row,2);
					IPScannerTest.dTM.setValueAt(row[3],this.row,3);
					IPScannerTest.dTM.setValueAt("[n/a]",this.row,4);
					}
					in.close();
				}
			else
			{
				row[1] = "[n/a]";
				row[2] = "[n/s]";
				row[3] = "[n/s]";
				row[4] = "[n/a]";
			 
				IPScannerTest.dTM.setValueAt(row[1], this.row, 1);
				IPScannerTest.dTM.setValueAt(row[2], this.row, 2);
				IPScannerTest.dTM.setValueAt(row[3], this.row, 3);
				IPScannerTest.dTM.setValueAt(row[4], this.row, 4);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public static Future<ScanResult> portIsOpen
	(final ExecutorService es, final String ip, final int port,final int timeout) {
		return es.submit(new Callable<ScanResult>() {
			@Override
			public ScanResult call() {
				try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ip,port),timeout);
						socket.close();
						return new ScanResult(port,true);
						}catch(Exception ex) {
							return new ScanResult(port,false);
						}
				
			}
		});
	}
	*/
}