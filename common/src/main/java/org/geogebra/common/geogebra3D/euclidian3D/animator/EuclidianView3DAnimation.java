package org.geogebra.common.geogebra3D.euclidian3D.animator;

import org.geogebra.common.geogebra3D.euclidian3D.EuclidianView3D;
import org.geogebra.common.geogebra3D.euclidian3D.animator.EuclidianView3DAnimator.AnimationType;

/**
 * class for 3D view animations
 *
 */
public abstract class EuclidianView3DAnimation {

	/**
	 * 3D view
	 */
	protected EuclidianView3D view3D;
	/**
	 * 3D view animator
	 */
	protected EuclidianView3DAnimator animator;

	/**
	 * constructor
	 * 
	 * @param view3D
	 *            3D view
	 * @param animator
	 *            3D view animator
	 */
	public EuclidianView3DAnimation(EuclidianView3D view3D, EuclidianView3DAnimator animator) {
		this.view3D = view3D;
		this.animator = animator;
	}

	/**
	 * setup values for start
	 */
	abstract public void setupForStart();

	/**
	 * 
	 * @return animation type
	 */
	abstract public AnimationType getType();

	/**
	 * process animation
	 */
	abstract public void animate();

	/**
	 * end animation
	 */
	public void end() {
		animator.stopAnimation();
	}

}