package net.sf.jabref.export.layout;

import net.sf.jabref.export.layout.LayoutFormatter;

import java.util.List;
import java.util.ArrayList;

/**
 * This is an abstract implementation of ParamLayoutFormatter, which provides some
 * functionality for the handling of argument strings.
 */
public abstract class AbstractParamLayoutFormatter implements ParamLayoutFormatter {

    private static char SEPARATOR = ',';

    /**
     * Parse an argument string and return the parts of the argument. The parts are
     * separated by commas, and escaped commas are reduced to literal commas.
     * @param arg The argument string.
     * @return An array of strings representing the parts of the argument.
     */
    public static String[] parseArgument(String arg) {
        List<String> parts = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean escaped = false;
        for (int i=0; i<arg.length(); i++) {
            if ((arg.charAt(i) == SEPARATOR) && !escaped) {
                parts.add(current.toString());
                current = new StringBuilder();
            } else if (arg.charAt(i) == '\\') {
                if (escaped) {
                    escaped = false;
                    current.append(arg.charAt(i));
                } else
                    escaped = true;
            } else if (escaped) {
                // Handle newline and tab:
                if (arg.charAt(i)=='n')
                    current.append('\n');
                else if (arg.charAt(i)=='t')
                    current.append('\t');
                else {
                    if ((arg.charAt(i) != ',') && (arg.charAt(i) != '"'))
                        current.append('\\');
                    current.append(arg.charAt(i));
                }
                escaped = false;
            } else
                current.append(arg.charAt(i));
        }
        parts.add(current.toString());
	    return parts.toArray(new String[parts.size()]);
    }

}