package org.geogebra.common.kernel.geos;

import org.geogebra.common.awt.GPoint;
import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.kernel.kernelND.GeoElementND;
import org.geogebra.common.kernel.parser.ParseException;
import org.geogebra.common.util.CopyPaste;
import org.geogebra.common.util.StringUtil;

/**
 * Library class for labeling geos; label-related static methods should go there
 */
public class LabelManager {

	/**
	 * Checks whether name can be used as label Parser.parseLabel takes care of
	 * checking unicode ranges and indices; this only checks for reserved names
	 * and CAS labels
	 * 
	 * @param geo
	 *            geo to be checked
	 * @param nameToCheck
	 *            potential label
	 * @return true for valid labels
	 */
	public static boolean checkName(GeoElementND geo, String nameToCheck) {
		String name = nameToCheck;
		if (name == null) {
			return true;
		}

		if (name.isEmpty() || name.startsWith(CopyPaste.labelPrefix)) {
			return false;
		}

		name = StringUtil.toLowerCaseUS(name);
		if (geo != null && geo.isGeoFunction()) {
			if (geo.getKernel().getApplication().getParserFunctions()
					.isReserved(name)) {
				return false;
			}
		}

		// $1 is a valid label for CAS cells, not other geos
		if (name.charAt(0) == '$' && (geo == null || !geo.isGeoCasCell())) {
			return false;
		}

		return true;
	}

	/**
	 * @param label
	 *            label
	 * @param kernel
	 *            kernel
	 * @return whether label can be parsed, is not reserved name and does not
	 *         start with $
	 */
	public static boolean isValidLabel(String label, Kernel kernel) {

		if (!checkName(null, label)) {
			return false;
		}

		try {
			// parseLabel for "A B" returns "A", check equality
			return label.trim()
					.equals(kernel.getAlgebraProcessor().parseLabel(label));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * @param var
	 *            variable name (for CAS cell)
	 * @return whether position(s) od $ are valid in this name
	 */
	public static boolean validVar(String var) {
		// check for invalid assignment variables like $, $$, $1, $2, ...,
		// $1$, $2$, ... which are dynamic references
		if (var.charAt(0) == GeoCasCell.ROW_REFERENCE_DYNAMIC) {
			boolean validVar = false;
			// if var.length() == 1 we have "$" and the for-loop won't be
			// entered
			for (int i = 1; i < var.length(); i++) {
				if (!Character.isDigit(var.charAt(i))) {
					if (i == 1 && var
							.charAt(1) == GeoCasCell.ROW_REFERENCE_DYNAMIC) {
						// "$$" so far, so it can be valid (if var.length >
						// 2) or invalid if "$$" is the whole var
					} else if (i == var.length() - 1 && var.charAt(var.length()
							- 1) == GeoCasCell.ROW_REFERENCE_DYNAMIC) {
						// "$dd...dd$" where all d are digits -> invalid
					} else {
						// "$xx..xx" where not all x are numbers and the
						// first x is not a '$' (there can only be one x)
						validVar = true;
						break;
					}
				}
			}
			return validVar;
		}
		return true;
	}

	/**
	 * set labels for array of GeoElements with given label prefix. e.g.
	 * labelPrefix = "F", geos.length = 2 sets geo[0].setLabel("F_1") and
	 * geo[1].setLabel("F_2") all members in geos are assumed to be initialized.
	 * 
	 * @param labelPrefix
	 *            prefix
	 * @param geos
	 *            array of geos to be labeled
	 */
	public static void setLabels(final String labelPrefix,
			final GeoElementND[] geos) {
		if (geos == null) {
			return;
		}

		int visible = 0;
		int firstVisible = 0;
		for (int i = geos.length - 1; i >= 0; i--) {
			if (geos[i].isVisible()) {
				firstVisible = i;
				visible++;
			}
		}

		switch (visible) {
		case 0: // no visible geos: they all get the labelPrefix as suggestion
			for (int i = 0; i < geos.length; i++) {
				geos[i].setLabel(labelPrefix);
			}
			break;

		case 1: // if there is only one visible geo, don't use indices
			geos[firstVisible].setLabel(labelPrefix);
			break;

		default:
			// is this a spreadsheet label?
			final GPoint p = GeoElementSpreadsheet
					.spreadsheetIndices(labelPrefix);
			if ((p.x >= 0) && (p.y >= 0)) {
				// more than one visible geo and it's a spreadsheet cell
				// use D1, E1, F1, etc as names
				final int col = p.x;
				final int row = p.y;
				for (int i = 0; i < geos.length; i++) {
					geos[i].setLabel(geos[i].getFreeLabel(GeoElementSpreadsheet
							.getSpreadsheetCellName(col + i, row)));
				}
			} else { // more than one visible geo: use indices if we got a
						// prefix
				for (int i = 0; i < geos.length; i++) {
					geos[i].setLabel(geos[i].getIndexLabel(labelPrefix));
				}
			}
		}
	}

	/**
	 * Sets labels for given geos
	 * 
	 * @param labels
	 *            labels
	 * @param geos
	 *            geos
	 * @param indexedOnly
	 *            true for labels a_1,a_2,a_3,...
	 */
	static void setLabels(final String[] labels, final GeoElement[] geos,
			final boolean indexedOnly) {
		final int labelLen = (labels == null) ? 0 : labels.length;

		if ((labelLen == 1) && (labels[0] != null) && !labels[0].equals("")) {
			setLabels(labels[0], geos);
			return;
		}

		String label;
		for (int i = 0; i < geos.length; i++) {
			if (i < labelLen) {
				label = labels[i];
			} else {
				label = null;
			}

			if (indexedOnly) {
				label = geos[i].getIndexLabel(label);
			}

			geos[i].setLabel(label);
		}
	}

	/**
	 * set labels for array of GeoElements pairwise: geos[i].setLabel(labels[i])
	 * 
	 * @param labels
	 *            array of labels
	 * @param geos
	 *            array of geos
	 */
	public static void setLabels(final String[] labels,
			final GeoElement[] geos) {
		setLabels(labels, geos, false);
	}
}
