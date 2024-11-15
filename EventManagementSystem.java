import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManagementSystem extends JFrame {
    private JComboBox<String> eventTypeBox, eventBox, participantCountBox;
    private HashMap<String, ArrayList<String>> eventsMap;
    private HashMap<String, String> venueMap;
    private HashMap<String, String[]> timeMap;
    private JButton registerButton, searchButton;
    private JPanel participantPanel;
    private ArrayList<JTextField> participantFields;
    private HashMap<String, String> participantEventMap;

    public EventManagementSystem() {
        participantEventMap = new HashMap<>();
        initializeEvents();
        initializeUI();
    }

    private void initializeEvents() {
        // Event data
        eventsMap = new HashMap<>();
        ArrayList<String> techEvents = new ArrayList<>();
        techEvents.add("Paper Presentation");
        techEvents.add("Project Presentation");
        techEvents.add("Quiz");
        techEvents.add("Coding Challenge");

        ArrayList<String> nonTechEvents = new ArrayList<>();
        nonTechEvents.add("Whisper");
        nonTechEvents.add("Group Discussion");
        nonTechEvents.add("Debate");
        nonTechEvents.add("Treasure Hunt");

        eventsMap.put("Tech", techEvents);
        eventsMap.put("Non-Tech", nonTechEvents);

        venueMap = new HashMap<>();
        venueMap.put("Paper Presentation", "Hall A");
        venueMap.put("Project Presentation", "Hall B");
        venueMap.put("Quiz", "Hall C");
        venueMap.put("Coding Challenge", "Hall D");
        venueMap.put("Whisper", "Hall E");
        venueMap.put("Group Discussion", "Hall F");
        venueMap.put("Debate", "Hall G");
        venueMap.put("Treasure Hunt", "Outdoor Area");

        timeMap = new HashMap<>();
        timeMap.put("Paper Presentation", new String[]{"10:00 AM", "12:00 PM"});
        timeMap.put("Project Presentation", new String[]{"1:00 PM", "3:00 PM"});
        timeMap.put("Quiz", new String[]{"3:30 PM", "5:00 PM"});
        timeMap.put("Coding Challenge", new String[]{"11:00 AM", "1:00 PM"});
        timeMap.put("Whisper", new String[]{"9:00 AM", "10:30 AM"});
        timeMap.put("Group Discussion", new String[]{"10:30 AM", "12:00 PM"});
        timeMap.put("Debate", new String[]{"2:00 PM", "4:00 PM"});
        timeMap.put("Treasure Hunt", new String[]{"1:00 PM", "3:00 PM"});
    }

    private void initializeUI() {
        setTitle("Event Management System");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Label titleLabel = new Label("Event Management System", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 20, 600, 30);

        Label eventTypeLabel = new Label("Select Event Type:");
        eventTypeLabel.setBounds(50, 70, 150, 25);
        eventTypeBox = new JComboBox<>(eventsMap.keySet().toArray(new String[0]));
        eventTypeBox.setBounds(200, 70, 200, 25);

        Label eventLabel = new Label("Select Event:");
        eventLabel.setBounds(50, 110, 150, 25);
        eventBox = new JComboBox<>();
        eventBox.setBounds(200, 110, 200, 25);
        eventTypeBox.addActionListener(e -> updateEventList());

        Label participantCountLabel = new Label("Select Number of Participants:");
        participantCountLabel.setBounds(50, 150, 250, 25);
        participantCountBox = new JComboBox<>(new String[]{"Select", "1", "2", "3"});
        participantCountBox.setBounds(300, 150, 100, 25);
        participantCountBox.addActionListener(e -> updateParticipantFields());

        Label participantDetailsLabel = new Label("Enter Participant Details:");
        participantDetailsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        participantDetailsLabel.setBounds(50, 190, 250, 25);

        registerButton = new JButton("Register Team");
        registerButton.setBounds(270, 600, 150, 30);
        registerButton.addActionListener(new RegisterAction());

        searchButton = new JButton("Search Participant");
        searchButton.setBounds(430, 600, 150, 30);
        searchButton.addActionListener(e -> new SearchPage(participantEventMap));

        add(titleLabel);
        add(eventTypeLabel);
        add(eventTypeBox);
        add(eventLabel);
        add(eventBox);
        add(participantCountLabel);
        add(participantCountBox);
        add(participantDetailsLabel);
        add(registerButton);
        add(searchButton);

        participantPanel = new JPanel();
        participantPanel.setLayout(new GridLayout(0, 2, 10, 10));
        participantPanel.setBounds(50, 230, 600, 350);
        add(participantPanel);

        participantFields = new ArrayList<>();
        setVisible(true);
    }

    private void updateEventList() {
        eventBox.removeAllItems();
        String selectedType = (String) eventTypeBox.getSelectedItem();
        ArrayList<String> events = eventsMap.get(selectedType);
        if (events != null) {
            for (String event : events) {
                eventBox.addItem(event);
            }
        }
    }

    private void updateParticipantFields() {
        String selectedValue = (String) participantCountBox.getSelectedItem();
        participantPanel.removeAll();
        participantFields.clear();

        if (!"Select".equals(selectedValue)) {
            int participantCount = Integer.parseInt(selectedValue);

            for (int i = 1; i <= participantCount; i++) {
                participantPanel.add(new JLabel("Name " + i + ":"));
                JTextField nameField = new JTextField(20);
                participantPanel.add(nameField);
                participantFields.add(nameField);

                participantPanel.add(new JLabel("Roll No " + i + ":"));
                JTextField rollField = new JTextField(20);
                participantPanel.add(rollField);
                participantFields.add(rollField);

                participantPanel.add(new JLabel("Department " + i + ":"));
                JTextField deptField = new JTextField(20);
                participantPanel.add(deptField);
                participantFields.add(deptField);

                participantPanel.add(new JLabel("Phone No " + i + ":"));
                JTextField phoneField = new JTextField(10);
                participantPanel.add(phoneField);
                participantFields.add(phoneField);
            }

            participantPanel.revalidate();
            participantPanel.repaint();
        }
    }

    private class RegisterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedEvent = (String) eventBox.getSelectedItem();
            if (selectedEvent != null) {
                String venue = venueMap.get(selectedEvent);
                String[] times = timeMap.get(selectedEvent);
                String timeRange = times[0] + " - " + times[1];

                StringBuilder participantDetails = new StringBuilder();
                for (int i = 0; i < participantFields.size(); i += 4) {
                    String participantName = participantFields.get(i).getText();
                    participantDetails.append("Name: ").append(participantName).append(", ")
                        .append("Roll No: ").append(participantFields.get(i + 1).getText()).append(", ")
                        .append("Department: ").append(participantFields.get(i + 2).getText()).append(", ")
                        .append("Phone No: ").append(participantFields.get(i + 3).getText()).append("\n");

                    participantEventMap.put(participantName, selectedEvent);
                }

                new ConfirmationPage(selectedEvent, venue, timeRange, participantDetails.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EventManagementSystem::new);
    }
}

class ConfirmationPage extends JFrame {
    public ConfirmationPage(String event, String venue, String time, String participants) {
        setTitle("Registration Confirmation");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel successLabel = new JLabel("Registration Successful!", JLabel.CENTER);
        successLabel.setFont(new Font("Arial", Font.BOLD, 24));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel eventLabel = new JLabel("Event: " + event, JLabel.CENTER);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        eventLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel venueLabel = new JLabel("Venue: " + venue, JLabel.CENTER);
        venueLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel timeLabel = new JLabel("Time: " + time, JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea participantsArea = new JTextArea(participants);
        participantsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        participantsArea.setEditable(false);
        participantsArea.setLineWrap(true);
        participantsArea.setWrapStyleWord(true);
        participantsArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(participantsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Participants Details"));

        panel.add(successLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(eventLabel);
        panel.add(venueLabel);
        panel.add(timeLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}

class SearchPage extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private HashMap<String, String> participantEventMap;

    public SearchPage(HashMap<String, String> participantEventMap) {
        this.participantEventMap = participantEventMap;
        setTitle("Search Participant");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JLabel searchLabel = new JLabel("Enter Participant Name:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchAction());

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));

        add(searchPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String participantName = searchField.getText().trim();
            String event = participantEventMap.get(participantName);

            if (event != null) {
                resultArea.setText("Participant: " + participantName + "\nEvent: " + event);
            } else {
                resultArea.setText("No event found for participant: " + participantName);
            }
        }
    }
}

