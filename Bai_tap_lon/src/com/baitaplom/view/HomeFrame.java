package com.baitaplom.view;

import com.baitaplom.client.AuctionClient;
import com.baitaplom.model.AuctionRoom;
import com.baitaplom.model.User;
import com.baitaplom.service.ItemService;
import com.baitaplom.service.RoomService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HomeFrame extends JFrame {
    
    private User currentUser;
    private AuctionClient client;
    private ItemService itemService;
    private RoomService roomService;
    private JList<AuctionRoom> roomList;
    private JTextField roomNameField;
    private JButton createRoomButton;
    private JButton joinRoomButton;
    private JButton manageItemsButton;
    private JButton leaveCurrentRoomButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private JLabel welcomeLabel;
    private JLabel statusLabel;
    
    public HomeFrame(User user, AuctionClient client) {
        this.currentUser = user;
        this.client = client;
        this.itemService = new ItemService();
        this.roomService = new RoomService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupFrame();
        loadRooms();
        checkUserRoomStatus();
    }
    
    private void checkUserRoomStatus() {
        boolean userInRoom = roomService.isUserInAnyRoom(currentUser.getId());
        leaveCurrentRoomButton.setEnabled(userInRoom);
        
        if (userInRoom) {
            statusLabel.setText("Trạng thái: Đã tham gia phòng");
        } else {
            statusLabel.setText("Trạng thái: Chưa tham gia phòng nào");
        }
    }
    
    private void leaveCurrentRoom() {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn rời phòng hiện tại?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            boolean success = client.leaveRoom(currentUser.getId());
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Rời phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                checkUserRoomStatus();
                loadRooms();
            } else {
                JOptionPane.showMessageDialog(this, "Rời phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void initializeComponents() {
        roomList = new JList<>();
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setFont(new Font("Arial", Font.PLAIN, 12));
        
        roomNameField = new JTextField(20);
        roomNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        createRoomButton = new JButton("Tạo phòng");
        joinRoomButton = new JButton("Tham gia phòng");
        manageItemsButton = new JButton("Quản lý vật phẩm");
        leaveCurrentRoomButton = new JButton("Rời phòng hiện tại");
        refreshButton = new JButton("Làm mới");
        logoutButton = new JButton("Đăng xuất");
        
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        createRoomButton.setFont(buttonFont);
        createRoomButton.setBackground(new Color(46, 204, 113));
        createRoomButton.setForeground(Color.WHITE);
        createRoomButton.setFocusPainted(false);
        
        joinRoomButton.setFont(buttonFont);
        joinRoomButton.setBackground(new Color(52, 152, 219));
        joinRoomButton.setForeground(Color.WHITE);
        joinRoomButton.setFocusPainted(false);
        joinRoomButton.setEnabled(false);
        
        manageItemsButton.setFont(buttonFont);
        manageItemsButton.setBackground(new Color(230, 126, 34));
        manageItemsButton.setForeground(Color.WHITE);
        manageItemsButton.setFocusPainted(false);
        manageItemsButton.setEnabled(false);
        
        leaveCurrentRoomButton.setFont(buttonFont);
        leaveCurrentRoomButton.setBackground(new Color(231, 76, 60));
        leaveCurrentRoomButton.setForeground(Color.WHITE);
        leaveCurrentRoomButton.setFocusPainted(false);
        leaveCurrentRoomButton.setEnabled(false);
        
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        
        welcomeLabel = new JLabel("Xin chào, " + currentUser.getFullname() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        
        statusLabel = new JLabel("Trạng thái: Chưa tham gia phòng nào");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(149, 165, 166));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        headerPanel.add(welcomeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        headerPanel.add(logoutButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        headerPanel.add(statusLabel, gbc);
        
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel createRoomLabel = new JLabel("Tạo phòng mới:");
        createRoomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(createRoomLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(roomNameField, gbc);
        
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(createRoomButton, gbc);
        
        JLabel roomsLabel = new JLabel("Danh sách phòng đấu giá:");
        roomsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(roomsLabel, gbc);
        
        JScrollPane scrollPane = new JScrollPane(roomList);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 200));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);
        
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(joinRoomButton, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(manageItemsButton, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(leaveCurrentRoomButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(refreshButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRoom();
            }
        });
        
        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRoom();
            }
        });
        
        manageItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageItems();
            }
        });
        
        leaveCurrentRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveCurrentRoom();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRooms();
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        
        roomList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    AuctionRoom selectedRoom = roomList.getSelectedValue();
                    joinRoomButton.setEnabled(selectedRoom != null);
                    manageItemsButton.setEnabled(selectedRoom != null);
                }
            }
        });
        
        roomNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRoom();
            }
        });
    }
    
    private void setupFrame() {
        setTitle("Trang chủ - Hệ thống đấu giá");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
    }
    
    private void createRoom() {
        String roomName = roomNameField.getText().trim();
        
        if (roomName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!client.isConnected()) {
            JOptionPane.showMessageDialog(this, "Mất kết nối với server! Vui lòng đăng nhập lại.", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            logout();
            return;
        }
        
        System.out.println("Creating room: " + roomName + " for user: " + currentUser.getId());
        AuctionRoom room = client.createRoom(roomName, currentUser.getId());
        
        if (room != null) {
            JOptionPane.showMessageDialog(this, "Tạo phòng thành công!\nPhòng: " + room.getName(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
            roomNameField.setText("");
            loadRooms();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void joinRoom() {
        AuctionRoom selectedRoom = roomList.getSelectedValue();
        
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để tham gia!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = client.joinRoom(selectedRoom.getId(), currentUser.getId());
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Tham gia phòng thành công!\nPhòng: " + selectedRoom.getName(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Trạng thái: Đã tham gia phòng " + selectedRoom.getName());
            joinRoomButton.setEnabled(false);
            
            dispose();
            SwingUtilities.invokeLater(() -> {
                new AuctionRoomFrame(currentUser, client, selectedRoom).setVisible(true);
            });
        } else {
            String debugInfo = roomService.debugJoinRoom(currentUser.getId(), selectedRoom.getId());
            System.out.println(debugInfo);
            JOptionPane.showMessageDialog(this, "Tham gia phòng thất bại!\nBạn có thể đã tham gia phòng khác hoặc phòng không khả dụng.\n\nDebug info:\n" + debugInfo, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadRooms() {
        if (!client.isConnected()) {
            JOptionPane.showMessageDialog(this, "Mất kết nối với server! Vui lòng đăng nhập lại.", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            logout();
            return;
        }
        
        System.out.println("Loading rooms...");
        List<AuctionRoom> rooms = client.getRooms();
        
        if (rooms != null) {
            System.out.println("Loaded " + rooms.size() + " rooms");
            roomList.setListData(rooms.toArray(new AuctionRoom[0]));
        } else {
            System.out.println("Failed to load rooms");
            roomList.setListData(new AuctionRoom[0]);
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void manageItems() {
        AuctionRoom selectedRoom = roomList.getSelectedValue();
        
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để quản lý vật phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ItemManagementDialog dialog = new ItemManagementDialog(currentUser, selectedRoom.getId(), itemService);
        dialog.setVisible(true);
    }
    
    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            client.disconnect();
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User testUser = new User(1L, "test", "Test User", "test@test.com", "123456789", "password", null);
            AuctionClient testClient = new AuctionClient();
            if (testClient.connect()) {
                new HomeFrame(testUser, testClient).setVisible(true);
            }
        });
    }
}
