/*
 * ParaMaxFlow ImageJ Plugin
 * Native code by Vladimir Kolmogorov, Plugin-code by Florian Jug.
 * Classification-extension uses WEKA and the Trainable-segmentation PlugIn.
 */

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;

import com.jug.paramaxflow.gui.ParaMaxFlowPanel;

/**
 * ParaMaxFlow
 *
 * @author Florian Jug
 */
public class ParaMaxFlow_ implements PlugIn {
	protected ImagePlus image;

	/**
	 * @see ij.plugin.PlugIn#run(java.lang.String)
	 */
	@Override
	public void run( final String arg ) {
		ParaMaxFlowPanel.main( null );
	}

	/**
	 * Main method for debugging.
	 *
	 * For debugging, it is convenient to have a method that starts ImageJ,
	 * loads an
	 * image and calls the plugin, e.g. after setting breakpoints.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(final String[] args) {
		// set the plugins.dir property to make the plugin appear in the Plugins menu
		final Class<?> clazz = ParaMaxFlow_.class;
		final String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
		final String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
		System.setProperty("plugins.dir", pluginsDir);

		// start ImageJ
		new ImageJ();

		// open the Clown sample
		final ImagePlus image = IJ.openImage("http://imagej.net/images/clown.jpg");
		image.show();

		// run the plugin
		IJ.runPlugIn(clazz.getName(), "");
	}

}
