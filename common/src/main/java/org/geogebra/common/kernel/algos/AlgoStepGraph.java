/*
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org
This file is part of GeoGebra.
This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by
the Free Software Foundation.
 */
package org.geogebra.common.kernel.algos;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.commands.Commands;
import org.geogebra.common.kernel.geos.GeoBoolean;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoNumeric;

/**
 * Stick graph algorithm
 * 
 * @author G. Sturr
 * 
 */
public class AlgoStepGraph extends AlgoBarChart {

	/******************************************************
	 * StepGraph[&lt;list of points&gt;]
	 * 
	 * @param cons
	 *            construction
	 * @param label
	 *            label
	 * @param list1
	 *            first list
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1) {

		super(cons, label, list1, null, null, null, null, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;list of points&gt;, &lt;boolean hasJump&gt;]
	 * 
	 * @param cons
	 * @param label
	 * @param list1
	 * @param hasJump
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1,
			GeoBoolean hasJump) {

		super(cons, label, list1, null, null, null, hasJump, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;list of points&gt;, &lt;boolean hasJump&gt;, &lt;point
	 * style&gt;]
	 * 
	 * @param cons
	 * @param label
	 * @param list1
	 * @param hasJump
	 * @param pointStyle
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1,
			GeoBoolean hasJump, GeoNumeric pointStyle) {

		super(cons, label, list1, null, null, null, hasJump, pointStyle,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;x list&gt;, &lt;y list&gt;]
	 * 
	 * @param cons
	 * @param label
	 * @param list1
	 * @param list2
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1,
			GeoList list2) {

		super(cons, label, list1, list2, null, null, null, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;x list&gt;, &lt;y list&gt;] (no label)
	 * 
	 * @param cons
	 * @param list1
	 * @param list2
	 */
	public AlgoStepGraph(Construction cons, GeoList list1, GeoList list2) {
		super(cons, list1, list2, null, null, null, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;x list&gt;, &lt;y list&gt;, &lt;boolean hasJump&gt;]
	 * 
	 * @param cons
	 * @param label
	 * @param list1
	 * @param list2
	 * @param hasJump
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1,
			GeoList list2, GeoBoolean hasJump) {
		super(cons, label, list1, list2, null, null, hasJump, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;x list&gt;, &lt;y list&gt;, &lt;boolean hasJump&gt;] (no
	 * label)
	 * 
	 * @param cons
	 * @param list1
	 * @param list2
	 * @param hasJump
	 */
	public AlgoStepGraph(Construction cons, GeoList list1, GeoList list2,
			GeoBoolean hasJump) {
		super(cons, list1, list2, null, null, hasJump, null,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	/******************************************************
	 * StepGraph[&lt;x list&gt;, &lt;y list&gt;, &lt;boolean hasJump&gt;,
	 * &lt;point style&gt;]
	 * 
	 * @param cons
	 * @param label
	 * @param list1
	 * @param list2
	 * @param showStep
	 */
	public AlgoStepGraph(Construction cons, String label, GeoList list1,
			GeoList list2, GeoBoolean showStep, GeoNumeric pointStyle) {
		super(cons, label, list1, list2, null, null, showStep, pointStyle,
				AlgoBarChart.TYPE_STEPGRAPH);
	}

	@Override
	public Commands getClassName() {
		return Commands.StepGraph;
	}

}