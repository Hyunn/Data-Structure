/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p4
// FILE:             Interval.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This interval can be used to represent various things. For example, in
 * scheduling problem, this will represent the start and end dates for an
 * assignment. This Interval will be stored as a data member inside our
 * IntervalTree.
 * 
 * <pre>
 * public Interval(T start, T end, String label)
 * </pre>
 * 
 * @param <T>
 *            the template param for start/end fields.
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	// declare data of interval
	private String label;
	private T start;
	private T end;

	public Interval(T start, T end, String label) {
		this.start = start;
		this.end = end;
		this.label = label;
	}

	/** Returns the start value (must be Comparable<T>) of the interval. */
	@Override
	public T getStart() {
		return start;
	}

	/** Returns the end value (must be Comparable<T>) of the interval. */
	@Override
	public T getEnd() {
		return end;
	}

	/** Returns the label for the interval. */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Return true if this interval overlaps with the other interval.
	 * 
	 * @param other
	 *            target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
	@Override
	public boolean overlaps(IntervalADT<T> other) {
		if (other == null)
			throw new IllegalArgumentException();

		// when interval is not overlaps
		if (end.compareTo(other.getStart()) < 0 || start.compareTo(other.getEnd()) > 0)
			return false;
		else
			return true;
	}

	/**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
	@Override
	public boolean contains(T point) {
		if (end.compareTo(point) <= 0 || start.compareTo(point) >= 0)
			return true;
		else
			return false;
	}

	/**
	 * Compares this interval with the other and return a negative value if this
	 * interval comes before the "other" interval. Intervals are compared first
	 * on their start time. The end time is only considered if the start time is
	 * the same.
	 * 
	 * @param other
	 *            the second interval to which compare this interval with
	 * 
	 * @return negative if this interval's comes before the other interval,
	 *         positive if this interval comes after the other interval, and 0
	 *         if the intervals are the same.
	 */
	@Override
	public int compareTo(IntervalADT<T> other) {
		// comparing ends data if starts are same
		if (start.equals(other.getStart()))
			return end.compareTo(other.getEnd());
		else {
			// duplicate intervals are not allowed
			if (end.equals(other.getEnd()))
				throw new IllegalArgumentException();
			return start.compareTo(other.getStart());
		}
	}

	/**
	 * Returns a specific string representation of the interval. It must return
	 * the interval in this form.
	 * 
	 * <p>
	 * For example: If the interval's label is p1 and the start is 24 and the
	 * end is 45, then the string returned is:
	 * </p>
	 * 
	 * <pre>
	 * p1 [24, 45]
	 * </pre>
	 */
	public String toString() {
		return this.getLabel() + " [" + this.getStart() + ", " + this.getEnd() + "]";
	}

}
