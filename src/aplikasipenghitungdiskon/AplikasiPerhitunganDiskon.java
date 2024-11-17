package aplikasipenghitungdiskon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AplikasiPerhitunganDiskon extends JFrame {
    private JTextField tfHargaAsli, tfKodeKupon;
    private JLabel lblHargaAkhir, lblPenghematan;
    private JComboBox<String> cbDiskon;
    private JButton btnHitung;
    private JSlider sliderDiskon;
    private JTextArea taRiwayat;

    public AplikasiPerhitunganDiskon() {
        setTitle("Aplikasi Perhitungan Diskon");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.setBackground(new Color(240, 240, 240));
        panelInput.add(new JLabel("Harga Asli:"));
        tfHargaAsli = new JTextField();
        tfHargaAsli.setFont(textFont);
        panelInput.add(tfHargaAsli);

        panelInput.add(new JLabel("Persentase Diskon:"));
        cbDiskon = new JComboBox<>(new String[]{"0%", "10%", "20%", "30%", "50%"});
        cbDiskon.setFont(textFont);
        panelInput.add(cbDiskon);

        panelInput.add(new JLabel("Atau Pilih dengan Slider:"));
        sliderDiskon = new JSlider(0, 100, 0);
        sliderDiskon.setFont(textFont);
        sliderDiskon.setMajorTickSpacing(10);
        sliderDiskon.setPaintTicks(true);
        sliderDiskon.setPaintLabels(true);
        panelInput.add(sliderDiskon);

        panelInput.add(new JLabel("Kode Kupon Diskon:"));
        tfKodeKupon = new JTextField();
        tfKodeKupon.setFont(textFont);
        panelInput.add(tfKodeKupon);

        btnHitung = new JButton("Hitung");
        btnHitung.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnHitung.setBackground(new Color(0, 123, 255));
        btnHitung.setForeground(Color.WHITE);
        panelInput.add(btnHitung);

        panelInput.add(new JLabel());

        JPanel panelOutput = new JPanel(new GridLayout(2, 1));
        panelOutput.setBackground(new Color(240, 240, 240));
        lblHargaAkhir = new JLabel("Harga Akhir: ", JLabel.CENTER);
        lblHargaAkhir.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPenghematan = new JLabel("Penghematan: ", JLabel.CENTER);
        lblPenghematan.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelOutput.add(lblHargaAkhir);
        panelOutput.add(lblPenghematan);

        JPanel panelRiwayat = new JPanel(new BorderLayout());
        panelRiwayat.setBackground(new Color(240, 240, 240));
        panelRiwayat.add(new JLabel("Riwayat Perhitungan:"), BorderLayout.NORTH);
        taRiwayat = new JTextArea();
        taRiwayat.setFont(textFont);
        taRiwayat.setEditable(false);
        panelRiwayat.add(new JScrollPane(taRiwayat), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panelInput, BorderLayout.NORTH);
        add(panelOutput, BorderLayout.CENTER);
        add(panelRiwayat, BorderLayout.SOUTH);

        btnHitung.addActionListener(e -> hitungDiskon());
        cbDiskon.addItemListener(e -> sliderDiskon.setValue(getSelectedDiskon()));
        sliderDiskon.addChangeListener(e -> {
            int value = sliderDiskon.getValue();
            cbDiskon.setSelectedItem(value + "%");
        });

        tfHargaAsli.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Masukkan hanya angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void hitungDiskon() {
        try {
            String hargaAsliText = tfHargaAsli.getText();
            if (hargaAsliText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan harga asli!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double hargaAsli = Double.parseDouble(hargaAsliText);
            int diskon = sliderDiskon.getValue(); // Ambil diskon langsung dari slider

            String kodeKupon = tfKodeKupon.getText().trim();
            if (kodeKupon.equalsIgnoreCase("DISKON10")) {
                diskon += 10;
                JOptionPane.showMessageDialog(this, "Kode kupon valid! Diskon tambahan 10%.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

            if (diskon > 100) diskon = 100;

            double penghematan = hargaAsli * diskon / 100;
            double hargaAkhir = hargaAsli - penghematan;

            lblHargaAkhir.setText(String.format("Harga Akhir: Rp %.2f", hargaAkhir));
            lblPenghematan.setText(String.format("Penghematan: Rp %.2f", penghematan));

            taRiwayat.append(String.format("Harga Asli: Rp %.2f, Diskon: %d%%, Harga Akhir: Rp %.2f\n", hargaAsli, diskon, hargaAkhir));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSelectedDiskon() {
        String selectedItem = (String) cbDiskon.getSelectedItem();
        return Integer.parseInt(selectedItem.replace("%", ""));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikasiPerhitunganDiskon app = new AplikasiPerhitunganDiskon();
            app.setVisible(true);
        });
    }
}
