package net.imglib2.newroi;

import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.newroi.util.IterableRandomAccessibleRegion;
import net.imglib2.newroi.util.SamplingIterableInterval;
import net.imglib2.type.BooleanType;

public class Regions
{
	// TODO: out-of-bounds / clipping

	public static < B extends BooleanType< B >, T > IterableInterval< T > sample( final IterableInterval< B > region, final RandomAccessible< T > img )
	{
		return SamplingIterableInterval.create( region, img );
	}

	public static < B extends BooleanType< B > > IterableInterval< B > iterable( final RandomAccessibleInterval< B > region )
	{
		if ( region instanceof IterableInterval )
			return ( IterableInterval< B > ) region;
		else
			return IterableRandomAccessibleRegion.create( region );
	}
}
