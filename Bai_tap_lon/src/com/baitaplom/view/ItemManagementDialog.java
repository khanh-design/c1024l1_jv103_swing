package com.baitaplom.view;

import com.baitaplom.model.Item;
import com.baitaplom.model.User;
import com.baitaplom.service.ItemService;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ItemManagementDialog extends JDialog {
    
    private User currentUser;
    private Long currentRoomId;
    private ItemService itemService;
    
    private JTextField itemNameField;
    private JTextArea descriptionArea;
    private JTextField startPriceField;
    private JTextField buyNowPriceField;
    private JTextField imagesField;
    private JButton createItemButton;
    private JButton refreshButton;
    private JButton closeButton;
    private JList<Item> itemList;
    private JLabel statusLabel;
    
    public ItemManagementDialog(User user, Long roomId, ItemService itemService) {
        this.currentUser = user;
        this.currentRoomId = roomId;
        this.itemService = itemService;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupDialog();
        loadItems();
    }
    
    private void initializeComponents() {
        itemNameField = new JTextField(20);
        itemNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        startPriceField = new JTextField(20);
        startPriceField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        buyNowPriceField = new JTextField(20);
        buyNowPriceField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        imagesField = new JTextField(20);
        imagesField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        createItemButton = new JButton("Đăng bán");
        refreshButton = new JButton("Làm mới");
        closeButton = new JButton("Đóng");
        
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        createItemButton.setFont(buttonFont);
        createItemButton.setBackground(new Color(46, 204, 113));
        createItemButton.setForeground(Color.WHITE);
        createItemButton.setFocusPainted(false);
        
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        
        closeButton.setFont(buttonFont);
        closeButton.setBackground(new Color(231, 76, 60));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        
        itemList = new JList<>();
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setFont(new Font("Arial", Font.PLAIN, 12));
        
        statusLabel = new JLabel("Quản lý vật phẩm trong phòng");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(new Color(52, 73, 94));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(statusLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel createItemLabel = new JLabel("Đăng vật phẩm mới:");
        createItemLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(createItemLabel, gbc);
        
        JLabel nameLabel = new JLabel("Tên vật phẩm:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(itemNameField, gbc);
        
        JLabel descLabel = new JLabel("Mô tả:");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(descLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(new JScrollPane(descriptionArea), gbc);
        
        JLabel startPriceLabel = new JLabel("Giá khởi điểm ($):");
        startPriceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(startPriceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(startPriceField, gbc);
        
        JLabel buyNowPriceLabel = new JLabel("Giá mua ngay ($):");
        buyNowPriceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(buyNowPriceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buyNowPriceField, gbc);
        
        JLabel imagesLabel = new JLabel("Hình ảnh (URL):");
        imagesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(imagesLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(imagesField, gbc);
        

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(createItemButton, gbc);
        

        JLabel itemsLabel = new JLabel("Danh sách vật phẩm đã đăng:");
        itemsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(itemsLabel, gbc);
        
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 150));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);
        
        // Refresh button
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(refreshButton, gbc);
        
        // Close button
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(closeButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createItem();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadItems();
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter key to create item
        itemNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createItem();
            }
        });
    }
    
    private void setupDialog() {
        setTitle("Quản lý vật phẩm");
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
    }
    
    private void createItem() {
        String name = itemNameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String startPriceText = startPriceField.getText().trim();
        String buyNowPriceText = buyNowPriceField.getText().trim();
        String images = imagesField.getText().trim();
        
        // Validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên vật phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả vật phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (startPriceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá khởi điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (buyNowPriceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá mua ngay!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Double startPrice = Double.parseDouble(startPriceText);
            Double buyNowPrice = Double.parseDouble(buyNowPriceText);
            
            if (startPrice <= 0) {
                JOptionPane.showMessageDialog(this, "Giá khởi điểm phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (buyNowPrice <= 0) {
                JOptionPane.showMessageDialog(this, "Giá mua ngay phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (buyNowPrice <= startPrice) {
                JOptionPane.showMessageDialog(this, "Giá mua ngay phải lớn hơn giá khởi điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("Creating item: " + name + " for seller: " + currentUser.getId() + " in room: " + currentRoomId);
            Item item = itemService.createItem(name, description, startPrice, buyNowPrice, currentUser.getId(), currentRoomId, images);
            
            if (item != null) {
                JOptionPane.showMessageDialog(this, "Đăng vật phẩm thành công!\nVật phẩm: " + item.getName(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadItems();
            } else {
                JOptionPane.showMessageDialog(this, "Đăng vật phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        itemNameField.setText("");
        descriptionArea.setText("");
        startPriceField.setText("");
        buyNowPriceField.setText("");
        imagesField.setText("");
    }
    
    private void loadItems() {
        System.out.println("Loading item for room: " + currentRoomId + " and seller: " + currentUser.getId());
        List<Item> items = itemService.getItemsBySeller(currentUser.getId());
        
        // Filter items by current room
        items.removeIf(item -> !item.getRoomId().equals(currentRoomId));
        
        System.out.println("Loaded " + items.size() + " items");
        itemList.setListData(items.toArray(new Item[0]));
    }
}
