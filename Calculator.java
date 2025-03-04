import javax.swing.*;  // Thư viện để tạo giao diện đồ họa
import java.awt.*;  // Thư viện để làm việc với giao diện đồ họa (màu sắc, bố cục, font...)
import java.awt.event.*;  // Thư viện xử lý sự kiện (click chuột, nhấn phím...)
import java.util.Stack;  // Thư viện dùng Stack để xử lý biểu thức


public class Calculator extends JFrame implements ActionListener {
    private JTextField display;  // Ô hiển thị kết quả
    private String currentInput = "";  // Biến lưu trữ chuỗi nhập vào
    private JTextArea historyArea;  // Vùng hiển thị lịch sử phép tính
    private boolean newInput = false;  // Biến kiểm soát khi nhập số mới
    private iphone16 parent;  // Đối tượng tham chiếu đến cửa sổ chính (ứng dụng điện thoại)
    private int mouseX, mouseY;  // Biến lưu vị trí chuột để di chuyển cửa sổ
    

    public Calculator(iphone16 parent) {
        this.parent = parent;
        setTitle("Máy tính VIP");  // Đặt tiêu đề cửa sổ
        setSize(350, 550);  // Kích thước cửa sổ
        setUndecorated(true);  // Loại bỏ viền cửa sổ
        setLocationRelativeTo(null);  // Căn giữa cửa sổ trên màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Đóng ứng dụng khi tắt cửa sổ
    
        // MousePressed lấy tọa độ chuột
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        // mouseDragged di chuyển cửa sổ theo tọa độ chuột
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });

        JPanel mainPanel = new JPanel(); // Panel chính chứa toàn bộ giao diện.
        mainPanel.setLayout(new BorderLayout());//sắp xếp 
        mainPanel.setBackground(new Color(20, 20, 20)); //màu nền
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//khoảng cách viền

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createLineBorder(new Color(50, 200, 50), 2));
        display.setPreferredSize(new Dimension(350, 60));
        mainPanel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 8, 8));
        buttonPanel.setBackground(new Color(30, 30, 30));

        String[] buttons = { //Danh sách các nút 
            "C", "xʸ", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "^",
            "←", "(", ")", "="
        };

        for (String text : buttons) {
            JButton button = createStyledButton(text);
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        // Lịch sử phép tính
        // JscrollPanel thanh cuộn nếu nội dung quá dài 
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.BOLD, 14));
        historyArea.setBackground(new Color(20, 20, 20));
        historyArea.setForeground(Color.WHITE);
        historyArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        bottomPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);

        JPanel buttonBottomPanel = new JPanel(new GridLayout(1, 2, 5, 0));
// Nút quay lại và xóa lịch sử 
        JButton backButton = new JButton("Quay lại");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        JButton clearHistoryButton = new JButton("Xóa lịch sử");
        clearHistoryButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearHistoryButton.setBackground(Color.ORANGE);
        clearHistoryButton.setForeground(Color.BLACK);
        clearHistoryButton.addActionListener(e -> historyArea.setText(""));

        buttonBottomPanel.add(backButton);
        buttonBottomPanel.add(clearHistoryButton);
        bottomPanel.add(buttonBottomPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }

            public void mousePressed(MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
            }

            public void mouseReleased(MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100));
            }
        });

        return button;
    }
// Lấy nội dung nút bấm
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." -> {
                if (newInput) {
                    currentInput = "";
                    newInput = false;
                }
                currentInput += command;
                display.setText(currentInput);
            }
            case "+", "-", "*", "/", "^", "xʸ", "%", "(", ")" -> {
                if (newInput) {
                    newInput = false;
                }
                if (!currentInput.isEmpty() && !currentInput.endsWith(" ")) {
                    currentInput += " " + (command.equals("xʸ") ? "^" : command) + " ";
                    display.setText(currentInput);
                }
            }
            case "C" -> {
                currentInput = "";
                display.setText("");
            }
            case "←" -> {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    display.setText(currentInput);
                }
            }
            case "√" -> {
                try {
                    double num = Double.parseDouble(currentInput);
                    if (num < 0) {
                        display.setText("Lỗi");
                    } else {
                        currentInput = String.valueOf(Math.sqrt(num));
                        display.setText(currentInput);
                    }
                    newInput = true;
                } catch (Exception ex) {
                    display.setText("Lỗi");
                }
            }
            case "=" -> {
                try {
                    double result = evaluateExpression(currentInput);
                    if (Double.isNaN(result)) {
                        display.setText("Lỗi");
                    } else {
                        historyArea.append(currentInput + " = " + result + "\n");
                        currentInput = String.valueOf(result);
                        display.setText(currentInput);
                        newInput = true;
                    }
                } catch (Exception ex) {
                    display.setText("Lỗi");
                }
            }
        }
    }
// Chuyển đổi biểu thức từ dạng trung tố sang hậu tố (convertToPostfix).
    private double evaluateExpression(String expression) {
        try {
            return evaluatePostfix(convertToPostfix(expression));
        } catch (Exception e) {
            return Double.NaN; 
        }
    }
    
    private String convertToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] tokens = infix.trim().split(" ");
    
        for (String token : tokens) {
            if (token.matches("[0-9]+(\\.[0-9]+)?")) { 
                output.append(token).append(" ");
            } else if ("+-*/^%".contains(token)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" ");
        }
        return output.toString().trim();
    }
    
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");
    
        for (String token : tokens) {
            if (token.matches("[0-9]+(\\.[0-9]+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(switch (token) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> a / b;
                    case "^" -> Math.pow(a, b);
                    case "%" -> a * (b / 100);
                    default -> throw new IllegalArgumentException("Phép toán không hợp lệ");
                });
            }
        }
        return stack.pop();
    }
    
    private int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^", "%" -> 3;
            default -> -1;
        };
    }
    
}
