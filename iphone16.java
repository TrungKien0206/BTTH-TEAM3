import javax.swing.*; // Import thư viện Swing để tạo giao diện đồ họa
import java.awt.*; // Import thư viện AWT để xử lý giao diện
import java.awt.event.ActionEvent; // Import thư viện xử lý sự kiện
import java.awt.event.ActionListener; // Import thư viện xử lý sự kiện nút bấm
import java.time.LocalTime; // Import thư viện để lấy thời gian thực
import java.time.format.DateTimeFormatter; // Import thư viện để định dạng thời gian

public class iphone16 extends JFrame { // Kế thừa JFrame để tạo cửa sổ ứng dụng
    private JLabel clockLabel; // Nhãn hiển thị đồng hồ

    public iphone16() { // Constructor để khởi tạo giao diện
        setTitle("iphone16 Promax 1TB"); // Đặt tiêu đề cho cửa sổ
        setSize(370, 620); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng ứng dụng khi nhấn nút X
        setLayout(null); // Sử dụng layout tự do (absolute layout)
        getContentPane().setBackground(Color.BLACK); // Đặt màu nền của cửa sổ là đen

        JPanel phoneBorder = new JPanel(); // Tạo một JPanel làm viền điện thoại
        phoneBorder.setBounds(10, 10, 335, 560); // Xác định kích thước và vị trí
        phoneBorder.setBackground(Color.DARK_GRAY); // Màu nền của viền điện thoại
        phoneBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20, true)); // Bo góc với đường viền màu xám
        phoneBorder.setLayout(null); // Đặt layout tự do để tự chỉnh vị trí các thành phần
        add(phoneBorder); // Thêm JPanel vào JFrame

        clockLabel = new JLabel(); // Khởi tạo nhãn để hiển thị đồng hồ
        clockLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Đặt font chữ cho đồng hồ
        clockLabel.setForeground(Color.WHITE); // Đặt màu chữ trắng
        clockLabel.setBounds(90, 120, 150, 150); // Xác định vị trí và kích thước đồng hồ
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
        phoneBorder.add(clockLabel); // Thêm nhãn đồng hồ vào JPanel

        Timer timer = new Timer(1000, e -> updateClock()); // Tạo Timer cập nhật đồng hồ mỗi giây
        timer.start(); // Bắt đầu Timer

        JButton calcButton = new JButton("Máy tính"); // Tạo nút bấm "Máy tính"
        calcButton.setBounds(60, 420, 100, 50); // Xác định vị trí và kích thước
        calcButton.setBackground(new Color(70, 70, 70)); // Đặt màu nền cho nút
        calcButton.setForeground(Color.WHITE); // Đặt màu chữ trắng
        calcButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true)); // Bo góc nút
        calcButton.addActionListener(e -> new Calculator(this)); // Gắn sự kiện mở ứng dụng máy tính
        phoneBorder.add(calcButton); // Thêm nút vào JPanel

        JButton fbButton = new JButton("Facebook"); // Tạo nút bấm "Facebook"
        fbButton.setBounds(180, 420, 100, 50); // Xác định vị trí và kích thước
        fbButton.setBackground(new Color(70, 70, 70)); // Đặt màu nền cho nút
        fbButton.setForeground(Color.WHITE); // Đặt màu chữ trắng
        fbButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true)); // Bo góc nút
        fbButton.addActionListener(e -> openFacebook()); // Gắn sự kiện mở Facebook
        phoneBorder.add(fbButton); // Thêm nút vào JPanel

        setVisible(true); // Hiển thị cửa sổ JFrame
    }

    private void updateClock() { // Phương thức cập nhật đồng hồ
        LocalTime time = LocalTime.now(); // Lấy thời gian hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Định dạng thời gian
        clockLabel.setText(time.format(formatter)); // Cập nhật hiển thị đồng hồ
    }

    private void openFacebook() { // Phương thức mở Facebook trong trình duyệt
        try {
            Desktop.getDesktop().browse(new java.net.URI("https://www.facebook.com")); // Mở đường dẫn
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu không mở được
        }
    }

    public static void main(String[] args) { // Phương thức main để chạy chương trình
        SwingUtilities.invokeLater(iphone16::new); // Chạy ứng dụng trong luồng giao diện
    }
}
