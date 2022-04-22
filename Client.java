
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Client {

	private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});

	}// end main

}

class MyFrame extends JFrame {
	public JTextField numberToSearch;
	public JButton transmitButton;
	public JTextField resultField;

	public String userChoice;

	public MyFrame() {
		super();
		init();
	}

	private void init() {

		numberToSearch = new JTextField();
		resultField = new JTextField();

		transmitButton = new JButton("Transmit");
		transmitButton.addActionListener(new MyButtonListener(this));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Prime Calculator");
		this.setLayout(new GridLayout(3, 4));

		this.add(new JLabel("Enter number:"));
		this.add(numberToSearch);

		this.add(new JLabel(""));
		this.add(transmitButton);

		this.add(new JLabel("Is Prime Number?"));
		this.add(resultField);

		// GUI Dimensions ====================================
//		int frameWidth = 1000;
//		int frameHeight = 450;
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		this.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);

		this.pack();
		this.setVisible(true);

	}

	class MyButtonListener implements ActionListener {
		MyFrame fr;

		public MyButtonListener(MyFrame frame) {
			fr = frame;

		}

		// Transmit Button =====================================
		public void actionPerformed(ActionEvent e) {

			transmitButton = (JButton) e.getSource();

			try {
				Socket connection = new Socket("127.0.0.1", 1782);
//				InputStream input = connection.getInputStream();
				OutputStream output = connection.getOutputStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String userNumber = fr.numberToSearch.getText();

				output.write(userNumber.length());
				output.write(userNumber.getBytes());

				// Reads from server and displays result to GUI
				String serverResponse = reader.readLine();

				resultField.setText(serverResponse);

				if (!connection.isClosed())
					connection.close();

			} catch (IOException d) {

				d.printStackTrace();
			}

		}

	}
}