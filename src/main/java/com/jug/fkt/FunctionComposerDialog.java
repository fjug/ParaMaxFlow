/**
 *
 */
package com.jug.fkt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.jug.paramaxflow.gui.CostFunctionPanel;

/**
 * @author jug
 */
public class FunctionComposerDialog implements ActionListener {

	private static FunctionComposerDialog instance;

	private final JDialog dialog;

	private Function1D< Double > createdFkt;

	private final JButton bOk;
	private final JButton bCancel;
	private final JButton bPreview;

	private final JTabbedPane tabs;

	private boolean buttonOkPushed;

	private final JPanel tabFktConstant;
	private final JTextField txtConstantValue;

	private final JPanel tabFktSigmoid;
	private final JTextField txtSigmoidS;
	private final JTextField txtSigmoidHeight;
	private final JTextField txtSigmoidDx;
	private final JTextField txtSigmoidDy;

	private final JPanel tabFktGaussian;
	private final JTextField txtGaussianHeight;
	private final JTextField txtGaussianMean;

	private final JTextField txtGaussianSd;

	private final CostFunctionPanel plotPanel;

	public FunctionComposerDialog( final Function1D< Double > initFkt ) {
		this();
		if ( initFkt instanceof Constant1D ) {
			txtConstantValue.setText( "" + ( ( Constant1D ) initFkt ).getValue() );
			tabs.setSelectedComponent( tabFktConstant );
		} else if ( initFkt instanceof Sigmoid1D ) {
			txtSigmoidS.setText( "" + ( ( Sigmoid1D ) initFkt ).getS() );
			txtSigmoidHeight.setText( "" + ( ( Sigmoid1D ) initFkt ).getHeight() );
			txtSigmoidDx.setText( "" + ( ( Sigmoid1D ) initFkt ).getDx() );
			txtSigmoidDy.setText( "" + ( ( Sigmoid1D ) initFkt ).getDy() );
			tabs.setSelectedComponent( tabFktSigmoid );
		} else if ( initFkt instanceof Gaussian1D ) {
			txtGaussianHeight.setText( "" + ( ( Gaussian1D ) initFkt ).getHeight() );
			txtGaussianMean.setText( "" + ( ( Gaussian1D ) initFkt ).getMean() );
			txtGaussianSd.setText( "" + ( ( Gaussian1D ) initFkt ).getSd() );
			tabs.setSelectedComponent( tabFktGaussian );
		}
		updatePlotPanel();
	}

	public FunctionComposerDialog() {
		dialog = new JDialog();
		dialog.setModalityType( ModalityType.APPLICATION_MODAL );
		dialog.setBounds( 100, 100, 600, 300 );

		buttonOkPushed = false;

		final JPanel main = new JPanel( new BorderLayout() );
		tabs = new JTabbedPane();

		// ********************** Input section *****************************

		tabFktConstant = new JPanel( new GridBagLayout() );
		tabFktConstant.setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 5 ) );
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 0;
		c.gridy = 0;
		tabFktConstant.add( new JLabel( "Constant value:" ), c );
		c.weightx = 0.7;
		c.gridx = 1;
		c.gridy = 0;
		txtConstantValue = new JTextField();
		txtConstantValue.setText( "0.0" );
		tabFktConstant.add( txtConstantValue, c );

		tabFktSigmoid = new JPanel( new GridBagLayout() );
		tabFktSigmoid.setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 5 ) );
		c.weightx = 0.3;
		c.gridx = 0;
		c.gridy = 0;
		tabFktSigmoid.add( new JLabel( "s:" ), c );
		c.gridy = 1;
		tabFktSigmoid.add( new JLabel( "height:" ), c );
		c.gridy = 2;
		tabFktSigmoid.add( new JLabel( "dx:" ), c );
		c.gridy = 3;
		tabFktSigmoid.add( new JLabel( "dy:" ), c );
		c.weightx = 0.7;
		c.gridx = 1;
		c.gridy = 0;
		txtSigmoidS = new JTextField();
		txtSigmoidS.setText( "10.0" );
		tabFktSigmoid.add( txtSigmoidS, c );
		c.gridy = 1;
		txtSigmoidHeight = new JTextField();
		txtSigmoidHeight.setText( "1.0" );
		tabFktSigmoid.add( txtSigmoidHeight, c );
		c.gridy = 2;
		txtSigmoidDx = new JTextField();
		txtSigmoidDx.setText( "0.5" );
		tabFktSigmoid.add( txtSigmoidDx, c );
		c.gridy = 3;
		txtSigmoidDy = new JTextField();
		txtSigmoidDy.setText( "0.0" );
		tabFktSigmoid.add( txtSigmoidDy, c );

		tabFktGaussian = new JPanel( new GridBagLayout() );
		tabFktGaussian.setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 5 ) );
		c.weightx = 0.3;
		c.gridx = 0;
		c.gridy = 0;
		tabFktGaussian.add( new JLabel( "height:" ), c );
		c.gridy = 1;
		tabFktGaussian.add( new JLabel( "mean:" ), c );
		c.gridy = 2;
		tabFktGaussian.add( new JLabel( "sd:" ), c );
		c.weightx = 0.7;
		c.gridx = 1;
		c.gridy = 0;
		txtGaussianHeight = new JTextField();
		txtGaussianHeight.setText( "1.0" );
		tabFktGaussian.add( txtGaussianHeight, c );
		c.gridy = 1;
		txtGaussianMean = new JTextField();
		txtGaussianMean.setText( "0.5" );
		tabFktGaussian.add( txtGaussianMean, c );
		c.gridy = 2;
		txtGaussianSd = new JTextField();
		txtGaussianSd.setText( "0.25" );
		tabFktGaussian.add( txtGaussianSd, c );

		tabs.addTab( "Constant", tabFktConstant );
		tabs.addTab( "Sigmoid", tabFktSigmoid );
		tabs.addTab( "Gaussian", tabFktGaussian );

		bOk = new JButton( "ok" );
		bOk.addActionListener( this );
		bCancel = new JButton( "cancel" );
		bCancel.addActionListener( this );
		bPreview = new JButton( "preview" );
		bPreview.addActionListener( this );

		// ********************** Plot preview section
		// *****************************
		plotPanel = new CostFunctionPanel();
		plotPanel.setFixedBoundsOnX( 0, 1 );
		updatePlotPanel();

		main.add( plotPanel, BorderLayout.CENTER );
		main.add( tabs, BorderLayout.WEST );
		final JPanel panelButtons = new JPanel( new FlowLayout() );
		panelButtons.add( bCancel );
		panelButtons.add( bOk );
		panelButtons.add( bPreview );
		main.add( panelButtons, BorderLayout.SOUTH );
		dialog.add( main );

		dialog.getRootPane().setDefaultButton( bOk );
	}

	/**
	 *
	 */
	private void updatePlotPanel() {
		final int STEPS = 200;

		final Function1D< Double > fkt = this.createFunction();

		if ( fkt != null ) {
			plotPanel.removeAllPlots();

			final double[] xArray = new double[ STEPS ];
			final double[] fktArray = new double[ STEPS ];
			for ( int i = 0; i < STEPS; i++ ) {
				final double value = ( ( double ) i + 1 ) / STEPS;
				xArray[ i ] = value;
				fktArray[ i ] = fkt.evaluate( value );
			}

			plotPanel.addLinePlot( "function preview", new Color( 80, 255, 80 ), xArray, fktArray );
			plotPanel.refresh();
		}
	}

	/**
	 * @return
	 */
	public static Function1D< Double > openSingleton() {
		if ( instance == null ) {
			instance = new FunctionComposerDialog();
		}
		instance.show();

		if ( instance.wasCanceled() ) return null;
		return instance.getFunction();
	}

	/**
	 * @return
	 */
	public Function1D< Double > open() {
		this.show();

		if ( wasCanceled() ) return null;
		return getFunction();
	}

	/**
	 *
	 */
	private void show() {
		this.dialog.setVisible( true );
	}

	/**
	 *
	 */
	private void close() {
		this.dialog.setVisible( false );
	}

	/**
	 * @return
	 */
	private Function1D< Double > getFunction() {
		return createdFkt;
	}

	/**
	 * @return
	 */
	private boolean wasCanceled() {
		return !buttonOkPushed;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e ) {
		if ( e.getSource().equals( bOk ) ) {
			this.buttonOkPushed = true;
			this.createdFkt = createFunction();
			this.updatePlotPanel();
			if ( this.createdFkt != null ) {
				dialog.setVisible( false );
			}
		} else if ( e.getSource().equals( bCancel ) ) {
			this.buttonOkPushed = false;
			dialog.setVisible( false );
		} else if ( e.getSource().equals( bPreview ) ) {
			updatePlotPanel();
		}
	}

	/**
	 * @return
	 */
	private Function1D< Double > createFunction() {
		if ( tabs.getSelectedComponent().equals( tabFktConstant ) ) {
			try {
				final double val = Double.parseDouble( txtConstantValue.getText() );
				return new Constant1D( val );
			} catch ( final NumberFormatException ex ) {
				JOptionPane.showMessageDialog( this.dialog, "Value cannot be parsed as double." );
			}
		}
		if ( tabs.getSelectedComponent().equals( tabFktSigmoid ) ) {
			try {
				final double s = Double.parseDouble( txtSigmoidS.getText() );
				final double h = Double.parseDouble( txtSigmoidHeight.getText() );
				final double dx = Double.parseDouble( txtSigmoidDx.getText() );
				final double dy = Double.parseDouble( txtSigmoidDy.getText() );
				return new Sigmoid1D( s, h, dx, dy );
			} catch ( final NumberFormatException ex ) {
				JOptionPane.showMessageDialog( this.dialog, "Some value cannot be parsed as double." );
			}
		}
		if ( tabs.getSelectedComponent().equals( tabFktGaussian ) ) {
			try {
				final double h = Double.parseDouble( txtGaussianHeight.getText() );
				final double m = Double.parseDouble( txtGaussianMean.getText() );
				final double s = Double.parseDouble( txtGaussianSd.getText() );
				return new Gaussian1D( h, m, s );
			} catch ( final NumberFormatException ex ) {
				JOptionPane.showMessageDialog( this.dialog, "Some value cannot be parsed as double." );
			}
		}
		return null;
	}

}
