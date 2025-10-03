package com.baitaplom.view;

import com.baitaplom.client.AuctionClient;
import com.baitaplom.model.AuctionRoom;
import com.baitaplom.model.Item;
import com.baitaplom.model.User;
import com.baitaplom.service.ItemService;
import com.baitaplom.service.RoomService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class AuctionRoomFrame extends JFrame {

    private User currentUser;
    private AuctionClient client;
    private AuctionRoom currentRoom;
    private ItemService itemService;
    private RoomService roomService;

    private JLabel roomNameLabel;
    private JLabel currentItemLabel;
    private JLabel currentPriceLabel;
    private JLabel timeLeftLabel;
    private JTextField bidAmountField;
    private JButton bidButton;
    private JButton buyNowButton;
    private JButton leaveRoomButton;
    private JButton refreshButton;
    private JTable itemQueueTable;
    private JTable participantsTable;
    private DefaultTableModel itemQueueModel;
    private DefaultTableModel participantsModel;
    private JLabel statusLabel;

    private Timer refreshTimer;
    private Timer auctionTimer;
    private Timer notificationTimer;
    private Item currentAuctionItem;
    private JLabel currentItemImageLabel;
    private int notificationCount = 0;
    private static final int AUCTION_TIMEOUT_MINUTES = 3;
    private static final int NOTIFICATION_INTERVAL_SECONDS = 30;

    public AuctionRoomFrame(User user, AuctionClient client, AuctionRoom room) {
        this.currentUser = user;
        this.client = client;
        this.currentRoom = room;
        this.itemService = new ItemService();
        this.roomService = new RoomService();

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupFrame();
        startRefreshTimer();
        loadRoomData();
    }

    private void initializeComponents() {
        roomNameLabel = new JLabel("Phòng: " + currentRoom.getName());
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        roomNameLabel.setForeground(new Color(52, 73, 94));

        currentItemLabel = new JLabel("Vật phẩm hiện tại: ");
        currentItemLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentItemLabel.setForeground(new Color(46, 204, 113));

        currentPriceLabel = new JLabel("Giá hiện tại: ");
        currentPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPriceLabel.setForeground(new Color(231, 76, 60));

        timeLeftLabel = new JLabel("Thời gian còn lại:");
        timeLeftLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLeftLabel.setForeground(new Color(149, 165, 166));

        bidAmountField = new JTextField(10);
        bidAmountField.setFont(new Font("Arial", Font.PLAIN, 12));

        bidButton = new JButton("Đấu giá");
        buyNowButton = new JButton("Mua ngay");
        leaveRoomButton = new JButton("Rời phòng");
        refreshButton = new JButton("Làm mới");

        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        bidButton.setFont(buttonFont);
        bidButton.setBackground(new Color(52, 152, 219));
        bidButton.setForeground(Color.WHITE);
        bidButton.setFocusPainted(false);

        buyNowButton.setFont(buttonFont);
        buyNowButton.setBackground(new Color(46, 204, 113));
        buyNowButton.setForeground(Color.WHITE);
        buyNowButton.setFocusPainted(false);

        leaveRoomButton.setFont(buttonFont);
        leaveRoomButton.setBackground(new Color(231, 76, 60));
        leaveRoomButton.setForeground(Color.WHITE);
        leaveRoomButton.setFocusPainted(false);

        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);

        String[] itemColumns = {"Tên vật phẩm", "Giá hiện tại", "Trạng thái"};
        itemQueueModel = new DefaultTableModel(itemColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        itemQueueTable = new JTable(itemQueueModel);
        itemQueueTable.setFont(new Font("Arial", Font.PLAIN, 12));
        itemQueueTable.setRowHeight(25);
        itemQueueTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        String[] participantColumns = {"Tên người dùng", "Email", "Số điện thoại"};
        participantsModel = new DefaultTableModel(participantColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        participantsTable = new JTable(participantsModel);
        participantsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        participantsTable.setRowHeight(25);
        participantsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        statusLabel = new JLabel("Trạng thái: " + currentRoom.getName());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(149, 165, 166));

        currentItemImageLabel = new JLabel();
        currentItemImageLabel.setHorizontalAlignment(JLabel.CENTER);
        currentItemImageLabel.setVerticalAlignment(JLabel.CENTER);
        currentItemImageLabel.setPreferredSize(new Dimension(200, 200));

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
        headerPanel.add(roomNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        headerPanel.add(leaveRoomButton, gbc);

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

        JLabel auctionLabel = new JLabel("ĐẤU GIÁ HIỆN TẠI");
        auctionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        auctionLabel.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(auctionLabel, gbc);

        JPanel auctionInfoPanel = new JPanel(new GridBagLayout());
        auctionInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đấu giá"));
        auctionInfoPanel.setBackground(Color.WHITE);

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 5, 5, 5);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        gbcInfo.anchor = GridBagConstraints.WEST;
        auctionInfoPanel.add(currentItemLabel, gbcInfo);

        gbcInfo.gridy = 1;
        auctionInfoPanel.add(currentPriceLabel, gbcInfo);

        gbcInfo.gridy = 2;
        auctionInfoPanel.add(timeLeftLabel, gbcInfo);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(auctionInfoPanel, gbc);

        JPanel bidPanel = new JPanel(new GridBagLayout());
        bidPanel.setBorder(BorderFactory.createTitledBorder("Đấu giá"));
        bidPanel.setBackground(Color.WHITE);

        GridBagConstraints gbcBid = new GridBagConstraints();
        gbcBid.insets = new Insets(5, 5, 5, 5);

        JLabel bidLabel = new JLabel("Số tiền đấu giá ($):");
        bidLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbcBid.gridx = 0;
        gbcBid.gridy = 0;
        gbcBid.anchor = GridBagConstraints.WEST;
        bidPanel.add(bidLabel, gbcBid);

        gbcBid.gridx = 1;
        gbcBid.fill = GridBagConstraints.HORIZONTAL;
        bidPanel.add(bidAmountField, gbcBid);

        gbcBid.gridx = 2;
        gbcBid.fill = GridBagConstraints.NONE;
        bidPanel.add(bidButton, gbcBid);

        gbcBid.gridx = 3;
        bidPanel.add(buyNowButton, gbcBid);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(bidPanel, gbc);

        JLabel queueLabel = new JLabel("Hàng đợi vật phẩm:");
        queueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(queueLabel, gbc);

        JScrollPane queueScrollPane = new JScrollPane(itemQueueTable);
        queueScrollPane.setPreferredSize(new java.awt.Dimension(400, 150));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(queueScrollPane, gbc);

        JLabel participantsLabel = new JLabel("Người tham gia:");
        participantsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(participantsLabel, gbc);

        JScrollPane participantsScrollPane = new JScrollPane(participantsTable);
        participantsScrollPane.setPreferredSize(new java.awt.Dimension(300, 150));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(participantsScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(refreshButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void setupEventHandlers() {
        bidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBid();
            }
        });

        buyNowButton.addActionListener((ActionEvent e) -> {
            buyNow();
        });

        leaveRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveRoom();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRoomData();
            }
        });

        bidAmountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBid();
            }
        });
    }

    private void setupFrame() {
        setTitle("Phòng đấu giá - " + currentRoom.getName());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        pack();
    }

    private void startRefreshTimer() {
        refreshTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRoomData();
            }
        });
        refreshTimer.start();
    }

    private void startAuctionTimer() {
        if (auctionTimer != null) {
            auctionTimer.stop();
        }
        
        auctionTimer = new Timer(AUCTION_TIMEOUT_MINUTES * 60 * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAuctionTimeout();
            }
        });
        auctionTimer.setRepeats(false);
        auctionTimer.start();
    }

    private void startNotificationTimer() {
        if (notificationTimer != null) {
            notificationTimer.stop();
        }
        
        notificationTimer = new Timer(NOTIFICATION_INTERVAL_SECONDS * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendNotification();
            }
        });
        notificationTimer.setRepeats(false);
        notificationTimer.start();
    }

    private void handleAuctionTimeout() {
        if (currentAuctionItem != null) {
            endAuctionWithWinner();
        }
    }

    private void sendNotification() {
        if (currentAuctionItem != null && notificationCount < 3) {
            notificationCount++;
            String message = String.format("Thông báo lần %d: Đấu giá sẽ kết thúc sau %d phút nếu không có ai trả giá cao hơn!", 
                notificationCount, AUCTION_TIMEOUT_MINUTES);
            
            JOptionPane.showMessageDialog(this, message, "Thông báo đấu giá", JOptionPane.INFORMATION_MESSAGE);
            
            if (notificationCount < 3) {
                startNotificationTimer();
            }
        }
    }

    private void endAuctionWithWinner() {
        if (currentAuctionItem != null) {
            JOptionPane.showMessageDialog(this, 
                "Đấu giá đã kết thúc!\nVật phẩm: " + currentAuctionItem.getName() + 
                "\nGiá cuối: $" + currentAuctionItem.getCurrentPrice(), 
                "Kết thúc đấu giá", JOptionPane.INFORMATION_MESSAGE);
            
            itemService.endAuction(currentAuctionItem.getId(), "SOLD");
            loadRoomData();
        }
    }

    private void saveBid(Long itemId, Double amount) {
        System.out.println("Saving bid: Item " + itemId + ", Amount: " + amount + ", User: " + currentUser.getId());
    }


    private void loadItemImage(String imageName) {
        if (imageName == null || imageName.isEmpty()) {
            currentItemImageLabel.setIcon(null);
            return;
        }

        
        try {
            String imagePath = "/src/resource/static/" + imageName;
            java.io.File imageFile = new java.io.File(imagePath);
            
            if (imageFile.exists()) {
                URL imageUrl = imageFile.toURI().toURL();
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                currentItemImageLabel.setIcon(new ImageIcon(scaled));
                return;
            } else {
            }
        } catch (Exception e) {
        }

        try {
            URL imageUrl = getClass().getResource("/static/" + imageName);
            if (imageUrl == null) {
                imageUrl = getClass().getResource("/resource/static/" + imageName);
            }
            
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                currentItemImageLabel.setIcon(new ImageIcon(scaled));
            } else {
                System.out.println("❌ Không tìm thấy ảnh trong resources");
                currentItemImageLabel.setIcon(null);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi load từ resources: " + e.getMessage());
            currentItemImageLabel.setIcon(null);
        }
    }

    private void loadRoomData() {
        currentAuctionItem = itemService.getCurrentAuctionItem(currentRoom.getId());
        List<Item> itemQueue = itemService.getItemsByRoom(currentRoom.getId());
        List<User> participants = roomService.getRoomParticipants(currentRoom.getId());

        if (currentAuctionItem != null) {
            String images = currentAuctionItem.getImages();

            loadItemImage(images);

            currentItemLabel.setText("Vật phẩm hiện tại: " + currentAuctionItem.getName());
            currentPriceLabel.setText("Giá hiện tại: " + currentAuctionItem.getCurrentPrice());
            buyNowButton.setEnabled(true);
            buyNowButton.setText("Mua ngay (" + currentAuctionItem.getBuyNowPrice() + ")");
            
            if (auctionTimer == null || !auctionTimer.isRunning()) {
                startAuctionTimer();
                startNotificationTimer();
            }
        } else {
            currentItemLabel.setText("Vật phẩm hiện tại:");
            currentPriceLabel.setText("Giá hiện tại:");
            buyNowButton.setEnabled(false);
            buyNowButton.setText("Mua ngay");
            currentItemImageLabel.setIcon(null);
            
            if (auctionTimer != null) {
                auctionTimer.stop();
            }
            if (notificationTimer != null) {
                notificationTimer.stop();
            }
        }

        itemQueueModel.setRowCount(0);
        for (Item item : itemQueue) {
            Object[] row = {
                item.getName(),
                "$" + item.getCurrentPrice(),
                item.getStatus()
            };
            itemQueueModel.addRow(row);
        }

        participantsModel.setRowCount(0);
        for (User user : participants) {
            Object[] row = {
                user.getFullname(),
                user.getEmail(),
                user.getPhone()
            };
            participantsModel.addRow(row);
        }

        bidButton.setEnabled(currentAuctionItem != null);
        bidAmountField.setEnabled(currentAuctionItem != null);

    }

    private void placeBid() {
        if (currentAuctionItem == null) {
            JOptionPane.showMessageDialog(this, "Không có vật phẩm nào đang đấu giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bidText = bidAmountField.getText().trim();
        if (bidText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền đấu giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Double bidAmount = Double.parseDouble(bidText);

            if (bidAmount <= currentAuctionItem.getCurrentPrice()) {
                JOptionPane.showMessageDialog(this, "Số tiền đấu giá phải lớn hơn giá hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bidAmount < currentAuctionItem.getCurrentPrice() + 100000) {
                JOptionPane.showMessageDialog(this, "Số tiền đấu giá phải lớn hơn giá hiện tại ít nhất 100.000 đồng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bidAmount >= currentAuctionItem.getBuyNowPrice()) {
                int result = JOptionPane.showConfirmDialog(this,
                    "Bạn đã đặt giá bằng/trên giá mua ngay!\n" +
                    "Vật phẩm sẽ được mua ngay với giá: $" + currentAuctionItem.getBuyNowPrice() + "\n" +
                    "Bạn có muốn tiếp tục?",
                    "Xác nhận mua ngay", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                    if (auctionTimer != null) {
                        auctionTimer.stop();
                    }
                    if (notificationTimer != null) {
                        notificationTimer.stop();
                    }
                    
                    boolean success = itemService.updateCurrentPrice(currentAuctionItem.getId(), currentAuctionItem.getBuyNowPrice());
                    if (success) {
                        saveBid(currentAuctionItem.getId(), currentAuctionItem.getBuyNowPrice());
                        itemService.endAuction(currentAuctionItem.getId(), "SOLD");
                        
                        JOptionPane.showMessageDialog(this, 
                            "Mua ngay thành công!\nVật phẩm: " + currentAuctionItem.getName() + 
                            "\nGiá: $" + currentAuctionItem.getBuyNowPrice(), 
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        bidAmountField.setText("");
                        loadRoomData();
                        return;
                    }
                } else {
                    return;
                }
            }

            boolean success = itemService.updateCurrentPrice(currentAuctionItem.getId(), bidAmount);

            if (success) {
                saveBid(currentAuctionItem.getId(), bidAmount);
                
                startAuctionTimer();
                startNotificationTimer();
                notificationCount = 0;
                
                JOptionPane.showMessageDialog(this, "Đấu giá thành công!\nSố tiền: $" + bidAmount, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                bidAmountField.setText("");
                loadRoomData();
            } else {
                JOptionPane.showMessageDialog(this, "Đấu giá thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền đấu giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buyNow() {
        if (currentAuctionItem == null) {
            JOptionPane.showMessageDialog(this, "Không có vật phẩm nào đang đấu giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn mua ngay vật phẩm này?\n"
                + "Vật phẩm: " + currentAuctionItem.getName() + "\n"
                + "Giá mua ngay: $" + currentAuctionItem.getBuyNowPrice(),
                "Xác nhận mua ngay", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            if (auctionTimer != null) {
                auctionTimer.stop();
            }
            if (notificationTimer != null) {
                notificationTimer.stop();
            }
            
            boolean success = itemService.endAuction(currentAuctionItem.getId(), "SOLD");

            if (success) {
                JOptionPane.showMessageDialog(this, "Mua ngay thành công!\nVật phẩm: " + currentAuctionItem.getName(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadRoomData();
            } else {
                JOptionPane.showMessageDialog(this, "Mua ngay thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void leaveRoom() {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn rời phòng?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            boolean success = client.leaveRoom(currentUser.getId());

            if (success) {
                if (refreshTimer != null) {
                    refreshTimer.stop();
                }
                if (auctionTimer != null) {
                    auctionTimer.stop();
                }
                if (notificationTimer != null) {
                    notificationTimer.stop();
                }
                
                dispose();

                SwingUtilities.invokeLater(() -> {
                    new HomeFrame(currentUser, client).setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Rời phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
