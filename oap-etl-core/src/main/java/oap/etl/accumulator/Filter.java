/*
 * The MIT License (MIT)
 *
 * Copyright (c) Open Application Platform Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package oap.etl.accumulator;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import oap.tsv.TypedListModel;

import java.util.List;
import java.util.function.Predicate;

@ToString
@EqualsAndHashCode()
public class Filter<T> implements Accumulator {
    final Accumulator accumulator;
    private final Predicate<T> filter;
    private int field;

    public Filter( Accumulator accumulator, int field, Predicate<T> filter ) {
        this.accumulator = accumulator;
        this.field = field;
        this.filter = filter;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void accumulate( List<Object> values ) {
        if( filter.test( ( T ) values.get( field ) ) ) accumulator.accumulate( values );
    }

    @Override
    public void reset() {
        accumulator.reset();
    }

    @Override
    public Object result() {
        return accumulator.result();
    }

    @Override
    public Filter<T> clone() {
        return new Filter<>( accumulator.clone(), field, filter );
    }

    @Override
    public TypedListModel.ColumnType getModelType() {
        return accumulator.getModelType();
    }
}
