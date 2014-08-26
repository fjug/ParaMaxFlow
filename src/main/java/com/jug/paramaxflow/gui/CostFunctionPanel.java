package com.jug.paramaxflow.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.Location;
import de.erichseifert.gral.util.Orientation;

public class CostFunctionPanel extends JPanel
{
	/** First corporate color used for normal coloring. */
	protected static final Color COLOR1 = new Color( 55, 170, 200 );

	/** Second corporate color used as signal color */
	protected static final Color COLOR2 = new Color( 200, 80, 75 );

	private static final int SAMPLE_COUNT = 200;

	private XYPlot plot = null;

	private InteractivePanel interactivePanel = null;

	@SuppressWarnings( "unchecked" )
	public CostFunctionPanel()
	{

		super( new BorderLayout() );
		setPreferredSize( new Dimension( 800, 600 ) );
		setBackground( Color.WHITE );

		plot = new XYPlot();
		plot.getAxis( plot.AXIS_X ).setAutoscaled( false );

		plot.setInsets( new Insets2D.Double( 20.0, 180.0, 40.0, 40.0 ) );
		plot.setLegendVisible( true );
		//
		plot.setLegendLocation( Location.WEST );
		// plot.setLegendDistance(20);

		// Format legend
		plot.getLegend().setOrientation( Orientation.VERTICAL );
		// plot.getLegend().setAlignmentY(0.0);

		// Add plot to Swing component
		interactivePanel = new InteractivePanel( plot );
		add( interactivePanel, BorderLayout.CENTER );
	}

	private static void formatLine( final XYPlot plot, final DataSeries series, final Color color )
	{
		plot.setPointRenderer( series, null );
		final DefaultLineRenderer2D line = new DefaultLineRenderer2D();
		line.setColor( color );
		plot.setLineRenderer( series, line );
	}

	public void removeAllPlots()
	{
		plot.clear();
	}

	public void addLinePlot( final String string, final Color color, final double[] xArray, final double[] yArray )
	{
		final DataTable data = new DataTable( Double.class, Double.class );

		for ( int i = 0; i < SAMPLE_COUNT; i++ )
		{
			data.add( xArray[ i ], yArray[ i ] );
		}

		final DataSeries ds = new DataSeries( string, data, 0, 1 );
		plot.add( ds );
		formatLine( plot, ds, color );
	}

	public void setFixedBoundsOnX( final double min, final double max )
	{
		plot.getAxis( plot.AXIS_X ).setRange( new Double( min ), new Double( max ) );
		plot.getAxis( plot.AXIS_Y ).setAutoscaled( true );
	}

	public void refresh()
	{
		interactivePanel.repaint();
	}
}
