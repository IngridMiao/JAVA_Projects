import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
    private static final String FILE_STORAGE_PATH = "file_storage_student_upload/";
    private static final String FILE_STORAGE_PATH2 = "file_storage_enterprise_upload/";//需手動於電腦創建
    private static final String FILE_STORAGE_PATH3 = "file_storage_student_download/";
    private static final String FILE_STORAGE_PATH4 = "file_storage_enterprise_download/";
    private static Connection conn;
    //private static int a;
    private static Lottery1 lt1;
    private static Lottery2 lt2;
    private static JTextArea resultArea;
    private static JFrame resultFrame;
    private static String timeReminder;

    public static void main(String[] args) {
    	conn = DatabaseConnection.getConnection();
    	
        File fileStorageDir = new File(FILE_STORAGE_PATH);
        if (!fileStorageDir.exists()) {
            fileStorageDir.mkdirs();
        }

        JFrame frame = new JFrame("專案");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使用 CardLayout 來管理不同的面板
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // 學校端的 GUI
        JPanel schoolPanel = new JPanel(new BorderLayout());
        JButton uploadToEnterpriseButton = new JButton("上傳檔案給企業端");
        JButton downloadFromEnterpriseButton = new JButton("從企業端下載檔案");

        JLabel linkLabel = new JLabel("<html><a href='https://docs.google.com/forms/d/e/1FAIpQLSe9D61DK_8ItW7r0KFxz434dooN65OsMam7PnAe1_xsCkY6DQ/viewform?usp=sf_link'>填寫抽獎表單</a></html>");
        JLabel timeNote = new JLabel();
        JPanel lotteryPanel2 = new JPanel(new GridLayout(2, 1));
        JTextArea schoolTextArea = new JTextArea();
        JScrollPane schoolScrollPane = new JScrollPane(schoolTextArea);
        JPanel uploadPanel = new JPanel(new GridLayout(2, 1)); // 用於整合按鈕和超連結標籤
        lotteryPanel2.add(linkLabel);
        lotteryPanel2.add(timeNote);
        uploadPanel.add(uploadToEnterpriseButton);
        uploadPanel.add(lotteryPanel2);
        schoolPanel.add(uploadPanel, BorderLayout.NORTH); // 將整合後的面板添加到上方
        schoolPanel.add(downloadFromEnterpriseButton, BorderLayout.SOUTH); 
        schoolPanel.add(schoolScrollPane, BorderLayout.CENTER);
        cardPanel.add(schoolPanel, "學生端");

        // 企業端的 GUI
        JPanel enterprisePanel = new JPanel(new BorderLayout());
        JButton downloadFromStudentButton = new JButton("從學生端下載檔案");
        JButton uploadToStudentButton = new JButton("上傳檔案給學生端");
        JButton lotteryGo = new JButton("開始抽獎");
        JButton lotteryConfirmButton = new JButton("確認");

        //抽獎模式
        JPanel timePanel = new JPanel(new GridLayout(1,3));
        JLabel timeLabel = new JLabel("輸入抽獎時間: ");
        JTextField time = new JTextField();
        JButton timeConfirm = new JButton("確認");
        timePanel.add(timeLabel);
        timePanel.add(time);
        timePanel.add(timeConfirm);
        JRadioButton rbtnLottery1, rbtnLottery2;
        rbtnLottery1 = new JRadioButton("只抽大獎");
        rbtnLottery2 = new JRadioButton("人人有獎");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnLottery1);
        group.add(rbtnLottery2);
        JPanel lotteryPanel = new JPanel(new GridLayout(1, 3));
        lotteryPanel.add(rbtnLottery1);
        lotteryPanel.add(rbtnLottery2);
        lotteryPanel.add(lotteryGo);

        JTextArea enterpriseTextArea = new JTextArea();
        JScrollPane enterpriseScrollPane = new JScrollPane(enterpriseTextArea);
        JPanel inPanel = new JPanel(new GridLayout(3,1));
        inPanel.add(downloadFromStudentButton);
        inPanel.add(timePanel);
        inPanel.add(lotteryPanel);
        enterprisePanel.add(inPanel, BorderLayout.NORTH);
        enterprisePanel.add(enterpriseScrollPane, BorderLayout.CENTER);
        enterprisePanel.add(uploadToStudentButton, BorderLayout.SOUTH);
        cardPanel.add(enterprisePanel, "企業端");
       
        // 選擇角色的面板
        JPanel rolePanel = new JPanel(new FlowLayout());
        String[] roles = {"學生端", "企業端"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleComboBox);

        JButton confirmButton = new JButton("確認");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                cardLayout.show(cardPanel, selectedRole);
            }
        });
        rolePanel.add(confirmButton);

        // 將所有面板加入到 frame 中並顯示
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(rolePanel, BorderLayout.NORTH);
        frame.setVisible(true);

        // 處理學生端上傳檔案給企業端的動作
        uploadToEnterpriseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選擇檔案
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // 將檔案複製到文件儲存位置
                        Files.copy(selectedFile.toPath(), Path.of(FILE_STORAGE_PATH + selectedFile.getName()));
                        // 更新企業端的檔案清單顯示
                        updateEnterpriseFileList(enterpriseTextArea);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // 處理學生端下載企業檔案的動作
        downloadFromEnterpriseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打開文件選擇器，讓用戶選擇要下載的文件
                JFileChooser fileChooser2 = new JFileChooser(FILE_STORAGE_PATH2);
                int result = fileChooser2.showOpenDialog(frame); // 修改為 showOpenDialog 用於選擇文件
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser2.getSelectedFile(); // 用戶選擇的文件

                    // 構建默認下載目錄
                    File downloadsDir = new File(FILE_STORAGE_PATH3);

                    // 構建目標文件路徑
                    File destinationFile = new File(downloadsDir, selectedFile.getName());

                    try {
                        // 如果默認下載目錄不存在，則創建它
                        if (!downloadsDir.exists()) {
                            downloadsDir.mkdirs();
                        }

                        // 將文件複製到下載目標位置
                        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(frame, "下載成功！");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "下載失敗！");
                    }
                }
            }
        });
        //學生填寫google表單
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://docs.google.com/forms/d/e/1FAIpQLSe9D61DK_8ItW7r0KFxz434dooN65OsMam7PnAe1_xsCkY6DQ/viewform?usp=sf_link"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 處理企業端從學生端下載檔案的動作
        downloadFromStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打開文件選擇器，讓用戶選擇要下載的文件
                JFileChooser fileChooser2 = new JFileChooser(FILE_STORAGE_PATH);
                int result = fileChooser2.showOpenDialog(frame); // 修改為 showOpenDialog 用於選擇文件
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser2.getSelectedFile(); // 用戶選擇的文件

                    // 構建默認下載目錄
                    File downloadsDir = new File(FILE_STORAGE_PATH4);

                    // 構建目標文件路徑
                    File destinationFile = new File(downloadsDir, selectedFile.getName());

                    try {
                        // 如果默認下載目錄不存在，則創建它
                        if (!downloadsDir.exists()) {
                            downloadsDir.mkdirs();
                        }

                        // 將文件複製到下載目標位置
                        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(frame, "下載成功！");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "下載失敗！");
                    }
                }
            }
        });
        
        // 處理企業端上傳檔案給學生端的動作
        uploadToStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選擇檔案
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // 將檔案複製到文件儲存位置
                        Files.copy(selectedFile.toPath(), Path.of(FILE_STORAGE_PATH2 + selectedFile.getName()));
                        // 更新學校端的檔案清單顯示
                        updateSchoolFileList(schoolTextArea);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // 在按鈕點擊時列印抽獎訊息至學生端(企業端 TO 學生端)
        timeConfirm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		timeReminder = time.getText();
                timeNote.setText("抽獎將於 "+timeReminder+" 開始，請提早十分鐘到現場參與抽獎結果!");
        	}
        });

        // 在按鈕點擊時顯示新的視窗(企業端)
        //確定按鈕，開始計算
        lotteryGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame lotteryLimFrame = new JFrame("抽獎條件");
                lotteryLimFrame.setSize(250, 100);
                lotteryLimFrame.setVisible(true);

                if (rbtnLottery1.isSelected()) {
                    JPanel lottery1Panel = new JPanel(new GridLayout(2, 2));
                    JLabel winner = new JLabel("輸入中獎人數：");
                    JTextField winnerNum = new JTextField();
 

                    lottery1Panel.add(winner);
                    lottery1Panel.add(winnerNum);
                    lotteryLimFrame.add(lottery1Panel);
                    
                    lotteryConfirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String input = winnerNum.getText();
                            
                            if (input.isEmpty()) {
                                JOptionPane.showMessageDialog(lotteryLimFrame, "請輸入中獎人數！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            try {
                                int a = Integer.parseInt(input);
                                Lottery1 lt1 = new Lottery1(conn, a);
                                lt1.lottery();
                                showResultFrame(lt1.getArr());
                                lotteryLimFrame.dispose();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(lotteryLimFrame, "中獎人數必須是數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    lottery1Panel.add(lotteryConfirmButton);

                } else if (rbtnLottery2.isSelected()) {
                    JPanel typePanel = new JPanel(new GridLayout(2, 2));
                    JLabel typeLabel = new JLabel("請輸入種類數：");
                    JTextField typeNumField = new JTextField();
                    JButton confirmTypeButton = new JButton("確認種類數");

                    typePanel.add(typeLabel);
                    typePanel.add(typeNumField);
                    typePanel.add(confirmTypeButton);
                    lotteryLimFrame.add(typePanel);

                    confirmTypeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String typeInput = typeNumField.getText();
                            if (typeInput.isEmpty()) {
                                JOptionPane.showMessageDialog(lotteryLimFrame, "請輸入種類數！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            try {
                                int totalPrize = Integer.parseInt(typeInput);

                                // 移除舊的組件
                                lotteryLimFrame.getContentPane().removeAll();
                                lotteryLimFrame.repaint();

                                JPanel prizePanel = new JPanel(new GridLayout(totalPrize, 2));
                                ArrayList<JTextField> serialNumFields = new ArrayList<>();
                                ArrayList<JTextField> prizeNameFields = new ArrayList<>();

                                for (int i = 0; i < totalPrize; i++) {
                                    JLabel winnerLabel = new JLabel("中獎序號 " + (i + 1) + "：");
                                    JTextField winnerNumField = new JTextField();
                                    JLabel prizeLabel = new JLabel("中獎名稱 " + (i + 1) + "：");
                                    JTextField prizeNumField = new JTextField();

                                    serialNumFields.add(winnerNumField);
                                    prizeNameFields.add(prizeNumField);

                                    prizePanel.add(winnerLabel);
                                    prizePanel.add(winnerNumField);
                                    prizePanel.add(prizeLabel);
                                    prizePanel.add(prizeNumField);
                                }

                                JButton confirmButton = new JButton("確認");

                                confirmButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            ArrayList<Lottery2> prizes = new ArrayList<>();
                                            ArrayList<String> text = new ArrayList<>();

                                            for (int i = 0; i < totalPrize; i++) {
                                                String serialInput = serialNumFields.get(i).getText();
                                                String itemInput = prizeNameFields.get(i).getText();

                                                if (serialInput.isEmpty() || itemInput.isEmpty()) {
                                                    JOptionPane.showMessageDialog(lotteryLimFrame, "請填寫所有字段！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                                    return;
                                                }

                                                int serialNum = Integer.parseInt(serialInput);
                                                prizes.add(new Lottery2(serialNum, itemInput));
                                            }

                                            Lottery2 lt2 = new Lottery2(conn, totalPrize, prizes, text);
                                            lt2.lottery();
                                            showResultFrame(lt2.getArr());

                                            lotteryLimFrame.dispose();
                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(lotteryLimFrame, "序號必須是數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                });

                                lotteryLimFrame.add(prizePanel, BorderLayout.CENTER);
                                lotteryLimFrame.add(confirmButton, BorderLayout.SOUTH);
                                lotteryLimFrame.revalidate();
                                lotteryLimFrame.repaint();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(lotteryLimFrame, "種類數必須是數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
            }
        });
    }

    // 更新企業端的檔案清單顯示
    private static void updateEnterpriseFileList(JTextArea textArea) {
        File fileStorageDir = new File(FILE_STORAGE_PATH);
        File[] files = fileStorageDir.listFiles();
        if (files != null) {
            textArea.setText("");
            for (File file : files) {
                textArea.append(file.getName() + "\n");
            }
        }
    }

    // 更新學校端的檔案清單顯示
    private static void updateSchoolFileList(JTextArea textArea) {
        File fileStorageDir = new File(FILE_STORAGE_PATH2);
        File[] files = fileStorageDir.listFiles();
        if (files != null) {
            textArea.setText("");
            for (File file : files) {
                textArea.append(file.getName() + "\n");
            }
        }
    }
    
    public static void showResultFrame(ArrayList<String> resultList) {
        resultArea = new JTextArea();
        resultFrame = new JFrame("中獎名單");
        resultFrame.setSize(400, 300);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultArea.setText("");
        for (String item : resultList) {
            resultArea.append(item + "\n");
        }
        resultFrame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        resultFrame.setVisible(true);
    }

}
