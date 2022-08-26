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

package io.confluent.ksql.function.udf.math;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class GcdTest {

  private Gcd udf;

  @Before
  public void setUp() {
    udf = new Gcd();
  }

  @Test
  public void shouldHandleNull() {
    assertThat(udf.gcd((Integer) null, null), is(nullValue()));
    assertThat(udf.gcd((Long)null, null), is(nullValue()));
    assertThat(udf.gcd(2, null), is(nullValue()));
    assertThat(udf.gcd(2L, null), is(nullValue()));
    assertThat(udf.gcd(null, 2), is(nullValue()));
    assertThat(udf.gcd(null, 2L), is(nullValue()));
  }

  @Test
  public void shouldHandleNegativeAndNegative() {
    assertThat(udf.gcd(-45, -36), is(9));
    assertThat(udf.gcd(-45L, -36L), is(9L));
    assertThat(udf.gcd(Integer.MIN_VALUE, Integer.MIN_VALUE), is(Integer.MIN_VALUE));
    assertThat(udf.gcd(-2147483646, -4), is(2));
  }

  @Test
  public void shouldHandleNegativeAndZero() {
    assertThat(udf.gcd(-45, 0), is(45));
    assertThat(udf.gcd(-45L, 0L), is(45L));
    assertThat(udf.gcd(Integer.MIN_VALUE, 0), is(Math.abs(Integer.MIN_VALUE)));
  }

  @Test
  public void shouldHandleNegativeAndPositive() {
    assertThat(udf.gcd(-45, 36), is(9));
    assertThat(udf.gcd(-45L, 36L), is(9L));
    assertThat(udf.gcd(Integer.MIN_VALUE, -4), is(4));
    assertThat(udf.gcd(-2147483646, -4), is(2));
  }

  @Test
  public void shouldHandleZeroAndZero() {
    assertThat(udf.gcd(0, 0), is(0));
    assertThat(udf.gcd(0L, 0L), is(0L));
  }

  @Test
  public void shouldHandlePositiveAndNegative() {
    assertThat(udf.gcd(45, 36), is(9));
    assertThat(udf.gcd(45L, 36L), is(9L));
    assertThat(udf.gcd(2147483646, -4), is(2));
  }

  @Test
  public void shouldHandlePositiveAndZero() {
    assertThat(udf.gcd(45, 0), is(45));
    assertThat(udf.gcd(45L, 0L), is(45L));
    assertThat(udf.gcd(Integer.MAX_VALUE, 0), is(Integer.MAX_VALUE));
  }

  @Test
  public void shouldHandlePositiveAndPositive() {
    assertThat(udf.gcd(45, 36), is(9));
    assertThat(udf.gcd(45L, 36L), is(9L));
    assertThat(udf.gcd(Integer.MAX_VALUE, Integer.MAX_VALUE), is(Integer.MAX_VALUE));
    assertThat(udf.gcd(2147483646, 4), is(2));
  }

}