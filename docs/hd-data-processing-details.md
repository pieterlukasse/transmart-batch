Processing details
==================

The data will be scanned twice. The first pass will only validate the data set.

n the second pass, the data will be inserted in the database. Zeros, negative
values and NaNs in the data file will *not* be inserted into the database.


The `zscore` column will be calculated as:

    clamp(-2.5, 2.5, (log_intensity - mean) / stdDev)

where `mean` and `stdDev` are values calculated over the `log_intensity` values
of all the values included in the same row (for the same probe). If the original
value (before the log calculation) was a non-positive number or `NaN`s, then it
will be excluded from the calculation of these statistics. This means each row
must have at least two positive numbers, otherwise the standard deviation cannot
be calculated. If the standard deviation is zero, the resulting zscore for all
the values in the row will be NaN. This NaN value *will be inserted* into the
database.

Finally, `clamp` is defined as

    double clamp(double lowerBound, double upperBound, double value) {
        Math.min(upperBound, Math.max(lowerBound, value))
    }

Clamping may not be a good idea, but it was implemented in this fashion in order
to match the value in the Kettle + stored procedure pipeline.