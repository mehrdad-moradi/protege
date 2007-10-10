package org.protege.editor.core.ui.error;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 28-Feb-2007<br><br>
 */
public class ErrorLogPanel extends JPanel {

    private ErrorLog errorLog;

    private JTextArea textArea;

    private SendErrorReportHandler errorReportHandler;


    public ErrorLogPanel(ErrorLog errorLog, SendErrorReportHandler handler) {
        this.errorLog = errorLog;
        this.errorReportHandler = handler;
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        JPanel contentPane = new JPanel(new BorderLayout(7, 7));
        contentPane.add(new JScrollPane(textArea));
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(new JButton(new AbstractAction("Send bug report") {
            public void actionPerformed(ActionEvent e) {
                if (handleSendErrorReport()) {
                    ErrorLogPanel.this.errorLog.clear();
                }
            }
        }), BorderLayout.WEST);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        add(contentPane);
    }


    private boolean handleSendErrorReport() {
        return errorReportHandler.sendErrorReport(errorLog);
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }


    public void setVisible(boolean b) {
        fillLog();
        Component parent = getParent();
        if (parent != null) {
            int w = (parent.getWidth() - getWidth()) / 2;
            int h = (parent.getHeight() - getHeight()) / 2;
            setLocation(w, h);
        }
        super.setVisible(b);
    }


    public void fillLog() {
        textArea.setText("");
        for (ErrorLog.ErrorLogEntry entry : errorLog.getEntries()) {
            textArea.append(entry.toString());
            textArea.append(
                    "---------------------------------------------------------------------------------------------------\n\n");
        }
    }
}
