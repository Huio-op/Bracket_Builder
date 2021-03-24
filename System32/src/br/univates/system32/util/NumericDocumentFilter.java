package br.univates.system32.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {

    private int maxChars;

    public NumericDocumentFilter(int maxChars) {
        this.maxChars = maxChars;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr)
            throws BadLocationException {

        StringBuilder buffer = new StringBuilder(text);
        for (int i = buffer.length() - 1; i >= 0; i--) {
            char ch = buffer.charAt(i);
            if (!Character.isDigit(ch)) {
                buffer.deleteCharAt(i);
            }
        }

        text = buffer.toString();
        if (fb.getDocument().getLength() + text.length() > maxChars) {
            int remaining = maxChars - fb.getDocument().getLength();
            text = text.substring(0, remaining);
        }

        if (text.length() > 0) {
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb,
            int offset, int length, String string, AttributeSet attr) throws BadLocationException {
        if (length > 0) {
            fb.remove(offset, length);
        }
        insertString(fb, offset, string, attr);
    }
}
