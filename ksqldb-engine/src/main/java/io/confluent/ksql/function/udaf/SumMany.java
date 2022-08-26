/*
 * Copyright 2022 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.function.udaf;

import io.confluent.ksql.util.KsqlConstants;
import io.confluent.ksql.util.Triple;

@UdafDescription(
        name = "SUM_MANY",
        description = "Testing multiple and variadic arguments",
        author = KsqlConstants.CONFLUENT_AUTHOR
)
public class SumMany implements Udaf<Triple<Integer, Integer, VariadicArgs<Double>>, Double, Double> {

  @UdafFactory(description = "Testing multiple and variadic arguments.")
  public static Udaf<Triple<Integer, Integer, VariadicArgs<Double>>, Double, Double> createSumMany() {
    return new SumMany(0);
  }

  @UdafFactory(description = "Testing multiple and variadic arguments.")
  public static Udaf<Triple<Integer, Integer, VariadicArgs<Double>>, Double, Double> createSumMany(int base) {
    return new SumMany(base);
  }

  private final int baseValue;

  public SumMany(final int baseValue) {
    this.baseValue = baseValue;
  }

  @Override
  public Double initialize() {
    return 0.0;
  }

  @Override
  public Double aggregate(Triple<Integer, Integer, VariadicArgs<Double>> current, Double aggregate) {
    final double first = current.getLeft() == null ? 0.0 : current.getLeft();
    final double second = current.getMiddle() == null ? 0.0 : current.getMiddle();
    final double third = current.getRight().stream().reduce(0.0, (a, b) ->
            a + (b == null ? 0 : b)
    );

    return first + second + third + aggregate;
  }

  @Override
  public Double merge(Double aggOne, Double aggTwo) {
    return aggOne + aggTwo;
  }

  @Override
  public Double map(Double agg) {
    return agg + baseValue;
  }
}
