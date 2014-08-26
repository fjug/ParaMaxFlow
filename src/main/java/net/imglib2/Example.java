package net.imglib2;

import net.imglib2.histogram.DiscreteFrequencyDistribution;
import net.imglib2.histogram.Histogram1d;
import net.imglib2.histogram.Real1dBinMapper;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.newroi.Regions;
import net.imglib2.newroi.util.ContainsRandomAccessible;
import net.imglib2.newroi.util.LocalizableSet;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.Views;

public class Example {

	static class MyCircle implements LocalizableSet {

		@Override
		public int numDimensions() {
			return 2;
		}

		@Override
		public boolean contains( final Localizable p ) {
			final double dx = p.getDoublePosition( 0 ) - 100;
			final double dy = p.getDoublePosition( 1 ) - 100;
			return dx * dx + dy * dy < 1000;
		}
	}

	public static void main( final String[] args ) {
		final Img< UnsignedByteType > img = ArrayImgs.unsignedBytes( 400, 400 );

		// create a (unbounded) BoolType RandomAccessible with pixel value decided by MyCircle.contains
		final RandomAccessible< BoolType > a = new ContainsRandomAccessible( new MyCircle() );

		// restrict that to the interval of img
		final RandomAccessibleInterval< BoolType > b = Views.interval( a, img );

		// make IteratableInterval that iterates only over the True pixels
		final IterableInterval< BoolType > c = Regions.iterable( b );

		// sample img with that: IteratableInterval that iterates only over the
		// img pixels for whose coordinates MyCircle.contains() is true
		final IterableInterval< UnsignedByteType > samples = Regions.sample( c, img );

		// Now I use it to fill the img pixels where MyCircle.contains() is true
		for ( final UnsignedByteType t : samples )
			t.set( 255 );
		ImageJFunctions.show( img );

		// instead Histogram1d could be computed like this:
		final int numBins = 255;
		final Histogram1d< UnsignedByteType > histogram = new Histogram1d< UnsignedByteType >( samples, new Real1dBinMapper< UnsignedByteType >( 0, 255, numBins, false ) );
		final DiscreteFrequencyDistribution dfd = histogram.dfd();
		// ... or ...
		final long[] longArray = histogram.toLongArray();
		for ( final long l : longArray )
			System.out.print( l + " " );
		System.out.println();
		// ...
	}
}
